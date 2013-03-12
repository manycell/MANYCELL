package sim.msscc.agents;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.XmlOptions;
import org.copasi.utils.FileProcessor;

import sim.msscc.data.CellCycleEventTimeMessage;
import sim.msscc.data.DBDataManager;
import sim.msscc.data.DataManagerInterface;
import sim.msscc.data.DatabaseConnectionPool;
import sim.msscc.data.EventData;
import sim.msscc.data.FileDataManager;
import sim.msscc.data.SimulationRequestMessage;
import sim.msscc.data.SimulatorParameterData;
import sim.msscc.data.StateData;
import sim.msscc.data.TimeCourseInputData;
import sim.msscc.data.TimeCourseSimulationResult;
import sim.msscc.utils.BlockThreadPoolExecutor;

//import for samasime xml configuration file
import org.manycell.config.CellularDocument.Cellular.Cell;
import org.manycell.config.ManycellDocument.Manycell;
import org.manycell.config.*;
import org.manycell.config.EnvironmentDocument.Environment;
import org.manycell.config.EnvironmentDocument.Environment.Nutrients;
import org.manycell.config.EnvironmentDocument.Environment.Nutrients.Nutrient;
import org.manycell.config.CellularDocument.Cellular;
import org.manycell.config.CellularDocument.Cellular.Cells;
import org.manycell.config.CellularDocument.Cellular.Cells.ChangeModelInitialConditons.Variable;
import org.manycell.config.DatabaseDocument.Database;
import org.manycell.config.SimulationDocument.Simulation;
import org.manycell.config.SimulationDocument.Simulation.ISAT;
import org.manycell.config.SimulationDocument.Simulation.ParallelExecution;
import org.manycell.config.SubCellularDocument.SubCellular;
import org.manycell.config.SubCellularDocument.SubCellular.Model;
import org.manycell.config.SubCellularDocument.SubCellular.Model.ActiveVariables;
import org.manycell.config.SubCellularDocument.SubCellular.Model.Events;
import org.manycell.config.SubCellularDocument.SubCellular.Model.Events.Event;
import org.manycell.config.SubCellularDocument.SubCellular.Model.TimeCourseSimulation;


/**
 * The <code>MasterAgent</code> class implements the global master
 * for culture simulator.
 *
 * @author Joseph O. Dada
 * *
 * @copyright
 * The University of Manchester
 *
 */
/**
 * @author dada
 *
 */
/**
 * @author dada
 *
 */
public class MasterAgent extends Agent {
	private static MasterAgent instance = null;
	//private SimulatorAgent simAgent = new SimulatorAgent("simAgent");
	private String webServiceAddress = null;
	private List <CellAgent> cellAgents;
	
	private float simDuration;
	private float simTime = 0.0f;
	private boolean allowParallelExecution = false;
	private int numberOfProcessors;
	
	CellCycleEventTimeMessage ccMsg;
	int agentCounter = 0;
	private double currentGlucose; 
	private double initialGlucose = 0.0;
	private double cellVolume = 1.0e-10;
	private double enviromentVolume;
	private double sufficientGLU = 10e-10;
	private int maxAgentAge;
	private String [] activeVariables = null;
	private int noOfSeededCells = 0;
	private Database database = null; // the database use for the storage of simulation results 
	
	private Hashtable cellAgentTable = new Hashtable();
	
	private DatabaseConnectionPool pool = new DatabaseConnectionPool();
	
	//private String configFile = null;	
	private String multiscaleMXMLFile = null;	
	
	long startTime; // = System.currentTimeMillis();

	//time corse data
	float stepSize; 
	int nTimeSteps; 
	
	//the generation of cells
	//int generation;

	float tabulatorTol; // = 0.03f; //former value=0.03	
	boolean usTabulator = false;
	protected Random random = new Random();	
	
//	protected float randDiviation = 0.0001f;

	String masterModelFile; // = "/usr/local/user-workspace/models/MASTERmodelMother.xml";
	
	String metaData = null;
	//agent that do the simulation
	SimulatorAgent simAgent = new SimulatorAgent("simAgent");	

	private SimulatorParameterData paramData = new SimulatorParameterData();;

	/**
	 * Creates a <code>MasterAgent</code> object.
	 * 
	 * Note: can't be used as a Java Bean without public constructor
	 * 
	 */
	protected MasterAgent() {
		this("Master");
	}

	public MasterAgent(String agentId) {
		super(agentId);

	}	
	/**
	 * Creates a <code>MasterAgent</code> object.
	 * 
	 * with certain number of cell seed in culture
	 * 
	 */
	public MasterAgent(String agentId, String multiscaleMXMLFile) {
		super(agentId);		
		this.multiscaleMXMLFile = multiscaleMXMLFile;		
	}		
		
	
		
