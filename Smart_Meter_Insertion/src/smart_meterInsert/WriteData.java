package smart_meterInsert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class WriteData extends TimerTask {

	private CsvUtils csv;
	private DatabaseHelper dbc = DatabaseHelper.getInstance();
	private FileUtils fileUtils = new FileUtils();
	private String dir;
	Logger logger = Logger.getLogger("MyLog");
	FileHandler fh;

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
						int responseCode = dbc.httpPost();
						if(responseCode != 200) {
							fh = new FileHandler("InsertionLog.log", true);
							logger.addHandler(fh);
							SimpleFormatter formatter = new SimpleFormatter();
							fh.setFormatter(formatter);
							// the following statement is used to log any messages
							String msg = new Date() + "\nfrom file " + file
									+ " rows " + i
									+ "\nURL : " + Prop.p.getProperty("API") + dbc.getQuery()
									+ "\nHttp Response Code : " + responseCode;
							logger.warning(msg);
							fh.close();
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