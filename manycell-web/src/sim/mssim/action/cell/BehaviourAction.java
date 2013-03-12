package sim.mssim.action.cell;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import sim.mssim.action.MSSimSupportAction;

public class BehaviourAction extends MSSimSupportAction implements SessionAware {
	private static final long serialVersionUID = 5156288255337069381L;
	private String name;
	
	//counter for number of behaviours
	private int counter;
	
	//session for holding data
	private Map<String, Object> session;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String addBehaviour() {	
			 
    	return SUCCESS;
    }
	
	
}
