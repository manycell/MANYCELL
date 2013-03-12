/* Begin CVS Header 
 *  $Source$ 
 *  $Revision$
 *  $Name$
 *  $Author$
 *  $Date$
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
package sim.jnative;

public class SimulatorConnector {	
	static {    	
	//	System.loadLibrary("psimnative");
		System.loadLibrary("psconnector");
	} 
	
	 /**
	 * Create a pointer to the CopasiWS client
	 */	 
	public native int initSimulator();

	/** Connect to the CopasiWS client 
	 * @param pWayName string, name of the pathway to run
	 * @param sInitialValue float array, initial value of species
	 * @param stepSize java float, step size for the time course simulator
	 * @param nTimeSteps java int, number of time steps for the simulator
	 * @param nSpecies java int, number of species in the sbml model 
	 */
	private native float[][] runTCSimulatior(
			String pathWayName,
			//String []speciesToChange,
			// float[] newSpeciesValue;
			float [] sInitialValue,
			float stepSize,
			int nTimeSteps,
			int nSpecies,
			int ptr);

	public static void main(String[] args) {
		SimulatorConnector connector = new SimulatorConnector();
		int res=connector.initSimulator();
		System.out.println("Please wait for the result.....");
		
		//assign parameter values
		int nTimeSteps = 2, nSpecies = 15;
		String pWayType = "cell cycle"; 
		float sSize = 1.0f;		
		float []sIValue = new float [nSpecies];

		for (int k = 0; k<nSpecies; k++){
			sIValue[k] = 0.034f;
		}		
		 
		float [][] simResult = connector.runTCSimulatior(pWayType, sIValue, sSize, nTimeSteps, nSpecies, res);       
		System.out.println();
		for (int i = 0; i < nTimeSteps+1; i++) {
			for (int j = 0; j < nSpecies; j++) {            	
				System.out.print("	" + simResult[i][j]);
			}
			System.out.println();
		}
	}

}
