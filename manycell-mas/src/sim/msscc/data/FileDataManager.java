package sim.msscc.data;

import java.io.File;

import org.copasi.utils.FileProcessor;

public class FileDataManager implements DataManagerInterface{
	//main diretory for storage
	String usersDirectory = "/usr/local/user-workspace/users";
	
	//variable for file processing
	FileProcessor fileProcessor = new FileProcessor();
	
	//store result in file
	public int storeTimeCourseResult(String userId, String CAID, String simulationRun,  String resultString, String resultMetadata){
		String pathDir = usersDirectory + "/"+userId+"/"+CAID+"/"+simulationRun;
		try{
			fileProcessor.writeToFile(resultString, pathDir+"/result.txt");			
		}catch(Exception e){//catch exception if any
			e.printStackTrace();
			return 1;
		}
		//return 0 for success
		return 0;
	}
	
	//store model in file
	public int storeModel(String userId, String CAID, String simulationRun, String modelString, String format, boolean isUpdatedModel){
		 //create temp dir for this job
		String pathDir = usersDirectory + "/"+userId+"/"+CAID+"/"+simulationRun;	
		try{		  
		    // Create multiple directories
		  boolean success = (new File(pathDir)).mkdirs();
		  String modelFilePrefix = "model";
		  String modelFileName = null;
		  if(isUpdatedModel)modelFilePrefix = "uModel";
		    if (success) {		    	
		      System.out.println("Directories: " + pathDir + " created");
		      if(format.equalsIgnoreCase("SBML")){
		    	  modelFileName = modelFilePrefix+".xml";
		      }else{modelFileName = modelFilePrefix+".cps";}		     
		      fileProcessor.writeToFile(modelString, pathDir+"/"+modelFileName);
		  //    fileProcessor.saveModelInSpecifiedDirectory(modelString, pathDir, modelFileName);
		    }

		    }catch (Exception e){//Catch exception if any
		      e.printStackTrace(); //.getMessage());
		    }       
		
		return 0;
	}	
	
	// load model from file
	public String loadModel(String userId, String CAID, String simulationRun, String format, boolean updatedModelRequire){
		String pathDir = usersDirectory + "/"+userId+"/"+CAID+"/"+simulationRun;

		String model = null;
		String modelFile = null;

		//make sure you send the appropriate model
		String modelFilePrefix = "model";		 
		if(updatedModelRequire)modelFilePrefix = "uModel";
		//check format require
		if(format.equalsIgnoreCase("SBML")){
			modelFile = modelFilePrefix+".xml";
		}else{modelFile =modelFilePrefix+".cps";}

		try{
			model = fileProcessor.readFileAsString(pathDir+"/"+modelFile);
		}catch(Exception e){//catch exception if any
			e.printStackTrace();
		}
		return model;
	}
	
	// load model from file
	public String loadModel(String pathName){		
		String model = null;
		try{
			model = fileProcessor.readFileAsString(pathName);
		}catch(Exception e){//catch exception if any
			e.printStackTrace();
		}
		return model;
	}
	
	// load model from file
/*	public void saveModel(String model, String pathName, String fileName){			
		try{
			fileProcessor.saveModelInSpecifiedDirectory(model, pathName, fileName);
		}catch(Exception e){//catch exception if any
			e.printStackTrace();
		}		
	}*/

	// retrieve results frmo file
	public String retrieveTimeCourseResult(String userId, String CAID, String simulationRun){
		return null; 
	}
	
	public int storeEventData(String CAID, String event, float eventTime,
			float globalTime, String stateatevent,  float GLUconsumed, int TCID) {
		return 0;
	}
	
	public int storeTimeCourseResult(String resultString) {
		return 0;
	}
	
	public EventData retrieveEventData(String CAID, String event, float simTime) {
		return null;
	}

}
