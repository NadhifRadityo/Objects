package Gradle

import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.attachObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.detachObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda0
import Gradle.Strategies.CommonUtils.purgeThreadLocal
import Gradle.Strategies.GradleUtils.asGradle
import Gradle.Strategies.GradleUtils.asProject
import Gradle.Strategies.Utils.__must_not_happen
import groovy.lang.Closure
import org.gradle.api.GradleException
import java.util.*

/**
 * Lifecycle (on every build):
 * Construct -> onConfigStarted -> onConfigFinished -> Deconstruct
 */

object Common {
	@JvmStatic internal val groovyKotlinCaches = ArrayList<GroovyKotlinCache<*>>()
	@JvmStatic internal val contextStack = ThreadLocal.withInitial<LinkedList<Context>> { LinkedList() }
	@JvmStatic internal var currentSession: Session? = null
		private set
	@JvmStatic private var cache: GroovyKotlinCache<Common>? = null

	@JvmStatic internal val onConfigStarted = HashMap<Int, MutableList<() -> Unit>>()
	@JvmStatic internal val onConfigFinished = HashMap<Int, MutableList<() -> Unit>>()

	@JvmStatic
	fun construct() {
		if(currentSession != null)
			throw IllegalStateException("Construct must be called once")
		println("CONSTRCUT")
		val context = lastContext()
		val gradle = asGradle(context.project)
		val session = Session(context, gradle.parent?.includedBuilds?.firstOrNull {
			it.projectDir.canonicalPath == context.project.rootProject.projectDir.canonicalPath })
		currentSession = session
		val exception = GradleException("Error while running construct")
		context(context.that) {
			val gradle = asGradle()
			if(session.build == null) {
				val unfinishedTasks = mutableListOf<String>()
				gradle.taskGraph.whenReady {
					unfinishedTasks += gradle.taskGraph.allTasks.filter { it.project.rootProject == context.project }.map { it.path }
					if(unfinishedTasks.isEmpty()) destruct()
				}
				gradle.taskGraph.afterTask {
					unfinishedTasks -= it.path
					if(unfinishedTasks.isEmpty()) destruct()
				}
			} else {
				val unfinishedProjects = mutableListOf<String>()
				gradle.allprojects { if(!it.state.executed) unfinishedProjects += it.path }
				gradle.afterProject { unfinishedProjects -= it.path; if(unfinishedProjects.isEmpty()) destruct() }
			}
			run {
				cache = prepareGroovyKotlinCache(Common)
				groovyKotlinCaches += cache!!
				Gradle.GroovyKotlinInteroperability.construct()
				Gradle.DynamicScripting.construct()
				Gradle.Strategies.construct()
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
		println("DESTRCUT")
		val session = currentSession
		if(session == null)
			throw IllegalStateException("Construct wasn't being called")
		val context = session.context
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
				Gradle.GroovyKotlinInteroperability.destruct()
				Gradle.DynamicScripting.destruct()
				Gradle.Strategies.destruct()
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
		currentSession = null
		if(exception.suppressed.isNotEmpty())
			throw exception
	}
	@JvmStatic
	fun initProject() {

	}

	@ExportGradle
	@JvmStatic
	fun <T> context(that: Any, callback: () -> T): T {
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
	fun <T> context(that: Any, callback: Closure<T>): T {
		return context(that, closureToLambda0(callback)) as T
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
