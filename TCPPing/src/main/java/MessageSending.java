import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.TimerTask;

public class MessageSending extends TimerTask {

	SocketChannel sChannel;
	public static int messageNumber = 0;
	String message;
	int size;

	/**
	 * Constructor for class MessageSending.
	 * 
	 * @param sChannel
	 * @param size
	 */
	public MessageSending(SocketChannel sChannel, int size) {
		this.sChannel = sChannel;
		this.size = size;
	}

	@Override
	public void run() {
		// Message number tracks number of messages send.
		messageNumber++;
		// Allocating a ByteBuffer of specific size.
		ByteBuffer bbuf = ByteBuffer.allocate(size);
		// Putting informations in message.
		bbuf.putInt(size);
		bbuf.putInt(messageNumber);
		bbuf.putLong(new Date().getTime());
		bbuf.position(0);

		try {
			//Sending message to Catcher.
			sChannel.write(bbuf);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
