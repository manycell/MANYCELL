package sim.msscc.agents;

import java.util.Hashtable;

//this class is presently not used, only necessary if there are more than one nutrient in the environment
public class MediumAgent {
	private Hashtable <String, Float> nutrients;
	private Hashtable <String, Float> consumedNutrients;	
	private Hashtable <String, Float> availableNutrients;

	public MediumAgent(Hashtable <String, Float> nutrients){
		this.nutrients = nutrients;
	}
}
