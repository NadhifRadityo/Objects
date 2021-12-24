package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class StreamUtils {
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_construct")).call();
	}
	public static int __INTERNAL_Gradle$Strategies$StreamUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_hashCode")).call();
	}
	public static int get__INTERNAL_Gradle$Strategies$StreamUtils_COPY_CACHE_SIZE() {
		return (Integer) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_COPY_CACHE_SIZE");
	}
	public static int getCOPY_CACHE_SIZE() {
		return (Integer) ((GroovyObject) getContext().getThat()).getProperty("COPY_CACHE_SIZE");
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_copyStream(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_copyStream")).call(inputStream, outputStream, progress);
	}
	public static void copyStream(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("copyStream")).call(inputStream, outputStream, progress);
	}
	public static String __INTERNAL_Gradle$Strategies$StreamUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_toString")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_destruct")).call();
	}
	public static byte[] __INTERNAL_Gradle$Strategies$StreamUtils_streamBytes(InputStream inputStream) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_streamBytes")).call(inputStream);
	}
	public static byte[] streamBytes(InputStream inputStream) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("streamBytes")).call(inputStream);
	}
	public static String __INTERNAL_Gradle$Strategies$StreamUtils_streamString(InputStream inputStream, Charset charset) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_streamString")).call(inputStream, charset);
	}
	public static String __INTERNAL_Gradle$Strategies$StreamUtils_streamString(InputStream inputStream) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_streamString")).call(inputStream);
	}
	public static String streamString(InputStream inputStream, Charset charset) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("streamString")).call(inputStream, charset);
	}
	public static String streamString(InputStream inputStream) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("streamString")).call(inputStream);
	}
	public static boolean __INTERNAL_Gradle$Strategies$StreamUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_equals")).call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_writeString(String string, OutputStream outputStream, Charset charset) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_writeString")).call(string, outputStream, charset);
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_writeString(String string, OutputStream outputStream) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_writeString")).call(string, outputStream);
	}
	public static void writeString(String string, OutputStream outputStream, Charset charset) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("writeString")).call(string, outputStream, charset);
	}
	public static void writeString(String string, OutputStream outputStream) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("writeString")).call(string, outputStream);
	}
	public static void __INTERNAL_Gradle$Strategies$StreamUtils_writeBytes(byte[] input, int off, int len, OutputStream outputStream) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_writeBytes")).call(input, off, len, outputStream);
	}
	public static void writeBytes(byte[] input, int off, int len, OutputStream outputStream) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("writeBytes")).call(input, off, len, outputStream);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.StreamUtils> get__INTERNAL_Gradle$Strategies$StreamUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.StreamUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$StreamUtils_cache");
	}
}
