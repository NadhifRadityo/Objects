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
	public static void __INTERNAL_Gradle$Common_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_construct")).call();
	}
	public static Gradle.Context __INTERNAL_Gradle$Common_lastContext() {
		return ((Closure<Gradle.Context>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_lastContext")).call();
	}
	public static Gradle.Context lastContext() {
		return ((Closure<Gradle.Context>) ((GroovyObject) getContext().getThat()).getProperty("lastContext")).call();
	}
	public static Gradle.Session get__INTERNAL_Gradle$Common_currentSession() {
		return (Gradle.Session) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_currentSession");
	}
	public static ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>> get__INTERNAL_Gradle$Common_groovyKotlinCaches() {
		return (ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_groovyKotlinCaches");
	}
	public static void __INTERNAL_Gradle$Common_addOnConfigFinished(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_addOnConfigFinished")).call(priority, callback);
	}
	public static void addOnConfigFinished(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("addOnConfigFinished")).call(priority, callback);
	}
	public static void __INTERNAL_Gradle$Common_removeOnConfigStarted(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_removeOnConfigStarted")).call(priority, callback);
	}
	public static void removeOnConfigStarted(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("removeOnConfigStarted")).call(priority, callback);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Common> get__INTERNAL_Gradle$Common_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Common>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_cache");
	}
	public static ThreadLocal<LinkedList<Gradle.Context>> get__INTERNAL_Gradle$Common_contextStack() {
		return (ThreadLocal<LinkedList<Gradle.Context>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_contextStack");
	}
	public static HashMap<Integer, List<Function0<Unit>>> get__INTERNAL_Gradle$Common_onConfigStarted() {
		return (HashMap<Integer, List<Function0<Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_onConfigStarted");
	}
	public static String __INTERNAL_Gradle$Common_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_toString")).call();
	}
	public static void __INTERNAL_Gradle$Common_removeOnConfigFinished(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_removeOnConfigFinished")).call(priority, callback);
	}
	public static void removeOnConfigFinished(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("removeOnConfigFinished")).call(priority, callback);
	}
	public static <T> T __INTERNAL_Gradle$Common_context(Object that, Function0<? extends T> callback) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_context")).call(that, callback);
	}
	public static <T> T __INTERNAL_Gradle$Common_context(Object that, Closure<T> callback) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_context")).call(that, callback);
	}
	public static <T> T context(Object that, Function0<? extends T> callback) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("context")).call(that, callback);
	}
	public static <T> T context(Object that, Closure<T> callback) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("context")).call(that, callback);
	}
	public static HashMap<Integer, List<Function0<Unit>>> get__INTERNAL_Gradle$Common_onConfigFinished() {
		return (HashMap<Integer, List<Function0<Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_onConfigFinished");
	}
	public static void __INTERNAL_Gradle$Common_addOnConfigStarted(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_addOnConfigStarted")).call(priority, callback);
	}
	public static void addOnConfigStarted(int priority, Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("addOnConfigStarted")).call(priority, callback);
	}
	public static void __INTERNAL_Gradle$Common_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_destruct")).call();
	}
	public static int __INTERNAL_Gradle$Common_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_hashCode")).call();
	}
	public static boolean __INTERNAL_Gradle$Common_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Common_equals")).call(other);
	}
}
