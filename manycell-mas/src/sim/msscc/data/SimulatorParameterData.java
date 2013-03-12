package sim.msscc.data;

import java.util.Map;

public class SimulatorParameterData {
	
	private float stepSize;
	private int nTimeSteps;
	private int nSpecies;
	private float tolerance;
	private String modelFile;
	
	private float simDuration;
	private float simTime;	
	
	private String [] activeVariableIds = {};
	
	private String biomassVariable = null; //"Area_B_m";// "MASS";
	private String partitionFunction = null; //"F";	
	private String eventVariable = null; // "BUD";
	private float eventVariableValue	=	0.0f;
	private String budVariable = null;
	
	private String nutrientModelId	=	null;
	private String GLUConsumeId	=	null;
	
	private double environmentVolume;
	
	Map<String, Float> changeVariableIdsValuesMap;
	
	
	public String getGLUConsumeId() {
		return GLUConsumeId;
	}
	public void setGLUConsumeId(String gLUConsumeId) {
		GLUConsumeId = gLUConsumeId;
	}
	public String getNutrientModelId() {
		return nutrientModelId;
	}
	public void setNutrientModelId(String nutrientModelId) {
		this.nutrientModelId = nutrientModelId;
	}
	public Map<String, Float> getChangeVariableIdsValuesMap() {
		return changeVariableIdsValuesMap;
	}
	public void setChangeVariableIdsValuesMap(
			Map<String, Float> changeVariableIdsValuesMap) {
		this.changeVariableIdsValuesMap = changeVariableIdsValuesMap;
	}
	public String getBiomassVariable() {
		return biomassVariable;
	}
	public void setBiomassVariable(String biomassVariable) {
		this.biomassVariable = biomassVariable;
	}
	public String getPartitionFunction() {
		return partitionFunction;
	}
	public void setPartitionFunction(String partitionFunction) {
		this.partitionFunction = partitionFunction;
	}
	public String getEventVariable() {
		return eventVariable;
	}
	public void setEventVariable(String eventVariable) {
		this.eventVariable = eventVariable;
	}
	public float getEventVariableValue() {
		return eventVariableValue;
	}
	public void setEventVariableValue(float eventVariableValue) {
		this.eventVariableValue = eventVariableValue;
	}
	public String[] getActiveVariableIds() {
		return activeVariableIds;
	}
	public void setActiveVariableIds(String[] activeVariableIds) {
		this.activeVariableIds = activeVariableIds;
	}	
	public String getBudVariable() {
		return budVariable;
	}
	public void setBudVariable(String budVariable) {
		this.budVariable = budVariable;
	}
	public float getStepSize() {
		return stepSize;
	}
	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}
	public int getnTimeSteps() {
		return nTimeSteps;
	}
	public void setnTimeSteps(int nTimeSteps) {
		this.nTimeSteps = nTimeSteps;
	}
	public int getnSpecies() {
		return nSpecies;
	}
	public void setnSpecies(int nSpecies) {
		this.nSpecies = nSpecies;
	}
	public float getTolerance() {
		return tolerance;
	}
	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}
	public float getSimDuration() {
		return simDuration;
	}
	public void setSimDuration(float simDuration) {
		this.simDuration = simDuration;
	}
	public float getSimTime() {
		return simTime;
	}
	public void setSimTime(float simTime) {
		this.simTime = simTime;
	}
	public String getModelFile() {
		return modelFile;
	}
	public void setModelFile(String modelFile) {
		this.modelFile = modelFile;
	}
	public double getEnvironmentVolume() {
		return environmentVolume;
	}
	public void setEnvironmentVolume(double environmentVolume) {
		this.environmentVolume = environmentVolume;
	}	
	
	
	
	

}
