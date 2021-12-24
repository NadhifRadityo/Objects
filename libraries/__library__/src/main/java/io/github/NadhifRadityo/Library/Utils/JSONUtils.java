package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class JSONUtils {
	public static <T> String __INTERNAL_Gradle$Strategies$JSONUtils_createJSONFile(T obj, File target) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_createJSONFile")).call(obj, target);
	}
	public static <T> String createJSONFile(T obj, File target) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("createJSONFile")).call(obj, target);
	}
	public static boolean __INTERNAL_Gradle$Strategies$JSONUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_equals")).call(other);
	}
	public static int __INTERNAL_Gradle$Strategies$JSONUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_hashCode")).call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JSONUtils> get__INTERNAL_Gradle$Strategies$JSONUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JSONUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_cache");
	}
	public static <T> String __INTERNAL_Gradle$Strategies$JSONUtils_JSONToString(T obj) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_JSONToString")).call(obj);
	}
	public static <T> String jSONToString(T obj) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("jSONToString")).call(obj);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(File file, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_toJson")).call(file, clazz);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(InputStream stream, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_toJson")).call(stream, clazz);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(Reader reader, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_toJson")).call(reader, clazz);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(String string, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_toJson")).call(string, clazz);
	}
	public static <T> T toJson(File file, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("toJson")).call(file, clazz);
	}
	public static <T> T toJson(InputStream stream, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("toJson")).call(stream, clazz);
	}
	public static <T> T toJson(Reader reader, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("toJson")).call(reader, clazz);
	}
	public static <T> T toJson(String string, Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("toJson")).call(string, clazz);
	}
	public static String __INTERNAL_Gradle$Strategies$JSONUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_toString")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$JSONUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_construct")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$JSONUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$JSONUtils_destruct")).call();
	}
}
