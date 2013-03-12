package sim.mssim.action.cell;

import java.util.Map;

import sim.mssim.action.MSSimSupportAction;

public class ActiveVariableAction extends MSSimSupportAction{
	private static final long serialVersionUID = 5156288255337069381L;
	
	private String id;
	private String name;
	
	//variable counter
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
	
	public String input() throws Exception {
		return SUCCESS;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
