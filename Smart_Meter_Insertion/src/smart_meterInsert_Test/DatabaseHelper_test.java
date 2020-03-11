package smart_meterInsert_Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smart_meterInsert.DatabaseHelper;
import smart_meterInsert.PropertiesReader;

class DatabaseHelper_test {

	DatabaseHelper dbh;

	@BeforeEach
	void setUp() throws Exception {
		dbh = DatabaseHelper.getInstance();
		PropertiesReader.init();
	}

	@Test
	void prepareParams_InvalidLength_ReturnFalse() {
		String[] key = { "abc", "efc" };
		String[] value = { "abc" };
		assertFalse(dbh.prepareParams("abc", key, value));
	}

	@Test
	void prepareParams_ValidLengh_ReturnTrue() {
		String[] key = { "abc", "efc" };
		String[] value = { "abc", "abc" };

		assertTrue(dbh.prepareParams("abc", key, value));

	}

	@Test
	void getQuery_EmptyParam_ReturnNull() throws UnsupportedEncodingException {
		String[] key = {};
		String[] value = {};
		dbh.prepareParams("", key, value);
		assertNull(dbh.getQuery());
	}

	@Test
	void getQuery_ValidData_ReturnEncoded() throws UnsupportedEncodingException {
		String[] key = { "key1", "key2", "key3" };
		String[] value = { "val1", "val2", "val3" };
		dbh.prepareParams("table1", key, value);
		assertEquals(dbh.getQuery(), "table=table1&key1=val1&key2=val2&key3=val3");

		// table=table1&key1=val1&key2=val2&key3=val3
	}

	@Test
	void httpPost_ValidUrl_ReturnTrue() {
//http://192.168.0.10:8080/simplecreate?table=JtcSmPlugData&plugId=&deviceId&plugStatus=&energyConsumption=1.9&powerLoad=1.8&loggedTime=2020-02-24T15:50:20.000Z&responseTime=2020-02-24T15:50:20.000Z

		String[] key = { "plugId", "deviceId", "plugStatus", "energyConsumption", "powerLoad", "loggedTime",
				"responseTime" };
		String[] val = { "PlugTess1_1", "", "1", "=1.9", "1.8", "2020-02-24T15:50:20.000Z",
				"2020-02-24T15:50:20.000Z" };
		dbh.prepareParams("JtcSmPlugData", key, val);
		assertEquals(dbh.httpPost(),200);
	}

	@Test
	void httpPost_InvalidUrl_ReturnFalse() {
		String[] key = { "plugId", "deviceId", "plugStatus", "energyConsumption", "powerLoad", "loggedTime",
				"responseTime" };
		String[] val = { "PlugTess1_1", "", "1", "=1.9", "1.8", "2020-02-24 15:50:20.000Z",
				"2020-02-24T15:50:20.000Z" };
		dbh.prepareParams("JtcSmPlugData", key, val);
		assertEquals(dbh.httpPost(),400);
	}

}
