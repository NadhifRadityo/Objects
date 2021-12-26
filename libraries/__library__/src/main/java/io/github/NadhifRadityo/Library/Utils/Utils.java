package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class Utils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$Utils", "std$Utils");
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
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.Utils> get__INTERNAL_Gradle$Strategies$Utils_cache() {
		return Utils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.Utils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils_cache");
	}
	public static <T> T __INTERNAL_Gradle$Strategies$Utils___unimplemented() {
		return Utils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils___unimplemented").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$Utils_equals(Object other) {
		return Utils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils_equals").call(other);
	}
	public static int __INTERNAL_Gradle$Strategies$Utils_hashCode() {
		return Utils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils_hashCode").call();
	}
	public static String __INTERNAL_Gradle$Strategies$Utils_toString() {
		return Utils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils_toString").call();
	}
	public static Throwable __INTERNAL_Gradle$Strategies$Utils___invalid_type() {
		return Utils.<Closure<Throwable>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils___invalid_type").call();
	}
	public static void __INTERNAL_Gradle$Strategies$Utils_construct() {
		Utils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$Utils_destruct() {
		Utils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils_destruct").call();
	}
	public static Throwable __INTERNAL_Gradle$Strategies$Utils___must_not_happen() {
		return Utils.<Closure<Throwable>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$Utils___must_not_happen").call();
	}
}
