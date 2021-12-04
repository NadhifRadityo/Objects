package Strategies

import DynamicScripting.Scripting.addInjectScript
import DynamicScripting.Scripting.removeInjectScript
import GroovyKotlinInteroperability.ExportGradle
import GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GroovyKotlinInteroperability.GroovyKotlinCache
import Strategies.FileUtils.fileString
import Strategies.FileUtils.mkfile
import Strategies.FileUtils.writeFileString
import Strategies.LoggerUtils.linfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonWriter
import java.io.*
import java.nio.charset.StandardCharsets

object JSONUtils {
	@JvmStatic private var cache: GroovyKotlinCache<JSONUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(JSONUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic
	fun <T> toJson(reader: Reader, clazz: Class<T>): T { return Gson().fromJson(reader, clazz) }
	@ExportGradle @JvmStatic
	fun <T> toJson(stream: InputStream, clazz: Class<T>): T { return Gson().fromJson(InputStreamReader(stream), clazz) }
	@ExportGradle @JvmStatic
	fun <T> toJson(string: String, clazz: Class<T>): T { return Gson().fromJson(string, clazz) }
	@ExportGradle @JvmStatic @Throws(IOException::class)
	fun <T> toJson(file: File, clazz: Class<T>?): T { return Gson().fromJson(fileString(file), clazz) }

	@ExportGradle
	@JvmStatic
	@Throws(IOException::class)
	fun <T> JSONToString(obj: T): String {
		StringWriter().use { stringWriter ->
			JsonWriter(stringWriter).use { jsonWriter ->
				jsonWriter.setIndent("\t")
				GsonBuilder().disableHtmlEscaping().create().toJson(obj, (obj as Any).javaClass, jsonWriter)
				return stringWriter.toString()
			}
		}
	}

	@ExportGradle
	@JvmStatic
	@Throws(Exception::class)
	fun <T> createJSONFile(obj: T, target: File): String {
		val stringOut = JSONToString(obj)
		mkfile(target)
		writeFileString(target, stringOut, StandardCharsets.UTF_8)
		linfo("Configurations written to: ${target.path}")
		return stringOut
	}
}
