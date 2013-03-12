package sim.mssim.action.result;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.copasi.utils.FileProcessor;

import sim.mssim.action.MSSimSupportAction;
import sim.mssim.modelmanager.ProcessSBMLModelManager;

public class ResultAction extends MSSimSupportAction implements	SessionAware {
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
	
	private FileProcessor fileProcessor;
	private String url;
	private String pathway; //="model-1"; //if user decide to use models from ManyCells
	private String modelName = null;
	private String [] fileNames;
	
		// session for holding data
	private Map<String, Object> session;	
	
	public ResultAction() {
		fileProcessor = new FileProcessor();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
		
	
	public String getPathway() {
		return pathway;
	}

	public void setPathway(String pathway) {
		this.pathway = pathway;
	}	

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}	

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public Map<String, Object> getSession() {
			return session;
		}

	public void setSession(Map session) {
		this.session = session;
	}
	
	public String doListModelDirForResults() throws Exception  {
		File file = new File(System.getenv("TOMCAT_HOME")+ "/manycell/samples");
		fileNames = file.list();		   	  	 
		return SUCCESS;
	}

	public String doGetResults() throws Exception  {
		String modelString = null;	
		try {					
			modelString = fileProcessor.readFileAsString(System.getenv("TOMCAT_HOME")+ "/manycell/samples/" + this.getPathway()+"/"+ this.getPathway()+".xml");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}			
		
		ProcessSBMLModelManager SMManager = new ProcessSBMLModelManager(modelString, true);		
		this.setModelName(SMManager.getModelName());
		
		if(this.getPathway().equals("model-1")){
			this.setUrl("/UI/Results/Chen-Graphs.jsp");			
		}
		else if(this.getPathway().equals("model-2")){			
			//sub-cellular section			
			this.setUrl("/UI/Results/Sp-Graphs.jsp");						
		}
		else if(this.getPathway().equals("model-3")){			
			//sub-cellular section			
			this.setUrl("/UI/Results/Chen-245-Graphs.jsp");						
		}
		else if(this.getPathway().equals("model-4")){			
			//sub-cellular section			
			this.setUrl("/UI/Results/Alcorn-Graphs.jsp");						
		}
		return SUCCESS;
	}	

}
