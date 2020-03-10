package smart_meterInsert;

import java.text.ParseException; 
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


public class WriteData extends TimerTask {

	private CsvUtils csv;
	private DatabaseHelper dbc = DatabaseHelper.getInstance();
	private FileUtils fileUtils = new FileUtils();
	private String dir;
	final Logger logger = (Logger) LogManager.getLogger("smart_insert");


	public WriteData(String dir) {
		this.dir = dir;
	}

	@Override
	public void run() {

		csv = new CsvUtils();
		try {
			ArrayList<String> files = fileUtils.listFilesForFolder(dir);
			String latestFile = fileUtils.getLatestFile(files);

			if (files.size() < 1)
				return;
			for (String file : files) {
				if (!file.equals(latestFile)) {
					// Inserting latest data collected from the csv
					ArrayList<String[]> data = csv.getCsv(this.dir + file);
					String tablename = file.split("_")[0];
					// loop through request
					for (int i = 1; i < data.size(); i++) {
						dbc.prepareParams(tablename, data.get(0), data.get(i));
						String query = dbc.getQuery();
						int responseCode = dbc.httpPost();
						if(responseCode != 200) {
							
							// the following statement is used to log any messages
							String msg = "from file " + file
									+ " rows " + i
									+ "\nURL : " + Prop.p.getProperty("API") + query
									+ "\nHttp Response Code : " + responseCode;
							logger.warn(msg);
//							
						}
					}
					fileUtils.moveFile(dir, file);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}