package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import org.gradle.api.Project;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class GroovyInteroperability {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$GroovyInteroperability", "std$GroovyInteroperability");
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
	public static <T> Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<T> __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_prepareGroovyKotlinCache(T obj) {
		return GroovyInteroperability.<Closure<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_prepareGroovyKotlinCache").call(obj);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_prepareGroovyKotlinCache(Object obj, Map<String, ? extends Function1<? super Object[], ?>> methods, Map<String, ? extends Function1<? super Object[], ?>> getter, Map<String, ? extends Function1<? super Object[], ?>> setter) {
		return GroovyInteroperability.<Closure<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_prepareGroovyKotlinCache").call(obj, methods, getter, setter);
	}
	public static int __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_hashCode() {
		return GroovyInteroperability.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_hashCode").call();
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_construct() {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_construct").call();
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_detachObject(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_detachObject").call(context, cache);
	}
	public static void detachObject(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("detachObject").call(context, cache);
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_attachProjectObject(Project project, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_attachProjectObject").call(project, cache);
	}
	public static void attachProjectObject(Project project, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("attachProjectObject").call(project, cache);
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_destruct() {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_destruct").call();
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_detachAnyObject(Object that, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_detachAnyObject").call(that, cache);
	}
	public static void detachAnyObject(Object that, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("detachAnyObject").call(that, cache);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.GroovyKotlinInteroperability.GroovyInteroperability> get__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_cache() {
		return GroovyInteroperability.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.GroovyKotlinInteroperability.GroovyInteroperability>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_cache");
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_detachProjectObject(Project project, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_detachProjectObject").call(project, cache);
	}
	public static void detachProjectObject(Project project, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("detachProjectObject").call(project, cache);
	}
	public static boolean __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_equals(Object other) {
		return GroovyInteroperability.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_equals").call(other);
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_attachObject(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_attachObject").call(context, cache);
	}
	public static void attachObject(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("attachObject").call(context, cache);
	}
	public static String __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_toString() {
		return GroovyInteroperability.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_toString").call();
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_attachAnyObject(Object that, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$GroovyInteroperability_attachAnyObject").call(that, cache);
	}
	public static void attachAnyObject(Object that, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		GroovyInteroperability.<Closure<Void>>__UTILS_GET_PROPERTY__("attachAnyObject").call(that, cache);
	}
}
