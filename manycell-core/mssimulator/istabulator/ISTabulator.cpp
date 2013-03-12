/* Begin CVS Header
 *  $Source: $
 *  $Revision: 1.1 $
 *  $Name:  $
 *  $Author: jdada $
 *  $Date: 2010/05/25 11:53:29 $
 * End CVS Header
 *
 *<!---------------------------------------------------------------------------
 * This file is part of multi-scale simulation software.
 *
 * Copyright 2010-2012 The University of Manchester.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation.  A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 *----------------------------------------------------------------------- -->
 */
#include "ISTabulator.h"
#include <math.h>
#include <sstream>

extern "C" {
	// Get declaration for all ellipsoid fortran functions
#include "ellipsoid.h"
}

using namespace std;

ISTabulator::ISTabulator() {
	// TODO Auto-generated constructor stub
}

float maxFind(double *value, int n) {
	float max = 0;
	for (int i = 0; i < n; i++) {
		if (max < value[i])
			max = value[i];
	}
	return max;
}

int ISTabulator::runTimeCourseSimulator(
		std::string model,
		std::string modelId,
		float tolerance,
		string* speciesId,
		double* sInitialValue,
		float stepSize,
		int nTimeSteps,
		int nSpecies,
		int nNonConstSpecies,
		float** data2DArray)
{
	//calculate the scale diagonal matrix (vector in this case) for the error
	//nSpecies = nSpecies-3;
	double sDiagMat[nNonConstSpecies];
	for(int i=0; i<nNonConstSpecies; i++)sDiagMat[i] = sInitialValue[i]/maxFind(sInitialValue, nNonConstSpecies);

	//set the return value to -1
	int success=-1;
	cout <<"My empty"<< mISTable.isEmpty()<<endl;
	cout <<"my Counter: " <<counter << endl;
	cout <<"Non Const Species: "<<nNonConstSpecies<<endl;
	int nL =(nNonConstSpecies*(nNonConstSpecies+1))/2;
	float **sMatrix;


	//double sMatrixF[nSpecies*nSpecies];
	double sValue_new[] = {19.9, 0.0, 0.0, 0.0, 0.0, 0.002, 0.0002, 1.5e-05};

	if(mISTable.isEmpty()){
		double sMatrixF[nNonConstSpecies*nNonConstSpecies];
		sMatrix = new float *[nNonConstSpecies];
			for (int j=0; j<nNonConstSpecies; j++){
				sMatrix[j] = new float[nNonConstSpecies];
			}
		int simId = 1;
		//create a simulator factory
		SimulatorFactory *factory = new SimulatorFactory();

		//use the simulator id (simId) variable above, which is 1 in this case for direct copasiws to create the instance of simulator
		Simulator *sim = factory->getSimulatorInstance(simId);
		cout <<"=========Go Here ================";
		//run the simulator
		success = sim->runTimeCourseSimulator(model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0], &sMatrix[0]);

		cout <<"Time course matrix data"<<endl;
			if(!success){
				for(int i = 0; i< nTimeSteps+1; i++){
					for(int j = 0; j < nNonConstSpecies+1; j++){
						cout << data2DArray[i][j] << "\t";
					}
					cout << endl;
				}
			}
		//copy the necessary gradient matrix data from actual sensitivity matrix
		if(!success){
			for(int i = 0; i< nNonConstSpecies; i++){
				for(int j = 0; j <nNonConstSpecies; j++){
					sMatrixF[i*nNonConstSpecies +j] = (double)(sDiagMat[i]*sMatrix[i][j]/tolerance);
				}
			}
		}
		double matrixL[nL];
		cout<<"\n===========Cholesky representation===========";
		cout <<nL<<endl;
		int n = nNonConstSpecies;
		ell_bbt2chol_(&n, &sMatrixF[0], matrixL);

		tree_node node;
		node.cholSMatrixL = new double[nL];
		node.cholSMatrixL = matrixL;

		for(int i = 0; i<nL; i++){
			node.cholSMatrixL[i] = matrixL[i];
			cout <<node.cholSMatrixL[i] <<endl;
		}

		//total no of matrix rows = number of time step require + 1 (1 for take care of the 0 step)
			int nRow = nTimeSteps+1;

			//matrix memory allocation
			node.timeCourseData = new float *[nRow];
			int i = 0, j = 0;

			for (i=0; i<nRow; i++){
				node.timeCourseData[i] = new float[nNonConstSpecies+1]; //add 1 to no of species to take care of time column
			}
			for(int i=0; i<nRow; i++)
				for(int j = 0; j<nNonConstSpecies+1; j++)
					node.timeCourseData[i][j] = data2DArray[i][j];

			node.sInitialValue = new double[nNonConstSpecies];
			for(int i = 0; i<nNonConstSpecies; i++)
				node.sInitialValue[i] = sInitialValue[i];

		node.nSpecies = nNonConstSpecies;
		node.nTimeSteps = nTimeSteps;

		//mISTable.insert(1, tolerance, &data2DArray[0], nTimeSteps, nSpecies);
		mISTable.insert(&node);
	//	delete sMatrix;

		//TODO
	}else{
		//get the data from the tree if the tree is not NULL
		tree_node *node; // = NULL;
		//	sInitialValue[0] = sInitialValue[0]+8;
		node = mISTable.search(sInitialValue);
		if(node!=NULL){
			cout <<"Retrieving from tree\n";
			for(int i = 0; i<nNonConstSpecies; i++){
				cout << node->sInitialValue[i]<<endl;
			}
			for(int i = 0; i< nTimeSteps+1; i++){
				for(int j = 0; j <nNonConstSpecies+1; j++){
					data2DArray[i][j] = node->timeCourseData[i][j];
					cout << data2DArray[i][j] <<"\t";
				}
				cout <<endl;
			}

			//calculate the eigenvalues
			int nStore = node->nSpecies;
			double uMatrix[nStore*nStore];
			double sMatrixL[nStore][nStore];
			double sMatrixLT[nStore][nStore];
			double lam[nStore];
			ell_chol2eig_(&nStore, node->cholSMatrixL, &uMatrix[0], lam);
			cout <<"Printing the eigenvalues"<<endl;
			for (int i=0; i<nStore; i++){
				cout<<"\n"<<lam[i];
			}
			/*for(int i = 0; i<(nStore*nStore); i++){
				cout<<uMatrix[i]<<endl;
			}*/

			for(int i=0; i<nStore; i++){
				for(int j=0; j<nStore; j++){
					sMatrixLT[i][j] = 0;
					sMatrixL[i][j] = 0;
				}
			}


			int k = 0;
			for(int i=0; i<nStore; i++){
				for(int j=i; j<nStore; j++){
					sMatrixL[j][i] = node->cholSMatrixL[k];
					sMatrixLT[j][i] = sMatrixL[j][i];
					k = k+1;
					cout <<sMatrixL[j][i] << "\t";
				}
				cout <<endl;
			}

			//multiplying to check for error
			double sValueDiff[nStore];
			double vCalculated[nStore];

			for(int i=0; i<nStore; i++){
				sValueDiff[i] = sInitialValue[i]-node->sInitialValue[i];
			}

			for(int i=0;i<nStore;i++){
				double value = 0;
				for(int k=0;k<nStore;k++){
					value=value+(sMatrixL[i][k]*sValueDiff[k]);
				}
				vCalculated[i]=value*(node->sInitialValue[i]/maxFind(node->sInitialValue, nStore));
			}
			cout << "Checking the error vector"<< endl;
			for(int i=0; i<nStore; i++){
				cout <<vCalculated[i] <<endl;
			}
			cout <<"End of error vector checking" <<endl;

			//approximate the time course value from the retrieve value
			cout <<"New approximate value"<<endl;
			for(int i = 0; i< nTimeSteps+1; i++){
				for(int j = 0; j <nNonConstSpecies+1; j++){
					if(j>0){
						data2DArray[i][j] = data2DArray[i][j] + vCalculated[j-1];
						if(data2DArray[i][j]<0)data2DArray[i][j] =data2DArray[i][j]*-1;
					}
					cout << data2DArray[i][j] <<"\t";
				}
				cout <<endl;
			}
		}else{
			double sMatrixF[nNonConstSpecies*nNonConstSpecies];
			sMatrix = new float *[nNonConstSpecies];
			for (int j=0; j<nNonConstSpecies; j++){
				sMatrix[j] = new float[nNonConstSpecies];
			}
			int simId = 1;
			//create a simulator factory
			SimulatorFactory *factory = new SimulatorFactory();

			//use the simulator id (simId) variable above, which is 1 in this case for direct copasiws to create the instance of simulator
			Simulator *sim = factory->getSimulatorInstance(simId);

			//run the simulator
			success = sim->runTimeCourseSimulator(model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0], &sMatrix[0]);

			//copy the necessary gradient matrix data from actual sensitivity matrix
			if(!success){
				for(int i = 0; i< nNonConstSpecies; i++){
					for(int j = 0; j <nNonConstSpecies; j++){
						sMatrixF[i*nNonConstSpecies +j] = (double)(sDiagMat[i]*sMatrix[i][j]/tolerance);
					}
				}
			}
			cout<<"\nSubsequent ===========Cholesky representation===========";
			double matrixL[nL];
			int n = nNonConstSpecies;
			ell_bbt2chol_(&n, &sMatrixF[0], matrixL);

			tree_node node;
			node.cholSMatrixL = new double[nL];

			for(int i = 0; i<nL; i++){
				node.cholSMatrixL[i] = matrixL[i];
				cout <<node.cholSMatrixL[i] <<endl;
			}

			//total no of matrix rows = number of time step require + 1 (1 for take care of the 0 step)
			int nRow = nTimeSteps+1;

			//matrix memory allocation
			node.timeCourseData = new float *[nRow];

			for (int i=0; i<nRow; i++){
				node.timeCourseData[i] = new float[nNonConstSpecies+1]; //add 1 to no of species to take care of time column
			}
			for(int i=0; i<nRow; i++)
				for(int j = 0; j<nNonConstSpecies+1; j++)
					node.timeCourseData[i][j] = data2DArray[i][j];

			//node.timeCourseData = data2DArray;

			node.sInitialValue = new double[nNonConstSpecies];

			//new initial condition
			cout <<"My New initial value"<<endl;
			for(int i=0; i<nNonConstSpecies; i++){
				node.sInitialValue[i] = sInitialValue[i];
				cout<<node.sInitialValue[i]<<endl;
			}

			node.nSpecies = nNonConstSpecies;
			node.nTimeSteps = nTimeSteps;
			//mISTable.insert(1, tolerance, &data2DArray[0], nTimeSteps, nSpecies);
			mISTable.insert(&node);

		}

		counter++;
		//mISTable.print_preorder();
	}

	//mISTable.print_inorder();
	return success;
}

