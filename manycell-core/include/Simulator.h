/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/include/Simulator.h,v $
 *  $Revision: 1.6 $
 *  $Name:  $
 *  $Author: jdada $
 *  $Date: 2010/06/07 09:44:46 $
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

#ifndef SIMULATOR_H_
#define SIMULATOR_H_

#include <string>
#include <vector>

/**
 * Base class for different types of simulator
 */
class Simulator {
public:

	/** External interface to ODEs simulator
	 * @param model, the sbml model to run
	 * @param modelId, id to identify the sbml model to run
	 * @param tolerance, for ISAT testing
	 * @param speciesId, string array containing ids of species in model
	 * @param speciesInitialValue, vector containing the species initial values.
	 * @param stepSize, float simulator step size
	 * @param nTimeSteps, total number of simulation time steps
	 * @param nPecies, total number of species in model
	 * @param nNonConstSpecies,total number of non constant species
	 * @param data2DArray, variable containing the output time series data
	 * @return 0 for success and >1 otherwise
	 */
	virtual int runTimeCourseSimulator(
			std::string model,
			std::string modelId,
			float tolerance,
			std::string* speciesId,
			double* speciesInitialValue,
			float stepSize,
			int nTimeSteps,
			int nSpecies,
			int nNonConstSpecies,
			float** data2DArray) = 0;

	/** External interface to ODEs simulator
		 * @param model, the sbml model to run
		 * @param modelId, id to identify the sbml model to run
		 * @param tolerance, for ISAT testing
		 * @param speciesId, string array containing ids of species in model
		 * @param speciesInitialValue, vector containing the species initial values.
		 * @param stepSize, float simulator step size
		 * @param nTimeSteps, total number of simulation time steps
		 * @param nPecies, total number of species in model
		 * @param nNonConstSpecies,total number of non constant species
		 * @param data2DArray, variable containing the output time series data
		 * @return 0 for success and >1 otherwise
		 */
		virtual int runSplitTimeCourseSimulation(
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
				float** data2DArray) = 0;
	// use when sensitivity matrix is required
	virtual int runTimeCourseSimulator(
			std::string model,
			std::string modelId,
			float tolerance,
			std::string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int nSpecies,
			int nNonConstSpecies,
			float** data2DArray,
			float** sMatix) = 0;

	/** External interface to ODEs simulator
	 * @param serviceAddress, the address of the web service to run the simulation
	 * @param model, the sbml model to run
	 * @param modelId, id to identify the sbml model to run
	 * @param stepSize, float simulator step size
	 * @param nTimeSteps, total number of simulation time steps
	 * @param result, variable containing the output data
	 * @return 0 for success and >1 otherwise
	 */
	virtual int runTimeCourseSimulator(
			std::string serviceAddress,
			std::string *model,
			std::string modelId,
			float tolerance,
			std::string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int sMatrixRow,
			int sMatrixCol,
			int nAssignmentSpecies,
			int nConstantSpecies,
			int nReactionSpecies,
			std::string* result) = 0;

	//simple run time course simulator
	virtual int runTimeCourseSimulator() = 0;

	/** The default constructor */
	Simulator(){}

	 /** The destructor */
	 ~Simulator(){}
//private:

//	 The copy constructor, you cannot call this directly */
	Simulator(const Simulator&);

	// The assignment operator, you cannot call this directly */
	Simulator& operator=(const Simulator&);
};

#endif /* SIMULATOR_H_ */
