import java.sql.Timestamp;

public class Message {
	long pitcherSend;
	long catcherRecived;
	long catcherSend;
	long pitcherRecived;
	int messageId;
	
	public Message(int messageId,long pitcherSend) {
		this.messageId=messageId;
		this.pitcherSend=pitcherSend;
	}
	
	
}
