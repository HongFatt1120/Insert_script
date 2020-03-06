package smart_meterInsert_Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smart_meterInsert.CsvUtils;

class csvUtils_Test {
	CsvUtils csv;

	@BeforeEach
	void setUp() throws Exception {
		csv = new CsvUtils();
	}
	

	//Test case for getCsv Method 
	//The followings test cases are correspond to the
	//file in Data\ directory
	@Test
	void getCsv_EmptyFilename_ReturnNull() {
		assertNull(csv.getCsv(""));
	}
	
	@Test
	void getCsv_validFilename_ReturnData() {
		ArrayList<String[]> data = csv.getCsv("Data\\JtcSmPlugData_1580566260.csv");
		assertEquals(data.size(),3);
	}
	
	@Test
	void getCsv_FileNotFound_ReturnNull() {
		assertNull(csv.getCsv("Data\\abc.csv"));
	}
	
	//End of getCsv Method 
	
	
	
	
	

}
