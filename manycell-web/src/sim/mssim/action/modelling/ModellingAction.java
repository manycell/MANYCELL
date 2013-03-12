package sim.mssim.action.modelling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.copasi.utils.FileProcessor;
import sim.mssim.action.MSSimSupportAction;
import sim.mssim.databasemanager.DatabaseManager;
import sim.mssim.modelmanager.ProcessSBMLModelManager;
import sim.utils.CellEvent;
import sim.utils.Species;

public class ModellingAction extends MSSimSupportAction implements SessionAware {
	private static final long serialVersionUID = 5156288255337069381L;	
	
	//get the libSBML path
		static
			  {
			    try
			    {
			    
			   //System.out.println("java.library.path="+System.getProperty("java.library.path"));
			    Runtime.getRuntime().loadLibrary("sbmlj");
			//    System.loadLibrary("sbmlj");
			     // Extra check to be sure we have access to libSBML: 
			      Class.forName("org.sbml.libsbml.libsbml");
			    }
			    catch (Exception e)
			    {
			      System.err.println("Error: could not load the libSBML library");
			      System.exit(1);
			    }
		  }
		
	private String url;
	
	private float startOutputTime;
	private float stepSize;
	private int stepNumber;
	
	//variables for checking data
	private String subCellularData = null;
	private String cellularData = null;
	private String environmentData = null;
	
	private int numberOfCells;
	
	//parameter for Cell Event identification
	private String behaviour;
	private String SPName;
	private String SSPName;
	private String NUTPName;
	
	private String contentType;
	private String content;	
	private String fileName;
	private String caption;
	private File sbml;
	private String modelName = null;
	private int version = 0; 
	
	private String [] fileNames;
	
	private String pathway; //if use decide to use models from ManyCells
	
	//environment model data variables
	private float volume;
	private String type;
	private String unit;

	private List<Species> species;
	private String SName;
	private float SValue;

	private List<String> speciesIds;
	private List<String> speciesNames;

	private List<String> paramIds;
	private List<String> paramNames;
	
	// session for holding data
	private Map<String, Object> session;
	private FileProcessor fileProcessor;

	String modelDirectory = "/usr/local/user-workspace/models";
	
	//variable for monoscale sub-cellualr model submission
	private String description;
	private Timestamp timestamp;
	private String user; 
	
	//Monoscale time course data
	//varibale for time course simulator
	private String plot;
	private String data;
	private String results;
	private String inputFormat;
	private String outputFormat;
	private File model;
	
	//varaible for sensitivity analysis
	private String deltaMinimum;	  
	private String deltaFactor;	  
	private String function;
	private String variable;

	public ModellingAction() {
		fileProcessor = new FileProcessor();
	}

	public Map<String, Object> getSession() {
		return session;
	}	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setSession(Map session) {
		this.session = session;
	}		
	
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//Monoscale time course data set and get	
	public File getModel() {
		return model;
	}

	public void setModel(File model) {
		this.model = model;
	}