int ISTabulator::runTimeCourseSimulator(
			const std::string model,
			const std::string modelId,
			float tolerance,
			std::string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int nSpecies,
			int nNonConstSpecies,
			float** data2DArray,
			float** sMatrix){
	return 0;
}

int ISTabulator::runSplitTimeCourseSimulation(
				std::string* model,
				std::string modelId,
				bool isInitialRun,
				float tolerance,
				std::string* speciesId,
				double* speciesInitialValue,
				float stepSize,
				int nTimeSteps,
				int nSpecies,
				int nNonConstSpecies,
				std::vector <std::string> &metaData,
				float** data2DArray){
	return 0;
}
/*int ISTabulator::runTimeCourseSimulator(
			std::string serviceAddress,
			std::string *model,
			std::string modelId,
			float stepSize,
			int nTimeSteps,
			std::string* result){
	return 0;
}*/

/*int ISTabulator::runTimeCourseSimulator(
			std::string serviceAddress,
			std::string *model,
			std::string modelId,
			float tolerance,
			string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int sMatrixRow,
			int sMatrixCol,
			std::string* result){
	return 0;
}*/

int ISTabulator::runTimeCourseSimulator(
			std::string serviceAddress,
			std::string *model,
			std::string modelId,
			float tolerance,
			string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int sMatrixRow,
			int sMatrixCol,
			int nAssignmentSpecies,
			int nConstantSpecies,
			int nReactionSpecies,
			std::string* result){
	query++;
/*	cout<<"No of Query:  "<<query<<endl;
	cout<<"No of Retrieve:  "<<retrieve<<endl;
	cout<<"Table size:  "<<counter<<endl;*/

//	ISATSummaryfile<<query<<"\t"<<retrieve<<"\t"<<counter<<"\n";
	//myfile.open ("example.txt");
	//ISATSummaryfile.close();

	cout<<"From the sender "<<sMatrixRow<<"\t"<<sMatrixCol<<endl;

	cout<<"Initial value from the query sender"<<endl;

/*	for(int i = 0; i<sMatrixRow; i++){
		cout<<sInitialValue[i]<<"\t"<<speciesId[i]<<endl;
	}*/
	sMatrixCol = sMatrixRow-nAssignmentSpecies;
//	sMatrixRow = sMatrixRow-nConstantSpecies;
 ///TODO
	//calculate the scale diagonal matrix (vector in this case) for the error
		//nSpecies = nSpecies-3;
		double sDiagMat[sMatrixRow];
		for(int i=0; i<sMatrixRow; i++)sDiagMat[i] = sInitialValue[i]/maxFind(sInitialValue, sMatrixRow);

		//set the return value to -1
		int success=-1;
	/*	cout <<"My empty"<< mISTable.isEmpty()<<endl;
		cout <<"my Counter: " <<counter << endl;
		cout <<"Non Const Species: "<<sMatrixRow<<endl;*/

		int nL =(sMatrixRow*(sMatrixRow+1))/2;
		float **sMatrix;

		//check if table is empty
		if(mISTable.isEmpty()){
			double sMatrixF[sMatrixRow*sMatrixRow];
			sMatrix = new float *[sMatrixRow];
				for (int j=0; j<sMatrixRow; j++){
					sMatrix[j] = new float[sMatrixRow];
				}
			int simId = 1;
			//create a simulator factory
			SimulatorFactory *factory = new SimulatorFactory();

			//use the simulator id (simId) variable above, which is 1 in this case for direct copasiws to create the instance of simulator

			CopasiWSSimulator *sim = new CopasiWSSimulator();
		//	Simulator *sim = factory->getSimulatorInstance(simId);
			cout <<"=========Go Here ================";

			//run the simulator
		//	success = sim->runTimeCourseSimulator(model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0], &sMatrix[0]);

			//sensitivity matrix and result in string
			string resultData, stringMatrix;
			success = sim->runTimeCourseSimulator(serviceAddress, model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, sMatrixRow, sMatrixCol, nAssignmentSpecies,
										nConstantSpecies, nReactionSpecies, &resultData);
	////////		success = sim->runTimeCourseSimulator(serviceAddress, model, modelId, stepSize, nTimeSteps, &resultData, &stringMatrix);
			*result = resultData;
		///cout<<*result<<endl;  //TODO
		///	cout<<stringMatrix<<endl;  //TODO

			cout<<"==============================================Computation=================="<<endl;

			/*	float **sSMatrix; //ixRow][sMatrixCol];
			sSMatrix = new float *[sMatrixRow];
							for (int j=0; j<sMatrixRow; j++){
								sSMatrix[j] = new float[sMatrixCol];
							}*/

	////////////	convertString2Array(stringMatrix, &sMatrix[0], sMatrixRow, sMatrixRow); //sMatrixCol;

			// do matrix transpose and form the A PSM
			//float sSMatrixT[sMatrixCol][sMatrixRow];

	/*		float **sSMatrixT; //ixRow][sMatrixCol];
						sSMatrixT = new float *[sMatrixCol];
										for (int j=0; j<sMatrixCol; j++){
											sSMatrixT[j] = new float[sMatrixRow];
										}

				//float sSMatrixT[n][n];
					for (int i = 0; i < sMatrixRow; ++i)	{
						for (int j = i+1; j < sMatrixCol; ++j)	{
						//	sSMatrix[i][j]=1.0;
							sSMatrixT[j][i] = sSMatrix[i][j];
							cout<<sSMatrixT[j][i]<<"\t";
						}
						cout<<endl;
					}


					//multply matrix
				//	float sMatrixMT[n][n];
					double tempResult;
					for(int i=0;i<sMatrixRow;i++){
							for(int j=0;j<sMatrixRow;j++){
								tempResult =0.0;
								for(int k=0;k<sMatrixCol;k++){
								//	cout<<tempResult<<endl;
									tempResult+=(sSMatrix[i][k]*sSMatrixT[k][j]);
									}
								sMatrix[i][j] = tempResult;
							//	cout << sMatrix[i][j] <<"\t";
							}
						//	cout <<endl;
					}*/


			cout <<"==========Time course matrix data========"<<endl;

	///TODO			if(!success){
			///		cout<<*result<<endl; ///TODO
			/*		for(int i = 0; i< nTimeSteps+1; i++){
						for(int j = 0; j < sMatrixRow+1; j++){
							cout << data2DArray[i][j] << "\t";
						}
						cout << endl;
					}*/
		///TODO		}

			//copy the necessary gradient matrix data from actual sensitivity matrix
			if(!success){
				for(int i = 0; i< sMatrixRow; i++){
					for(int j = 0; j <sMatrixRow; j++){
				//		sMatrixF[i*sMatrixRow +j] = (double)(sDiagMat[i]*sMatrix[i][j]/tolerance); //TODO is it without if
					if(i==j){sMatrixF[i*sMatrixRow +j] = (double)(sDiagMat[i]/tolerance);}
						else{
							sMatrixF[i*sMatrixRow +j] = 0.0;
						}
					//	sMatrixF[i*sMatrixRow +j] = (double)(sMatrix[i][j]/tolerance);
					}
				}
			}
			double matrixL[nL];
			cout<<"\n===========Cholesky representation===========";
			cout <<nL<<endl;
			int n = sMatrixRow;
			ell_bbt2chol_(&n, &sMatrixF[0], matrixL);

			tree_node node;
			node.cholSMatrixL = new double[nL];
			node.cholSMatrixL = matrixL;

			for(int i = 0; i<nL; i++){
				node.cholSMatrixL[i] = matrixL[i];
			///	cout <<node.cholSMatrixL[i] <<endl; //TODO
			}

			//total no of matrix rows = number of time step require + 1 (1 for take care of the 0 step)
				int nRow = nTimeSteps+1;

				//copy time course data
				node.timeCourseResult = *result;
				//matrix memory allocation
			//	node.timeCourseData = new float *[nRow];
				int i = 0, j = 0;

			/*	for (i=0; i<nRow; i++){
					node.timeCourseData[i] = new float[sMatrixRow+1]; //add 1 to no of species to take care of time column
				}
				for(int i=0; i<nRow; i++)
					for(int j = 0; j<sMatrixRow+1; j++)
						node.timeCourseData[i][j] = data2DArray[i][j];*/

				node.sInitialValue = new double[sMatrixRow];
				for(int i = 0; i<sMatrixRow; i++)
					node.sInitialValue[i] = sInitialValue[i];

			node.nSpecies = sMatrixRow;
			node.nTimeSteps = nTimeSteps;

			//mISTable.insert(1, tolerance, &data2DArray[0], nTimeSteps, nSpecies);
			mISTable.insert(&node);
		//	delete sMatrix;

			counter++;
			//TODO
		}else{
			//get the data from the tree if the tree is not NULL
			tree_node *node; // = NULL;

			//	sInitialValue[0] = sInitialValue[0]+8;

			//checking a most frequently used table
	/*		int mfuChecker = -1;
		if (!mMFUISTable.isEmpty()) {
			node = mMFUISTable.search(sInitialValue);
			if(node!=NULL){
			cout<<"=======Result is from MFU table======="<<endl;
			cout<<"My size: "<<mMFUISTableCounter<<endl;
			mfuChecker = 1;
			}else{
				cout<<"=======Searched from the MFU table but No Result======="<<endl;
				cout<<"My current MFU table size: "<<mMFUISTableCounter<<endl;
			}
		}
		else{
			node = mISTable.search(sInitialValue);
		//	cout<<"=======Result is from Original main table======="<<endl;
		//	cout<<"My size: "<<mISTableCounter<<endl;
			if(node!=NULL)mfuChecker = 0;
		}

		//if nothing is found in the mfu table go the main table
		if(node==NULL){
			node = mISTable.search(sInitialValue);
			if(node!=NULL){
			cout<<"=======Result is from Original main table======="<<endl;
			cout<<"My size: "<<mISTableCounter<<endl;
			mfuChecker = 0;
			}else{
				cout<<"=======Searched from the main table but no Result======="<<endl;
				cout<<"My current main table size: "<<mISTableCounter<<endl;
			}
		}*/

			node = mISTable.search(sInitialValue);
			if(node!=NULL){
				retrieve++;
				cout <<"Retrieving from tree\n"; //TODO
		/**		for(int i = 0; i<sMatrixRow; i++){
					cout << node->sInitialValue[i]<<endl;
				}*/

			/*	for(int i = 0; i< nTimeSteps+1; i++){
					for(int j = 0; j <sMatrixRow+1; j++){
						data2DArray[i][j] = node->timeCourseData[i][j];
						cout << data2DArray[i][j] <<"\t";
					}
					cout <<endl;
				}*/

				//get time course result string from tree;
				*result = node->timeCourseResult;

		/////TODO		cout<<*result<<endl;
				//calculate the eigenvalues
				int nStore = node->nSpecies;
				double uMatrix[nStore*nStore];
				double sMatrixL[nStore][nStore];
				double sMatrixLT[nStore][nStore];
				double lam[nStore];
				ell_chol2eig_(&nStore, node->cholSMatrixL, &uMatrix[0], lam);

			//debugging
			/*	cout <<"Printing the eigenvalues"<<endl;
				for (int i=0; i<nStore; i++){
					cout<<"\n"<<lam[i];
				}*/
				/*for(int i = 0; i<(nStore*nStore); i++){
					cout<<uMatrix[i]<<endl;
				}*/

				for(int i=0; i<nStore; i++){
					for(int j=0; j<nStore; j++){
						sMatrixLT[i][j] = 0;
						sMatrixL[i][j] = 0;
					}
				}


				int k = 0;
				for(int i=0; i<nStore; i++){
					for(int j=i; j<nStore; j++){
						sMatrixL[j][i] = node->cholSMatrixL[k];
						sMatrixLT[j][i] = sMatrixL[j][i];
						k = k+1;
					////	cout <<sMatrixL[j][i] << "\t";
					}
				////	cout <<endl;
				}

				//multiplying to check for error
				double sValueDiff[nStore];
				double vCalculated[nStore];

				for(int i=0; i<nStore; i++){
					sValueDiff[i] = sInitialValue[i]-node->sInitialValue[i];
				}

				//calculating the error
				double error = 0.0;
				for(int i=0;i<nStore;i++){
					double value = 0;
					for(int k=0;k<nStore;k++){
						value=value+(sMatrixL[i][k]*sValueDiff[k]);
					}
					vCalculated[i]=value;
				}
				for(int i=0; i<nStore; i++){
					error = error + sValueDiff[i]*vCalculated[i];
				}
				cout<<"Error calculated: "<<error<<endl;

				for(int i=0;i<nStore;i++){
					double value = 0;
					for(int k=0;k<nStore;k++){
						value=value+(sMatrixL[i][k]*sValueDiff[k]);
					}
					vCalculated[i]=value*(node->sInitialValue[i]/maxFind(node->sInitialValue, nStore));
				}
				//for debugging
			/*	cout << "Checking the error vector"<< endl;
				for(int i=0; i<nStore; i++){
					cout <<vCalculated[i] <<endl;
				}
				cout <<"End of error vector checking" <<endl; */

				//approximate the time course value from the retrieve value
				//cout <<"New approximate value"<<endl;
				/*for(int i = 0; i< nTimeSteps+1; i++){
					for(int j = 0; j <sMatrixRow+1; j++){
						if(j>0){
							data2DArray[i][j] = data2DArray[i][j] + vCalculated[j-1];
							if(data2DArray[i][j]<0)data2DArray[i][j] =data2DArray[i][j]*-1;
						}
						cout << data2DArray[i][j] <<"\t";
					}
					cout <<endl;
				}*/

				//add record to mfu table if retrieve is successful
		/*		if(!mfuChecker){
					tree_node mfuNode;
					//copy result to the node
					mMFUISTableCounter++;
					mfuNode.timeCourseResult = node->timeCourseResult;

					mfuNode.sInitialValue = new double[node->nSpecies];

					//====for debugging new initial condition ====
					cout <<"=====My New initial value======"<<endl;
					mfuNode.cholSMatrixL = new double[nL];

					for(int i = 0; i<nL; i++){
						mfuNode.cholSMatrixL[i] = node->cholSMatrixL[i];
						//cout <<node.cholSMatrixL[i] <<endl;
					}
					mfuNode.nSpecies = node->nSpecies;
					mfuNode.nTimeSteps = node->nTimeSteps;
					for(int i=0; i<mfuNode.nSpecies; i++){
						mfuNode.sInitialValue[i] = node->sInitialValue[i];
						//	cout<<node.sInitialValue[i]<<endl;
					}

					mMFUISTable.insert(&mfuNode);
				}*/
			}else{
				double sMatrixF[sMatrixRow*sMatrixRow];
				sMatrix = new float *[sMatrixRow];
				for (int j=0; j<sMatrixRow; j++){
					sMatrix[j] = new float[sMatrixRow];
				}
				int simId = 1;
				//create a simulator factory
				SimulatorFactory *factory = new SimulatorFactory();

				CopasiWSSimulator *sim = new CopasiWSSimulator();
				//use the simulator id (simId) variable above, which is 1 in this case for direct copasiws to create the instance of simulator
				//Simulator *sim = factory->getSimulatorInstance(simId);

				//run the simulator
			//	success = sim->runTimeCourseSimulator(model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0], &sMatrix[0]);

				//sensitivity matrix in string
				string resultData, stringSMatrix;
				success = sim->runTimeCourseSimulator(serviceAddress, model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, sMatrixRow, sMatrixCol, nAssignmentSpecies,
													nConstantSpecies, nReactionSpecies, &resultData);
			///////	success = sim->runTimeCourseSimulator(serviceAddress, model, modelId, stepSize, nTimeSteps, &resultData, &stringSMatrix);
				*result = resultData;
		//////		convertString2Array(stringSMatrix, &sMatrix[0], sMatrixRow, sMatrixRow);

				//copy the necessary gradient matrix data from actual sensitivity matrix
				if(!success){
					for(int i = 0; i< sMatrixRow; i++){
						for(int j = 0; j <sMatrixRow; j++){
						//	sMatrixF[i*sMatrixRow +j] = (double)(sMatrix[i][j]/tolerance);
					//		sMatrixF[i*sMatrixRow +j] = (double)(sDiagMat[i]*sMatrix[i][j]/tolerance); //TODO ###
						if(i==j){sMatrixF[i*sMatrixRow +j] = (double)(sDiagMat[i]/tolerance);}
							else{
								sMatrixF[i*sMatrixRow +j] = 0.0;
							}
						}
					}
				}
				cout<<"\nSubsequent ===========Cholesky representation===========";
				double matrixL[nL];
				int n = sMatrixRow;
				ell_bbt2chol_(&n, &sMatrixF[0], matrixL);

				tree_node node;
				node.cholSMatrixL = new double[nL];

				for(int i = 0; i<nL; i++){
					node.cholSMatrixL[i] = matrixL[i];
					//cout <<node.cholSMatrixL[i] <<endl;
				}

				//total no of matrix rows = number of time step require + 1 (1 for take care of the 0 step)
				int nRow = nTimeSteps+1;

				//matrix memory allocation
			/*	node.timeCourseData = new float *[nRow];

				for (int i=0; i<nRow; i++){
					node.timeCourseData[i] = new float[sMatrixRow+1]; //add 1 to no of species to take care of time column
				}
				for(int i=0; i<nRow; i++)
					for(int j = 0; j<sMatrixRow+1; j++)
						node.timeCourseData[i][j] = data2DArray[i][j];*/

				//copy result to the node
				node.timeCourseResult = *result;

				node.sInitialValue = new double[sMatrixRow];

				//====for debugging new initial condition ====
				cout <<"My New initial value"<<endl;
				for(int i=0; i<sMatrixRow; i++){
					node.sInitialValue[i] = sInitialValue[i];
				//	cout<<node.sInitialValue[i]<<endl;
				}

				node.nSpecies = sMatrixRow;
				node.nTimeSteps = nTimeSteps;
				mISTableCounter++; //increment the size
				mISTable.insert(&node);
				counter++;
			}


			//mISTable.print_preorder();
		}

		//mISTable.print_inorder();
		return success;

}

