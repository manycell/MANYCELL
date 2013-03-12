package sim.mssim.action.cell;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.xmlbeans.XmlOptions;
/*import org.manycell.config.BioReactorDocument.BioReactor;
import org.manycell.config.BioReactorDocument.BioReactor.Nutrients;
import org.manycell.config.BioReactorDocument.BioReactor.Nutrients.Nutrient;
import org.manycell.config.CellsDocument.Cells;
import org.manycell.config.CellsDocument.Cells.Cell;*/
//import org.manycell.config.CellsDocument.Cells.;
//import org.manycell.config.CellsDocument.Cells.Cell.ChangeModelInitialConditons;
//import org.manycell.config.CellsDocument.Cells.Cell..ChangeModelParameterValues;
import org.manycell.config.DatabaseDocument.Database;
import org.manycell.config.ManycellDocument;
import org.manycell.config.ManycellDocument.Manycell;
import org.manycell.config.SimulationDocument.Simulation;
/*import org.manycell.config.SubCellularModelsDocument.SubCellularModels;
import org.manycell.config.SubCellularModelsDocument.SubCellularModels.Model.Events;
import org.manycell.config.SubCellularModelsDocument.SubCellularModels.Model.Events.Event;
import org.manycell.config.SubCellularModelsDocument.SubCellularModels.Model;
//import org.manycell.config.SubCellularModelsDocument.SubCellularModels.Model.CellSizePartitionVaraible;
import org.manycell.config.SubCellularModelsDocument.SubCellularModels.Model.TimeCourseSimulation;*/
import org.manycell.config.ManycellDocument;

import sim.mssim.action.MSSimSupportAction;

public class BasicCellDataAction extends MSSimSupportAction implements SessionAware {
	private static final long serialVersionUID = 5156288255337069381L;	
	
	//Micro-environment/Bioreactor variable	
	private float rVolume = 1.0f;
	private String rType = null;
	
	//nutrients in the reactor
	private double nValue = 2E-5;
	private String nName = "Glucose";
	private boolean isAmount = true;
	
	//data for cells
	private String id;
	private double volume = 10E-10;	
	private String isDaughter = "yes";
	private String subCellularModel = "SCM";	
	
	//time course simulation parameter
	private int stepSize = 5;
	private int numberOfSteps = 10;
	private String modelType = "continum";
	private String partionVariableId = "FValue";
	
	//event data
	private String eventName = "division";
	private String eventId  = "div";
	private String eventModelId = "BUD";
	
	//counter for counting cells
	private int counter;
	
	
	//session for holding data
	private Map<String, Object> session;	
	
	//can be deleted	
	public float getrVolume() {
		return rVolume;
	}

	public void setrVolume(float rVolume) {
		this.rVolume = rVolume;
	}	
	
	public String getrType() {
		return rType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getIsDaughter() {
		return isDaughter;
	}

	public void setIsDaughter(String isDaughter) {
		this.isDaughter = isDaughter;
	}

	public String getSubCellularModel() {
		return subCellularModel;
	}

	public void setSubCellularModel(String subCellularModel) {
		this.subCellularModel = subCellularModel;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String input() throws Exception {
		return SUCCESS;
	}	
	
	public String addMSSimData() {	
		String  cCounter = (String)this.getSession().get("cCounter");
		if(cCounter==null){
			cCounter = "1";
		}else{		
		int newMCounter = Integer.parseInt(cCounter)+1;
		cCounter = String.valueOf(newMCounter);
		}
		getSession().put("cCounter", cCounter);
		
		//set the id of this cell
		this.setId("id"+cCounter);
		
		// initialise system variables
		ManycellDocument simDoc = null;	
		Manycell sim = null;
		
		//get the manycell document from the session
		simDoc = (ManycellDocument)getSession().get("xml");
		//String counter = (String)getSession().get("cCounter");				
		
		//check if it's new document
		if(simDoc==null){
			
			//clear the counters and xml document from the session for new beginning 
			 getSession().remove("cCounter");
			 getSession().remove("xml");
			 
			 XmlOptions options = new XmlOptions();
			 Map<String, String> substitutes = new HashMap<String, String>();
			 substitutes.put("", "http://www.manycell.org/sim-config-schema");
			 options.setLoadSubstituteNamespaces(substitutes);
		
			 simDoc = ManycellDocument.Factory.newInstance(options);
			 sim = Manycell.Factory.newInstance();
		
		
	/*		 Cells cells = Cells.Factory.newInstance();			
		
			 Cell cell = cells.addNewCell();
			 cell.setId(this.getId());
			 cell.setVolume(this.getVolume());
			 cell.setSubCellularModel(this.getSubCellularModel());
			 if(this.getIsDaughter().equals("yes")){
				 cell.setIsDaughter(true);
			 }else{
				 cell.setIsDaughter(true);
			 }
			 ChangeModelInitialConditons sInitial = ChangeModelInitialConditons.Factory.newInstance();
		//	 ChangeModelParameterValues pValues = ChangeModelParameterValues.Factory.newInstance();
		
			 BioReactor reactor = BioReactor.Factory.newInstance();
			 reactor.setVolume(this.getrVolume());
			 reactor.setCultureType(this.getrType());
			 Nutrients nutrients = Nutrients.Factory.newInstance();
			 Nutrient nutrient = Nutrient.Factory.newInstance();
		
			 Simulation simulation = Simulation.Factory.newInstance();
			 Database database = Database.Factory.newInstance();
		
			 SubCellularModels subModel = SubCellularModels.Factory.newInstance();
			 
		// initialise the model and time course simulator for cells
			 SubCellularModels.Model model = subModel.addNewModel();					 
			 model.setId("SCM");
			 model.setType(this.modelType);
			 model.setIsDaughter(true);
			 
			 TimeCourseSimulation tSim = TimeCourseSimulation.Factory.newInstance();
			 tSim.setNumberOfSteps(this.numberOfSteps);
			 tSim.setStepSize(this.stepSize);
			 
			 //events
			 Events events = Events.Factory.newInstance();
			
			 Event event = events.addNewEvent();
			 event.setName(this.eventName);
			 event.setId(this.eventId);
			 event.setModelId(this.eventModelId);
			 
			 //add the events to the subcellularmodel
			 model.setEvents(events);			
			 
			// handle partition variable
			 Model.CellSizePartitionVariable partVariable = Model.CellSizePartitionVariable.Factory.newInstance();			 
			 partVariable.setModelId(this.partionVariableId);
			 model.setCellSizePartitionVariable(partVariable);			
	*/		
			 //add the created model to subcellular model
			 
			 //set all the manycell element
/*			 sim.setBioReactor(reactor);
			 sim.setCells(cells);
			 sim.setSimulation(simulation);
			 sim.setDatabase(database);
			 sim.setSubCellularModels(subModel);*/
			 
		
		}else{
			sim = simDoc.getManycell();
		}
		simDoc.setManycell(sim);
		System.out.println(simDoc.toString());
		 
    	return SUCCESS;
    }
	
}
