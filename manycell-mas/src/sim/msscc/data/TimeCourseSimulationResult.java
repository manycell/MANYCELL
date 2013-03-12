package sim.msscc.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TimeCourseSimulationResult {	
	
//	private String []headerData;
//	private double [][]resultArray;
//	private String resultString;
	private String headerString = null;
	
	private double [][]parameterResultArray;
	private String paramResultString;
	private String paramHeaderString;
	private String []paramHeaderData;
	
	private double [][]speciesResultArray;
	private String speciesResultString;
	private String speciesHeaderString;
	private String []speciesHeaderData;
	
	private float divisionTime = -1f;
	private double []sInit;
	
	private float GLUconsumed;
	private float sizeAtDivision;
	private float sizeMultiplier;
	
	private String biomassVariable = null; // "Area_B_m";// "MASS";
	private String partitionFunction = null; //"F";	
	private String eventVariable = null; //"BUD";
	private float eventVariableValue	=	0.0f;

	private String stateAtEvent = null;
	private String simState = null;
	private StringBuffer cellSizeAtSim;
	
	private float [] fastTime;	
	private String [] activeVariableIds ={};
	private float [][] activeVariableValues;
	private float parentDivisionTime;
	
	private String GLUConsumeId = null; //"parameter_22"; //"parameter_7";	
	
	private boolean isDivided;	
	
	
	
	public String getBiomassVariable() {
		return biomassVariable;
	}


	public void setBiomassVariable(String biomassVariable) {
		this.biomassVariable = biomassVariable;
	}


	public String getPartitionFunction() {
		return partitionFunction;
	}


	public void setPartitionFunction(String partitionFunction) {
		this.partitionFunction = partitionFunction;
	}


	public String getGLUConsumeId() {
		return GLUConsumeId;
	}


	public void setGLUConsumeId(String gLUConsumeId) {
		GLUConsumeId = gLUConsumeId;
	}


	public void setActiveVariableIds(String[] activeVariableIds) {
		this.activeVariableIds = activeVariableIds;
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



	public boolean isDivided() {
		return isDivided;
	}



	public void setDivided(boolean isDivided) {
		this.isDivided = isDivided;
	}



	public String getSimState() {
		return simState;
	}



	public void setSimState(String simState) {
		this.simState = simState;
	}



	public float[] getFastTime() {
		return fastTime;
	}



	public void setFastTime(float[] fastTime) {
		this.fastTime = fastTime;
	}



	public String[] getActiveVariableIds() {
		return activeVariableIds;
	}



	public void setActiveVariables(String[] activeVariableIds) {
		this.activeVariableIds = activeVariableIds;
	}



	public float[][] getActiveVariableValues() {
		return activeVariableValues;
	}



	public void setActiveVariableValues(float[][] activeVariableValues) {
		this.activeVariableValues = activeVariableValues;
	}



	public float getParentDivisionTime() {
		return parentDivisionTime;
	}


	public void setParentDivisionTime(float parentDivisionTime) {
		this.parentDivisionTime = parentDivisionTime;
	}


	//constructor
	public TimeCourseSimulationResult(String resultString, SimulationRequestMessage simMsg){
	//	this.convert2Array(resultString);
		this.setActiveVariables(simMsg.getActiveVariableIds());
		this.setBiomassVariable(simMsg.getBiomassVariable());
		this.setPartitionFunction(simMsg.getPartitionFunction());
		this.setEventVariable(simMsg.getEventVariable());
		this.setEventVariableValue(simMsg.getEventVariableValue());
		this.setGLUConsumeId(simMsg.getGLUConsumeId());
		this.extractState(resultString);
	}	
	
	
	
	public float getSizeMultiplier() {
		return sizeMultiplier;
	}



	public void setSizeMultiplier(float sizeMultiplier) {
		this.sizeMultiplier = sizeMultiplier;
	}



	public float getSizeAtDivision() {
		return sizeAtDivision;
	}



	public void setSizeAtDivision(float sizeAtDivision) {
		this.sizeAtDivision = sizeAtDivision;
	}



	public float getGLUconsumed() {
		return GLUconsumed;
	}


	public void setGLUconsumed(float gLUconsumed) {
		GLUconsumed = gLUconsumed;
	}


	public String getHeaderString() {
		return headerString;
	}


	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}


	public double[][] getSpeciesResultArray() {
		return speciesResultArray;
	}

	public void setSpeciesResultArray(double[][] speciesResultArray) {
		this.speciesResultArray = speciesResultArray;
	}

	public String getSpeciesResultString() {
		return speciesResultString;
	}

	public void setSpeciesResultString(String speciesResultString) {
		this.speciesResultString = speciesResultString;
	}

	public String getSpeciesHeaderString() {
		return speciesHeaderString;
	}

	public void setSpeciesHeaderString(String speciesHeaderString) {
		this.speciesHeaderString = speciesHeaderString;
	}

	public String[] getSpeciesHeaderData() {
		return speciesHeaderData;
	}




	public void setSpeciesHeaderData(String[] speciesHeaderData) {
		this.speciesHeaderData = speciesHeaderData;
	}

	public float getDivisionTime() {
		return divisionTime;
	}


	public void setDivisionTime(float divisionTime) {
		this.divisionTime = divisionTime;
	}

	public double[] getsInit() {
		return sInit;
	}

	public void setsInit(double[] sInit) {
		this.sInit = sInit;
	}

	public String getStateAtEvent() {
		return stateAtEvent;
	}


	public void setStateAtEvent(String stateAtEvent) {
		this.stateAtEvent = stateAtEvent;
	}
	

	public double[][] getParameterResultArray() {
		return parameterResultArray;
	}


	public void setParameterResultArray(double[][] parameterResultArray) {
		this.parameterResultArray = parameterResultArray;
	}


	public String getParamResultString() {
		return paramResultString;
	}


	public void setParamResultString(String paramResultString) {
		this.paramResultString = paramResultString;
	}


	public String getParamHeaderString() {
		return paramHeaderString;
	}


	public void setParamHeaderString(String paramHeaderString) {
		this.paramHeaderString = paramHeaderString;
	}


	public String[] getParamHeaderData() {
		return paramHeaderData;
	}


	public void setParamHeaderData(String[] paramHeaderData) {
		this.paramHeaderData = paramHeaderData;
	}


	//method for extracting state from simulation result
	
	public void extractState(String simResult){
		String str =null, strHead =null;		
		List<String> speciesResultArrayList = new ArrayList();
		
		List<String> strSpeciesHeadList = new ArrayList();		
		List<String> paramResultArrayList = new ArrayList();		
		
		//process the header for both species and parameters
		BufferedReader reader = new BufferedReader(new StringReader(simResult));
		try {
			strHead = reader.readLine();
			headerString = strHead;
			StringTokenizer strSPHeaderStrings = new StringTokenizer(strHead, "$"); // toekenizer	
			speciesHeaderString = strSPHeaderStrings.nextToken();
			paramHeaderString = strSPHeaderStrings.nextToken();		
			
			StringTokenizer strings;			
			strings = new StringTokenizer(speciesHeaderString, "|"); // toekenizer	
			int strCounter = strings.countTokens();
			speciesHeaderData = new String[strCounter+1];
			speciesHeaderData[0] = "Time";
			strSpeciesHeadList.add("Time");
			
		//	get the header from the token and print
			for (int i = 0; i < strCounter; i++) {				
				speciesHeaderData[i+1] = strings.nextToken();
		//		System.out.print(" " +speciesHeaderData[i]+"\t");	
			}	
		//	System.out.println();
			
			//pass the parameter header
			StringTokenizer paramStrings = new StringTokenizer(paramHeaderString, "|"); // toekenizer
			int phCounter = paramStrings.countTokens();
			paramHeaderData = new String[phCounter];			
			
		//	get the header from the token and print
			for (int i = 0; i < phCounter; i++) {				
				paramHeaderData[i] = paramStrings.nextToken();
		//		System.out.print(" " +paramHeaderData[i]+"\t");	
			}			
			
		//	System.out.println();
			
			//getting the remaining data from string and separating into species and parameter data
			while ((str = reader.readLine()) != null) {
				StringTokenizer strResultToken = new StringTokenizer(str, "$"); // toekenizer	
				
				speciesResultArrayList.add(strResultToken.nextToken());
				paramResultArrayList.add(strResultToken.nextToken());		
			}

		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		sInit = new double[speciesHeaderData.length];
		speciesResultArray = new double[speciesResultArrayList.size()][speciesHeaderData.length];
		
		//get the current simulation state
		simState = speciesResultArrayList.get(speciesResultArrayList.size()-1)+"$" +paramResultArrayList.get(paramResultArrayList.size()-1);
	//	simState = speciesResultArrayList.get(speciesResultArrayList.size())+"$" +paramResultArrayList.get(paramResultArrayList.size());
	//	System.out.println(simState);
		
		if(!this.checkDivision(speciesResultArrayList, paramResultArrayList)){
				
		//array for temporary active variable  and time	
		activeVariableValues = new float[speciesResultArrayList.size()-1][activeVariableIds.length]; //removed -1
		fastTime = new float[speciesResultArrayList.size()-1]; //removed -1
		StringTokenizer strToken;	
		

		//get the active variable data from the simulation results	
		for(int i=0; i<speciesResultArrayList.size()-1; i++){ //removed  -1
			String strLine = speciesResultArrayList.get(i);
			strToken = new StringTokenizer(strLine, "|"); // toekenizer	

			for (int j = 0; j <speciesHeaderData.length; j++) {
				double speciesConc = Double.parseDouble(strToken.nextToken());
				speciesResultArray[i][j] = speciesConc;
				for(int k=0;k<activeVariableIds.length; k++){
					if(activeVariableIds[k].equals(speciesHeaderData[j])){					
						activeVariableValues[i][k] = (float)speciesConc;
					}
				}
			}
			fastTime[i] = (float)speciesResultArray[i][0];
		}
		
		
		//process and print out the parameter variable based simulation data	
		parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
		for(int i=0; i<paramResultArrayList.size(); i++){
			String strLine = paramResultArrayList.get(i);
			StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer

			for (int j = 0; j <paramHeaderData.length; j++) {
				parameterResultArray[i][j] = Double.parseDouble(strParamToken.nextToken());	
				if(paramHeaderData[j].equals(GLUConsumeId )){
					GLUconsumed = (float)parameterResultArray[i][j];				
				}
				//	System.out.print(" " +parameterResultArray[i][j]+"\t");			
			}
			//System.out.println("Consumed " +GLUconsumed+"\t");
			//	System.out.println();
		}
		}
		
		//this.checkDivision(speciesResultArrayList, paramResultArrayList);

	}
	
	public boolean checkDivision(List<String> speciesResultArrayList, List<String> paramResultArrayList ){
		//process and print out the species based simulation data
				int stateEventLine = 0;
				StringTokenizer strToken;	
				divisionTime = -1;
				//array for tmporary active variable  and time	
				float [][]tmpActiveVariableValues = new float[speciesResultArrayList.size()][activeVariableIds.length];
				float []tmpFastTime = new float[speciesResultArrayList.size()];
				
				//==========checking for event line using parameter values
						parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
						for(int i=0; i<paramResultArrayList.size(); i++){
							String strLine = paramResultArrayList.get(i);
							StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer			
						//	System.out.println("Parameter header"+ strLine);
							for (int j = 0; j <paramHeaderData.length; j++) {	
							//	System.out.println("Parameter value testing  "+paramHeaderData[j]+ " == "+ Double.parseDouble(strParamToken.nextToken()));
								String myToken = strParamToken.nextToken();
								if(myToken.equals("0")){
									parameterResultArray[i][j] = 0.0;
									}
								else if(myToken.equals("nan")){
									parameterResultArray[i][j] = 0.0;
								}
								else 
									parameterResultArray[i][j] = Double.parseDouble(myToken);	
								
								if(paramHeaderData[j].equalsIgnoreCase(eventVariable) && (parameterResultArray[i][j]!=eventVariableValue)){
								//	System.out.println("Parameter header"+ strLine);
									divisionTime = (float)parameterResultArray[i][j];
									stateEventLine = i; //main not sure check
									i=999999999; //find the 1st division time, spring out of the loop
									j=999999999; //find the 1st division time, spring out of the loop
								//	System.out.println("========Event Line========= "+strLine);									
								}		
						}
						
						}
				//=========end of checking using parameter values

						for(int i=0; i<speciesResultArrayList.size(); i++){
							String strLine = speciesResultArrayList.get(i);
							strToken = new StringTokenizer(strLine, "|"); // toekenizer	
					//		System.out.println("Species header"+ strLine);
							for (int j = 0; j <speciesHeaderData.length; j++) {
								double speciesConc;
						/*		String myToken = strToken.nextToken();
								if(myToken.equals("0")){
									speciesConc = 0.0;
									}
								else if(myToken.equals("nan")){
									speciesConc = 0.0;
								}
								else speciesConc = Double.parseDouble(myToken);*/
								
								speciesConc = Double.parseDouble(strToken.nextToken());
								
								//get the active variable data from the simulation results	
								for(int k=0;k<activeVariableIds.length; k++){
									if(activeVariableIds[k].equals(speciesHeaderData[j])){					
										tmpActiveVariableValues[i][k] = (float)speciesConc;
									}
								}

								speciesResultArray[i][j] = speciesConc;			
								
								if(speciesHeaderData[j].equals(biomassVariable)&&i==stateEventLine-1){							
									this.setSizeAtDivision((float)speciesConc);
								}				

							}
							
							//get the time and use as temporary fast time allocation
							tmpFastTime[i] = (float)speciesResultArray[i][0];
						}
						
						//allocating parameter values to the active variable if required
				/*		for(int i=0; i<paramResultArrayList.size(); i++){
							String strLine = paramResultArrayList.get(i);
							strToken = new StringTokenizer(strLine, "|"); // toekenizer	
					//		System.out.println("Species header"+ strLine);
							for (int j = 0; j <paramHeaderData.length; j++) {
								double paramValue;								
								paramValue = Double.parseDouble(strToken.nextToken());
								
								//get the active variable data from the simulation results	
								for(int k=0;k<activeVariableIds.length; k++){
									if(activeVariableIds[k].equals(paramHeaderData[j])){					
										tmpActiveVariableValues[i][k] = (float)paramValue;
									}
								}
								parameterResultArray[i][j] = paramValue;												

							}						
							
						}*/
						
						
					
						//make the stateatevent state after event stateAfterEvent
						//check if there is no division

						if(stateEventLine == 0){	
							stateAtEvent = speciesResultArrayList.get(speciesResultArrayList.size()-1); 
							this.setDivided(false);			
						}else{
							this.setDivided(true);


							//copy the tmporary active variable values and and time 	
							int counter = 0;
							//	counter = tmpFastTime.length-1;
							if(divisionTime==-1f){			
								counter = tmpFastTime.length;
							}
							else{
								for(int i=0; i<tmpFastTime.length; i++){
									if(tmpFastTime[i]<=divisionTime){										
										counter++;
									}
								}
								counter = counter-1;
							}

							//copy the variables
							//	activeVariableValues = new float[tmpFastTime.length-1][activeVariableIds.length];
							//	fastTime = new float[tmpFastTime.length-1];
							activeVariableValues = new float[counter][activeVariableIds.length];
							fastTime = new float[counter];

							//	for(int i=0; i<(tmpFastTime.length-1); i++){
							for(int i=0; i<counter; i++){
								fastTime[i] = tmpFastTime[i];
								//		System.out.print(fastTime[i]+"\t");
								for(int j=0; j<activeVariableIds.length; j++){
									activeVariableValues[i][j] = tmpActiveVariableValues[i][j];
								//	System.out.print("===========active======= "+activeVariableValues[i][j]+"\t");
								}
							//	System.out.println();
							}
										
							
							
							for(int i=0; i<speciesResultArrayList.size(); i++){			
								if(i==stateEventLine)stateAtEvent = speciesResultArrayList.get(i);	//add 1 to move to actual state position
								
								//Spiesser model ###
							//	if(i==stateEventLine-1)stateAtEvent = speciesResultArrayList.get(i); 
								
							}
						//}

						//process and print out the parameter variable based simulation data	
						parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
						for(int i=0; i<paramResultArrayList.size(); i++){
							String strLine = paramResultArrayList.get(i);
							StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer
							if(stateEventLine!=0){
								if(i==stateEventLine)stateAtEvent = stateAtEvent+"$"+strLine; 
								
								//spiesser model ###
							//	if(i==stateEventLine-1)stateAtEvent = stateAtEvent+"$"+strLine; 
								
							}

							for (int j = 0; j <paramHeaderData.length; j++) {
								parameterResultArray[i][j] = Double.parseDouble(strParamToken.nextToken());	
								if(i==stateEventLine && paramHeaderData[j].equals(partitionFunction)){								
									sizeMultiplier = (float)parameterResultArray[i][j];	
								}
								
								//get the glucose level at this point
								if(i==stateEventLine && paramHeaderData[j].equals(GLUConsumeId)){ 
									GLUconsumed = (float)parameterResultArray[i][j];				
								}
								//	System.out.print(" " +parameterResultArray[i][j]+"\t");			
							}
							
						}	
					//	System.out.println("State=============="+ stateAtEvent);
					}
						
				//add the parameter array if there was no division
				if(stateEventLine==0){			
					stateAtEvent = stateAtEvent+"$"+ paramResultArrayList.get(paramResultArrayList.size()-1); 
					simState = stateAtEvent;
			//		System.out.println("No division"+ simState);
				}
			//	if(this.isDivided){this.setParentDivisionTime(fastTime[fastTime.length-2]);}
		
		return this.isDivided;
	}
	
	public boolean checkDivisionT(List<String> speciesResultArrayList, List<String> paramResultArrayList ){		
		//process and print out the species based simulation data
		int stateEventLine = 0;
		StringTokenizer strToken;	
		
		//array for tmporary active variable  and time	
		float [][]tmpActiveVariableValues = new float[speciesResultArrayList.size()][activeVariableIds.length];
		float []tmpFastTime = new float[speciesResultArrayList.size()];
		
		//==========checking for event line using parameter values
				parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
				for(int i=0; i<paramResultArrayList.size(); i++){
					String strLine = paramResultArrayList.get(i);
					StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer			
				//	System.out.println("Parameter header"+ strLine);
					for (int j = 0; j <paramHeaderData.length; j++) {	
					//	System.out.println("Parameter value testing  "+paramHeaderData[j]+ " == "+ Double.parseDouble(strParamToken.nextToken()));
						String myToken = strParamToken.nextToken();
					/*	if(myToken.equals("0")){
							parameterResultArray[i][j] = 0.0;}
						else if(myToken.equals("nan")){
							parameterResultArray[i][j] = 0.0;
						}
						else */
						parameterResultArray[i][j] = Double.parseDouble(myToken);	
						if(paramHeaderData[j].equalsIgnoreCase(eventVariable) && (parameterResultArray[i][j]==eventVariableValue)){
					//		System.out.println("++++++++++++++++++++++++++++intside+++++++++++++++++++++++++++++++ " +eventVariable+" "+ eventVariableValue);
							stateEventLine = i;							 
						}		
				}
				
				}
		//=========end of checking using parmater values
		
		for(int i=0; i<speciesResultArrayList.size(); i++){
			String strLine = speciesResultArrayList.get(i);
			strToken = new StringTokenizer(strLine, "|"); // toekenizer	

			for (int j = 0; j <speciesHeaderData.length; j++) {
				double speciesConc = Double.parseDouble(strToken.nextToken());
				
				//get the active variable data from the simulation results	
				for(int k=0;k<activeVariableIds.length; k++){
					if(activeVariableIds[k].equals(speciesHeaderData[j])){					
						tmpActiveVariableValues[i][k] = (float)speciesConc;
					}
				}

				speciesResultArray[i][j] = speciesConc;			
				if(stateEventLine==0){
					if(speciesHeaderData[j].equalsIgnoreCase(eventVariable) && (speciesResultArray[i][j]==eventVariableValue)){
						divisionTime = (float)speciesResultArray[i][0];					
						stateEventLine = i;
					}					
				}
				if(speciesHeaderData[j].equals(biomassVariable)&&i==stateEventLine){
			//		System.out.println("++++++++++++"+speciesHeaderData+"=========="+speciesConc);
					this.setSizeAtDivision((float)speciesConc);
				}				

			}
			
			//get the time and use as temporary fast time allocation
			tmpFastTime[i] = (float)speciesResultArray[i][0];
		}
		
		divisionTime = (float)speciesResultArray[stateEventLine][0];
		

		//make the stateatevent state after event stateAfterEvent
		//check if there is no division

		if(stateEventLine == 0){	
			stateAtEvent = speciesResultArrayList.get(speciesResultArrayList.size()-1);
			this.setDivided(false);			
		}else{
			this.setDivided(true);


			//copy the tmporary active variable values and and time 	
			int counter = 0;
			//	counter = tmpFastTime.length-1;
			if(divisionTime==0.0f){			
				counter = tmpFastTime.length;
			}
			else{
				for(int i=0; i<tmpFastTime.length; i++){
					if(tmpFastTime[i]<=divisionTime){
						counter++;
					}
				}
				counter = counter-1;
			}

			//copy the variables
			//	activeVariableValues = new float[tmpFastTime.length-1][activeVariableIds.length];
			//	fastTime = new float[tmpFastTime.length-1];
			activeVariableValues = new float[counter][activeVariableIds.length];
			fastTime = new float[counter];

			//	for(int i=0; i<(tmpFastTime.length-1); i++){
			for(int i=0; i<counter; i++){
				fastTime[i] = tmpFastTime[i];
				//		System.out.print(fastTime[i]+"\t");
				for(int j=0; j<activeVariableIds.length; j++){
					activeVariableValues[i][j] = tmpActiveVariableValues[i][j];
				//	System.out.print("===========active======= "+activeVariableValues[i][j]+"\t");
				}
			//	System.out.println();
			}
						
			
			
			for(int i=0; i<speciesResultArrayList.size(); i++){			
				if(i==stateEventLine)stateAtEvent = speciesResultArrayList.get(i);				
				
			}
		//}

		//process and print out the parameter variable based simulation data	
		parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
		for(int i=0; i<paramResultArrayList.size(); i++){
			String strLine = paramResultArrayList.get(i);
			StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer
			if(stateEventLine!=0){
				if(i==stateEventLine)stateAtEvent = stateAtEvent+"$"+strLine;
			}

			for (int j = 0; j <paramHeaderData.length; j++) {
				parameterResultArray[i][j] = Double.parseDouble(strParamToken.nextToken());	
				if(i==stateEventLine && paramHeaderData[j].equals(partitionFunction)){
					sizeMultiplier = (float)parameterResultArray[i][j];	
				}
				
				//get the glucose level at this point
				if(i==stateEventLine && paramHeaderData[j].equals(GLUConsumeId)){
					GLUconsumed = (float)parameterResultArray[i][j];				
				}
				//	System.out.print(" " +parameterResultArray[i][j]+"\t");			
			}
			//System.out.println("Consumed " +GLUconsumed+"\t");
			//	System.out.println();
		}	
	}
		//add the parameter array if there was no division
		if(stateEventLine==0){			
			stateAtEvent = stateAtEvent+"$"+ paramResultArrayList.get(paramResultArrayList.size()-1);
			simState = stateAtEvent;
		}

	//	this.setSizeAtDivision(this.sizeAtDivision/(1-this.sizeMultiplier));
		return isDivided;
	}

	//method to convert the simulation result into 2 dimensional array
	public void convert2Array(String simResult) {
	//	System.out.println("I'm using this ============================================");
		String str =null, strHead =null;
		List<String> speciesResultArrayList = new ArrayList();
		List<String> strSpeciesHeadList = new ArrayList();
		
		List<String> paramResultArrayList = new ArrayList();
		List<String> strParamHeadList = new ArrayList();
		
		//process the header for both species and parameters
		BufferedReader reader = new BufferedReader(new StringReader(simResult));
		try {
			strHead = reader.readLine();
			headerString = strHead;
			StringTokenizer strSPHeaderStrings = new StringTokenizer(strHead, "$"); // toekenizer	
			speciesHeaderString = strSPHeaderStrings.nextToken();
			paramHeaderString = strSPHeaderStrings.nextToken();
		//	System.out.print("Header Testing: " +speciesHeaderString);
			//	speciesHeaderString = strHead;
			//System.out.print(strHead+"\n");	
			//parsing the species header
			
			StringTokenizer strings;			
			strings = new StringTokenizer(speciesHeaderString, "|"); // toekenizer	
			int strCounter = strings.countTokens();
			speciesHeaderData = new String[strCounter+1];
			speciesHeaderData[0] = "Time";
			strSpeciesHeadList.add("Time");
			
		//	get the header from the token and print
			for (int i = 0; i < strCounter; i++) {				
				speciesHeaderData[i+1] = strings.nextToken();
		//		System.out.print(" " +speciesHeaderData[i]+"\t");	
			}	
		//	System.out.println();
			
			//pass the parameter header
			StringTokenizer paramStrings = new StringTokenizer(paramHeaderString, "|"); // toekenizer
			int phCounter = paramStrings.countTokens();
			paramHeaderData = new String[phCounter];			
			
		//	get the header from the token and print
			for (int i = 0; i < phCounter; i++) {				
				paramHeaderData[i] = paramStrings.nextToken();
		//		System.out.print(" " +paramHeaderData[i]+"\t");	
			}			
			
		//	System.out.println();
			
			//getting the remaining data from string and separating into species and parameter data
			while ((str = reader.readLine()) != null) {
				StringTokenizer strResultToken = new StringTokenizer(str, "$"); // toekenizer	
				
				speciesResultArrayList.add(strResultToken.nextToken());
				paramResultArrayList.add(strResultToken.nextToken());		
			}

		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		sInit = new double[speciesHeaderData.length];
		speciesResultArray = new double[speciesResultArrayList.size()][speciesHeaderData.length];
		
		//array for tmporary active variable  and time	
		float [][]tmpActiveVariableValues = new float[speciesResultArrayList.size()][activeVariableIds.length];
		float []tmpFastTime = new float[speciesResultArrayList.size()];
		StringTokenizer strToken;		
		
		//process and print out the species based simulation data
		int stateEventLine = 0;
		
		//==========checking for event line using parameter values
		parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
		for(int i=0; i<paramResultArrayList.size(); i++){
			String strLine = paramResultArrayList.get(i);
			StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer			

			for (int j = 0; j <paramHeaderData.length; j++) {
				parameterResultArray[i][j] = Double.parseDouble(strParamToken.nextToken());	
				if(paramHeaderData[j].equalsIgnoreCase(eventVariable) && (parameterResultArray[i][j]==eventVariableValue)){
					stateEventLine = i;
				}		
		}
		
		}
		//=========end of checking using parmater values
		
	//	String strInitial = null;
		//List<Double> sInitList = new ArrayList();
		cellSizeAtSim = new StringBuffer();
		for(int i=0; i<speciesResultArrayList.size(); i++){
			String strLine = speciesResultArrayList.get(i);
		strToken = new StringTokenizer(strLine, "|"); // toekenizer	
		
		for (int j = 0; j <speciesHeaderData.length; j++) {
			double speciesConc = Double.parseDouble(strToken.nextToken());
			
			//get the active variable data from the simulation results	
			for(int k=0;k<activeVariableIds.length; k++){
				if(activeVariableIds[k].equals(speciesHeaderData[j])){					
					tmpActiveVariableValues[i][k] = (float)speciesConc;
				}
			}
			
			speciesResultArray[i][j] = speciesConc;			
			if((speciesHeaderData[j].equalsIgnoreCase(eventVariable) && (speciesResultArray[i][j]==eventVariableValue)) || stateEventLine!=0){// && (speciesResultArray[i][0]>1)){
		//		if(speciesHeaderData[j].equalsIgnoreCase("1stDivisionTime") && !(speciesResultArray[i][j]==-1)){				
					divisionTime = (float)speciesResultArray[i][0];					
					stateEventLine = i;
					/*if(speciesHeaderData[j].equals(biomassVariable)){
						this.setSizeAtDivision((float)speciesConc);
					}*/
					
			//stateAtEvent = strLine;
				
			}
			if(speciesHeaderData[j].equals(biomassVariable)&&i==stateEventLine){
				this.setSizeAtDivision((float)speciesConc);
			}
		
		/*	if(speciesHeaderData[j].equals(biomassVariable)){
			//	System.out.print("Test "+ speciesConc);
				cellSizeAtSim.append(String.valueOf(speciesConc));
				if(j!=(speciesResultArrayList.size()-1))cellSizeAtSim.append("|");
			}*/
			
		//	System.out.print(" " +resultArray[i][j]+"\t");			
		}	
		tmpFastTime[i] = (float)speciesResultArray[i][0];
	//	System.out.println();
		}
		
		//copy the tmporary active variable values and and time 	
		int counter = 0;
	//	counter = tmpFastTime.length-1;
		if(divisionTime==0.0f){			
			counter = tmpFastTime.length;
		}
		else{
			for(int i=0; i<tmpFastTime.length; i++){
				if(tmpFastTime[i]<=divisionTime){
					counter++;
				}
			}
			counter = counter-1;
		}
		
		activeVariableValues = new float[counter][activeVariableIds.length];
		fastTime = new float[counter];
		
		for(int i=0; i<(counter); i++){
			fastTime[i] = tmpFastTime[i];
	//		System.out.print(fastTime[i]+"\t");
			for(int j=0; j<activeVariableIds.length; j++){
				activeVariableValues[i][j] = tmpActiveVariableValues[i][j];
			//	System.out.print("===========active======= "+activeVariableValues[i][j]+"\t");
			}
		//	System.out.println();
		}
		
		System.out.println("Size mass value:  "+ cellSizeAtSim.toString());
		
		//make the stateatevent state after event stateAfterEvent
		//check if there is no division
		simState = speciesResultArrayList.get(speciesResultArrayList.size()-1);
		if(stateEventLine == 0){			
			stateAtEvent = speciesResultArrayList.get(speciesResultArrayList.size()-1);
		}else{
			for(int i=0; i<speciesResultArrayList.size(); i++){			
				if(i==stateEventLine)stateAtEvent = speciesResultArrayList.get(i);
			}
		}
		
		//process and print out the parameter variable based simulation data	
		parameterResultArray = new double[paramResultArrayList.size()][paramHeaderData.length];		
		for(int i=0; i<paramResultArrayList.size(); i++){
			String strLine = paramResultArrayList.get(i);
			StringTokenizer strParamToken = new StringTokenizer(strLine, "|"); // toekenizer
			if(stateEventLine!=0){
				if(i==stateEventLine)stateAtEvent = stateAtEvent+"$"+strLine;
			}

			for (int j = 0; j <paramHeaderData.length; j++) {
				parameterResultArray[i][j] = Double.parseDouble(strParamToken.nextToken());	
				if(i==stateEventLine && paramHeaderData[j].equals(partitionFunction)){
				sizeMultiplier = (float)parameterResultArray[i][j];	
			}
			if(i==stateEventLine && paramHeaderData[j].equals(GLUConsumeId )){
				GLUconsumed = (float)parameterResultArray[i][j];				
			}
		//	System.out.print(" " +parameterResultArray[i][j]+"\t");			
		}
		//System.out.println("Consumed " +GLUconsumed+"\t");
	//	System.out.println();
		}
		
		//no division take place
		simState = simState+"$"+ paramResultArrayList.get(paramResultArrayList.size()-1);
		if(stateEventLine==0){			
			stateAtEvent = stateAtEvent+"$"+ paramResultArrayList.get(paramResultArrayList.size()-1);
		}
		
	//	System.out.println(stateAtEvent);
	/*	StringTokenizer sInitToken = new StringTokenizer(stateAtEvent, "|"); // toekenizer
		for (int j = 0; j <speciesHeaderData.length; j++) {			
				sInit[j] = Double.parseDouble(sInitToken.nextToken());	
		}*/
		
		this.setSizeAtDivision(this.sizeAtDivision/(1-this.sizeMultiplier));
	}

}
