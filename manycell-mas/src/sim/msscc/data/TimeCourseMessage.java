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

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}


	/**
	 * @return the speciesInitialCondition
	 */
	public String[] getSpeciesInitialCondition() {
		return speciesInitialCondition;
	}


	/**
	 * @param speciesInitialCondition the speciesInitialCondition to set
	 */
	public void setSpeciesInitialCondition(String[] speciesInitialCondition) {
		this.speciesInitialCondition = speciesInitialCondition;
	}


	/**
	 * @return the stepSize
	 */
	public int getStepSize() {
		return stepSize;
	}


	/**
	 * @param stepSize the stepSize to set
	 */
	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}


	/**
	 * @return the nTimeSteps
	 */
	public int getnTimeSteps() {
		return nTimeSteps;
	}


	/**
	 * @param nTimeSteps the nTimeSteps to set
	 */
	public void setnTimeSteps(int nTimeSteps) {
		this.nTimeSteps = nTimeSteps;
	}


	/**
	 * @return the nSpecies
	 */
	public int getnSpecies() {
		return nSpecies;
	}


	/**
	 * @param nSpecies the nSpecies to set
	 */
	public void setnSpecies(int nSpecies) {
		this.nSpecies = nSpecies;
	}


	/**
	 * @return the speciesId
	 */
	public String[] getSpeciesId() {
		return speciesId;
	}


	/**
	 * @param speciesId the speciesId to set
	 */
	public void setSpeciesId(String[] speciesId) {
		this.speciesId = speciesId;
	}


	/**
	 * @return the tolerance
	 */
	public float getTolerance() {
		return tolerance;
	}


	/**
	 * @param tolerance the tolerance to set
	 */
	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}


	/**
	 * @return the valueIsSet
	 */
	public boolean isValueIsSet() {
		return valueIsSet;
	}


	/**
	 * @param valueIsSet the valueIsSet to set
	 */
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
