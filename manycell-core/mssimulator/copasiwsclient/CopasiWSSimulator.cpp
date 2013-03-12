/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/mssimulator/copasiwsclient/CopasiWSSimulator.cpp,v $
 *  $Revision: 1.7 $
 *  $Name:  $
 *  $Author: jdada $
 *  $Date: 2010/08/18 12:50:27 $
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
#include "iostream"
#include "fstream"
#include "string"
#include "CopasiWSSimulator.h"

//========
#include <vector>
#include <sstream>
#include <cmath>
#include <cstdlib>
//========

//#include <sbml/SBMLDocument.h>
//#include <sbml/SBMLReader.h>
//#include <sbml/Model.h>

using namespace std;

#include "copasiws/soapbindingProxy.h" // get proxy
#include "copasiws/binding.nsmap" // get namespace bindings

using namespace std;
CopasiWSSimulator::CopasiWSSimulator() {
	// TODO Auto-generated constructor stub

}
int CopasiWSSimulator::runTimeCourseSimulator(
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
	//prepare the parameters for the service
	//const char * = "http://www.comp-sys-bio.org/CopasiWS/services/TimeCourseService";
	const char * serviceAddress = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	binding bind;
	ns2__DeterministicParameters *param  = new ns2__DeterministicParameters();
	_ns2__RunDeterministicSimulator *runReqDoc = new _ns2__RunDeterministicSimulator();
	_ns2__RunDeterministicSimulatorResponse *runResDoc = new _ns2__RunDeterministicSimulatorResponse();

	//parameters for sensitivity matrix
	ns2__SensitivityParameters *sParam = new ns2__SensitivityParameters();
	sParam->deltaFactor = 0.001;
	sParam->deltaMinimum = 1e-12;
	sParam->function = ns2__Function__Non_Constant_x0020Concentration_x0020of_x0020Species;
	sParam->variable = ns2__Variable__Initial_x0020Concentration;

	//print out the model for debuging only
	//cout << model;
	//set the parameters
	float duration = nTimeSteps/stepSize;
	param->StepSize = stepSize;
	param->Duration = duration;
	//param->OutputStartTime = 10.0f;
	stringstream nSteps;
	nSteps << nTimeSteps;
	string stepNumber = nSteps.str();
	param->StepNumber = stepNumber;
	param->__union_DeterministicParameters = 2;
	param->union_DeterministicParameters.sbml = &model;
	param->outputFormat = _ns2__DeterministicParameters_outputFormat__text;
	param->updatedModelRequired = false;
	param->sensitivityParameters = sParam;
	runReqDoc->parameters = param;

	//set the address of the service if different from default
	bind.endpoint = serviceAddress;
	if(bind.__ns1__runDeterministicSimulator(runReqDoc, runResDoc)==SOAP_OK)
		cout<<SOAP_SVR_FAULT<<endl;
	else
		soap_print_fault(bind.soap, stderr);
	soap_print_fault_location(bind.soap, stderr);

	//the data table from the simulator
	string dataTable = runResDoc->outputResult->result;
	string sMatrix = runResDoc->sensitityResult;
	//	string* updatedModel = runResDoc->updatedModel;
	//	cout<<*updatedModel<<endl;
	cout <<"My result" << sMatrix << "End of result";

	//print for debugging, must be removed
	//cout << dataTable << "\n";

	//convert the simulation data table into a 2 dimesional array
	stringstream ss;
	ss << param->StepNumber;
	int is_int;
	ss >> is_int;

	int nRow = is_int+1;

	stringstream s_in (stringstream::in | stringstream::out);
	s_in << dataTable;

	// remove the header information (metadata), may be needed in future
	string line;
	getline(s_in,line);

	string *hdata;
	string hsdata;
	stringstream hss, hss1;
	hss << line;
	hss1 << line;

	//count the number of column data
	int colCounter =0;
	while (getline(hss, hsdata, '|')){
		colCounter++;
	}

	//allocate memory for the header metadata and get the into it
	hdata = new string[colCounter];
	int i = 0, j=0;
	for (j = 0; j <colCounter && getline(hss1, hsdata, '|'); j++){
		hdata[j] = hsdata;
	}

	float in_float;

	//loop to get the data from the stream
	string linel;
	for (i = 0; i < nRow && getline(s_in,linel, '\n'); i++) {
		string data;
		stringstream ss;
		ss << linel;

		//get the first column data only and assign it to the result array
		getline(ss, data, '|');
		stringstream in_ss;
		in_ss << data;
		in_ss >> in_float;
		data2DArray[i][0] = in_float;

		//get the remaining column data
		for(j = 0; j < colCounter; j++){
			getline(ss, data, '|');
			stringstream in_ss;
			in_ss << data;
			in_ss >> in_float;

			//check to make sure the data sent back to requester correspond to the species id received
			for(int k = 0; k <nSpecies; k++){
				if(hdata[j].compare(speciesId[k])){
					data2DArray[i][j+1] = in_float;
				}
			}
			//print for debugging
			//cout << data2DArray[i][j] << "\t";
		}
		//cout << endl;
	}

	//clean up the mess
	delete param;
	delete runReqDoc;
	delete runResDoc;
	return 0;
}

