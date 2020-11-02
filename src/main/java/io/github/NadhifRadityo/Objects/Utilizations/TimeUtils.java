package io.github.NadhifRadityo.Objects.Utilizations;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
	private TimeUtils() {
		
	}
	
	public static String getTime(String format, long millis) {
		Timestamp timestamp = new Timestamp(millis);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timestamp);
	}
	public static String getTime(String format) { return getTime(format, System.currentTimeMillis()); }
	public static String getTime() { return getTime("HH:mm:ss"); }
	
	public static long getMilliseconds(long millis) { return millis % 1000; }
	public static long getMilliseconds() { return getMilliseconds(System.currentTimeMillis()); }
}
