package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;
import sun.misc.Unsafe;

public class UnsafeUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$UnsafeUtils", "std$UnsafeUtils");
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
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_count() {
		return UnsafeUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_count");
	}
	public static Unsafe get__INTERNAL_Gradle$Strategies$UnsafeUtils_unsafe() {
		return UnsafeUtils.<Unsafe>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_unsafe");
	}
	public static Unsafe getUnsafe() {
		return UnsafeUtils.<Unsafe>__UTILS_GET_PROPERTY__("unsafe");
	}
	public static String __INTERNAL_Gradle$Strategies$UnsafeUtils_toString() {
		return UnsafeUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_toString").call();
	}
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_buf() {
		return UnsafeUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_buf");
	}
	public static boolean __INTERNAL_Gradle$Strategies$UnsafeUtils_equals(Object other) {
		return UnsafeUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_equals").call(other);
	}
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_mark() {
		return UnsafeUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_mark");
	}
	public static ThreadLocal<WeakReference<ByteArrayOutputStream>> get__INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBufferStore() {
		return UnsafeUtils.<ThreadLocal<WeakReference<ByteArrayOutputStream>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBufferStore");
	}
	public static void __INTERNAL_Gradle$Strategies$UnsafeUtils_destruct() {
		UnsafeUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_destruct").call();
	}
	public static int __INTERNAL_Gradle$Strategies$UnsafeUtils_hashCode() {
		return UnsafeUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_hashCode").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.UnsafeUtils> get__INTERNAL_Gradle$Strategies$UnsafeUtils_cache() {
		return UnsafeUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.UnsafeUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_cache");
	}
	public static ThreadLocal<WeakReference<byte[]>> get__INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArrayStore() {
		return UnsafeUtils.<ThreadLocal<WeakReference<byte[]>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArrayStore");
	}
	public static ByteArrayInputStream __INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBuffer(byte[] bytes, int off, int len) {
		return UnsafeUtils.<Closure<ByteArrayInputStream>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBuffer").call(bytes, off, len);
	}
	public static ByteArrayInputStream tempInputBuffer(byte[] bytes, int off, int len) {
		return UnsafeUtils.<Closure<ByteArrayInputStream>>__UTILS_GET_PROPERTY__("tempInputBuffer").call(bytes, off, len);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArray(int length) {
		return UnsafeUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArray").call(length);
	}
	public static byte[] tempByteArray(int length) {
		return UnsafeUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("tempByteArray").call(length);
	}
	public static ByteArrayOutputStream __INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBuffer() {
		return UnsafeUtils.<Closure<ByteArrayOutputStream>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBuffer").call();
	}
	public static ByteArrayOutputStream tempOutputBuffer() {
		return UnsafeUtils.<Closure<ByteArrayOutputStream>>__UTILS_GET_PROPERTY__("tempOutputBuffer").call();
	}
	public static void __INTERNAL_Gradle$Strategies$UnsafeUtils_construct() {
		UnsafeUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_construct").call();
	}
	public static ThreadLocal<WeakReference<ByteArrayInputStream>> get__INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBufferStore() {
		return UnsafeUtils.<ThreadLocal<WeakReference<ByteArrayInputStream>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBufferStore");
	}
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_pos() {
		return UnsafeUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_pos");
	}
}
