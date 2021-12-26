package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class ClassUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$ClassUtils", "std$ClassUtils");
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
	public static <T> Class<? extends T> __INTERNAL_Gradle$Strategies$ClassUtils_classForName0(String name, boolean initialize, ClassLoader loader) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_classForName0").call(name, initialize, loader);
	}
	public static <T> Class<? extends T> __INTERNAL_Gradle$Strategies$ClassUtils_classForName0(String name, boolean initialize) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_classForName0").call(name, initialize);
	}
	public static <T> Class<? extends T> __INTERNAL_Gradle$Strategies$ClassUtils_classForName0(String name) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_classForName0").call(name);
	}
	public static <T> Class<? extends T> classForName0(String name, boolean initialize, ClassLoader loader) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("classForName0").call(name, initialize, loader);
	}
	public static <T> Class<? extends T> classForName0(String name, boolean initialize) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("classForName0").call(name, initialize);
	}
	public static <T> Class<? extends T> classForName0(String name) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("classForName0").call(name);
	}
	public static String __INTERNAL_Gradle$Strategies$ClassUtils_callerUserImplementedClass(String... notFilter) {
		return ClassUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_callerUserImplementedClass").call(notFilter);
	}
	public static String callerUserImplementedClass(String... notFilter) {
		return ClassUtils.<Closure<String>>__UTILS_GET_PROPERTY__("callerUserImplementedClass").call(notFilter);
	}
	public static File __INTERNAL_Gradle$Strategies$ClassUtils_currentClassFile(Class<?> clazz) {
		return ClassUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_currentClassFile").call(clazz);
	}
	public static File __INTERNAL_Gradle$Strategies$ClassUtils_currentClassFile(String... notFilter) {
		return ClassUtils.<Closure<File>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_currentClassFile").call(notFilter);
	}
	public static File currentClassFile(Class<?> clazz) {
		return ClassUtils.<Closure<File>>__UTILS_GET_PROPERTY__("currentClassFile").call(clazz);
	}
	public static File currentClassFile(String... notFilter) {
		return ClassUtils.<Closure<File>>__UTILS_GET_PROPERTY__("currentClassFile").call(notFilter);
	}
	public static void __INTERNAL_Gradle$Strategies$ClassUtils_overrideFinal(Object obj, Field field, Object newValue) {
		ClassUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_overrideFinal").call(obj, field, newValue);
	}
	public static void overrideFinal(Object obj, Field field, Object newValue) {
		ClassUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("overrideFinal").call(obj, field, newValue);
	}
	public static int __INTERNAL_Gradle$Strategies$ClassUtils_hashCode() {
		return ClassUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_hashCode").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ClassUtils_construct() {
		ClassUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_construct").call();
	}
	public static String __INTERNAL_Gradle$Strategies$ClassUtils_toString() {
		return ClassUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_toString").call();
	}
	public static <T> Class<? extends T> __INTERNAL_Gradle$Strategies$ClassUtils_classForName(String name, boolean initialize, ClassLoader loader) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_classForName").call(name, initialize, loader);
	}
	public static <T> Class<? extends T> __INTERNAL_Gradle$Strategies$ClassUtils_classForName(String name, boolean initialize) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_classForName").call(name, initialize);
	}
	public static <T> Class<? extends T> __INTERNAL_Gradle$Strategies$ClassUtils_classForName(String name) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_classForName").call(name);
	}
	public static <T> Class<? extends T> classForName(String name, boolean initialize, ClassLoader loader) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("classForName").call(name, initialize, loader);
	}
	public static <T> Class<? extends T> classForName(String name, boolean initialize) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("classForName").call(name, initialize);
	}
	public static <T> Class<? extends T> classForName(String name) {
		return ClassUtils.<Closure<Class<? extends T>>>__UTILS_GET_PROPERTY__("classForName").call(name);
	}
	public static Map<Class<?>, Class<?>> get__INTERNAL_Gradle$Strategies$ClassUtils_primitiveToBoxed() {
		return ClassUtils.<Map<Class<?>, Class<?>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_primitiveToBoxed");
	}
	public static Map<Class<?>, Class<?>> getPrimitiveToBoxed() {
		return ClassUtils.<Map<Class<?>, Class<?>>>__UTILS_GET_PROPERTY__("primitiveToBoxed");
	}
	public static Map<Class<?>, Class<?>> get__INTERNAL_Gradle$Strategies$ClassUtils_boxedToPrimitive() {
		return ClassUtils.<Map<Class<?>, Class<?>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_boxedToPrimitive");
	}
	public static Map<Class<?>, Class<?>> getBoxedToPrimitive() {
		return ClassUtils.<Map<Class<?>, Class<?>>>__UTILS_GET_PROPERTY__("boxedToPrimitive");
	}
	public static MetaClass __INTERNAL_Gradle$Strategies$ClassUtils_metaClassFor(Class<?> clazz) {
		return ClassUtils.<Closure<MetaClass>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_metaClassFor").call(clazz);
	}
	public static MetaClass __INTERNAL_Gradle$Strategies$ClassUtils_metaClassFor(Object obj) {
		return ClassUtils.<Closure<MetaClass>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_metaClassFor").call(obj);
	}
	public static MetaClass metaClassFor(Class<?> clazz) {
		return ClassUtils.<Closure<MetaClass>>__UTILS_GET_PROPERTY__("metaClassFor").call(clazz);
	}
	public static MetaClass metaClassFor(Object obj) {
		return ClassUtils.<Closure<MetaClass>>__UTILS_GET_PROPERTY__("metaClassFor").call(obj);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ClassUtils> get__INTERNAL_Gradle$Strategies$ClassUtils_cache() {
		return ClassUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ClassUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_cache");
	}
	public static ClassLoader get__INTERNAL_Gradle$Strategies$ClassUtils_defaultClassLoader() {
		return ClassUtils.<ClassLoader>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_defaultClassLoader");
	}
	public static ClassLoader getDefaultClassLoader() {
		return ClassUtils.<ClassLoader>__UTILS_GET_PROPERTY__("defaultClassLoader");
	}
	public static List<String> get__INTERNAL_Gradle$Strategies$ClassUtils_defaultNotPackageFilter() {
		return ClassUtils.<List<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_defaultNotPackageFilter");
	}
	public static boolean __INTERNAL_Gradle$Strategies$ClassUtils_equals(Object other) {
		return ClassUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_equals").call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$ClassUtils_destruct() {
		ClassUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ClassUtils_destruct").call();
	}
}
