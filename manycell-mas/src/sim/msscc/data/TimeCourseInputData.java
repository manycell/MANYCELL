package sim.msscc.data;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.sbml.libsbml.ListOfParameters;
import org.sbml.libsbml.ListOfSpecies;
import org.sbml.libsbml.Model;
import org.sbml.libsbml.SBMLDocument;
import org.sbml.libsbml.SBMLReader;

//import org.sbml.jsbml.*;

public class TimeCourseInputData {	
	private String model;
	private double[]speciesInitialCondition;
	private float stepSize;
	private int nTimeSteps;
	
	private int sMatrixRow;
	private int sMatrixCol;
	
	private int nSpecies;
	private String []speciesId;
	
	private float tolerance;	
	private float sumOfGLUConsumed;
	private double cellVolume; // = 10e-10f;
	private double environmentVolume; 
	private double currentGlucose;	
	private float cellBirthSize;
	
	private boolean isNewCell;
	
	private String biomassVariable = null; //"MASS";
//	private String GLUVariable = "species_1";
	private String nutrientModelId	=	null;
	private String budVariable = null;// "species_5" ; //"BUD";	
	
	Map<String, Float> changeVariableIdsValuesMap;
	private String partitionFunction = null; //"F";
	private String eventVariable = null; 
	private float eventVariableValue = -1f; 
	
	//monitoring the species types (reaction, assignment, constant)
	int nReactionSpecies;
	int nConstantSpecies;
	int nAssignmentSpecies;
		
