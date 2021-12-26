package io.github.NadhifRadityo.Library.Providers

import io.github.NadhifRadityo.Library.LibraryImplement
import io.github.NadhifRadityo.Library.Utils.ExceptionUtils.exception
import io.github.NadhifRadityo.Library.Utils.FileUtils.file
import io.github.NadhifRadityo.Library.Utils.HashUtils.*
import io.github.NadhifRadityo.Library.Utils.LoggerUtils.*
import io.github.NadhifRadityo.Library.Utils.PropertiesUtils.copyAllProperties
import java.io.File
import java.util.*

enum class State(val code: Int) {
	VALIDATE_RESULT_ERROR(1),
	VALIDATE_RESULT_PASSED(0),
	VALIDATE_RESULT_NOT_PASSED(-1),
	VALIDATE_REQUEST_RUN(0),
	VALIDATE_REQUEST_FETCH(1)
}
val DOWNLOAD_MAX_RETRY = 3

typealias GetItemCallback = (Any) -> Array<String>
typealias ItemFileCallback = (Any, String) -> File
typealias FileValidationCallback = (Any, String, File, ItemFileCallback, State, DownloadCallback) -> Array<State?>
typealias DownloadCallback = (Any, String, File) -> Unit

fun defaultItemFileCallback(path: String?): ItemFileCallback {
	val staticDir = LibraryImplement.__static_dir()
	return { _, item -> file(staticDir, path, item) }
}

@Throws(Exception::class)
fun download0(dependency: Any, getItems: GetItemCallback, itemFileCallback: ItemFileCallback,
			 fileValidationCallback: FileValidationCallback, download: DownloadCallback): Map<String, File?> {
	var retry = 0
	val results = mutableMapOf<String, File?>()
	val items = getItems(dependency)
	var i = 0
	while(i < items.size) {
		val item = items[i]
		val itemFile = itemFileCallback(dependency, item)
		val validate = {
			val validationResults = fileValidationCallback(dependency, item, itemFile,
				itemFileCallback, State.VALIDATE_REQUEST_RUN, download)
			var anyPassed = false
			var anyNotPassed = false
			var allError = true
			for(validationResult in validationResults) {
				if(!anyPassed && validationResult == State.VALIDATE_RESULT_PASSED)
					anyPassed = true
				if(!anyNotPassed && validationResult == State.VALIDATE_RESULT_NOT_PASSED)
					anyNotPassed = true
				if(allError && validationResult != State.VALIDATE_RESULT_ERROR)
					allError = false
				if(anyPassed && !allError && anyNotPassed) break
			}
			arrayOf(validationResults, anyPassed, anyNotPassed, allError)
		}

		val wasDownloaded = itemFile.exists()
		if(wasDownloaded) {
			_linfo("File already downloaded, validating... (%s)", itemFile.path)
			val validationResult = validate()
			val anyPassed = validationResult[1] as Boolean
			val anyNotPassed = validationResult[2] as Boolean
			val allError = validationResult[3] as Boolean
			if(anyPassed) {
				retry = 0
				results[item] = itemFile
				i++
				continue
			}
			if(anyNotPassed) _lerror("Validation failed! Will retry...")
			if(allError) _lwarn("Validation not available! Try downloading again...")
		}

		fileValidationCallback(dependency, item, itemFile,
			itemFileCallback, State.VALIDATE_REQUEST_FETCH, download)
		if(wasDownloaded) {
			_linfo("File already downloaded, validating... (%s)", itemFile.path)
			val validationResult = validate()
			val anyPassed = validationResult[1] as Boolean
			val allError = validationResult[3] as Boolean
			if(anyPassed) {
				retry = 0
				results[item] = itemFile
				i++
				continue
			}
			if(allError) {
				_lwarn("Validation not available! Assuming it's good.")
				retry = 0
				results[item] = itemFile
				i++
				continue
			}
			_lerror("Validation failed! Will retry...")
		}

		download(dependency, item, itemFile)
		val validationResult = validate()
		val anyPassed = validationResult[1] as Boolean
		val anyNotPassed = validationResult[2] as Boolean
		val allError = validationResult[3] as Boolean
		if(anyPassed) {
			retry = 0
			results[item] = itemFile
			i++
			continue
		}
		if(allError) {
			_lwarn("Validation not available! Assuming it's good.")
			retry = 0
			results[item] = itemFile
			i++
			continue
		}
		if(anyNotPassed) {
			if(retry < DOWNLOAD_MAX_RETRY - 1) {
				_lerror("Validation failed! Will retry...")
				retry++
				i -= 1
			} else {
				_lerror("Error! cannot download file, check your internet connection.")
				results[item] = null
			}
		}
		i++
	}
	return results
}

