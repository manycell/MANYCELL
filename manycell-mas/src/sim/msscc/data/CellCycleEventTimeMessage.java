package sim.msscc.data;

public class CellCycleEventTimeMessage extends Message {
	private String eventName;
	private float eventTime;
	private float globalTime;
	
	
	public CellCycleEventTimeMessage(String sender, String receiver,
			String messageId, String eventName, float eventTime,
			float globalTime) {
		super(sender, receiver, messageId);
		this.eventName = eventName;
		this.eventTime = eventTime;
		this.globalTime = globalTime;
	}


	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public float getEventTime() {
		return eventTime;
	}


	public void setEventTime(float eventTime) {
		this.eventTime = eventTime;
	}


	public float getGlobalTime() {
		return globalTime;
	}


	public void setGlobalTime(float globalTime) {
		this.globalTime = globalTime;
	}
}
