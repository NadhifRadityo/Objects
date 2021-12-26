package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import kotlin.Lazy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class JavascriptUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$JavascriptUtils", "std$JavascriptUtils");
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
	public static Lazy<Function2<String, Object[], Object>> get__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptNashorn() {
		return JavascriptUtils.<Lazy<Function2<String, Object[], Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptNashorn");
	}
	public static Lazy<Function2<String, Object[], Object>> getJavascriptNashorn() {
		return JavascriptUtils.<Lazy<Function2<String, Object[], Object>>>__UTILS_GET_PROPERTY__("javascriptNashorn");
	}
	public static void __INTERNAL_Gradle$Strategies$JavascriptUtils_destruct() {
		JavascriptUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_destruct").call();
	}
	public static Function1<Object[], Object> __INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallbackF(String source) {
		return JavascriptUtils.<Closure<Function1<Object[], Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallbackF").call(source);
	}
	public static Function1<Object[], Object> runJavascriptAsCallbackF(String source) {
		return JavascriptUtils.<Closure<Function1<Object[], Object>>>__UTILS_GET_PROPERTY__("runJavascriptAsCallbackF").call(source);
	}
	public static boolean __INTERNAL_Gradle$Strategies$JavascriptUtils_equals(Object other) {
		return JavascriptUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_equals").call(other);
	}
	public static Object __INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascript(String source, Object... args) {
		return JavascriptUtils.<Closure<Object>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascript").call(source, args);
	}
	public static Object runJavascript(String source, Object... args) {
		return JavascriptUtils.<Closure<Object>>__UTILS_GET_PROPERTY__("runJavascript").call(source, args);
	}
	public static void __INTERNAL_Gradle$Strategies$JavascriptUtils_construct() {
		JavascriptUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_construct").call();
	}
	public static int __INTERNAL_Gradle$Strategies$JavascriptUtils_hashCode() {
		return JavascriptUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_hashCode").call();
	}
	public static String __INTERNAL_Gradle$Strategies$JavascriptUtils_toString() {
		return JavascriptUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_toString").call();
	}
	public static Lazy<Function2<String, Object[], Object>> get__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptGraalVm() {
		return JavascriptUtils.<Lazy<Function2<String, Object[], Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptGraalVm");
	}
	public static Lazy<Function2<String, Object[], Object>> getJavascriptGraalVm() {
		return JavascriptUtils.<Lazy<Function2<String, Object[], Object>>>__UTILS_GET_PROPERTY__("javascriptGraalVm");
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JavascriptUtils> get__INTERNAL_Gradle$Strategies$JavascriptUtils_cache() {
		return JavascriptUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JavascriptUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_cache");
	}
	public static <T> Closure<T> __INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallback(String source) {
		return JavascriptUtils.<Closure<Closure<T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallback").call(source);
	}
	public static <T> Closure<T> runJavascriptAsCallback(String source) {
		return JavascriptUtils.<Closure<Closure<T>>>__UTILS_GET_PROPERTY__("runJavascriptAsCallback").call(source);
	}
}
