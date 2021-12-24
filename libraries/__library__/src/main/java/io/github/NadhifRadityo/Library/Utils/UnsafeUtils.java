package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;
import sun.misc.Unsafe;

public class UnsafeUtils {
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_count() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_count");
	}
	public static Unsafe get__INTERNAL_Gradle$Strategies$UnsafeUtils_unsafe() {
		return (Unsafe) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_unsafe");
	}
	public static Unsafe getUnsafe() {
		return (Unsafe) ((GroovyObject) getContext().getThat()).getProperty("unsafe");
	}
	public static String __INTERNAL_Gradle$Strategies$UnsafeUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_toString")).call();
	}
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_buf() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_buf");
	}
	public static boolean __INTERNAL_Gradle$Strategies$UnsafeUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_equals")).call(other);
	}
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_mark() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_mark");
	}
	public static ThreadLocal<WeakReference<ByteArrayOutputStream>> get__INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBufferStore() {
		return (ThreadLocal<WeakReference<ByteArrayOutputStream>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBufferStore");
	}
	public static void __INTERNAL_Gradle$Strategies$UnsafeUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_destruct")).call();
	}
	public static int __INTERNAL_Gradle$Strategies$UnsafeUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_hashCode")).call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.UnsafeUtils> get__INTERNAL_Gradle$Strategies$UnsafeUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.UnsafeUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_cache");
	}
	public static ThreadLocal<WeakReference<byte[]>> get__INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArrayStore() {
		return (ThreadLocal<WeakReference<byte[]>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArrayStore");
	}
	public static ByteArrayInputStream __INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBuffer(byte[] bytes, int off, int len) {
		return ((Closure<ByteArrayInputStream>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBuffer")).call(bytes, off, len);
	}
	public static ByteArrayInputStream tempInputBuffer(byte[] bytes, int off, int len) {
		return ((Closure<ByteArrayInputStream>) ((GroovyObject) getContext().getThat()).getProperty("tempInputBuffer")).call(bytes, off, len);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArray(int length) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempByteArray")).call(length);
	}
	public static byte[] tempByteArray(int length) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("tempByteArray")).call(length);
	}
	public static ByteArrayOutputStream __INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBuffer() {
		return ((Closure<ByteArrayOutputStream>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempOutputBuffer")).call();
	}
	public static ByteArrayOutputStream tempOutputBuffer() {
		return ((Closure<ByteArrayOutputStream>) ((GroovyObject) getContext().getThat()).getProperty("tempOutputBuffer")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$UnsafeUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_construct")).call();
	}
	public static ThreadLocal<WeakReference<ByteArrayInputStream>> get__INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBufferStore() {
		return (ThreadLocal<WeakReference<ByteArrayInputStream>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_tempInputBufferStore");
	}
	public static long get__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_pos() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$UnsafeUtils_AFIELD_ByteArrayInputStream_pos");
	}
}
