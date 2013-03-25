package sim.msscc.data;

/**
 * The <code>EventData</code> is responsible for handling event data for the cell agents. 
 * Events are used to connect the internal biochemistry of cell to its behaviours at the cellular level  
 *
 * @author Joseph O. Dada 
 *
 * @copyright
 * MANYCELL, UNICELLSYS Project
 * (C) The University of Manchester 2012
 *
 */
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

	/**
	 * @return the gTime
	 */
	public float getgTime() {
		return gTime;
	}

	/**
	 * @param gTime the gTime to set
	 */
	public void setgTime(float gTime) {
		this.gTime = gTime;
	}

	/**
	 * @return the eventTime
	 */
	public float getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(float eventTime) {
		this.eventTime = eventTime;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the stateAtEvent
	 */
	public String getStateAtEvent() {
		return stateAtEvent;
	}

	/**
	 * @param stateAtEvent the stateAtEvent to set
	 */
	public void setStateAtEvent(String stateAtEvent) {
		this.stateAtEvent = stateAtEvent;
	}

	/**
	 * @return the simState
	 */
	public String getSimState() {
		return simState;
	}

	/**
	 * @param simState the simState to set
	 */
	public void setSimState(String simState) {
		this.simState = simState;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the agentgAge
	 */
	public int getAgentgAge() {
		return agentgAge;
	}

	/**
	 * @param agentgAge the agentgAge to set
	 */
	public void setAgentgAge(int agentgAge) {
		this.agentgAge = agentgAge;
	}

	/**
	 * @return the tcid
	 */
	public int getTcid() {
		return tcid;
	}

	/**
	 * @param tcid the tcid to set
	 */
	public void setTcid(int tcid) {
		this.tcid = tcid;
	}

	/**
	 * @return the metaData
	 */
	public String getMetaData() {
		return metaData;
	}

	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	/**
	 * @return the cycleTime
	 */
	public float getCycleTime() {
		return cycleTime;
	}

	/**
	 * @param cycleTime the cycleTime to set
	 */
	public void setCycleTime(float cycleTime) {
		this.cycleTime = cycleTime;
	}
	
	

}