	//construct an object to set variables from model
	public TimeCourseInputData(String model){
		super();
		this.model = model;
		//this.exstractInitialCondition(model);
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

	
	public Map<String, Float> getChangeVariableIdsValuesMap() {
		return changeVariableIdsValuesMap;
	}

	public void setChangeVariableIdsValuesMap(
			Map<String, Float> changeVariableIdsValuesMap) {
		this.changeVariableIdsValuesMap = changeVariableIdsValuesMap;
	}

	public String getBiomassVariable() {
		return biomassVariable;
	}

	public void setBiomassVariable(String biomassVariable) {
		this.biomassVariable = biomassVariable;
	}
	
	public String getNutrientModelId() {
		return nutrientModelId;
	}

	public void setNutrientModelId(String nutrientModelId) {
		this.nutrientModelId = nutrientModelId;
	}	

	public String getBudVariable() {
		return budVariable;
	}

	public void setBudVariable(String budVariable) {
		this.budVariable = budVariable;
	}

	public String getPartitionFunction() {
		return partitionFunction;
	}

	public void setPartitionFunction(String partitionFunction) {
		this.partitionFunction = partitionFunction;
	}	

	public String getEventVariable() {
		return eventVariable;
	}

	public void setEventVariable(String eventVariable) {
		this.eventVariable = eventVariable;
	}

	public float getEventVariableValue() {
		return eventVariableValue;
	}

	public void setEventVariableValue(float eventVariableValue) {
		this.eventVariableValue = eventVariableValue;
	}

	public int getnReactionSpecies() {
		return nReactionSpecies;
	}

	public void setnReactionSpecies(int nReactionSpecies) {
		this.nReactionSpecies = nReactionSpecies;
	}

	public int getnConstantSpecies() {
		return nConstantSpecies;
	}

	public void setnConstantSpecies(int nConstantSpecies) {
		this.nConstantSpecies = nConstantSpecies;
	}

	public int getnAssignmentSpecies() {
		return nAssignmentSpecies;
	}

	public void setnAssignmentSpecies(int nAssignmentSpecies) {
		this.nAssignmentSpecies = nAssignmentSpecies;
	}


	public double getEnvironmentVolume() {
		return environmentVolume;
	}

	public void setEnvironmentVolume(double environmentVolume) {
		this.environmentVolume = environmentVolume;
	}

	public double getCellVolume() {
		return cellVolume;
	}



	public void setCellVolume(double cellVolume) {
		this.cellVolume = cellVolume;
	}



	public boolean isNewCell() {
		return isNewCell;
	}



	public void setNewCell(boolean isNewCell) {
		this.isNewCell = isNewCell;
	}



	public float getCellBirthSize() {
		return cellBirthSize;
	}



	public void setCellBirthSize(float cellBirthSize) {
		this.cellBirthSize = cellBirthSize;
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


	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double[] getSpeciesInitialCondition() {
		return speciesInitialCondition;
	}
	public void setSpeciesInitialCondition(double[] speciesInitialCondition) {
		this.speciesInitialCondition = speciesInitialCondition;
	}
	public float getStepSize() {
		return stepSize;
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
	public int getnSpecies() {
		return nSpecies;
	}
	public void setnSpecies(int nSpecies) {
		this.nSpecies = nSpecies;
	}
	public String[] getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(String[] speciesId) {
		this.speciesId = speciesId;
	}
	public float getTolerance() {
		return tolerance;
	}
	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}
	
	
	public int getsMatrixRow() {
		return sMatrixRow;
	}

	public void setsMatrixRow(int sMatrixRow) {
		this.sMatrixRow = sMatrixRow;
	}

	public int getsMatrixCol() {
		return sMatrixCol;
	}

	public void setsMatrixCol(int sMatrixCol) {
		this.sMatrixCol = sMatrixCol;
	}



	// exstract initial condition from model
	public void exstractInitialCondition() {
	//	this.model = modelString;		
		SBMLReader reader = new SBMLReader();
		
		SBMLDocument doc = null;		
		Model sbmlModel;
		
		
		ListOfSpecies sList;

		try{
		doc = reader.readSBMLFromString(model);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	/*	ListOfSpecies sList;

		doc = reader.readSBMLFromString(modelString);*/
		
	//TODO	ArrayList<String> spList = new ArrayList<String>(); 
		ArrayList<String> spRowList = new ArrayList<String>(); 
		ArrayList<String> spColList = new ArrayList<String>(); 
		ArrayList<String> spConstList = new ArrayList<String>(); 
		ArrayList<String> spBoundList = new ArrayList<String>(); 
		
		sbmlModel = doc.getModel();		
		sList = sbmlModel.getListOfSpecies();
		// counting number of non fixed species
		nSpecies = (int) sList.size();		
		int rowCounter = 0;
	/** TODO	for (int i = 0; i < nSpecies; i++) {
			//if ((!sList.get(i).getBoundaryCondition() && sList.get(i).getConstant()) || (!sList.get(i).getBoundaryCondition() && !sList.get(i).getConstant())) {
			//changed to test something
				if ((sList.get(i).getConstant()) || (!sList.get(i).getBoundaryCondition() && !sList.get(i).getConstant())) {
					if ((!sList.get(i).getConstant())) {
					//add specise id to the list
					spList.add(sList.get(i).getId());	
					rowCounter++;
				}
				}
			//	if ((!sList.get(i).getConstant())) {
		//		rowCounter++;
			//	}
				//set the nutrient level
				if(doc.getModel().getListOfSpecies().get(i).getId().equals(GLUVariable)){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCurrentGlucose()); //set the initial nutrient level
				}
		} */
		
		//=========taking all reaction and assignment species into consideration==========
		for (int i = 0; i < nSpecies; i++) {	
			if ((!sList.get(i).getConstant())) {				
				spRowList.add(sList.get(i).getId());
						rowCounter++;
				}
			if ((sList.get(i).getConstant()) || (!sList.get(i).getBoundaryCondition() && !sList.get(i).getConstant())) {	
				if ((!sList.get(i).getConstant())) {
					//add specise id to the list
					spColList.add(sList.get(i).getId());						
				}else if ((sList.get(i).getConstant())) {
					spConstList.add(sList.get(i).getId());	 
				}else if(sList.get(i).getBoundaryCondition()){
					spBoundList.add(sList.get(i).getId());
				}
				
				}			
				//set the nutrient level
				if(doc.getModel().getListOfSpecies().get(i).getId().equals(this.nutrientModelId)){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCurrentGlucose()); //set the initial nutrient level
				}
		}
		//====================================================================================
		// sort species id list
	//TODO	java.util.Collections.sort(spList);
		java.util.Collections.sort(spColList);
		java.util.Collections.sort(spRowList);
		java.util.Collections.sort(spConstList);
		java.util.Collections.sort(spBoundList);
		
		//counting the species
		nReactionSpecies = spColList.size();
		nAssignmentSpecies = spBoundList.size();
		nConstantSpecies = spConstList.size();
		
		for(int i=0; i<spConstList.size(); i++){
			spRowList.add(spConstList.get(i));
		}
		
		//add the assignment species to the col list
		for(int i=0; i<spBoundList.size(); i++){
			spColList.add(spBoundList.get(i));
		}
		//add the constant species
		for(int i=0; i<spConstList.size(); i++){
			spColList.add(spConstList.get(i));
		}

		//========================================================================
		//set the row and column of sensitivity matrix
	//TODO	sMatrixRow = rowCounter;	
		
		//====
		sMatrixRow = spRowList.size();	
		sMatrixCol = spColList.size();		
		//======
		
	//TODO	sMatrixCol = spList.size();
	//	System.out.println("Species "+ sMatrixCol+ "Row species: "+ rowCounter);			
		
		        
		// iterate through list and print result
	//	for (String str : spList)
		 //   System.out.println(str);
		
		
		speciesId = new String[sMatrixRow];
		this.speciesInitialCondition = new double[sMatrixRow];

		for (int i = 0; i < nSpecies; i++) {			
			for(int j=0; j<sMatrixRow; j++){
				if(spRowList.get(j).equals(sList.get(i).getId())){
					if (sList.get(i).isSetInitialAmount()) {
						this.speciesInitialCondition[j] = sList.get(i).getInitialAmount();
					} else {						
						if(doc.getModel().getListOfSpecies().get(i).getId().equals(this.getBiomassVariable())){
								doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCellBirthSize()); //set the birth size to new value
						}	
						
						//set the values of the variables to change
						for(int ii=0; ii<this.changeVariableIdsValuesMap.size(); ii++){
							if(this.changeVariableIdsValuesMap.containsKey(doc.getModel().getListOfSpecies().get(i).getId())){
								doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.changeVariableIdsValuesMap.get(doc.getModel().getListOfSpecies().get(i).getId()));
							}
						}
						
						this.speciesInitialCondition[j] = sList.get(i).getInitialConcentration();
						
						
					//	this.speciesInitialCondition[j] = sList.get(i).getInitialConcentration();
					}
					
					
					speciesId[j] = sList.get(i).getId(); //spRowList.get(j);
				//	System.out.println("Intial Con form Model: "+ speciesId[j]+"=="+speciesInitialCondition[j]);
				}
			}

		}
	//	nSpecies = speciesId.length;


		//	=====================================================================



		// nNonConstSpecies = counter;
		/*	speciesId = new String[nSpecies];
		this.speciesInitialCondition = new double[nSpecies];
		// int k = 0;
		for (int i = 0; i < nSpecies; i++) {
			// if(!sList.get(i).getBoundaryCondition()){
			if (sList.get(i).isSetInitialAmount()) {
				this.speciesInitialCondition[i] = sList.get(i).getInitialAmount();
			} else {
				if(doc.getModel().getListOfSpecies().get(i).getId().equals(biomassVariable)){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCellBirthSize()); //set the birth size to new value
				}
				this.speciesInitialCondition[i] = sList.get(i).getInitialConcentration();
			}
			speciesId[i] = sList.get(i).getId();

		}*/
		model = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + doc.toSBML();
		sbmlModel.delete();
		sList.delete();
		doc.delete();		 
		reader.delete();
	}
	
