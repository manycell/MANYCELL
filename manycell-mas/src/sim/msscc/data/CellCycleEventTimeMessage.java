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
	 * @return the globalTime
	 */
	public float getGlobalTime() {
		return globalTime;
	}


	/**
	 * @param globalTime the globalTime to set
	 */
	public void setGlobalTime(float globalTime) {
		this.globalTime = globalTime;
	}
	

}
