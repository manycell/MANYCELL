package sim.mssim.modelmanager;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.axis2.AxisFault;
import org.apache.xmlbeans.XmlOptions;
import org.copasi.copasiws.schemas.OutputResult;
import org.copasi.copasiws.services.sensitivityanalysisws.SensitivityAnalysisServiceStub;
import org.copasi.copasiws.services.sensitivityanalysisws.types.Function;
import org.copasi.copasiws.services.sensitivityanalysisws.types.Parameters;
import org.copasi.copasiws.services.sensitivityanalysisws.types.RunSimulatorDocument;
import org.copasi.copasiws.services.sensitivityanalysisws.types.RunSimulatorResponseDocument;
import org.copasi.copasiws.services.sensitivityanalysisws.types.Variable;
import org.copasi.copasiws.services.timecoursews.TimeCourseServiceStub;
import org.copasi.copasiws.services.timecoursews.types.DeterministicParameters;
import org.copasi.copasiws.services.timecoursews.types.RunDeterministicSimulatorDocument;
import org.copasi.copasiws.services.timecoursews.types.RunDeterministicSimulatorResponseDocument;
import org.copasi.processor.SBRMLProcessor;
import org.copasi.utils.FileProcessor;
import org.copasi.utils.SimpleGraphPloter;
import org.manycell.config.ManycellDocument;
import org.manycell.config.ManycellDocument.Manycell;
import org.sbml.libsbml.ListOfParameters;
import org.sbml.libsbml.ListOfSpecies;
import org.sbml.libsbml.Model;
import org.sbml.libsbml.SBMLDocument;
import org.sbml.libsbml.SBMLReader;

public class ProcessSBMLModelManager {	
		
	ListOfSpecies sList;
	ListOfParameters pList;
	private List<String> speciesIds = new ArrayList<String>();
	private List<String> speciesNames = new ArrayList<String>();
	private List<String>paramIds = new ArrayList<String>();
	private List<String> paramNames = new ArrayList<String>();	
	private String modelName = null;
	private int version;
	private FileProcessor fileProcessor;
	
	Map<String, Object> sNMap = new HashMap<String, Object>();
	Map<String, Object> pNMap = new HashMap<String, Object>();
	
