/*
 * DataWriter will be trigger when intervals 
 * and insert the data into database by doing an API call
 * 
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class DataWriter extends TimerTask {

	/* csv is a CsvUtils object to get the data from file. */
	private CsvUtils csv;

	/* databasHelper is getting an singleton instance */
	private DatabaseHelper databaseHelper = DatabaseHelper
	        .getInstance();

	/* Initialize a fileUtils object to do is getting an singleton instance */
	private FileUtils fileUtils = new FileUtils();

	/* store the current folder directory for constructor */
	private String dir;

	/* Initialize Log4j2 object to log errors if any. */
	final Logger logger = (Logger) LogManager
	        .getLogger("smart_insert");

	/**
	 * Constructor
	 * 
	 * @param null
	 * @param dir  folder directory
	 */
	public DataWriter(String dir) {
		this.dir = dir;
	}

	/**
	 * Running a thread to insert the database if there's more than 1 file in the
	 * folder
	 * 
	 * @param null
	 * @return null
	 * @exception ParseException
	 */
	@Override
	public void run() {

		csv = new CsvUtils();
		try {
			ArrayList<String> files = fileUtils
			        .listFilesForFolder(dir);
			String latestFile = fileUtils.getLatestFile(files);

			if (files == null || files.size() < 1)
				return;
			for (String file : files) {
				if (!file.equals(latestFile)) {
					// Inserting latest data collected from the csv
					ArrayList<String[]> data = csv
					        .getCsv(this.dir + file);
					
					int nameIndex = file.lastIndexOf("_");
					String tablename = file.substring(0, nameIndex).replace("_", "-");
					// loop through request
					for (int i = 1; i < data.size(); i++) {
						databaseHelper.prepareParams(
						        data.get(0), data.get(i));
//						String query = databaseHelper.getQuery();
						int responseCode = databaseHelper.httpPost(tablename);
						if (responseCode != 201 || responseCode == 0) {
							// the following statement is used to log any messages
							String msg = "from file " + file + " rows "
							        + i + "\nURL : "
							        + PropertiesReader.prop
							                .getProperty("API")
							        + "\nHttp Response Code : "
							        + responseCode;
							logger.warn(msg);
////							
						}
					}
					fileUtils.moveFile(dir, file);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}