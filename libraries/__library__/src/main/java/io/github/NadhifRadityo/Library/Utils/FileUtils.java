package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import java.nio.charset.Charset;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class FileUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$FileUtils", "std$FileUtils");
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
	public static void __INTERNAL_Gradle$Strategies$FileUtils_delFile0(File file) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_delFile0").call(file);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileString(File file, Charset charset) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileString").call(file, charset);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileString(File file) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileString").call(file);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileString(String path, Charset charset) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileString").call(path, charset);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileString(String path) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileString").call(path);
	}
	public static String fileString(File file, Charset charset) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileString").call(file, charset);
	}
	public static String fileString(File file) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileString").call(file);
	}
	public static String fileString(String path, Charset charset) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileString").call(path, charset);
	}
	public static String fileString(String path) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileString").call(path);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_fileRelative(File from, File what) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileRelative").call(from, what);
	}
	public static File fileRelative(File from, File what) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("fileRelative").call(from, what);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_writeFileString(File file, String string, Charset charset) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_writeFileString").call(file, string, charset);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_writeFileString(File file, String string) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_writeFileString").call(file, string);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_writeFileString(String path, String string, Charset charset) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_writeFileString").call(path, string, charset);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_writeFileString(String path, String string) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_writeFileString").call(path, string);
	}
	public static void writeFileString(File file, String string, Charset charset) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeFileString").call(file, string, charset);
	}
	public static void writeFileString(File file, String string) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeFileString").call(file, string);
	}
	public static void writeFileString(String path, String string, Charset charset) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeFileString").call(path, string, charset);
	}
	public static void writeFileString(String path, String string) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeFileString").call(path, string);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_toString() {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_destruct() {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_destruct").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.FileUtils> get__INTERNAL_Gradle$Strategies$FileUtils_cache() {
		return FileUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.FileUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_cache");
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_writeFileBytes(File file, byte[] bytes, int off, int len) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_writeFileBytes").call(file, bytes, off, len);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_writeFileBytes(String path, byte[] bytes, int off, int len) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_writeFileBytes").call(path, bytes, off, len);
	}
	public static void writeFileBytes(File file, byte[] bytes, int off, int len) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeFileBytes").call(file, bytes, off, len);
	}
	public static void writeFileBytes(String path, byte[] bytes, int off, int len) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("writeFileBytes").call(path, bytes, off, len);
	}
	public static boolean __INTERNAL_Gradle$Strategies$FileUtils_equals(Object other) {
		return FileUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_equals").call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_construct() {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_construct").call();
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileName(File file) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileName").call(file);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileName(String fileName) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileName").call(fileName);
	}
	public static String fileName(File file) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileName").call(file);
	}
	public static String fileName(String fileName) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileName").call(fileName);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_mkdir(File parent, String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_mkdir").call(parent, children);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_mkdir(String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_mkdir").call(children);
	}
	public static File mkdir(File parent, String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("mkdir").call(parent, children);
	}
	public static File mkdir(String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("mkdir").call(children);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_mkfile(File parent, String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_mkfile").call(parent, children);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_mkfile(String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_mkfile").call(children);
	}
	public static File mkfile(File parent, String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("mkfile").call(parent, children);
	}
	public static File mkfile(String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("mkfile").call(children);
	}
	public static void __INTERNAL_Gradle$Strategies$FileUtils_delFile(File file) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_delFile").call(file);
	}
	public static void delFile(File file) {
		FileUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("delFile").call(file);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_file(File parent, String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_file").call(parent, children);
	}
	public static File __INTERNAL_Gradle$Strategies$FileUtils_file(String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_file").call(children);
	}
	public static File file(File parent, String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("file").call(parent, children);
	}
	public static File file(String... children) {
		return FileUtils.<Closure<File>>__UTILS_GET_PROPERTY__("file").call(children);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$FileUtils_fileBytes(File file) {
		return FileUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileBytes").call(file);
	}
	public static byte[] __INTERNAL_Gradle$Strategies$FileUtils_fileBytes(String path) {
		return FileUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileBytes").call(path);
	}
	public static byte[] fileBytes(File file) {
		return FileUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("fileBytes").call(file);
	}
	public static byte[] fileBytes(String path) {
		return FileUtils.<Closure<byte[]>>__UTILS_GET_PROPERTY__("fileBytes").call(path);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileExtension(File file) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileExtension").call(file);
	}
	public static String __INTERNAL_Gradle$Strategies$FileUtils_fileExtension(String fileName) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_fileExtension").call(fileName);
	}
	public static String fileExtension(File file) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileExtension").call(file);
	}
	public static String fileExtension(String fileName) {
		return FileUtils.<Closure<String>>__UTILS_GET_PROPERTY__("fileExtension").call(fileName);
	}
	public static int __INTERNAL_Gradle$Strategies$FileUtils_hashCode() {
		return FileUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$FileUtils_hashCode").call();
	}
}
