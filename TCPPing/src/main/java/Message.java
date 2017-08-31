public class Message {
	long pitcherSend;
	long catcher;
	long pitcherRecived;
	int messageId;
	/**
	 * Message contains messageId, pitcherSend, catcher (receive == send) and pitcherRecived.
	 * All four parametars are passed to this constructor.
	 * @param messageId
	 * @param pitcherSend
	 * @param catcher
	 * @param pitcherRecived
	 */
	public Message(int messageId,long pitcherSend, long catcher, long pitcherRecived) {
		this.messageId=messageId;
		this.pitcherSend=pitcherSend;
		this.catcher=catcher;
		this.pitcherRecived=pitcherRecived;
	}
	
	@Override
	// Overrides method toString. 
	public String toString() {
		String str=pitcherSend+ " "+ catcher + " " + pitcherRecived;
		return str;
	}
}
