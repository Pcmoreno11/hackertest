package com.hotelbeds.supplierintegrations.hackertest.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
	
	private DateUtils() {
		// Not called
	}

	private static final String PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	/**
	 * 
	 * @param dateTime1
	 * @param dateTime2
	 * @return the number of minutes (rounded down) between two timestamps  time1 and  time2
	 */
	public static Long minutesBetweenDates(LocalDateTime dateTime1, LocalDateTime dateTime2) {
		
		return Math.abs(Duration.between(dateTime1, dateTime2).toMinutes());
	}
	
	/**
	 * 
	 * @param time in RFC 2822 format 
	 * @return the LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Long value) {
		return Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/**
	 * 
	 * @param dateTime1 in RFC 2822 format 
	 * @param dateTime2 in RFC 2822 format 
	 * @return the number of minutes (rounded down) between two timestamps time1 and  time2
	 */
	public static Long minutesBetweenDates(String dateTime1, String dateTime2) {
		
		return minutesBetweenDates(toLocalDateTime(dateTime1), toLocalDateTime(dateTime2));
	}
	
	
	/**
	 * 
	 * @param time in RFC 2822 format 
	 * @return the LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(String time) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN, Locale.UK)
				.withZone(ZoneId.systemDefault());
		return LocalDateTime.parse(time, formatter);
	}
}
