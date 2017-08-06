
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public class Catcher {
	public int portCatcher=0;
	public String bind=new String();
	public Catcher(int port, String bind) {
		portCatcher=port;
		this.bind=bind;
	}
	
	public void listen() throws Exception {
		String message;
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(bind, portCatcher));
		
		Socket connectionSocket = serverSocket.accept();
		BufferedReader inFromPitcher = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToPitcher = new DataOutputStream(connectionSocket.getOutputStream());
		
		while (true) {
			int size;
			message = inFromPitcher.readLine();
			System.out.println("Received: " + message);	
			size=message.getBytes().length;
			Integer a=5;
			System.out.println(a.byteValue());
			StringBuilder sb = new StringBuilder(message);
			sb.insert(29,"-");
			sb.insert(30, new Date().getTime());
			
			message=sb.toString();
			System.out.println(message);
			outToPitcher.writeBytes(message + "\n");
		}
	}
}