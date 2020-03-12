/*
 * The program will read the configuration 
 * from the config.propeties file and create the number of 
 * threads to insert data. 
 *  
 *  
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SmartMeter_Insert {

	private static String[] dir;
	private static String[] period;
	static {
		File log4j2File = new File("log4j2.xml");
		System.setProperty("log4j2.configurationFile",
		        log4j2File.toURI().toString());
	}

	public static void main(String[] arg)
	        throws IOException, ParseException, InterruptedException {

		PropertiesReader.init();

		period = PropertiesReader.prop.getProperty("Interval")
		        .split(",");
		dir = PropertiesReader.prop.getProperty("dir").split(",");
		Integer.parseInt(PropertiesReader.prop.getProperty("Day"));

		runTasks();

	}

	// Creating threads depend on properties file
	public static void runTasks() {
		for (int i = 0; i < period.length; i++) {
			DataWriter write = new DataWriter(dir[i]);
			double hr = Double.parseDouble(period[i]) * 24;

			Timer timer = new Timer();
			IntervalUtils iu = new IntervalUtils(hr);
			timer.scheduleAtFixedRate(write, iu.getNextInterval(),
			        TimeUnit.MINUTES.toMillis((int) hr * 60));
//			timer.scheduleAtFixedRate(write, 0, TimeUnit.MINUTES.toMillis((int) hr * 60));
		}
	}
}
