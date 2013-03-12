package sim.msscc.data;

public class DivisionTimeEventData {
	private float gTime;
	private float divisionTime;
	private String eventName = "division";	
	private String stateAtEvent;
	private String agentId;
	private String tcid;
	
	public DivisionTimeEventData(float gTime, float divisionTime,
			String eventName,String stateAtEvent, String agentId, String tcid) {
		super();
		this.gTime = gTime;
		this.divisionTime = divisionTime;
		this.eventName = eventName;
		this.stateAtEvent = stateAtEvent;
		this.agentId = agentId;
		this.tcid = tcid;
	}
	
	
	
	

}
