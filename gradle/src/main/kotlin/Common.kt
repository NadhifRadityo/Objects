import GroovyInteroperability.closureToLambda0
import Utils.__must_not_happen
import groovy.lang.Closure
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.util.*

object Common {
	@JvmStatic private val contextStack = ThreadLocal.withInitial<LinkedList<Project>> { LinkedList() }
	@JvmStatic internal val onBuildFinished = ArrayList<() -> Unit>()
	@JvmStatic private var initContext: Project? = null

	@JvmStatic fun init() {
		if(initContext != null)
			throw IllegalStateException("Init must be called once")
		val that = lastContext()
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
		}
	}
	@JvmStatic fun deinit() {
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
			onBuildFinished.reverse()
			for(callback in onBuildFinished)
				try { callback() } catch(e: Throwable)
				{ exception.addSuppressed(e) }
			Utils.pullKotlinFromGradle(Common)
		}
		Utils.purgeThreadLocal(contextStack)
		onBuildFinished.clear()
		initContext = null
		if(exception.suppressed.isNotEmpty())
			throw exception
	}
	@JvmStatic fun initProject() {

	}

	@ExportGradle
	@JvmStatic fun context(project: Any, callback: () -> Unit) {
		val asProject = Utils.asProject(project)
		val stack = contextStack.get()
		stack.addLast(asProject)
		try {
			callback()
		} catch(e: Throwable) {
			throw Error(e)
		} finally {
			val last = stack.removeLast()
			if(asProject != last)
				__must_not_happen()
		}
	}
	@ExportGradle
	@JvmStatic fun context(project: Any, callback: Closure<Unit>) {
		context(project, closureToLambda0(callback))
	}
	@ExportGradle
	@JvmStatic fun lastContext(): Project {
		val stack = contextStack.get()
		if(stack.size == 0)
			throw IllegalStateException("context is not available")
		return stack.last
	}
}
