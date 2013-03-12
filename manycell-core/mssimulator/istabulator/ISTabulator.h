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

#ifndef ISTABULATOR_H_
#define ISTABULATOR_H_

#include <iostream>
#include <fstream>
#include "SimulatorFactory.h"
#include "Simulator.h"
#include "BSTree.h"

BSTree mISTable;
BSTree mMFUISTable;
int mISTableCounter = 0;
int mMFUISTableCounter = 0;
int query = 0;
int retrieve = 0;
int counter = 1;
//std::ofstream ISATSummaryfile("isat.txt");
class ISTabulator : public Simulator {
public:
	ISTabulator();
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
			float** sMatrix);

/*virtual int runTimeCourseSimulator(
					std::string serviceAddress,
					std::string *model,
					std::string modelId,
					float stepSize,
					int nTimeSteps,
					std::string* result);*/

//new method for tabulator
virtual int runTimeCourseSimulator1(
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
			std::string* result);

//new method for tabulator
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

virtual int runTimeCourseSimulator();

virtual void convertString2Array(std::string dataTable, float** mat, int nRowm, int nCol);

virtual ~ISTabulator();

protected:
//	BSTree mISTable;
//	int counter;

};
#endif /* ISTABULATOR_H_ */
