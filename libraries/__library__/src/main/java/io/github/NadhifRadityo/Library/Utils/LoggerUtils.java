package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;

import static io.github.NadhifRadityo.Library.LibraryEntry.getProject;
import static io.github.NadhifRadityo.Library.Utils.ExceptionUtils.throwableToString;

public class LoggerUtils {
	public static boolean disableDebugPrint = false;

	protected static void parseFormat(Object... format) {
		for(int i = 0; i < format.length; i++) {
			Object object = format[i];
			if(object instanceof Throwable)
				format[i] = throwableToString((Throwable) object);
		}
	}
	public static void println_impl(String text) {
		Closure<Void> println_impl = (Closure<Void>) getProject().findProperty("ext_common$logger_println_impl");
		if(println_impl == null) { System.out.println(text); return; }
		println_impl.call(text);
	}
	public static void log(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[LOG] " + line); }
	public static void info(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[INFO] " + line); }
	public static void debug(String text, Object... format) { if(disableDebugPrint) return; parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[DEBUG] " + line); }
	public static void warn(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[WARN] " + line); }
	public static void error(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[ERROR] " + line); }
}
