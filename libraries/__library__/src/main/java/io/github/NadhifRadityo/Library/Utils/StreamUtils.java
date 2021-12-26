package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class StreamUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$StreamUtils", "std$StreamUtils");
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
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_construct() {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_construct").call();
	}
	public static int __INTERNAL_Gradle$Strategies$StreamUtils_hashCode() {
		return StreamUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_hashCode").call();
	}
	public static int get__INTERNAL_Gradle$Strategies$StreamUtils_COPY_CACHE_SIZE() {
		return StreamUtils.<Integer>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_COPY_CACHE_SIZE");
	}
	public static int getCOPY_CACHE_SIZE() {
		return StreamUtils.<Integer>__UTILS_GET_PROPERTY__("COPY_CACHE_SIZE");
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_copyStream(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_copyStream").call(inputStream, outputStream, progress);
	}
	public static void copyStream(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("copyStream").call(inputStream, outputStream, progress);
	}
	public static String __INTERNAL_Gradle$Strategies$StreamUtils_toString() {
		return StreamUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_destruct() {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_destruct").call();
	}
	public static byte[] __INTERNAL_Gradle$Strategies$StreamUtils_streamBytes(InputStream inputStream) {
		return StreamUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_streamBytes").call(inputStream);
	}
	public static byte[] streamBytes(InputStream inputStream) {
		return StreamUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("streamBytes").call(inputStream);
	}
	public static String __INTERNAL_Gradle$Strategies$StreamUtils_streamString(InputStream inputStream, Charset charset) {
		return StreamUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_streamString").call(inputStream, charset);
	}
	public static String __INTERNAL_Gradle$Strategies$StreamUtils_streamString(InputStream inputStream) {
		return StreamUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_streamString").call(inputStream);
	}
	public static String streamString(InputStream inputStream, Charset charset) {
		return StreamUtils.<Closure<String>>__UTILS_GET_PROPERTY__("streamString").call(inputStream, charset);
	}
	public static String streamString(InputStream inputStream) {
		return StreamUtils.<Closure<String>>__UTILS_GET_PROPERTY__("streamString").call(inputStream);
	}
	public static boolean __INTERNAL_Gradle$Strategies$StreamUtils_equals(Object other) {
		return StreamUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_equals").call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_writeString(String string, OutputStream outputStream, Charset charset) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_writeString").call(string, outputStream, charset);
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_writeString(String string, OutputStream outputStream) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_writeString").call(string, outputStream);
	}
	public static void writeString(String string, OutputStream outputStream, Charset charset) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeString").call(string, outputStream, charset);
	}
	public static void writeString(String string, OutputStream outputStream) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeString").call(string, outputStream);
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_writeBytes(byte[] input, int off, int len, OutputStream outputStream) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_writeBytes").call(input, off, len, outputStream);
	}
	public static void writeBytes(byte[] input, int off, int len, OutputStream outputStream) {
		StreamUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeBytes").call(input, off, len, outputStream);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.StreamUtils> get__INTERNAL_Gradle$Strategies$StreamUtils_cache() {
		return StreamUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.StreamUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$StreamUtils_cache");
	}
}