	//set intial condition
	public void setInitialCondition() {
		//	this.model = modelString;		
			SBMLReader reader = new SBMLReader();
			
			SBMLDocument doc = null;		
			Model sbmlModel;
			
			
			ListOfSpecies sList;

			try{
				doc = reader.readSBMLFromString(model);
			}catch(Exception e){
				e.printStackTrace();
			}		

			//ArrayList<String> spList = new ArrayList<String>(); 
			
			ArrayList<String> spRowList = new ArrayList<String>(); 
			ArrayList<String> spColList = new ArrayList<String>(); 
			ArrayList<String> spConstList = new ArrayList<String>(); 
			ArrayList<String> spBoundList = new ArrayList<String>(); 
			
			sbmlModel = doc.getModel();		
			sList = sbmlModel.getListOfSpecies();
			// counting number of non fixed species
			nSpecies = (int) sList.size();		
			int rowCounter = 0;
			
	/*	 TODO	for (int i = 0; i < nSpecies; i++) {
				if ((sList.get(i).getConstant()) || (!sList.get(i).getBoundaryCondition() && !sList.get(i).getConstant())) {
					if ((!sList.get(i).getConstant())) {
						//add specise id to the list
						spList.add(sList.get(i).getId());	
						rowCounter++;
					}
				}			
			}			*/
			
		//=========taking all reaction and assignment species into consideration==========
			for (int i = 0; i < nSpecies; i++) {	
				if ((!sList.get(i).getConstant())) {				
					spRowList.add(sList.get(i).getId());
							rowCounter++;
					}
				if ((sList.get(i).getConstant()) || (!sList.get(i).getBoundaryCondition() && !sList.get(i).getConstant())) {	
					if ((!sList.get(i).getConstant())) {
						//add specise id to the list
						spColList.add(sList.get(i).getId());						
					}else if ((sList.get(i).getConstant())) {
						spConstList.add(sList.get(i).getId());	 
					}else if(sList.get(i).getBoundaryCondition()){
						spBoundList.add(sList.get(i).getId());
					}
					
					}			
					//set the nutrient level
					if(doc.getModel().getListOfSpecies().get(i).getId().equals(this.nutrientModelId)){
						doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCurrentGlucose()); //set the initial nutrient level
					}
			}
			//====================================================================================

