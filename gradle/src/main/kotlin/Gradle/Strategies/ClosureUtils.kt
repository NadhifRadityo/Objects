package Gradle.Strategies

import Gradle.Common.context
import Gradle.Common.groovyKotlinCaches
import Gradle.Common.lastContext
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.KotlinClosure

object ClosureUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ClosureUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(ClosureUtils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	@ExportGradle
	@JvmStatic
	fun lash(that: Any, closure: KotlinClosure, vararg prependArgs: Any?): KotlinClosure {
		val result = KotlinClosure("lash ${closure.name}")
		result.overloads += KotlinClosure.KLambdaOverload lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(prependArgs.size + args.size)
			System.arraycopy(prependArgs, 0, finalArgs, 0, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size, args.size)
			return@lambda context(that) lambda1@{ return@lambda1 closure.call(*finalArgs) }
		}
		return result
	}
	@ExportGradle
	@JvmStatic
	fun lash(closure: KotlinClosure): KotlinClosure {
		return lash(lastContext().that, closure)
	}

	@ExportGradle
	@JvmStatic
	fun bind(self: Any?, closure: KotlinClosure, vararg prependArgs: Any?): KotlinClosure {
		val result = KotlinClosure("bind ${closure.name}")
		result.overloads += KotlinClosure.KLambdaOverload lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(1 + prependArgs.size + args.size)
			finalArgs[0] = self
			System.arraycopy(prependArgs, 0, finalArgs, 1, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size + 1, args.size)
			return@lambda closure.call(*finalArgs)
		}
		return result
	}
}
