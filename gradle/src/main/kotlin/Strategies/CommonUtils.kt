package Strategies

import DynamicScripting.Scripting.addInjectScript
import DynamicScripting.Scripting.removeInjectScript
import GroovyKotlinInteroperability.ExportGradle
import GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GroovyKotlinInteroperability.GroovyKotlinCache
import Strategies.ExceptionUtils.exception
import Strategies.LoggerUtils.ldebug
import Strategies.LoggerUtils.linfo
import Strategies.ProgressUtils.progress
import Strategies.ProgressUtils.progress_id
import Strategies.StreamUtils.copyStream
import java.io.BufferedInputStream
import java.io.OutputStream
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.nio.file.Paths
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import kotlin.math.log10

object CommonUtils {
	@JvmStatic private var cache: GroovyKotlinCache<CommonUtils>? = null
	@JvmStatic private var downloadProgressCache: StringBuilder? = null
	@JvmStatic internal var readableByteCount: CharacterIterator = StringCharacterIterator("kMGTPE")

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(CommonUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic @Throws(URISyntaxException::class)
	fun urlToUri(url: URL): URI { return URI(url.protocol, url.userInfo, url.host, url.port, url.path, url.query, url.ref) }
	@ExportGradle @JvmStatic @Throws(MalformedURLException::class, URISyntaxException::class)
	fun formattedUrl(url: String): String { return urlToUri(URL(url)).toASCIIString() }

	@ExportGradle
	@JvmStatic
	fun bytesToHexString(bytes: ByteArray): String {
		val builder = StringBuilder()
		for(aByte in bytes) builder.append(((aByte.toInt() and 0xff) + 0x100).toString(16).substring(1))
		return builder.toString()
	}
	@ExportGradle
	@JvmStatic
	fun hexStringToBytes(string: String): ByteArray {
		val bytes = ByteArray(string.length / 2)
		for(i in string.indices step 2)
			bytes[i / 2] = ((string[i].digitToInt(16) shl 4) + string[i + 1].digitToInt(16)).toByte()
		return bytes
	}

	@ExportGradle
	@JvmStatic
	fun purgeThreadLocal(threadLocal: ThreadLocal<*>) {
		for(thread in Thread.getAllStackTraces().keys) { try {
			val FIELD_Thread_threadLocals: Field = thread.javaClass.getDeclaredField("threadLocals")
			FIELD_Thread_threadLocals.isAccessible = true
			val threadLocals = FIELD_Thread_threadLocals.get(thread) ?: continue
			val FIELD_ThreadLocalMap_remove: Method = threadLocals.javaClass.getDeclaredMethod("remove", ThreadLocal::class.java)
			FIELD_ThreadLocalMap_remove.isAccessible = true
			FIELD_ThreadLocalMap_remove.invoke(threadLocals, threadLocal)
		} catch(e: Throwable) { exception(e) } }
	}

	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun downloadFile(url: URL, outputStream: OutputStream) {
		 progress(progress_id(url, outputStream)).use { prog0 ->
			prog0.inherit()
			prog0.category = CommonUtils::class.java.toString()
			prog0.description = "Downloading file"
			prog0.pstart()
			val source = Paths.get(URI(url.toString()).path).fileName.toString()
			prog0.pdo("Opening connection $source")
			ldebug("Starting to download: $source")
			val connection = url.openConnection()
			val completeFileSize = connection.contentLength.toLong()
			BufferedInputStream(connection.getInputStream()).use { inputStream ->
				prog0.pdo("Downloading $source")
				linfo("Downloading $source... ($url)")
				copyStream(inputStream, outputStream, newStreamProgress(completeFileSize))
			}
		}
	}

	@ExportGradle
	@JvmStatic
	fun newStreamProgress(totalSize: Long): Consumer<Long> {
		return object : Consumer<Long> {
			var startTime = 0L
			var lastTime = 0L
			var lastLength = 0L
			var lastPrint = 0L
			var speed = 0L
			var speeds: LongArray? = null
			var speedsIndex = 0
			var prog0: ProgressUtils.ProgressWrapper? = null
			override fun accept(length: Long) {
				if(length == -1L) {
					startTime = System.currentTimeMillis()
					lastTime = System.currentTimeMillis()
					speeds = LongArray(30)
					Arrays.fill(speeds, -1L)
					prog0 = progress(progress_id(speeds as Any?))
					prog0?.inherit()
					prog0?.category = CommonUtils::class.java.toString()
					prog0?.description = "Stream progress"
					prog0?.pstart()
					return
				}
				if(length == -2L) {
					prog0?.close()
					return
				}
				prog0?.pdo(String.format("%.2f%%", length * 100.0f / totalSize.toFloat()))
				val alpha = 0.9f
				val now = System.currentTimeMillis()
				val deltaTime = Math.max(1, now - lastTime)
				val deltaLength = length - lastLength
				speeds!![speedsIndex++] = deltaLength * 1000 / deltaTime
				if(speedsIndex >= speeds!!.size) speedsIndex = 0
				lastTime = now
				lastLength = length
				if(now - lastPrint < 1000) return
				var result: Long = 0
				val values = speeds
				var counter = 0
				for(value in values!!) {
					if(value == -1L) continue
					counter++
					result += value
				}
				result /= counter.toLong()
				speed = (speed * alpha).toLong() + (result * (1 - alpha)).toLong()
				printDownloadProgress(startTime, totalSize, length, speed)
				lastPrint = now
			}
		}
	}

	@ExportGradle
	@JvmStatic
	fun printDownloadProgress(startTime: Long, total: Long, current: Long, speed: Long) {
		if(downloadProgressCache == null) downloadProgressCache = StringBuilder(200)
		if(total < 0) return
		val eta = if(speed == 0L) Long.MAX_VALUE else (total - current) * 1000 / speed
		val etaHms = if(speed == 0L) "N/A" else String.format(
			"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
			TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
			TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1)
		)
		val percent = if(total == 0L) 0 else (current * 100 / total).toInt()
		with(downloadProgressCache!!) {
			append(java.lang.String.join("", Collections.nCopies(2 - if(percent == 0) 0 else log10(percent.toDouble()).toInt(), " ")))
			append(String.format(" %d%% [", percent))
			append(java.lang.String.join("", Collections.nCopies(percent, "=")))
			append('>')
			append(java.lang.String.join("", Collections.nCopies(100 - percent, " ")))
			append(']')
			append(java.lang.String.join("", Collections.nCopies(log10(total.toDouble()).toInt() - if(current == 0L) 0 else log10(current.toDouble()).toInt(), " ")))
			append(String.format(" %d/%d, ETA: %s, Speed: %s/s", current, total, etaHms, humanReadableByteCount(speed)))
		}
		linfo(downloadProgressCache.toString().replace("%".toRegex(), "%%"))
		downloadProgressCache!!.setLength(0)
	}

	@ExportGradle
	@JvmStatic
	fun humanReadableByteCount(bytes0: Long): String {
		var bytes = bytes0
		if(-1000 < bytes && bytes < 1000) return "$bytes B"; readableByteCount.index = 0
		while(bytes <= -999950 || bytes >= 999950) { bytes /= 1000; readableByteCount.next() }
		return String.format("%.1f %cB", bytes / 1000.0, readableByteCount.current())
	}
}
