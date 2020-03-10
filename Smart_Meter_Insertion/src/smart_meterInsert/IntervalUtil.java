package smart_meterInsert;

import java.util.Calendar; 
import java.util.Date;

public class IntervalUtil {

	private double interval; // This may change

	// Constructor
	public IntervalUtil(double interval) {
		this.interval = interval;
	}

	// Function to get unit of intervals
	public double countIntervalUnits() {
		return 24 / this.interval;
	}

	// Convert minute to hours in decimal
	public double getCurrentTimeInHourDecimal() {
		Calendar rightNow = Calendar.getInstance();
		double hour = rightNow.get(Calendar.HOUR_OF_DAY);
		double minute = rightNow.get(Calendar.MINUTE);
		return hour + (minute * 60) / 3600;
	}

	// Find next interval
	public Date getNextInterval() {
		double timeNow = getCurrentTimeInHourDecimal();
		double currentUnit = timeNow / this.interval;
		double nextUnit = Math.ceil(currentUnit);
		Calendar rightNow = Calendar.getInstance();
		double result =  nextUnit*interval;
		int intPart = (int) result;
		double min =   (result - intPart)*60;
		rightNow.set(Calendar.HOUR_OF_DAY,intPart);
		rightNow.set(Calendar.MINUTE,(int)min);
		rightNow.set(Calendar.SECOND, 0);

		return rightNow.getTime();
	}
}