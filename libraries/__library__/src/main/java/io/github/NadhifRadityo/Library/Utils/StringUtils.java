package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class StringUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$StringUtils", "std$StringUtils");
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
	public static boolean __INTERNAL_Gradle$Strategies$StringUtils_equals(Object other) {
		return StringUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_equals").call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_mostSafeString(String string) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_mostSafeString").call(string);
	}
	public static String mostSafeString(String string) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("mostSafeString").call(string);
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_randomString(int length, char... charset) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_randomString").call(length, charset);
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_randomString(int length) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_randomString").call(length);
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_randomString() {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_randomString").call();
	}
	public static String randomString(int length, char... charset) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("randomString").call(length, charset);
	}
	public static String randomString(int length) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("randomString").call(length);
	}
	public static String randomString() {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("randomString").call();
	}
	public static String get__INTERNAL_Gradle$Strategies$StringUtils_ASCII_CHARACTERS() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_ASCII_CHARACTERS");
	}
	public static String getASCII_CHARACTERS() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("ASCII_CHARACTERS");
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_escape(String string) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_escape").call(string);
	}
	public static String escape(String string) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("escape").call(string);
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_toString() {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_toString").call();
	}
	public static String get__INTERNAL_Gradle$Strategies$StringUtils_DIGITS() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_DIGITS");
	}
	public static String getDIGITS() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("DIGITS");
	}
	public static String __INTERNAL_Gradle$Strategies$StringUtils_unescapeJavaString(String string) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_unescapeJavaString").call(string);
	}
	public static String unescapeJavaString(String string) {
		return StringUtils.<Closure<String>>__UTILS_GET_PROPERTY__("unescapeJavaString").call(string);
	}
	public static int __INTERNAL_Gradle$Strategies$StringUtils_hashCode() {
		return StringUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_hashCode").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.StringUtils> get__INTERNAL_Gradle$Strategies$StringUtils_cache() {
		return StringUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.StringUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_cache");
	}
	public static void __INTERNAL_Gradle$Strategies$StringUtils_destruct() {
		StringUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_destruct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$StringUtils_construct() {
		StringUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_construct").call();
	}
	public static String get__INTERNAL_Gradle$Strategies$StringUtils_ALPHANUMERIC() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_ALPHANUMERIC");
	}
	public static String getALPHANUMERIC() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("ALPHANUMERIC");
	}
	public static String get__INTERNAL_Gradle$Strategies$StringUtils_ALPHABETS_LOWER() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_ALPHABETS_LOWER");
	}
	public static String getALPHABETS_LOWER() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("ALPHABETS_LOWER");
	}
	public static String get__INTERNAL_Gradle$Strategies$StringUtils_ASCII_SYMBOLS() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_ASCII_SYMBOLS");
	}
	public static String getASCII_SYMBOLS() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("ASCII_SYMBOLS");
	}
	public static String get__INTERNAL_Gradle$Strategies$StringUtils_ALPHABETS_UPPER() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StringUtils_ALPHABETS_UPPER");
	}
	public static String getALPHABETS_UPPER() {
		return StringUtils.<String>__UTILS_GET_PROPERTY__("ALPHABETS_UPPER");
	}
}
