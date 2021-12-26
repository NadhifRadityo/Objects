package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class KeywordsUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$KeywordsUtils", "std$KeywordsUtils");
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
	public static void __INTERNAL_Gradle$Strategies$KeywordsUtils_destruct() {
		KeywordsUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_destruct").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.KeywordsUtils> get__INTERNAL_Gradle$Strategies$KeywordsUtils_cache() {
		return KeywordsUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.KeywordsUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_cache");
	}
	public static <T> Gradle.Strategies.KeywordsUtils.With<T> __INTERNAL_Gradle$Strategies$KeywordsUtils_with(T user) {
		return KeywordsUtils.<Closure<Gradle.Strategies.KeywordsUtils.With<T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_with").call(user);
	}
	public static <T> Gradle.Strategies.KeywordsUtils.With<T> with(T user) {
		return KeywordsUtils.<Closure<Gradle.Strategies.KeywordsUtils.With<T>>>__UTILS_GET_PROPERTY__("with").call(user);
	}
	public static <T> Gradle.Strategies.KeywordsUtils.From<T> __INTERNAL_Gradle$Strategies$KeywordsUtils_from(T user) {
		return KeywordsUtils.<Closure<Gradle.Strategies.KeywordsUtils.From<T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_from").call(user);
	}
	public static <T> Gradle.Strategies.KeywordsUtils.From<T> from(T user) {
		return KeywordsUtils.<Closure<Gradle.Strategies.KeywordsUtils.From<T>>>__UTILS_GET_PROPERTY__("from").call(user);
	}
	public static String __INTERNAL_Gradle$Strategies$KeywordsUtils_toString() {
		return KeywordsUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$KeywordsUtils_construct() {
		KeywordsUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_construct").call();
	}
	public static <T> Gradle.Strategies.KeywordsUtils.Being<T> __INTERNAL_Gradle$Strategies$KeywordsUtils_being(T user) {
		return KeywordsUtils.<Closure<Gradle.Strategies.KeywordsUtils.Being<T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_being").call(user);
	}
	public static <T> Gradle.Strategies.KeywordsUtils.Being<T> being(T user) {
		return KeywordsUtils.<Closure<Gradle.Strategies.KeywordsUtils.Being<T>>>__UTILS_GET_PROPERTY__("being").call(user);
	}
	public static int __INTERNAL_Gradle$Strategies$KeywordsUtils_hashCode() {
		return KeywordsUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_hashCode").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$KeywordsUtils_equals(Object other) {
		return KeywordsUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$KeywordsUtils_equals").call(other);
	}
}
