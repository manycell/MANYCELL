/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/mssimulator/copasiwsclient/Factory.h,v $
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

#ifndef FACTORY_H_
#define FACTORY_H_

#include "Simulator.h"

/**
 * Factory class for creating different types of simulator
 */
class Factory{
public:
	virtual Simulator* createCopasiWSSimulatorInstance() = 0;
	virtual Simulator* createTabulationSimulatorInstance() = 0;
};

#endif /* FACTORY_H_ */
