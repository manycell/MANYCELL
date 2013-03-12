/*
 * @file TestSimulator.cpp
 * @date 21 Jun 2010
 * @author: Joseph Olufemi Dada 
 *
 *<!---------------------------------------------------------------------------
 * Copyright 2010-2012 The University of Manchester.  
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online.
 *----------------------------------------------------------------------- -->
 */
#include <cstdlib>
#include <math.h>
#include "TestSimulator.h"
#include "ISTabulator.h"
#include <time.h>

extern "C" {
	// Get declaration for all ellipsoid fortran functions
#include "ellipsoid.h"
}
//#define B(i,j) b[j][i];

using namespace std;
TestSimulator::TestSimulator() {
	// TODO Auto-generated constructor stub
}

TestSimulator::~TestSimulator() {
	// TODO Auto-generated destructor stub

}

float maxFind(double *value, int n) {
	float max = 0;
	for (int i = 0; i < n; i++) {
		if (max < value[i])
			max = value[i];
	}
	return max;
}
int main( int argc, char* argv[])
{
	//start time to calculate execution time
	time_t start_time = time(NULL);
	int iteration;
	int whichSimulator;

	//get the number of iteration  and which simulator parameters.
	cout <<"I am hers====";

	iteration = atoi( argv[1] );
	whichSimulator = atoi(argv[2]);


	//which simulator to run, 1 to use copasiws client simulator directly, 2 for ISAT tabulator simulator
	//if ( argc == 3 )
	int simId = whichSimulator;

	//simulator parameters, this is just a test as the parameter values will be passed from the upper layer
	float stepSize = 1.0f;
	int nTimeSteps	=	10;
	int nSpecies = 0;
	int nNonConstSpecies = 0;

	// the SBML model and model id
	string modelId ("glycolysis");


	//load the sbml file
	ifstream in("BIOMD0000000245.xml");
	string SBMLModel, line;
	while(getline(in, line))
		SBMLModel += line + "\n";

	//specify the tolerance
	float tolerance = 0.002f;

	//variable to hold the species ids
	string speciesId[] = {"s_glu", "s_pyr", "s_acetate", "s_acetald", "s_EtOH", "x", "a", "AcDH"};
	string *sId = speciesId;
	nSpecies = 8;

	//variable to hold the initial values of species
	double speciesIValue[] = {15.0, 0.0, 0.0, 0.0, 0.0, 0.002, 0.0, 1.5e-05};
	double *sInitialValue = speciesIValue;

	//variable for simulation result usually a two dimensional matrix
	float **data2DArray;

	//total no of matrix rows = number of time step require + 1 (1 for take care of the 0 step)
	int nRow = nTimeSteps+1;

	//matrix memory allocation
	data2DArray = new float *[nRow];
	int i = 0, j = 0;

	for (i=0; i<nRow; i++){
		data2DArray[i] = new float[nSpecies+1]; //add 1 to no of species to take care of time column
	}

	//variable for sensitivity matrix if required
	float **sMatrix;
		//allocate memory to the matrix
		sMatrix = new float *[nSpecies];
		for (i=0; i<nSpecies; i++){
			sMatrix[i] = new float[nSpecies];
		}

	//create a simulator factory
	SimulatorFactory *factory = new SimulatorFactory();

	//use the simulator id (simId) variable above, which is 1 in this case for direct copasiws to create the instance of simulator
	Simulator *sim = factory->getSimulatorInstance(simId);

	//run the simulator
	if(simId==2){
	for(int i=0; i<iteration; i++){
		if(i==5 || i==10 || i==15 || i==20)
	sInitialValue[0] = sInitialValue[0] + 0.8;
		//if(i==2)  stepSize = 0.1;
	//	if(i==10) stepSize = 2.0;
		//if(i==15) stepSize = 0.5;
	int success = sim->runTimeCourseSimulator(SBMLModel, modelId, tolerance, sId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0]);

	}

	mISTable.print_preorder(); //print_inorder();
	}else if(simId==1){
		for(int i=0; i<iteration; i++){
			int success = sim->runTimeCourseSimulator(SBMLModel, modelId, tolerance, sId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0]);
		}
	}
	else{
		int success = sim->runTimeCourseSimulator(SBMLModel, modelId, tolerance, sId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0], &sMatrix[0]);

	//print out the array data for debugging and testing
	cout <<"Time course matrix data"<<endl;
	if(!success){
		for(i = 0; i< nRow; i++){
			for(j = 0; j < nSpecies+1; j++){
				cout << data2DArray[i][j] << "\t";
			}
			cout << endl;
		}
	}

	//print out the array sensitivity matrix data for debugging and testing
	cout <<endl <<"Sensitivity (mapping) matrix for approximating time course data"<<endl;
	if(!success){
		for(i = 0; i< nSpecies; i++){
			for(j = 0; j < nSpecies; j++){
				cout << sMatrix[i][j] << "\t";
			}
			cout << endl;
		}
	}

	//testing the ellipsoid
	double sValue[] = {15.0, 0.0, 0.0, 0.0, 0.0, 0.002, 0.0002, 1.5e-05};
	double sValue_p[] = {17.9, 0.0, 0.0, 0.0, 0.0, 0.002, 0.0002, 1.5e-05};
	double sValue_q[] = {16.5, 0.0, 0.0, 0.0, 0.0, 0.002, 0.0002, 1.5e-05};

	int n = 8;
	int nL =(n*(n+1))/2;

	float sMatrixf[n][n];
	double matrixL[nL];
	double matrixAL[nL];
	double uMatrix[n][n];
	double lam[n];

	cout << "Max Value: " <<maxFind(sValue, n) <<endl;
	// do matrix transpose for mat2
