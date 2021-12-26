package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class Common {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$Common", "std$Common");
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
	public static void __INTERNAL_Gradle$Common_construct() {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_construct").call();
	}
	public static Gradle.Context __INTERNAL_Gradle$Common_lastContext() {
		return Common.<Closure<Gradle.Context>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_lastContext").call();
	}
	public static Gradle.Context lastContext() {
		return Common.<Closure<Gradle.Context>>__UTILS_GET_PROPERTY__("lastContext").call();
	}
	public static Gradle.Session get__INTERNAL_Gradle$Common_currentSession() {
		return Common.<Gradle.Session>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_currentSession");
	}
	public static ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>> get__INTERNAL_Gradle$Common_groovyKotlinCaches() {
		return Common.<ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_groovyKotlinCaches");
	}
	public static void __INTERNAL_Gradle$Common_addOnConfigFinished(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_addOnConfigFinished").call(priority, callback);
	}
	public static void addOnConfigFinished(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("addOnConfigFinished").call(priority, callback);
	}
	public static void __INTERNAL_Gradle$Common_removeOnConfigStarted(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_removeOnConfigStarted").call(priority, callback);
	}
	public static void removeOnConfigStarted(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("removeOnConfigStarted").call(priority, callback);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Common> get__INTERNAL_Gradle$Common_cache() {
		return Common.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Common>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_cache");
	}
	public static ThreadLocal<LinkedList<Gradle.Context>> get__INTERNAL_Gradle$Common_contextStack() {
		return Common.<ThreadLocal<LinkedList<Gradle.Context>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_contextStack");
	}
	public static HashMap<Integer, List<Function0<Unit>>> get__INTERNAL_Gradle$Common_onConfigStarted() {
		return Common.<HashMap<Integer, List<Function0<Unit>>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_onConfigStarted");
	}
	public static String __INTERNAL_Gradle$Common_toString() {
		return Common.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_toString").call();
	}
	public static void __INTERNAL_Gradle$Common_removeOnConfigFinished(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_removeOnConfigFinished").call(priority, callback);
	}
	public static void removeOnConfigFinished(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("removeOnConfigFinished").call(priority, callback);
	}
	public static <T> T __INTERNAL_Gradle$Common_context(Object that, Function0<? extends T> callback) {
		return Common.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_context").call(that, callback);
	}
	public static <T> T __INTERNAL_Gradle$Common_context(Object that, Closure<T> callback) {
		return Common.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_context").call(that, callback);
	}
	public static <T> T context(Object that, Function0<? extends T> callback) {
		return Common.<Closure<T>>__UTILS_GET_PROPERTY__("context").call(that, callback);
	}
	public static <T> T context(Object that, Closure<T> callback) {
		return Common.<Closure<T>>__UTILS_GET_PROPERTY__("context").call(that, callback);
	}
	public static HashMap<Integer, List<Function0<Unit>>> get__INTERNAL_Gradle$Common_onConfigFinished() {
		return Common.<HashMap<Integer, List<Function0<Unit>>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_onConfigFinished");
	}
	public static void __INTERNAL_Gradle$Common_addOnConfigStarted(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_addOnConfigStarted").call(priority, callback);
	}
	public static void addOnConfigStarted(int priority, Function0<Unit> callback) {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("addOnConfigStarted").call(priority, callback);
	}
	public static void __INTERNAL_Gradle$Common_destruct() {
		Common.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_destruct").call();
	}
	public static int __INTERNAL_Gradle$Common_hashCode() {
		return Common.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_hashCode").call();
	}
	public static boolean __INTERNAL_Gradle$Common_equals(Object other) {
		return Common.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Common_equals").call(other);
	}
}
