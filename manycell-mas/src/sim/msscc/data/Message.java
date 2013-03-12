package sim.msscc.data;

public abstract class Message {
	private String messageId;
	private String sender;
	private String receiver;
	
	Message(String sender, String receiver, String messageId){
		this.sender = sender;
		this.receiver = receiver;
		this.messageId = messageId;
	}
}
