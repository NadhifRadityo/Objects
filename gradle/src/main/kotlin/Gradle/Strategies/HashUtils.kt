package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.CommonUtils.bytesToHexString
import Gradle.Strategies.CommonUtils.hexStringToBytes
import Gradle.Strategies.FileUtils.fileString
import Gradle.Strategies.LoggerUtils.ldebug
import Gradle.Strategies.ProcessUtils.getCommandOutput
import Gradle.Strategies.ProgressUtils.progress
import Gradle.Strategies.ProgressUtils.progress_id
import Gradle.Strategies.UnsafeUtils.tempByteArray
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*

object HashUtils {
	@JvmStatic private var cache: GroovyKotlinCache<HashUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(HashUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun checksumJavaNative(file: File, digest: String): ByteArray {
		progress(progress_id(file, digest)).use { prog0 ->
			prog0.inherit()
			prog0.category = HashUtils::class.java.toString()
			prog0.description = "Creating java checksum"
			prog0.pstart()
			prog0.pdo("Checksum Java ($digest) ${file.path}")
			ldebug("Creating java checksum $digest: ${file.path}")
			FileInputStream(file).use { fileInputStream ->
				val messageDigest = MessageDigest.getInstance(digest)
				val buffer: ByteArray = tempByteArray(8192)
				var read: Int
				while(fileInputStream.read(buffer).also { read = it } != -1) messageDigest.update(buffer, 0, read)
				return messageDigest.digest()
			}
		}
	}
	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun checksumJavaNative(bytes: ByteArray, digest: String): ByteArray {
		progress(progress_id(bytes, digest)).use { prog0 ->
			prog0.inherit()
			prog0.category = HashUtils::class.java.toString()
			prog0.description = "Creating java checksum"
			prog0.pstart()
			prog0.pdo("Checksum Java ($digest) [Array]")
			ldebug("Creating java checksum $digest: [Array]")
			val messageDigest = MessageDigest.getInstance(digest)
			messageDigest.update(bytes, 0, bytes.size)
			return messageDigest.digest()
		}
	}
	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun checksumExeCertutil(exe: File, file: File, digest: String): ByteArray {
		progress(progress_id(exe, file, digest)).use { prog0 ->
			prog0.inherit()
			prog0.category = HashUtils::class.java.toString()
			prog0.description = "Creating certutil checksum"
			prog0.pstart()
			prog0.pdo("Checksum certutil ($digest) ${file.path}")
			ldebug("Creating exe certutil (${exe.path}) checksum $digest: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, "-hashfile", file.canonicalPath, digest)
				?: throw Error("Error occurred when running command")
			return hexStringToBytes(commandOutput.split("\n").toTypedArray()[1].trim())
		}
	}
	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun checksumExeOpenssl(exe: File, file: File, digest: String): ByteArray {
		progress(progress_id(exe, file, digest)).use { prog0 ->
			prog0.inherit()
			prog0.category = HashUtils::class.java.toString()
			prog0.description = "Creating openssl checksum"
			prog0.pstart()
			prog0.pdo("Checksum openssl ($digest) ${file.path}")
			ldebug("Creating exe openssl (${exe.path}) checksum $digest: ${file.path}")
			val prefix = digest.uppercase(Locale.getDefault()) + "(" + file.canonicalPath + ")= "
			val commandOutput = getCommandOutput(exe.canonicalPath, digest, file.canonicalPath)
				?: throw Error("Error occurred when running command")
			return hexStringToBytes(commandOutput.substring(prefix.length).trim())
		}
	}
	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun checksumExeMdNsum(exe: File, file: File, N: Int): ByteArray {
		progress(progress_id(exe, file, N)).use { prog0 ->
			prog0.inherit()
			prog0.category = HashUtils::class.java.toString()
			prog0.description = ("Creating mdnsum checksum")
			prog0.pstart()
			prog0.pdo("Checksum md${N}sum ${file.path}")
			ldebug("Creating exe md${N}sum (${exe.path}) checksum: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, file.canonicalPath)
				?: throw Error("Error occurred when running command")
			return hexStringToBytes(commandOutput.substring(1, 33).trim())
		}
	}
	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun checksumExeShaNsum(exe: File, file: File, N: Int): ByteArray {
		progress(progress_id(exe, file, N)).use { prog0 ->
			prog0.inherit()
			prog0.category = HashUtils::class.java.toString()
			prog0.description = ("Creating shansum checksum")
			prog0.pstart()
			prog0.pdo("Checksum sha${N}sum ${file.path}")
			ldebug("Creating exe sha${N}sum (${exe.path}) checksum: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, file.canonicalPath)
				?: throw Error("Error occurred when running command")
			return hexStringToBytes(commandOutput.substring(1, 41).trim())
		}
	}

	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	internal fun getHashExpected(obj: Any?): String {
		return when(obj) {
			is String -> obj
			is ByteArray -> bytesToHexString(obj)
			is File -> fileString(obj)
			else -> throw IllegalStateException()
		}
	}

	@ExportGradle
	@JvmStatic
	fun HASH_JAVA_NATIVE(digest: String): (Any, Any?) -> Boolean {
		return { obj, expected0 ->
			val generated: String = when(obj) {
				is File -> bytesToHexString(checksumJavaNative(obj, digest))
				is ByteArray -> bytesToHexString(checksumJavaNative(obj, digest))
				else -> throw IllegalStateException()
			}
			val expected = getHashExpected(expected0)
			generated == expected
		}
	}
	@ExportGradle
	@JvmStatic
	fun HASH_EXE_CERTUTIL(exe: File, digest: String): (File, Any?) -> Boolean {
		return { obj, expected0 ->
			val generated: String = bytesToHexString(checksumExeCertutil(exe, obj, digest))
			val expected = getHashExpected(expected0)
			generated == expected
		}
	}
	@ExportGradle
	@JvmStatic
	fun HASH_EXE_OPENSSL(exe: File, digest: String): (File, Any?) -> Boolean {
		return { obj, expected0 ->
			val generated: String = bytesToHexString(checksumExeOpenssl(exe, obj, digest))
			val expected = getHashExpected(expected0)
			generated == expected
		}
	}
	@ExportGradle
	@JvmStatic
	fun HASH_EXE_MDNSUM(exe: File, N: Int): (File, Any?) -> Boolean {
		return { obj, expected0 ->
			val generated: String = bytesToHexString(checksumExeMdNsum(exe, obj, N))
			val expected = getHashExpected(expected0)
			generated == expected
		}
	}
	@ExportGradle
	@JvmStatic
	fun HASH_EXE_SHANSUM(exe: File, N: Int): (File, Any?) -> Boolean {
		return { obj, expected0 ->
			val generated: String = bytesToHexString(checksumExeShaNsum(exe, obj, N))
			val expected = getHashExpected(expected0)
			generated == expected
		}
	}
}
