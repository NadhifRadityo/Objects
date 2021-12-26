package Gradle.Strategies

import Gradle.Common.groovyKotlinCaches
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.ExceptionUtils.throwableToString
import Gradle.Strategies.GradleUtils.asService
import org.gradle.api.logging.LogLevel
import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutputFactory

object LoggerUtils {
	@JvmStatic private var cache: GroovyKotlinCache<LoggerUtils>? = null
	@JvmStatic private var factory: StyledTextOutputFactory? = null
	@JvmStatic private var instances: MutableMap<Int, StyledTextOutput>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(LoggerUtils)
		groovyKotlinCaches += cache!!
		factory = asService(StyledTextOutputFactory::class.java)
		instances = HashMap()
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
		factory = null
		instances = null
	}

	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerCreate(identifier: Any? = null, loggerCategory: String?) {
		val instance = factory!!.create(loggerCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerCreate(identifier: Any? = null, loggerCategory: String?, logLevel: LogLevel?) {
		val instance = factory!!.create(loggerCategory, logLevel)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerCreate(identifier: Any? = null, loggerCategory: Class<*>?) {
		val instance = factory!!.create(loggerCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerCreate(identifier: Any? = null, loggerCategory: Class<*>?, logLevel: LogLevel?) {
		val instance = factory!!.create(loggerCategory, logLevel)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerDestroy(identifier: Any? = null) {
		instances!!.remove(System.identityHashCode(identifier))
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerInstance(identifier: Any? = null): StyledTextOutput? {
		return instances!![System.identityHashCode(identifier)]
	}

	@JvmStatic @JvmOverloads
	fun __on_print(identifier: Any? = null, callback: (StyledTextOutput) -> Unit) {
		val instance = loggerInstance(identifier)
		if(instance != null) { callback(instance); return }
		loggerCreate(identifier, identifier?.toString() ?: "default")
		callback(loggerInstance(identifier)!!)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerAppend(identifier: Any? = null, c: Char) {
		__on_print(identifier) { it.append(c) }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerAppend(identifier: Any? = null, csq: CharSequence?) {
		__on_print(identifier) { it.append(csq) }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerAppend(identifier: Any? = null, csq: CharSequence?, start: Int, end: Int) {
		__on_print(identifier) { it.append(csq, start, end) }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerStyle(identifier: Any? = null, style: StyledTextOutput.Style?) {
		__on_print(identifier) { it.style(style) }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerText(identifier: Any? = null, text: Any?) {
		__on_print(identifier) { it.text(text) }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerPrintln(identifier: Any? = null, text: Any? = null) {
		__on_print(identifier) { if(text != null) it.println(text) else it.println() }
	}
//	@GroovyKotlinInteroperability.ExportGradle
//	@JvmStatic @JvmOverloads
//	fun println(identifier: Any? = null, text: Any?) {
//		__on_print(identifier) { it.println(text) }
//	}
//	@GroovyKotlinInteroperability.ExportGradle
//	@JvmStatic @JvmOverloads
//	fun println(identifier: Any? = null) {
//		__on_print(identifier) { it.println() }
//	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerFormat(identifier: Any? = null, pattern: String, vararg args: Any?) {
		__on_print(identifier) { it.format(pattern, args); }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerFormatln(identifier: Any? = null, pattern: String, vararg args: Any?) {
		__on_print(identifier) { it.formatln(pattern, args); }
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun loggerException(identifier: Any? = null, throwable: Throwable?) {
		__on_print(identifier) { it.exception(throwable); }
	}

	@ExportGradle @JvmStatic
	val __escapeCodes: Map<String, String> =
		("fblack|30 fred|31 fgreen|32 fyellow|33 fblue|34 fmagenta|35 fcyan|36 fwhite|37 fbblack|90 fbred|91 fbgreen|92 fbyellow|93 fbblue|94 fbmagenta|95 fbcyan|96 fbwhite|97 freset|39 " +
		 "bblack|40 bred|41 bgreen|42 byellow|43 bblue|44 bmagenta|45 bcyan|46 bwhite|47 bbblack|100 bbred|101 bbgreen|102 bbyellow|103 bbblue|104 bbmagenta|105 bbcyan|106 bbwhite|107 breset|49")
			.split(' ').map { it.split('|') }.associateBy({ it[0] }, { "\u001b[${it[1]}m" })
	@JvmStatic val __injectLog: Array<Any?> = arrayOf("fbblue", "[LOG] ")
	@JvmStatic val __injectInfo: Array<Any?> = arrayOf("fbgreen", "[INFO] ")
	@JvmStatic val __injectDebug: Array<Any?> = arrayOf("fbmagenta", "[DEBUG] ")
	@JvmStatic val __injectWarn: Array<Any?> = arrayOf("fyellow", "[WARN] ")
	@JvmStatic val __injectError: Array<Any?> = arrayOf("fred", "[ERROR] ")

	@JvmStatic
	fun __identify_object(obj: Any?): Any? {
		return when(obj) {
			is Throwable -> throwableToString(obj)
			else -> obj
		}
	}
	@JvmStatic
	fun __println_impl(vararg args0: Any?) {
		val args: Array<Any?> = arrayOf(*args0)
		for(i in args.indices) {
			val arg = args[i]
			val identifiedArg = __identify_object(arg)
			val escapeCode = __escapeCodes[identifiedArg]
			args[i] = escapeCode ?: identifiedArg
		}
		var compiledString = args.map { it.toString() }.joinToString("")
		compiledString += __escapeCodes["freset"] + __escapeCodes["breset"]
		if(factory == null) { println(compiledString); return }
		loggerPrintln(null, compiledString)
	}
	@JvmStatic
	fun __inject_additional(inject: Array<Any?>, args: Array<out Any?>): Array<Any?> {
		val newArgs = arrayOfNulls<Any>(args.size + inject.size)
		inject.copyInto(newArgs, 0, 0)
		args.copyInto(newArgs, inject.size, 0)
		return newArgs
	}
	@ExportGradle @JvmStatic fun llog(vararg args: Any?) { __println_impl(*__inject_additional(__injectLog, args)) }
	@ExportGradle @JvmStatic fun linfo(vararg args: Any?) { __println_impl(*__inject_additional(__injectInfo, args)) }
	@ExportGradle @JvmStatic fun ldebug(vararg args: Any?) { __println_impl(*__inject_additional(__injectDebug, args)) }
	@ExportGradle @JvmStatic fun lwarn(vararg args: Any?) { __println_impl(*__inject_additional(__injectWarn, args)) }
	@ExportGradle @JvmStatic fun lerror(vararg args: Any?) { __println_impl(*__inject_additional(__injectError, args)) }

	@ExportGradle @JvmStatic fun _llog(format: String, vararg args: Any?) { llog(String.format(format, *args)) }
	@ExportGradle @JvmStatic fun _linfo(format: String, vararg args: Any?) { linfo(String.format(format, *args)) }
	@ExportGradle @JvmStatic fun _ldebug(format: String, vararg args: Any?) { ldebug(String.format(format, *args)) }
	@ExportGradle @JvmStatic fun _lwarn(format: String, vararg args: Any?) { lwarn(String.format(format, *args)) }
	@ExportGradle @JvmStatic fun _lerror(format: String, vararg args: Any?) { lerror(String.format(format, *args)) }
}
