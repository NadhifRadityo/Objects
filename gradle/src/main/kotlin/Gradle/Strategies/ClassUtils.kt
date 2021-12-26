package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.ExceptionUtils.exception
import Gradle.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import Gradle.Strategies.UnsafeUtils.unsafe
import groovy.lang.MetaClass
import org.codehaus.groovy.runtime.InvokerHelper.getMetaClass
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object ClassUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ClassUtils>? = null
	@ExportGradle @JvmStatic
	val defaultClassLoader = ClassUtils::class.java.classLoader
	@ExportGradle @JvmStatic
	val boxedToPrimitive = mapOf<Class<*>, Class<*>>(
		Void::class.javaObjectType to Void::class.javaPrimitiveType!!,
		Int::class.javaObjectType to Int::class.javaPrimitiveType!!,
		Long::class.javaObjectType to Long::class.javaPrimitiveType!!,
		Short::class.javaObjectType to Short::class.javaPrimitiveType!!,
		Float::class.javaObjectType to Float::class.javaPrimitiveType!!,
		Double::class.javaObjectType to Double::class.javaPrimitiveType!!,
		Char::class.javaObjectType to Char::class.javaPrimitiveType!!,
		Byte::class.javaObjectType to Byte::class.javaPrimitiveType!!,
		Boolean::class.javaObjectType to Boolean::class.javaPrimitiveType!!)
	@ExportGradle @JvmStatic
	val primitiveToBoxed = mapOf<Class<*>, Class<*>>(
		Void::class.javaPrimitiveType!! to Void::class.javaObjectType,
		Int::class.javaPrimitiveType!! to Int::class.javaObjectType,
		Long::class.javaPrimitiveType!! to Long::class.javaObjectType,
		Short::class.javaPrimitiveType!! to Short::class.javaObjectType,
		Float::class.javaPrimitiveType!! to Float::class.javaObjectType,
		Double::class.javaPrimitiveType!! to Double::class.javaObjectType,
		Char::class.javaPrimitiveType!! to Char::class.javaObjectType,
		Byte::class.javaPrimitiveType!! to Byte::class.javaObjectType,
		Boolean::class.javaPrimitiveType!! to Boolean::class.javaObjectType)

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

	@ExportGradle @JvmStatic
	fun <T> classForName(name: String?, initialize: Boolean = true, loader: ClassLoader? = defaultClassLoader): Class<out T>? {
		return try { Class.forName(name, initialize, loader) as Class<out T> } catch(e: Exception) { exception(e); null } }
	@ExportGradle @JvmStatic @Throws(ClassNotFoundException::class)
	fun <T> classForName0(name: String?, initialize: Boolean = true, loader: ClassLoader? = defaultClassLoader): Class<out T> {
		return Class.forName(name, initialize, loader) as Class<out T> }
	@ExportGradle @JvmStatic
	fun metaClassFor(clazz: Class<*>): MetaClass { return getMetaClass(clazz) }
	@ExportGradle @JvmStatic
	fun metaClassFor(obj: Any): MetaClass { return getMetaClass(obj) }

	@ExportGradle @JvmStatic
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

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun currentClassFile(clazz: Class<*>): File {
		var result = File(URLDecoder.decode(clazz.protectionDomain.codeSource.location.path, StandardCharsets.UTF_8.name()))
		if(result.isDirectory) result = File(result, clazz.simpleName + ".class")
		return result
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun currentClassFile(vararg notFilter: String): File? {
		val currentClass = classForName<Any>(callerUserImplementedClass(*notFilter))
		return if(currentClass == null) null else currentClassFile(currentClass)
	}

	val defaultNotPackageFilter = listOf("java", "sun", "kotlin", "groovy", "org.gradle", "org.apache", "org.codehaus", "Gradle", "DUMMY$")
	@ExportGradle @JvmStatic
	fun callerUserImplementedClass(vararg notFilter: String): String? {
		return Thread.currentThread().stackTrace.first { s ->
			val className = s.className
			defaultNotPackageFilter.find { className.startsWith(it) } == null &&
					notFilter.find { className.startsWith(it) } == null
		}?.className
	}
}
