package io.github.NadhifRadityo.Library.Utils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.jar.Attributes;

import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.unsafe;

public class AttributesUtils {
	protected static ThreadLocal<WeakReference<Attributes.Name>> localAttributesKey = new ThreadLocal<>();
	protected static final long AFIELD_Attributes$Name_name;
	protected static final long AFIELD_Attributes$Name_hashCode;

	static { try {
		Field FIELD_Attributes$Name_name = Attributes.Name.class.getDeclaredField("name");
		Field FIELD_Attributes$Name_hashCode = Attributes.Name.class.getDeclaredField("hashCode");
		AFIELD_Attributes$Name_name = unsafe.objectFieldOffset(FIELD_Attributes$Name_name);
		AFIELD_Attributes$Name_hashCode = unsafe.objectFieldOffset(FIELD_Attributes$Name_hashCode);
	} catch(Exception e) { throw new Error(e); } }

	public static Attributes.Name keyAttribute(String key) {
		Attributes.Name result = localAttributesKey.get() != null ? localAttributesKey.get().get() : null;
		if(result == null) {
			try { result = (Attributes.Name) unsafe.allocateInstance(Attributes.Name.class);
			} catch(InstantiationException e) { throw new Error(e); }
			localAttributesKey.set(new WeakReference<>(result));
		}
		unsafe.putObject(result, AFIELD_Attributes$Name_name, key);
		unsafe.putInt(result, AFIELD_Attributes$Name_hashCode, -1);
		return result;
	}
	public static <T> T a_getObject(Attributes attributes, String key, Function<String, T> converter, T defaultValue) {
		String value = (String) attributes.get(keyAttribute(key));
		return value != null ? converter.apply(value) : defaultValue;
	}
	public static String a_getString(Attributes attributes, String key, String defaultValue) { return a_getObject(attributes, key, Function.identity(), defaultValue); }
	public static byte a_getByte(Attributes attributes, String key, byte defaultValue) { return a_getObject(attributes, key, Byte::valueOf, defaultValue); }
	public static boolean a_getBoolean(Attributes attributes, String key, boolean defaultValue) { return a_getObject(attributes, key, Boolean::valueOf, defaultValue); }
	public static char a_getChar(Attributes attributes, String key, char defaultValue) { return a_getObject(attributes, key, v -> v.charAt(0), defaultValue); }
	public static short a_getShort(Attributes attributes, String key, short defaultValue) { return a_getObject(attributes, key, Short::valueOf, defaultValue); }
	public static int a_getInt(Attributes attributes, String key, int defaultValue) { return a_getObject(attributes, key, Integer::valueOf, defaultValue); }
	public static long a_getLong(Attributes attributes, String key, long defaultValue) { return a_getObject(attributes, key, Long::valueOf, defaultValue); }
	public static float a_getFloat(Attributes attributes, String key, float defaultValue) { return a_getObject(attributes, key, Float::valueOf, defaultValue); }
	public static double a_getDouble(Attributes attributes, String key, double defaultValue) { return a_getObject(attributes, key, Double::valueOf, defaultValue); }
	public static String a_getString(Attributes attributes, String key) { return a_getString(attributes, key, null); }
	public static byte a_getByte(Attributes attributes, String key) { return a_getByte(attributes, key, (byte) 0); }
	public static boolean a_getBoolean(Attributes attributes, String key) { return a_getBoolean(attributes, key, false); }
	public static char a_getChar(Attributes attributes, String key) { return a_getChar(attributes, key, (char) 0); }
	public static short a_getShort(Attributes attributes, String key) { return a_getShort(attributes, key, (short) 0); }
	public static int a_getInt(Attributes attributes, String key) { return a_getInt(attributes, key, 0); }
	public static long a_getLong(Attributes attributes, String key) { return a_getLong(attributes, key, 0); }
	public static float a_getFloat(Attributes attributes, String key) { return a_getFloat(attributes, key, 0); }
	public static double a_getDouble(Attributes attributes, String key) { return a_getDouble(attributes, key, 0); }
	public static <T> void a_setObject(Attributes attributes, String key, Function<T, String> converter, T value) { attributes.put(keyAttribute(key), converter.apply(value)); }
	public static void a_setObject(Attributes attributes, String key, Object value) { a_setObject(attributes, key, Object::toString, value); }
	public static void a_setString(Attributes attributes, String key, String value) { a_setObject(attributes, key, Function.identity(), value); }
	public static void a_setByte(Attributes attributes, String key, byte value) { a_setObject(attributes, key, (v) -> Byte.toString(v), value); }
	public static void a_setBoolean(Attributes attributes, String key, boolean value) { a_setObject(attributes, key, (v) -> Boolean.toString(v), value); }
	public static void a_setChar(Attributes attributes, String key, char value) { a_setObject(attributes, key, (v) -> Character.toString(v), value); }
	public static void a_setShort(Attributes attributes, String key, short value) { a_setObject(attributes, key, (v) -> Short.toString(v), value); }
	public static void a_setInt(Attributes attributes, String key, int value) { a_setObject(attributes, key, (v) -> Integer.toString(v), value); }
	public static void a_setLong(Attributes attributes, String key, long value) { a_setObject(attributes, key, (v) -> Long.toString(v), value); }
	public static void a_setFloat(Attributes attributes, String key, float value) { a_setObject(attributes, key, (v) -> Float.toString(v), value); }
	public static void a_setDouble(Attributes attributes, String key, double value) { a_setObject(attributes, key, (v) -> Double.toString(v), value); }
}