int CopasiWSSimulator::runTimeCourseSimulator(
		std::string model,
		std::string modelId,
		float tolerance,
		string* speciesId,
		double* sInitialValue,
		float stepSize,
		int nTimeSteps,
		int nSpecies,
		int nNonConstSpecies,
		float** data2DArray,
		float** sMatrix)
{
	//prepare the parameters for the service
	//const char * = "http://www.comp-sys-bio.org/CopasiWS/services/TimeCourseService";
	const char * serviceAddress = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	binding bind;
	ns2__DeterministicParameters *param  = new ns2__DeterministicParameters();
	_ns2__RunDeterministicSimulator *runReqDoc = new _ns2__RunDeterministicSimulator();
	_ns2__RunDeterministicSimulatorResponse *runResDoc = new _ns2__RunDeterministicSimulatorResponse();

	//parameters for sensitivity matrix
	ns2__SensitivityParameters *sParam = new ns2__SensitivityParameters();
	sParam->deltaFactor = 0.001;
	sParam->deltaMinimum = 1e-12;
	sParam->function = ns2__Function__Non_Constant_x0020Concentration_x0020of_x0020Species;
	sParam->variable = ns2__Variable__Initial_x0020Concentration;

	//print out the model for debuging only
	//cout << model;
	//set the parameters
	float duration = nTimeSteps/stepSize;
	param->StepSize = stepSize;
	param->Duration = duration;
	//param->OutputStartTime = 10.0f;
	stringstream nSteps;
	nSteps << nTimeSteps;
	string stepNumber = nSteps.str();
	param->StepNumber = stepNumber;
	param->__union_DeterministicParameters = 2;
	param->union_DeterministicParameters.sbml = &model;
	param->outputFormat = _ns2__DeterministicParameters_outputFormat__text;
	param->sensitivityParameters = sParam;
	param->updatedModelRequired = true;
	runReqDoc->parameters = param;

	//set the address of the service if different from default
	bind.endpoint = serviceAddress;
	if(bind.__ns1__runDeterministicSimulator(runReqDoc, runResDoc)==SOAP_OK)
		cout<<SOAP_SVR_FAULT<<endl;
	else
		soap_print_fault(bind.soap, stderr);
	soap_print_fault_location(bind.soap, stderr);

	//the data table from the simulator
	string dataTable = runResDoc->outputResult->result;
	string sMatrixTable = runResDoc->sensitityResult;

	cout <<"My result  here"<<endl;
	string* updatedModel = runResDoc->updatedModel;
	cout<<updatedModel<<endl;
	//	cout <<"My result" << sMatrix << "End of result";

	//print for debugging, must be removed
	cout << dataTable << "\n";

	//convert the simulation data table into a 2 dimesional array
	stringstream ss;
	ss << param->StepNumber;
	int is_int;
	ss >> is_int;

	int nRow = is_int+1;

	stringstream s_in (stringstream::in | stringstream::out);
	s_in << dataTable;

	//s_in << sMatrix;

	// remove the header information (metadata), may be needed in future
	string line;
	getline(s_in,line);

	string *hdata;
	string hsdata;
	stringstream hss, hss1;
	hss << line;
	hss1 << line;

	//count the number of column data
	int colCounter =0;
	while (getline(hss, hsdata, '|')){
		colCounter++;
	}

	//allocate memory for the header metadata and get the into it
	hdata = new string[colCounter];
	int i = 0, j=0;
	for (j = 0; j <colCounter && getline(hss1, hsdata, '|'); j++){
		hdata[j] = hsdata;
	}

	float in_float;

	//loop to get the data from the stream
	string linel;
	for (i = 0; i < nRow && getline(s_in,linel, '\n'); i++) {
		string data;
		stringstream ss;
		ss << linel;

		//get the first column data only and assign it to the result array
		getline(ss, data, '|');
		stringstream in_ss;
		in_ss << data;
		in_ss >> in_float;
		data2DArray[i][0] = in_float;

		//get the remaining column data
		for(j = 0; j < colCounter; j++){
			getline(ss, data, '|');
			stringstream in_ss;
			in_ss << data;
			in_ss >> in_float;

			//check to make sure the data sent back to requester correspond to the species id received
			for(int k = 0; k <nNonConstSpecies; k++){
				if(hdata[j].compare(speciesId[k])){
					data2DArray[i][j+1] = in_float;
				}
			}
			//print for debugging
			cout << data2DArray[i][j] << "\t";
		}
		cout << endl;
	}

	stringstream ss_in (stringstream::in | stringstream::out);
	cout << sMatrixTable;
	ss_in << sMatrixTable;

	// remove the header information (metadata), may be needed in future
	string sLine;
	getline(ss_in,sLine);

	string *shdata;
	string shsdata;
	stringstream shss, shss1;
	shss << sLine;
	shss1 << sLine;

	//count the number of column data
	int sColCounter =0;
	while (getline(shss, shsdata, '|')){
		sColCounter++;
	}

	//	cout <<"Total Column: " <<sColCounter;
	//allocate memory for the header metadata and get the into it
	shdata = new string[sColCounter];
	//	int i = 0, j=0;
	for (j = 0; j <sColCounter && getline(shss1, shsdata, '|'); j++){
		shdata[j] = shsdata;
	}

	//loop to get the data from the stream
	string sLinel;
	int cont=0;
	for (i = 0; i < sColCounter && getline(ss_in,sLinel, '\n'); i++) {
		cont++;
		string data;
		stringstream ss;
		ss << sLinel;

		//get the column data
		for(j = 0; j < sColCounter; j++){
			getline(ss, data, '|');
			stringstream in_ss;
			in_ss << data;
			in_ss >> in_float;

			//check to make sure the data sent back to requester correspond to the species id received
			for(int k = 0; k <sColCounter; k++){
				if(shdata[j].compare(speciesId[k])){
					sMatrix[i][j] = in_float;
				}
			}
		}
	}

	//clean up the mess
	delete	sParam;
	delete param;
	delete runReqDoc;
	delete runResDoc;
	return 0;
}
int CopasiWSSimulator::runSplitTimeCourseSimulation(
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
	//prepare the parameters for the service
	//const char * = "http://www.comp-sys-bio.org/CopasiWS/services/TimeCourseService";
	const char * serviceAddress = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	binding bind;
	ns2__DeterministicParameters *param  = new ns2__DeterministicParameters();
	_ns2__RunDeterministicSimulator *runReqDoc = new _ns2__RunDeterministicSimulator();
	_ns2__RunDeterministicSimulatorResponse *runResDoc = new _ns2__RunDeterministicSimulatorResponse();

	//parameters for sensitivity matrix
	ns2__SensitivityParameters *sParam = new ns2__SensitivityParameters();
	sParam->deltaFactor = 0.001;
	sParam->deltaMinimum = 1e-12;
	sParam->function = ns2__Function__Non_Constant_x0020Concentration_x0020of_x0020Species;
	sParam->variable = ns2__Variable__Initial_x0020Concentration;

	//print out the model for debuging only
	//cout << model;
	//set the parameters
	float duration = nTimeSteps/stepSize;
	param->StepSize = stepSize;
	param->Duration = duration;
	//param->OutputStartTime = 10.0f;
	stringstream nSteps;
	nSteps << nTimeSteps;
	string stepNumber = nSteps.str();
	param->StepNumber = stepNumber;
	if(isInitialRun){
		param->__union_DeterministicParameters = 2;
		param->union_DeterministicParameters.sbml = model;
	}else{
		param->__union_DeterministicParameters = 1;
		param->union_DeterministicParameters.copasiml = model;
		param->inputFormat = _ns2__DeterministicParameters_inputFormat__CopasiML;
		//	cout<<*model;
	}
	param->outputFormat = _ns2__DeterministicParameters_outputFormat__text;
	param->updatedModelRequired = true;
	param->sensitivityParameters = sParam;
	runReqDoc->parameters = param;

	//set the address of the service if different from default
	bind.endpoint = serviceAddress;
	if(bind.__ns1__runDeterministicSimulator(runReqDoc, runResDoc)==SOAP_OK)
		cout<<SOAP_SVR_FAULT<<endl;
	else
		soap_print_fault(bind.soap, stderr);
	soap_print_fault_location(bind.soap, stderr);

	//the data table from the simulator
	string dataTable = runResDoc->outputResult->result;
	string sMatrix = runResDoc->sensitityResult;
	string* updatedModel = runResDoc->updatedModel;
	//model = runResDoc->updatedModel;
	*model = *updatedModel;

	//	cout<<*model<<"My End"<<endl;
	//cout <<"My result" << sMatrix << "End of result";

	//print for debugging, must be removed
	//cout << dataTable << "\n";

	//convert the simulation data table into a 2 dimesional array
	stringstream ss;
	ss << param->StepNumber;
	int is_int;
	ss >> is_int;

	int nRow = is_int+1;

	stringstream s_in (stringstream::in | stringstream::out);
	s_in << dataTable;

	// remove the header information (metadata), may be needed in future
	string line;
	getline(s_in,line);

	string *hdata;
	string hsdata;
	stringstream hss, hss1;
	hss << line;
	hss1 << line;

	//count the number of column data
	int colCounter =0;
	while (getline(hss, hsdata, '|')){
		colCounter++;
	}

	//allocate memory for the header metadata and get the into it
	//	std::vector <std::string> metaData;
	hdata = new string[colCounter];
	int i = 0, j=0;
	for (j = 0; j <colCounter && getline(hss1, hsdata, '|'); j++){
		metaData.push_back(hsdata);
		hdata[j] = hsdata;
	}

	float in_float;

	//loop to get the data from the stream
	string linel;
	for (i = 0; i < nRow && getline(s_in,linel, '\n'); i++) {
		string data;
		stringstream ss;
		ss << linel;

		//get the first column data only and assign it to the result array
		getline(ss, data, '|');
		stringstream in_ss;
		in_ss << data;
		in_ss >> in_float;
		data2DArray[i][0] = in_float;

		//get the remaining column data
		for(j = 0; j < colCounter; j++){
			getline(ss, data, '|');
			stringstream in_ss;
			in_ss << data;
			in_ss >> in_float;

			//check to make sure the data sent back to requester correspond to the species id received
			//for(int k = 0; k <nSpecies; k++){
			for(int k = 0; k <metaData.size(); k++){
				//		if(hdata[j].compare(speciesId[k])){
				data2DArray[i][j+1] = in_float;
				//	}
			}
			//print for debugging
			//cout << data2DArray[i][j] << "\t";
		}
		//	cout << endl;
	}

	//clean up the mess
	delete param;
	delete runReqDoc;
	delete runResDoc;
	return 0;

}

