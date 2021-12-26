package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.List;
import kotlin.Pair;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class KotlinClosureUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$KotlinClosureUtils", "std$KotlinClosureUtils");
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
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_destruct() {
		KotlinClosureUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_destruct").call();
	}
	public static int __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_hashCode() {
		return KotlinClosureUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_hashCode").call();
	}
	public static void __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_construct() {
		KotlinClosureUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_construct").call();
	}
	public static Pair<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload, Object[]> __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_doOverloading(List<? extends Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload> overloads, Object... args) {
		return KotlinClosureUtils.<Closure<Pair<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload, Object[]>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_doOverloading").call(overloads, args);
	}
	public static Pair<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload, Object[]> doOverloading(List<? extends Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload> overloads, Object... args) {
		return KotlinClosureUtils.<Closure<Pair<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload, Object[]>>>__UTILS_GET_PROPERTY__("doOverloading").call(overloads, args);
	}
	public static <R> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKFunctionOverloads(Object[] owners, KFunction<? extends R> function) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKFunctionOverloads").call(owners, function);
	}
	public static <R> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] getKFunctionOverloads(Object[] owners, KFunction<? extends R> function) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("getKFunctionOverloads").call(owners, function);
	}
	public static <V> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKProperty0Overloads(KProperty0<? extends V> property) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKProperty0Overloads").call(property);
	}
	public static <V> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] getKProperty0Overloads(KProperty0<? extends V> property) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("getKProperty0Overloads").call(property);
	}
	public static String __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_toString() {
		return KotlinClosureUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_toString").call();
	}
	public static <T, V> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKProperty1Overloads(T owner, KProperty1<T, ? extends V> property) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKProperty1Overloads").call(owner, property);
	}
	public static <T, V> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] getKProperty1Overloads(T owner, KProperty1<T, ? extends V> property) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("getKProperty1Overloads").call(owner, property);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils> get__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_cache() {
		return KotlinClosureUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_cache");
	}
	public static boolean __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_equals(Object other) {
		return KotlinClosureUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_equals").call(other);
	}
	public static <D, E, V> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] __INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKProperty2Overloads(D owner1, E owner2, KProperty2<D, E, ? extends V> property) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$GroovyKotlinInteroperability$KotlinClosure$KotlinClosureUtils_getKProperty2Overloads").call(owner1, owner2, property);
	}
	public static <D, E, V> Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[] getKProperty2Overloads(D owner1, E owner2, KProperty2<D, E, ? extends V> property) {
		return KotlinClosureUtils.<Closure<Gradle.GroovyKotlinInteroperability.KotlinClosure.Overload[]>>__UTILS_GET_PROPERTY__("getKProperty2Overloads").call(owner1, owner2, property);
	}
}
