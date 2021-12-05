package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.UnsafeUtils.unsafe
import java.util.*

object PropertiesUtils {
    @JvmStatic private var cache: GroovyKotlinCache<PropertiesUtils>? = null
    @JvmStatic internal val AFIELD_Properties_defaults: Long

    @JvmStatic
    fun construct() {
        cache = prepareGroovyKotlinCache(PropertiesUtils)
        addInjectScript(cache!!)
    }
    @JvmStatic
    fun destruct() {
        removeInjectScript(cache!!)
        cache = null
    }

    init {
        val FIELD_Properties_defaults = Properties::class.java.getDeclaredField("defaults")
        AFIELD_Properties_defaults = unsafe.objectFieldOffset(FIELD_Properties_defaults)
    }

    @ExportGradle
    @JvmStatic
    fun __get_defaults_properties(properties: Properties?): Properties? {
        return unsafe.getObject(properties, AFIELD_Properties_defaults) as Properties?
    }
    @ExportGradle
    @JvmStatic
    fun extendProperties(original: Properties?, extend: Properties?, nullable: Boolean): Properties? {
        if(nullable && sizeNonDefaultProperties(original) == 0 && sizeAllProperties(extend) == 0) return null
        val properties = Properties(extend)
        if(original != null) properties.putAll(original)
        return properties
    }
    @ExportGradle
    @JvmStatic
    fun copyAllProperties(properties: Properties?, nullable: Boolean): Properties? {
        if(nullable && sizeAllProperties(properties) == 0) return null
        val result = Properties()
        enumerateAllProperties(properties, result)
        return result
    }
    @ExportGradle
    @JvmStatic
    fun copyNonDefaultProperties(properties: Properties?, nullable: Boolean): Properties? {
        if(nullable && sizeNonDefaultProperties(properties) == 0) return null
        val result = Properties()
        enumerateNonDefaultProperties(properties, result)
        return result
    }
    @ExportGradle
    @JvmStatic
    fun enumerateAllProperties(properties: Properties?, hashtable: Hashtable<Any?, Any?>) {
        if(properties == null) return
        val defaults = __get_defaults_properties(properties)
        if(defaults != null) enumerateAllProperties(defaults, hashtable)
        enumerateNonDefaultProperties(properties, hashtable)
    }
    @ExportGradle
    @JvmStatic
    fun enumerateNonDefaultProperties(properties: Properties?, hashtable: Hashtable<Any?, Any?>) {
        if(properties == null) return
        val enumeration = properties.keys()
        while(enumeration.hasMoreElements()) {
            val key = enumeration.nextElement()
            val value = properties[key]
            hashtable[key] = value
        }
    }
    @ExportGradle
    @JvmStatic
    fun sizeAllProperties(properties: Properties?): Int {
        if(properties == null) return 0
        val defaults = __get_defaults_properties(properties)
        return (if(defaults != null) sizeAllProperties(defaults) else 0) + sizeNonDefaultProperties(properties)
    }
    @ExportGradle
    @JvmStatic
    fun sizeNonDefaultProperties(properties: Properties?): Int {
        return properties?.size ?: 0
    }

