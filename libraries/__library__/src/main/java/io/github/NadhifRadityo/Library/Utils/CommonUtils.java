package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.text.CharacterIterator;
import java.util.function.Consumer;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class CommonUtils {
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_purgeThreadLocal(ThreadLocal<?> threadLocal) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_purgeThreadLocal")).call(threadLocal);
	}
	public static void purgeThreadLocal(ThreadLocal<?> threadLocal) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("purgeThreadLocal")).call(threadLocal);
	}
	public static boolean __INTERNAL_Gradle$Strategies$CommonUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_equals")).call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_toString")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_construct")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_downloadFile(URL url, OutputStream outputStream) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_downloadFile")).call(url, outputStream);
	}
	public static void downloadFile(URL url, OutputStream outputStream) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("downloadFile")).call(url, outputStream);
	}
	public static StringBuilder get__INTERNAL_Gradle$Strategies$CommonUtils_downloadProgressCache() {
		return (StringBuilder) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_downloadProgressCache");
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_destruct")).call();
	}
	public static CharacterIterator get__INTERNAL_Gradle$Strategies$CommonUtils_readableByteCount() {
		return (CharacterIterator) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_readableByteCount");
	}
	public static Consumer<Long> __INTERNAL_Gradle$Strategies$CommonUtils_newStreamProgress(long totalSize) {
		return ((Closure<Consumer<Long>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_newStreamProgress")).call(totalSize);
	}
	public static Consumer<Long> newStreamProgress(long totalSize) {
		return ((Closure<Consumer<Long>>) ((GroovyObject) getContext().getThat()).getProperty("newStreamProgress")).call(totalSize);
	}
	public static URI __INTERNAL_Gradle$Strategies$CommonUtils_urlToUri(URL url) {
		return ((Closure<URI>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_urlToUri")).call(url);
	}
	public static URI urlToUri(URL url) {
		return ((Closure<URI>) ((GroovyObject) getContext().getThat()).getProperty("urlToUri")).call(url);
	}
	public static int __INTERNAL_Gradle$Strategies$CommonUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_hashCode")).call();
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_humanReadableByteCount(long bytes0) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_humanReadableByteCount")).call(bytes0);
	}
	public static String humanReadableByteCount(long bytes0) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("humanReadableByteCount")).call(bytes0);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$CommonUtils_hexStringToBytes(String string) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_hexStringToBytes")).call(string);
	}
	public static byte[] hexStringToBytes(String string) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("hexStringToBytes")).call(string);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.CommonUtils> get__INTERNAL_Gradle$Strategies$CommonUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.CommonUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_cache");
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_bytesToHexString(byte... bytes) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_bytesToHexString")).call(bytes);
	}
	public static String bytesToHexString(byte... bytes) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("bytesToHexString")).call(bytes);
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_printDownloadProgress(long startTime, long total, long current, long speed) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_printDownloadProgress")).call(startTime, total, current, speed);
	}
	public static void printDownloadProgress(long startTime, long total, long current, long speed) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("printDownloadProgress")).call(startTime, total, current, speed);
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_formattedUrl(String url) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$CommonUtils_formattedUrl")).call(url);
	}
	public static String formattedUrl(String url) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("formattedUrl")).call(url);
	}
}
