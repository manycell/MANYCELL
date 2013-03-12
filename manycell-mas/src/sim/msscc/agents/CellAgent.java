package sim.msscc.agents;

import java.sql.Connection;
import java.util.Map;

import sim.msscc.behaviours.TimeCourseBehaviour;
import sim.msscc.data.DatabaseConnectionPool;
import sim.msscc.data.EventData;
import sim.msscc.data.FileDataManager;
import sim.msscc.data.SimulationRequestMessage;
import sim.msscc.data.SimulatorParameterData;
import sim.msscc.data.TimeCourseInputData;

public class CellAgent implements Runnable{
	
	private String agentId = null;
	private float birthSize;
	private double volume;
	private boolean isMotherCell = true;
	private boolean isDaughterCell = true;
	private String motherId = null;
	private int motherAgeAtBirth;
//	private String model;
	private SimulationRequestMessage simMsg;
	private TimeCourseInputData tcInputData = null;
	private float lastDivisionEventTime;
	private SimulatorParameterData paramData = null;
	private EventData eventData = null;
	private float gTime;
	private String metaData;
	private String stateAtEvent;
	private String simState;
	private boolean isNewCell = true;
	private float sumOfGLUConsumed;
	private int gAge = 0;	
//	private int generation = 0;
	private int stepCounter = 0; //count the number of time step before cell division
	private boolean isDivided = false;
	private boolean useTabulator;
	private String webServiceAddress;
	private DatabaseConnectionPool pool;	
	
	/*private String [] changedVariableIds = {};
	private float [] changedVariableValues = {};		*/	
	
	public boolean isUseTabulator() {
		return useTabulator;
	}
	public void setUseTabulator(boolean useTabulator) {
		this.useTabulator = useTabulator;
	}
	public String getWebServiceAddress() {
		return webServiceAddress;
	}
	public void setWebServiceAddress(String webServiceAddress) {
		this.webServiceAddress = webServiceAddress;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public DatabaseConnectionPool getPool() {
		return pool;
	}
	public void setPool(DatabaseConnectionPool pool) {
		this.pool = pool;
	}
	public boolean isDivided() {
		return isDivided;
	}
	public void setDivided(boolean isDivided) {
		this.isDivided = isDivided;
	}
	public int getStepCounter() {
		return stepCounter;
	}
	public void setStepCounter(int stepCounter) {
		this.stepCounter = stepCounter;
	}
	public boolean isNewCell() {
		return isNewCell;
	}
	public void setNewCell(boolean isNewCell) {
		this.isNewCell = isNewCell;
	}
	public String getSimState() {
		return simState;
	}
	public void setSimState(String simState) {
		this.simState = simState;
	}
	public float getBirthSize() {
		return birthSize;
	}
	public void setBirthSize(float birthSize) {
		this.birthSize = birthSize;
	}
	public int getgAge() {
		return gAge;
	}
	public void setgAge(int gAge) {
		this.gAge = gAge;
	}

	private double currentGlucose;
	private float cycleTime;	
	
	public float getCycleTime() {
		return cycleTime;
	}
	public void setCycleTime(float cycleTime) {
		this.cycleTime = cycleTime;
	}
	
	
	public double getCurrentGlucose() {
		return currentGlucose;
	}
	public void setCurrentGlucose(double currentGlucose) {
		this.currentGlucose = currentGlucose;
	}
	public float getSumOfGLUConsumed() {
		return sumOfGLUConsumed;
	}
	public void setSumOfGLUConsumed(float sumOfGLUConsumed) {
		this.sumOfGLUConsumed = sumOfGLUConsumed;
	}
	public CellAgent(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentId() {
		return agentId;
	}
	
	public String getMotherId() {
		return motherId;
	}
	public void setMotherId(String motherId) {
		this.motherId = motherId;
	}
	public int getMotherAgeAtBirth() {
		return motherAgeAtBirth;
	}
	public void setMotherAgeAtBirth(int motherAgeAtBirth) {
		this.motherAgeAtBirth = motherAgeAtBirth;
	}
	public String getStateAtEvent() {
		return stateAtEvent;
	}
	public void setStateAtEvent(String stateAtEvent) {
		this.stateAtEvent = stateAtEvent;
	}
	public String getMetaData() {
		return metaData;
	}
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}



	public boolean isMotherCell() {
		return isMotherCell;
	}



	public void setMotherCell(boolean isMotherCell) {
		this.isMotherCell = isMotherCell;
	}



	public boolean isDaughterCell() {
		return isDaughterCell;
	}



	public void setDaughterCell(boolean isDaughterCell) {
		this.isDaughterCell = isDaughterCell;
	}



	public SimulationRequestMessage getSimMsg() {
		return simMsg;
	}



	public void setSimMsg(SimulationRequestMessage simMsg) {
		this.simMsg = simMsg;
	}



	public TimeCourseInputData getTcInputData() {
		return tcInputData;
	}



	public void setTcInputData(TimeCourseInputData tcInputData) {
		this.tcInputData = tcInputData;
	}



	public float getLastDivisionEventTime() {
		return lastDivisionEventTime;
	}



	public void setLastDivisionEventTime(float lastDivisionEventTime) {
		this.lastDivisionEventTime = lastDivisionEventTime;
	}



	public SimulatorParameterData getParamData() {
		return paramData;
	}



	public void setParamData(SimulatorParameterData paramData) {
		this.paramData = paramData;
	}	


	public EventData getEventData() {
		return eventData;
	}



	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}
	


