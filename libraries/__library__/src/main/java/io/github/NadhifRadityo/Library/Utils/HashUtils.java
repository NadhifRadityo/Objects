package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import kotlin.jvm.functions.Function2;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class HashUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$HashUtils", "std$HashUtils");
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
	public static String __INTERNAL_Gradle$Strategies$HashUtils_toString() {
		return HashUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_toString").call();
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_OPENSSL(File exe, String digest) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_OPENSSL").call(exe, digest);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_OPENSSL(File exe, String digest) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("HASH_EXE_OPENSSL").call(exe, digest);
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_SHANSUM(File exe, int N) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_SHANSUM").call(exe, N);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_SHANSUM(File exe, int N) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("HASH_EXE_SHANSUM").call(exe, N);
	}
	public static void __INTERNAL_Gradle$Strategies$HashUtils_destruct() {
		HashUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_destruct").call();
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative(File file, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative").call(file, digest);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative(byte[] bytes, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative").call(bytes, digest);
	}
	public static byte[] checksumJavaNative(File file, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("checksumJavaNative").call(file, digest);
	}
	public static byte[] checksumJavaNative(byte[] bytes, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("checksumJavaNative").call(bytes, digest);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeShaNsum(File exe, File file, int N) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeShaNsum").call(exe, file, N);
	}
	public static byte[] checksumExeShaNsum(File exe, File file, int N) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("checksumExeShaNsum").call(exe, file, N);
	}
	public static boolean __INTERNAL_Gradle$Strategies$HashUtils_equals(Object other) {
		return HashUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_equals").call(other);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeMdNsum(File exe, File file, int N) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeMdNsum").call(exe, file, N);
	}
	public static byte[] checksumExeMdNsum(File exe, File file, int N) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("checksumExeMdNsum").call(exe, file, N);
	}
	public static Function2<Object, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_JAVA_NATIVE(String digest) {
		return HashUtils.<Closure<Function2<Object, Object, Boolean>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_HASH_JAVA_NATIVE").call(digest);
	}
	public static Function2<Object, Object, Boolean> HASH_JAVA_NATIVE(String digest) {
		return HashUtils.<Closure<Function2<Object, Object, Boolean>>>__UTILS_GET_PROPERTY__("HASH_JAVA_NATIVE").call(digest);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeCertutil(File exe, File file, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeCertutil").call(exe, file, digest);
	}
	public static byte[] checksumExeCertutil(File exe, File file, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("checksumExeCertutil").call(exe, file, digest);
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_CERTUTIL(File exe, String digest) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_CERTUTIL").call(exe, digest);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_CERTUTIL(File exe, String digest) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("HASH_EXE_CERTUTIL").call(exe, digest);
	}
	public static void __INTERNAL_Gradle$Strategies$HashUtils_construct() {
		HashUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_construct").call();
	}
	public static String __INTERNAL_Gradle$Strategies$HashUtils_getHashExpected(Object obj) {
		return HashUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_getHashExpected").call(obj);
	}
	public static String getHashExpected(Object obj) {
		return HashUtils.<Closure<String>>__UTILS_GET_PROPERTY__("getHashExpected").call(obj);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.HashUtils> get__INTERNAL_Gradle$Strategies$HashUtils_cache() {
		return HashUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.HashUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_cache");
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeOpenssl(File exe, File file, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeOpenssl").call(exe, file, digest);
	}
	public static byte[] checksumExeOpenssl(File exe, File file, String digest) {
		return HashUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("checksumExeOpenssl").call(exe, file, digest);
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_MDNSUM(File exe, int N) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_MDNSUM").call(exe, N);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_MDNSUM(File exe, int N) {
		return HashUtils.<Closure<Function2<File, Object, Boolean>>>__UTILS_GET_PROPERTY__("HASH_EXE_MDNSUM").call(exe, N);
	}
	public static int __INTERNAL_Gradle$Strategies$HashUtils_hashCode() {
		return HashUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$HashUtils_hashCode").call();
	}
}
