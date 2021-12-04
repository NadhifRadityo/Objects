package Strategies

import DynamicScripting.Scripting.addInjectScript
import DynamicScripting.Scripting.removeInjectScript
import GroovyKotlinInteroperability.ExportGradle
import GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GroovyKotlinInteroperability.GroovyKotlinCache
import Strategies.UnsafeUtils.unsafe
import org.apache.commons.lang3.SystemUtils
import java.lang.management.ManagementFactory
import java.nio.ByteOrder
import java.util.*
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

}
