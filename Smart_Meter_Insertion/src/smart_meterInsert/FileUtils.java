package smart_meterInsert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileUtils {

	
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

	public synchronized void moveFile(String dir, String file) {

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

	public synchronized String getLatestFile(ArrayList<String> filelist) throws ParseException {
		if (filelist == null || filelist.size() < 1 ) {
			return null;
		}
		String currentdate = filelist.get(0).split("_")[1];
		int currentDateInE = Integer.parseInt(currentdate.substring(0, currentdate.lastIndexOf('.')));
		String finalDate = filelist.get(0);
		
		for (int i = 0; i < filelist.size(); i++) {
			String[] firstdate = filelist.get(i).split("_");
			String formattedStr = firstdate[1].substring(0, firstdate[1].lastIndexOf('.'));
			int latestDate =Integer.parseInt(formattedStr);
			if (latestDate  > currentDateInE) {
				currentdate = currentdate;
				finalDate = filelist.get(i);
			}
		}
		return finalDate;
	}
}
