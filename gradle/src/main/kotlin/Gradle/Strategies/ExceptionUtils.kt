package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import java.io.IOException
import java.io.InterruptedIOException
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.channels.ClosedByInterruptException

object ExceptionUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ExceptionUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(ExceptionUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic
	fun throwableToString(throwable: Throwable): String {
		try {
			StringWriter().use { stringWriter ->
				PrintWriter(stringWriter).use { printWriter ->
					throwable.printStackTrace(printWriter)
					return stringWriter.toString()
				}
			}
		} catch(e: IOException) { throw Error(exception(e)) }
	}

	@ExportGradle @JvmStatic
	fun <E: Throwable?> exception(e: E): E {
		if(e is InterruptedException) throw MustNotCatchThisError(e)
		if(e is ClosedByInterruptException) throw MustNotCatchThisError(e)
		if(e is InterruptedIOException) throw MustNotCatchThisError(e)
		return e
	}

	private class MustNotCatchThisError(e: Throwable?): Error(e) {
		init {
			LoggerUtils.lerror(e)
		}
	}
}