	public float getgTime() {
		return gTime;
	}


	public void setgTime(float gTime) {
		this.gTime = gTime;
	}	
	
	public void run(){		
	//	 SimulationRequestMessage simMsg;	
		 // float gTime = ccMsg.getGlobalTime();
		  System.out.println("My Time: "+gTime+" My Id: "+this.getAgentId());

		/*  String model = this.loadModel(this.getParamData().getModelFile());
		  tcInputData = new TimeCourseInputData(model);  */
		  if(gTime==0.0f){
			  this.setTcInputData(this.prepareTCInputData(paramData.getModelFile()));
		  }else{
			  this.tcInputData = this.prepareTCInputData(this.paramData.getModelFile());
			//  this.tcInputData.exstractInitialCondition();
			  this.tcInputData.setSumOfGLUConsumed(this.getSumOfGLUConsumed());
			  this.tcInputData.setCurrentGlucose(this.getCurrentGlucose());			  
			  this.tcInputData.setNutrientModelId(this.paramData.getNutrientModelId());
			  
			  this.tcInputData.setPartitionFunction(this.paramData.getPartitionFunction());
			  this.tcInputData.setEventVariable(this.paramData.getEventVariable());
			  this.tcInputData.setEventVariableValue(this.paramData.getEventVariableValue());
			  this.tcInputData.setEnvironmentVolume(this.paramData.getEnvironmentVolume());
			  this.tcInputData.setCellVolume(this.volume);
			  this.tcInputData.setNewCell(this.isNewCell());
			  //use sim state or state at event
			  //is cell duaghter cell
			//  if(this.isDivided() && this.isDaughterCell() && this.isNewCell()){
				  if(this.isDivided){	
				  this.tcInputData.changeSInitialCondition(this.getStateAtEvent(), this.getMetaData(), this.isDaughterCell(), this.isDivided);
				  
			  }else{
				  this.tcInputData.changeSInitialCondition(this.getSimState(), this.getMetaData(), this.isDaughterCell(), this.isDivided);
				  
			  }
			  
				  //extract the initial condition
				  this.tcInputData.setInitialCondition();
			  
		/*	  if(this.gAge==0 && this.isNewCell()){				  
			  this.tcInputData.changeSInitialCondition(this.getStateAtEvent(), this.getMetaData(), this.isDaughterCell());			  
			 }else if(!this.isNewCell() && this.gAge==0){				 
				 this.tcInputData.changeSInitialCondition(this.getStateAtEvent(), this.getMetaData(), this.isDaughterCell());		  
			}else if(!this.isNewCell() && this.gAge>=0){
				this.tcInputData.changeSInitialCondition(this.getSimState(), this.getMetaData(), this.isDaughterCell());
			}*/
			  
		/*	else{
				this.tcInputData.changeSInitialCondition(this.getStateAtEvent(), this.getMetaData(), this.isDaughterCell());
				
			}*/
		  }	
		  
		  simMsg = new SimulationRequestMessage(this.getAgentId(), null, "", this.tcInputData.getModel(), this.gTime);	
		
		  
		  simMsg.setnTimeSteps(this.paramData.getnTimeSteps());
		  simMsg.setStepSize(this.paramData.getStepSize());
		  simMsg.setTolerance(this.paramData.getTolerance()); 
		  simMsg.setStepCounter(this.getStepCounter());
		  simMsg.setDivisionTime(this.isDivided());		  
		  simMsg.setActiveVariableIds(this.paramData.getActiveVariableIds());
		  simMsg.setBiomassVariable(this.paramData.getBiomassVariable());
		  simMsg.setEventVariable(this.paramData.getEventVariable());
		  simMsg.setEventVariableValue(this.paramData.getEventVariableValue());
		  simMsg.setPartitionFunction(this.paramData.getPartitionFunction());
		  simMsg.setGLUConsumeId(this.paramData.getGLUConsumeId());
		
		//  simMsg.setLastDivisionTime(this.getLastDivisionEventTime());
		  
		  simMsg.setnSpecies(this.getTcInputData().getnSpecies());	   
		  simMsg.setsMatrixRow((this.getTcInputData().getsMatrixRow()));
		  simMsg.setsMatrixCol((this.getTcInputData().getsMatrixCol()));
		  
		  simMsg.setnAssignmentSpecies(this.getTcInputData().getnAssignmentSpecies());
		  simMsg.setnConstantSpecies(this.getTcInputData().getnConstantSpecies());
		  simMsg.setnReactionSpecies(this.getTcInputData().getnReactionSpecies());
		  
		  simMsg.setSpeciesInitialCondition(this.getTcInputData().getSpeciesInitialCondition());	   
		  simMsg.setSpeciesId(this.getTcInputData().getSpeciesId());
		  //System.out.println("=========="+this.getTcInputData().getCellBirthSize());
		  this.setBirthSize(this.getTcInputData().getCellBirthSize());
		  
		  if(this.isDaughterCell()){
			  simMsg.setCellType("D");
		  }else{
			  simMsg.setCellType("P");
		  }
		  
		  if(this.isNewCell()){
			  simMsg.setNew(true);
		  }else{
			  simMsg.setNew(false);
		  }
		  
		//run time course
		  TimeCourseBehaviour tcBehaviour = new TimeCourseBehaviour();
		  tcBehaviour.setAgentId(this.getAgentId());
		  tcBehaviour.setCellAge(this.getgAge());
		  tcBehaviour.setBirthSize(this.getBirthSize());
		  tcBehaviour.setMotherId(this.getMotherId());
		  tcBehaviour.setMotherAgeAtBirth(this.getMotherAgeAtBirth());
		//  tcBehaviour.setGeneration(this.getGeneration());
		//  System.out.println("Divmmmmmmm============"+ this.getLastDivisionEventTime());
		  tcBehaviour.setLdEventTime(this.getLastDivisionEventTime());
		  tcBehaviour.setWebServiceAddress(this.getWebServiceAddress());
		  tcBehaviour.setUseTabulator(this.isUseTabulator());
		  Connection con = pool.getCoonection();
		  tcBehaviour.setCon(con);			 
		  tcBehaviour.processData(simMsg);	
		//  pool.closeConnection(con);	
	}
	
