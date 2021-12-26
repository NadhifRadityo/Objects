package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.api.logging.LogLevel;
import org.gradle.internal.logging.text.StyledTextOutput;
import org.gradle.internal.logging.text.StyledTextOutputFactory;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class LoggerUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$LoggerUtils", "std$LoggerUtils");
	}
	protected static void __UTILS_DESTRUCT__() {
		if(__UTILS_IMPORTED__ != null)
			__UTILS_IMPORTED__ = null;
	}
	protected static <T> T __UTILS_GET_PROPERTY__(String property) {
		return (T) __UTILS_IMPORTED__.getProperty(property);
	}
	protected static <T> void __UTILS_SET_PROPERTY__(String property, T value) {
		__UTILS_IMPORTED__.setProperty(property, value);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_lwarn(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_lwarn").call(args);
	}
	public static void lwarn(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("lwarn").call(args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils__linfo(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils__linfo").call(format, args);
	}
	public static void _linfo(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("_linfo").call(format, args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_llog(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_llog").call(args);
	}
	public static void llog(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("llog").call(args);
	}
	public static Object[] get__INTERNAL_Gradle$Strategies$LoggerUtils___injectLog() {
		return LoggerUtils.<Object[]>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___injectLog");
	}
	public static int __INTERNAL_Gradle$Strategies$LoggerUtils_hashCode() {
		return LoggerUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_hashCode").call();
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils___println_impl(Object... args0) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___println_impl").call(args0);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_construct() {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils__ldebug(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils__ldebug").call(format, args);
	}
	public static void _ldebug(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("_ldebug").call(format, args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerFormat(Object identifier, String pattern, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerFormat").call(identifier, pattern, args);
	}
	public static void loggerFormat(Object identifier, String pattern, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerFormat").call(identifier, pattern, args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerException(Object identifier, Throwable throwable) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerException").call(identifier, throwable);
	}
	public static void loggerException(Object identifier, Throwable throwable) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerException").call(identifier, throwable);
	}
	public static boolean __INTERNAL_Gradle$Strategies$LoggerUtils_equals(Object other) {
		return LoggerUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_equals").call(other);
	}
	public static Object[] get__INTERNAL_Gradle$Strategies$LoggerUtils___injectInfo() {
		return LoggerUtils.<Object[]>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___injectInfo");
	}
	public static Map<Integer, StyledTextOutput> get__INTERNAL_Gradle$Strategies$LoggerUtils_instances() {
		return LoggerUtils.<Map<Integer, StyledTextOutput>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_instances");
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils__lwarn(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils__lwarn").call(format, args);
	}
	public static void _lwarn(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("_lwarn").call(format, args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils__llog(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils__llog").call(format, args);
	}
	public static void _llog(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("_llog").call(format, args);
	}
	public static Object[] get__INTERNAL_Gradle$Strategies$LoggerUtils___injectError() {
		return LoggerUtils.<Object[]>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___injectError");
	}
	public static StyledTextOutputFactory get__INTERNAL_Gradle$Strategies$LoggerUtils_factory() {
		return LoggerUtils.<StyledTextOutputFactory>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_factory");
	}
	public static Object[] __INTERNAL_Gradle$Strategies$LoggerUtils___inject_additional(Object[] inject, Object... args) {
		return LoggerUtils.<Closure<Object[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___inject_additional").call(inject, args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_destruct() {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_destruct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerDestroy(Object identifier) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerDestroy").call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerDestroy() {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerDestroy").call();
	}
	public static void loggerDestroy(Object identifier) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerDestroy").call(identifier);
	}
	public static void loggerDestroy() {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerDestroy").call();
	}
	public static StyledTextOutput __INTERNAL_Gradle$Strategies$LoggerUtils_loggerInstance(Object identifier) {
		return LoggerUtils.<Closure<StyledTextOutput>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerInstance").call(identifier);
	}
	public static StyledTextOutput __INTERNAL_Gradle$Strategies$LoggerUtils_loggerInstance() {
		return LoggerUtils.<Closure<StyledTextOutput>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerInstance").call();
	}
	public static StyledTextOutput loggerInstance(Object identifier) {
		return LoggerUtils.<Closure<StyledTextOutput>>__UTILS_GET_PROPERTY__("loggerInstance").call(identifier);
	}
	public static StyledTextOutput loggerInstance() {
		return LoggerUtils.<Closure<StyledTextOutput>>__UTILS_GET_PROPERTY__("loggerInstance").call();
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_ldebug(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_ldebug").call(args);
	}
	public static void ldebug(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("ldebug").call(args);
	}
	public static String __INTERNAL_Gradle$Strategies$LoggerUtils_toString() {
		return LoggerUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_toString").call();
	}
	public static Map<String, String> get__INTERNAL_Gradle$Strategies$LoggerUtils___escapeCodes() {
		return LoggerUtils.<Map<String, String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___escapeCodes");
	}
	public static Map<String, String> get__escapeCodes() {
		return LoggerUtils.<Map<String, String>>__UTILS_GET_PROPERTY__("__escapeCodes");
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils___on_print(Object identifier, Function1<? super StyledTextOutput, Unit> callback) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___on_print").call(identifier, callback);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_lerror(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_lerror").call(args);
	}
	public static void lerror(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("lerror").call(args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerFormatln(Object identifier, String pattern, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerFormatln").call(identifier, pattern, args);
	}
	public static void loggerFormatln(Object identifier, String pattern, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerFormatln").call(identifier, pattern, args);
	}
	public static Object[] get__INTERNAL_Gradle$Strategies$LoggerUtils___injectDebug() {
		return LoggerUtils.<Object[]>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___injectDebug");
	}
	public static Object[] get__INTERNAL_Gradle$Strategies$LoggerUtils___injectWarn() {
		return LoggerUtils.<Object[]>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___injectWarn");
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerStyle(Object identifier, StyledTextOutput.Style style) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerStyle").call(identifier, style);
	}
	public static void loggerStyle(Object identifier, StyledTextOutput.Style style) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerStyle").call(identifier, style);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_linfo(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_linfo").call(args);
	}
	public static void linfo(Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("linfo").call(args);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.LoggerUtils> get__INTERNAL_Gradle$Strategies$LoggerUtils_cache() {
		return LoggerUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.LoggerUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_cache");
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils__lerror(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils__lerror").call(format, args);
	}
	public static void _lerror(String format, Object... args) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("_lerror").call(format, args);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerText(Object identifier, Object text) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerText").call(identifier, text);
	}
	public static void loggerText(Object identifier, Object text) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerText").call(identifier, text);
	}
	public static Object __INTERNAL_Gradle$Strategies$LoggerUtils___identify_object(Object obj) {
		return LoggerUtils.<Closure<Object>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils___identify_object").call(obj);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerPrintln(Object identifier, Object text) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerPrintln").call(identifier, text);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerPrintln(Object identifier) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerPrintln").call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerPrintln() {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerPrintln").call();
	}
	public static void loggerPrintln(Object identifier, Object text) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerPrintln").call(identifier, text);
	}
	public static void loggerPrintln(Object identifier) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerPrintln").call(identifier);
	}
	public static void loggerPrintln() {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerPrintln").call();
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerAppend(Object identifier, char c) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerAppend").call(identifier, c);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerAppend(Object identifier, CharSequence csq) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerAppend").call(identifier, csq);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerAppend(Object identifier, CharSequence csq, int start, int end) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerAppend").call(identifier, csq, start, end);
	}
	public static void loggerAppend(Object identifier, char c) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerAppend").call(identifier, c);
	}
	public static void loggerAppend(Object identifier, CharSequence csq) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerAppend").call(identifier, csq);
	}
	public static void loggerAppend(Object identifier, CharSequence csq, int start, int end) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerAppend").call(identifier, csq, start, end);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate(Object identifier, Class<?> loggerCategory) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate").call(identifier, loggerCategory);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate(Object identifier, Class<?> loggerCategory, LogLevel logLevel) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate").call(identifier, loggerCategory, logLevel);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate(Object identifier, String loggerCategory) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate").call(identifier, loggerCategory);
	}
	public static void __INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate(Object identifier, String loggerCategory, LogLevel logLevel) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$LoggerUtils_loggerCreate").call(identifier, loggerCategory, logLevel);
	}
	public static void loggerCreate(Object identifier, Class<?> loggerCategory) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerCreate").call(identifier, loggerCategory);
	}
	public static void loggerCreate(Object identifier, Class<?> loggerCategory, LogLevel logLevel) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerCreate").call(identifier, loggerCategory, logLevel);
	}
	public static void loggerCreate(Object identifier, String loggerCategory) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerCreate").call(identifier, loggerCategory);
	}
	public static void loggerCreate(Object identifier, String loggerCategory, LogLevel logLevel) {
		LoggerUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("loggerCreate").call(identifier, loggerCategory, logLevel);
	}
}
