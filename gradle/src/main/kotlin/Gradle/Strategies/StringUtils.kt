package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import java.util.*

object StringUtils {
	@JvmStatic private var cache: GroovyKotlinCache<StringUtils>? = null
	@ExportGradle const val ALPHABETS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	@ExportGradle const val ALPHABETS_LOWER = "abcdefghijklmnopqrstuvwxyz"
	@ExportGradle const val DIGITS = "0123456789"
	@ExportGradle const val ALPHANUMERIC = ALPHABETS_UPPER + ALPHABETS_LOWER + DIGITS;
	@ExportGradle const val ASCII_SYMBOLS = " !\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
	@ExportGradle const val ASCII_CHARACTERS = ALPHANUMERIC + ASCII_SYMBOLS

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(StringUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	// https://stackoverflow.com/questions/3537706/how-to-unescape-a-java-string-literal-in-java
	@ExportGradle @JvmStatic
	fun unescapeJavaString(string: String): String {
		val stringBuilder = StringBuilder(string.length)
		var i = 0
		while(i < string.length) {
			var currentChar = string[i]
			if(currentChar != '\\') { stringBuilder.append(currentChar); i++; continue }
			val nextChar = if(i == string.length - 1) '\\' else string[i + 1]
			if(nextChar in '0'..'7') {
				var code = "" + nextChar; i++
				if(i < string.length - 1 && string[i + 1] >= '0' && string[i + 1] <= '7') {
					code += string[i + 1]; i++
					if(i < string.length - 1 && string[i + 1] >= '0' && string[i + 1] <= '7') {
						code += string[i + 1]; i++ } }
				stringBuilder.append(code.toInt(8).toChar())
				i++; continue
			}
			when(nextChar) {
				'\\' -> currentChar = '\\'
				'b' -> currentChar = '\b'
				'f' -> currentChar = '\u000c'
				'n' -> currentChar = '\n'
				'r' -> currentChar = '\r'
				't' -> currentChar = '\t'
				'\"' -> currentChar = '\"'
				'\'' -> currentChar = '\''
				'u' -> {
					if(i >= string.length - 5) currentChar = 'u';
					else {
						val code = ("" + string[i + 2] + string[i + 3] + string[i + 4] + string[i + 5]).toInt(16)
						stringBuilder.append(Character.toChars(code))
						i += 5; i++; continue
					}
				}
				else -> throw Error("Invalid escape character at ${i + 1}\n${string}")
			}
			stringBuilder.append(currentChar)
			i++; i++
		}
		return stringBuilder.toString()
	}

	// https://stackoverflow.com/questions/2406121/how-do-i-escape-a-string-in-java
	@ExportGradle @JvmStatic
	fun escape(string: String): String {
		return string.replace("\\", "\\\\")
			.replace("\t", "\\t")
			.replace("\b", "\\b")
			.replace("\n", "\\n")
			.replace("\r", "\\r")
			.replace("\u000c", "\\u000c")
			.replace("\'", "\\'")
			.replace("\"", "\\\"")
	}

	@ExportGradle @JvmStatic
	fun mostSafeString(string: String): String { return string.lowercase(Locale.getDefault()).replace("[^A-Za-z0-9]".toRegex(), "_") }
	@ExportGradle @JvmStatic @JvmOverloads
	fun randomString(length: Int = 8, charset: CharArray = ALPHANUMERIC.toCharArray()): String { return (0 until length).map { charset.random() }.joinToString("") }
}
