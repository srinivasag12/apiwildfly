package com.bsol.iri.fileSharing.util;

/**
 * 
 * @author rupesh
 *	
 * This class is used for basic date time manipulation
 */

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public static Date getTodaysDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.getTime();
	}

	public static Date addDaysToCurrectDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date subDaysToCurrectDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}
}
