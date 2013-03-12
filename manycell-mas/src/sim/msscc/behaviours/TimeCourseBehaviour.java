package sim.msscc.behaviours;

import java.sql.Connection;
import java.text.DecimalFormat;

import org.copasi.utils.FileProcessor;

import sim.jnative.PSConnector;
import sim.msscc.data.DBDataManager;
import sim.msscc.data.DatabaseConnectionPool;
import sim.msscc.data.EventData;
import sim.msscc.data.SimulationRequestMessage;
import sim.msscc.data.StateData;
import sim.msscc.data.TimeCourseSimulationResult;


public class TimeCourseBehaviour {
	
	private PSConnector psconnector = new PSConnector();	
	private DBDataManager dataManager = new DBDataManager();
	private Connection con;
	SimulationRequestMessage simMsg;
	private float ldEventTime;
	private String agentId;	
	private int cellAge;
	private float birthSize;
	private int motherAgeAtBirth;
	private String motherId;
//	private int generation;
	boolean useTabulator = false;
	private String webServiceAddress; //= "http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService";			
		
	public String getWebServiceAddress() {
		return webServiceAddress;
	}

	public boolean isUseTabulator() {
		return useTabulator;
	}

	public void setUseTabulator(boolean useTabulator) {
		this.useTabulator = useTabulator;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public DBDataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DBDataManager dataManager) {
		this.dataManager = dataManager;
	}
	

	public void setWebServiceAddress(String webServiceAddress) {
		this.webServiceAddress = webServiceAddress;
	}	
	
	public int getMotherAgeAtBirth() {
		return motherAgeAtBirth;
	}

	public void setMotherAgeAtBirth(int motherAgeAtBirth) {
		this.motherAgeAtBirth = motherAgeAtBirth;
	}

	public String getMotherId() {
		return motherId;
	}

	public void setMotherId(String motherId) {
		this.motherId = motherId;
	}

	public float getBirthSize() {
		return birthSize;
	}

	public void setBirthSize(float birthSize) {
		this.birthSize = birthSize;
	}

	public int getCellAge() {
		return cellAge;
	}

	public void setCellAge(int cellAge) {
		this.cellAge = cellAge;
	}

	public SimulationRequestMessage getSimMsg() {
		return simMsg;
	}

	public void setSimMsg(SimulationRequestMessage simMsg) {
		this.simMsg = simMsg;
	}

	public float getLdEventTime() {
		return ldEventTime;
	}

	public void setLdEventTime(float ldEventTime) {
		this.ldEventTime = ldEventTime;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}	

