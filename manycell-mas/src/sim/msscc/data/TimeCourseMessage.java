package sim.msscc.data;

public class TimeCourseMessage {
	private String model;
	private String[]speciesInitialCondition;
	private int stepSize;
	private int nTimeSteps;
	private int nSpecies;
	private String []speciesId;
	private float tolerance;
	
	
	private boolean valueIsSet = false;
	
	
public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}	

	public String[] getSpeciesInitialCondition() {
		return speciesInitialCondition;
	}


	public void setSpeciesInitialCondition(String[] speciesInitialCondition) {
		this.speciesInitialCondition = speciesInitialCondition;
	}


	public boolean isValueIsSet() {
		return valueIsSet;
	}


	public void setValueIsSet(boolean valueIsSet) {
		this.valueIsSet = valueIsSet;
	}


	synchronized void setSimInput(String model){
		if(valueIsSet==true){
			try{
				wait();
			}
			catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
			}
			
			this.setModel(model);
			valueIsSet=false;
			notify();
		}
	}

}
