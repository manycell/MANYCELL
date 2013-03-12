package sim.jnative;

import org.sbml.libsbml.ListOfSpecies;
import org.sbml.libsbml.Model;
import org.sbml.libsbml.SBMLDocument;
import org.sbml.libsbml.SBMLReader;

public class PSConnector {
	private String [] speciesName;
	private String [] speciesId;
	private String SBMLModel;
	private String uModel;
	private String modelId;
	private int nSpecies = 0; 
	private int nNonConstSpecies = 0;
	private double[] sInitialValue;
	private float tolerance = 0.0005f;		
		
	private String simResult;
	
	private String webServiceAddress;
	
	
	
	public String getWebServiceAddress() {
		return webServiceAddress;
	}

	public void setWebServiceAddress(String webServiceAddress) {
		this.webServiceAddress = webServiceAddress;
	}

	public String getSimResult() {
		return simResult;
	}

	public void setSimResult(String simResult) {
		this.simResult = simResult;
	}

	public String getuModel() {
		return uModel;
	}

	public void setuModel(String uModel) {
		this.uModel = uModel;
	}

	public String[] getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String[] speciesName) {
		this.speciesName = speciesName;
	}

	public String[] getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(String[] speciesId) {
		this.speciesId = speciesId;
	}

	public String getSBMLModel() {
		return SBMLModel;
	}

	public void setSBMLModel(String sBMLModel) {
		SBMLModel = sBMLModel;
	}

	public double[] getsInitialValue() {		
		return sInitialValue;
	}

	public void setsInitialValue(double[] sInitialValue) {
		this.sInitialValue = sInitialValue;
	}	

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public int getnSpecies() {
		return nSpecies;
	}

	public void setnSpecies(int nSpecies) {
		this.nSpecies = nSpecies;
	}


	public int getnNonConstSpecies() {
		return nNonConstSpecies;
	}

	public void setnNonConstSpecies(int nNonConstSpecies) {
		this.nNonConstSpecies = nNonConstSpecies;
	}



	static {		
		System.loadLibrary("psconnector");
	} 
	
	static
	  {
	    try
	    {
	   // 	System.out.println("java.library.path="+System.getProperty("java.library.path"));
	      System.loadLibrary("sbmlj");
	     // Extra check to be sure we have access to libSBML: 
	      Class.forName("org.sbml.libsbml.libsbml");
	    }
	    catch (Exception e)
	    {
	      System.err.println("Error: could not load the libSBML library");
	      System.exit(1);
	    }
	  }

	 /**
	 * Create a pointer to the CopasiWS client
	 */	 
	public native long initSimulator(int simId);	
	
	public void setModelProperties(int pWayType){
		//prepare the model file
		String modelDirectory = "/usr/local/user-workspace/models";
		String modelFileName = null, sModel = null;

		switch(pWayType){
		case 1:
			modelId = "cellCycle";
			sModel = "BIOMD0000000056.xml"; 
			break;
		case 2:
			modelId = "glycolysis";
			sModel = "BIOMD0000000064.xml"; 
			break;
		case 3:
			modelId = "gGlycolysis";
			sModel = "BIOMD0000000245.xml"; 
			break;
		case 4:
			modelId = "MAPKK";
			sModel = "BIOMD0000000010.xml"; 
			break;			
		default: System.out.println("Invalid pathway type specification.");
		break;

		}

		modelFileName = modelDirectory + "/" + sModel;

		SBMLReader reader = new SBMLReader();
		SBMLDocument doc;
		Model model;
		ListOfSpecies sList;

		doc = reader.readSBMLFromFile(modelFileName);		
		model = doc.getModel();
		sList = model.getListOfSpecies();
		//counting number of non fixed species
		nSpecies = (int)sList.size();
		int counter = 0;// nSpecies;
		for(int i=0; i<nSpecies; i++){
			if((!sList.get(i).getBoundaryCondition() && sList.get(i).getConstant())|| (!sList.get(i).getBoundaryCondition() && !sList.get(i).getConstant())){
				counter++;
			}
		}
		nNonConstSpecies = counter;
	//	nSpecies = (int)sList.size();
		speciesId = new String[nSpecies];
		this.sInitialValue = new double[nSpecies];	
	//	int k = 0;
		for (int i=0; i<nSpecies; i++){
		//	if(!sList.get(i).getBoundaryCondition()){
			if(sList.get(i).isSetInitialAmount()){			
				this.sInitialValue[i] = sList.get(i).getInitialAmount();				
			}
			else{				
				this.sInitialValue[i] = sList.get(i).getInitialConcentration();
			}
			speciesId[i] = sList.get(i).getId();
		//	k = k+1;
		//	}
			
			//System.out.println("Ids: "+speciesId[i]+" Species initial value:  " +doc.getModel().getListOfSpecies().get(i).getInitialConcentration()); //sInitialValue[i]);
		}
				
	}	
	
	public void changeInitialCondition(int pWayType, double []changeSIValue, String[] speciesId){
		//prepare the model file
		String modelDirectory = "/usr/local/user-workspace/models";
		String modelFileName = null, sModel = null;

		switch(pWayType){
		case 1:
			modelId = "cellCycle";
			sModel = "BIOMD0000000056.xml"; 
			break;
		case 2:
			modelId = "glycolysis";
			sModel = "BIOMD0000000064.xml"; 
			break;
		case 3:
			modelId = "gGlycolysis";
			sModel = "BIOMD0000000245.xml"; 
			break;
		case 4:
			modelId = "MAPKK";
			sModel = "BIOMD0000000010.xml"; 
			break;			
		default: System.out.println("Invalid pathway type specification.");
		break;

		}

		modelFileName = modelDirectory + "/" + sModel;

		SBMLReader reader = new SBMLReader();
		SBMLDocument doc;
		Model model;
		ListOfSpecies sList;

		doc = reader.readSBMLFromFile(modelFileName);		
		//model = doc.getModel();
		sList = doc.getModel().getListOfSpecies();		
		nSpecies = speciesId.length; //(int)sList.size();
		this.speciesId = new String[nSpecies];
		this.speciesId = speciesId; //new String[nSpecies];
		
		this.sInitialValue = new double[nSpecies];
		
		this.sInitialValue = changeSIValue;
	//	for(int j = 0; j<nSpecies; j++){
		for (int i=0; i<(int)sList.size(); i++){			
			if(sList.get(i).isSetInitialAmount()){
				//if(sList.get(i).getId()==speciesId[j])
				doc.getModel().getListOfSpecies().get(i).setInitialAmount(changeSIValue[i]);								
			}
			else{				
			//	if(sList.get(i).getId()==speciesId[j]){					
				doc.getModel().getListOfSpecies().get(i).setInitialConcentration(changeSIValue[i]);	
			//	System.out.println("Get printed "+doc.getModel().getListOfSpecies().get(i).getInitialConcentration());
				//}
			}
			//	System.out.println("Ids: "+speciesId[j]+" Species initial value:  " +doc.getModel().getListOfSpecies().get(i).getInitialConcentration());

			
	//	}
		
		}
	//	doc.setModel(model);
		SBMLModel = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + doc.toSBML();		
	//	System.out.print(SBMLModel);
	}	

	/** Connect to the CopasiWS client 
	 * @param SBMLModel string, biochemical modle in SBML format to be run by the simulator
	 * @param modelId string, id to identify the model
	 * @param tolerance float, for ISAT tabulator
	 * @param speciesId string, array containing the model species Ids  
	 * @param sInitialValue float array, initial value of species
	 * @param stepSize java float, step size for the time course simulator
	 * @param nTimeSteps java int, number of time steps for the simulator
	 * @param nSpecies java int, number of species in the sbml model 
	 */
	public native float[][] runTCSimulatior(
			String webServiceAddress,
			String SBMLModel,
			String modelId,					
			float tolerance,
			String []speciesId,	
			double [] sInitialValue,
			float stepSize,
			int nTimeSteps,
			int sMatrixRow,
			int sMatrixCol,
			int nAssignmentSpecies,
			int nReactionSpecies,			
			int nConstantSpecies,
			long ptr);	

	public static void main(String[] args) {
		PSConnector connector = new PSConnector();
		int simulatorId = 1;
		long res=connector.initSimulator(simulatorId);
		System.out.println("Please wait for the result.....");
		
		//assign parameter values
		int nTimeSteps = 3, nSpecies = 15;
		int pWayType = 1; 
		float sSize = 1.0f;		
		double []sIValue = new double [nSpecies];

		for (int k = 0; k<nSpecies; k++){
			sIValue[k] = 0.034f;
		}		
		 
		/*float tolerance = 0.0005f;
		float [][] simResult = connector.runTCSimulatior(pWayType, tolerance, sIValue, sSize, nTimeSteps, nSpecies, res);       
		
		/*System.out.println();
		for (int i = 0; i < nTimeSteps+1; i++) {
			for (int j = 0; j < nSpecies; j++) {            	
				System.out.print("	" + simResult[i][j]);
			}
			System.out.println();
		}*/
	}

}
