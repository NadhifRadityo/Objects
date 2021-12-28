package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.UnsafeUtils.unsafe
import org.apache.commons.lang3.SystemUtils
import java.io.File
import java.lang.management.ManagementFactory
import java.nio.ByteOrder
import java.util.*
import java.util.function.Function
import java.util.regex.Pattern

object RuntimeUtils {
	@JvmStatic private var cache: GroovyKotlinCache<RuntimeUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(RuntimeUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic val vmArguments = Collections.unmodifiableList(ManagementFactory.getRuntimeMXBean().inputArguments)
	@ExportGradle const val OS_WINDOWS = "windows"
	@ExportGradle const val OS_OSX = "osx"
	@ExportGradle const val OS_SOLARIS = "solaris"
	@ExportGradle const val OS_LINUX = "linux"
	@ExportGradle const val ARCH_X86_32 = "x86_32"
	@ExportGradle const val ARCH_X86_64 = "x86_64"
	@ExportGradle const val ARCH_PPC = "ppc"

	@ExportGradle @JvmStatic val OS_DETECTION_NAME: String = if(SystemUtils.IS_OS_WINDOWS) OS_WINDOWS
		else if(SystemUtils.IS_OS_MAC_OSX) OS_OSX
		else if(SystemUtils.IS_OS_SOLARIS) OS_SOLARIS
		else if(SystemUtils.IS_OS_LINUX) OS_LINUX
		else throw IllegalArgumentException("Unknown operating system ${SystemUtils.OS_NAME}")
	@ExportGradle @JvmStatic val OS_DETECTION_ARCH: String = when(SystemUtils.OS_ARCH) {
		"x86", "i386", "i486", "i586", "i686" -> ARCH_X86_32
		"x86_64", "amd64" -> ARCH_X86_64
		"powerpc" -> ARCH_PPC
		else -> throw IllegalArgumentException("Unknown architecture ${SystemUtils.OS_ARCH}")
	}
	@ExportGradle @JvmStatic val IS_OS_32BIT: Boolean = OS_DETECTION_ARCH == ARCH_X86_32
	@ExportGradle @JvmStatic val IS_OS_64BIT: Boolean = OS_DETECTION_ARCH == ARCH_X86_64
	@ExportGradle @JvmStatic val IS_OS_BIG_ENDIAN: Boolean = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN
	@ExportGradle @JvmStatic val IS_OS_LITTLE_ENDIAN: Boolean = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN
	@ExportGradle @JvmStatic val JAVA_DETECTION_VERSION: Int = run {
		val matcher = Pattern.compile("(1\\.|)([0-9]+)").matcher(SystemUtils.JAVA_VERSION)
		require(matcher.find()) { "Unknown version ${SystemUtils.JAVA_VERSION}" }
		matcher.group(2).toInt()
	}
	@ExportGradle @JvmStatic val IS_JAVA_32BIT: Boolean = unsafe.addressSize() == 4
	@ExportGradle @JvmStatic val IS_JAVA_64BIT: Boolean = unsafe.addressSize() == 8

	@ExportGradle @JvmStatic @JvmOverloads fun <T> env_getObject(key: String, converter: Function<String, T>, defaultValue: T? = null): T? { val value = System.getenv()[key]; return if(value != null) converter.apply(value) else defaultValue }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getString(key: String, defaultValue: String? = null): String? { return env_getObject(key, { s -> s }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getFile(key: String, defaultValue: File? = null): File? { return env_getObject(key, { s -> file(s) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getFiles(key: String, defaultValue: Array<File>? = null): Array<File>? { return env_getObject(key, { s -> s.split(";").map { file(it) }.toTypedArray() }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getByte(key: String, defaultValue: Byte = 0.toByte()): Byte { return env_getObject(key, { s -> java.lang.Byte.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getBoolean(key: String, defaultValue: Boolean = false): Boolean { return env_getObject(key, { s -> java.lang.Boolean.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getChar(key: String, defaultValue: Char = 0.toChar()): Char { return env_getObject(key, { s -> s[0] }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getShort(key: String, defaultValue: Short = 0.toShort()): Short { return env_getObject(key, { s -> java.lang.Short.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getInt(key: String, defaultValue: Int = 0): Int { return env_getObject(key, { s -> java.lang.Integer.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getLong(key: String, defaultValue: Long = 0): Long { return env_getObject(key, { s -> java.lang.Long.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getFloat(key: String, defaultValue: Float = 0f): Float { return env_getObject(key, { s -> java.lang.Float.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun env_getDouble(key: String, defaultValue: Double = 0.0): Double { return env_getObject(key, { s -> java.lang.Double.valueOf(s) }, defaultValue)!! }
}
