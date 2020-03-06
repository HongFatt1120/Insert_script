package smart_meterInsert_Test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smart_meterInsert.FileUtils;

class FileUtils_test {
	FileUtils file;
	
	@BeforeEach
	void setUp() throws Exception {
		file = new FileUtils();
	}

	@Test
	void listFilesForFolder_EmptyDirectory_ReturnNull() {
		assertNull(file.listFilesForFolder(""));
	}
	
	@Test
	void listFilesForFolder_InvalidDir_ReturnNull() {
		assertNull(file.listFilesForFolder("abc\\"));
	}
	
	@Test
	void listFilesForFolder_ValidDir_ReturnData() {
		ArrayList<String> fileData = file.listFilesForFolder("Data\\");
		assertEquals(fileData.size(),2);
	}
	
	@Test
	void moveFile_invalidDir_FileDirNoChange() {
		file.moveFile("Dat1a\\", "JtcSmPlugData_158056620.csv");
		ArrayList<String> fileData = file.listFilesForFolder("Data\\");
		assertEquals(fileData.size(),2);
	}
	
	@Test
	void moveFile_invalidFile_FileDirNoChange() {
		file.moveFile("Data\\", "abc.csv");
		ArrayList<String> fileData = file.listFilesForFolder("Data\\");
		assertEquals(fileData.size(),2);
	}

//	@Test
//	void moveFile_ValidDirAndFile_NumberOfFileInDirEqualZero() {
//		file.moveFile("Data\\", "JtcSmPlugData_1580566260.csv");
//		ArrayList<String> fileData = file.listFilesForFolder("Data\\");
//		assertEquals(fileData.size(),0);
//	}
	
	@Test
	void getLatestFile_fileListNull_returnNull() throws ParseException {
		assertNull(file.getLatestFile(null));
	}
	
	@Test
	void getLatestFile_FileListNotInit_returnNull() throws ParseException {
		ArrayList<String> filelist = new ArrayList<String>();
		assertNull(file.getLatestFile(filelist));
	}
	
	@Test
	void getLatestFile_GetlatestFile_returnNull() throws ParseException {
		ArrayList<String> filelist = new ArrayList<String>();
		filelist.add("JtcSmPlugData_1580566260.csv");
		filelist.add("JtcSmPlugData_1580566200.csv");
		assertEquals(file.getLatestFile(filelist),"JtcSmPlugData_1580566260.csv");
	}
	
	

}
