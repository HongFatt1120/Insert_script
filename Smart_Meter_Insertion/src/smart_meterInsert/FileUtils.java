/*
 * FileUtils class is used to handle actions   
 * that related to files. 
 * 
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.io.File; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;

public class FileUtils {

	
	/**
	 * list the filename in the directory 
	 * @param dir folder directory 
	 * @return Arraylist of string of file names
	 */
	public synchronized ArrayList<String> listFilesForFolder(final String dir) {
		ArrayList<String> filename = new ArrayList<String>();
		File folder = new File(dir);
		if(!folder.exists())
			return null ;
		
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory())
				filename.add(fileEntry.getName());
		}
		return filename;
	}

	/**
	 * moving the completed file into the completed folder
	 * @param dir folder directory 
	 * @param file file to move
	 * @return void
	 */
	public void moveFile(String dir, String file) {

		try {
			File src = new File(dir + file);
			if(!src.exists())
				return;
			File folder = new File(dir + "completed\\");

			Files.move(src.toPath(), folder.toPath().resolve(src.getName()), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Compare all the files, find the latest time  
	 * @param filelist
	 * @return latestFile 
	 * @throws ParseException
	 */
	public  String getLatestFile(ArrayList<String> filelist) throws ParseException {
		if (filelist == null || filelist.size() < 1 ) {
			return null;
		}
		String FileTimestamp = filelist.get(0).split("_")[1];
		int timeInEpoch = Integer.parseInt(FileTimestamp.substring(0, FileTimestamp.lastIndexOf('.')));
		String latestFile = filelist.get(0);
		
		for (int i = 0; i < filelist.size(); i++) {
			String[] firstdate = filelist.get(i).split("_");
			String formattedStr = firstdate[1].substring(0, firstdate[1].lastIndexOf('.'));
			int latestDate =Integer.parseInt(formattedStr);
			if (latestDate  > timeInEpoch) {
				latestFile = filelist.get(i);
			}
		}
		return latestFile;
	}
}
