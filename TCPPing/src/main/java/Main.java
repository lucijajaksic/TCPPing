import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

class Main {
	
	/**
	 * Syntax used by JCommander to parse the program arguments.
	 */
	@Parameter(names = "-p", description = "Pitcher")
	static boolean pit = false;
	@Parameter(names = "-c", description = "Catcher")
	static boolean cat = false;
	@Parameter(names = "-port", description = "TCP socket port")
	static int port=4444;
	@Parameter(names = "-bind", description = "TCP socket bind adress")
	static String bind = new String("0.0.0.0");
	@Parameter(names = "-mps", description = "Messages per second")
	static int mps = 1;
	@Parameter(names = "-size", description = "Length of a message")
	static int size = 300;
	@Parameter(description = "Host name")
	static List<String> hostname = new ArrayList<String>();

	public static void main(String... args) throws Exception {
		
		Main ex = new Main();
		// JCommander parsing...
		JCommander.newBuilder().addObject(ex).build().parse(args);
		ex.run();
		// Checking if the size of message is between 50 and 3000 bytes.
		if (size>3000 || size <50){
			System.out.println("Message size should be between 50 and 3000.");
			System.exit(1);
		}
		// Checking whether to run Pitcher or Catcher.
		if (pit && !cat) {
			Pitcher pitcher = new Pitcher(port,mps,size, hostname.get(0));
			pitcher.connect();
		} else if (cat && !pit) {
			Catcher catcher = new Catcher(port,bind);
			catcher.listen();
		} else if (!cat && !pit) {
			System.out.println("Pitcher or Catcher needs to be choosen.");
		}
		else {
			System.out.println("Pitcher and Catcher can not be choosen at the same time.");
		}
		
		
		
	}

	public void run() {
		// Printing parameters of the program. 
		System.out.printf("Pitcher:%s Catcher: %s Port: %d Hostname: %s Bind: %s Mps: %d Size: %d \n", pit, cat, port,
				hostname, bind, mps, size);
	}
}