package com.pig8.api.platform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author navy
 *
 */
public class DateUtils {

	public static String getCurrentDate() {
		return formatDateToString(new Date(), "yyyy-MM-dd");
	}

	public static String getCurrentTime() {
		return formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDateToString(Date date, String format) {
		if(date==null){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static String formatDateToString(Date date) {
		return formatDateToString(date, "yyyy-MM-dd");
	}

	public static String formatDateToString(long millis, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(millis);
		return dateFormat.format(c.getTime());
	}
	
	public static Date stringToDate(String date, String format) {
		if(date==null){
			return null;
		}
		DateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat(format);
		Date day = null;
		try {
			day = dateFormat.parse(date);
		} catch (ParseException e) {
			LogUtils.error(e);
		}
		return day;
	}

	public static Date stringToDate(String date) {
		return stringToDate(date, "yyyy-MM-dd");
	}
	
}
