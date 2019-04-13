package io.github.NadhifRadityo.Objects.Utilizations;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class TimeUtils {
	private TimeUtils() {
		
	}
	
	public static String getTime(String format) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timestamp);
	}
	
	public static String getTime() {
		return getTime("HH:mm:ss");
	}
	
	public static long getMiliseconds() {
		return System.currentTimeMillis() % 1000;
	}
}
