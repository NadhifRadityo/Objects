package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.List;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class RuntimeUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$RuntimeUtils", "std$RuntimeUtils");
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
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_SOLARIS() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_SOLARIS");
	}
	public static String getOS_SOLARIS() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("OS_SOLARIS");
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_WINDOWS() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_WINDOWS");
	}
	public static String getOS_WINDOWS() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("OS_WINDOWS");
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_OSX() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_OSX");
	}
	public static String getOS_OSX() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("OS_OSX");
	}
	public static String __INTERNAL_Gradle$Strategies$RuntimeUtils_toString() {
		return RuntimeUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_toString").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$RuntimeUtils_equals(Object other) {
		return RuntimeUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_equals").call(other);
	}
	public static boolean get__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_JAVA_32BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_JAVA_32BIT");
	}
	public static boolean getIS_JAVA_32BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("IS_JAVA_32BIT");
	}
	public static boolean get__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_JAVA_64BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_JAVA_64BIT");
	}
	public static boolean getIS_JAVA_64BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("IS_JAVA_64BIT");
	}
	public static boolean get__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_LITTLE_ENDIAN() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_LITTLE_ENDIAN");
	}
	public static boolean getIS_OS_LITTLE_ENDIAN() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("IS_OS_LITTLE_ENDIAN");
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_LINUX() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_LINUX");
	}
	public static String getOS_LINUX() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("OS_LINUX");
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_ARCH_PPC() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_ARCH_PPC");
	}
	public static String getARCH_PPC() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("ARCH_PPC");
	}
	public static boolean get__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_32BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_32BIT");
	}
	public static boolean getIS_OS_32BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("IS_OS_32BIT");
	}
	public static void __INTERNAL_Gradle$Strategies$RuntimeUtils_construct() {
		RuntimeUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_construct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$RuntimeUtils_destruct() {
		RuntimeUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_destruct").call();
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_ARCH_X86_64() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_ARCH_X86_64");
	}
	public static String getARCH_X86_64() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("ARCH_X86_64");
	}
	public static int __INTERNAL_Gradle$Strategies$RuntimeUtils_hashCode() {
		return RuntimeUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_hashCode").call();
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_ARCH_X86_32() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_ARCH_X86_32");
	}
	public static String getARCH_X86_32() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("ARCH_X86_32");
	}
	public static boolean get__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_BIG_ENDIAN() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_BIG_ENDIAN");
	}
	public static boolean getIS_OS_BIG_ENDIAN() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("IS_OS_BIG_ENDIAN");
	}
	public static int get__INTERNAL_Gradle$Strategies$RuntimeUtils_JAVA_DETECTION_VERSION() {
		return RuntimeUtils.<Integer>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_JAVA_DETECTION_VERSION");
	}
	public static int getJAVA_DETECTION_VERSION() {
		return RuntimeUtils.<Integer>__UTILS_GET_PROPERTY__("JAVA_DETECTION_VERSION");
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.RuntimeUtils> get__INTERNAL_Gradle$Strategies$RuntimeUtils_cache() {
		return RuntimeUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.RuntimeUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_cache");
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_DETECTION_ARCH() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_DETECTION_ARCH");
	}
	public static String getOS_DETECTION_ARCH() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("OS_DETECTION_ARCH");
	}
	public static String get__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_DETECTION_NAME() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_OS_DETECTION_NAME");
	}
	public static String getOS_DETECTION_NAME() {
		return RuntimeUtils.<String>__UTILS_GET_PROPERTY__("OS_DETECTION_NAME");
	}
	public static List<String> get__INTERNAL_Gradle$Strategies$RuntimeUtils_vmArguments() {
		return RuntimeUtils.<List<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_vmArguments");
	}
	public static List<String> getVmArguments() {
		return RuntimeUtils.<List<String>>__UTILS_GET_PROPERTY__("vmArguments");
	}
	public static boolean get__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_64BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$RuntimeUtils_IS_OS_64BIT");
	}
	public static boolean getIS_OS_64BIT() {
		return RuntimeUtils.<Boolean>__UTILS_GET_PROPERTY__("IS_OS_64BIT");
	}
}