	 public String initialise(float gTime, Connection con) {		  
		  SimulationRequestMessage simMsg;	
		 // float gTime = ccMsg.getGlobalTime();
		  System.out.println("My Time: "+gTime+" My Id: "+this.getAgentId());

		/*  String model = this.loadModel(this.getParamData().getModelFile());		 
		  tcInputData = new TimeCourseInputData(model);  */
		  if(gTime==0.0f){			  
		//	  this.setTcInputData(this.prepareTCInputData(this.paramData.getModelFile()));
		  this.tcInputData = this.prepareTCInputData(this.paramData.getModelFile());
		  this.tcInputData.setCellBirthSize(this.getBirthSize());
		  this.tcInputData.setCurrentGlucose(this.currentGlucose);
		  this.tcInputData.setChangeVariableIdsValuesMap(this.paramData.getChangeVariableIdsValuesMap());
		  this.tcInputData.setBiomassVariable(this.paramData.getBiomassVariable());		
		  this.tcInputData.setPartitionFunction(this.paramData.getPartitionFunction());
		  this.tcInputData.setEventVariable(this.paramData.getEventVariable());
		  this.tcInputData.setEventVariableValue(this.paramData.getEventVariableValue());
		  this.tcInputData.setNutrientModelId(this.paramData.getNutrientModelId());
		  this.tcInputData.setBudVariable(this.paramData.getBudVariable());
		  this.tcInputData.setEnvironmentVolume(this.paramData.getEnvironmentVolume());
		  this.tcInputData.exstractInitialCondition();
		  }else{
			//  System.out.println("===========Ia here==============");
			  this.tcInputData.changeSInitialCondition(this.getEventData().getStateAtEvent(), this.getEventData().getMetaData(), this.isDaughterCell, this.isDivided);
		  }	

		  simMsg = new SimulationRequestMessage(this.getAgentId(), null, "", this.tcInputData.getModel(), gTime);	
		
		  simMsg.setnTimeSteps(this.paramData.getnTimeSteps());
		  simMsg.setStepSize(this.paramData.getStepSize());
		  simMsg.setTolerance(this.paramData.getTolerance()); 
		  
		  simMsg.setActiveVariableIds(this.paramData.getActiveVariableIds());
		  simMsg.setBiomassVariable(this.paramData.getBiomassVariable());
		  simMsg.setEventVariable(this.paramData.getEventVariable());
		  simMsg.setEventVariableValue(this.paramData.getEventVariableValue());
		  simMsg.setPartitionFunction(this.paramData.getPartitionFunction());
		  simMsg.setGLUConsumeId(this.paramData.getGLUConsumeId());
		  
		  simMsg.setStepCounter(this.getStepCounter());

		  simMsg.setsMatrixRow((this.getTcInputData().getsMatrixRow()));
		  simMsg.setsMatrixCol((this.getTcInputData().getsMatrixCol()));
		  simMsg.setnAssignmentSpecies(this.getTcInputData().getnAssignmentSpecies());
		  simMsg.setnConstantSpecies(this.getTcInputData().getnConstantSpecies());
		  simMsg.setnReactionSpecies(this.getTcInputData().getnReactionSpecies());
		  
		  simMsg.setnSpecies(this.getTcInputData().getnSpecies());	    
		  simMsg.setSpeciesInitialCondition(this.getTcInputData().getSpeciesInitialCondition());	   
		  simMsg.setSpeciesId(this.getTcInputData().getSpeciesId());		  
		  if(this.isDaughterCell()){
			  simMsg.setCellType("D");
		  }else{
			  simMsg.setCellType("P");
		  }
		  
		//run time course
		  TimeCourseBehaviour tcBehaviour = new TimeCourseBehaviour();
		  tcBehaviour.setAgentId(this.getAgentId());
		//  tcBehaviour.setGeneration(this.getGeneration());
		  tcBehaviour.setLdEventTime(this.getLastDivisionEventTime());
		  tcBehaviour.setBirthSize(this.getBirthSize());	
		  tcBehaviour.setMotherId(this.getMotherId());
		  tcBehaviour.setMotherAgeAtBirth(this.getMotherAgeAtBirth());
		  tcBehaviour.setWebServiceAddress(this.getWebServiceAddress());
		  tcBehaviour.setUseTabulator(this.isUseTabulator());
		  
		  tcBehaviour.setCon(con);	 
		  String metaData = tcBehaviour.processStateData(simMsg);
		//  pool.closeConnection(con);
		  return metaData;	
		  
		//  eventData = tcBehaviour.processEventData(simMsg);	
		//  return eventData.getMetaData();		  

	  }
	
	 public String loadModel(String pathName){
			FileDataManager fileManager = new FileDataManager();			
			return fileManager.loadModel(pathName);		
		}
	 
	/* public void saveModel(String pathName, String model){
			FileDataManager fileManager = new FileDataManager();	
				
		}*/
	  
	//prepare the time course input data for cell agent
		public TimeCourseInputData prepareTCInputData(String modelFile){
			TimeCourseInputData tcInputData = new TimeCourseInputData(this.loadModel(modelFile));		
			tcInputData.setTolerance(this.paramData.getTolerance());		
			tcInputData.setnTimeSteps(this.paramData.getnTimeSteps());
			tcInputData.setStepSize(this.paramData.getStepSize());	
			
			 tcInputData.setChangeVariableIdsValuesMap(this.paramData.getChangeVariableIdsValuesMap());		
			 tcInputData.setBiomassVariable(this.paramData.getBiomassVariable());	
			 tcInputData.setBudVariable(this.paramData.getBudVariable());
			 tcInputData.setNutrientModelId(this.paramData.getNutrientModelId());
			 tcInputData.setPartitionFunction(this.paramData.getPartitionFunction());
			return tcInputData;
		}
}
