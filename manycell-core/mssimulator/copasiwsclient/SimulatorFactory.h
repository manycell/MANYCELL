/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/mssimulator/copasiwsclient/SimulatorFactory.h,v $
 *  $Revision: 1.2 $
 *  $Name:  $
 *  $Author: jdada $
 *  $Date: 2010/05/28 15:43:38 $
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

#ifndef SIMULATORFACTORY_H_
#define SIMULATORFACTORY_H_

#include "ISTabulator.h"
#include "Factory.h"
#include "Simulator.h"
#include "CopasiWSSimulator.h"

/**
 * A factory class for creating different types of simulator. Presently a subclass
 * of Factory class but can be used without the Factory class
 */
class SimulatorFactory : public Factory {
public:
	SimulatorFactory();

	//create the CopasiWS Simulator instance
	virtual Simulator* createCopasiWSSimulatorInstance(){
		return new CopasiWSSimulator;
	};

	//create the tabulation instance

		virtual Simulator* createTabulationSimulatorInstance(){};

	//return any instance identify by id, 1 for CopasiWSSimulator, 2 for TabulationSimulator
	virtual Simulator* getSimulatorInstance(int id);

	virtual ~SimulatorFactory();
};

#endif /* SIMULATORFACTORY_H_ */
