package smart_meterInsert;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Prop {
	private static FileReader reader;
	public static Properties p;
	// Loading properties file
	public static void init() throws IOException {
		reader = new FileReader("config.properties");
		p = new Properties();
		p.load(reader);
	}
}
