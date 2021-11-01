import GroovyInteroperability.closureToLambda0
import Utils.__must_not_happen
import Utils.applyKotlinGradle
import groovy.lang.Closure
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.util.*

object Common {
	@JvmStatic
	private val contextStack = ThreadLocal.withInitial<LinkedList<Context>> { LinkedList() }
	@JvmStatic
	internal val onBuildFinished = ArrayList<() -> Unit>()
	@JvmStatic
	private var initContext: Any? = null

	@JvmStatic
	fun init() {
		if(initContext != null)
			throw IllegalStateException("Init must be called once")
		val that = lastContext().that
		initContext = that
		context(that) {
			val gradle = Utils.asGradle()
			gradle.buildFinished { deinit() }
			Utils.pushKotlinToGradle(Common)
			run {
				GroovyInteroperability.init()
				Utils.init()
				Progress.init()
				Logger.init()
				Import.init()
			}
			applyKotlinGradle(that)
		}
	}
	@JvmStatic
	fun deinit() {
		val that = initContext!!
		val exception = GradleException("Error while running callback")
		context(that) {
			run {
				Import.deinit()
				Logger.deinit()
				Progress.deinit()
				Utils.deinit()
				GroovyInteroperability.deinit()
			}
			Utils.pullKotlinFromGradle(Common)
			onBuildFinished.reverse()
			for(callback in onBuildFinished)
				try { callback() } catch(e: Throwable)
				{ exception.addSuppressed(e) }
			applyKotlinGradle(that)
		}
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
		} catch(e: Throwable) {
			throw Error(e)
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
