package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class KotlinUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$KotlinUtils", "std$KotlinUtils");
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
	public static Set<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__emptySet() {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__emptySet").call();
	}
	public static Set<Object> emptySet() {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("emptySet").call();
	}
	public static Map<Object, Object> __INTERNAL_Gradle$Strategies$KotlinUtils__mapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<Map<Object, Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__mapOf").call(pairs);
	}
	public static Map<Object, Object> mapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<Map<Object, Object>>>__UTILS_GET_PROPERTY__("mapOf").call(pairs);
	}
	public static List<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__mutableListOf(Object... elements) {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__mutableListOf").call(elements);
	}
	public static List<Object> mutableListOf(Object... elements) {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("mutableListOf").call(elements);
	}
	public static Object[] __INTERNAL_Gradle$Strategies$KotlinUtils__arrayOf(Object... elements) {
		return KotlinUtils.<Closure<Object[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__arrayOf").call(elements);
	}
	public static Object[] arrayOf(Object... elements) {
		return KotlinUtils.<Closure<Object[]>>__UTILS_GET_PROPERTY__("arrayOf").call(elements);
	}
	public static Set<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__mutableSetOf(Object... elements) {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__mutableSetOf").call(elements);
	}
	public static Set<Object> mutableSetOf(Object... elements) {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("mutableSetOf").call(elements);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.KotlinUtils> get__INTERNAL_Gradle$Strategies$KotlinUtils_cache() {
		return KotlinUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.KotlinUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils_cache");
	}
	public static float[] __INTERNAL_Gradle$Strategies$KotlinUtils__floatArrayOf(float... elements) {
		return KotlinUtils.<Closure<float[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__floatArrayOf").call(elements);
	}
	public static float[] floatArrayOf(float... elements) {
		return KotlinUtils.<Closure<float[]>>__UTILS_GET_PROPERTY__("floatArrayOf").call(elements);
	}
	public static int __INTERNAL_Gradle$Strategies$KotlinUtils_hashCode() {
		return KotlinUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils_hashCode").call();
	}
	public static List<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__listOfNotNull(Object... elements) {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__listOfNotNull").call(elements);
	}
	public static List<Object> listOfNotNull(Object... elements) {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("listOfNotNull").call(elements);
	}
	public static char[] __INTERNAL_Gradle$Strategies$KotlinUtils__charArrayOf(char... elements) {
		return KotlinUtils.<Closure<char[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__charArrayOf").call(elements);
	}
	public static char[] charArrayOf(char... elements) {
		return KotlinUtils.<Closure<char[]>>__UTILS_GET_PROPERTY__("charArrayOf").call(elements);
	}
	public static Map<Object, Object> __INTERNAL_Gradle$Strategies$KotlinUtils__emptyMap() {
		return KotlinUtils.<Closure<Map<Object, Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__emptyMap").call();
	}
	public static Map<Object, Object> emptyMap() {
		return KotlinUtils.<Closure<Map<Object, Object>>>__UTILS_GET_PROPERTY__("emptyMap").call();
	}
	public static Map<Object, Object> __INTERNAL_Gradle$Strategies$KotlinUtils__mutableMapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<Map<Object, Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__mutableMapOf").call(pairs);
	}
	public static Map<Object, Object> mutableMapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<Map<Object, Object>>>__UTILS_GET_PROPERTY__("mutableMapOf").call(pairs);
	}
	public static boolean __INTERNAL_Gradle$Strategies$KotlinUtils_equals(Object other) {
		return KotlinUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils_equals").call(other);
	}
	public static Set<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__setOf(Object... elements) {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__setOf").call(elements);
	}
	public static Set<Object> setOf(Object... elements) {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("setOf").call(elements);
	}
	public static HashSet<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__hashSetOf(Object... elements) {
		return KotlinUtils.<Closure<HashSet<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__hashSetOf").call(elements);
	}
	public static HashSet<Object> hashSetOf(Object... elements) {
		return KotlinUtils.<Closure<HashSet<Object>>>__UTILS_GET_PROPERTY__("hashSetOf").call(elements);
	}
	public static HashMap<Object, Object> __INTERNAL_Gradle$Strategies$KotlinUtils__hashMapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<HashMap<Object, Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__hashMapOf").call(pairs);
	}
	public static HashMap<Object, Object> hashMapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<HashMap<Object, Object>>>__UTILS_GET_PROPERTY__("hashMapOf").call(pairs);
	}
	public static long[] __INTERNAL_Gradle$Strategies$KotlinUtils__longArrayOf(long... elements) {
		return KotlinUtils.<Closure<long[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__longArrayOf").call(elements);
	}
	public static long[] longArrayOf(long... elements) {
		return KotlinUtils.<Closure<long[]>>__UTILS_GET_PROPERTY__("longArrayOf").call(elements);
	}
	public static short[] __INTERNAL_Gradle$Strategies$KotlinUtils__shortArrayOf(short... elements) {
		return KotlinUtils.<Closure<short[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__shortArrayOf").call(elements);
	}
	public static short[] shortArrayOf(short... elements) {
		return KotlinUtils.<Closure<short[]>>__UTILS_GET_PROPERTY__("shortArrayOf").call(elements);
	}
	public static Set<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__setOfNotNull(Object... elements) {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__setOfNotNull").call(elements);
	}
	public static Set<Object> setOfNotNull(Object... elements) {
		return KotlinUtils.<Closure<Set<Object>>>__UTILS_GET_PROPERTY__("setOfNotNull").call(elements);
	}
	public static ArrayList<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__arrayListOf(Object... elements) {
		return KotlinUtils.<Closure<ArrayList<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__arrayListOf").call(elements);
	}
	public static ArrayList<Object> arrayListOf(Object... elements) {
		return KotlinUtils.<Closure<ArrayList<Object>>>__UTILS_GET_PROPERTY__("arrayListOf").call(elements);
	}
	public static String __INTERNAL_Gradle$Strategies$KotlinUtils_toString() {
		return KotlinUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$KotlinUtils_construct() {
		KotlinUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils_construct").call();
	}
	public static boolean[] __INTERNAL_Gradle$Strategies$KotlinUtils__booleanArrayOf(boolean... elements) {
		return KotlinUtils.<Closure<boolean[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__booleanArrayOf").call(elements);
	}
	public static boolean[] booleanArrayOf(boolean... elements) {
		return KotlinUtils.<Closure<boolean[]>>__UTILS_GET_PROPERTY__("booleanArrayOf").call(elements);
	}
	public static Object[] __INTERNAL_Gradle$Strategies$KotlinUtils__arrayOfNulls(int size) {
		return KotlinUtils.<Closure<Object[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__arrayOfNulls").call(size);
	}
	public static Object[] arrayOfNulls(int size) {
		return KotlinUtils.<Closure<Object[]>>__UTILS_GET_PROPERTY__("arrayOfNulls").call(size);
	}
	public static void __INTERNAL_Gradle$Strategies$KotlinUtils_destruct() {
		KotlinUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils_destruct").call();
	}
	public static byte[] __INTERNAL_Gradle$Strategies$KotlinUtils__byteArrayOf(byte... elements) {
		return KotlinUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__byteArrayOf").call(elements);
	}
	public static byte[] byteArrayOf(byte... elements) {
		return KotlinUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("byteArrayOf").call(elements);
	}
	public static List<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__emptyList() {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__emptyList").call();
	}
	public static List<Object> emptyList() {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("emptyList").call();
	}
	public static LinkedHashSet<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__linkedSetOf(Object... elements) {
		return KotlinUtils.<Closure<LinkedHashSet<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__linkedSetOf").call(elements);
	}
	public static LinkedHashSet<Object> linkedSetOf(Object... elements) {
		return KotlinUtils.<Closure<LinkedHashSet<Object>>>__UTILS_GET_PROPERTY__("linkedSetOf").call(elements);
	}
	public static List<Object> __INTERNAL_Gradle$Strategies$KotlinUtils__listOf(Object... elements) {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__listOf").call(elements);
	}
	public static List<Object> listOf(Object... elements) {
		return KotlinUtils.<Closure<List<Object>>>__UTILS_GET_PROPERTY__("listOf").call(elements);
	}
	public static double[] __INTERNAL_Gradle$Strategies$KotlinUtils__doubleArrayOf(double... elements) {
		return KotlinUtils.<Closure<double[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__doubleArrayOf").call(elements);
	}
	public static double[] doubleArrayOf(double... elements) {
		return KotlinUtils.<Closure<double[]>>__UTILS_GET_PROPERTY__("doubleArrayOf").call(elements);
	}
	public static int[] __INTERNAL_Gradle$Strategies$KotlinUtils__intArrayOf(int... elements) {
		return KotlinUtils.<Closure<int[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__intArrayOf").call(elements);
	}
	public static int[] intArrayOf(int... elements) {
		return KotlinUtils.<Closure<int[]>>__UTILS_GET_PROPERTY__("intArrayOf").call(elements);
	}
	public static LinkedHashMap<Object, Object> __INTERNAL_Gradle$Strategies$KotlinUtils__linkedMapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<LinkedHashMap<Object, Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KotlinUtils__linkedMapOf").call(pairs);
	}
	public static LinkedHashMap<Object, Object> linkedMapOf(Pair<?, ?>... pairs) {
		return KotlinUtils.<Closure<LinkedHashMap<Object, Object>>>__UTILS_GET_PROPERTY__("linkedMapOf").call(pairs);
	}
}
