package Gradle

import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.attachObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.detachObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda0
import Gradle.Strategies.CommonUtils.purgeThreadLocal
import Gradle.Strategies.GradleUtils.asGradle
import Gradle.Strategies.GradleUtils.asProject0
import Gradle.Strategies.Utils.__must_not_happen
import groovy.lang.Closure
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
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
			throw IllegalStateException("Constructor must be called once")
		println("CONSTRUCT")
		val context = lastContext()
		val (that, _, project) = context
		val gradle = asGradle(project)
		val session = Session(context, gradle.parent?.includedBuilds?.firstOrNull {
			it.projectDir.canonicalPath == project.rootProject.projectDir.canonicalPath })
		currentSession = session

		val exception = GradleException("Error while running construct")
		context(that) {
			if(session.build == null) {
				val mainTasks = mutableListOf<Task>()
				val unfinishedTasks = mutableListOf<Task>()
				val dependsTreeBack = mutableMapOf<Task, MutableSet<Task>>()
				val dependsTree = mutableMapOf<Task, Set<Task>?>()
				fun computeTreeBack(task: Task, stack: LinkedList<Task> = LinkedList()) {
					val tree = dependsTreeBack.computeIfAbsent(task) { mutableSetOf() }
					val depends = dependsTree.computeIfAbsent(task) {
						try { gradle.taskGraph.getDependencies(task) }
						catch(ignored: IllegalStateException) { setOf() }
					}
					tree += stack
					if(depends == null) return
					stack.addLast(task)
					for(depend in depends)
						computeTreeBack(depend, stack)
					stack.removeLast()
				}
				fun computeTree() {
					val iterator = unfinishedTasks.iterator()
					iteratorLoop@ while(iterator.hasNext()) {
						val unfinishedTask = iterator.next()
						val depends = dependsTree[unfinishedTask]
						if(depends != null) {
							for(depend in depends) {
								if(depend.state.failure == null) continue
								iterator.remove()
								continue@iteratorLoop
							}
						}
						for(tree in dependsTree.entries) {
							val (task, dependencies) = tree
							if(task == unfinishedTask || dependencies == null) continue
							if(!dependencies.contains(unfinishedTask)) continue
							val failures = dependencies.count { it.state.failure != null }
							if(failures > 0) continue
							continue@iteratorLoop
						}
						if(!mainTasks.contains(unfinishedTask)) {
							iterator.remove()
							continue@iteratorLoop
						}
					}
				}
				gradle.taskGraph.whenReady {
					val currentTask = gradle.taskGraph.allTasks.filter { it.project.rootProject == project }
					mainTasks += gradle.startParameter.taskNames.flatMap { project.getTasksByName(it, true) }
					unfinishedTasks += currentTask
					currentTask.forEach { computeTreeBack(it) }
					if(unfinishedTasks.isEmpty()) destruct()
				}
				gradle.taskGraph.afterTask {
					unfinishedTasks -= it
					if(it.state.failure != null) {
						val tree = dependsTreeBack[it]
						if(tree != null) for(task in tree)
							unfinishedTasks -= task
					}
					computeTree()
					if(unfinishedTasks.isEmpty()) destruct()
				}
			} else {
				val unfinishedProjects = mutableListOf<Project>()
				gradle.allprojects { if(!it.state.executed) unfinishedProjects += it }
				gradle.afterProject { unfinishedProjects -= it; if(unfinishedProjects.isEmpty()) destruct() }
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
		println("DESTRUCT")
		val session = currentSession
		if(session == null)
			throw IllegalStateException("Constructor wasn't being called")
		val context = session.context
		val (that, _, project) = context
		val gradle = asGradle(project)

		val exception = GradleException("Error while running destruct")
		context(that) {
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
	fun constructNoContext() {
		val exception = GradleException("Error while running construct")
		run {
			cache = prepareGroovyKotlinCache(Common)
			groovyKotlinCaches += cache!!
			Gradle.GroovyKotlinInteroperability.construct()
			Gradle.DynamicScripting.construct()
			Gradle.Strategies.construct()
		}
		val priorities = onConfigStarted.keys.sortedDescending()
		for(priority in priorities) {
			val list = onConfigStarted[priority]!!
			list.reverse()
			for(callback in list)
				try { callback() } catch(e: Throwable)
				{ exception.addSuppressed(e) }
		}
		if(exception.suppressed.isNotEmpty())
			throw exception
	}
	@JvmStatic
	fun destructNoContext() {
		val exception = GradleException("Error while running destruct")
		val priorities = onConfigFinished.keys.sortedDescending()
		for(priority in priorities) {
			val list = onConfigFinished[priority]!!
			list.reverse()
			for(callback in list)
				try { callback() } catch(e: Throwable)
				{ exception.addSuppressed(e) }
		}
		run {
			Gradle.GroovyKotlinInteroperability.destruct()
			Gradle.DynamicScripting.destruct()
			Gradle.Strategies.destruct()
			groovyKotlinCaches -= cache!!
			cache = null
		}
		groovyKotlinCaches.clear()
		purgeThreadLocal(contextStack)
		onConfigStarted.clear()
		onConfigFinished.clear()
		currentSession = null
		if(exception.suppressed.isNotEmpty())
			throw exception
	}

	@ExportGradle @JvmStatic
	fun <T> context(that: Any, callback: () -> T): T {
		val context = Context(that, asProject0(that))
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
	@ExportGradle @JvmStatic
	fun <T> context(that: Any, callback: Closure<T>): T {
		return context(that, closureToLambda0(callback)) as T
	}
	@ExportGradle @JvmStatic
	fun lastContext0(): Context? {
		val stack = contextStack.get()
		return if(stack.size > 0) stack.last() else null
	}
	@ExportGradle @JvmStatic
	fun lastContext(): Context {
		return lastContext0() ?: throw IllegalStateException("Context is not available")
	}

	@ExportGradle @JvmStatic
	fun addOnConfigStarted(priority: Int, callback: () -> Unit) {
		val list = onConfigStarted.computeIfAbsent(priority) { ArrayList() }
		list.add(callback)
	}
	@ExportGradle @JvmStatic
	fun removeOnConfigStarted(priority: Int, callback: () -> Unit) {
		val list = onConfigStarted[priority] ?: return
		list.remove(callback)
		if(list.isEmpty())
			onConfigStarted.remove(priority)
	}
	@ExportGradle @JvmStatic
	fun addOnConfigFinished(priority: Int, callback: () -> Unit) {
		val list = onConfigFinished.computeIfAbsent(priority) { ArrayList() }
		list.add(callback)
	}
	@ExportGradle @JvmStatic
	fun removeOnConfigFinished(priority: Int, callback: () -> Unit) {
		val list = onConfigFinished[priority] ?: return
		list.remove(callback)
		if(list.isEmpty())
			onConfigFinished.remove(priority)
	}
}