    @ExportGradle
    @JvmStatic
    fun <T> pn_getObject(properties: Properties, key: String, type: Class<T>, defaultValue: T?): T? {
        val obj = properties[key]
        return if(type.isInstance(obj)) obj as T? else defaultValue
    }
    @ExportGradle @JvmStatic fun <T> pn_getObject(properties: Properties, key: String, type: Class<T>): T? { return pn_getObject(properties, key, type, null) }
    @ExportGradle @JvmStatic @JvmOverloads fun <T> pn_getObject(properties: Properties, key: String, defaultValue: T? = null): T? { return pn_getObject(properties, key, Any::class.java, defaultValue) as T? }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getByte(properties: Properties, key: String, defaultValue: Byte = 0.toByte()): Byte { return pn_getObject(properties, key, Byte::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getBoolean(properties: Properties, key: String, defaultValue: Boolean = false): Boolean { return pn_getObject(properties, key, Boolean::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getChar(properties: Properties, key: String, defaultValue: Char = 0.toChar()): Char { return pn_getObject(properties, key, Char::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getShort(properties: Properties, key: String, defaultValue: Short = 0.toShort()): Short { return pn_getObject(properties, key, Short::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getInt(properties: Properties, key: String, defaultValue: Int = 0): Int { return pn_getObject(properties, key, Int::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getLong(properties: Properties, key: String, defaultValue: Long = 0): Long { return pn_getObject(properties, key, Long::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getFloat(properties: Properties, key: String, defaultValue: Float = 0f): Float { return pn_getObject(properties, key, Float::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getDouble(properties: Properties, key: String, defaultValue: Double = 0.0): Double { return pn_getObject(properties, key, Double::class.java, defaultValue)!! }

    @ExportGradle
    @JvmStatic
    fun <T> p_getObject(properties: Properties, key: String, type: Class<T>, defaultValue: T?): T? {
        val obj = properties[key]
        val castedObject = if(type.isInstance(obj)) obj as T? else null
        if(castedObject != null) return castedObject
        val defaults = __get_defaults_properties(properties) ?: return defaultValue
        return p_getObject(defaults, key, type, defaultValue)
    }
    @ExportGradle @JvmStatic fun <T> p_getObject(properties: Properties, key: String, type: Class<T>): T? { return p_getObject(properties, key, type, null) }
    @ExportGradle @JvmStatic @JvmOverloads fun <T> p_getObject(properties: Properties, key: String, defaultValue: T? = null): T? { return p_getObject(properties, key, Any::class.java, defaultValue) as T? }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getByte(properties: Properties, key: String, defaultValue: Byte = 0.toByte()): Byte { return p_getObject(properties, key, Byte::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getBoolean(properties: Properties, key: String, defaultValue: Boolean = false): Boolean { return p_getObject(properties, key, Boolean::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getChar(properties: Properties, key: String, defaultValue: Char = 0.toChar()): Char { return p_getObject(properties, key, Char::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getShort(properties: Properties, key: String, defaultValue: Short = 0.toShort()): Short { return p_getObject(properties, key, Short::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getInt(properties: Properties, key: String, defaultValue: Int = 0): Int { return p_getObject(properties, key, Int::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getLong(properties: Properties, key: String, defaultValue: Long = 0): Long { return p_getObject(properties, key, Long::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getFloat(properties: Properties, key: String, defaultValue: Float = 0f): Float { return p_getObject(properties, key, Float::class.java, defaultValue)!! }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getDouble(properties: Properties, key: String, defaultValue: Double = 0.0): Double { return p_getObject(properties, key, Double::class.java, defaultValue)!! }

    @ExportGradle
    @JvmStatic
    fun <T> p_setObject(properties: Properties, key: String?, type: Class<T>?, value: T?) {
        if(key == null || value == null) return
        properties[key] = value
    }
    @ExportGradle @JvmStatic fun <T> p_setObject(properties: Properties, key: String?, value: T) { p_setObject(properties, key, Any::class.java, value) }
    @ExportGradle @JvmStatic fun p_setByte(properties: Properties, key: String?, value: Byte) { p_setObject(properties, key, Byte::class.java, value) }
    @ExportGradle @JvmStatic fun p_setBoolean(properties: Properties, key: String?, value: Boolean) { p_setObject(properties, key, Boolean::class.java, value) }
    @ExportGradle @JvmStatic fun p_setChar(properties: Properties, key: String?, value: Char) { p_setObject(properties, key, Char::class.java, value) }
    @ExportGradle @JvmStatic fun p_setShort(properties: Properties, key: String?, value: Short) { p_setObject(properties, key, Short::class.java, value) }
    @ExportGradle @JvmStatic fun p_setInt(properties: Properties, key: String?, value: Int) { p_setObject(properties, key, Int::class.java, value) }
    @ExportGradle @JvmStatic fun p_setLong(properties: Properties, key: String?, value: Long) { p_setObject(properties, key, Long::class.java, value) }
    @ExportGradle @JvmStatic fun p_setFloat(properties: Properties, key: String?, value: Float) { p_setObject(properties, key, Float::class.java, value) }
    @ExportGradle @JvmStatic fun p_setDouble(properties: Properties, key: String?, value: Double) { p_setObject(properties, key, Double::class.java, value) }
}
