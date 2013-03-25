package sim.msscc.data;

/**
 * The <code>StateData</code> defines basics attribute for handling state information for cell agents 
 *
 * @author Joseph O. Dada 
 *
 * @copyright
 * MANYCELL, UNICELLSYS Project
 * (C) The University of Manchester 2012
 *
 */
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
	 * @return the isDivided
	 */
	public String getIsDivided() {
		return isDivided;
	}

	/**
	 * @param isDivided the isDivided to set
	 */
	public void setIsDivided(String isDivided) {
		this.isDivided = isDivided;
	}

	/**
	 * @return the glucoseconsumed
	 */
	public float getGlucoseconsumed() {
		return glucoseconsumed;
	}

	/**
	 * @param glucoseconsumed the glucoseconsumed to set
	 */
	public void setGlucoseconsumed(float glucoseconsumed) {
		this.glucoseconsumed = glucoseconsumed;
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

	
	
	

}
