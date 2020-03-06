package smart_meterInsert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CsvUtils {

	private BufferedReader br = null;
	private String line = "";
	private String cvsSplitBy = ",";
	
	// Reading Csv and return data from the csv
	public ArrayList<String[]> getCsv(String file) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] records = line.split(cvsSplitBy);
				data.add(records);
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
}