	public ProcessSBMLModelManager(){
		
	}
	public ProcessSBMLModelManager(String fileNameWithPath, boolean isModelString){
		fileProcessor = new FileProcessor();
		SBMLReader reader = new SBMLReader();
		SBMLDocument doc = null;
		Model sbmlModel;
	//	ListOfSpecies sList;
	//	ListOfParameters pList;
		
		try{
			if(isModelString){
			doc = reader.readSBMLFromString(fileNameWithPath);
			}else{
				doc = reader.readSBMLFromFile(fileNameWithPath);
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		sbmlModel = doc.getModel();	
		this.setModelName(sbmlModel.getName());
		this.setVersion((int)sbmlModel.getVersion());
		sList = sbmlModel.getListOfSpecies();
		pList = sbmlModel.getListOfParameters();
		
		if(sList!=null){
			for(int i=0; i<sList.size(); i++){
				speciesIds.add(sList.get(i).getId());
				speciesNames.add(sList.get(i).getName());				
			}
		}
		
		if(pList!=null){
			for(int i=0; i<pList.size(); i++){
				paramIds.add(pList.get(i).getId());
				paramNames.add(pList.get(i).getName());
			}
		}
	}	
	
	public int getVersion() {
		return version;
	}



	public void setVersion(int version) {
		this.version = version;
	}



	public String [] getModelSpeciesIds(){
		return null;
	}	
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String [] getModelSpeciesNames(){
		return null;
	}
	public String getModelItemValue(String mId, Map<String, Object> map){
		if(map.containsKey(mId)){
			return (String)(map.get(mId));			
		}else{
			return null;
		}
	}
	
	
	public void processSBMLModel(String fileNameWithPath, boolean isModelString) {
		SBMLReader reader = new SBMLReader();
		SBMLDocument doc = null;
		Model sbmlModel;
		ListOfSpecies sList;
		ListOfParameters pList;
		
		try{
			if(isModelString){
			doc = reader.readSBMLFromString(fileNameWithPath);
			}else{
				doc = reader.readSBMLFromFile(fileNameWithPath);
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		sbmlModel = doc.getModel();	
		sList = sbmlModel.getListOfSpecies();
		pList = sbmlModel.getListOfParameters();
		
		if(sList!=null){
			for(int i=0; i<sList.size(); i++){
				speciesIds.add(sList.get(i).getId());
				speciesNames.add(sList.get(i).getName());				
			}
		}
		
		if(pList!=null){
			for(int i=0; i<pList.size(); i++){
				paramIds.add(pList.get(i).getId());
				paramNames.add(pList.get(i).getName());
			}
		}
	}
	
	public double getSpeciesValueByName(String speciesName){
		String name = null;
		double value = 0.0;
		if(sList!=null){
			for(int i=0; i<sList.size(); i++){				
				name = speciesNames.get(i);
				if(name.equals(speciesName)){
					if(sList.get(i).isSetInitialAmount()){
						value = sList.get(i).getInitialAmount();
					}else{
						value = sList.get(i).getInitialConcentration();	
					}
				
				i=i+9999999; //jump out of the loop
				}
			}
		}
		return value;
}
	
	public String getSpeciesIdByName(String speciesName){
		String name = null;
		String id = null;
		if(sList!=null){
			for(int i=0; i<sList.size(); i++){				
				name = speciesNames.get(i);
				if(name.equals(speciesName)){
				id = sList.get(i).getId();	
				i=i+9999999; //jump out of the loop
				}
			}
		}
		return id;
}
	
	public double getParamValueByName(String paramName){
		String name = null;
		double value = 0.0;
		if(pList!=null){
			for(int i=0; i<pList.size(); i++){				
				name = paramNames.get(i);				
				if(name.equals(paramName)){					
				value = pList.get(i).getValue();				
				i=i+9999999; //jump out of the loop
				}
			}
		}
		return value;
}
	
	public String getParamIdByName(String paramName){
		String name = null;
		String id = null;
		if(pList!=null){
			for(int i=0; i<pList.size(); i++){				
				name = paramNames.get(i);
				if(name.equals(paramName)){
				id = pList.get(i).getId();	
				i=i+9999999; //jump out of the loop
				}
			}
		}
		return id;
}
	
	public List<String> getSpeciesIds(){		
		return speciesIds;
	}
	
	public List<String> getSpeciesNames(){		
		return speciesNames;
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

	public void setSpeciesIds(List<String> speciesIds) {
		this.speciesIds = speciesIds;
	}

	public void setSpeciesNames(List<String> speciesNames) {
		this.speciesNames = speciesNames;
	}	
	
public Map<String, String> runTSSimAndPlotResult(String host, String hostName, File model, String stepSize, String stepNumber, String userInputFormat, String userOutputFormat){
    	
    	//the processor
		FileProcessor fileProcessor = new FileProcessor();
	//	SBRMLProcessor sbrmlProcessor = new SBRMLProcessor();		
		Map<String, Object> operationMap = new HashMap<String, Object>();
		List<String> modelObjectList = new ArrayList<String>();
    	
    	Random rand = new Random();
        int n = 1000000000;
        int fileId = rand.nextInt(n + 1) + 1;
            	
    	
    	String results = null;       	
    	File file = null;    	
    	
    	String serviceAddress = "http://" + hostName+ ":8080/CopasiWS/services/TimeCourseService";
    	TimeCourseServiceStub stub;    	    
    	
    	RunDeterministicSimulatorDocument reqDoc = RunDeterministicSimulatorDocument.Factory.newInstance();
    	
    	RunDeterministicSimulatorDocument.RunDeterministicSimulator reqElement	=
    		RunDeterministicSimulatorDocument.RunDeterministicSimulator.Factory.newInstance();   
    	
    	DeterministicParameters params =  DeterministicParameters.Factory.newInstance();
    	DeterministicParameters.OutputFormat.Enum outputFormat = DeterministicParameters.OutputFormat.TEXT;
    	DeterministicParameters.InputFormat.Enum inputFormat; 
    	
    	params.setIntegrateReducedModel(true);
    	params.setAdamsMaxOrder(BigInteger.valueOf(12));
    	    	
    	Long tmp1 =Long.valueOf(stepNumber);
    	if(tmp1>500l) tmp1 = 500l;
    	Float tmp2 = Float.valueOf(stepSize);
    	params.setStepNumber(BigInteger.valueOf(tmp1));
    	params.setStepSize(Float.valueOf(stepSize)); 
    	
    	float tmp = tmp1 * tmp2;
    	params.setDuration(tmp);
    	params.setOutputStartTime(0f);
    	   
    	if(userInputFormat.equals("sbml")){        	
        	inputFormat = DeterministicParameters.InputFormat.SBML;        		 
   	 	}else{   	 	
   	 		inputFormat = DeterministicParameters.InputFormat.COPASI_ML;		 
   	 	} 
        
        if(userOutputFormat.equals("sbrml")){        	
        	outputFormat = DeterministicParameters.OutputFormat.SBRML;               	 		 
   	 	}else{
   	 		outputFormat = DeterministicParameters.OutputFormat.TEXT;
   	 	} 
        params.setInputFormat(inputFormat);   	
        params.setOutputFormat(outputFormat);         
        
        //sbrml processor
        SBRMLProcessor sbrmlProcessor = new SBRMLProcessor();
    	
        try
        {        	
        	stub = new TimeCourseServiceStub(serviceAddress); 
        	if(userInputFormat.equals("sbml")){
        		params.setSbml(fileProcessor.readFileAsString(model.toString()));             	       		  
        	}else{
        		params.setCopasiml(fileProcessor.readFileAsString(model.toString()));               	
        	}
        	
        	reqElement.setParameters(params);
        	reqDoc.setRunDeterministicSimulator(reqElement);     	
              	
        	RunDeterministicSimulatorResponseDocument resDoc = stub.runDeterministicSimulator(reqDoc); 
        	
        	RunDeterministicSimulatorResponseDocument.RunDeterministicSimulatorResponse resElement = resDoc.getRunDeterministicSimulatorResponse();
        	OutputResult outputResult = resElement.getOutputResult();
        	results = outputResult.getResult();        	       	
        	
        	//clean the mess
            stub.cleanup();         
          //  file.deleteOnExit();        	
        }
        catch (AxisFault axisFault) {
        	axisFault.printStackTrace();
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        } 
        
        Map<String, String> map = new HashMap<String, String>();
        String tomcatHome = System.getenv("TOMCAT_HOME");
        System.out.print(tomcatHome);
		String directory = tomcatHome +"/webapps/ManyCell/UI/Samples/Monoscale";

		String plotOut = "tmpPlot_" + Math.random();
		String sbrmlOut = "sbrml_" + Math.random();
		String tableDataOut = "table_" + Math.random();
		File plotFile = new File(directory +"/"+plotOut+".png");
		File sbrmlFile = new File(directory +"/"+sbrmlOut+".xml");
		File tableDataFile = new File(directory +"/"+tableDataOut+".txt");

		//testing the result by plotting it using Gnuplot
		SimpleGraphPloter ploter = new SimpleGraphPloter();
		
		try{
			fileProcessor.saveModelInSpecifiedDirectory(results, directory, "/" + sbrmlOut +".xml");
			operationMap = sbrmlProcessor.convertSBRMLToTableFileFormat(sbrmlFile.toString(), tableDataFile.toString()); //.directory + "/sbrmlTableData.txt");
			modelObjectList = (List<String>)operationMap.get("0modelObjectId");			

			//get the model id from the map
			String[] modelObjectId = (String[]) modelObjectList.toArray((new String[0]));

			//create the plot graphic		
			ploter.plotGraphFromSBRML(tableDataFile.toString(), plotFile.toString(), modelObjectId);
		}catch(Exception e){
			System.out.println(e.toString());

		}	
		plotFile.deleteOnExit();
		sbrmlFile.deleteOnExit();
		tableDataFile.deleteOnExit();
		map.put("plot", plotOut +".png");
		map.put("data", sbrmlOut +".xml");
		map.put("native", results);
		return map;
	
    }   

public String runSimulatorSBRMLResults(String host, String hostName, File model, String userInputFormat, String userOutputFormat, String deltaFactor, String deltaMinimum, String function, String variable){
	FileProcessor fileProcessor = new FileProcessor();
	String results = null;     	
	File file = null;
	String tomcatHome = System.getenv("TOMCAT_HOME");
	String serviceAddress = "http://" + hostName+ ":8080/CopasiWS/services/SensitivityAnalysisService";        
	SensitivityAnalysisServiceStub stub; 
	RunSimulatorDocument reqDoc = RunSimulatorDocument.Factory.newInstance();
	
	RunSimulatorDocument.RunSimulator reqElement	=
		RunSimulatorDocument.RunSimulator.Factory.newInstance();       	

	Parameters params =  Parameters.Factory.newInstance();
	Parameters.OutputFormat.Enum outputFormat = Parameters.OutputFormat.TEXT;
	Parameters.InputFormat.Enum inputFormat; 
    
    if(userInputFormat.equals("sbml")){        	
    	inputFormat = Parameters.InputFormat.SBML;        		 
	 	}else{   	 	
	 		inputFormat = Parameters.InputFormat.COPASI_ML;		 
	 	} 
    
    if(userOutputFormat.equals("sbrml")){        	
    	outputFormat = Parameters.OutputFormat.SBRML;               	 		 
	 	}else{
	 		outputFormat = Parameters.OutputFormat.TEXT;
	 	} 
    params.setInputFormat(inputFormat);   	
    params.setOutputFormat(outputFormat);       
    
       	
    try
    {     
    	if(function.equalsIgnoreCase("Concentration Fluxes of Reaction")){
        	params.setFunction(Function.CONCENTRATION_FLUXES_OF_REACTION);
        }else if(function.equalsIgnoreCase("Non-Constant Concentration of Species")){
        	params.setFunction(Function.NON_CONSTANT_CONCENTRATION_OF_SPECIES);
        }
    	if(variable.equalsIgnoreCase("All Parameter Values")){
        	params.setVariable(Variable.ALL_PARAMETER_VALUES);
        }else if(variable.equalsIgnoreCase("Initial Concentration")){
        	params.setVariable(Variable.INITIAL_CONCENTRATION);
        }
    	stub = new SensitivityAnalysisServiceStub(serviceAddress); 
    	
    	if(userInputFormat.equals("sbml")){
    		params.setSbml(fileProcessor.readFileAsString(model.toString()));             	       		  
    	}else{
    		params.setCopasiml(fileProcessor.readFileAsString(model.toString()));               	
    	}
    	
    	if(deltaFactor!=null){
    		params.setDeltaFactor(Float.parseFloat(deltaFactor));    		
    	}
    	
    	if(deltaFactor!=null){
    		params.setDeltaMinimum(Float.parseFloat(deltaMinimum));
    	}
    	reqElement.setParameters(params);
    	reqDoc.setRunSimulator(reqElement);         	
    	RunSimulatorResponseDocument resDoc = stub.runSimulator(reqDoc);  
    	
    	RunSimulatorResponseDocument.RunSimulatorResponse resElement = resDoc.getRunSimulatorResponse();
    	OutputResult outputResult = resElement.getOutputResult();
    	results = outputResult.getResult();
    	String resultFormat = outputResult.getFormat().toString();
    	
    	//create a tmp file for result output in SBRML format, this will be deleted any time the server is restarted        	
    //	File directory = new File("/fs/garfinkel/dada/Copasi/applications/apache-tomcat-6.0.14/webapps/CopasiWeb/CopasiWebUI");        	
    	
    	if(resultFormat.equals("sbrml")){ 
    		File directory = new File(tomcatHome +"/webapps/ManyCell/UI/Samples/Monoscale/");       		
    		file = fileProcessor.saveModelInSpecifiedDirectory(results, "sbrmlFile", ".xml", directory); 
    		results = file.getName();
    		//delete file on exit                      
            file.deleteOnExit();
    	}      	
    	
    	//clean the mess
        stub.cleanup();  
    	
    }
    catch (AxisFault axisFault) {
    	axisFault.printStackTrace();
    }
    catch (Exception ex) {
    	ex.printStackTrace();
    }       
    return results;
}
	
	

}
