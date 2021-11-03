import Common.groovyKotlinCaches
import Utils.prepareGroovyKotlinCache

object Keywords {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null

	@JvmStatic
	fun init() {
		cache = prepareGroovyKotlinCache(Keywords)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun deinit() {
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