int CopasiWSSimulator::runTimeCourseSimulator(
		std::string serviceAddress,
		std::string *model,
		std::string modelId,
		float telerance,
		std::string* speciesId,
		double* sInitialValue,
		float stepSize,
		int nTimeSteps,
		int sMatrixRow,
		int sMatrixCol,
		int nAssignmentSpecies,
		int nConstantSpecies,
		int nReactionSpecies,
		std::string *result){
	char *servAdd = new char[serviceAddress.length()+1];
	strcpy(servAdd,serviceAddress.c_str());

	//prepare the parameters for the service
	//const char * = "http://www.comp-sys-bio.org/CopasiWS/services/TimeCourseService";
	//	const char * serviceAddress = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	binding bind;
	ns2__DeterministicParameters *param  = new ns2__DeterministicParameters();
	_ns2__RunDeterministicSimulator *runReqDoc = new _ns2__RunDeterministicSimulator();
	_ns2__RunDeterministicSimulatorResponse *runResDoc = new _ns2__RunDeterministicSimulatorResponse();

	//parameters for sensitivity matrix
/*	ns2__SensitivityParameters *sParam = new ns2__SensitivityParameters();
	sParam->deltaFactor = 0.001;
	sParam->deltaMinimum = 1e-12;
	sParam->function = ns2__Function__Non_Constant_x0020Concentration_x0020of_x0020Species;
	sParam->variable = ns2__Variable__Initial_x0020Concentration;
*/
	//print out the model for debuging only
	//cout << model;
	//set the parameters
	float duration = nTimeSteps*stepSize;
	param->StepSize = stepSize;
	param->Duration = duration;
	//param->OutputStartTime = 10.0f;
	stringstream nSteps;
	nSteps << nTimeSteps;
	string stepNumber = nSteps.str();
	param->StepNumber = stepNumber;
	//	if(isInitialRun){
	param->__union_DeterministicParameters = 2;
	param->union_DeterministicParameters.sbml = model;
	param->outputFormat = _ns2__DeterministicParameters_outputFormat__text;
	param->updatedModelRequired = true;
////	param->sensitivityParameters = sParam;
	runReqDoc->parameters = param;

	//set the address of the service if different from default
	bind.endpoint = servAdd;
	if(bind.__ns1__runDeterministicSimulator(runReqDoc, runResDoc)==SOAP_OK)
		cout<<SOAP_SVR_FAULT<<endl;
	else
		soap_print_fault(bind.soap, stderr);
	soap_print_fault_location(bind.soap, stderr);

	//the data table from the simulator
	string dataTable = runResDoc->outputResult->result;
	////string sMatrixTable = runResDoc->sensitityResult;
	string *updatedModel = runResDoc->updatedModel;
	//	model = runResDoc->updatedModel;
	*model = *updatedModel;

	*result = dataTable;
	//cout<<sMatrixTable;

/*	stringstream ss_in (stringstream::in | stringstream::out);
	//	cout << sMatrixTable;
	ss_in << sMatrixTable;

	// remove the header information (metadata), may be needed in future
	string sLine;
	getline(ss_in,sLine);

	string *shdata;
	string shsdata;
	stringstream shss, shss1;
	shss << sLine;
	shss1 << sLine;

	//count the number of column data
	///int sColCounter =0;
	///while (getline(shss, shsdata, '|')){
///		sColCounter++;
///	}

	//	cout <<"Total Column: " <<sColCounter;
	//allocate memory for the header metadata and get the into it
	shdata = new string[sMatrixCol];
	//	int i = 0, j=0;
	for (int j = 0; j <sMatrixCol && getline(shss1, shsdata, '|'); j++){
		shdata[j] = shsdata;
	}

	//loop to get the data from the stream
	float in_float;
	string sLinel;
	int cont=0;
	//int sRowColCounter = 54;
	for (int i = 0; i < sMatrixRow && getline(ss_in,sLinel, '\n'); i++) {
		cont++;
		string data;
		stringstream ss;
		ss << sLinel;

		//get the column data
		for(int j = 0; j < sMatrixCol; j++){
			getline(ss, data, '|');
			stringstream in_ss;
			in_ss << data;
			in_ss >> in_float;

			//check to make sure the data sent back to requester correspond to the species id received
			//	for(int k = 0; k <sColCounter; k++){
			//	if(shdata[j].compare(speciesId[k])){
		//	sMatrix[i][j] = in_float;
			//	}
			//	}

		//print for debugging
		//	cout << sMatrix[i][j] << "\t";
		cout << in_float << "\t";
	}
		cout << endl;
}*/
//	cout<<*model<<"My End"<<endl;
//cout <<"My result" << sMatrix << "End of result";

//clean up the mess
//delete sParam;
delete param;
delete runReqDoc;
delete runResDoc;
return 0;
}

