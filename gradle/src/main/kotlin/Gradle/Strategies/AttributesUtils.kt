package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.ExceptionUtils.exception
import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.UnsafeUtils.unsafe
import java.io.File
import java.lang.ref.WeakReference
import java.util.function.Function
import java.util.jar.Attributes

object AttributesUtils {
	@JvmStatic private var cache: GroovyKotlinCache<AttributesUtils>? = null
	@JvmStatic internal var localAttributesKey = ThreadLocal<WeakReference<Attributes.Name?>?>()
	@JvmStatic internal val AFIELD_Attributes_Name_name: Long
	@JvmStatic internal val AFIELD_Attributes_Name_hashCode: Long

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(AttributesUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	init {
		val FIELD_Attributes_Name_name = Attributes.Name::class.java.getDeclaredField("name")
		val FIELD_Attributes_Name_hashCode = Attributes.Name::class.java.getDeclaredField("hashCode")
		AFIELD_Attributes_Name_name = unsafe.objectFieldOffset(FIELD_Attributes_Name_name)
		AFIELD_Attributes_Name_hashCode = unsafe.objectFieldOffset(FIELD_Attributes_Name_hashCode)
	}

	@ExportGradle @JvmStatic
	fun attributeKey(key: String?): Attributes.Name {
		var result = localAttributesKey.get()?.get()
		if(result == null) {
			result = try { unsafe.allocateInstance(Attributes.Name::class.java) as Attributes.Name }
				catch(e: InstantiationException) { throw Error(exception(e)) }
			localAttributesKey.set(WeakReference(result))
		}
		unsafe.putObject(result, AFIELD_Attributes_Name_name, key)
		unsafe.putInt(result, AFIELD_Attributes_Name_hashCode, -1)
		return result!!
	}

	@ExportGradle @JvmStatic @JvmOverloads fun <T> a_getObject(attributes: Attributes, key: String, converter: Function<String, T>, defaultValue: T? = null): T? { val value = attributes[attributeKey(key)] as String?; return if(value != null) converter.apply(value) else defaultValue }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getString(attributes: Attributes, key: String, defaultValue: String? = null): String? { return a_getObject(attributes, key, { s -> s }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getFile(attributes: Attributes, key: String, defaultValue: File? = null): File? { return a_getObject(attributes, key, { s -> file(s) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getFiles(attributes: Attributes, key: String, defaultValue: Array<File>? = null): Array<File>? { return a_getObject(attributes, key, { s -> s.split(";").map { file(it) }.toTypedArray() }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getByte(attributes: Attributes, key: String, defaultValue: Byte = 0.toByte()): Byte { return a_getObject(attributes, key, { s -> java.lang.Byte.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getBoolean(attributes: Attributes, key: String, defaultValue: Boolean = false): Boolean { return a_getObject(attributes, key, { s -> java.lang.Boolean.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getChar(attributes: Attributes, key: String, defaultValue: Char = 0.toChar()): Char { return a_getObject(attributes, key, { s -> s[0] }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getShort(attributes: Attributes, key: String, defaultValue: Short = 0.toShort()): Short { return a_getObject(attributes, key, { s -> java.lang.Short.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getInt(attributes: Attributes, key: String, defaultValue: Int = 0): Int { return a_getObject(attributes, key, { s -> java.lang.Integer.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getLong(attributes: Attributes, key: String, defaultValue: Long = 0): Long { return a_getObject(attributes, key, { s -> java.lang.Long.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getFloat(attributes: Attributes, key: String, defaultValue: Float = 0f): Float { return a_getObject(attributes, key, { s -> java.lang.Float.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getDouble(attributes: Attributes, key: String, defaultValue: Double = 0.0): Double { return a_getObject(attributes, key, { s -> java.lang.Double.valueOf(s) }, defaultValue)!! }
	@ExportGradle @JvmStatic fun <T> a_setObject(attributes: Attributes, key: String, converter: (T) -> String?, value: T) { attributes[attributeKey(key)] = converter(value) }
	@ExportGradle @JvmStatic fun a_setObject(attributes: Attributes, key: String, value: Any?) { a_setObject(attributes, key, { v -> v?.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setString(attributes: Attributes, key: String, value: String?) { a_setObject(attributes, key, { v -> v }, value) }
	@ExportGradle @JvmStatic fun a_setFile(attributes: Attributes, key: String, value: File?) { a_setObject(attributes, key, { v -> v?.canonicalPath }, value) }
	@ExportGradle @JvmStatic fun a_setFiles(attributes: Attributes, key: String, value: Array<File>?) { a_setObject(attributes, key, { v -> v?.joinToString(";") { it.canonicalPath } }, value) }
	@ExportGradle @JvmStatic fun a_setByte(attributes: Attributes, key: String, value: Byte) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setBoolean(attributes: Attributes, key: String, value: Boolean) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setChar(attributes: Attributes, key: String, value: Char) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setShort(attributes: Attributes, key: String, value: Short) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setInt(attributes: Attributes, key: String, value: Int) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setLong(attributes: Attributes, key: String, value: Long) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setFloat(attributes: Attributes, key: String, value: Float) { a_setObject(attributes, key, { v -> v.toString() }, value) }
	@ExportGradle @JvmStatic fun a_setDouble(attributes: Attributes, key: String, value: Double) { a_setObject(attributes, key, { v -> v.toString() }, value) }
}
