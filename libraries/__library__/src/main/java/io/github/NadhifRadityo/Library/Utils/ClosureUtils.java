package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class ClosureUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$ClosureUtils", "std$ClosureUtils");
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
	public static void __INTERNAL_Gradle$Strategies$ClosureUtils_destruct() {
		ClosureUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_destruct").call();
	}
	public static int __INTERNAL_Gradle$Strategies$ClosureUtils_hashCode() {
		return ClosureUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_hashCode").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ClosureUtils> get__INTERNAL_Gradle$Strategies$ClosureUtils_cache() {
		return ClosureUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ClosureUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_cache");
	}
	public static Gradle.GroovyKotlinInteroperability.KotlinClosure __INTERNAL_Gradle$Strategies$ClosureUtils_bind(Object self, Gradle.GroovyKotlinInteroperability.KotlinClosure closure, Object... prependArgs) {
		return ClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_bind").call(self, closure, prependArgs);
	}
	public static Gradle.GroovyKotlinInteroperability.KotlinClosure bind(Object self, Gradle.GroovyKotlinInteroperability.KotlinClosure closure, Object... prependArgs) {
		return ClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure>>__UTILS_GET_PROPERTY__("bind").call(self, closure, prependArgs);
	}
	public static String __INTERNAL_Gradle$Strategies$ClosureUtils_toString() {
		return ClosureUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ClosureUtils_construct() {
		ClosureUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_construct").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$ClosureUtils_equals(Object other) {
		return ClosureUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_equals").call(other);
	}
	public static Gradle.GroovyKotlinInteroperability.KotlinClosure __INTERNAL_Gradle$Strategies$ClosureUtils_lash(Gradle.GroovyKotlinInteroperability.KotlinClosure closure) {
		return ClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_lash").call(closure);
	}
	public static Gradle.GroovyKotlinInteroperability.KotlinClosure __INTERNAL_Gradle$Strategies$ClosureUtils_lash(Object that, Gradle.GroovyKotlinInteroperability.KotlinClosure closure, Object... prependArgs) {
		return ClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClosureUtils_lash").call(that, closure, prependArgs);
	}
	public static Gradle.GroovyKotlinInteroperability.KotlinClosure lash(Gradle.GroovyKotlinInteroperability.KotlinClosure closure) {
		return ClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure>>__UTILS_GET_PROPERTY__("lash").call(closure);
	}
	public static Gradle.GroovyKotlinInteroperability.KotlinClosure lash(Object that, Gradle.GroovyKotlinInteroperability.KotlinClosure closure, Object... prependArgs) {
		return ClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure>>__UTILS_GET_PROPERTY__("lash").call(that, closure, prependArgs);
	}
}
