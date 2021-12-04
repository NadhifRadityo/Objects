package Strategies

import DynamicScripting.Scripting.addInjectScript
import DynamicScripting.Scripting.removeInjectScript
import GroovyKotlinInteroperability.ExportGradle
import GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GroovyKotlinInteroperability.GroovyKotlinCache
import Strategies.ExceptionUtils.exception
import Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import Strategies.UnsafeUtils.unsafe
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object ClassUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ClassUtils>? = null
	@ExportGradle @JvmStatic
	val boxedToPrimitive = mapOf(
		Int::class.javaObjectType to Int::class.javaPrimitiveType,
		Long::class.javaObjectType to Long::class.javaPrimitiveType,
		Short::class.javaObjectType to Short::class.javaPrimitiveType,
		Float::class.javaObjectType to Float::class.javaPrimitiveType,
		Double::class.javaObjectType to Double::class.javaPrimitiveType,
		Char::class.javaObjectType to Char::class.javaPrimitiveType,
		Byte::class.javaObjectType to Byte::class.javaPrimitiveType,
		Boolean::class.javaObjectType to Boolean::class.javaPrimitiveType)

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(ClassUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle
	@JvmStatic
	fun <T> classForName(classname: String?): Class<out T>? {
		return try { Class.forName(classname) as Class<out T> }
			catch(e: Exception) { exception(e); null }
	}

	@ExportGradle
	@JvmStatic
	@Throws(ClassNotFoundException::class)
	fun <T> classForName0(classname: String?): Class<out T> {
		return Class.forName(classname) as Class<out T>
	}

	@ExportGradle
	@JvmStatic
	fun overrideFinal(obj: Any?, field: Field, newValue: Any?) {
		val useUnsafe: Boolean = JAVA_DETECTION_VERSION > 12
		var exception: Throwable? = null
		try {
			if(!useUnsafe) {
				val modifiersField = Field::class.java.getDeclaredField("modifiers")
				modifiersField.isAccessible = true
				modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
				field.isAccessible = true
				field[obj] = newValue
			}
		} catch(e: Throwable) { exception = exception(e) }
		if(!useUnsafe && exception == null) return
		try {
			val fieldObject = obj ?: unsafe.staticFieldBase(field)
			val fieldOffset = if(obj != null) unsafe.objectFieldOffset(field) else unsafe.staticFieldOffset(field)
			unsafe.putObject(fieldObject, fieldOffset, newValue)
		} catch(e: Throwable) {
			if(exception != null) e.addSuppressed(exception)
			throw Error(exception(e))
		}
	}

	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun currentClassFile(clazz: Class<*>): File {
		var result = File(URLDecoder.decode(clazz.protectionDomain.codeSource.location.path, StandardCharsets.UTF_8.name()))
		if(result.isDirectory) result = File(result, clazz.simpleName + ".class")
		return result
	}

	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun currentClassFile(): File? {
		val currentClass = classForName<Any>(Thread.currentThread().stackTrace[2].className)
		return if(currentClass == null) null else currentClassFile(currentClass)
	}
}