int CopasiWSSimulator::runTimeCourseSimulator(
		std::string serviceAddress,
		std::string *model,
		std::string modelId,
		float stepSize,
		int nTimeSteps,
		std::string *result,
		std::string *sMatrix){
	char *servAdd = new char[serviceAddress.length()+1];
	strcpy(servAdd,serviceAddress.c_str());

	//prepare the parameters for the service
	//const char * = "http://www.comp-sys-bio.org/CopasiWS/services/TimeCourseService";
	//	const char * serviceAddress = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	binding bind;
	ns2__DeterministicParameters *param  = new ns2__DeterministicParameters();
	_ns2__RunDeterministicSimulator *runReqDoc = new _ns2__RunDeterministicSimulator();
	_ns2__RunDeterministicSimulatorResponse *runResDoc = new _ns2__RunDeterministicSimulatorResponse();

	//parameters for sensitivity matrix
	ns2__SensitivityParameters *sParam = new ns2__SensitivityParameters();
	sParam->deltaFactor = 0.001;
	sParam->deltaMinimum = 1e-12;
	sParam->function = ns2__Function__Non_Constant_x0020Concentration_x0020of_x0020Species;
	sParam->variable = ns2__Variable__Initial_x0020Concentration;

	//print out the model for debuging only
	//cout << model;
	//set the parameters
	float duration = nTimeSteps*stepSize;
	param->StepSize = stepSize;
	param->Duration = duration;
	//param->OutputStartTime = 10.0f;
	stringstream nSteps;
	nSteps << nTimeSteps;
	string stepNumber = nSteps.str();
	param->StepNumber = stepNumber;
	//	if(isInitialRun){
	param->__union_DeterministicParameters = 2;
	param->union_DeterministicParameters.sbml = model;
	param->outputFormat = _ns2__DeterministicParameters_outputFormat__text;
	param->updatedModelRequired = true;
	param->sensitivityParameters = sParam;
	runReqDoc->parameters = param;

	//set the address of the service if different from default
	bind.endpoint = servAdd;
	if(bind.__ns1__runDeterministicSimulator(runReqDoc, runResDoc)==SOAP_OK)
		cout<<SOAP_SVR_FAULT<<endl;
	else
		soap_print_fault(bind.soap, stderr);
	soap_print_fault_location(bind.soap, stderr);

	//the data table from the simulator
	string dataTable = runResDoc->outputResult->result;
	string sMatrixTable = runResDoc->sensitityResult;
	string *updatedModel = runResDoc->updatedModel;
	//	model = runResDoc->updatedModel;
	*model = *updatedModel;
	*result = dataTable;
	*sMatrix = sMatrixTable;
	//cout<<*sMatrix;

	//	cout<<*model<<"My End"<<endl;
	//cout <<"My result" << sMatrix << "End of result";

	//clean up the mess
	delete sParam;
	delete param;
	delete runReqDoc;
	delete runResDoc;
	return 0;
}