	/*public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}*/	
	
	
	public float getStepSize() {
		return stepSize;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public int getNoOfSeededCells() {
		return noOfSeededCells;
	}

	public void setNoOfSeededCells(int noOfSeededCells) {
		this.noOfSeededCells = noOfSeededCells;
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

	public float getTabulatorTol() {
		return tabulatorTol;
	}

	public String[] getActiveVariables() {
		return activeVariables;
	}

	public void setActiveVariables(String[] activeVariables) {
		this.activeVariables = activeVariables;
	}

	public void setTabulatorTol(float tabulatorTol) {
		this.tabulatorTol = tabulatorTol;
	}

	public boolean isAllowParallelExecution() {
		return allowParallelExecution;
	}

	public String getMultiscaleMXMLFile() {
		return multiscaleMXMLFile;
	}

	public void setMultiscaleMXMLFile(String multiscaleMXMLFile) {
		this.multiscaleMXMLFile = multiscaleMXMLFile;
	}

	public void setAllowParallelExecution(boolean allowParallelExecution) {
		this.allowParallelExecution = allowParallelExecution;
	}

	public int getNumberOfProcessors() {
		return numberOfProcessors;
	}

	public void setNumberOfProcessors(int numberOfProcessors) {
		this.numberOfProcessors = numberOfProcessors;
	}

	public double getCellVolume() {
		return cellVolume;
	}

	public void setCellVolume(double cellVolume) {
		this.cellVolume = cellVolume;
	}
	

	public double getEnviromentVolume() {
		return enviromentVolume;
	}

	public void setEnviromentVolume(double enviromentVolume) {
		this.enviromentVolume = enviromentVolume;
	}

	public String getMasterModelFile() {
		return masterModelFile;
	}

	public void setMasterModelFile(String masterModelFile) {
		this.masterModelFile = masterModelFile;
	}

	public boolean isUsTabulator() {
		return usTabulator;
	}

	public void setUsTabulator(boolean usTabulator) {
		this.usTabulator = usTabulator;
	}

	public String getWebServiceAddress() {
		return webServiceAddress;
	}

	public void setWebServiceAddress(String webServiceAddress) {
		this.webServiceAddress = webServiceAddress;
	}	

	public double getCurrentGlucose() {
		return currentGlucose;
	}

	public void setCurrentGlucose(double currentGlucose) {
		this.currentGlucose = currentGlucose;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
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

	/**
	 * Initializes the master agent to begin processing. It initializes all the parameters and the cell agents
	 * using the multiscale model specified in the MXML model file
	 */
	public void initialize() {
		// initialise system variables
		ManycellDocument simDoc = null;
	//	this.setGeneration(1);
		Cells cells = null;
		Cell []cell = null;
		try {
			File file = new File(this.getMultiscaleMXMLFile());

			XmlOptions options = new XmlOptions();
			Map<String, String> substitutes = new HashMap<String, String>();
			substitutes.put("", "http://www.manycell.org/sim-config-schema");
			options.setLoadSubstituteNamespaces(substitutes);
			simDoc = ManycellDocument.Factory.parse(file, options);
			Manycell sim = simDoc.getManycell();

			// initialise the model and time course simulator for cells
			SubCellular cellModel = sim.getSubCellular();
			Model [] model = cellModel.getModelArray();
			paramData.setGLUConsumeId(model[0].getNutrientConsumptionVariable().getModelId());
			this.setMasterModelFile(model[0].getFileNameWithPath());
			TimeCourseSimulation tcSim = model[0].getTimeCourseSimulation();
			this.webServiceAddress = tcSim.getWebServiceAddress();
			this.stepSize = tcSim.getStepSize();
			this.nTimeSteps = tcSim.getNumberOfSteps();
			
			this.paramData.setPartitionFunction(model[0].getCellSizePartitionVariable().getModelId());
			if(model[0].getBudVariable()!=null)paramData.setBudVariable(model[0].getBudVariable().getModelId());
			Events events = model[0].getEvents();
			Events.Event []event = events.getEventArray();
			
			for(int ie=0; ie<event.length; ie++){				
				if(event[ie].getName().equals("division")){
				this.paramData.setEventVariable(event[ie].getModelId());				
				this.paramData.setEventVariableValue(event[ie].getValue());
				}
			}
			
			
			//set the Ids of the variable to monitor
			ActiveVariables activeVariable = model[0].getActiveVariables();			
			ActiveVariables.Variable []variable = activeVariable.getVariableArray();
			activeVariables = new String[variable.length]; 
			for(int i=0; i<variable.length; i++){
				this.activeVariables[i] = variable[i].getModelId();
			}
			paramData.setActiveVariableIds(this.activeVariables);		
			
			
			// initialise from the environment
			Environment env = sim.getEnvironment();
			
			this.setEnviromentVolume(env.getVolume().getSize()*this.converterUnit(env.getVolume().getUnit()));
			Nutrients nutrients = env.getNutrients();
			Nutrient[] nutrient = nutrients.getNutrientArray();
			paramData.setEnvironmentVolume(this.getEnviromentVolume());

		//	if (nutrient[0].getIsAmount()) { TODO
				paramData.setNutrientModelId(nutrient[0].getModelId());	
			//	this.setCurrentGlucose(nutrient[0].getValue());
				this.setCurrentGlucose(nutrient[0].getValue()/this.getEnviromentVolume());
				this.initialGlucose = this.getCurrentGlucose();
				String nutrientName = nutrient[0].getName();				
	//		}
		
		//initialise the main culture simulation
		Simulation simulation = sim.getSimulation();
		this.setSimDuration(simulation.getDuration());
		this.maxAgentAge = simulation.getMaxCellGeologicalAge();
		System.out.print(this.maxAgentAge);	
	
		if(simulation.getUseISAT()){
			ISAT isat = simulation.getISAT();
			this.usTabulator = true;
			this.tabulatorTol = isat.getTolerance();
		}else if(simulation.getAllowParallelExecution()){
			ParallelExecution pExecution = simulation.getParallelExecution();
			this.setAllowParallelExecution(true);
			this.setNumberOfProcessors(pExecution.getNumberOfProcessors());
		}		
		
		//set the database parameters
		this.database = sim.getDatabase();
		pool.setServerName(database.getServerName());
		pool.setPort(database.getPort());		
		pool.setDatabaseName(database.getDatabaseName());	
		pool.setUser(database.getUserName());
		pool.setPassword(database.getPassword());
		pool.setInitialConnections(database.getInitialConnections());
		pool.setMaxConnections(database.getMaxConnections());	
		
		//initialise the cells			
		
		//cells are specified in group
		cells = sim.getCellular().getCells();
		if(cells!=null){
			this.noOfSeededCells = cells.getTotal();
		String unit = cells.getCellVolumeUnit();		
		this.setCellVolume(cells.getAverageCellVolume()*this.converterUnit(cells.getCellVolumeUnit()));
		}else{
			//cells are individually specified
			cell = sim.getCellular().getCellArray();
			this.noOfSeededCells = cell.length;
			cells = null;
		}
		
		}catch (Exception e){
			e.printStackTrace();
		}		
	
		//clean the database				
	/*	dataManager.dropORCreateTable("eventdata", true);
		dataManager.dropORCreateTable("timecoursedata", true);
		dataManager.dropORCreateTable("eventdata", false);	
		dataManager.dropORCreateTable("timecoursedata", false);	

	//	simAgent.initialize();
		simAgent.addAgentEventListener(this);


		cellAgents = new ArrayList<CellAgent>();		
		//	simAgent.initialize();
	//	this.addAgentEventListener(simAgent);

		paramData = new SimulatorParameterData();
		paramData.setModelFile(masterModelBudFile);
		paramData.setnTimeSteps(this.nTimeSteps);
		paramData.setStepSize(this.stepSize);
		paramData.setTolerance(this.tabulatorTol);
		simAgent.initialize();
        this.addAgentEventListener(simAgent);

			this.proceesInitialDivisionTimeEvent();	
		//	proceesInitialDivisionTimeEventNew();
	//	this.proceesInitialDirectDivisionTimeEvent(simTime);
	//	this.processDirectEvent(simTime);

	////		this.initialCellAgentCells(cellSeed);					
		//	cellAgentTable.clear();
		this.startAgentProcessing();	*/	
		
		//this is new version
		pool.initPool();
		Connection con = pool.getCoonection();
		DBDataManager dataManager = new DBDataManager();
		dataManager.setConnection(con);
		//dataManager.initPool(pool, true);
		
		dataManager.dropORCreateTable("cellagents", true);
		dataManager.dropORCreateTable("cellstates", true);
		dataManager.dropORCreateTable("eventdata", true);
		dataManager.dropORCreateTable("timecoursedata", true);	
		dataManager.dropORCreateTable("observations", true);	
		dataManager.dropORCreateTable("aresultsummary", true);
		
		dataManager.dropORCreateTable("cellagents", false);
		dataManager.dropORCreateTable("cellstates", false);
		dataManager.dropORCreateTable("eventdata", false);	
		dataManager.dropORCreateTable("timecoursedata", false);	
		dataManager.dropORCreateTable("observations", false);	
		dataManager.dropORCreateTable("aresultsummary", false);
		
	//	dataManager.cleanUp();
		//String modelFileName = masterModelBudFile;
	//	SimulatorParameterData paramData = new SimulatorParameterData();
		paramData.setModelFile(masterModelFile);			
		paramData.setnTimeSteps(this.nTimeSteps);
		paramData.setStepSize(this.stepSize);
		paramData.setTolerance(this.tabulatorTol);		
		
		startTime = System.currentTimeMillis();
		this.initialiseAgent(cells, cell);
		this.process();
		dataManager.cleanUp();
		pool.closeConnection(con);
	}

	/**
	 * Provides the synchronous processing done by this agent.
	 */
	public void process() {		
		String event = "division";	
		//database manager object
		DBDataManager dataManager = new DBDataManager();
		Connection conn = pool.getCoonection();
		dataManager.setConnection(conn);
		
		//int generation = dataManager.retrieveMaxAgentGeneration();
		// dataManager.initPool(pool, false);
		for (simTime = 0.0f; simTime < simDuration; simTime = simTime
				+ (stepSize * nTimeSteps)) {
			
			// get the glucose consumed by all cells in this time step

			DBDataManager conDataManager = new DBDataManager();
			Connection gluConn = pool.getCoonection();
			conDataManager.setConnection(gluConn);
			
			float sumOfGLUConsumed = conDataManager.getSumOfGLUConsumed(simTime);				
			conDataManager.cleanUp();
			pool.closeConnection(gluConn);
			
			//claculate the execution time at each iteration
			long presentTime = System.currentTimeMillis();				
			double usedTime = (presentTime-startTime)/1000; //60000 change to second
			
			DBDataManager summaryDataManager = new DBDataManager();
			Connection sConn = pool.getCoonection();
			summaryDataManager.setConnection(sConn);
	//		double currentGLUConc = this.currentGlucose/this.reactorVolume;
			double sumGLUCons = sumOfGLUConsumed*this.cellVolume;
			summaryDataManager.storeAResultSummary(usedTime, simTime,this.getCurrentGlucose(), sumGLUCons, this.agentCounter);
			System.out.println("Current Glucose "+ this.getCurrentGlucose()+" "+paramData.getNutrientModelId()+" sumConsume "+sumGLUCons);
			summaryDataManager.cleanUp();
			pool.closeConnection(sConn);
			
	/*		if(this.getCurrentGlucose()<this.initialGlucose*0.2){
			//	this.stepSize=10;
				this.nTimeSteps=1;
			}*/
		//	generation = generation+1; // increase the generation 
			if (this.getCurrentGlucose() >= 0) { // this.sufficientGLU){

				// the queue object and the thread pool
				int dataFetchSize = 30;

				// parallel execution directive 
				BlockThreadPoolExecutor execSvc = null;
				if (this.isAllowParallelExecution()) {
					//create a queue object to handle job queue
					BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(this.getNumberOfProcessors()*2, true);						
					execSvc = new BlockThreadPoolExecutor(this.getNumberOfProcessors(), this.getNumberOfProcessors(), this.getNumberOfProcessors(), 500L, TimeUnit.MILLISECONDS, queue);
					
				}
				// database manager object
				// DBDataManager dataManager = new DBDataManager();
				// dataManager.initPool(pool, true);				
				//TODO
				
				//ResultSet rset = dataManager.fetchEventData(event, simTime);
				ResultSet rset = dataManager.fetchStateData(simTime, dataFetchSize);
				//	ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);
				//	ExecutorService execSvc = Executors.newFixedThreadPool(4);

				try{	
				//	System.out.println("ResultSet Fetch Size: "+rset.getFetchSize());	
					while(rset.next()){
						StateData stateData;
						float gTime = simTime+(stepSize*nTimeSteps);
						
						//test if there was a division
						if(rset.getString("isdivided").equals("NO")){
							stateData = new StateData(gTime,rset.getString("caid"));
							stateData.setSimState(rset.getString("state"));
						//	System.out.println("simState==="+rset.getString("state"));				
							//	float eventTime = eventData.getEventTime()+rset.getFloat("cycletime");
							//is old cell
							DBDataManager ageDataManager = new DBDataManager();
							Connection ageConn = pool.getCoonection();
							ageDataManager.setConnection(ageConn);
							int gAge = ageDataManager.retrieveAgentAge(stateData.getAgentId());							
							//EventData eventData = null;
						//	if(gAge>0)eventData = ageDataManager.fetchEventData("division", simTime, stateData.getAgentId());
							ageDataManager.cleanUp();
							pool.closeConnection(ageConn);

							//daughter cell is created only if cell age is less 30, cell stop division 
							// after it has divided 30 times					
							CellAgent oldAgent = new CellAgent(stateData.getAgentId());	
							float eventTime = rset.getFloat("eventtime") + this.nTimeSteps*this.stepSize*(rset.getFloat("stepCounter")+1); //TODO
							oldAgent.setLastDivisionEventTime(eventTime);							
							oldAgent.setParamData(paramData);					
							oldAgent.setMetaData(this.getMetaData());

							//oldAgent.setStateAtEvent(eventData.getStateAtEvent());
							oldAgent.setSimState(stateData.getSimState());
						//	oldAgent.setLastDivisionEventTime(eventData.getCycleTime());	
							oldAgent.setNewCell(false);
							
							if(gAge>0) //is no more a daughter cell
							oldAgent.setDaughterCell(false);
							
							oldAgent.setStepCounter(rset.getInt("stepcounter")+1);
							oldAgent.setgTime(gTime);							
							oldAgent.setSumOfGLUConsumed(sumOfGLUConsumed);	
							oldAgent.setCurrentGlucose(this.getCurrentGlucose());
							oldAgent.setgAge(gAge);
							
							oldAgent.setWebServiceAddress(this.getWebServiceAddress());
							oldAgent.setUseTabulator(this.isUsTabulator());
							oldAgent.setVolume(this.getCellVolume());
							oldAgent.setPool(pool);
							
							if (this.isAllowParallelExecution()) {
								execSvc.execute(oldAgent);
							} else {
								oldAgent.run();
							}

						}else if(rset.getString("isdivided").equals("YES")){					
							stateData = new StateData(gTime,rset.getString("caid"));
							stateData.setSimState(rset.getString("state"));

							DBDataManager eventDataManager = new DBDataManager();
							Connection eventConn = pool.getCoonection();
							eventDataManager.setConnection(eventConn);
							EventData eventData  = eventDataManager.fetchEventData("division", simTime, stateData.getAgentId());
							eventDataManager.cleanUp();
							pool.closeConnection(eventConn);
							//	EventData eventData = new EventData(gTime, stateData.getAgentId());	
							//get calculate the event time for both mother and daughter cell, event time of mother is the start event time for daughter
						//	float eventTime = eventData.getCycleTime() + eventData.getEventTime();
							//calculating the current time
							float eventTime = eventData.getCycleTime() -this.nTimeSteps*this.stepSize*(rset.getFloat("stepCounter")) + eventData.getEventTime(); //+eventData.getCycleTime();
							
						//	System.out.println("=========Event======="+eventTime +"==================");
						/*while(srset.next()){
								System.out.println(srset.getString("stateatevent"));
								eventData.setStateAtEvent(srset.getString("stateatevent"));
								//?
								eventTime = srset.getFloat("eventtime")+srset.getFloat("cycletime");
							}*/
							
							
							
							//eventData.setEventTime(rset.getFloat("cycletime"));
							
							//eventDataManager.cleanUp();
															
							//is old cell
							DBDataManager ageDataManager = new DBDataManager();
							Connection ageConn = pool.getCoonection();
							ageDataManager.setConnection(ageConn);
							int gAge = ageDataManager.retrieveAgentAge(stateData.getAgentId());
						//	int generation = ageDataManager.retrieveAgentGeneration(stateData.getAgentId());
							ageDataManager.cleanUp();
							pool.closeConnection(ageConn);

							//daughter cell is created only if cell age is less 30, cell stop division 
							// after it has divided 30 times
							if(gAge<=maxAgentAge){
								CellAgent oldAgent = new CellAgent(stateData.getAgentId());						
								oldAgent.setLastDivisionEventTime(eventTime);							
								oldAgent.setParamData(paramData);					
								oldAgent.setMetaData(this.getMetaData());

								//testing with state at end of simulation
								oldAgent.setStateAtEvent(eventData.getStateAtEvent());					
								oldAgent.setSimState(stateData.getSimState());
							//	oldAgent.setLastDivisionEventTime(eventData.getCycleTime());
								oldAgent.setgTime(gTime);
								oldAgent.setStepCounter(0);
								oldAgent.setDaughterCell(false);
								oldAgent.setNewCell(false);								
								oldAgent.setSumOfGLUConsumed(sumOfGLUConsumed);	
								oldAgent.setCurrentGlucose(this.getCurrentGlucose());
								oldAgent.setDivided(true);
								oldAgent.setgAge(gAge+1);
								oldAgent.setWebServiceAddress(this.getWebServiceAddress());
								oldAgent.setUseTabulator(this.isUsTabulator());
								oldAgent.setPool(pool);
								oldAgent.setVolume(this.getCellVolume());

								if (this.isAllowParallelExecution()) {
									execSvc.execute(oldAgent);
								} else {
									oldAgent.run();
								}

								//create new cell/home/dada/mssim_workspace-1/mssim_workspace
								String newAgentId = "CAID"+ (this.agentCounter);
								CellAgent newAgent = new CellAgent(newAgentId);
								newAgent.setMotherId(oldAgent.getAgentId());
								newAgent.setMotherAgeAtBirth(oldAgent.getgAge());
							//	newAgent.setGeneration(oldAgent.getGeneration()+gAge+1);
								newAgent.setParamData(paramData);
								
								//the first division event time for daughter cell is the last division event for mother cell plus stepsize
								newAgent.setLastDivisionEventTime(eventTime);
								newAgent.setMetaData(this.getMetaData());
								newAgent.setStateAtEvent(eventData.getStateAtEvent());
								//oldAgent.setSimState(eventData.getSimState());
								newAgent.setgTime(gTime);
								newAgent.setStepCounter(0);
								newAgent.setDaughterCell(true);	
								newAgent.setNewCell(true);	
								newAgent.setSumOfGLUConsumed(sumOfGLUConsumed);
								newAgent.setCurrentGlucose(this.getCurrentGlucose());
								
								//newAgent.setDivided(true); it should be false for new cell
								newAgent.setDivided(true);
								newAgent.setgAge(0);
								
								//newAgent.setGeneration(getGeneration());								
								newAgent.setWebServiceAddress(this.getWebServiceAddress());
								newAgent.setUseTabulator(this.isUsTabulator());
								newAgent.setPool(pool);
								newAgent.setVolume(this.getCellVolume());
								
								if (this.isAllowParallelExecution()) {
									execSvc.execute(newAgent);
								} else {
									newAgent.run();
								}
						//		System.out.println("No of Agent Now: "+this.agentCounter);
								
								if (this.isAllowParallelExecution()) {
									System.out.println("Queue size now: " + execSvc.getQueue().size());
								}
								this.agentCounter++;					

							}
						}

					}	

					if (this.isAllowParallelExecution()) {
						execSvc.shutdown();
						while (!execSvc.isTerminated()) {

						}
					}
					//Thread.currentThread().join();
					rset.close();
				}catch(Exception e) {
					e.printStackTrace();
				}				
				
				//this.setCurrentGlucose((this.getCurrentGlucose()*10e-3f - 10e-10f*sumOfGLUConsumed)/10e-3f);
				
				//only for Spiesser model
			//	this.setCurrentGlucose(this.getCurrentGlucose());
				
				//setting for main models				
				
				this.setCurrentGlucose((this.getCurrentGlucose()*this.getEnviromentVolume() - sumOfGLUConsumed*this.getCellVolume())/this.getEnviromentVolume());
				
				}else{
					simTime = this.getSimDuration() +10; //jump out of the loop and stop the agent if glucose is no more sufficient for the cells
					System.out.println("Simulator stops with current GLU "+ this.getCurrentGlucose()+ "less than the sifficient GLU"+this.sufficientGLU);
				//	this.stopAgentProcessing(); //stop the master agent processing
				}

			}
			dataManager.cleanUp();
			pool.closeConnection(conn);

	//	}	
	
		
	}	
	
	public void processOld() {		
		String event = "division";
		if(this.getCurrentGlucose()>=this.sufficientGLU){
		for(simTime=0.0f; simTime<simDuration; simTime=simTime+(stepSize*nTimeSteps)){
			
		//the queue object and the thread pool
			int dataFetchSize = 40;
			BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20, true);
			BlockThreadPoolExecutor execSvc = new BlockThreadPoolExecutor(4, 20, 15, 500L, TimeUnit.MILLISECONDS, queue);
			
			//database manager object
			DBDataManager dataManager = new DBDataManager();
			Connection fechCon = pool.getCoonection();
			dataManager.setConnection(fechCon);
			
			//get the glucose consumed by all cells in this time step
			float sumOfGLUConsumed = dataManager.getSumOfGLUConsumed(simTime);
			//dataManager.cleanUp();
			
			//ResultSet rset = dataManager.fetchEventData(event, simTime);
			ResultSet rset = dataManager.fetchEventData(simTime, dataFetchSize);
		//	ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);		
			
		//	ExecutorService execSvc = Executors.newFixedThreadPool(4);
			
			try{	
				System.out.println("ResultSet Fetch Size: "+rset.getFetchSize());	
				while(rset.next()){
					EventData eventData;
					float gTime = simTime+(stepSize*nTimeSteps);
					
					//test if there was a division
					if(rset.getString("event")==null){
						eventData = new EventData(gTime,rset.getString("caid"));
						eventData.setSimState(rset.getString("simState"));
						System.out.println("simState==="+rset.getString("simState"));				
					//	float eventTime = eventData.getEventTime()+rset.getFloat("cycletime");
						//is old cell
						DBDataManager ageDataManager = new DBDataManager();
						Connection ageConn = pool.getCoonection();
						ageDataManager.setConnection(ageConn);
						int gAge = ageDataManager.retrieveAgentAge(eventData.getAgentId());
						ageDataManager.cleanUp();
						pool.closeConnection(ageConn);
						
						//daughter cell is created only if cell age is less 30, cell stop division 
						// after it has divided 30 times					
						CellAgent oldAgent = new CellAgent(eventData.getAgentId());						
					//	oldAgent.setLastDivisionEventTime(eventTime);							
						oldAgent.setParamData(paramData);					
						oldAgent.setMetaData(this.getMetaData());
						
						//oldAgent.setStateAtEvent(eventData.getStateAtEvent());
						oldAgent.setStateAtEvent(eventData.getSimState());
						//oldAgent.setLastDivisionEventTime(eventData.getEventTime());
						oldAgent.setgTime(gTime);
						/*if(gAge>0){
						oldAgent.setDaughterCell(false);
						}else{ oldAgent.setDaughterCell(true);}*/
						oldAgent.setSumOfGLUConsumed(sumOfGLUConsumed);	
						oldAgent.setCurrentGlucose(this.getCurrentGlucose());
						oldAgent.setgAge(gAge+1);
						execSvc.execute(oldAgent);						
						
					}else{
					
					eventData = new EventData(gTime,rset.getFloat("eventtime"), event ,rset.getString("stateatevent"), rset.getString("caid"),rset.getInt("tcid"));
					eventData.setSimState(rset.getString("simState"));
				//	System.out.println("Division==="+rset.getFloat("eventtime"));				
					float eventTime = eventData.getEventTime()+rset.getFloat("cycletime");
					//is old cell
					DBDataManager ageDataManager = new DBDataManager();
					Connection ageConn = pool.getCoonection();
					ageDataManager.setConnection(ageConn);
					int gAge = ageDataManager.retrieveAgentAge(eventData.getAgentId());
					ageDataManager.cleanUp();
					pool.closeConnection(ageConn);
					
					//daughter cell is created only if cell age is less 30, cell stop division 
					// after it has divided 30 times
					if(gAge!=30){
					CellAgent oldAgent = new CellAgent(eventData.getAgentId());						
					oldAgent.setLastDivisionEventTime(eventTime);							
					oldAgent.setParamData(paramData);					
					oldAgent.setMetaData(this.getMetaData());
					
					//testing with state at end of simulation
					oldAgent.setStateAtEvent(eventData.getStateAtEvent());
					System.out.println("sim state:"+ eventData.getSimState());
				//	oldAgent.setSimState(eventData.getSimState());
				//	oldAgent.setLastDivisionEventTime(eventData.getEventTime());
					oldAgent.setgTime(gTime);
					oldAgent.setDaughterCell(false);
					oldAgent.setSumOfGLUConsumed(sumOfGLUConsumed);	
					oldAgent.setCurrentGlucose(this.getCurrentGlucose());
					oldAgent.setgAge(gAge+1);
					execSvc.execute(oldAgent);
					
				//	oldAgent.run();					
					
					//create new cell
					String newAgentId = "CAID"+ (this.agentCounter);
					CellAgent newAgent = new CellAgent(newAgentId);
					newAgent.setParamData(paramData);
					//the first division event time for duaghter cell is the last division event for mother cell
					newAgent.setLastDivisionEventTime(oldAgent.getLastDivisionEventTime());
					newAgent.setMetaData(this.getMetaData());
					newAgent.setStateAtEvent(eventData.getStateAtEvent());
				//	oldAgent.setSimState(eventData.getSimState());					
					newAgent.setgTime(gTime);					
					newAgent.setDaughterCell(true);
					newAgent.setSumOfGLUConsumed(sumOfGLUConsumed);
					newAgent.setCurrentGlucose(this.getCurrentGlucose());
					execSvc.execute(newAgent);
				
				//	newAgent.run();
					System.out.println("No of Agent Now: "+this.agentCounter);
			//		System.out.println("Queue size now: " + execSvc.getQueue().size());
					this.agentCounter++;
					//newAgent.run();
					
				//	cellAgentList.add(oldAgent);
				//	cellAgentList.add(newAgent);
					
				/*	if(cellAgentList.size()==4){
						 ExecutorService execSvc = Executors.newFixedThreadPool( 4 );

							for(int i =0; i<4; i++){
							execSvc.execute(cellAgentList.get(i));
						}
						cellAgentList.removeAll(cellAgentList);
						execSvc.shutdown();
					}*/					
					
					}
				}
					
				}	
				
				
				execSvc.shutdown();
				while (!execSvc.isTerminated()) {

				}
				//Thread.currentThread().join();
				rset.close();
			}catch(Exception e) {
				e.printStackTrace();
			}				
			dataManager.cleanUp();
			this.setCurrentGlucose((this.getCurrentGlucose()*10e-3f - 10e-10f*sumOfGLUConsumed)/10e-3f);
		}
		}				

	}	
	
	void initialiseAgent(Cells cells, Cell []cell) {	
		Random vRandom = new Random();		
		float randGen = 0.0f;
		if(simTime == 0.0f){
			float size = 0.0f;
			//using the XML input file
			if(cell!=null){
				int totalCell = cell.length;
				for(int i=0; i<totalCell; i++){
					randGen = (float)vRandom.nextGaussian();
					Map<String, Float> changeInitCondMap = new HashMap<String, Float>();
					CellAgent cAgent = new CellAgent("CAID"+i);	
				//	cAgent.setGeneration(1);
					cAgent.setgAge(0);					
						size = cell[i].getSizeVariable().getValue();					
					this.paramData.setBiomassVariable(cell[i].getSizeVariable().getModelId());
					//set the variable to change					
						Cellular.Cell.ChangeModelInitialConditons.Variable []variable = cell[i].getChangeModelInitialConditons().getVariableArray();					
						for(int ii=0; ii<variable.length; ii++){
							changeInitCondMap.put(variable[ii].getModelId(), variable[ii].getValue());
						}				
					
						paramData.setChangeVariableIdsValuesMap(changeInitCondMap);
						cAgent.setBirthSize(size);
						cAgent.setMotherId(cAgent.getAgentId());
						cAgent.setMotherAgeAtBirth(0);
					//	this.setCurrentGlucose((this.getCurrentGlucose()/this.getEnviromentVolume());
					//	cAgent.setCurrentGlucose(this.getCurrentGlucose()/this.getEnviromentVolume());
						cAgent.setCurrentGlucose(this.getCurrentGlucose());
						Connection con = pool.getCoonection();
						//cAgent.setPool(pool);
						cAgent.setWebServiceAddress(this.getWebServiceAddress());
						cAgent.setUseTabulator(this.isUsTabulator());
						cAgent.setParamData(paramData);
						cAgent.setVolume(this.getCellVolume());
						cAgent.setLastDivisionEventTime(0.0f);	
					//	System.out.println(cAgent.getAgentId());
						metaData = cAgent.initialise(simTime, con);
						pool.closeConnection(con);				
						this.agentCounter++;
					
				}	
			}else{
			int totalCell = cells.getTotal();
			float cellsize = cells.getSizeVariable().getValue();
		//	Cell [] cell = cells.getCellArray();
			for(int i=0; i<totalCell; i++){
				randGen = (float)vRandom.nextGaussian();
				Map<String, Float> changeInitCondMap = new HashMap<String, Float>();
				CellAgent cAgent = new CellAgent("CAID"+i);	
			//	cAgent.setGeneration(1);
				cAgent.setgAge(0);
				if(cells.getSizeVariable().getIsRandom()){
					size = cellsize+(float)random.nextGaussian()*cells.getSizeVariable().getStandardDeviation();					
				}else{
					size = cellsize;										
				}
				this.paramData.setBiomassVariable(cells.getSizeVariable().getModelId());
				//set the variable to change
				if(cells.getChangeModelInitialConditons().getIsRandom()){
					Variable []variable = cells.getChangeModelInitialConditons().getVariableArray();				
					for(int ii=0; ii<variable.length; ii++){
						changeInitCondMap.put(variable[ii].getModelId(), variable[ii].getValue()+randGen*cells.getChangeModelInitialConditons().getStandardDeviation());
					}					
				}else{
					Variable []variable = cells.getChangeModelInitialConditons().getVariableArray();					
					for(int ii=0; ii<variable.length; ii++){
						changeInitCondMap.put(variable[ii].getModelId(), variable[ii].getValue());
					}				
				}
					paramData.setChangeVariableIdsValuesMap(changeInitCondMap);
					cAgent.setBirthSize(size);
					cAgent.setMotherId(cAgent.getAgentId());
					cAgent.setMotherAgeAtBirth(0);
				//	this.setCurrentGlucose((this.getCurrentGlucose()/this.getEnviromentVolume());
				//	cAgent.setCurrentGlucose(this.getCurrentGlucose()/this.getEnviromentVolume());
					cAgent.setCurrentGlucose(this.getCurrentGlucose());
					Connection con = pool.getCoonection();
					//cAgent.setPool(pool);
					cAgent.setWebServiceAddress(this.getWebServiceAddress());
					cAgent.setUseTabulator(this.isUsTabulator());
					cAgent.setParamData(paramData);
					cAgent.setVolume(this.getCellVolume());
					cAgent.setLastDivisionEventTime(0.0f);	
				//	System.out.println(cAgent.getAgentId());
					metaData = cAgent.initialise(simTime, con);
					pool.closeConnection(con);				
					this.agentCounter++;
				
			}
			}
			
			System.out.println("Please wait initial.....");

		}
		
	}	 

	/**
	 * Provides the asynchronous, autonomous behavior of this agent that occurs
	 * periodically, depending on the sleep time for this agent.
	 */
	public void processTimerPop() {
		//	process();	
	}

	/**
	 * Performs synchronous event processing for this agent.
	 *
	 * @param event the AgentEvent object
	 */
	public void processAgentEvent(AgentEvent event) {
		
			this.processEvent(event);	
	}	

	public void processEvent(AgentEvent event) {	

	}

	/**
	 * Checks the condition being monitored by this agent.
	 * 
	 * @return <code>true</code> if the condition occurred or <code>false
	 *         </code> if
	 *         the condition did not occur
	 */
	private boolean checkCondition(float simTime) {
		boolean truth = false;
		if (simTime < simDuration) {
			truth = true;			
		}
		return truth;
	}

	/**
	 * In the Singleton design pattern, used to get single instance.
	 * 
	 * @return the MasterAgent object
	 * 
	 */
	static public MasterAgent getInstance() {
		if (instance == null) {
			instance = new MasterAgent("Master");
		}
		return instance;
	}

	public String loadModel(String pathName){
		FileDataManager fileManager = new FileDataManager();
		return fileManager.loadModel(pathName);		
	}

	//prepare the time course input data for cell agent
	public TimeCourseInputData prepareTCInputData(String modelFile){
		TimeCourseInputData tcInputData = new TimeCourseInputData(this.loadModel(modelFile));		
		tcInputData.setTolerance(tabulatorTol);		
		tcInputData.setnTimeSteps(nTimeSteps);
		tcInputData.setStepSize(stepSize);			
		return tcInputData;
	}

	public TimeCourseInputData prepareTCInputDataFromString(String model){
		TimeCourseInputData tcInputData = new TimeCourseInputData(model);		
		tcInputData.setTolerance(tabulatorTol);		
		tcInputData.setnTimeSteps(nTimeSteps);
		tcInputData.setStepSize(stepSize);			
		return tcInputData;
	}

/*	public TimeCourseInputData UpdateTCInputDataWithNewInitialState(String model, String stateAtEvent, String headerString, boolean isDaugther){
		TimeCourseInputData tcInputData = new TimeCourseInputData(model);		
		tcInputData.setTolerance(tabulatorTol);		
		tcInputData.setnTimeSteps(nTimeSteps);
		tcInputData.setStepSize(stepSize);
		tcInputData.changeSInitialCondition(stateAtEvent, headerString, isDaugther);
		return tcInputData;
	}
*/
	public double [] updateModelWithNewState(String stateAtEvent){	
		//	List<Double> sInitCond = new ArrayList();
		StringTokenizer sInitToken = new StringTokenizer(stateAtEvent, "|"); // toekenizer
		sInitToken.nextToken();
		double [] sInitCond = new double [sInitToken.countTokens()];
		for (int j = 0; j <sInitToken.countTokens(); j++) {				
			sInitCond[j] = Double.parseDouble(sInitToken.nextToken());	
			System.out.println(sInitCond[j]);
		}
		return sInitCond;
	}
	
/*	public synchronized void addCellAgent(CellAgent cellAgentg){
		cellAgentTable.put(cellAgentg.getAgentId(), cellAgentg);
	}
*/	
	
	//examples of thread executor
	
/*//  // fixed pool, unlimited queue
//  ExecutorService service = Executors.newFixedThreadPool(10 );
//  ThreadPoolExecutor executor = (ThreadPoolExecutor) service;

  // fixed pool fixed queue
  BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100, true);
  ThreadPoolExecutor executor = new ThreadPoolExecutor(
    10, // core size
    20, // max size
    1, // keep alive time
    TimeUnit.MINUTES, // keep alive time units
    queue, // the queue to use
  );

  // set rejected execution handler
  // or catch exception from executor.execute (see below)
  if (!wantExceptionOnReject) executor.setRejectedExecutionHandler(new RejectedHandler());

  for(long i = 0; ; ++i) {
    Task t = new Task(String.valueOf(i));
    System.out.println(Thread.currentThread().getName() + " submitted " + t + ", queue size = " + executor.getQueue().size());
    try {
      executor.execute(t);
    } catch (RejectedExecutionException e) {
      // will be thrown if rejected execution handler
      // is not set with executor.setRejectedExecutionHandler
      e.printStackTrace();
    }
    Thread.sleep(1);
  }*/
	
	/**
	 * @param unit, the unit measurement to convert e.g. liter
	 * @return the double value of the converted unit e.g. 1.0 for 1 liter, 0.001 for milliliter
	 */
	public double converterUnit(String unit){
		double unitConvert = 1.0f;
		if(unit.equals("l") || unit.equals("liter"))unitConvert = 1.0;
		if(unit.equals("ml") || unit.equals("milliliter") )unitConvert = 0.001;
		if(unit.equals("microliter"))unitConvert = 1.0E-6;
		if(unit.equals("nanoliter"))unitConvert = 1.0E-9;
		return unitConvert;
	}

}