/*	float sMatrixT[n][n];
		for (int i = 0; i < n; ++i)	{
			for (int j = 0; j < n; ++j)	{
				sMatrixT[i][j] = sMatrix[j][i];
			}
		}

		//multply matrix
		float sMatrixMT[n][n];
		for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					for(int k=0;k<n;k++){
						sMatrixMT[i][j]=sMatrixMT[i][j]+(sMatrixT[j][k]*sMatrix[k][j]);
						}
					cout << sMatrixMT[i][j] <<"\t";
				}
				cout <<endl;
		}*/
	//multiplying to check for error
	double sValueDiff[n];
	double vCalculated[n];
	for(int i=0; i<n; i++){
		sValueDiff[i] = sValue[i]-sValue_p[i];
	}

	for(int i=0;i<n;i++){
		double value = 0;
		for(int k=0;k<n;k++){
			value=value+(sMatrixf[i][k]*sValueDiff[k]);
		}
		vCalculated[i]=value*(sValue[i]/maxFind(sValue, n));
	}

	cout << "Error vector"<< endl;
	for(int i=0; i<n; i++){
		//if(vCalculated[i] <0 alculated[i]!=0)vCalculated[i] = -1*vCalculated[i];
		cout << fabs(vCalculated[i]) <<endl;
	}
   cout <<"End of error vector" <<endl;

	/*float error =0.0;
	for(int i=0; i<n; i++){
		error = error + sValueDiff[i]*vCalculated[i];
	}
	cout <<"approximate error = " << error <<endl;*/

	//copy the neccessary gradient matrix data from actual sensitivity matrix
   double sMatrixFort[n*n];

	if(!success){
		for(int i = 0; i< n; i++){
			for(int j = 0; j <n; j++){
				sMatrixFort[i*n +j] = (double)sMatrix[i][j]; //*(sValue[i]/maxFind(sValue, n));
				cout <<sMatrixFort[i*n+j];
				//sMatrixf[i][j] =  0.0;
				//sMatrixf[i][j] = sMatrix[i-1][j-1];
			//	float save = sMatrixf[i][j];
				//	sMatrixf[i][j] = sMatrixf[j][i];
				//	sMatrixf[j][i] = save;
		//		sMatrixf[i][j] = sMatrixf[i][j]*(sValue[i]/maxFind(sValue, n));
		//		cout << sMatrixf[i][j] <<"\t";
			}
		//	cout <<endl;

		}
	}

	/*for(int i = 1; i< n; i++){
				for(int j = 0; j<n; j++){
					cout << sMatrixf[i][j] <<"\t";
				}
				cout <<endl;

			}*/
	/*int info = -1;
	int k_ell = 1;
	ell_pt_shrink_(&n,sValue, matrixL, sValue_p, &k_ell, &info);

	cout << "Shrinking: " << info <<endl;
*/
/*	ell_full2eig_(&n, sMatrixf[0], uMatrix[0],lam);
	//eigen printing
	cout <<"\nStart of eigenvalue";
//	ell_bbt2eig_(&n, sMatrixf[0],uMatrix[0], lam);
	for (i=0; i<n; i++){
		cout<<"\n"<<lam[i];
	}
	cout <<"\nEnd of eigenvalue\n" << endl;

	for(i = 0; i< n; i++){
		for(j = 0; j < n; j++){
			cout <<uMatrix[i][j] << "\t";
		}
		cout << endl;
	}*/

	//n=2;
	//Cholesky representation and printing
	cout<<"\nCholesky representation\n"<<endl;
	ell_bbt2chol_(&n, &sMatrixFort[0],matrixL);
//	B(2,4);
	//ell_bbt2chol_(&n, B,matrixL);