int ISTabulator::runTimeCourseSimulator1(
			std::string serviceAddress,
			std::string *model,
			std::string modelId,
			float tolerance,
			string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int sMatrixRow,
			int sMatrixCol,
			std::string* result){

		return 0;
}

//TODO
/*void ISTabulator::convertString2BigArray(string dataTable, float **data2DArray, int nRow, int nCol) {

	int rowCounter = 0;
	int colCounter = 0;
	string line;
	stringstream in (stringstream::in | stringstream::out);
	in << dataTable;

	//print for debugging
	cout << dataTable << "\n";

	// remove the header information, may be needed in future
	getline(in,line);
	int i = 0, j=0;
	float in_float;

	float remainderVector [nRow-nCol];
	int remainderRow[nRow-nCol];

	//process the header information
	string *hdata;
		string hsdata;
		stringstream hss, hss1;
		hss << line;
		hss1 << line;

		//allocate memory for the header metadata and get the into it
		hdata = new string[nCol];
		for (j = 0; j <nCol && getline(hss1, hsdata, '|'); j++){
			hdata[j] = hsdata;
		}

		//add assignment row not computed
		int ri = 0;
	for (int k = 0; k < nRow; k++) {
		for (int p = 0; p < nCol; k++) {
			if (!speciesId[k].compare(hdata[p])) {
				remainderVector[ri] = speciesId[i];
				remainderRow[ri]= k;
				ri++;
			}
		}
	}

	//loop to get the data from the stream
	for (i = 0; i < nRow && getline(in, line, '\n'); i++) {
		string data;
		stringstream ss;
		ss << line;
		for (j = 0; j < nCol; j++) {
			if (!hdata[i].compare(speciesId[i])) {
				if (i == j) {
					data2DArray[i][j] = 1.0;
				} else
					data2DArray[i][j] = 0.0;

			} else {
				getline(ss, data, '|');
				stringstream in_ss;
				in_ss << data;
				in_ss >> in_float;
				data2DArray[i][j] = in_float;
				cout << data2DArray[i][j] << "\t";

			}
		}
		cout << endl;

	}*/

	/*	for(int i = 0; i< nRow; i++){
			for(int j = 0; j < nRow; j++){
				if(j>nCol){
					data2DArray[i][j] = 0.0;
				}
				cout<<data2DArray[i][j]<<"\t";

			}
			cout << endl;
		}*/
