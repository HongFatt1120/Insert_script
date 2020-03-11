/*
 * IntervalUtils class is used to handle actions   
 * that related to datetime. 
 * 
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.util.Calendar;
import java.util.Date;

public class IntervalUtils {

	/*store the interval in hours for constructor*/
	private double interval;

	/**
	 * Constructor
	 * @param null
	 * @param interval
	 */
	public IntervalUtils(double interval) {
		this.interval = interval;
	}

	/**
	 * Convert minute to hours in decimal 
	 * @param null
	 * @return
	 */
	public double getCurrentTimeInHourDecimal() {
		Calendar rightNow = Calendar.getInstance();
		double hour = rightNow.get(Calendar.HOUR_OF_DAY);
		double minute = rightNow.get(Calendar.MINUTE);
		return hour + (minute * 60) / 3600;
	}
	/**
	 * get the next interval in date time
	 * @param null
	 * @return rightNow next interval in date time
	 */
	public Date getNextInterval() {
		double timeNow = getCurrentTimeInHourDecimal();
		double currentUnit = timeNow / this.interval;
		double nextUnit = Math.ceil(currentUnit);
		Calendar rightNow = Calendar.getInstance();
		double result = nextUnit * interval;
		int intPart = (int) result;
		double min = (result - intPart) * 60;
		rightNow.set(Calendar.HOUR_OF_DAY, intPart);
		rightNow.set(Calendar.MINUTE, (int) min);
		rightNow.set(Calendar.SECOND, 0);

		return rightNow.getTime();
	}
}