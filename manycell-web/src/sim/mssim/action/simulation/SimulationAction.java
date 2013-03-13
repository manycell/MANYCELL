package sim.mssim.action.simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.xmlbeans.XmlOptions;
import org.copasi.utils.FileProcessor;
import org.manycell.config.CellularDocument.Cellular;
import org.manycell.config.ManyCellConfig;
import org.manycell.config.ManycellDocument;
import org.manycell.config.EnvironmentDocument.Environment;
import org.manycell.config.EnvironmentDocument.Environment.Nutrients;
import org.manycell.config.EnvironmentDocument.Environment.Nutrients.Nutrient;
import org.manycell.config.DatabaseDocument.Database;
import org.manycell.config.ManycellDocument.Manycell;
import org.manycell.config.SimulationDocument.Simulation;
import org.manycell.config.SubCellularDocument.SubCellular;
import org.manycell.config.SubCellularDocument.SubCellular.Model;
import org.manycell.config.SubCellularDocument.SubCellular.Model.TimeCourseSimulation;


import sim.mssim.action.MSSimSupportAction;
import sim.utils.CellEvent;
import sim.utils.Species;

/**
 * @author dada
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
/**
 * @author dada
 *
 */
/**
 * @author dada
 *
 */
public class SimulationAction extends MSSimSupportAction implements	SessionAware {
	private static final long serialVersionUID = 5156288255337069381L;

	// get the libSBML path
	static {
		try {

			// System.out.println("java.library.path="+System.getProperty("java.library.path"));
			Runtime.getRuntime().loadLibrary("sbmlj");
			// System.loadLibrary("sbmlj");
			// Extra check to be sure we have access to libSBML:
			Class.forName("org.sbml.libsbml.libsbml");
		} catch (Exception e) {
			System.err.println("Error: could not load the libSBML library");
			System.exit(1);
		}
	}

	private String url;
	// variables for checking data
	private String subCellularData = null;
	private String cellularData = null;
	private String environmentData = null;
	
	//multiscale model data variable
	//Sub-cellular section
	private String modelName = null;
	private String modelType = "continum";
	private float startOutputTime;
	private float stepSize;
	private int stepNumber;	
	private List<Species> activeSpecies;
	
	//variable for setting event data
	private String eventName;
	private String eventId;
	private String eventModelId;
	
	//Cellular section
	private int numberOfCells;
	private List<Species> cellDiffSpecies;
	
	//cell specific parameters
	private double averageCellVolume = 10e-3;
	private String cellVolumeUnit = "microliter";
	
	//Environment section
	//volume variables
	private float volume;
	private String type;
	private String unit;	
	
	//nutrient variables
	private String SName;
	private float SValue;
	private String SId;
	
	//multiscale simulation environment
	private String executionType;
		
	// session for holding data
	private Map<String, Object> session;
	private FileProcessor fileProcessor;

	private ManyCellConfig config; 	
	
	/**
	 * @param session the session to set
	 */
	public void setSession(Map session) {
		this.session = session;
	}	
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the subCellularData
	 */
	public String getSubCellularData() {
		return subCellularData;
	}

	/**
	 * @param subCellularData the subCellularData to set
	 */
	public void setSubCellularData(String subCellularData) {
		this.subCellularData = subCellularData;
	}

	/**
	 * @return the cellularData
	 */
	public String getCellularData() {
		return cellularData;
	}

	/**
	 * @param cellularData the cellularData to set
	 */
	public void setCellularData(String cellularData) {
		this.cellularData = cellularData;
	}

	/**
	 * @return the environmentData
	 */
	public String getEnvironmentData() {
		return environmentData;
	}

	/**
	 * @param environmentData the environmentData to set
	 */
	public void setEnvironmentData(String environmentData) {
		this.environmentData = environmentData;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the modelType
	 */
	public String getModelType() {
		return modelType;
	}

	/**
	 * @param modelType the modelType to set
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	/**
	 * @return the startOutputTime
	 */
	public float getStartOutputTime() {
		return startOutputTime;
	}

	/**
	 * @param startOutputTime the startOutputTime to set
	 */
	public void setStartOutputTime(float startOutputTime) {
		this.startOutputTime = startOutputTime;
	}

	/**
	 * @return the stepSize
	 */
	public float getStepSize() {
		return stepSize;
	}

	/**
	 * @param stepSize the stepSize to set
	 */
	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}

