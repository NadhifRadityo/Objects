package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class ProcessUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$ProcessUtils", "std$ProcessUtils");
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
	public static void __INTERNAL_Gradle$Strategies$ProcessUtils_construct() {
		ProcessUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_construct").call();
	}
	public static int __INTERNAL_Gradle$Strategies$ProcessUtils_hashCode() {
		return ProcessUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_hashCode").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProcessUtils_destruct() {
		ProcessUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_destruct").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$ProcessUtils_equals(Object other) {
		return ProcessUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_equals").call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$ProcessUtils_getCommandOutput(File basedir, String... arguments) {
		return ProcessUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_getCommandOutput").call(basedir, arguments);
	}
	public static String __INTERNAL_Gradle$Strategies$ProcessUtils_getCommandOutput(String... arguments) {
		return ProcessUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_getCommandOutput").call(arguments);
	}
	public static String getCommandOutput(File basedir, String... arguments) {
		return ProcessUtils.<Closure<String>>__UTILS_GET_PROPERTY__("getCommandOutput").call(basedir, arguments);
	}
	public static String getCommandOutput(String... arguments) {
		return ProcessUtils.<Closure<String>>__UTILS_GET_PROPERTY__("getCommandOutput").call(arguments);
	}
	public static File __INTERNAL_Gradle$Strategies$ProcessUtils_searchPath(String executable) {
		return ProcessUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_searchPath").call(executable);
	}
	public static File searchPath(String executable) {
		return ProcessUtils.<Closure<File>>__UTILS_GET_PROPERTY__("searchPath").call(executable);
	}
	public static String __INTERNAL_Gradle$Strategies$ProcessUtils_toString() {
		return ProcessUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_toString").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ProcessUtils> get__INTERNAL_Gradle$Strategies$ProcessUtils_cache() {
		return ProcessUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ProcessUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProcessUtils_cache");
	}
}