/*////	cout<<"=========================Matrix deduce information=============="<<endl;

}*/ ////

//TODO must check this will generate error
void ISTabulator::convertString2Array(string dataTable, float **data2DArray, int nRow, int nCol) {

	int rowCounter = 0;
	int colCounter = 0;
	string line;
	stringstream in (stringstream::in | stringstream::out);
	in << dataTable;

	//print for debugging
///TODO	cout << dataTable << "\n";

	// remove the header information, may be needed in future
	getline(in,line);
	int i = 0, j=0;
	float in_float;

	//loop to get the data from the stream
	for (i = 0; i < nRow && getline(in,line, '\n'); i++) {
		string data;
		stringstream ss;
		ss << line;
		for (j = 0; j < nCol && getline(ss, data, '|'); j++){
			stringstream in_ss;
			in_ss << data;
			in_ss >> in_float;
		/*	if(in_float==0){
				data2DArray[i][j] = 1.0;
			}else{*/
			data2DArray[i][j] = in_float;
		//	}
	///		cout<<data2DArray[i][j]<<"\t";

		}
	///	cout<<endl;

	}

	/*	for(int i = 0; i< nRow; i++){
			for(int j = 0; j < nRow; j++){
				if(j>nCol){
					data2DArray[i][j] = 0.0;
				}
				cout<<data2DArray[i][j]<<"\t";

			}
			cout << endl;
		}*/
	cout<<"=========================Matrix deduce information=============="<<endl;

}

int ISTabulator::runTimeCourseSimulator(){
	//TODO
	//	return 0;
}
//ISTabulator::ISTabulator(){ counter = 0;}
ISTabulator::~ISTabulator() {
//	ISATSummaryfile.close();
	// TODO Auto-generated destructor stub
}

