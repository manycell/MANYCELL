package sim.msscc.agents;

import java.util.Hashtable;

/**
 * The <code>MediumAgent</code> is an implementation of the model of the nutrient in the environment
 * of the biological cell
 *  
 * @author Joseph O. Dada
 *
 * @copyright
 * MANYCELL, UNICELLSYS Project
 * (C) The University of Manchester 2012
 *
 */
//this class is presently not used, only necessary if there are more than one nutrient in the environment
public class MediumAgent {
	private Hashtable <String, Float> nutrients;
	private Hashtable <String, Float> consumedNutrients;	
	private Hashtable <String, Float> availableNutrients;

	public MediumAgent(Hashtable <String, Float> nutrients){
		this.nutrients = nutrients;
	}
}