	public void cleanUPDB(){
		//dataManager.cleanUp();
		if(con!=null){
			try {				
				con.close();			
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}

	/** 
	 * Run the Time Course simulation using the PSConnector object 
	 */
	public void runTCSimulator() {
		int simulatorId;
		if (this.isUseTabulator()) {
			simulatorId = 2;
		} else {
			simulatorId = 1;
		}
		long res = psconnector.initSimulator(simulatorId);
	//	int res = psconnector.initSimulator(simulatorId);
		
		double[] sInit = simMsg.getSpeciesInitialCondition(); 		
		String[] sId = simMsg.getSpeciesId(); 	
		//System.out.println(res);		
		psconnector.runTCSimulatior(webServiceAddress, simMsg.getModel(), "test", simMsg.getTolerance(), sId, sInit, simMsg.getStepSize(), 
				simMsg.getnTimeSteps(), simMsg.getsMatrixRow(), simMsg.getsMatrixCol(),simMsg.getnAssignmentSpecies(), simMsg.getnConstantSpecies(), simMsg.getnReactionSpecies(), res);
	
		/*FileProcessor fileProc = new FileProcessor();
					fileProc.saveModelInSpecifiedDirectory(simMsg.getModel(), 
							"/usr/local/user-workspace/models/", "modelMotherTesttt.xml");*/
		//System.out.println(psconnector.getuModel());		
	}
	
	
	/** 
	 * Process and store the agent and simulation data in database using the dataManager object. 
	 * DataManager object must be created by the calling cell agent
	 * @param simMsg, gives information or metadata about the simulation message request
	 */
	public void processData(SimulationRequestMessage simMsg){
		this.simMsg = simMsg;	
		dataManager.setConnection(this.con);
		
		float gTime;			
		//	String agentId = agent.getAgentId();
		//	float ldEventTime = agent.getLastDivisionEventTime();			
			gTime = simMsg.getGlobalTime();	
			
			
			runTCSimulator();					
			String simResultString = psconnector.getSimResult();
			TimeCourseSimulationResult simResult = new TimeCourseSimulationResult(simResultString, simMsg);
			
		//	gTime = simResult.getParentDivisionTime() + simMsg.getnTimeSteps()*simMsg.getStepSize();
			
			float cycleTime = 0.0f;
			
			if(simResult.isDivided())cycleTime = simResult.getDivisionTime() + ((simMsg.getStepCounter())*simMsg.getnTimeSteps()*simMsg.getStepSize()); // + simMsg.getnTimeSteps()*simMsg.getStepSize();
		
	//		if(simResult.isDivided())cycleTime = simResult.getDivisionTime() + ((simMsg.getStepCounter())*simMsg.getStepSize()); // + simMsg.getnTimeSteps()*simMsg.getStepSize();
			
	//		System.out.println("CcyTiem:"+cycleTime+"Conter=== "+simMsg.getStepCounter() + "===Valeu=="+ (simMsg.getStepCounter()*simMsg.getnTimeSteps()*simMsg.getStepSize()));
		//	float divTime = this.getLdEventTime(); // + simResult.getDivisionTime(); // simMsg.getLastDivisionTime(); //this.getLdEventTime();
			float eventTime = this.getLdEventTime();
		//	if(!simResult.isDivided())eventTime = eventTime + simMsg.getStepCounter()*simMsg.getnTimeSteps()*simMsg.getStepSize();
	//		float eventTime = this.getLdEventTime() + simMsg.getStepCounter()*simMsg.getStepSize(); // +cycleTime; //TODO - need to check
			//	if(this.getCellAge()==0){divTime = cycleTime ;}
	//		divTime = this.getLdEventTime() + cycleTime;
		//	int storeTCID = dataManager.storeTimeCourseResult(simResultString);
						
			if(simMsg.getCellType().equals("D") && simMsg.isNew()){
				dataManager.storeAgentData(this.getAgentId(), this.getBirthSize(), this.getCellAge(), this.getMotherId(), this.getMotherAgeAtBirth());
			}else{
				dataManager.updateAgentData(this.getAgentId(), this.getCellAge());
			}
		//	dataManager.storeEventData(this.getAgentId(), "division",divTime, gTime, simResult.getStateAtEvent(), simResult.getGLUconsumed(), storeTCID);
			
			//store event data			
			if(!simResult.isDivided()){	
				dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), eventTime, simMsg.getStepCounter(), this.getCellAge(),"NO");
				
			//	dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), simMsg.getStepCounter(), "NO", storeTCID);
			}else{
				dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), eventTime, simMsg.getStepCounter(), this.getCellAge(), "YES");				
				dataManager.storeEventData(this.getAgentId(), "division", eventTime, gTime, simResult.getStateAtEvent(), cycleTime, simMsg.getCellType(), simResult.getSizeAtDivision(), this.getCellAge());

			}

			/*if(!simResult.isDivision()){
				
				dataManager.storeEventData(this.getAgentId(), "division",divTime, gTime, simResult.getStateAtEvent(),simResult.getSimState(), simResult.getGLUconsumed(), cycleTime, simMsg.getCellType(), simResult.getSizeAtDivision(), storeTCID);
				}else{
					dataManager.storeEventData(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), storeTCID);
					
				}*/
			//store the observations			
			//change the global time to the eventtime for the observations data, but first convert it to nearest tenth			
			if(eventTime!=0){
				int x = Math.round(eventTime);
				x = x + (int)simMsg.getStepSize()/2;
				x = x/(int)simMsg.getStepSize();
				x = x*(int)simMsg.getStepSize();
				gTime = x; // + simMsg.getStepSize();
			//	gTime = (Math.round(eventTime/simMsg.getStepSize())*simMsg.getStepSize())+simMsg.getStepSize();
				}
		//	if(simResult.isDivided())gTime = gTime+simMsg.getStepSize();
			System.out.print("This is Test "+gTime); 
			dataManager.storeObservations(this.getAgentId(), simResult.getActiveVariableIds(), simResult.getActiveVariableValues(), simResult.getFastTime(), gTime);
				
			
			simMsg.setHeaderString(simResult.getHeaderString());
			
			//testing data manager
		//	dataManager.retrieveEventData(agentId, "division",gTime);			
			System.out.println("Division Time Event: "+simResult.getDivisionTime());	
		//	return simMsg;
			dataManager.cleanUp();
			cleanUPDB();
	}
	
	/**
	 * Processing the state data from the simulation results stored in the database
	 * @param simMsg, information or metadata about the message
	 * @return String headerString for the state data
	 */
	public String processStateData(SimulationRequestMessage simMsg){
		this.simMsg = simMsg;		
		dataManager.setConnection(this.getCon());		
	
		float gTime;			
	//		String agentId = agent.getAgentId();
		//	float ldEventTime = agent.getLastDivisionEventTime();			
			gTime = simMsg.getGlobalTime();			
			
			runTCSimulator();					
			String simResultString = psconnector.getSimResult();
			TimeCourseSimulationResult simResult = new TimeCourseSimulationResult(simResultString, simMsg);
			float cycleTime = simResult.getDivisionTime(); //+ (simMsg.getStepCounter()*simMsg.getnTimeSteps()*simMsg.getStepSize()); // + simMsg.getnTimeSteps()*simMsg.getStepSize();
		//	float divTime = this.getLdEventTime() + cycleTime;
			float eventTime =  0.0f; //cycleTime;
		//	float cellSize = simResult.getSizeAtDivision();
			//int storeTCID = dataManager.storeTimeCourseResult(simResultString);
			//dataManager.storeEventData(this.getAgentId(), "division",divTime, gTime, simResult.getStateAtEvent(), simResult.getGLUconsumed(), storeTCID);
			
			if(simMsg.getCellType().equals("D")){
				dataManager.storeAgentData(this.getAgentId(), this.getBirthSize(), this.getCellAge(), this.motherId, this.motherAgeAtBirth);
			}else{
				dataManager.updateAgentData(this.getAgentId(), this.getCellAge());
			}
			
			//store event data			
			if(!simResult.isDivided()){	
			//	dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), simMsg.getStepCounter(), "NO", storeTCID);
				
				dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), eventTime, simMsg.getStepCounter(), this.getCellAge(), "NO");
			}else{
				dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), eventTime, simMsg.getStepCounter(), this.getCellAge(), "YES");				
				dataManager.storeEventData(this.getAgentId(), "division", eventTime, gTime, simResult.getStateAtEvent(), cycleTime, simMsg.getCellType(), simResult.getSizeAtDivision(), this.getCellAge());

			}
			
			/*if(simResult.getStateAtEvent()!=null){
			dataManager.storeEventData(this.getAgentId(), "division",divTime, gTime, simResult.getStateAtEvent(),simResult.getSimState(), simResult.getGLUconsumed(), cycleTime, simMsg.getCellType(), simResult.getSizeAtDivision(), storeTCID);
			}else{
				dataManager.storeEventData(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), storeTCID);
				
			}*/
			dataManager.storeObservations(this.getAgentId(), simResult.getActiveVariableIds(), simResult.getActiveVariableValues(), simResult.getFastTime(), gTime);
		//	simMsg.setHeaderString(simResult.getHeaderString());
			
			//testing data manager
		//	dataManager.retrieveEventData(agentId, "division",gTime);			
			System.out.println("Division Time Event: "+simResult.getDivisionTime());			
			
		/*	for(int i = 0; i<simResult.getsInit().length;i++){
				System.out.println("Initial condition: "+simResult.getsInit()[i]);
			}*/		
				
			dataManager.cleanUp();
		//	this.cleanUPDB();
		return	simResult.getHeaderString();
		
	}
	
	/**
	 * Processing the event data from the simulation results stored in the database
	 * @param simMsg, information or metadata about the message
	 * @return eventData
	 */
	public EventData processEventData(SimulationRequestMessage simMsg){
		this.simMsg = simMsg;	
		
		float gTime;			
	//		String agentId = agent.getAgentId();
		//	float ldEventTime = agent.getLastDivisionEventTime();			
			gTime = simMsg.getGlobalTime();			
			
			runTCSimulator();					
			String simResultString = psconnector.getSimResult();
			TimeCourseSimulationResult simResult = new TimeCourseSimulationResult(simResultString, simMsg);
			float cycleTime = simResult.getDivisionTime();
			float eventTime = 0.0f; // cycleTime;
		//	float divTime = this.getLdEventTime() + cycleTime;
		//	float cellSize = simResult.getSizeAtDivision();
			int storeTCID = dataManager.storeTimeCourseResult(simResultString);
			//dataManager.storeEventData(this.getAgentId(), "division",divTime, gTime, simResult.getStateAtEvent(), simResult.getGLUconsumed(), storeTCID);
			
			if(simMsg.getCellType().equals("D")){
				dataManager.storeAgentData(this.getAgentId(), this.getBirthSize(), this.getCellAge(), this.getMotherId(), this.getMotherAgeAtBirth());
			}else{
				dataManager.updateAgentData(this.getAgentId(), this.getCellAge());
			}
			
			//store event data			
			if(!simResult.isDivided()){	
				dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), eventTime, simMsg.getStepCounter(), this.getCellAge(), "NO");
				
			//	dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), simMsg.getStepCounter(), "NO", storeTCID);
			}else{
				dataManager.storeCellState(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), eventTime, simMsg.getStepCounter(), this.getCellAge(), "YES");				
				dataManager.storeEventData(this.getAgentId(), "division", eventTime, gTime, simResult.getStateAtEvent(), cycleTime, simMsg.getCellType(), simResult.getSizeAtDivision(), this.getCellAge());

			}
			
			/*if(simResult.getStateAtEvent()!=null){
			dataManager.storeEventData(this.getAgentId(), "division",divTime, gTime, simResult.getStateAtEvent(),simResult.getSimState(), simResult.getGLUconsumed(), cycleTime, simMsg.getCellType(), simResult.getSizeAtDivision(), storeTCID);
			}else{
				dataManager.storeEventData(this.getAgentId(), gTime, simResult.getSimState(), simResult.getGLUconsumed(), storeTCID);
				
			}*/
			dataManager.storeObservations(this.getAgentId(), simResult.getActiveVariableIds(), simResult.getActiveVariableValues(), simResult.getFastTime(), gTime);
		//	simMsg.setHeaderString(simResult.getHeaderString());
			
			//testing data manager
		//	dataManager.retrieveEventData(agentId, "division",gTime);			
			System.out.println("Division Time Event: "+simResult.getDivisionTime());
			
			EventData eventData = new EventData(gTime, eventTime, "division", simResult.getStateAtEvent(), this.getAgentId(), storeTCID);
			eventData.setCycleTime(cycleTime);
			eventData.setMetaData(simResult.getHeaderString());
			
			
		/*	for(int i = 0; i<simResult.getsInit().length;i++){
				System.out.println("Initial condition: "+simResult.getsInit()[i]);
			}*/		
			
		return	eventData;
		
	}
	
	/*public void storeObservations(String []fastTime, String activeVariables, float[][] activeVariableValues, float gTime){
		
		
	}*/

}