typealias ValidationCallback = (File, Any) -> Boolean
typealias GetFileCallback = (String) -> File

@Throws(Exception::class)
fun validate(validations: Map<String, List<ValidationCallback>>, getFile: GetFileCallback): Array<State?> {
	val iterator = validations.entries.iterator()
	val results = arrayOfNulls<State>(validations.size)
	var i = -1
	L0@ while(iterator.hasNext()) {
		val (extension, providers) = iterator.next()
		val targetFile = getFile("")
		val checksumFile = getFile(extension)
		i++
		if(!targetFile.exists() || !checksumFile.exists()) {
			results[i] = State.VALIDATE_RESULT_ERROR
			continue
		}
		for(provider in providers) {
			try {
				val passed = provider(targetFile, checksumFile)
				if(passed) results[i] = State.VALIDATE_RESULT_PASSED
				else results[i] = State.VALIDATE_RESULT_NOT_PASSED
				continue@L0
			} catch(e: Throwable) {
				_lerror("Error while validating checksum,\n%s", exception(e))
			}
		}
		results[i] = State.VALIDATE_RESULT_ERROR
	}
	return results
}

const val DEFAULT_HASH_OPTION_JAVA_NATIVE_MD2 = "md2:JAVA_NATIVE:MD2"
const val DEFAULT_HASH_OPTION_JAVA_NATIVE_MD5 = "md5:JAVA_NATIVE:MD5"
const val DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA1 = "sha1:JAVA_NATIVE:SHA-1"
const val DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA224 = "sha224:JAVA_NATIVE:SHA-224"
const val DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA256 = "sha256:JAVA_NATIVE:SHA-256"
const val DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA384 = "sha384:JAVA_NATIVE:SHA-384"
const val DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA512 = "sha512:JAVA_NATIVE:SHA-512"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD2 = "md2:EXE_CERTUTIL:MD2"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD4 = "md4:EXE_CERTUTIL:MD4"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD5 = "md5:EXE_CERTUTIL:MD5"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA1 = "sha1:EXE_CERTUTIL:SHA1"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA256 = "sha256:EXE_CERTUTIL:SHA256"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA384 = "sha384:EXE_CERTUTIL:SHA384"
const val DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA512 = "sha512:EXE_CERTUTIL:SHA512"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_MD4 = "md4:EXE_OPENSSL:MD4"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_MD5 = "md5:EXE_OPENSSL:MD5"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA1 = "sha1:EXE_OPENSSL:SHA1"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA224 = "sha224:EXE_OPENSSL:SHA224"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA256 = "sha256:EXE_OPENSSL:SHA256"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA384 = "sha384:EXE_OPENSSL:SHA384"
const val DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA512 = "sha512:EXE_OPENSSL:SHA512"
const val DEFAULT_HASH_OPTION_EXE_MD5SUM = "md5:EXE_MD5SUM"
const val DEFAULT_HASH_OPTION_EXE_SHA1SUM = "sha1:EXE_SHA1SUM"
const val DEFAULT_HASH_OPTION_EXE_SHA224SUM = "sha224:EXE_SHA224SUM"
const val DEFAULT_HASH_OPTION_EXE_SHA256SUM = "sha256:EXE_SHA256SUM"
const val DEFAULT_HASH_OPTION_EXE_SHA384SUM = "sha384:EXE_SHA384SUM"
const val DEFAULT_HASH_OPTION_EXE_SHA512SUM = "sha512:EXE_SHA512SUM"

