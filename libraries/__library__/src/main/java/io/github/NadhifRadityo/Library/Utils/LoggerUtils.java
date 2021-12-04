package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import org.gradle.api.logging.LogLevel;
import org.gradle.internal.logging.text.StyledTextOutput;

import static io.github.NadhifRadityo.Library.LibraryEntry.getProject;
import static io.github.NadhifRadityo.Library.Utils.ExceptionUtils.throwableToString;

public class LoggerUtils {
	public static void logger_create(Object... args) {
		Closure<Void> logger_create = (Closure<Void>) getProject().findProperty("__INTERNAL_Logger_loggerCreate");
		assert logger_create != null;
		logger_create.call(args);
	}
	public static void loggerCreate(Object identifier, String loggerCategory) {
		logger_create(identifier, loggerCategory != null ? loggerCategory : "");
	}
	public static void loggerCreate(Object identifier, Class<?> loggerCategory) {
		logger_create(identifier, loggerCategory != null ? loggerCategory : LoggerUtils.class);
	}
	public static void loggerCreate(Object identifier, String loggerCategory, LogLevel logLevel) {
		logger_create(identifier, loggerCategory, logLevel);
	}
	public static void loggerCreate(Object identifier, Class<?> loggerCategory, LogLevel logLevel) {
		logger_create(identifier, loggerCategory, logLevel);
	}
	public static void loggerCreate(String loggerCategory) {
		logger_create(null, loggerCategory);
	}
	public static void loggerCreate(Class<?> loggerCategory) {
		logger_create(null, loggerCategory);
	}
	public static void loggerCreate(String loggerCategory, LogLevel logLevel) {
		logger_create(null, loggerCategory, logLevel);
	}
	public static void loggerCreate(Class<?> loggerCategory, LogLevel logLevel) {
		logger_create(null, loggerCategory, logLevel);
	}

	public static void logger_destroy(Object... args) {
		Closure<Void> logger_destroy = (Closure<Void>) getProject().findProperty("__INTERNAL_Logger_loggerDestroy");
		assert logger_destroy != null;
		logger_destroy.call(args);
	}
	public static void loggerDestroy(Object identifier) {
		logger_destroy(identifier);
	}
	public static void loggerDestroy() {
		logger_destroy((Object) null);
	}

	public static StyledTextOutput logger_instance(Object... args) {
		Closure<StyledTextOutput> logger_instance = (Closure<StyledTextOutput>) getProject().findProperty("__INTERNAL_Logger_loggerInstance");
		assert logger_instance != null;
		return logger_instance.call(args);
	}
	public static StyledTextOutput loggerInstance(Object identifier) {
		return logger_instance(identifier);
	}
	public static StyledTextOutput loggerInstance() {
		return logger_instance((Object) null);
	}

	public static boolean disableDebugPrint = false;

	protected static void parseFormat(Object... format) {
		for(int i = 0; i < format.length; i++) {
			Object object = format[i];
			if(object instanceof Throwable)
				format[i] = throwableToString((Throwable) object);
		}
	}
	public static void println_impl(String text) {
		Closure<Void> println_impl = (Closure<Void>) getProject().findProperty("__INTERNAL_Logger___println_impl");
		if(println_impl == null) { System.out.println(text); return; }
		println_impl.call(text);
	}
	public static void log(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[LOG] " + line); }
	public static void info(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[INFO] " + line); }
	public static void debug(String text, Object... format) { if(disableDebugPrint) return; parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[DEBUG] " + line); }
	public static void warn(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[WARN] " + line); }
	public static void error(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) println_impl("[ERROR] " + line); }
}
