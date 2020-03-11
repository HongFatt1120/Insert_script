package smart_meterInsert_Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smart_meterInsert.IntervalUtils;

class IntervalUtil_test {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void getNextInterval_getNearestHour_returnNearestHour()
	{
		IntervalUtils iu = new IntervalUtils(1);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, 1);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		System.out.println(now.getTime());
		assertEquals(iu.getNextInterval().toString(),now.getTime().toString());
	}
	
}