fun DEFAULT_HASH_OPTIONS_ALL_MD2(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_MD2)
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD2)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_MD4(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD4)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_MD4)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_MD5(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_MD5)
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD5)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_MD5)
	if(properties.containsKey("md5sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_MD5SUM)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_SHA1(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA1)
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA1)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA1)
	if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA1SUM)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_SHA224(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA224)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA224)
	if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA224SUM)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_SHA256(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA256)
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA256)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA256)
	if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA256SUM)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_SHA384(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA384)
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA384)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA384)
	if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA384SUM)
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL_SHA512(_properties: Properties): List<String> {
	val properties = copyAllProperties(_properties, false)
	val result = mutableListOf<String>()
	result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA512)
	if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA512)
	if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA512)
	if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA512SUM)
	return result
}
fun DEFAULT_HASH_OPTIONS_MDN(properties: Properties): List<String> {
	val result = mutableListOf<String>()
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD2(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD4(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD5(properties))
	return result
}
fun DEFAULT_HASH_OPTIONS_SHAN(properties: Properties): List<String> {
	val result = mutableListOf<String>()
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA1(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA224(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA256(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA384(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA512(properties))
	return result
}
fun DEFAULT_HASH_OPTIONS_ALL(properties: Properties): List<String> {
	val result = mutableListOf<String>()
	result.addAll(DEFAULT_HASH_OPTIONS_MDN(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_SHAN(properties))
	return result
}
fun DEFAULT_HASH_OPTIONS_MD5_SHA1(properties: Properties): List<String> {
	val result = mutableListOf<String>()
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD5(properties))
	result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA1(properties))
	return result
}

fun hashesAvailable(_properties: Properties?, options: String): Map<String, List<ValidationCallback>> {
	val properties = copyAllProperties(_properties, false)
	val result = hashMapOf<String, MutableList<ValidationCallback>>()
	val getFileIfPropertiesExist = { key: String -> if(properties.containsKey(key)) File(properties.getProperty(key)) else null }
	val certutil = getFileIfPropertiesExist("certutilPath")
	val openssl = getFileIfPropertiesExist("opensslPath")
	val md5sum = getFileIfPropertiesExist("md5sumPath")
	val sha1sum = getFileIfPropertiesExist("sha1sumPath")
	val sha224sum = getFileIfPropertiesExist("sha224sumPath")
	val sha256sum = getFileIfPropertiesExist("sha256sumPath")
	val sha384sum = getFileIfPropertiesExist("sha384sumPath")
	val sha512sum = getFileIfPropertiesExist("sha512sumPath")

	for(option in options.split(",")) {
		val settings = option.split(":").toTypedArray()
		val extension = settings[0]
		val provider = settings[1]
		val args = arrayOfNulls<String>(settings.size - 2)
		System.arraycopy(settings, 2, args, 0, args.size)

		val providers = result.computeIfAbsent(extension) { mutableListOf() }
		when(provider.uppercase(Locale.getDefault())) {
			"JAVA_NATIVE" -> providers += HASH_JAVA_NATIVE(args[0])
			"EXE_CERTUTIL" -> providers += HASH_EXE_CERTUTIL(certutil, args[0])
			"EXE_OPENSSL" -> providers += HASH_EXE_OPENSSL(openssl, args[0])
			"EXE_MD5SUM" -> providers += HASH_EXE_MDNSUM(md5sum, 5)
			"EXE_SHA1SUM" -> providers += HASH_EXE_SHANSUM(sha1sum, 1)
			"EXE_SHA224SUM" -> providers += HASH_EXE_SHANSUM(sha224sum, 224)
			"EXE_SHA256SUM" -> providers += HASH_EXE_SHANSUM(sha256sum, 256)
			"EXE_SHA384SUM" -> providers += HASH_EXE_SHANSUM(sha384sum, 384)
			"EXE_SHA512SUM" -> providers += HASH_EXE_SHANSUM(sha512sum, 512)
		}
	}
	return result
}
