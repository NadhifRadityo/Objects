package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.HashMap;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class PersistentMemory {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$PersistentMemory", "std$PersistentMemory");
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
	public static String __INTERNAL_Gradle$Strategies$PersistentMemory_toString() {
		return PersistentMemory.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_toString").call();
	}
	public static Gradle.Strategies.PersistentMemory.Memory __INTERNAL_Gradle$Strategies$PersistentMemory_persistentMemory() {
		return PersistentMemory.<Closure<Gradle.Strategies.PersistentMemory.Memory>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_persistentMemory").call();
	}
	public static Gradle.Strategies.PersistentMemory.Memory __INTERNAL_Gradle$Strategies$PersistentMemory_persistentMemory(String id) {
		return PersistentMemory.<Closure<Gradle.Strategies.PersistentMemory.Memory>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_persistentMemory").call(id);
	}
	public static Gradle.Strategies.PersistentMemory.Memory persistentMemory() {
		return PersistentMemory.<Closure<Gradle.Strategies.PersistentMemory.Memory>>__UTILS_GET_PROPERTY__("persistentMemory").call();
	}
	public static Gradle.Strategies.PersistentMemory.Memory persistentMemory(String id) {
		return PersistentMemory.<Closure<Gradle.Strategies.PersistentMemory.Memory>>__UTILS_GET_PROPERTY__("persistentMemory").call(id);
	}
	public static void __INTERNAL_Gradle$Strategies$PersistentMemory_construct() {
		PersistentMemory.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$PersistentMemory_destruct() {
		PersistentMemory.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_destruct").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$PersistentMemory_equals(Object other) {
		return PersistentMemory.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_equals").call(other);
	}
	public static HashMap<String, Gradle.Strategies.PersistentMemory.Memory> get__INTERNAL_Gradle$Strategies$PersistentMemory_memories() {
		return PersistentMemory.<HashMap<String, Gradle.Strategies.PersistentMemory.Memory>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_memories");
	}
	public static int __INTERNAL_Gradle$Strategies$PersistentMemory_hashCode() {
		return PersistentMemory.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_hashCode").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.PersistentMemory> get__INTERNAL_Gradle$Strategies$PersistentMemory_cache() {
		return PersistentMemory.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.PersistentMemory>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$PersistentMemory_cache");
	}
}
