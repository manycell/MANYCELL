package sim.mssim.modelmanager;

import java.io.File;
import java.io.IOException;

import org.copasi.utils.FileProcessor;

public class SubCellularModelManager {
	
private FileProcessor fileProcessor;
	
	String modelDirectory = "/usr/local/user-workspace/models";

	public SubCellularModelManager(){	
		fileProcessor = new FileProcessor();		
	}

	public String submitModel(File model, String modelType, String cellType ){
		String result = null;
		String modelString = null;
		try{
			modelString = fileProcessor.readFileAsString(model.toString());
		}catch (IOException ioe){
			ioe.printStackTrace();
		}

		if(modelString!=null){
			try{			
				fileProcessor.saveModelInSpecifiedDirectory(modelString, modelDirectory, modelType + "_"+ cellType+".xml");
				result = "SUCCESS: Model successfully submitted";
			}catch (Exception e){
				e.printStackTrace();				
			}
		}		  
		return result;

	}
	
	public String saveModelInSession(File model, String modelType, String cellType ){
		String result = null;
		String modelString = null;
		try{
			modelString = fileProcessor.readFileAsString(model.toString());
		}catch (IOException ioe){
			ioe.printStackTrace();
		}

		if(modelString!=null){
			try{			
				fileProcessor.saveModelInSpecifiedDirectory(modelString, modelDirectory, modelType + "_"+ cellType+".xml");
				result = "SUCCESS: Model successfully submitted";
			}catch (Exception e){
				e.printStackTrace();				
			}
		}		  
		return result;

	}

}