int CopasiWSSimulator::runTimeCourseSimulator(){
	//prepare the parameters for the service
	/*binding bind;
	ns2__DeterministicParameters *param  = new ns2__DeterministicParameters();
	_ns2__RunDeterministicSimulator *runReqDoc = new _ns2__RunDeterministicSimulator();
	_ns2__RunDeterministicSimulatorResponse *runResDoc = new _ns2__RunDeterministicSimulatorResponse();

	//load the sbml file
	//ifstream in("/home/dada/data/kineticDataTest/sbml.xml");
	ifstream in("/home/dada/data/release_25March2009_sbmls/curated/BIOMD0000000010.xml");
	string s, line;
	while(getline(in, line))
		s += line + "\n";*/

	//read the document from the string
	/*SBMLReader reader;
	SBMLDocument *doc;
	Model *model;
	ListOfSpecies *sList;
	doc = reader.readSBMLFromString(s);
	model = doc->getModel();
	sList = model->getListOfSpecies();

	//change the initial concentration of the species
	sList->get("MKKK")->setInitialConcentration(2000);

	//the sbml model for simulator
	s = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>\n";
	s += doc->toSBML();
	 */
	//set the parameters
	//param->Duration = 1.0f;

	//commented by JO Dada to be added if needed later
	/*param->StepSize = 1.0f;
	param->StepNumber = "10";
	param->__union_DeterministicParameters = 2;
	param->union_DeterministicParameters.sbml = &s;
	param->outputFormat = _ns2__DeterministicParameters_outputFormat__text;
	runReqDoc->parameters = param;

	//set the address of the service if different from default
	bind.endpoint = "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";
	if(bind.__ns1__runDeterministicSimulator(runReqDoc, runResDoc)==SOAP_OK)
		cout<<SOAP_SVR_FAULT<<endl;
	else
		soap_print_fault(bind.soap, stderr);
	soap_print_fault_location(bind.soap, stderr);

	ofstream sbrmlFile("/home/dada/data/kineticDataTest/sbrml.xml");
	if(!sbrmlFile){
		cerr << "Failed to open sbrmlFile\n";
		exit(EXIT_FAILURE);
	}
	//save the string as SBRML file
	sbrmlFile << runResDoc->outputResult->result; // <<"\n";
	sbrmlFile.close();

	//convert the simulation data into a matrix
	stringstream ss;
	ss << param->StepNumber;
	int is_int;
	ss >> is_int;

	int nRow = is_int+1;
	int nCol = 8;

	float **mat;
	//matrix memory allocation
	mat = new float *[nRow];
	int i = 0, j = 0;
	for (i=0; i<nRow; i++){
		mat[i] = new float[nCol];
	}

	readData(runResDoc->outputResult->result, &mat[0], nRow, nCol);

	//print out the array data for debbuging
	for(i = 0; i < nRow; i++){
		for(j = 0; j < nCol; j++){
			cout << mat[i][j] << "\t";
		}
		cout << endl;
	}
	//clean up the mess
	delete param;
	delete runReqDoc;
	delete runResDoc;*/
	//delete doc;
	return 0;
}
CopasiWSSimulator::~CopasiWSSimulator() {
	// TODO Auto-generated destructor stub
}

void CopasiWSSimulator::readData(string dataTable, float **data2DArray, int nRow, int nCol) {

	string line;
	stringstream in (stringstream::in | stringstream::out);
	in << dataTable;

	//print for debugging
//	cout << dataTable << "\n";

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
			data2DArray[i][j] = in_float;
		}
	}

}
