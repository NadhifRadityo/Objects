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
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$CommonUtils", "std$CommonUtils");
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
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_purgeThreadLocal(ThreadLocal<?> threadLocal) {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_purgeThreadLocal").call(threadLocal);
	}
	public static void purgeThreadLocal(ThreadLocal<?> threadLocal) {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("purgeThreadLocal").call(threadLocal);
	}
	public static boolean __INTERNAL_Gradle$Strategies$CommonUtils_equals(Object other) {
		return CommonUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_equals").call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_toString() {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_construct() {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_downloadFile(URL url, OutputStream outputStream) {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_downloadFile").call(url, outputStream);
	}
	public static void downloadFile(URL url, OutputStream outputStream) {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("downloadFile").call(url, outputStream);
	}
	public static StringBuilder get__INTERNAL_Gradle$Strategies$CommonUtils_downloadProgressCache() {
		return CommonUtils.<StringBuilder>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_downloadProgressCache");
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_destruct() {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_destruct").call();
	}
	public static CharacterIterator get__INTERNAL_Gradle$Strategies$CommonUtils_readableByteCount() {
		return CommonUtils.<CharacterIterator>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_readableByteCount");
	}
	public static Consumer<Long> __INTERNAL_Gradle$Strategies$CommonUtils_newStreamProgress(long totalSize) {
		return CommonUtils.<Closure<Consumer<Long>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_newStreamProgress").call(totalSize);
	}
	public static Consumer<Long> newStreamProgress(long totalSize) {
		return CommonUtils.<Closure<Consumer<Long>>>__UTILS_GET_PROPERTY__("newStreamProgress").call(totalSize);
	}
	public static URI __INTERNAL_Gradle$Strategies$CommonUtils_urlToUri(URL url) {
		return CommonUtils.<Closure<URI>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_urlToUri").call(url);
	}
	public static URI urlToUri(URL url) {
		return CommonUtils.<Closure<URI>>__UTILS_GET_PROPERTY__("urlToUri").call(url);
	}
	public static int __INTERNAL_Gradle$Strategies$CommonUtils_hashCode() {
		return CommonUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_hashCode").call();
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_humanReadableByteCount(long bytes0) {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_humanReadableByteCount").call(bytes0);
	}
	public static String humanReadableByteCount(long bytes0) {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("humanReadableByteCount").call(bytes0);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$CommonUtils_hexStringToBytes(String string) {
		return CommonUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_hexStringToBytes").call(string);
	}
	public static byte[] hexStringToBytes(String string) {
		return CommonUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("hexStringToBytes").call(string);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.CommonUtils> get__INTERNAL_Gradle$Strategies$CommonUtils_cache() {
		return CommonUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.CommonUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_cache");
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_bytesToHexString(byte... bytes) {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_bytesToHexString").call(bytes);
	}
	public static String bytesToHexString(byte... bytes) {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("bytesToHexString").call(bytes);
	}
	public static void __INTERNAL_Gradle$Strategies$CommonUtils_printDownloadProgress(long startTime, long total, long current, long speed) {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_printDownloadProgress").call(startTime, total, current, speed);
	}
	public static void printDownloadProgress(long startTime, long total, long current, long speed) {
		CommonUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("printDownloadProgress").call(startTime, total, current, speed);
	}
	public static String __INTERNAL_Gradle$Strategies$CommonUtils_formattedUrl(String url) {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$CommonUtils_formattedUrl").call(url);
	}
	public static String formattedUrl(String url) {
		return CommonUtils.<Closure<String>>__UTILS_GET_PROPERTY__("formattedUrl").call(url);
	}
}