//	ell_full2low_(&n, sMatrixf[0],matrixAL);
//	ell_low2chol_(&n, matrixAL, matrixL);
	for (int i=0; i<nL; i++){
		//matrixL[i] = matrixL[i]/tolerance;
		cout<<"\n"<<matrixL[i];
	}
	cout<<"\n";

	n=8;
	cout << "Eigenvalues through chol";
	ell_chol2eig_(&n, matrixL, uMatrix[0], lam);
	for (int i=0; i<n; i++){
	//	lam[i] = fabs(lam[i]);
		cout<<"\n"<<lam[i];
	}
	n=8;
	cout <<"\nMaximum singular value: " <<maxFind(lam, n);
	cout <<"\nEigenvalues end" << endl;
/*
	n=8;
	for(i = 0; i< n; i++){
		for(j = 0; j < n; j++){
			cout <<uMatrix[i][j] << "\t";
		}
		cout << endl;
	}
	//calculate the radii
	n=8;
	float r_in =0.0;
	float r_out =0.0;
	ell_rad_lower_(&n, matrixL, &r_in); //, &r_out);

	n=8;
	ell_rad_upper_(&n, matrixL, &r_out); //, &r_out);
	cout <<"\nRadius Inside:	"<< r_in <<"\nOutside Radius: "<<r_out <<endl;
	r_in = 0.0;
	r_out = 0.0;

	n=8;
	//piont p in ellipsoid of c
	//distance point to ellipsoid
	float s;
	ell_pt_dist_(&n, sValue, matrixL, sValue_p, &s);
	cout <<"\nS distance: "<<s << endl;
	s = 0;*/

	n=8;
	bool inn = false;
	ell_pt_in_(&n, sValue, matrixL, sValue_p, &inn);
	cout << "Output: " << inn <<endl;

	//Calculate hyper plane
	n=8;
	double xh[n];
	double v[n];
	ell_pt_hyper_(&n, sValue, matrixL, sValue_p, xh, v);
	float sdist1 = 0.0, sdist2=0.0, sdist3=0.0;

	for(int i=0; i<n; i++){
				cout <<"xh:   "<<xh[i] <<" v: "<<v[i]<<endl;
				sdist2 = sdist2 + v[i]*(sValue_p[i]-xh[i]);
				sdist1 = sdist1 + v[i]*(sValue[i]-xh[i]);
				sdist3 = sdist3 + v[i]*(sValue_q[i]-xh[i]);
			}
	cout <<"Distance for 1: " <<sdist1 <<" Distance for 2: " <<sdist2 <<" Distance for 3: " << sdist3<<endl;
	n=8;
	for(int i=0; i<n; i++){
		cout <<"xh:   "<<xh[i] <<" v: "<<v[i]<<endl;
	}
	cout <<"\nEnd of hyper\n";

	//testing ell_pt_near_far#
	int knf = 1;
	double xnf[n];
	ell_pt_near_far_(&knf, &n, sValue, matrixL, sValue_p, xnf);
	for(int i=0; i<n;i++){
		cout <<"ell_pt_far_near:	" <<xnf[i] <<endl;
	}
	n=8;
	double qual = 0.003;
	int max_it = 1;
	bool intersect = false;
	ell_pair_separate_(&n, sValue, matrixL, sValue_p, matrixL, &qual, &max_it, xh, v, &intersect);

	for(int i=0; i<n; i++){
			cout <<"xh:   "<<xh[i] <<" v: "<<v[i]<<endl;
			sdist2 = sdist2 + v[i]*(sValue_p[i]-xh[i]);
			sdist1 = sdist1 + v[i]*(sValue[i]-xh[i]);
			sdist3 = sdist3 + v[i]*(sValue_q[i]-xh[i]);
		}

	cout <<"Intersect: "<<intersect <<endl;
	cout <<"Distance for 1: " <<sdist1 <<" Distance for 2: " <<sdist2 <<" Distance for 3: " << sdist3<<endl;
	//clean up the mess to free the system resource
	//delete the data array pointer
//	for ( int r = 0; r < nRow; ++r ){
//		delete [] data2DArray[r];
//	}

	/*for ( int r = 0; r < nSpecies; ++r ){
		delete [] sMatrix[r];
	}*/
	//delete []sMatrix;
	//	delete []data2DArray;
	//	data2DArray=NULL;
	}
	//delete the simulator and the factory objects
	sim->~Simulator();
	factory->~SimulatorFactory();

	//end time to calculate execution time
	time_t end_time = time(NULL);
	cout <<"start: " <<start_time <<" End time "<<end_time <<endl;
	double total_time=difftime(end_time,start_time);
	cout <<"Total time in seconds: " <<total_time <<endl;

	//return 0 for success
	return 0;
}

