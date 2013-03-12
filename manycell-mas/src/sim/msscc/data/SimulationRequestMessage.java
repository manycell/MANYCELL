package sim.msscc.data;

public class SimulationRequestMessage extends Message{
	private String model;
	private double []speciesInitialCondition;
	private float stepSize;
	private int nTimeSteps;
	private int nSpecies;
	
	private int sMatrixRow;
	private int sMatrixCol;
	
	private String []speciesId;
	private String headerString;
	private float tolerance;	
		
	private float globalTime;	
	private float lastDivisionTime;
	private String cellType;
	private int stepCounter = 0; //counter for the time step before cell division
	
	private float cellBirthSize;
	private boolean isNew;
	private boolean isDivisionTime;
	
	private String [] activeVariableIds ={};
	
	private String biomassVariable = null; //"Area_B_m";// "MASS";
	private String partitionFunction = null; //"F";	
	private String eventVariable = null; // "BUD";
	private float eventVariableValue	=	0.0f;
	private String GLUConsumeId = null; 

	// monitoring the species types (reaction, assignment, constant)
	int nReactionSpecies;
	int nConstantSpecies;
	int nAssignmentSpecies;
	
	public SimulationRequestMessage(String sender, String receiver, String messageId, String model, float globalTime){
		super(sender, receiver, messageId);
		this.model = model;
		this.globalTime = globalTime;
	}	
	
	
	public String getGLUConsumeId() {
		return GLUConsumeId;
	}


	public void setGLUConsumeId(String gLUConsumeId) {
		GLUConsumeId = gLUConsumeId;
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



	public float getLastDivisionTime() {
		return lastDivisionTime;
	}



	public void setLastDivisionTime(float lastDivisionTime) {
		this.lastDivisionTime = lastDivisionTime;
	}



	public int getnReactionSpecies() {
		return nReactionSpecies;
	}



	public void setnReactionSpecies(int nReactionSpecies) {
		this.nReactionSpecies = nReactionSpecies;
	}



	public int getnConstantSpecies() {
		return nConstantSpecies;
	}



	public void setnConstantSpecies(int nConstantSpecies) {
		this.nConstantSpecies = nConstantSpecies;
	}



	public int getnAssignmentSpecies() {
		return nAssignmentSpecies;
	}



	public void setnAssignmentSpecies(int nAssignmentSpecies) {
		this.nAssignmentSpecies = nAssignmentSpecies;
	}



	public float getCellBirthSize() {
		return cellBirthSize;
	}



	public void setCellBirthSize(float cellBirthSize) {
		this.cellBirthSize = cellBirthSize;
	}



	public boolean isDivisionTime() {
		return isDivisionTime;
	}



	public void setDivisionTime(boolean isDivisionTime) {
		this.isDivisionTime = isDivisionTime;
	}



	public int getStepCounter() {
		return stepCounter;
	}


	public void setStepCounter(int stepCounter) {
		this.stepCounter = stepCounter;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public String getCellType() {
		return cellType;
	}



	public void setCellType(String cellType) {
		this.cellType = cellType;
	}



	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double[] getSpeciesInitialCondition() {
		return speciesInitialCondition;
	}

	public void setSpeciesInitialCondition(double[] speciesInitialCondition) {
		this.speciesInitialCondition = speciesInitialCondition;
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

	public String[] getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(String[] speciesId) {
		this.speciesId = speciesId;
	}

	public float getTolerance() {
		return tolerance;
	}

	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}

	public float getGlobalTime() {
		return globalTime;
	}

	public void setGlobalTime(float globalTime) {
		this.globalTime = globalTime;
	}

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	public int getsMatrixRow() {
		return sMatrixRow;
	}

	public void setsMatrixRow(int sMatrixRow) {
		this.sMatrixRow = sMatrixRow;
	}

	public int getsMatrixCol() {
		return sMatrixCol;
	}

	public void setsMatrixCol(int sMatrixCol) {
		this.sMatrixCol = sMatrixCol;
	}	
	
	
	
	
}
