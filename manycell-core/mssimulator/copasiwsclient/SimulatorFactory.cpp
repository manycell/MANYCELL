/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/mssimulator/copasiwsclient/SimulatorFactory.cpp,v $
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

#include "SimulatorFactory.h"
#include "CopasiWSSimulator.h"
#include "ISTabulator.h"
SimulatorFactory::SimulatorFactory() {
	// TODO Auto-generated constructor stub

}

SimulatorFactory::~SimulatorFactory() {
	// TODO Auto-generated destructor stub
}

Simulator *SimulatorFactory::getSimulatorInstance(int id){
	switch (id){
		case 1:
			return new CopasiWSSimulator;
		case 2:
			return new ISTabulator;
		default:
			return NULL;
		}
}

