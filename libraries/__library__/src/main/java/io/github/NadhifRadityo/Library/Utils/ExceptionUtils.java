package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class ExceptionUtils {
	public static <E extends Throwable> E __INTERNAL_Gradle$Strategies$ExceptionUtils_exception(E e) {
		return ((Closure<E>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_exception")).call(e);
	}
	public static <E extends Throwable> E exception(E e) {
		return ((Closure<E>) ((GroovyObject) getContext().getThat()).getProperty("exception")).call(e);
	}
	public static void __INTERNAL_Gradle$Strategies$ExceptionUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_construct")).call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$ExceptionUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_equals")).call(other);
	}
	public static void __INTERNAL_Gradle$Strategies$ExceptionUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_destruct")).call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ExceptionUtils> get__INTERNAL_Gradle$Strategies$ExceptionUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ExceptionUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_cache");
	}
	public static String __INTERNAL_Gradle$Strategies$ExceptionUtils_throwableToString(Throwable throwable) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_throwableToString")).call(throwable);
	}
	public static String throwableToString(Throwable throwable) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("throwableToString")).call(throwable);
	}
	public static String __INTERNAL_Gradle$Strategies$ExceptionUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_toString")).call();
	}
	public static int __INTERNAL_Gradle$Strategies$ExceptionUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ExceptionUtils_hashCode")).call();
	}
}
