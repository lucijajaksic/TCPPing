import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Logger;

public class Pitcher {
	public int portPitcher = 0;
	public int mps;
	public int size;
	public String hostname = new String("localhost");
	public int AtoB;
	public int BtoA;
	public int AtoBtoA;
	public int maxAtoB;
	public int maxBtoA;
	public int maxAtoBtoA;

	public Pitcher(int port, int mps, int size, String hostname) {
		portPitcher = port;
		this.mps = mps;
		this.size = size;
		this.hostname = hostname;
	}

	public void connect() throws UnknownHostException, IOException, InterruptedException {
		
		String message;
		Socket clientSocket = new Socket(hostname, portPitcher);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.format("%s%10s%10s%10s%10s%10s%10s%10s%15s \n", "HH:MM:SS",	"TotalMsg",	"Mps",	"A->B",	"B->A",	"A->B->A",	"Max A->B",	"Max B->A",	"Max A->B->A");
		Timer timer=new Timer();
		timer.schedule(new MessageSending(outToServer, size),0, 1000/mps);
		while (true) {	
			
			System.out.format("%s%10s%10d%10d%10d%10d%10d%10d%15d \n", new SimpleDateFormat("HH:mm:ss").format(new Date().getTime()), "9999", mps, AtoB, BtoA, AtoBtoA, maxAtoB,maxBtoA, maxAtoBtoA );
			message = inFromServer.readLine();

			System.out.println("From server: " + message);
			message+= new Date().getTime();
			System.out.println("Pitcher recives" + message);
			
			
			Thread.sleep(1000/mps);
			
		}
		//clientSocket.close();

	}
}