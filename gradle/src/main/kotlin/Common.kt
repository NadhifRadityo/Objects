import GroovyInteroperability.closureToLambda0
import Utils.__must_not_happen
import Utils.attachObject
import Utils.detachObject
import Utils.prepareGroovyKotlinCache
import groovy.lang.Closure
import org.gradle.api.GradleException
import java.util.*
import kotlin.collections.ArrayList

object Common {
	@JvmStatic
	internal val groovyKotlinCaches = ArrayList<GroovyKotlinCache<*>>()
	@JvmStatic
	private val contextStack = ThreadLocal.withInitial<LinkedList<Context>> { LinkedList() }
	@JvmStatic
	internal val onBuildFinished = ArrayList<() -> Unit>()
	@JvmStatic
	private var initContext: Context? = null
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null

	@JvmStatic
	fun init() {
		if(initContext != null)
			throw IllegalStateException("Init must be called once")
		val context = lastContext()
		initContext = context
		context(context.that) {
			val gradle = Utils.asGradle()
			gradle.buildFinished { deinit() }
			run {
				cache = prepareGroovyKotlinCache(Common)
				groovyKotlinCaches += cache!!
				GroovyInteroperability.init()
				Utils.init()
				Progress.init()
				Logger.init()
				Import.init()
			}
			for(cache in groovyKotlinCaches)
				attachObject(context, cache)
		}
	}
	@JvmStatic
	fun deinit() {
		val context = initContext!!
		val exception = GradleException("Error while running callback")
		context(context.that) {
			onBuildFinished.reverse()
			for(callback in onBuildFinished)
				try { callback() } catch(e: Throwable)
				{ exception.addSuppressed(e) }
			for(cache in groovyKotlinCaches.reversed())
				detachObject(context, cache)
			run {
				Import.deinit()
				Logger.deinit()
				Progress.deinit()
				Utils.deinit()
				GroovyInteroperability.deinit()
				groovyKotlinCaches -= cache!!
				cache = null
			}
		}
		for(cache in groovyKotlinCaches)
			System.err.println("Cache `${cache.owner.toString()}` [${cache.owner?.javaClass.toString()}] is not cleared")
		groovyKotlinCaches.clear()
		Utils.purgeThreadLocal(contextStack)
		onBuildFinished.clear()
		initContext = null
		if(exception.suppressed.isNotEmpty())
			throw exception
	}
	@JvmStatic
	fun initProject() {

	}

	@ExportGradle
	@JvmStatic
	fun context(that: Any, callback: () -> Unit) {
		val context = Context(that, Utils.asProject(that))
		val stack = contextStack.get()
		stack.addLast(context)
		try {
			callback()
		} finally {
			val last = stack.removeLast()
			if(context != last)
				__must_not_happen()
		}
	}
	@ExportGradle
	@JvmStatic
	fun context(that: Any, callback: Closure<Unit>) {
		context(that, closureToLambda0(callback))
	}
	@ExportGradle
	@JvmStatic
	fun lastContext(): Context {
		val stack = contextStack.get()
		if(stack.size == 0)
			throw IllegalStateException("context is not available")
		return stack.last
	}
}
