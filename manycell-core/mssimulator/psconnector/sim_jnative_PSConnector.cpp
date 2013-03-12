/*
 * @file sim_jnative_PSConnector.c
 * @date 30 Apr 2010
 * @author: Joseph Olufemi Dada 
 *
 *<!---------------------------------------------------------------------------
 * This file is part of multiscale simulation software.
 *
 * Copyright 2010-2012 The University of Manchester.  
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation.  A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as ....
 *----------------------------------------------------------------------- -->
 */
#include <jni.h>
#include <SimulatorFactory.h>
#include <Factory.h>
#include <Simulator.h>
#include <sim_jnative_PSConnector.h>
#include <string.h>
#include <vector>
#include <iostream>
#include <fstream>


//#include <sbml/SBMLDocument.h>
//#include <sbml/SBMLReader.h>
//#include <sbml/Model.h>

using namespace std;

JNIEXPORT jlong JNICALL Java_sim_jnative_PSConnector_initSimulator(JNIEnv * env, jobject jobj, jint simId){

	SimulatorFactory * factory = new SimulatorFactory();
	Simulator *sim = factory->getSimulatorInstance(simId);
	return ((jlong)sim);
}

JNIEXPORT jobjectArray JNICALL Java_sim_jnative_PSConnector_runTCSimulatior(
		JNIEnv * env,
		jobject jobj,
		jstring webServiceAddress,
		jstring SBMLModel,
		jstring modelId,
		jfloat tolerance,
		jobjectArray speciesId,
		jdoubleArray sInitialValue,
		jfloat stepSize,
		jint nTimeSteps,
		jint sMatrixRow,
		jint sMatrixCol,
		jint nAssignmentSpecies,
		jint nConstantSpecies,
		jint nReactionSpecies,
		jlong ptr)
{
	//the address of service to run
//	const char *servAdd = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	const char *servAdd = env->GetStringUTFChars(webServiceAddress, 0);
	//the instance method
	jclass cls = env->GetObjectClass(jobj);
		jstring jstr;
		jfieldID fid;   // store the field ID

		 // Look for the instance field s in cls
		     fid = env->GetFieldID(cls, "uModel", "Ljava/lang/String;");
		     if (fid == NULL) {
		         return NULL; // failed to find the field
		     }

		jmethodID midM = env->GetMethodID(cls, "setuModel",
				"(Ljava/lang/String;)V");
		if (midM == NULL) {
			return NULL; // method not found
		}

		jmethodID midR = env->GetMethodID(cls, "setSimResult",
						"(Ljava/lang/String;)V");
				if (midR == NULL) {
					return NULL; // method not found
				}
	/*	//second instance method
		jint isRun;
		jmethodID midIsRun = env->GetMethodID(cls, "getIsInitialRun", "()I");
		isRun = env->CallIntMethod(jobj, midIsRun);
		//bool isInitialRun = jbool;
		cout<<"Bool"<<isRun<<endl;*/


		//cast the pointer to simulator class
		Simulator *sim = (Simulator*)(ptr);

		//allocate memory
		int nRow = nTimeSteps+1;
		jobjectArray result;
		int i=0, j=0;

		//allocate memory and copy the species initial values
		double *sIValueArray;
		sIValueArray = env->GetDoubleArrayElements(sInitialValue, NULL);
		if (sIValueArray == NULL) {
			return 0; // exception occurred
		}

		//copy the SBML model and model id
		const char *model = env->GetStringUTFChars(SBMLModel, 0);
	//	string model = env->GetStringUTFChars(SBMLModel, 0);
		const char *mId = env->GetStringUTFChars(modelId, 0);

		//allocate memory for species id and copy the ids from jobjectarray
		string *sId = new string[sMatrixRow];
		for(i=0;i<sMatrixRow;i++) {
			jstring elem = (jstring)env->GetObjectArrayElement(speciesId, i);
			string id = env->GetStringUTFChars(elem, 0);
			sId[i] = id;
		}

	/*	float **data2DArray;

		//matrix memory allocation
		data2DArray = new float *[nRow];
		for (i=0; i<nRow; i++){
		//	data2DArray[i] = new float[nNonConstSpecies];
			data2DArray[i] = new float[nSpecies];
		}*/

		//testing the simple run

		//copy char to string
		string uModel(model);
		string resultData;
		string serviceAddress(servAdd);
		//cout<<uModel;
		int success = sim->runTimeCourseSimulator(serviceAddress, &uModel, mId, tolerance, sId, sIValueArray, stepSize, nTimeSteps, sMatrixRow, sMatrixCol, nAssignmentSpecies,
				nConstantSpecies, nReactionSpecies, &resultData);
		//	cout<<"I am Here after==========";
		//cout<<uModel;


		//copy and convert the result in string format to char* and send back the result data
		char *uResult = new char[resultData.length()+1];
		strcpy(uResult,resultData.c_str());

		jstr = (jstring)env->NewStringUTF((const char*)uResult);
		env->CallVoidMethod(jobj, midR, jstr);

		//copy and convert the updated model in string format to char* and send back the model
		char *uRModel = new char[uModel.length()+1];
		strcpy(uRModel,uModel.c_str());

		jstr = (jstring)env->NewStringUTF((const char*)uRModel);
		env->CallVoidMethod(jobj, midM, jstr);


		//cout <<model;
		//int success = sim->runTimeCourseSimulator(model, mId, tolerance, sId, sIValueArray, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, &data2DArray[0]);

		//copy char to string
	/*	string uModel(model);

	//	cout<<uModel;
		bool isInitialRun;
		if(isRun==0){
		isInitialRun = true;
		}else{isInitialRun = false;

		}

		//cout<<uModel<<endl;
		vector <string> metaData;
		int success = sim->runSplitTimeCourseSimulation(&uModel, mId, isInitialRun, tolerance, sId, sIValueArray, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, metaData,
				&data2DArray[0]);

		cout<<metaData.size()<<endl;
		for(int i=0; i<metaData.size();i++){
		cout<<metaData[i]<<endl;
		}

		//copy and convert the updated model in string format to char* and send back the model
		char *cuModel = new char[uModel.length()+1];
		strcpy(cuModel,uModel.c_str());

		jstr = (jstring)env->NewStringUTF((const char*)cuModel);


	   //  if (jstr == NULL) {
	   //      return NULL; // out of memory
	  //   }
	  //   env->SetObjectField(jobj, fid, jstr);
	//	cout<<"=============begin"<<cuModel<<"=====================end"<<endl;
	//	cout<<env->GetStringUTFChars(jstr, 0);
		//char otherString[40];
		//jstring newString = env->NewStringUTF((const char*)otherString);

		env->CallVoidMethod(jobj, mid, jstr);

*/
		//clean the mess
	//	delete[]cuModel;
		//find the float array class
	/*	jclass floatArrCls = env->FindClass("[F");
		if (floatArrCls == NULL) {
			return NULL; //exception thrown
		}
		result = env->NewObjectArray(nRow, floatArrCls, NULL);
		if (result == NULL) {
			return NULL; // out of memory error thrown /
		}

		for (i = 0; i < nRow; i++) {
		//	jfloat tmp [nNonConstSpecies];
		//	jfloatArray arr = env->NewFloatArray(nNonConstSpecies);
			jfloat tmp [nSpecies];
			jfloatArray arr = env->NewFloatArray(nSpecies);
			if (arr == NULL) {
				return NULL; // out of memory error thrown
			}
			for (j = 0; j < nSpecies; j++) {
				tmp[j] = (jfloat)data2DArray[i][j];
			}
			env->SetFloatArrayRegion(arr, 0, nSpecies, tmp);

			env->SetObjectArrayElement(result, i, arr);

			env->DeleteLocalRef(arr);
		}*/
		//clean up the mess
		delete []sId;
		delete []uResult;
		delete []uRModel;
	/*	for(i=0; i < nRow; i++)
		{
		  delete [] data2DArray[i];
		}

		delete [] data2DArray;*/
		env->ReleaseDoubleArrayElements(sInitialValue, sIValueArray, 0);
		env->ReleaseStringUTFChars(SBMLModel, model);
		env->ReleaseStringUTFChars(modelId, mId);
		env->ReleaseStringUTFChars(webServiceAddress, servAdd);
		delete sim;
		return NULL;
	//	return result;
}
/*
#include <stdio.h>
 2 #include <postgresql/libpq-fe.h>
 3 #include <string>
 4
 5 int     main() {
 6 PGconn          *conn;
 7 PGresult        *res;
 8 int             rec_count;
 9 int             row;
10 int             col;
11
12
13
14         conn = PQconnectdb("dbname=ljdata host=localhost user=dataman password=supersecret");
15
16         if (PQstatus(conn) == CONNECTION_BAD) {
17                 puts("We were unable to connect to the database");
18                 exit(0);
19         }
20
21         res = PQexec(conn,
22                 "update people set phonenumber=\'5055559999\' where id=3");
23
24         res = PQexec(conn,
25                 "select lastname,firstname,phonenumber from people order by id");
26
27         if (PQresultStatus(res) != PGRES_TUPLES_OK) {
28                 puts("We did not get any data!");
29                 exit(0);
30         }
31
32         rec_count = PQntuples(res);
33
34         printf("We received %d records.\n", rec_count);
35         puts("==========================");
36
37         for (row=0; row<rec_count; row++) {
38                 for (col=0; col<3; col++) {
39                         printf("%s\t", PQgetvalue(res, row, col));
40                 }
41                 puts("");
42         }
43
44         puts("==========================");
45
46         PQclear(res);
47
48         PQfinish(conn);
49
50         return 0;
51 }
g++ -lpq db.cpp -o db

*/


 

