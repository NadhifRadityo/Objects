package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import org.w3c.dom.Document;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class XMLUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$XMLUtils", "std$XMLUtils");
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
	public static Document __INTERNAL_Gradle$Strategies$XMLUtils_newXMLDocument(Object obj0) {
		return XMLUtils.<Closure<Document>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_newXMLDocument").call(obj0);
	}
	public static Document newXMLDocument(Object obj0) {
		return XMLUtils.<Closure<Document>>__UTILS_GET_PROPERTY__("newXMLDocument").call(obj0);
	}
	public static boolean __INTERNAL_Gradle$Strategies$XMLUtils_equals(Object other) {
		return XMLUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_equals").call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$XMLUtils_toString() {
		return XMLUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_toString").call();
	}
	public static int __INTERNAL_Gradle$Strategies$XMLUtils_hashCode() {
		return XMLUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_hashCode").call();
	}
	public static void __INTERNAL_Gradle$Strategies$XMLUtils_construct() {
		XMLUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_construct").call();
	}
	public static String __INTERNAL_Gradle$Strategies$XMLUtils_createXMLFile(Object obj, File target) {
		return XMLUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_createXMLFile").call(obj, target);
	}
	public static String createXMLFile(Object obj, File target) {
		return XMLUtils.<Closure<String>>__UTILS_GET_PROPERTY__("createXMLFile").call(obj, target);
	}
	public static void __INTERNAL_Gradle$Strategies$XMLUtils_destruct() {
		XMLUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_destruct").call();
	}
	public static String __INTERNAL_Gradle$Strategies$XMLUtils_XMLToString(Object obj) {
		return XMLUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_XMLToString").call(obj);
	}
	public static String xMLToString(Object obj) {
		return XMLUtils.<Closure<String>>__UTILS_GET_PROPERTY__("xMLToString").call(obj);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.XMLUtils> get__INTERNAL_Gradle$Strategies$XMLUtils_cache() {
		return XMLUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.XMLUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$XMLUtils_cache");
	}
}
