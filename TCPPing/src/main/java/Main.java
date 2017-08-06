import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

class Main {

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
	static String hostname = new String("localhost");

	public static void main(String... args) throws Exception {
		Main ex = new Main();
		JCommander.newBuilder().addObject(ex).build().parse(args);
		ex.run();

		if (pit && !cat) {
			Pitcher pitcher = new Pitcher(port,mps,size,hostname);
			pitcher.connect();
		} else if (cat && !pit) {
			Catcher catcher = new Catcher(port,bind);
			catcher.listen();
		} else {
			System.out.println("Pitcher and Catcher can not be choosen at the same time.");
		}
	}

	public void run() {
		System.out.printf("Pitcher:%s Catcher: %s Port: %d Hostname: %s Bind: %s Mps: %d Size: %d \n", pit, cat, port,
				hostname, bind, mps, size);
	}
}