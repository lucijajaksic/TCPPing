import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.TimerTask;

public class MessageSending extends TimerTask {
	DataOutputStream outToServer;
	int messageNumber=0;
	String message;
	int size;
	public MessageSending(DataOutputStream outToServer, int size) {
		this.outToServer=outToServer;
		this.size=size;
	}
	
	@Override
	public void run() {
		messageNumber++;
		ByteBuffer bbuf= ByteBuffer.allocate(size);
		//message=messageNumber + " " +Long.toString(new Date().getTime())+" ";
		//bbuf.put(message.getBytes());
		bbuf.putInt(messageNumber);
		bbuf.putLong(new Date().getTime());
		
//		sb.insert(0,messageNumber);
//		sb.insert(9,"-");
//		sb.insert(10,new Date().getTime());
//		
//		
//		message=sb.toString();
		
		try {
			outToServer.writeBytes(message + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Pitcher sends" + message);
		
	}
	
}
