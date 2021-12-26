package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.CommonUtils.newStreamProgress
import Gradle.Strategies.UnsafeUtils.tempByteArray
import Gradle.Strategies.UnsafeUtils.tempInputBuffer
import Gradle.Strategies.UnsafeUtils.tempOutputBuffer
import groovy.transform.ThreadInterrupt
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.function.Consumer

object StreamUtils {
	@JvmStatic private var cache: GroovyKotlinCache<StreamUtils>? = null
	@ExportGradle const val COPY_CACHE_SIZE = 65536

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(StreamUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic @ThreadInterrupt @Throws(IOException::class)
	fun copyStream(inputStream: InputStream, outputStream: OutputStream, progress: Consumer<Long>?) {
		val buffer = tempByteArray(COPY_CACHE_SIZE)
		progress?.accept(-1L)
		var length = 0L
		var read = 0
		while(!Thread.currentThread().isInterrupted && inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
			outputStream.write(buffer, 0, read)
			length += read.toLong()
			progress?.accept(length)
		}
		progress?.accept(-2L)
		if(Thread.currentThread().isInterrupted) throw InterruptedIOException()
	}

	@ExportGradle @JvmStatic @Throws(IOException::class)
	fun streamBytes(inputStream: InputStream): ByteArray {
		val outputStream = tempOutputBuffer()
		val totalSize = if(inputStream is FileInputStream) inputStream.channel.size() else 0
		val progress = if(totalSize >= 1000 * 1000 * 10) newStreamProgress(totalSize) else null
		return try {
			copyStream(inputStream, outputStream, progress)
			outputStream.close(); outputStream.toByteArray()
		} finally { outputStream.reset() }
	}

	@ExportGradle @JvmStatic @Throws(IOException::class)
	fun writeBytes(input: ByteArray, off: Int, len: Int, outputStream: OutputStream) {
		val inputStream = tempInputBuffer(input, off, len)
		val progress = if(len >= 1000 * 1000 * 10) newStreamProgress(len.toLong()) else null
		try { copyStream(inputStream, outputStream, progress); inputStream.close()
		} finally { inputStream.reset() }
	}

	@ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
	fun streamString(inputStream: InputStream, charset: Charset = StandardCharsets.UTF_8): String { return String(streamBytes(inputStream), charset) }
	@ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
	fun writeString(string: String, outputStream: OutputStream, charset: Charset = StandardCharsets.UTF_8) { val bytes = string.toByteArray(charset); writeBytes(bytes, 0, bytes.size, outputStream) }
}
