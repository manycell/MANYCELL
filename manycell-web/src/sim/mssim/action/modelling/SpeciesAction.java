package sim.mssim.action.modelling;


import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import sim.mssim.action.MSSimSupportAction;
import sim.mssim.modelmanager.ProcessSBMLModelManager;

import com.opensymphony.xwork2.ActionSupport;

public class SpeciesAction extends MSSimSupportAction implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	private String SName;
	private double value;	
	private Map<String, Object> session;

	
	public String getSName() {
		return SName;
	}

	public void setSName(String sName) {
		SName = sName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}	
	
public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

public String execute() throws Exception{	
		String modelString = (String)getSession().get("sbmlModel");
		ProcessSBMLModelManager wsManager = new ProcessSBMLModelManager(modelString, true);
		//String modelName  = 		
		setValue(wsManager.getSpeciesValueByName(getSName()));			
		return SUCCESS;
	}
	
	
}