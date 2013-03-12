/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/include/CopasiWSSimulator.h,v $
 *  $Revision: 1.4 $
 *  $Name:  $
 *  $Author: jdada $
 *  $Date: 2010/08/18 11:02:42 $
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

#ifndef COPASIWSSIMULATOR_H_
#define COPASIWSSIMULATOR_H_
//#include "string"

#include "Simulator.h"
class CopasiWSSimulator : public Simulator {
public:
	CopasiWSSimulator();

	virtual int runTimeCourseSimulator(
			const std::string model,
			const std::string modelId,
			float tolerance,
			std::string* speciesId,
			double* sInitialValue,
			float stepSize,
			int nTimeSteps,
			int nSpecies,
			int nNonConstSpecies,
			float** data2DArray);

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
						float** data2DArray);

	// use when sensitivity matrix is required
	virtual int runTimeCourseSimulator(
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
			float** sMatix);

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
				std::string* result);

	virtual int runTimeCourseSimulator(
					std::string serviceAddress,
					std::string *model,
					std::string modelId,
					float stepSize,
					int nTimeSteps,
					std::string* result,
					std::string* xMatrix);

	virtual int runTimeCourseSimulator();

	virtual void readData(std::string dataTable, float** mat, int nRowm, int nCol);

	virtual ~CopasiWSSimulator();
};

#endif /* COPASIWSSIMULATOR_H_ */
