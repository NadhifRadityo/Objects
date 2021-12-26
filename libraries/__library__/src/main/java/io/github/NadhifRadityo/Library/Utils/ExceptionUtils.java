package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class ExceptionUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$ExceptionUtils", "std$ExceptionUtils");
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
	public static <E extends Throwable> E __INTERNAL_Gradle$Strategies$ExceptionUtils_exception(E e) {
		return ExceptionUtils.<Closure<E>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_exception").call(e);
	}
	public static <E extends Throwable> E exception(E e) {
		return ExceptionUtils.<Closure<E>>__UTILS_GET_PROPERTY__("exception").call(e);
	}
	public static void __INTERNAL_Gradle$Strategies$ExceptionUtils_construct() {
		ExceptionUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_construct").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$ExceptionUtils_equals(Object other) {
		return ExceptionUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_equals").call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$ExceptionUtils_destruct() {
		ExceptionUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_destruct").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ExceptionUtils> get__INTERNAL_Gradle$Strategies$ExceptionUtils_cache() {
		return ExceptionUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ExceptionUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_cache");
	}
	public static String __INTERNAL_Gradle$Strategies$ExceptionUtils_throwableToString(Throwable throwable) {
		return ExceptionUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_throwableToString").call(throwable);
	}
	public static String throwableToString(Throwable throwable) {
		return ExceptionUtils.<Closure<String>>__UTILS_GET_PROPERTY__("throwableToString").call(throwable);
	}
	public static String __INTERNAL_Gradle$Strategies$ExceptionUtils_toString() {
		return ExceptionUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_toString").call();
	}
	public static int __INTERNAL_Gradle$Strategies$ExceptionUtils_hashCode() {
		return ExceptionUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ExceptionUtils_hashCode").call();
	}
}
