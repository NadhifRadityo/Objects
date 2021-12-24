package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import kotlin.Lazy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class JavascriptUtils {
	public static Lazy<Function2<String, Object[], Object>> get__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptNashorn() {
		return (Lazy<Function2<String, Object[], Object>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptNashorn");
	}
	public static Lazy<Function2<String, Object[], Object>> getJavascriptNashorn() {
		return (Lazy<Function2<String, Object[], Object>>) ((GroovyObject) getContext().getThat()).getProperty("javascriptNashorn");
	}
	public static void __INTERNAL_Gradle$Strategies$JavascriptUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_destruct")).call();
	}
	public static Function1<Object[], Object> __INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallbackF(String source) {
		return ((Closure<Function1<Object[], Object>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallbackF")).call(source);
	}
	public static Function1<Object[], Object> runJavascriptAsCallbackF(String source) {
		return ((Closure<Function1<Object[], Object>>) ((GroovyObject) getContext().getThat()).getProperty("runJavascriptAsCallbackF")).call(source);
	}
	public static boolean __INTERNAL_Gradle$Strategies$JavascriptUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_equals")).call(other);
	}
	public static Object __INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascript(String source, Object... args) {
		return ((Closure<Object>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascript")).call(source, args);
	}
	public static Object runJavascript(String source, Object... args) {
		return ((Closure<Object>) ((GroovyObject) getContext().getThat()).getProperty("runJavascript")).call(source, args);
	}
	public static void __INTERNAL_Gradle$Strategies$JavascriptUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_construct")).call();
	}
	public static int __INTERNAL_Gradle$Strategies$JavascriptUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_hashCode")).call();
	}
	public static String __INTERNAL_Gradle$Strategies$JavascriptUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_toString")).call();
	}
	public static Lazy<Function2<String, Object[], Object>> get__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptGraalVm() {
		return (Lazy<Function2<String, Object[], Object>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_javascriptGraalVm");
	}
	public static Lazy<Function2<String, Object[], Object>> getJavascriptGraalVm() {
		return (Lazy<Function2<String, Object[], Object>>) ((GroovyObject) getContext().getThat()).getProperty("javascriptGraalVm");
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JavascriptUtils> get__INTERNAL_Gradle$Strategies$JavascriptUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JavascriptUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_cache");
	}
	public static <T> Closure<T> __INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallback(String source) {
		return ((Closure<Closure<T>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JavascriptUtils_runJavascriptAsCallback")).call(source);
	}
	public static <T> Closure<T> runJavascriptAsCallback(String source) {
		return ((Closure<Closure<T>>) ((GroovyObject) getContext().getThat()).getProperty("runJavascriptAsCallback")).call(source);
	}
}
