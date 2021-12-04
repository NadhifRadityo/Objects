import GroovyKotlinInteroperability.ExportGradle
import GroovyKotlinInteroperability.GroovyInteroperability.attachObject
import GroovyKotlinInteroperability.GroovyInteroperability.detachObject
import GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GroovyKotlinInteroperability.GroovyKotlinCache
import GroovyKotlinInteroperability.GroovyManipulation.closureToLambda0
import Strategies.CommonUtils.purgeThreadLocal
import Strategies.GradleUtils.asGradle
import Strategies.GradleUtils.asProject
import Strategies.Utils.__must_not_happen
import groovy.lang.Closure
import org.gradle.api.GradleException
import java.util.*

/**
 * Lifecycle:
 * Construct -> onConfigStarted -> onConfigFinished -> Deconstruct
 */

object Common {
	@JvmStatic internal val groovyKotlinCaches = ArrayList<GroovyKotlinCache<*>>()
	@JvmStatic internal val contextStack = ThreadLocal.withInitial<LinkedList<Context>> { LinkedList() }
	@JvmStatic internal var initContext: Context? = null
		private set
	@JvmStatic private var cache: GroovyKotlinCache<Common>? = null

	@JvmStatic internal val onConfigStarted = HashMap<Int, MutableList<() -> Unit>>()
	@JvmStatic internal val onConfigFinished = HashMap<Int, MutableList<() -> Unit>>()

	@JvmStatic
	fun construct() {
		if(initContext != null)
			throw IllegalStateException("Init must be called once")
		val context = lastContext()
		val exception = GradleException("Error while running construct")
		initContext = context
		context(context.that) {
			val gradle = asGradle()
			gradle.buildFinished { destruct() }
			run {
				cache = prepareGroovyKotlinCache(Common)
				groovyKotlinCaches += cache!!
				GroovyKotlinInteroperability.construct()
				DynamicScripting.construct()
				Strategies.construct()
			}
			for(cache in groovyKotlinCaches)
				attachObject(context, cache)
			val priorities = onConfigStarted.keys.sortedDescending()
			for(priority in priorities) {
				val list = onConfigStarted[priority]!!
				list.reverse()
				for(callback in list)
					try { callback() } catch(e: Throwable)
					{ exception.addSuppressed(e) }
			}
		}
		if(exception.suppressed.isNotEmpty())
			throw exception
	}
	@JvmStatic
	fun destruct() {
		val context = initContext!!
		val exception = GradleException("Error while running destruct")
		context(context.that) {
			val priorities = onConfigFinished.keys.sortedDescending()
			for(priority in priorities) {
				val list = onConfigFinished[priority]!!
				list.reverse()
				for(callback in list)
					try { callback() } catch(e: Throwable)
					{ exception.addSuppressed(e) }
			}
			for(cache in groovyKotlinCaches.reversed())
				detachObject(context, cache)
			run {
				GroovyKotlinInteroperability.destruct()
				DynamicScripting.destruct()
				Strategies.destruct()
				groovyKotlinCaches -= cache!!
				cache = null
			}
		}
		for(cache in groovyKotlinCaches)
			System.err.println("Cache `${cache.owner.toString()}` [${cache.owner?.javaClass.toString()}] is not cleared")
		groovyKotlinCaches.clear()
		purgeThreadLocal(contextStack)
		onConfigStarted.clear()
		onConfigFinished.clear()
		initContext = null
		if(exception.suppressed.isNotEmpty())
			throw exception
	}
	@JvmStatic
	fun initProject() {

	}

	@ExportGradle
	@JvmStatic
	fun context(that: Any, callback: () -> Any?): Any? {
		val context = Context(that, asProject(that))
		val stack = contextStack.get()
		stack.addLast(context)
		try {
			return callback()
		} finally {
			val last = stack.removeLast()
			if(context != last)
				__must_not_happen()
		}
	}
	@ExportGradle
	@JvmStatic
	fun context(that: Any, callback: Closure<Any?>): Any? {
		return context(that, closureToLambda0(callback))
	}
	@ExportGradle
	@JvmStatic
	fun lastContext(): Context {
		val stack = contextStack.get()
		if(stack.size == 0)
			throw IllegalStateException("context is not available")
		return stack.last
	}

	@ExportGradle
	@JvmStatic
	fun addOnConfigStarted(priority: Int, callback: () -> Unit) {
		val list = onConfigStarted.computeIfAbsent(priority) { ArrayList() }
		list.add(callback)
	}
	@ExportGradle
	@JvmStatic
	fun removeOnConfigStarted(priority: Int, callback: () -> Unit) {
		val list = onConfigStarted[priority] ?: return
		list.remove(callback)
		if(list.isEmpty())
			onConfigStarted.remove(priority)
	}
	@ExportGradle
	@JvmStatic
	fun addOnConfigFinished(priority: Int, callback: () -> Unit) {
		val list = onConfigFinished.computeIfAbsent(priority) { ArrayList() }
		list.add(callback)
	}
	@ExportGradle
	@JvmStatic
	fun removeOnConfigFinished(priority: Int, callback: () -> Unit) {
		val list = onConfigFinished[priority] ?: return
		list.remove(callback)
		if(list.isEmpty())
			onConfigFinished.remove(priority)
	}
}
