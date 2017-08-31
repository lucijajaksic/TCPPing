import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class Statistic extends TimerTask {
	public long AtoB;
	public long BtoA;
	public long AtoBtoA;
	public long maxAtoB;
	public long maxBtoA;
	public long maxAtoBtoA;
	
	/**
	 * After every second method run is called to print statistics.
	 */
	@Override
	public void run() {
		AtoB = 0;
		BtoA = 0;
		AtoBtoA = 0;
		maxAtoB = 0;
		maxBtoA = 0;
		maxAtoBtoA = 0;
		int counter = 0;
		// Current time in milliseconds.
		long currentTime = new Date().getTime();
		//Iterating through all received messages and calculating the statistics of messages received in last 1000 milliseconds.
		for (Message mes : Pitcher.messages) {
			if ((currentTime - mes.pitcherRecived) <= 1000) {
				// Sum of milliseconds between message send from pitcher and received from catcher in last 1000 milliseconds.
				AtoB += (mes.catcher - mes.pitcherSend);
				// Sum of milliseconds between message send from catcher and received from pitcher in last 1000 milliseconds.
				BtoA += (mes.pitcherRecived - mes.catcher);
				// Sum of milliseconds between message send from pitcher and received from pitcher in last 1000 milliseconds.
				AtoBtoA += (mes.pitcherRecived - mes.pitcherSend);
				counter++;
				// Max time in ms between message send from pitcher and received from catcher in last 1000 milliseconds.
				if ((mes.catcher - mes.pitcherSend) > maxAtoB) {
					maxAtoB = (mes.catcher- mes.pitcherSend);
				}
				// Max time in ms between message send from catcher and received from pitcher in last 1000 milliseconds.
				if ((mes.pitcherRecived - mes.catcher) > maxBtoA) {
					maxBtoA = (mes.pitcherRecived - mes.catcher);
				}
				// Max time in ms between message send from pitcher and received from pitcher in last 1000 milliseconds.
				if ((mes.pitcherRecived - mes.pitcherSend) > maxAtoBtoA) {
					maxAtoBtoA = (mes.pitcherRecived - mes.pitcherSend);
				}

			}
		}
		// Calculating the averages.
		if (counter != 0) {
			AtoB = AtoB / counter;
			BtoA = BtoA / counter;
			AtoBtoA = AtoBtoA / counter;
		}
		// Printing statistics.
		System.out.format("%s%14d%14d%14d%14d%14d%14d%14d%15d \n",
				new SimpleDateFormat("HH:mm:ss").format(new Date().getTime()), MessageSending.messageNumber, Main.mps, AtoB,
				BtoA, AtoBtoA, maxAtoB, maxBtoA, maxAtoBtoA);
		

	}

}
