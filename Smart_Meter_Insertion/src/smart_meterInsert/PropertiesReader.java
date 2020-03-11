/*
 * PropertiesReader is used to stored the values from the  
 * config.properties file. 
 * 
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
	/* prop is public object for another classes to access the value in 
	 * properties file*/
	public static Properties prop = null;
	
	/* reader is used to read the config.propties file*/
	private static FileReader reader = null;

	
	/**
	 * Read values into a properties 
	 * @throws IOException
	 */
	public static void init() throws IOException {
		reader = new FileReader("config.properties");
		prop = new Properties();
		prop.load(reader);
	}
}
