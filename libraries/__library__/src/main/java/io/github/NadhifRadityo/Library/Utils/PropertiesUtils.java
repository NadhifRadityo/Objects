package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.Hashtable;
import java.util.Properties;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class PropertiesUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$PropertiesUtils", "std$PropertiesUtils");
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
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_construct() {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setChar(Properties properties, String key, char value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setChar").call(properties, key, value);
	}
	public static void p_setChar(Properties properties, String key, char value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setChar").call(properties, key, value);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_destruct() {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_destruct").call();
	}
	public static byte __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getByte(Properties properties, String key, byte defaultValue) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getByte").call(properties, key, defaultValue);
	}
	public static byte __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getByte(Properties properties, String key) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getByte").call(properties, key);
	}
	public static byte p_getByte(Properties properties, String key, byte defaultValue) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("p_getByte").call(properties, key, defaultValue);
	}
	public static byte p_getByte(Properties properties, String key) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("p_getByte").call(properties, key);
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_sizeNonDefaultProperties(Properties properties) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_sizeNonDefaultProperties").call(properties);
	}
	public static int sizeNonDefaultProperties(Properties properties) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("sizeNonDefaultProperties").call(properties);
	}
	public static boolean __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getBoolean(Properties properties, String key, boolean defaultValue) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getBoolean").call(properties, key, defaultValue);
	}
	public static boolean __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getBoolean(Properties properties, String key) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getBoolean").call(properties, key);
	}
	public static boolean p_getBoolean(Properties properties, String key, boolean defaultValue) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("p_getBoolean").call(properties, key, defaultValue);
	}
	public static boolean p_getBoolean(Properties properties, String key) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("p_getBoolean").call(properties, key);
	}
	public static boolean __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getBoolean(Properties properties, String key, boolean defaultValue) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getBoolean").call(properties, key, defaultValue);
	}
	public static boolean __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getBoolean(Properties properties, String key) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getBoolean").call(properties, key);
	}
	public static boolean pn_getBoolean(Properties properties, String key, boolean defaultValue) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("pn_getBoolean").call(properties, key, defaultValue);
	}
	public static boolean pn_getBoolean(Properties properties, String key) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("pn_getBoolean").call(properties, key);
	}
	public static byte __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getByte(Properties properties, String key, byte defaultValue) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getByte").call(properties, key, defaultValue);
	}
	public static byte __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getByte(Properties properties, String key) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getByte").call(properties, key);
	}
	public static byte pn_getByte(Properties properties, String key, byte defaultValue) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("pn_getByte").call(properties, key, defaultValue);
	}
	public static byte pn_getByte(Properties properties, String key) {
		return PropertiesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("pn_getByte").call(properties, key);
	}
	public static long __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getLong(Properties properties, String key, long defaultValue) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getLong").call(properties, key, defaultValue);
	}
	public static long __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getLong(Properties properties, String key) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getLong").call(properties, key);
	}
	public static long pn_getLong(Properties properties, String key, long defaultValue) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("pn_getLong").call(properties, key, defaultValue);
	}
	public static long pn_getLong(Properties properties, String key) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("pn_getLong").call(properties, key);
	}
	public static Properties __INTERNAL_Gradle$Strategies$PropertiesUtils___get_defaults_properties(Properties properties) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils___get_defaults_properties").call(properties);
	}
	public static Properties __get_defaults_properties(Properties properties) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("__get_defaults_properties").call(properties);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_enumerateAllProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_enumerateAllProperties").call(properties, hashtable);
	}
	public static void enumerateAllProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("enumerateAllProperties").call(properties, hashtable);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_enumerateNonDefaultProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_enumerateNonDefaultProperties").call(properties, hashtable);
	}
	public static void enumerateNonDefaultProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("enumerateNonDefaultProperties").call(properties, hashtable);
	}
	public static double __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getDouble(Properties properties, String key, double defaultValue) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getDouble").call(properties, key, defaultValue);
	}
	public static double __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getDouble(Properties properties, String key) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getDouble").call(properties, key);
	}
	public static double pn_getDouble(Properties properties, String key, double defaultValue) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("pn_getDouble").call(properties, key, defaultValue);
	}
	public static double pn_getDouble(Properties properties, String key) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("pn_getDouble").call(properties, key);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setByte(Properties properties, String key, byte value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setByte").call(properties, key, value);
	}
	public static void p_setByte(Properties properties, String key, byte value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setByte").call(properties, key, value);
	}
	public static float __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getFloat(Properties properties, String key, float defaultValue) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getFloat").call(properties, key, defaultValue);
	}
	public static float __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getFloat(Properties properties, String key) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getFloat").call(properties, key);
	}
	public static float p_getFloat(Properties properties, String key, float defaultValue) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("p_getFloat").call(properties, key, defaultValue);
	}
	public static float p_getFloat(Properties properties, String key) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("p_getFloat").call(properties, key);
	}
	public static Properties __INTERNAL_Gradle$Strategies$PropertiesUtils_extendProperties(Properties original, Properties extend, boolean nullable) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_extendProperties").call(original, extend, nullable);
	}
	public static Properties extendProperties(Properties original, Properties extend, boolean nullable) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("extendProperties").call(original, extend, nullable);
	}
	public static float __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getFloat(Properties properties, String key, float defaultValue) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getFloat").call(properties, key, defaultValue);
	}
	public static float __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getFloat(Properties properties, String key) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getFloat").call(properties, key);
	}
	public static float pn_getFloat(Properties properties, String key, float defaultValue) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("pn_getFloat").call(properties, key, defaultValue);
	}
	public static float pn_getFloat(Properties properties, String key) {
		return PropertiesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("pn_getFloat").call(properties, key);
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_sizeAllProperties(Properties properties) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_sizeAllProperties").call(properties);
	}
	public static int sizeAllProperties(Properties properties) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("sizeAllProperties").call(properties);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject(Properties properties, String key, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject").call(properties, key, defaultValue);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject(Properties properties, String key) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject").call(properties, key);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject(Properties properties, String key, Class<T> type) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject").call(properties, key, type);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getObject").call(properties, key, type, defaultValue);
	}
	public static <T> T p_getObject(Properties properties, String key, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("p_getObject").call(properties, key, defaultValue);
	}
	public static <T> T p_getObject(Properties properties, String key) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("p_getObject").call(properties, key);
	}
	public static <T> T p_getObject(Properties properties, String key, Class<T> type) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("p_getObject").call(properties, key, type);
	}
	public static <T> T p_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("p_getObject").call(properties, key, type, defaultValue);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setFloat(Properties properties, String key, float value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setFloat").call(properties, key, value);
	}
	public static void p_setFloat(Properties properties, String key, float value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setFloat").call(properties, key, value);
	}
	public static long get__INTERNAL_Gradle$Strategies$PropertiesUtils_AFIELD_Properties_defaults() {
		return PropertiesUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_AFIELD_Properties_defaults");
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_hashCode() {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_hashCode").call();
	}
	public static short __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getShort(Properties properties, String key, short defaultValue) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getShort").call(properties, key, defaultValue);
	}
	public static short __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getShort(Properties properties, String key) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getShort").call(properties, key);
	}
	public static short pn_getShort(Properties properties, String key, short defaultValue) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("pn_getShort").call(properties, key, defaultValue);
	}
	public static short pn_getShort(Properties properties, String key) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("pn_getShort").call(properties, key);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setLong(Properties properties, String key, long value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setLong").call(properties, key, value);
	}
	public static void p_setLong(Properties properties, String key, long value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setLong").call(properties, key, value);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setDouble(Properties properties, String key, double value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setDouble").call(properties, key, value);
	}
	public static void p_setDouble(Properties properties, String key, double value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setDouble").call(properties, key, value);
	}
	public static Properties __INTERNAL_Gradle$Strategies$PropertiesUtils_copyAllProperties(Properties properties, boolean nullable) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_copyAllProperties").call(properties, nullable);
	}
	public static Properties copyAllProperties(Properties properties, boolean nullable) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("copyAllProperties").call(properties, nullable);
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getInt(Properties properties, String key, int defaultValue) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getInt").call(properties, key, defaultValue);
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getInt(Properties properties, String key) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getInt").call(properties, key);
	}
	public static int p_getInt(Properties properties, String key, int defaultValue) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("p_getInt").call(properties, key, defaultValue);
	}
	public static int p_getInt(Properties properties, String key) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("p_getInt").call(properties, key);
	}
	public static char __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getChar(Properties properties, String key, char defaultValue) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getChar").call(properties, key, defaultValue);
	}
	public static char __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getChar(Properties properties, String key) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getChar").call(properties, key);
	}
	public static char p_getChar(Properties properties, String key, char defaultValue) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("p_getChar").call(properties, key, defaultValue);
	}
	public static char p_getChar(Properties properties, String key) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("p_getChar").call(properties, key);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setBoolean(Properties properties, String key, boolean value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setBoolean").call(properties, key, value);
	}
	public static void p_setBoolean(Properties properties, String key, boolean value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setBoolean").call(properties, key, value);
	}
	public static short __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getShort(Properties properties, String key, short defaultValue) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getShort").call(properties, key, defaultValue);
	}
	public static short __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getShort(Properties properties, String key) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getShort").call(properties, key);
	}
	public static short p_getShort(Properties properties, String key, short defaultValue) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("p_getShort").call(properties, key, defaultValue);
	}
	public static short p_getShort(Properties properties, String key) {
		return PropertiesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("p_getShort").call(properties, key);
	}
	public static <T> void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setObject(Properties properties, String key, T value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setObject").call(properties, key, value);
	}
	public static <T> void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setObject(Properties properties, String key, Class<T> type, T value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setObject").call(properties, key, type, value);
	}
	public static <T> void p_setObject(Properties properties, String key, T value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setObject").call(properties, key, value);
	}
	public static <T> void p_setObject(Properties properties, String key, Class<T> type, T value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setObject").call(properties, key, type, value);
	}
	public static boolean __INTERNAL_Gradle$Strategies$PropertiesUtils_equals(Object other) {
		return PropertiesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_equals").call(other);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject(Properties properties, String key, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject").call(properties, key, defaultValue);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject(Properties properties, String key) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject").call(properties, key);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject(Properties properties, String key, Class<T> type) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject").call(properties, key, type);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getObject").call(properties, key, type, defaultValue);
	}
	public static <T> T pn_getObject(Properties properties, String key, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("pn_getObject").call(properties, key, defaultValue);
	}
	public static <T> T pn_getObject(Properties properties, String key) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("pn_getObject").call(properties, key);
	}
	public static <T> T pn_getObject(Properties properties, String key, Class<T> type) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("pn_getObject").call(properties, key, type);
	}
	public static <T> T pn_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		return PropertiesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("pn_getObject").call(properties, key, type, defaultValue);
	}
	public static double __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getDouble(Properties properties, String key, double defaultValue) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getDouble").call(properties, key, defaultValue);
	}
	public static double __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getDouble(Properties properties, String key) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getDouble").call(properties, key);
	}
	public static double p_getDouble(Properties properties, String key, double defaultValue) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("p_getDouble").call(properties, key, defaultValue);
	}
	public static double p_getDouble(Properties properties, String key) {
		return PropertiesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("p_getDouble").call(properties, key);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.PropertiesUtils> get__INTERNAL_Gradle$Strategies$PropertiesUtils_cache() {
		return PropertiesUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.PropertiesUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_cache");
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setShort(Properties properties, String key, short value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setShort").call(properties, key, value);
	}
	public static void p_setShort(Properties properties, String key, short value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setShort").call(properties, key, value);
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getInt(Properties properties, String key, int defaultValue) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getInt").call(properties, key, defaultValue);
	}
	public static int __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getInt(Properties properties, String key) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getInt").call(properties, key);
	}
	public static int pn_getInt(Properties properties, String key, int defaultValue) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("pn_getInt").call(properties, key, defaultValue);
	}
	public static int pn_getInt(Properties properties, String key) {
		return PropertiesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("pn_getInt").call(properties, key);
	}
	public static long __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getLong(Properties properties, String key, long defaultValue) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getLong").call(properties, key, defaultValue);
	}
	public static long __INTERNAL_Gradle$Strategies$PropertiesUtils_p_getLong(Properties properties, String key) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_getLong").call(properties, key);
	}
	public static long p_getLong(Properties properties, String key, long defaultValue) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("p_getLong").call(properties, key, defaultValue);
	}
	public static long p_getLong(Properties properties, String key) {
		return PropertiesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("p_getLong").call(properties, key);
	}
	public static void __INTERNAL_Gradle$Strategies$PropertiesUtils_p_setInt(Properties properties, String key, int value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_p_setInt").call(properties, key, value);
	}
	public static void p_setInt(Properties properties, String key, int value) {
		PropertiesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("p_setInt").call(properties, key, value);
	}
	public static Properties __INTERNAL_Gradle$Strategies$PropertiesUtils_copyNonDefaultProperties(Properties properties, boolean nullable) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_copyNonDefaultProperties").call(properties, nullable);
	}
	public static Properties copyNonDefaultProperties(Properties properties, boolean nullable) {
		return PropertiesUtils.<Closure<Properties>>__UTILS_GET_PROPERTY__("copyNonDefaultProperties").call(properties, nullable);
	}
	public static String __INTERNAL_Gradle$Strategies$PropertiesUtils_toString() {
		return PropertiesUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_toString").call();
	}
	public static char __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getChar(Properties properties, String key, char defaultValue) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getChar").call(properties, key, defaultValue);
	}
	public static char __INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getChar(Properties properties, String key) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PropertiesUtils_pn_getChar").call(properties, key);
	}
	public static char pn_getChar(Properties properties, String key, char defaultValue) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("pn_getChar").call(properties, key, defaultValue);
	}
	public static char pn_getChar(Properties properties, String key) {
		return PropertiesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("pn_getChar").call(properties, key);
	}
}
