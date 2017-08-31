
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class Catcher {
	public int portCatcher = 0;
	public String bind = new String();
	/**
	 * Constructor for Catcher.
	 * @param port
	 * @param bind
	 */
	public Catcher(int port, String bind) {
		portCatcher = port;
		this.bind = bind;
	}

	public void listen() throws Exception {
		// Opening server socket.
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(portCatcher));
		SocketChannel sChannel = serverSocketChannel.accept();
		sChannel.configureBlocking(true);
		
		int size = 300;
		int messageNumber = 0;
		long pitcherSendTime = 0;
		while (true) {
			// Allocating a ByteBuffer of specific size.
			ByteBuffer bbuf = ByteBuffer.allocate(3000);
			int numBytesRead;
			try {
				// Reading message from pitcher.
				numBytesRead = sChannel.read(bbuf);

				bbuf.position(0);
				// Getting informations from message.
				size = bbuf.getInt();
				messageNumber = bbuf.getInt();
				pitcherSendTime = bbuf.getLong();
				
			} catch (NotYetConnectedException e) {
			}
			
			// Sending message back to pitcher.
			ByteBuffer pomBbuf = ByteBuffer.allocate(size);
			pomBbuf.putInt(size);
			pomBbuf.putInt(messageNumber);
			pomBbuf.putLong(pitcherSendTime);
			pomBbuf.putLong(new Date().getTime());
			pomBbuf.position(0);

			try {
				sChannel.write(pomBbuf);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}