			//========================================================================
			// sort species id list			
			//java.util.Collections.sort(spList);	
			
			java.util.Collections.sort(spColList);
			java.util.Collections.sort(spRowList);
			java.util.Collections.sort(spConstList);
			java.util.Collections.sort(spBoundList);
			
			//counting the species
			nReactionSpecies = spColList.size();
			nAssignmentSpecies = spBoundList.size();
			nConstantSpecies = spConstList.size();
			
			for(int i=0; i<spConstList.size(); i++){
				spRowList.add(spConstList.get(i));
			}
			
			//add the assignment species to the col list
			for(int i=0; i<spBoundList.size(); i++){
				spColList.add(spBoundList.get(i));
			}
			//add the constant species
			for(int i=0; i<spConstList.size(); i++){
				spColList.add(spConstList.get(i));
			}
			
			//set the row and column of sensitivity matrix
		//	sMatrixRow = rowCounter;	

			//sMatrixCol = spList.size();
			//====
			sMatrixRow = spRowList.size();	
			sMatrixCol = spColList.size();		
			//======
				

			speciesId = new String[sMatrixRow];
			this.speciesInitialCondition = new double[sMatrixRow];

			for (int i = 0; i < nSpecies; i++) {
				for(int j=0; j<sMatrixRow; j++){
					if(spRowList.get(j).equals(sList.get(i).getId())){
						if (sList.get(i).isSetInitialAmount()) {
							this.speciesInitialCondition[j] = sList.get(i).getInitialAmount();
						} else {							
							this.speciesInitialCondition[j] = sList.get(i).getInitialConcentration();
						}
						speciesId[j] = sList.get(i).getId(); //spRowList.get(j);
						//	System.out.println("Intial Con form Model: "+ speciesId[j]+"=="+speciesInitialCondition[j]);
					}
				}

			}