	/**
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		return stepNumber;
	}

	/**
	 * @param stepNumber the stepNumber to set
	 */
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	/**
	 * @return the activeSpecies
	 */
	public List<Species> getActiveSpecies() {
		return activeSpecies;
	}

	/**
	 * @param activeSpecies the activeSpecies to set
	 */
	public void setActiveSpecies(List<Species> activeSpecies) {
		this.activeSpecies = activeSpecies;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the eventModelId
	 */
	public String getEventModelId() {
		return eventModelId;
	}

	/**
	 * @param eventModelId the eventModelId to set
	 */
	public void setEventModelId(String eventModelId) {
		this.eventModelId = eventModelId;
	}

	/**
	 * @return the numberOfCells
	 */
	public int getNumberOfCells() {
		return numberOfCells;
	}

	/**
	 * @param numberOfCells the numberOfCells to set
	 */
	public void setNumberOfCells(int numberOfCells) {
		this.numberOfCells = numberOfCells;
	}

	/**
	 * @return the cellDiffSpecies
	 */
	public List<Species> getCellDiffSpecies() {
		return cellDiffSpecies;
	}

	/**
	 * @param cellDiffSpecies the cellDiffSpecies to set
	 */
	public void setCellDiffSpecies(List<Species> cellDiffSpecies) {
		this.cellDiffSpecies = cellDiffSpecies;
	}

	/**
	 * @return the averageCellVolume
	 */
	public double getAverageCellVolume() {
		return averageCellVolume;
	}

	/**
	 * @param averageCellVolume the averageCellVolume to set
	 */
	public void setAverageCellVolume(double averageCellVolume) {
		this.averageCellVolume = averageCellVolume;
	}

	/**
	 * @return the cellVolumeUnit
	 */
	public String getCellVolumeUnit() {
		return cellVolumeUnit;
	}

	/**
	 * @param cellVolumeUnit the cellVolumeUnit to set
	 */
	public void setCellVolumeUnit(String cellVolumeUnit) {
		this.cellVolumeUnit = cellVolumeUnit;
	}

	/**
	 * @return the volume
	 */
	public float getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(float volume) {
		this.volume = volume;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the sName
	 */
	public String getSName() {
		return SName;
	}

	/**
	 * @param sName the sName to set
	 */
	public void setSName(String sName) {
		SName = sName;
	}

	/**
	 * @return the sValue
	 */
	public float getSValue() {
		return SValue;
	}

	/**
	 * @param sValue the sValue to set
	 */
	public void setSValue(float sValue) {
		SValue = sValue;
	}

	/**
	 * @return the sId
	 */
	public String getSId() {
		return SId;
	}

	/**
	 * @param sId the sId to set
	 */
	public void setSId(String sId) {
		SId = sId;
	}

	/**
	 * @return the executionType
	 */
	public String getExecutionType() {
		return executionType;
	}

	/**
	 * @param executionType the executionType to set
	 */
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}	

	/**
	 * @return the fileProcessor
	 */
	public FileProcessor getFileProcessor() {
		return fileProcessor;
	}

	/**
	 * @param fileProcessor the fileProcessor to set
	 */
	public void setFileProcessor(FileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	/**
	 * @return the config
	 */
	public ManyCellConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(ManyCellConfig config) {
		this.config = config;
	}

	/**
	 * @return the isAmount
	 */
	public boolean isAmount() {
		return isAmount;
	}

	/**
	 * @param isAmount the isAmount to set
	 */
	public void setAmount(boolean isAmount) {
		this.isAmount = isAmount;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isDaughter
	 */
	public String getIsDaughter() {
		return isDaughter;
	}

	/**
	 * @param isDaughter the isDaughter to set
	 */
	public void setIsDaughter(String isDaughter) {
		this.isDaughter = isDaughter;
	}

	/**
	 * @return the partionVariableId
	 */
	public String getPartionVariableId() {
		return partionVariableId;
	}

	/**
	 * @param partionVariableId the partionVariableId to set
	 */
	public void setPartionVariableId(String partionVariableId) {
		this.partionVariableId = partionVariableId;
	}

	public String doCheckMultiscaleModel() throws Exception  {
		this.setSubCellularData((String)getSession().get("SubCellularData"));
		this.setCellularData((String)getSession().get("CellularData"));
		this.setEnvironmentData((String)getSession().get("EnvironmentData"));
		
		if(this.getSubCellularData()==null || this.getCellularData()==null || this.getEnvironmentData()==null){
			this.setUrl("/UI/Simulation/MultiscaleModelChecker.jsp");			
		}
		else if(this.getSubCellularData()!=null && this.getCellularData()!=null && this.getEnvironmentData()!=null){			
			//sub-cellular section			
			this.setUrl("/UI/Simulation/MultiscaleModelContent.jsp");						
		}
		return SUCCESS;
	}

	public String doShowMultiscaleModel() throws Exception  {
		this.setSubCellularData((String)getSession().get("SubCellularData"));
		this.setCellularData((String)getSession().get("CellularData"));
		this.setEnvironmentData((String)getSession().get("EnvironmentData"));
		
		//sub-cellular section
		this.setModelName((String)getSession().get("modelName"));		
		Map<String, Object> param = (HashMap<String, Object>)getSession().get("param");			
		this.setStartOutputTime((Float)param.get("startTime"));
		this.setStepSize((Float)param.get("stepSize"));
		this.setStepNumber((Integer)param.get("stepNumber"));		
		this.setActiveSpecies((List<Species>)getSession().get("ActiveSpecies"));
		
		//cellular section
		this.setNumberOfCells((Integer)this.getSession().get("NumberOfCells"));
		this.setCellDiffSpecies((List<Species>)this.getSession().get("CellDiffSpecies"));
		
		//environment section		
		Map<String, Object>envData = (HashMap)getSession().get("envData");
		this.setType((String)envData.get("type"));
		this.setVolume((Float)envData.get("volume"));
		this.setUnit((String)envData.get("unit"));	
		
		Species sp = (Species)getSession().get("NutrientSP");
		this.setSName(sp.getName());
		this.setSId(sp.getId());
		this.setSValue(sp.getValue());		
		return SUCCESS;
	}
	
	//Bioreactor variable			
		//nutrients in the reactor		
		private boolean isAmount = true;
		
		//data for cells
		private String id;
		private String isDaughter = "yes";
		private String subCellularModel = "SCM";	
		
		public String getSubCellularModel() {
			return subCellularModel;
		}

		public void setSubCellularModel(String subCellularModel) {
			this.subCellularModel = subCellularModel;
		}

		//time course simulation parameter	
		private String partionVariableId = "FValue";		
		
	/**
	 * @return SUCCESS as string
	 * @throws Exception
	 */
	public String doGenerateMXMLModel() throws Exception  {		
		config = new ManyCellConfig();
		fileProcessor = new FileProcessor();
		
		int jobId=0;
		String manyCellUser = getServletRequest().getRemoteUser();
		String userJobDir = config.getTomcatHome()+"/manycell/"+manyCellUser+"/"+manyCellUser; 
		String userJobExecDir = config.getHomeDirectory()+"/"+config.getUser()+"/"+config.getUserWorkspaceDirectory()+"/"+manyCellUser+"/"+manyCellUser;
		File jobStoreDir = null;
						
		String subCellularModelId = "SCM";
		// initialise system variables
		ManycellDocument simDoc = null;	
		Manycell sim = null;
		
		//get the manycell document from the session
		simDoc = (ManycellDocument)getSession().get("xml");				
		
		//check if it's new document
		if(simDoc==null){			
			//clear the xml document from the session for new beginning 			
			 getSession().remove("xml");
			 
			 XmlOptions options = new XmlOptions();
			 Map<String, String> substitutes = new HashMap<String, String>();
			 substitutes.put("", "http://www.manycell.org/sim-config-schema");
			 options.setLoadSubstituteNamespaces(substitutes);
		
			 simDoc = ManycellDocument.Factory.newInstance(options);
			 sim = Manycell.Factory.newInstance();
			 
			 
			// Sub-cellular section
			SubCellular subModel = SubCellular.Factory.newInstance();

			// initialise the model and time course simulator for cells
			SubCellular.Model cellModel = subModel.addNewModel();
			this.setModelName((String)this.getSession().get("modelName"));
			cellModel.setName(this.getModelName());
			cellModel.setId("SCM"); //TODO
			cellModel.setType(this.getModelType());
			cellModel.setIsDaughter(true);
			

			//time course parameters
			Map<String, Object> param = (HashMap<String, Object>)getSession().get("param");			
			this.setStartOutputTime((Float)param.get("startTime"));
			this.setStepSize((Float)param.get("stepSize"));
			this.setStepNumber((Integer)param.get("stepNumber"));		
			this.setActiveSpecies((List<Species>)getSession().get("ActiveSpecies"));
			
			TimeCourseSimulation tSim = TimeCourseSimulation.Factory.newInstance();
			tSim.setNumberOfSteps(this.getStepNumber());
			tSim.setStepSize(this.getStepSize());
			tSim.setStartOutputTime(this.getStartOutputTime());
			tSim.setWebServiceAddress(config.getWebServiceAddress());
			cellModel.setTimeCourseSimulation(tSim); //add the time course simulation to model
						
			//take care of active species variables
			SubCellular.Model.ActiveVariables activeVariable = cellModel.addNewActiveVariables();
			if(this.getActiveSpecies()!=null){
			 for(int i=0; i<this.getActiveSpecies().size(); i++){
				 SubCellular.Model.ActiveVariables.Variable variable =  activeVariable.addNewVariable();
				 Species sp = (Species)this.getActiveSpecies().get(i);
				 variable.setModelId(sp.getId());								 
			 }	
			} 
			// events
			SubCellular.Model.Events events = SubCellular.Model.Events.Factory.newInstance();
			SubCellular.Model.Events.Event event = events.addNewEvent();
			
			//setting the cell event
			CellEvent cellEvent = (CellEvent)this.getSession().get("CellEvent");
			event.setName(cellEvent.getEvenName());
			if(cellEvent.getEvenName().equals("division")){
				event.setId("div");
			}
			event.setModelId(cellEvent.getParamId());
			event.setValue(cellEvent.getParamValue());
			
			// add the events to the subcellular
			cellModel.setEvents(events);

			// handle partition variable
			Model.CellSizePartitionVariable partVariable = Model.CellSizePartitionVariable.Factory.newInstance();
			partVariable.setModelId((String)this.getSession().get("SizePartition"));
			cellModel.setCellSizePartitionVariable(partVariable);	
			
			//setting the model file directory
			jobId = storeUserMetaData();
			jobStoreDir = new File(userJobDir+jobId);
			userJobExecDir = userJobExecDir+jobId;
			jobStoreDir.mkdirs();
			
			String modelFilePath = userJobExecDir+"/sbml-model.xml";
			cellModel.setFileNameWithPath(modelFilePath);
				 
			//Cellular section
			 Cellular cellular = Cellular.Factory.newInstance();		
			 Cellular.Cells cells = Cellular.Cells.Factory.newInstance();	
			 Cellular.Cells.Behaviours behaviours =  Cellular.Cells.Behaviours.Factory.newInstance();
			 Cellular.Cells.Behaviours.Behaviour behaviour = behaviours.addNewBehaviour();
			
			 if(cellEvent.getEvenName().equals("division")){
				 behaviour.setName(cellEvent.getEvenName());
				 behaviour.setEventReference("div");				 
				}
			 cells.setBehaviours(behaviours);			 
			 
			 //get the cellular info from the session
			 this.setNumberOfCells((Integer)this.getSession().get("NumberOfCells"));
			 this.setCellDiffSpecies((List<Species>)this.getSession().get("CellDiffSpecies"));
			 
			 //set the cells info
			 cells.setTotal(this.getNumberOfCells());
			 this.setAverageCellVolume(config.getAverageCellVolume());
			 cells.setAverageCellVolume(this.getAverageCellVolume());
			 cells.setCellVolumeUnit(this.getCellVolumeUnit());		
			 
			 //the cell size
			 Cellular.Cells.SizeVariable size =  Cellular.Cells.SizeVariable.Factory.newInstance();
			 Species cellSize = (Species)this.getSession().get("CellSize");
			 size.setIsRandom(true);
			 size.setStandardDeviation(this.getConfig().getStandardDeviation());
			 size.setModelId(cellSize.getId());
			 size.setValue(cellSize.getValue());
			 cells.setSizeVariable(size);
			 
			 Cellular.Cells.ChangeModelInitialConditons sInitial = Cellular.Cells.ChangeModelInitialConditons.Factory.newInstance();
			if(this.getCellDiffSpecies()!=null){
				sInitial.setIsRandom(true);
				sInitial.setStandardDeviation(this.getConfig().getStandardDeviation());
			 for(int i=0; i<this.getCellDiffSpecies().size(); i++){
				 Cellular.Cells.ChangeModelInitialConditons.Variable variable =  sInitial.addNewVariable();
				 Species sp = (Species)this.getCellDiffSpecies().get(i);				 
				 variable.setModelId(sp.getId());
				 variable.setValue(sp.getValue());				 
			 }	
			}
			cells.setChangeModelInitialConditons(sInitial);
			cellular.setCells(cells);

			// Set the environment section data
			Environment environ = Environment.Factory.newInstance();
			Environment.Volume eVolume = Environment.Volume.Factory.newInstance();
			Map<String, Object> envData = (HashMap) getSession().get("envData");
			this.setType((String) envData.get("type"));
			this.setVolume((Float) envData.get("volume"));
			this.setUnit((String) envData.get("unit"));
			 
			 eVolume.setSize(this.getVolume());
			 eVolume.setUnit(this.getUnit());
			 
			 environ.setType(this.getType());
			 environ.setVolume(eVolume);
			
			// Environemt nutrients
			Nutrients nutrients = Nutrients.Factory.newInstance();
			Nutrient eNutrient = nutrients.addNewNutrient();
			Species sp = (Species) getSession().get("NutrientSP");
			this.setSName(sp.getName());
			this.setSId(sp.getId());
			this.setSValue(sp.getValue());

			eNutrient.setName(this.getSName());
			eNutrient.setModelId(this.getSId());
			eNutrient.setValue(this.getSValue());
			environ.setNutrients(nutrients);				
			
			//Nutrient variable			
			Model.NutrientConsumptionVariable nVariable = Model.NutrientConsumptionVariable.Factory.newInstance();
			nVariable.setModelId((String)this.getSession().get("NutrientVariable"));			
			cellModel.setNutrientConsumptionVariable(nVariable);

			//set the multiscale simulation parameters
			Simulation simulation = Simulation.Factory.newInstance();
			if(this.getExecutionType().equals("ISAT")){
				simulation.setAllowParallelExecution(false);
				simulation.setUseISAT(true);
			}else{
				simulation.setAllowParallelExecution(true);
				simulation.setUseISAT(false);
			}
			simulation.addNewParallelExecution().setNumberOfProcessors(config.getNumberOfProcessors());
			simulation.addNewISAT().setTolerance(config.getTolerance());
			simulation.setDuration(config.getSimulationDuration());
			simulation.setMaxCellGeologicalAge(config.getMaxCellGeologicalAge());
			
			//set the database info section using the config information
			Database database = Database.Factory.newInstance();
			database.setDatabaseName(config.getDatabaseName());
			database.setPort(config.getPort());
			database.setServerName(config.getServerName());
			database.setUserName(config.getUserName());
			if(config.getPassword()!=null)database.setPassword(config.getPassword());
			database.setInitialConnections(config.getInitialConnections());
			database.setMaxConnections(config.getMaxConnections());			
			 			
			 //add the created model to subcellular model
			 
			 //set all the manycell element
			 sim.setEnvironment(environ);
			 sim.setCellular(cellular);
			 sim.setSimulation(simulation);
			 sim.setDatabase(database);
			 sim.setSubCellular(subModel);
			 
		
		}else{
			sim = simDoc.getManycell();
		}
		simDoc.setManycell(sim);	
		
		createCopyScriptFile(jobStoreDir.toString()+"/manycell-copyier.sh", manyCellUser, jobStoreDir.toString(), userJobExecDir);
		
		//saving the model file
		try{		
		fileProcessor.saveModelInSpecifiedDirectory((String)this.getSession().get("sbmlModel"), jobStoreDir.toString(), "sbml-model.xml");
		fileProcessor.saveModelInSpecifiedDirectory(simDoc.toString(), jobStoreDir.toString(), "manycell-mxml-model.xml");
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		Runtime r = Runtime.getRuntime();
		try{
			r.exec(jobStoreDir.toString()+"/manycell-copyier.sh");			
		}catch(Exception e){
			e.printStackTrace();
		}		
	//	System.out.println("This is test: "+test.substring(4));
    	return SUCCESS;
    }	
	
    /**
     * Write data to file using specified path
     * @param fileNameWithPath
     * @param manyCellUser
     * @param jobStoreDir
     * @param userJobExecDir
     */
    public void createCopyScriptFile (String fileNameWithPath, String manyCellUser, String jobStoreDir, String userJobExecDir) {	
   /* 	java ...... &
    	echo "$!" > myjavaprogram.pid

    	When you need to kill it, just do:

    	kill `cat myjavaprogram.pid`
*/
    	String jobIdFile =userJobExecDir + "/"+ manyCellUser+".pid"; // config.getHomeDirectory()	+ "/$USER/"+ config.getUserWorkspaceDirectory()+"/"+ manyCellUser+"/"+manyCellUser+jobId +"/"+ manyCellUser+".pid";
    	// creating a script file for copying and running manycell
		StringBuffer buf = new StringBuffer();
		buf.append("#!/bin/bash\n");
		buf.append("HOST='" + config.getHostName() + "'\n");
		buf.append("USER='" + config.getUser() + "'\n");

		try{
		buf.append("ssh $USER@$HOST mkdir " + config.getHomeDirectory()	+ "/$USER/"+  config.getUserWorkspaceDirectory()+"/"+manyCellUser+"\n");				
		buf.append("ssh $USER@$HOST mkdir " +userJobExecDir+"\n");		
		buf.append("scp " + jobStoreDir	+ "/sbml-model.xml $USER@$HOST:" + userJobExecDir+"\n");
		buf.append("scp " + jobStoreDir	+ "/manycell-mxml-model.xml $USER@$HOST:" + userJobExecDir+"\n");
		buf.append("ssh $USER@$HOST touch " + userJobExecDir+"/job.txt \n");
		buf.append("ssh $USER@$HOST chmod u+wx " + userJobExecDir+"/job.txt \n");
		buf.append("ssh $USER@$HOST touch " + jobIdFile+ "\n");				
		buf.append("ssh $USER@$HOST chmod u+wx " + jobIdFile+ "\n");
		
		buf.append("library='-Djava.library.path=\""+config.getLibraryPath()+"\"'\n");
		buf.append("parameter='"+userJobExecDir+"/manycell-mxml-model.xml > "+ userJobExecDir+"/job.txt & pid=$!; echo $pid > "+jobIdFile+"'\n");
		buf.append("ssh $USER@$HOST java $library -jar " + config.getExecutableFile() +" $parameter");
						
	//	buf.append("ssh $USER@$HOST java -jar " + config.getExecutableFile() +" "+userJobExecDir+"/manycell-mxml-model.xml > " + userJobExecDir +"/job.txt &\n");
		
	//	parameter='/home/joseph/local/user-workspace/users/dada/dada15/manycell-mxml-model.xml > /home/$USER/local/user-workspace/users/dada/dada15/job.txt & pid=$!; echo $pid > /home/$US
//		& pid=$!; echo $pid > config.getHomeDirectory()+ "/$USER/"+  config.getUserWorkspaceDirectory()+"/" +manyCellUser +".pid" +"'
//
	//	buf.append("ssh $USER@$HOST echo '$!' > " + config.getHomeDirectory()+ "/$USER/"+  config.getUserWorkspaceDirectory()+"/" +manyCellUser +".pid" +" & \n");	

		}catch(Exception e){
			e.printStackTrace();
		}
		//save the script file
		try {
			File file = new File(fileNameWithPath);			
			FileWriter writer = new FileWriter(file);			
            BufferedWriter buffWriter = new BufferedWriter(writer);
            buffWriter.write(buf.toString());
            file.setExecutable(true);
            buffWriter.close();
            writer.close();
        } catch (IOException ex) {
        }        
    }
    
    /**
     * Store metadata about job to be run into database
     * @return the job id
     * @throws Exception
     */
    public int storeUserMetaData() throws Exception {
    	int jobId = 0;
    	String user = getServletRequest().getRemoteUser();		
		
		Connection con=this.getDBconnection();		
		String state = "started";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try{
		Statement stmt = con.createStatement();
		int val = stmt.executeUpdate("INSERT INTO jobs(username, modelname, state, starttime) VALUES('"+user+"','"+this.getModelName()+"','"+state+"','"+timestamp +"')");
		ResultSet rset = stmt.executeQuery("SELECT id FROM jobs where username='"+user+"' AND starttime='"+timestamp+"'");	
		while(rset.next()){
		jobId = rset.getInt(1);			
		}
		con.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return jobId;
				
    }
    
    /**
     * get connection object into database
     * @return con, the connection object     *
     */
    public Connection getDBconnection(){
    	String url = "jdbc:postgresql://" +config.getServerName()+":"+config.getPort()+"/"+config.getDatabaseName();		
		String driverName = "org.postgresql.Driver";
		String rUserName = config.getUserName();
		
		String rPassword = config.getPassword();
		if(config.getPassword()!=null) rPassword="";
		Connection con=null;		
		try{
			Class.forName(driverName).newInstance();
			con=DriverManager.getConnection(url, rUserName, rPassword);			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return con;
    }	
	
}
