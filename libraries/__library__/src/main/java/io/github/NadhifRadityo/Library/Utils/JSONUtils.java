package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class JSONUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$JSONUtils", "std$JSONUtils");
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
	public static <T> String __INTERNAL_Gradle$Strategies$JSONUtils_createJSONFile(T obj, File target) {
		return JSONUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_createJSONFile").call(obj, target);
	}
	public static <T> String createJSONFile(T obj, File target) {
		return JSONUtils.<Closure<String>>__UTILS_GET_PROPERTY__("createJSONFile").call(obj, target);
	}
	public static boolean __INTERNAL_Gradle$Strategies$JSONUtils_equals(Object other) {
		return JSONUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_equals").call(other);
	}
	public static int __INTERNAL_Gradle$Strategies$JSONUtils_hashCode() {
		return JSONUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_hashCode").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JSONUtils> get__INTERNAL_Gradle$Strategies$JSONUtils_cache() {
		return JSONUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.JSONUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_cache");
	}
	public static <T> String __INTERNAL_Gradle$Strategies$JSONUtils_JSONToString(T obj) {
		return JSONUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_JSONToString").call(obj);
	}
	public static <T> String jSONToString(T obj) {
		return JSONUtils.<Closure<String>>__UTILS_GET_PROPERTY__("jSONToString").call(obj);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(File file, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_toJson").call(file, clazz);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(InputStream stream, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_toJson").call(stream, clazz);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(Reader reader, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_toJson").call(reader, clazz);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$JSONUtils_toJson(String string, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_toJson").call(string, clazz);
	}
	public static <T> T toJson(File file, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("toJson").call(file, clazz);
	}
	public static <T> T toJson(InputStream stream, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("toJson").call(stream, clazz);
	}
	public static <T> T toJson(Reader reader, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("toJson").call(reader, clazz);
	}
	public static <T> T toJson(String string, Class<T> clazz) {
		return JSONUtils.<Closure<T>>__UTILS_GET_PROPERTY__("toJson").call(string, clazz);
	}
	public static String __INTERNAL_Gradle$Strategies$JSONUtils_toString() {
		return JSONUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$JSONUtils_construct() {
		JSONUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$JSONUtils_destruct() {
		JSONUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$JSONUtils_destruct").call();
	}
}
