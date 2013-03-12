package sim.msscc.data;

public class EventData {
	
	private float gTime;
	private float eventTime;
	private String eventName = "division";
	private String stateAtEvent;
	private String simState;
	private String agentId;
	private int agentgAge;
	private int tcid;
	private String metaData;
	private float cycleTime;
	
	public EventData(float gTime, String agentId){
		this.agentId = agentId;
		this.gTime = gTime;
	}
	
	public EventData(float gTime, float eventTime,
			String eventName, String stateAtEvent, String agentId, int tcid) {
		super();
		this.gTime = gTime;
		this.eventTime = eventTime;
		this.eventName = eventName;
		this.stateAtEvent = stateAtEvent;
		this.agentId = agentId;
		this.tcid = tcid;
	}	
	
	
	public float getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(float cycleTime) {
		this.cycleTime = cycleTime;
	}

	public String getSimState() {
		return simState;
	}

	public void setSimState(String simState) {
		this.simState = simState;
	}

	public int getAgentgAge() {
		return agentgAge;
	}


	public void setAgentgAge(int agentgAge) {
		this.agentgAge = agentgAge;
	}


	public float getgTime() {
		return gTime;
	}

	public void setgTime(float gTime) {
		this.gTime = gTime;
	}

	public float getEventTime() {
		return eventTime;
	}

	public void setEventTime(float eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}	
	

	public String getStateAtEvent() {
		return stateAtEvent;
	}

	public void setStateAtEvent(String stateAtEvent) {
		this.stateAtEvent = stateAtEvent;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public int getTcid() {
		return tcid;
	}

	public void setTcid(int tcid) {
		this.tcid = tcid;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}	
	
	
	

}
