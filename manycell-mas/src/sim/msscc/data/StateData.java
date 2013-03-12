package sim.msscc.data;

public class StateData {
	private float gTime;	
	private String simState;
	private String agentId;
	private String isDivided;
	private float glucoseconsumed;
	private int tcid;
	private String metaData;
	
	public StateData(float gTime, String agentId){
		this.agentId = agentId;
		this.gTime = gTime;
	}

	public float getgTime() {
		return gTime;
	}

	public void setgTime(float gTime) {
		this.gTime = gTime;
	}

	public String getSimState() {
		return simState;
	}

	public void setSimState(String simState) {
		this.simState = simState;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getIsDivided() {
		return isDivided;
	}

	public void setIsDivided(String isDivided) {
		this.isDivided = isDivided;
	}

	public float getGlucoseconsumed() {
		return glucoseconsumed;
	}

	public void setGlucoseconsumed(float glucoseconsumed) {
		this.glucoseconsumed = glucoseconsumed;
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
