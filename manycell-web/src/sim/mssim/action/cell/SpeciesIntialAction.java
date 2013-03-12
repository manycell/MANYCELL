package sim.mssim.action.cell;

import java.util.Map;

//import org.manycell.config.CellsDocument.Cells.Cell.ChangeModelInitialConditons.Variable;
import org.manycell.config.ManycellDocument;
/*import org.manycell.config.CellsDocument.Cells;
import org.manycell.config.CellsDocument.Cells.Cell;
import org.manycell.config.CellsDocument.Cells.Cell.ChangeModelInitialConditons;*/
import org.manycell.config.ManycellDocument.Manycell;

import sim.mssim.action.MSSimSupportAction;

public class SpeciesIntialAction extends MSSimSupportAction{
	private static final long serialVersionUID = 5156288255337069381L;
	private String id;	
	private String name;
	private String value;	
	private String actualValue;	
	
	//counter for species 
	private int counter;	
	
	//session for holding data
	private Map<String, Object> session;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
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

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String input() throws Exception {
		return SUCCESS;
	}
	
	public String modifySpecisInitial() {
	ManycellDocument simDoc = null;	
	
	//get the manycell document from the session
	simDoc = (ManycellDocument)getSession().get("xml");
	String cellId = "id"+(String)getSession().get("cCounter");
	
	Manycell sim = simDoc.getManycell();	
/*	
	Cells cells = sim.getCells();	
	Cell [] cell = cells.getCellArray();
	
	for(int i=0; i<cell.length; i++){
		String id = cell[i].getId();
		if(cellId.equals(id)){
			ChangeModelInitialConditons sInitial = cell[i].getChangeModelInitialConditons();
			if(sInitial==null)sInitial = ChangeModelInitialConditons.Factory.newInstance();
			Variable variable = sInitial.addNewVariable();
			////variable.setIsRandom(false);
			variable.setModelId(this.id);
			variable.setValue(Float.parseFloat(this.value));	
			cell[i].setChangeModelInitialConditons(sInitial);
		}
	}
	cells.setCellArray(cell);
	sim.setCells(cells);
	simDoc.setManycell(sim);
	this.session.put("xml", simDoc);
*/	
	return SUCCESS;
	}
}