	public String getPlot() {
		return plot;
	}
	
	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}	
	
	public String getDeltaMinimum() {
		return deltaMinimum;
	}

	public void setDeltaMinimum(String deltaMinimum) {
		this.deltaMinimum = deltaMinimum;
	}

	public String getDeltaFactor() {
		return deltaFactor;
	}

	public void setDeltaFactor(String deltaFactor) {
		this.deltaFactor = deltaFactor;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String getPathway() {
		return pathway;
	}

	public void setPathway(String pathway) {		
		this.pathway = pathway;
	}

	public String getSubCellularData() {
		return subCellularData;
	}

	public void setSubCellularData(String subCellularData) {
		this.subCellularData = subCellularData;
	}

	public String getCellularData() {
		return cellularData;
	}

	public void setCellularData(String cellularData) {
		this.cellularData = cellularData;
	}

	public String getEnvironmentData() {
		return environmentData;
	}

	public void setEnvironmentData(String environmentData) {
		this.environmentData = environmentData;
	}
	

	public int getNumberOfCells() {
		return numberOfCells;
	}

	public void setNumberOfCells(int numberOfCells) {
		this.numberOfCells = numberOfCells;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public File getSbml() {
		return sbml;
	}

	public void setSbml(File sbml) {
		this.sbml = sbml;
	}	

	public float getStartOutputTime() {
		return startOutputTime;
	}

	public void setStartOutputTime(float startOutputTime) {
		this.startOutputTime = startOutputTime;
	}

	public float getStepSize() {
		return stepSize;
	}

	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getSName() {
		return SName;
	}

	public void setSName(String sName) {
		SName = sName;
	}
	

	public float getSValue() {
		return SValue;
	}

	public void setSValue(float sValue) {
		SValue = sValue;
	}

	public List<Species> getSpecies() {
		return species;
	}	

	public String getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}

	public String getSPName() {
		return SPName;
	}

	public void setSPName(String sPName) {
		SPName = sPName;
	}
	
	public String getSSPName() {
		return SSPName;
	}

	public void setSSPName(String sSPName) {
		SSPName = sSPName;
	}	
	
	public String getNUTPName() {
		return NUTPName;
	}

	public void setNUTPName(String nUTPName) {
		NUTPName = nUTPName;
	}

	public void setSpecies(List<Species> species) {
		this.species = species;
	}

	public List<String> getSpeciesIds() {
		return speciesIds;
	}

	public void setSpeciesIds(List<String> speciesIds) {
		this.speciesIds = speciesIds;
	}

	public List<String> getSpeciesNames() {
		return speciesNames;
	}

	public void setSpeciesNames(List<String> speciesNames) {
		this.speciesNames = speciesNames;
	}

	public List<String> getParamIds() {
		return paramIds;
	}

	public void setParamIds(List<String> paramIds) {
		this.paramIds = paramIds;
	}

	public List<String> getParamNames() {
		return paramNames;
	}

	public void setParamNames(List<String> paramNames) {
		this.paramNames = paramNames;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}	

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// process sub-cellular model data
	public String execute() throws Exception {	
		this.prepare();
		return SUCCESS;
	}

	public void prepare() throws Exception {		
		getSession().clear();		
		String modelString = null;		
		try {
			if (this.getSbml()==null && !this.getPathway().equals("please select")) {	
				modelString	= fileProcessor.readFileAsString(System.getenv("TOMCAT_HOME")+ "/manycell/samples/" + this.getPathway()+"/"+ this.getPathway()+".xml");
			} else {
				modelString = fileProcessor.readFileAsString(this.getSbml().toString());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		getSession().put("sbmlModel", modelString);

		speciesIds = new ArrayList<String>();
		speciesNames = new ArrayList<String>();
		paramIds = new ArrayList<String>();
		paramNames = new ArrayList<String>();
		ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);		
		this.setSpeciesIds(SMManager.getSpeciesIds());
		this.setSpeciesNames(SMManager.getSpeciesNames());
		this.setParamIds(SMManager.getParamIds());
		this.setParamNames(SMManager.getParamNames());
		this.setVersion(SMManager.getVersion());
		this.setModelName(SMManager.getModelName());
		this.setVersion(SMManager.getVersion());
		this.setModelName(SMManager.getModelName());
		getSession().put("modelName", this.getModelName());
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String doSubmitMonoscaleSubCellularModel() throws Exception  {
		String modelString = (String)this.getSession().get("sbmlModel");
		
		File file = new File(System.getenv("TOMCAT_HOME")+ "/manycell/samples");		
		fileNames = file.list();
		int size = fileNames.length +1;
		File nFile = new File(System.getenv("TOMCAT_HOME")+ "/manycell/samples/model-"+size);
		nFile.mkdir();	
		String prefix = "model-"+size;
		fileProcessor.saveModelInSpecifiedDirectory(modelString, nFile.toString(), "model-"+size+".xml");
	
		
		ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);		
		this.setModelName(SMManager.getModelName());	
		//write the model metadata
	//	this.setTimestamp(new Timestamp(System.currentTimeMillis()));
		StringBuffer buf = new StringBuffer();
		String nameOfFile = "metadata.txt";
		buf.append("submiter="+getServletRequest().getRemoteUser()+"\n");
		buf.append("timestamp="+this.getTimestamp()+"\n");		
		buf.append("modelName="+this.getModelName()+"\n");
		buf.append("description="+this.getDescription()+"\n");
		
		// save the script file
		try {
			File mFile = new File(nFile.toString()+"/"+nameOfFile);
			FileWriter writer = new FileWriter(mFile);
			BufferedWriter buffWriter = new BufferedWriter(writer);
			buffWriter.write(buf.toString());
			mFile.setExecutable(true);
			buffWriter.close();
			writer.close();
		} catch (IOException ex) {
		}
	 
		return SUCCESS;
	}
	
	public String doCheckMonoscaleSubCellularModel() throws Exception  {
		String modelString = fileProcessor.readFileAsString(this.getSbml().toString());
				
		ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);		
		this.setModelName(SMManager.getModelName());
		
		this.setUser(getServletRequest().getRemoteUser());
		this.getSession().put("modelName", this.getModelName());
		this.getSession().put("sbmlModel", modelString);		
		this.setTimestamp(new Timestamp(System.currentTimeMillis()));	 
		return SUCCESS;
	}
	
	public String doListSubCellularModelDirectory() throws Exception  {
		File file = new File(System.getenv("TOMCAT_HOME")+ "/manycell/samples");
		fileNames = file.list();		   	  	 
		return SUCCESS;
	}
	
	public String doLoadSubCellularModel() throws Exception  {		
		
		String modelString = null;	
		try {					
			modelString = fileProcessor.readFileAsString(System.getenv("TOMCAT_HOME")+ "/manycell/samples/" + this.getPathway()+"/"+ this.getPathway()+".xml");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}			
		
		ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);		
		this.setModelName(SMManager.getModelName());
		
		//testing
		DatabaseManager manager = new DatabaseManager();
		
		manager.createObjectArray(); 
		
		
		return SUCCESS;
	}
	
	public String doRunDeterministicSimulator() throws Exception  {		
		ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager();	
		
		java.net.InetAddress id = java.net.InetAddress.getLocalHost();	    	
		String host = java.net.InetAddress.getLocalHost().getHostName();
		String hostName = id.getCanonicalHostName(); 		
		
		//get the info from map and set the variables	
		File file = new File(System.getenv("TOMCAT_HOME")+ "/manycell/samples/" + this.getPathway()+"/"+ this.getPathway()+".xml");
		this.setInputFormat("sbml");
		this.setOutputFormat("sbrml");
		Map<String, String> map = SMManager.runTSSimAndPlotResult(host, hostName, file, String.valueOf(this.getStepSize()), String.valueOf(this.getStepNumber()), "sbml", "sbrml");
	//	System.out.println(map.get("plot"));
		setPlot("http://" + hostName+ ":8080/ManyCell/UI/Samples/Monoscale/" +map.get("plot"));
		setData("http://" + hostName+ ":8080/ManyCell/UI/Samples/Monoscale/" +map.get("data"));
		setResults(map.get("native"));
		return SUCCESS;
		
	}
	
	 public String doRunSensitivity() throws Exception  {
		 ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager();	
		 	java.net.InetAddress id = java.net.InetAddress.getLocalHost();	    	
		 	String host = java.net.InetAddress.getLocalHost().getHostName();		 	
		 	String hostName = id.getCanonicalHostName(); 
		 	
		 	File file = new File(System.getenv("TOMCAT_HOME")+ "/manycell/samples/" + this.getPathway()+"/"+ this.getPathway()+".xml");
			this.setInputFormat("sbml");
			this.setOutputFormat("sbrml");
		 	this.setResults("http://" + hostName+ ":8080/ManyCell/UI/Samples/Monoscale/" + SMManager.runSimulatorSBRMLResults(host, hostName, file, getInputFormat(), getOutputFormat(), getDeltaFactor(), getDeltaMinimum(), getFunction(), getVariable()));      	  	 
		 	  	 
		      return SUCCESS;
		  }
	
		public String doSubmitSubCellularModel() throws Exception  {			
			String modelString = (String)getSession().get("sbmlModel");
			
			speciesIds = new ArrayList<String>();
			speciesNames = new ArrayList<String>();			
			paramIds = new ArrayList<String>();
			paramNames = new ArrayList<String>();	
			
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);			
		//	SMManager.processSBMLModel(modelString, true);		
			this.setSpeciesIds(SMManager.getSpeciesIds());
			this.setSpeciesNames(SMManager.getSpeciesNames());
			this.setParamIds(SMManager.getParamIds());
			this.setParamNames(SMManager.getParamNames());	
			
			   	  	 
			return SUCCESS;
		}	
		
		public String doSubCellularSpecies() throws Exception  {
			List <Species> speciesSave = null;
			Species sp = new Species();
			sp.setName(this.getSName());
			sp.setValue(this.getSValue());
			
			String modelString = (String)getSession().get("sbmlModel");			
			
			speciesIds = new ArrayList<String>();
			speciesNames = new ArrayList<String>();			
			paramIds = new ArrayList<String>();
			paramNames = new ArrayList<String>();	
			
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);			
		//	SMManager.processSBMLModel(modelString, true);		
			this.setSpeciesIds(SMManager.getSpeciesIds());
			this.setSpeciesNames(SMManager.getSpeciesNames());
			this.setParamIds(SMManager.getParamIds());
			this.setParamNames(SMManager.getParamNames());	
			
			sp.setId(SMManager.getSpeciesIdByName(this.getSName()));			
			speciesSave = (List<Species>)getSession().get("ActiveSpecies");
			if(speciesSave!=null){			
				for(int i=0; i<speciesSave.size(); i++){					
					if(sp.getId().equals(speciesSave.get(i).getId())){
						speciesSave.remove(i);						
					}					
				}	
				speciesSave.add(sp);
			}
			else{
				speciesSave = new ArrayList<Species>();
				speciesSave.add(sp);
				
			}
			getSession().put("ActiveSpecies", speciesSave);
			this.setSpecies(speciesSave);		
			   	  	 
			return SUCCESS;
		}	
		
		public String doSaveSubCellularData() throws Exception  {
			String modelString = (String)getSession().get("sbmlModel");			
			List<Species> speciesSave = (List<Species>)getSession().get("ActiveSpecies");	
			this.setSpecies(speciesSave);
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);	
			this.setVersion(SMManager.getVersion());
			this.setModelName(SMManager.getModelName());
			getSession().put("modelName", this.getModelName());
		//	getSession().put("SubCellularData", "SubCellularData");
						
			return SUCCESS;
		}	
		
		public String doSubmitSubCellularData() throws Exception  {			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("startTime", this.getStartOutputTime());
			param.put("stepSize", this.getStepSize());
			param.put("stepNumber", this.getStepNumber());
			getSession().put("param", param);
			
			getSession().put("SubCellularData", "SubCellularData");
						
			return SUCCESS;
		}	
		
		public String doCheckSubCellularData() throws Exception  {
			this.setSubCellularData((String)getSession().get("SubCellularData"));
			if(this.getSubCellularData()==null) this.setUrl("/UI/Modelling/SubCellularDataChecker.jsp");
			if(this.getSubCellularData()!=null) this.setUrl("/UI/Modelling/CellularContent.jsp");
			return SUCCESS;
		}	

		//process cellular model data
		public String doSubmitCellularModel() throws Exception  {			
			String modelString = (String)getSession().get("sbmlModel");
			
			speciesIds = new ArrayList<String>();
			speciesNames = new ArrayList<String>();			
			paramIds = new ArrayList<String>();
			paramNames = new ArrayList<String>();	
			
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);			
		//	SMManager.processSBMLModel(modelString, true);		
			this.setSpeciesIds(SMManager.getSpeciesIds());
			this.setSpeciesNames(SMManager.getSpeciesNames());
			this.setParamIds(SMManager.getParamIds());
			this.setParamNames(SMManager.getParamNames());			
			   	  	 
			return SUCCESS;
		}	
		
		public String doCellularSpecies() throws Exception  {
			List <Species> speciesSave = null;
			Species sp = new Species();
			sp.setName(this.getSName());
			sp.setValue(this.getSValue());
			
			String modelString = (String)getSession().get("sbmlModel");			
			
			speciesIds = new ArrayList<String>();
			speciesNames = new ArrayList<String>();			
			paramIds = new ArrayList<String>();
			paramNames = new ArrayList<String>();	
			
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);			
		//	SMManager.processSBMLModel(modelString, true);		
			this.setSpeciesIds(SMManager.getSpeciesIds());
			this.setSpeciesNames(SMManager.getSpeciesNames());
			this.setParamIds(SMManager.getParamIds());
			this.setParamNames(SMManager.getParamNames());	
			
			sp.setId(SMManager.getSpeciesIdByName(this.getSName()));			
			speciesSave = (List<Species>)getSession().get("CellDiffSpecies");
			if(speciesSave!=null){			
				for(int i=0; i<speciesSave.size(); i++){					
					if(sp.getId().equals(speciesSave.get(i).getId())){
						speciesSave.remove(i);						
					}					
				}	
				speciesSave.add(sp);
			}
			else{
				speciesSave = new ArrayList<Species>();
				speciesSave.add(sp);
				
			}
			getSession().put("CellDiffSpecies", speciesSave);
			this.setSpecies(speciesSave);		
			   	  	 
			return SUCCESS;
		}	
		
		public String doSaveCellularData() throws Exception  {
			this.setSubCellularData((String)getSession().get("SubCellularData"));
			
			String modelString = (String)getSession().get("sbmlModel");			
			List<Species> speciesSave = (List<Species>)getSession().get("CellDiffSpecies");			
			this.setSpecies(speciesSave);
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);	
			this.setVersion(SMManager.getVersion());
			this.setModelName(SMManager.getModelName());
			getSession().put("CellularData", "CellularData");
			return SUCCESS;
		}
		
		public String doSubmitCellularData() throws Exception  {			
			getSession().put("NumberOfCells", this.getNumberOfCells());	
			CellEvent cellEvent = new CellEvent();
			Species cellSize = new Species();
			cellEvent.setParamName(this.getSPName());
			cellEvent.setEvenName(this.getBehaviour());
			String modelString = (String)getSession().get("sbmlModel");
			
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);			
			cellEvent.setParamId(SMManager.getParamIdByName(this.getSPName()));
			cellEvent.setParamValue((float)SMManager.getParamValueByName(this.getSPName()));			
			getSession().put("CellEvent", cellEvent);	
			
			//setting the cell size variable
			cellSize.setId(SMManager.getSpeciesIdByName(this.getSName()));
			cellSize.setValue((float)SMManager.getSpeciesValueByName(this.getSName()));	
			cellSize.setName(this.getSName());
			getSession().put("CellSize", cellSize);
			
			getSession().put("SizePartition", SMManager.getParamIdByName(this.getSSPName()));
			getSession().put("NutrientVariable", SMManager.getParamIdByName(this.getNUTPName()));
			
			getSession().put("CellularData", "CellularData");						
			return SUCCESS;
		}	
		
		public String doCheckCellularData() throws Exception  {
			this.setCellularData((String)getSession().get("CellularData"));
			if(this.getCellularData()==null){
				this.setUrl("/UI/Modelling/CellularDataChecker.jsp");			
			}
			else if(this.getCellularData()!=null){
				this.setUrl("/UI/Modelling/EnvironmentContent.jsp");				
			}
			return SUCCESS;
		}	
		
		//Process the Environment data		
		public String doSubmitEnvironmentModel() throws Exception  {						
			String modelString = (String)getSession().get("sbmlModel");
			
			speciesIds = new ArrayList<String>();
			speciesNames = new ArrayList<String>();			
			paramIds = new ArrayList<String>();
			paramNames = new ArrayList<String>();	
			
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);			
		//	SMManager.processSBMLModel(modelString, true);		
			this.setSpeciesIds(SMManager.getSpeciesIds());
			this.setSpeciesNames(SMManager.getSpeciesNames());
			this.setParamIds(SMManager.getParamIds());
			this.setParamNames(SMManager.getParamNames());
			
			
		//	getSession().put("EnvironmentData", "EnvironmentData");  	  	 
			return SUCCESS;
		}	
		
		public String doSubmitEnvironmentData() throws Exception  {				
			//get the model from the session and process it
			String modelString = (String)getSession().get("sbmlModel");
			ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);	
			
			//Nutrient species data
			Species sp = new Species();
			sp.setName(this.getSName());
			sp.setValue(this.getSValue());
			sp.setId(SMManager.getSpeciesIdByName(this.getSName()));			
			getSession().put("NutrientSP", sp);	
			
			//get the environment data ans store it
			Map<String, Object> envData = new HashMap<String, Object>();
			envData.put("type", this.getType());
			envData.put("volume", this.getVolume());
			envData.put("unit", this.getUnit());
			getSession().put("envData", envData);
			
			//indicate in session that environment data has been stored
			getSession().put("EnvironmentData", "EnvironmentData");						
			return SUCCESS;
		}	
}