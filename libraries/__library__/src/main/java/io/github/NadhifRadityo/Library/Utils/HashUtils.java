package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import kotlin.jvm.functions.Function2;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class HashUtils {
	public static String __INTERNAL_Gradle$Strategies$HashUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_toString")).call();
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_OPENSSL(File exe, String digest) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_OPENSSL")).call(exe, digest);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_OPENSSL(File exe, String digest) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("HASH_EXE_OPENSSL")).call(exe, digest);
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_SHANSUM(File exe, int N) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_SHANSUM")).call(exe, N);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_SHANSUM(File exe, int N) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("HASH_EXE_SHANSUM")).call(exe, N);
	}
	public static void __INTERNAL_Gradle$Strategies$HashUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_destruct")).call();
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative(File file, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative")).call(file, digest);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative(byte[] bytes, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_checksumJavaNative")).call(bytes, digest);
	}
	public static byte[] checksumJavaNative(File file, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("checksumJavaNative")).call(file, digest);
	}
	public static byte[] checksumJavaNative(byte[] bytes, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("checksumJavaNative")).call(bytes, digest);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeShaNsum(File exe, File file, int N) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeShaNsum")).call(exe, file, N);
	}
	public static byte[] checksumExeShaNsum(File exe, File file, int N) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("checksumExeShaNsum")).call(exe, file, N);
	}
	public static boolean __INTERNAL_Gradle$Strategies$HashUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_equals")).call(other);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeMdNsum(File exe, File file, int N) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeMdNsum")).call(exe, file, N);
	}
	public static byte[] checksumExeMdNsum(File exe, File file, int N) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("checksumExeMdNsum")).call(exe, file, N);
	}
	public static Function2<Object, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_JAVA_NATIVE(String digest) {
		return ((Closure<Function2<Object, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_HASH_JAVA_NATIVE")).call(digest);
	}
	public static Function2<Object, Object, Boolean> HASH_JAVA_NATIVE(String digest) {
		return ((Closure<Function2<Object, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("HASH_JAVA_NATIVE")).call(digest);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeCertutil(File exe, File file, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeCertutil")).call(exe, file, digest);
	}
	public static byte[] checksumExeCertutil(File exe, File file, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("checksumExeCertutil")).call(exe, file, digest);
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_CERTUTIL(File exe, String digest) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_CERTUTIL")).call(exe, digest);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_CERTUTIL(File exe, String digest) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("HASH_EXE_CERTUTIL")).call(exe, digest);
	}
	public static void __INTERNAL_Gradle$Strategies$HashUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_construct")).call();
	}
	public static String __INTERNAL_Gradle$Strategies$HashUtils_getHashExpected(Object obj) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_getHashExpected")).call(obj);
	}
	public static String getHashExpected(Object obj) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("getHashExpected")).call(obj);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.HashUtils> get__INTERNAL_Gradle$Strategies$HashUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.HashUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_cache");
	}
	public static byte[] __INTERNAL_Gradle$Strategies$HashUtils_checksumExeOpenssl(File exe, File file, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_checksumExeOpenssl")).call(exe, file, digest);
	}
	public static byte[] checksumExeOpenssl(File exe, File file, String digest) {
		return ((Closure<byte[]>) ((GroovyObject) getContext().getThat()).getProperty("checksumExeOpenssl")).call(exe, file, digest);
	}
	public static Function2<File, Object, Boolean> __INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_MDNSUM(File exe, int N) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_HASH_EXE_MDNSUM")).call(exe, N);
	}
	public static Function2<File, Object, Boolean> HASH_EXE_MDNSUM(File exe, int N) {
		return ((Closure<Function2<File, Object, Boolean>>) ((GroovyObject) getContext().getThat()).getProperty("HASH_EXE_MDNSUM")).call(exe, N);
	}
	public static int __INTERNAL_Gradle$Strategies$HashUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$HashUtils_hashCode")).call();
	}
}