			//	model = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + doc.toSBML();
			sbmlModel.delete();
			sList.delete();
			doc.delete();		 
			reader.delete();
	}

	
	/** the initial condition from model
	 * @param modelString
	 */
	public void exstractInitialCondition(String modelString) {
		this.model = modelString;		
		SBMLReader reader = new SBMLReader();
		SBMLDocument doc = null;
		Model sbmlModel;


		ListOfSpecies sList;

		try{
			doc = reader.readSBMLFromString(modelString);
		}catch(Exception e){
			e.printStackTrace();
		}

		/*	ListOfSpecies sList;

		doc = reader.readSBMLFromString(modelString);*/

		sbmlModel = doc.getModel();		
		sList = sbmlModel.getListOfSpecies();
		// counting number of non fixed species
		nSpecies = (int) sList.size();
		int counter = 0;// nSpecies;
		for (int i = 0; i < nSpecies; i++) {
			if ((!sList.get(i).getBoundaryCondition() && sList.get(i)
					.getConstant())
					|| (!sList.get(i).getBoundaryCondition() && !sList.get(i)
							.getConstant())) {
				counter++;
			}
		}
		// nNonConstSpecies = counter;
		speciesId = new String[nSpecies];
		this.speciesInitialCondition = new double[nSpecies];
		// int k = 0;
		for (int i = 0; i < nSpecies; i++) {			
			// if(!sList.get(i).getBoundaryCondition()){
			if (sList.get(i).isSetInitialAmount()) {
				this.speciesInitialCondition[i] = sList.get(i).getInitialAmount();
			} else {
		//		System.out.println("================Testinhg=========="+doc.getModel().getListOfSpecies().get(i).getId()+" "+biomassVariable);
				if(doc.getModel().getListOfSpecies().get(i).getId().equals(biomassVariable)){					
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCellBirthSize()); //set the birth size to new value
				}
				//set the values of the variables to change
				System.out.println("Changing initial condition, please wait... ");
				for(int ii=0; ii<this.changeVariableIdsValuesMap.size(); ii++){
					if(this.changeVariableIdsValuesMap.containsKey(doc.getModel().getListOfSpecies().get(i).getId())){
						doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.changeVariableIdsValuesMap.get(doc.getModel().getListOfSpecies().get(i).getId()));
					}
				}
				//set the nutrient level
			/*	if(doc.getModel().getListOfSpecies().get(i).getId().equals(GLUVariable)){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(this.getCurrentGlucose()); //set the initial nutrient level
				}*/
				this.speciesInitialCondition[i] = sList.get(i).getInitialConcentration();
			//	System.out.print("Amount value "+this.speciesInitialCondition[i]);
			}
			speciesId[i] = sList.get(i).getId();

		}
		model = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + doc.toSBML();
		sbmlModel.delete();
		sList.delete();
		doc.delete();		 
		reader.delete();
	}

	/** modify the initial condition for next itereation
	 * @param stateAtEvent, state at the last event
	 * @param headerString, header information for the state
	 * @param isDaughter, to indicate whether it is a daughter cell agent
	 * @param isDivided, to check whether the cell agent has divided or not
	 */
	public void changeSInitialCondition(String stateAtEvent, String headerString, boolean isDaughter, boolean isDivided){
	//	System.out.println("My State ==:" + stateAtEvent);
	//	System.out.println("isDaughetr: "+ isDaughter+ " isDivided: "+ isDivided);
		
		//convert the stateAtevent variable in array format
		StringTokenizer headerToken = new StringTokenizer(headerString, "$"); // toekenizer
		String speciesHeader = headerToken.nextToken();
		String paramHeader = headerToken.nextToken();


		StringTokenizer stateAtEventToken = new StringTokenizer(stateAtEvent, "$"); // toekenizer
		String speciesInitStr = stateAtEventToken.nextToken();
		String paramInitStr = stateAtEventToken.nextToken();

		//	System.out.println(speciesHeader);
		//	System.out.println(speciesInitStr);
		//	System.out.println(paramHeader);
		//	System.out.println(paramInitStr);


		//convert the header information into array of string
		int budVariableIndex = 0, biomassIndex=0;
		double biomassValue = 0.0;
		double budVariableValue = 0.0;
		double biomassTotal = 0.0;
		int sizeOfSpecies = 7;
		StringTokenizer speciesHeaderToken = new StringTokenizer(speciesHeader, "|"); // toekenizer		
		int speciesHeadCounter = speciesHeaderToken.countTokens();
		String[] speciesHeadArray = new String [speciesHeadCounter];
		for (int j = 0; j <speciesHeadCounter; j++) {				
			speciesHeadArray[j] = speciesHeaderToken.nextToken();
			if(speciesHeadArray[j].equals(this.getBudVariable()))budVariableIndex = j;
			//		System.out.println(speciesHeadArray[j]+"\t" +budVariableIndex);
		}		

		//	System.out.println("Parameters: "+paramInitStr);
		StringTokenizer sInitToken = new StringTokenizer(speciesInitStr, "|"); // toekenizer
		String eventTime = sInitToken.nextToken();
		//	System.out.println("Time value:  "+eventTime);
		int sinitCondCounter = sInitToken.countTokens();
		double [] sInitCond = new double [sinitCondCounter];
		
		for (int j = 0; j <sinitCondCounter; j++) {				
			sInitCond[j] = Double.parseDouble(sInitToken.nextToken());
			if(speciesHeadArray[j].equals(this.getBiomassVariable())){
				biomassValue = sInitCond[j];
				biomassIndex = j;
			}
			if(speciesHeadArray[j].equals(this.getBudVariable())){
		//		System.out.println("==========&&&&&&&&&&&&&&&&&&&===============Variable "+ this.getBudVariable());
				budVariableValue = sInitCond[j];
		//		System.out.println("==========&&&&&&&&&&&&&&&&&&&===============Value" + budVariableValue); 
				budVariableIndex = j;
			}
			//	System.out.println(speciesHeadArray[j]+"\t");
			//	System.out.println(sInitCond[j]+"\n");

		}
		
		//convert the parameter initials to array
		StringTokenizer pInitToken = new StringTokenizer(paramInitStr, "|"); // toekenizer
		//pInitToken.nextToken();
		int pIinitCondCounter = pInitToken.countTokens();
		double [] pInitCond = new double [pIinitCondCounter];
		for (int j = 0; j <pIinitCondCounter; j++) {				
			pInitCond[j] = Double.parseDouble(pInitToken.nextToken());	
			
			//	System.out.println(pInitCond[j]+"\n");
		}
		
		
		//convert the parameter header information into array of string
		double valueOfPartitionFunction = 0.0;		
		StringTokenizer paramHeaderToken = new StringTokenizer(paramHeader, "|"); // toekenizer		
		int paramHeadCounter = paramHeaderToken.countTokens();
		String[] paramHeadArray = new String [paramHeadCounter];
		for (int j = 0; j <paramHeadCounter; j++) {				
			paramHeadArray[j] = paramHeaderToken.nextToken();	
			if(paramHeadArray[j].equals(this.getPartitionFunction())) valueOfPartitionFunction = pInitCond[j];
			//	System.out.println(paramHeadArray[j]+"\t");
			//	System.out.println(pInitCond[j]+"\n");	
	/*		if(paramHeadArray[j].equals(this.getEventVariable())){
				if(isDivided){
				pInitCond[j] = this.getEventVariableValue();				
				}
				System.out.println("Parameter Init "+ pInitCond[j]);*
			}*/
		}

		SBMLReader reader = new SBMLReader();
		SBMLDocument doc =null;			
		ListOfSpecies sList;

		try{
			doc = reader.readSBMLFromString(model);
		}catch(Exception e){
			e.printStackTrace();
		}
			
		
	//	int testingCode = 0;
		sList = doc.getModel().getListOfSpecies();	
	//	System.out.println(sList.size()+"Outside===========vvvvv===========I am Here===========vvvvvvvv====================");		
		//	nSpecies = speciesId.length; //(int)sList.size();		
		for (int i=0; i<(int)sList.size(); i++){	
			for(int j = 0; j<sInitCond.length; j++){
				if(sList.get(i).isSetInitialAmount()){					
					if(sList.get(i).getId().equals(speciesHeadArray[j])){
						doc.getModel().getListOfSpecies().get(i).setInitialAmount(sInitCond[j]);
						//		System.out.println("Before: "+doc.getModel().getListOfSpecies().get(i).getInitialAmount());
					}					
				}			
				else{					
					if(sList.get(i).getId().equals(speciesHeadArray[j])){
						doc.getModel().getListOfSpecies().get(i).setInitialConcentration(sInitCond[j]);							
							
						//check for division and modify the biomass value
						if(isDivided){
						if(speciesHeadArray[j].equals(this.getBiomassVariable())){							
							if(isDaughter && this.isNewCell()){					
							//		System.out.println("My concentration Now"+ speciesHeadArray[j]+ "==="+ sInitCond[j] + "My State i :"+isDaughter);
						if(sList.size()!=sizeOfSpecies){	
					//		System.out.println("===========vvvvv===========I am Here===========vvvvvvvv====================");
							doc.getModel().getListOfSpecies().get(i).setInitialConcentration((valueOfPartitionFunction)*(sInitCond[j]/(1-valueOfPartitionFunction)));
						//	doc.getModel().getListOfSpecies().get(i).setInitialConcentration((valueOfPartitionFunction)*(sInitCond[j]));
						}
							//set the cell size at division
							this.setCellBirthSize((float)doc.getModel().getListOfSpecies().get(i).getInitialConcentration());							
						}
						else{		
							doc.getModel().getListOfSpecies().get(i).setInitialConcentration(sInitCond[j]);
						//	doc.getModel().getListOfSpecies().get(i).setInitialConcentration((1-valueOfPartitionFunction)*sInitCond[j]);					
					//		System.out.println("Initial concentration: "+doc.getModel().getListOfSpecies().get(i).getInitialConcentration());
						}
						}
						}
						
						if(speciesHeadArray[j].equals(this.getBudVariable())){													
							if(sList.size()!=sizeOfSpecies){							
								doc.getModel().getListOfSpecies().get(i).setInitialConcentration((0.0));
							}							
						}						
						
						if(speciesHeadArray[j].equals(this.getNutrientModelId())){
							double gluValue = this.getSumOfGLUConsumed()*this.getCellVolume();							
							doc.getModel().getListOfSpecies().get(i).setInitialConcentration((this.getCurrentGlucose()*this.getEnvironmentVolume()-gluValue)/this.getEnvironmentVolume());
							//	System.out.println("The new Glucose after cell consumption: "+doc.getModel().getListOfSpecies().get(i).getInitialConcentration());
						} 						
						
					}
					
				}

			}

		}
		biomassTotal = sInitCond[biomassIndex-1];
		if(sList.size()==sizeOfSpecies && budVariableIndex!=0 && this.isNewCell){
	/*	System.out.println("===========vvvv=================================================Index "+ budVariableIndex);
		System.out.println("Value bud========================================================= "+budVariableValue);
		System.out.println(biomassIndex);
		System.out.println(biomassValue);
		System.out.println("Biomass Total: "+biomassTotal);*/
		
		for(int i = 0; i<sInitCond.length; i++){
			if(i==biomassIndex-1){
				doc.getModel().getListOfSpecies().get(i).setInitialConcentration((valueOfPartitionFunction)*biomassTotal); 				
			}
			if(i==budVariableIndex){
				doc.getModel().getListOfSpecies().get(i).setInitialConcentration(0.0);
			}
			if(i==biomassIndex){
				doc.getModel().getListOfSpecies().get(i).setInitialConcentration(budVariableValue); 
				this.setCellBirthSize((float)budVariableValue);							
				
			}
			
		}
	}else
		if(sList.size()==sizeOfSpecies && budVariableIndex!=0 && isDivided){			
			for(int i = 0; i<sInitCond.length; i++){
				doc.getModel().getListOfSpecies().get(i).setInitialConcentration(sInitCond[i]);
				if(i==biomassIndex-1){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration((1-valueOfPartitionFunction)*biomassTotal); 
		//			System.out.println("=========Biomass Testing:========== "+valueOfPartitionFunction);
				}
				if(i==budVariableIndex){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(0.0);
				}
				if(i==biomassIndex){
					doc.getModel().getListOfSpecies().get(i).setInitialConcentration(biomassValue); 
				}
				
			}
		}
		
		//change the parameter initials for only the old cells
		ListOfParameters pList = doc.getModel().getListOfParameters();
		if(!isDivided) { //!this.isNewCell()){ //added !isDivided
	//		if(!isDivided){
			for (int i=0; i<(int)pList.size(); i++){	
				for(int j = 0; j<pInitCond.length; j++){
					if(pList.get(i).getId().equals(paramHeadArray[j])){
						//	System.out.println("Id Model: "+ doc.getModel().getListOfParameters().get(i).getId());							
							doc.getModel().getListOfParameters().get(i).setValue(pInitCond[j]);	
						
					//	if(isDivided){
				/*	if(paramHeadArray[j].equals(this.getEventVariable())){
								pInitCond[j] = this.getEventVariableValue();
								doc.getModel().getListOfParameters().get(i).setValue(pInitCond[j]);
							}	*/					
					//	}
				//		System.out.println("\tId :"+ doc.getModel().getListOfParameters().get(i).getId()+ " value " +doc.getModel().getListOfParameters().get(i).getValue());	
						//	System.out.println("\tId value: "+ doc.getModel().getListOfParameters().toString());
					}
				}
			}
		}	
	/*	System.out.print("Printing Concentration values\n");
		for(int j = 0; j<sInitCond.length; j++){
			System.out.print(doc.getModel().getListOfSpecies().get(j).getInitialConcentration()+"\t"); 
		}
		System.out.print("Printing parameter values\n");
		for (int i=0; i<(int)pList.size(); i++){			
				System.out.print(doc.getModel().getListOfParameters().get(i).getValue()+"\t");			
		}
		*/

	/*	if(paramHeadArray[j].equals(this.getEventVariable()) && ){
			pInitCond[j] = this.getEventVariableValue();
		}*/
		
		model = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + doc.toSBML();
	/*	Random rand = new Random();
		if(!isDaughter){ //!this.isNewCell()){
		FileWriter writer; 
		try{		
			File file1 = new File("file1."+rand.nextInt()+".xml");
			System.out.println("Code=== save 1 file \n");
			writer = new FileWriter(file1);
			writer.write(model);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		*/
		//just to test the generated sbml file
	/*	FileWriter writer; 
		try{
		if(testingCode==1){
			File file1 = new File("file1.xml");
			System.out.println("Code=== save 1 file \n");
			writer = new FileWriter(file1);
			writer.write(model);
			writer.close();
		}else if(testingCode==0){
			File file0 = new File("file0.xml");
			System.out.println("Code=== save 0 file \n");
			writer = new FileWriter(file0);
			writer.write(model);
			writer.close();
		}else{
			File file2 = new File("file2.xml");
			System.out.println("Code=== save 2 file \n");
			writer = new FileWriter(file2);
			writer.write(model);
			writer.close();
		}
		}catch (Exception e){
			e.printStackTrace();
		}*/

		pList.delete();
		sList.delete();
		doc.delete();		 
		reader.delete();
		//	System.out.println(model);
	}

}
