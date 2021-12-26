package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.jar.Attributes;
import kotlin.jvm.functions.Function1;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class AttributesUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$AttributesUtils", "std$AttributesUtils");
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
	public static double __INTERNAL_Gradle$Strategies$AttributesUtils_a_getDouble(Attributes attributes, String key, double defaultValue) {
		return AttributesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getDouble").call(attributes, key, defaultValue);
	}
	public static double __INTERNAL_Gradle$Strategies$AttributesUtils_a_getDouble(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getDouble").call(attributes, key);
	}
	public static double a_getDouble(Attributes attributes, String key, double defaultValue) {
		return AttributesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("a_getDouble").call(attributes, key, defaultValue);
	}
	public static double a_getDouble(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Double>>__UTILS_GET_PROPERTY__("a_getDouble").call(attributes, key);
	}
	public static Attributes.Name __INTERNAL_Gradle$Strategies$AttributesUtils_keyAttribute(String key) {
		return AttributesUtils.<Closure<Attributes.Name>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_keyAttribute").call(key);
	}
	public static Attributes.Name keyAttribute(String key) {
		return AttributesUtils.<Closure<Attributes.Name>>__UTILS_GET_PROPERTY__("keyAttribute").call(key);
	}
	public static ThreadLocal<WeakReference<Attributes.Name>> get__INTERNAL_Gradle$Strategies$AttributesUtils_localAttributesKey() {
		return AttributesUtils.<ThreadLocal<WeakReference<Attributes.Name>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_localAttributesKey");
	}
	public static float __INTERNAL_Gradle$Strategies$AttributesUtils_a_getFloat(Attributes attributes, String key, float defaultValue) {
		return AttributesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getFloat").call(attributes, key, defaultValue);
	}
	public static float __INTERNAL_Gradle$Strategies$AttributesUtils_a_getFloat(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getFloat").call(attributes, key);
	}
	public static float a_getFloat(Attributes attributes, String key, float defaultValue) {
		return AttributesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("a_getFloat").call(attributes, key, defaultValue);
	}
	public static float a_getFloat(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Float>>__UTILS_GET_PROPERTY__("a_getFloat").call(attributes, key);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setLong(Attributes attributes, String key, long value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setLong").call(attributes, key, value);
	}
	public static void a_setLong(Attributes attributes, String key, long value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setLong").call(attributes, key, value);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$AttributesUtils_a_getObject(Attributes attributes, String key, Function<String, T> converter, T defaultValue) {
		return AttributesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getObject").call(attributes, key, converter, defaultValue);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$AttributesUtils_a_getObject(Attributes attributes, String key, Function<String, T> converter) {
		return AttributesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getObject").call(attributes, key, converter);
	}
	public static <T> T a_getObject(Attributes attributes, String key, Function<String, T> converter, T defaultValue) {
		return AttributesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("a_getObject").call(attributes, key, converter, defaultValue);
	}
	public static <T> T a_getObject(Attributes attributes, String key, Function<String, T> converter) {
		return AttributesUtils.<Closure<T>>__UTILS_GET_PROPERTY__("a_getObject").call(attributes, key, converter);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setInt(Attributes attributes, String key, int value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setInt").call(attributes, key, value);
	}
	public static void a_setInt(Attributes attributes, String key, int value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setInt").call(attributes, key, value);
	}
	public static String __INTERNAL_Gradle$Strategies$AttributesUtils_toString() {
		return AttributesUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_toString").call();
	}
	public static <T> void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setObject(Attributes attributes, String key, Function1<? super T, String> converter, T value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setObject").call(attributes, key, converter, value);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setObject(Attributes attributes, String key, Object value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setObject").call(attributes, key, value);
	}
	public static <T> void a_setObject(Attributes attributes, String key, Function1<? super T, String> converter, T value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setObject").call(attributes, key, converter, value);
	}
	public static void a_setObject(Attributes attributes, String key, Object value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setObject").call(attributes, key, value);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_construct() {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setByte(Attributes attributes, String key, byte value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setByte").call(attributes, key, value);
	}
	public static void a_setByte(Attributes attributes, String key, byte value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setByte").call(attributes, key, value);
	}
	public static long get__INTERNAL_Gradle$Strategies$AttributesUtils_AFIELD_Attributes_Name_hashCode() {
		return AttributesUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_AFIELD_Attributes_Name_hashCode");
	}
	public static int __INTERNAL_Gradle$Strategies$AttributesUtils_hashCode() {
		return AttributesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_hashCode").call();
	}
	public static String __INTERNAL_Gradle$Strategies$AttributesUtils_a_getString(Attributes attributes, String key, String defaultValue) {
		return AttributesUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getString").call(attributes, key, defaultValue);
	}
	public static String __INTERNAL_Gradle$Strategies$AttributesUtils_a_getString(Attributes attributes, String key) {
		return AttributesUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getString").call(attributes, key);
	}
	public static String a_getString(Attributes attributes, String key, String defaultValue) {
		return AttributesUtils.<Closure<String>>__UTILS_GET_PROPERTY__("a_getString").call(attributes, key, defaultValue);
	}
	public static String a_getString(Attributes attributes, String key) {
		return AttributesUtils.<Closure<String>>__UTILS_GET_PROPERTY__("a_getString").call(attributes, key);
	}
	public static boolean __INTERNAL_Gradle$Strategies$AttributesUtils_equals(Object other) {
		return AttributesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_equals").call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setFloat(Attributes attributes, String key, float value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setFloat").call(attributes, key, value);
	}
	public static void a_setFloat(Attributes attributes, String key, float value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setFloat").call(attributes, key, value);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_destruct() {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_destruct").call();
	}
	public static long get__INTERNAL_Gradle$Strategies$AttributesUtils_AFIELD_Attributes_Name_name() {
		return AttributesUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_AFIELD_Attributes_Name_name");
	}
	public static byte __INTERNAL_Gradle$Strategies$AttributesUtils_a_getByte(Attributes attributes, String key, byte defaultValue) {
		return AttributesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getByte").call(attributes, key, defaultValue);
	}
	public static byte __INTERNAL_Gradle$Strategies$AttributesUtils_a_getByte(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getByte").call(attributes, key);
	}
	public static byte a_getByte(Attributes attributes, String key, byte defaultValue) {
		return AttributesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("a_getByte").call(attributes, key, defaultValue);
	}
	public static byte a_getByte(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Byte>>__UTILS_GET_PROPERTY__("a_getByte").call(attributes, key);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setBoolean(Attributes attributes, String key, boolean value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setBoolean").call(attributes, key, value);
	}
	public static void a_setBoolean(Attributes attributes, String key, boolean value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setBoolean").call(attributes, key, value);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setChar(Attributes attributes, String key, char value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setChar").call(attributes, key, value);
	}
	public static void a_setChar(Attributes attributes, String key, char value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setChar").call(attributes, key, value);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setString(Attributes attributes, String key, String value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setString").call(attributes, key, value);
	}
	public static void a_setString(Attributes attributes, String key, String value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setString").call(attributes, key, value);
	}
	public static int __INTERNAL_Gradle$Strategies$AttributesUtils_a_getInt(Attributes attributes, String key, int defaultValue) {
		return AttributesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getInt").call(attributes, key, defaultValue);
	}
	public static int __INTERNAL_Gradle$Strategies$AttributesUtils_a_getInt(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getInt").call(attributes, key);
	}
	public static int a_getInt(Attributes attributes, String key, int defaultValue) {
		return AttributesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("a_getInt").call(attributes, key, defaultValue);
	}
	public static int a_getInt(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("a_getInt").call(attributes, key);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setShort(Attributes attributes, String key, short value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setShort").call(attributes, key, value);
	}
	public static void a_setShort(Attributes attributes, String key, short value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setShort").call(attributes, key, value);
	}
	public static char __INTERNAL_Gradle$Strategies$AttributesUtils_a_getChar(Attributes attributes, String key, char defaultValue) {
		return AttributesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getChar").call(attributes, key, defaultValue);
	}
	public static char __INTERNAL_Gradle$Strategies$AttributesUtils_a_getChar(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getChar").call(attributes, key);
	}
	public static char a_getChar(Attributes attributes, String key, char defaultValue) {
		return AttributesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("a_getChar").call(attributes, key, defaultValue);
	}
	public static char a_getChar(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Character>>__UTILS_GET_PROPERTY__("a_getChar").call(attributes, key);
	}
	public static long __INTERNAL_Gradle$Strategies$AttributesUtils_a_getLong(Attributes attributes, String key, long defaultValue) {
		return AttributesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getLong").call(attributes, key, defaultValue);
	}
	public static long __INTERNAL_Gradle$Strategies$AttributesUtils_a_getLong(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getLong").call(attributes, key);
	}
	public static long a_getLong(Attributes attributes, String key, long defaultValue) {
		return AttributesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("a_getLong").call(attributes, key, defaultValue);
	}
	public static long a_getLong(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Long>>__UTILS_GET_PROPERTY__("a_getLong").call(attributes, key);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.AttributesUtils> get__INTERNAL_Gradle$Strategies$AttributesUtils_cache() {
		return AttributesUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.AttributesUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_cache");
	}
	public static boolean __INTERNAL_Gradle$Strategies$AttributesUtils_a_getBoolean(Attributes attributes, String key, boolean defaultValue) {
		return AttributesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getBoolean").call(attributes, key, defaultValue);
	}
	public static boolean __INTERNAL_Gradle$Strategies$AttributesUtils_a_getBoolean(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getBoolean").call(attributes, key);
	}
	public static boolean a_getBoolean(Attributes attributes, String key, boolean defaultValue) {
		return AttributesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("a_getBoolean").call(attributes, key, defaultValue);
	}
	public static boolean a_getBoolean(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("a_getBoolean").call(attributes, key);
	}
	public static void __INTERNAL_Gradle$Strategies$AttributesUtils_a_setDouble(Attributes attributes, String key, double value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_setDouble").call(attributes, key, value);
	}
	public static void a_setDouble(Attributes attributes, String key, double value) {
		AttributesUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("a_setDouble").call(attributes, key, value);
	}
	public static short __INTERNAL_Gradle$Strategies$AttributesUtils_a_getShort(Attributes attributes, String key, short defaultValue) {
		return AttributesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getShort").call(attributes, key, defaultValue);
	}
	public static short __INTERNAL_Gradle$Strategies$AttributesUtils_a_getShort(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$AttributesUtils_a_getShort").call(attributes, key);
	}
	public static short a_getShort(Attributes attributes, String key, short defaultValue) {
		return AttributesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("a_getShort").call(attributes, key, defaultValue);
	}
	public static short a_getShort(Attributes attributes, String key) {
		return AttributesUtils.<Closure<Short>>__UTILS_GET_PROPERTY__("a_getShort").call(attributes, key);
	}
}
