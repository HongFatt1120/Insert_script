/*
 * CsvUtils class is used to handle actions  
 * that related to csv file 
 * 
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvUtils {
	/*
	 * Function are developed to read and retrieve data from a Csv file.
	 */

	/*
	 * br is a BufferedReader object to read data from file.
	 */
	private BufferedReader br = null;

	/*
	 * String line is used to take in each line of records from the BufferReader
	 * object.
	 */
	private String line = "";

	/*
	 * Delimiter indicate that how the records is being split. 
	 * */
	private String delimiter = ",";

	/*
	 * Initialize an arraylist of string array to store data from csv file
	 */
	private ArrayList<String[]> data = new ArrayList<String[]>();

	/**
	 * Retrieving data from csv
	 * @param file provide a file path
	 * @return Arraylist<String[]> a set of data in an arraylist of string array 
	 * @exception FileNotFoundException invalid file path given.
	 * @exception IOException I/O operations got interrupt.
	 */
	public ArrayList<String[]> getCsv(String file) {
		
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] records = line.split(delimiter);
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