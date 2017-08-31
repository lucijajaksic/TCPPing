import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Pitcher {
	public int portPitcher = 0;
	public int mps;
	public int size;
	public String hostname = new String("localhost");
	public static ArrayList<Message> messages=new ArrayList<Message>();
	/**
	 * Constructor for pitcher.
	 * @param port
	 * @param mps
	 * @param size
	 * @param hostname
	 */
	public Pitcher(int port, int mps, int size, String hostname) {
		portPitcher = port;
		this.mps = mps;
		this.size = size;
		this.hostname = hostname;
	}

	public void connect() throws UnknownHostException, IOException, InterruptedException {
		// Opening connection.
		SocketChannel sChannel = SocketChannel.open();
		sChannel.connect(new InetSocketAddress(hostname, portPitcher));
		
		// When program is interrupted (Ctrl-C), it prints number of lost messages - messages that pitcher send but did not received.
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		       System.out.println("Lost messages: " + (MessageSending.messageNumber-Pitcher.messages.size()));
		    }
		 });

		
		System.out.format("%s%14s%14s%14s%14s%14s%14s%14s%15s \n", "HH:MM:SS", "TotalMsg", "Mps", "A->B[ms]", "B->A[ms]",
				"A->B->A[ms]", "Max A->B[ms]", "Max B->A[ms]", "Max A->B->A[ms]");
		
		Timer timer = new Timer();
		// Sending X messages every second.
		timer.schedule(new MessageSending(sChannel, size), 0, 1000 / mps);
		// Printing statistic every second.
		timer.schedule(new Statistic(), 1000, 1000);
		while (true) {
			// Allocating a ByteBuffer of specific size.
			ByteBuffer pomBbuf = ByteBuffer.allocate(size);
			
			int numBytesRead;
			long catcherSendTime=0;
			long pitcherSendTime=0;
			int messageNumber=0;
			try {
				// Reading a message from catcher.
				numBytesRead = sChannel.read(pomBbuf);
				// Reading size, messafeNUmber, pitcherSendTime and catcherSendTime from message.
				pomBbuf.position(0);
				size = pomBbuf.getInt();
				messageNumber = pomBbuf.getInt();
				pitcherSendTime = pomBbuf.getLong();
				catcherSendTime=pomBbuf.getLong();
				

			} catch (NotYetConnectedException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Adding new message to an array list of messages.
			messages.add(new Message(messageNumber, pitcherSendTime, catcherSendTime, new Date().getTime() ));
			
		}
		
		
		

	}

}