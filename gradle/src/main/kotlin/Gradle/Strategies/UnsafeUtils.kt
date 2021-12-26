package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import sun.misc.Unsafe
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.ref.WeakReference

object UnsafeUtils {
    @JvmStatic private var cache: GroovyKotlinCache<UnsafeUtils>? = null
    @ExportGradle @JvmStatic val unsafe: Unsafe
    @JvmStatic internal val AFIELD_ByteArrayInputStream_buf: Long
    @JvmStatic internal val AFIELD_ByteArrayInputStream_pos: Long
    @JvmStatic internal val AFIELD_ByteArrayInputStream_mark: Long
    @JvmStatic internal val AFIELD_ByteArrayInputStream_count: Long
    @JvmStatic internal val tempByteArrayStore = ThreadLocal<WeakReference<ByteArray>>()
    @JvmStatic internal val tempOutputBufferStore = ThreadLocal<WeakReference<ByteArrayOutputStream?>?>()
    @JvmStatic internal val tempInputBufferStore = ThreadLocal<WeakReference<ByteArrayInputStream?>?>()

    @JvmStatic
    fun construct() {
        cache = prepareGroovyKotlinCache(UnsafeUtils)
        addInjectScript(cache!!)
    }
    @JvmStatic
    fun destruct() {
        removeInjectScript(cache!!)
        cache = null
    }

    init {
        val FIELD_Unsafe_theUnsafe = Unsafe::class.java.getDeclaredField("theUnsafe")
        FIELD_Unsafe_theUnsafe.isAccessible = true
        unsafe = FIELD_Unsafe_theUnsafe.get(null) as Unsafe
        val FIELD_ByteArrayInputStream_buf = ByteArrayInputStream::class.java.getDeclaredField("buf")
        val FIELD_ByteArrayInputStream_pos = ByteArrayInputStream::class.java.getDeclaredField("pos")
        val FIELD_ByteArrayInputStream_mark = ByteArrayInputStream::class.java.getDeclaredField("mark")
        val FIELD_ByteArrayInputStream_count = ByteArrayInputStream::class.java.getDeclaredField("count")
        AFIELD_ByteArrayInputStream_buf = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_buf)
        AFIELD_ByteArrayInputStream_pos = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_pos)
        AFIELD_ByteArrayInputStream_mark = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_mark)
        AFIELD_ByteArrayInputStream_count = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_count)
    }

    @ExportGradle @JvmStatic
    fun tempByteArray(length: Int): ByteArray {
        var result = tempByteArrayStore.get()?.get()
        if(result == null || result.size < length) {
            result = ByteArray(length)
            tempByteArrayStore.set(WeakReference(result))
        }
        return result
    }

    @ExportGradle @JvmStatic
    fun tempOutputBuffer(): ByteArrayOutputStream {
        var result = tempOutputBufferStore.get()?.get()
        if(result == null) {
            result = ByteArrayOutputStream()
            tempOutputBufferStore.set(WeakReference(result))
        }
        return result
    }

    @ExportGradle @JvmStatic
    fun tempInputBuffer(bytes: ByteArray, off: Int, len: Int): ByteArrayInputStream {
        var result = tempInputBufferStore.get()?.get()
        if(result == null) {
            result = ByteArrayInputStream(bytes, off, len)
            tempInputBufferStore.set(WeakReference(result))
        }
        checkNotNull(unsafe)
        unsafe.putObject(result, AFIELD_ByteArrayInputStream_buf, bytes)
        unsafe.putInt(result, AFIELD_ByteArrayInputStream_pos, off)
        unsafe.putInt(result, AFIELD_ByteArrayInputStream_mark, off)
        unsafe.putInt(result, AFIELD_ByteArrayInputStream_count, Math.min(off + len, bytes.size))
        return result
    }
}
