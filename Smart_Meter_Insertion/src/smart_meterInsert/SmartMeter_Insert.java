package smart_meterInsert;

import java.io.File; 
import java.io.IOException;
import java.text.ParseException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;




public class SmartMeter_Insert {

	private static String[] dir;
	private static String[] period;
	static {
	    File log4j2File = new File("log4j2.xml");
	    System.setProperty("log4j2.configurationFile", log4j2File.toURI().toString());
	}
	public static void main(String[] arg) throws IOException, ParseException, InterruptedException {
		
		Prop.init();
	
		period = Prop.p.getProperty("Interval").split(",");
		dir = Prop.p.getProperty("dir").split(",");
		Integer.parseInt(Prop.p.getProperty("Day"));


//		private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyClass.class);
//		logger.info("hello!");
		runTasks();

	}

	// Creating threads depend on properties file
	public static void runTasks() {
//		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(dir.length);
		for (int i = 0; i < period.length; i++) {
			WriteData write = new WriteData(dir[i]);
			double hr = Double.parseDouble(period[i]) * 24;

			Timer timer = new Timer();
			new IntervalUtil(hr);
//			timer.scheduleAtFixedRate(write, iu.getNextInterval(), TimeUnit.MINUTES.toMillis((int) hr * 60));
			timer.scheduleAtFixedRate(write, 0, TimeUnit.MINUTES.toMillis((int) hr * 60));
		}
	}

}
