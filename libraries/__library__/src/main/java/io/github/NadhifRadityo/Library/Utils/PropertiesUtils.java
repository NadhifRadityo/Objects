package io.github.NadhifRadityo.Library.Utils;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.unsafe;

public class PropertiesUtils {
	protected static final long AFIELD_Properties_defaults;

	static { try {
		Field FIELD_Properties_defaults = Properties.class.getDeclaredField("defaults");
		AFIELD_Properties_defaults = unsafe.objectFieldOffset(FIELD_Properties_defaults);
	} catch(Exception e) { throw new Error(e); } }

	public static Properties __get_defaults_properties(Properties properties) {
		return (Properties) unsafe.getObject(properties, AFIELD_Properties_defaults);
	}
	public static Properties extendProperties(Properties original, Properties extend, boolean nullable) {
		if(nullable && sizeNonDefaultProperties(original) == 0 && sizeAllProperties(extend) == 0)
			return null;
		Properties properties = new Properties(extend);
		if(original != null)
			properties.putAll(original);
		return properties;
	}
	public static Properties copyAllProperties(Properties properties, boolean nullable) {
		if(nullable && sizeAllProperties(properties) == 0)
			return null;
		Properties result = new Properties();
		enumerateAllProperties(properties, result);
		return result;
	}
	public static Properties copyNonDefaultProperties(Properties properties, boolean nullable) {
		if(nullable && sizeNonDefaultProperties(properties) == 0)
			return null;
		Properties result = new Properties();
		enumerateNonDefaultProperties(properties, result);
		return result;
	}
	public static void enumerateAllProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		if(properties == null) return;
		Properties defaults = __get_defaults_properties(properties);
		if(defaults != null) enumerateAllProperties(defaults, hashtable);
		enumerateNonDefaultProperties(properties, hashtable);
	}
	public static void enumerateNonDefaultProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		if(properties == null) return;
		Enumeration<Object> enumeration = properties.keys();
		while(enumeration.hasMoreElements()) {
			Object key = enumeration.nextElement();
			Object value = properties.get(key);
			hashtable.put(key, value);
		}
	}
	public static int sizeAllProperties(Properties properties) {
		if(properties == null) return 0;
		Properties defaults = __get_defaults_properties(properties);
		return (defaults != null ? sizeAllProperties(defaults) : 0) + sizeNonDefaultProperties(properties);
	}
	public static int sizeNonDefaultProperties(Properties properties) {
		return properties != null ? properties.size() : 0;
	}
	public static <T> T pn_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		Object object = properties.get(key);
		return type.isInstance(object) ? (T) object : defaultValue;
	}
	public static <T> T pn_getObject(Properties properties, String key, T defaultValue) { return (T) pn_getObject(properties, key, Object.class, defaultValue); }
	public static byte pn_getByte(Properties properties, String key, byte defaultValue) { return pn_getObject(properties, key, Byte.class, defaultValue); }
	public static boolean pn_getBoolean(Properties properties, String key, boolean defaultValue) { return pn_getObject(properties, key, Boolean.class, defaultValue); }
	public static char pn_getChar(Properties properties, String key, char defaultValue) { return pn_getObject(properties, key, Character.class, defaultValue); }
	public static short pn_getShort(Properties properties, String key, short defaultValue) { return pn_getObject(properties, key, Short.class, defaultValue); }
	public static int pn_getInt(Properties properties, String key, int defaultValue) { return pn_getObject(properties, key, Integer.class, defaultValue); }
	public static long pn_getLong(Properties properties, String key, long defaultValue) { return pn_getObject(properties, key, Long.class, defaultValue); }
	public static float pn_getFloat(Properties properties, String key, float defaultValue) { return pn_getObject(properties, key, Float.class, defaultValue); }
	public static double pn_getDouble(Properties properties, String key, double defaultValue) { return pn_getObject(properties, key, Double.class, defaultValue); }
	public static <T> T pn_getObject(Properties properties, String key, Class<T> type) { return pn_getObject(properties, key, type, null); }
	public static <T> T pn_getObject(Properties properties, String key) { return (T) pn_getObject(properties, key, Object.class, null); }
	public static byte pn_getByte(Properties properties, String key) { return pn_getByte(properties, key, (byte) 0); }
	public static boolean pn_getBoolean(Properties properties, String key) { return pn_getBoolean(properties, key, false); }
	public static char pn_getChar(Properties properties, String key) { return pn_getChar(properties, key, (char) 0); }
	public static short pn_getShort(Properties properties, String key) { return pn_getShort(properties, key, (short) 0); }
	public static int pn_getInt(Properties properties, String key) { return pn_getInt(properties, key, 0); }
	public static long pn_getLong(Properties properties, String key) { return pn_getLong(properties, key, 0); }
	public static float pn_getFloat(Properties properties, String key) { return pn_getFloat(properties, key, 0); }
	public static double pn_getDouble(Properties properties, String key) { return pn_getDouble(properties, key, 0); }
	public static <T> T p_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		Object object = properties.get(key);
		T castedObject = type.isInstance(object) ? (T) object : null;
		if(castedObject != null) return castedObject;
		Properties defaults = __get_defaults_properties(properties);
		if(defaults == null) return defaultValue;
		return p_getObject(defaults, key, type, defaultValue);
	}
	public static <T> T p_getObject(Properties properties, String key, T defaultValue) { return (T) p_getObject(properties, key, Object.class, defaultValue); }
	public static byte p_getByte(Properties properties, String key, byte defaultValue) { return p_getObject(properties, key, Byte.class, defaultValue); }
	public static boolean p_getBoolean(Properties properties, String key, boolean defaultValue) { return p_getObject(properties, key, Boolean.class, defaultValue); }
	public static char p_getChar(Properties properties, String key, char defaultValue) { return p_getObject(properties, key, Character.class, defaultValue); }
	public static short p_getShort(Properties properties, String key, short defaultValue) { return p_getObject(properties, key, Short.class, defaultValue); }
	public static int p_getInt(Properties properties, String key, int defaultValue) { return p_getObject(properties, key, Integer.class, defaultValue); }
	public static long p_getLong(Properties properties, String key, long defaultValue) { return p_getObject(properties, key, Long.class, defaultValue); }
	public static float p_getFloat(Properties properties, String key, float defaultValue) { return p_getObject(properties, key, Float.class, defaultValue); }
	public static double p_getDouble(Properties properties, String key, double defaultValue) { return p_getObject(properties, key, Double.class, defaultValue); }
	public static <T> T p_getObject(Properties properties, String key, Class<T> type) { return p_getObject(properties, key, type, null); }
	public static <T> T p_getObject(Properties properties, String key) { return (T) p_getObject(properties, key, Object.class, null); }
	public static byte p_getByte(Properties properties, String key) { return p_getByte(properties, key, (byte) 0); }
	public static boolean p_getBoolean(Properties properties, String key) { return p_getBoolean(properties, key, false); }
	public static char p_getChar(Properties properties, String key) { return p_getChar(properties, key, (char) 0); }
	public static short p_getShort(Properties properties, String key) { return p_getShort(properties, key, (short) 0); }
	public static int p_getInt(Properties properties, String key) { return p_getInt(properties, key, 0); }
	public static long p_getLong(Properties properties, String key) { return p_getLong(properties, key, 0); }
	public static float p_getFloat(Properties properties, String key) { return p_getFloat(properties, key, 0); }
	public static double p_getDouble(Properties properties, String key) { return p_getDouble(properties, key, 0); }
	public static <T> void p_setObject(Properties properties, String key, Class<T> type, T value) { if(key == null || value == null) return; properties.put(key, value); }
	public static <T> void p_setObject(Properties properties, String key, T value) { p_setObject(properties, key, Object.class, value); }
	public static void p_setByte(Properties properties, String key, byte value) { p_setObject(properties, key, Byte.class, value); }
	public static void p_setBoolean(Properties properties, String key, boolean value) { p_setObject(properties, key, Boolean.class, value); }
	public static void p_setChar(Properties properties, String key, char value) { p_setObject(properties, key, Character.class, value); }
	public static void p_setShort(Properties properties, String key, short value) { p_setObject(properties, key, Short.class, value); }
	public static void p_setInt(Properties properties, String key, int value) { p_setObject(properties, key, Integer.class, value); }
	public static void p_setLong(Properties properties, String key, long value) { p_setObject(properties, key, Long.class, value); }
	public static void p_setFloat(Properties properties, String key, float value) { p_setObject(properties, key, Float.class, value); }
	public static void p_setDouble(Properties properties, String key, double value) { p_setObject(properties, key, Double.class, value); }
}
