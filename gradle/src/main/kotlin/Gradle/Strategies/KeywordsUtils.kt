package Gradle.Strategies

import Gradle.Common.groovyKotlinCaches
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache

object KeywordsUtils {
	@JvmStatic private var cache: GroovyKotlinCache<KeywordsUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(KeywordsUtils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	open class From<T>(val user: T)
	@ExportGradle
	@JvmStatic
	fun <T> from(user: T): From<T> {
		return From(user)
	}

	open class Being<T>(val user: T)
	@ExportGradle
	@JvmStatic
	fun <T> being(user: T): Being<T> {
		return Being(user)
	}

	open class With<T>(val user: T)
	@ExportGradle
	@JvmStatic
	fun <T> with(user: T): With<T> {
		return With(user)
	}
}
