package Gradle.Strategies

import Gradle.Common
import Gradle.Common.groovyKotlinCaches
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.Utils.__invalid_type
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.invocation.Gradle
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.groovy.scripts.BasicScript
import org.gradle.kotlin.dsl.KotlinScript
import org.gradle.kotlin.dsl.support.DefaultKotlinScript
import org.gradle.kotlin.dsl.support.KotlinScriptHost
import org.gradle.launcher.daemon.server.scaninfo.DaemonScanInfo
import java.lang.management.ManagementFactory

object GradleUtils {
	@JvmStatic private var cache: GroovyKotlinCache<GradleUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(GradleUtils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	// Gradle Object Conversion
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun asProject(project: Any? = null): Project {
		if(project == null) return Common.lastContext().project
		if(project is Project) return project
		if(project is String) return Common.lastContext().project.project(project)
		if(project is BasicScript) return (project.scriptTarget as ProjectInternal)
		if(project is DefaultKotlinScript) return kotlinScriptHostTarget(project)
		throw __invalid_type()
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun asGradle(project: Any? = null): Gradle {
		return asProject(project).gradle
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun asTask(task: Any, project: Any? = null): Task {
		if(task is Task) return task
		if(task is String) return asProject(project).tasks.getByName(task)
		throw __invalid_type()
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun <T> asService(clazz: Class<T>, project: Any? = null): T {
		return (asProject(project) as ProjectInternal).services.get(clazz)
	}

	// Gradle Global Ext
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun hasGlobalExt(key: String, project: Any? = null): Boolean {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		return gradleExt.has(key)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun <T> getGlobalExt(key: String, project: Any? = null): T? {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		return if(gradleExt.has(key)) gradleExt.get(key) as T? else null
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun <T> setGlobalExt(key: String, obj: T, project: Any? = null) {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		gradleExt.set(key, obj)
	}

	// Gradle Task
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun forwardTask(project: Any?, filter: (Task) -> Boolean, exec: (Task) -> Unit = {}) {
		val project0 = Common.lastContext().project
		val project1 = asProject(project)
		project1.tasks.filter(filter).map { task -> project0.task(task.name) {
			it.group = task.group; it.dependsOn(task) } }.forEach(exec)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun runAfterAnotherTask(tasks: Collection<Any>, project: Any? = null) {
		val tasks0 = tasks.map { asTask(it, project) }
		for(i in 1 until tasks0.size) tasks0[i].mustRunAfter(tasks0[i - 1])
	}

	// Gradle Daemon
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun isSingleUseDaemon(project: Any? = null): Boolean {
		return asService(DaemonScanInfo::class.java, project).isSingleUse
	}
	@ExportGradle
	@JvmStatic
	fun isRunningOnDebug(): Boolean {
		return ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
			.filter { it.indexOf("-agentlib:jdwp") != -1 }.count() > 0
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun isDaemonProbablyUnstable(project: Any? = null): Boolean {
		return isSingleUseDaemon(project) || isRunningOnDebug()
	}

	// Kotlin DSL
	fun <T : Any> kotlinScriptHostTarget(script: DefaultKotlinScript): T {
		val FIELD_UNKNOWN_host = script.javaClass.superclass.getDeclaredField("host")
		if(!KotlinScriptHost::class.java.isAssignableFrom(FIELD_UNKNOWN_host.type)) throw __invalid_type()
		FIELD_UNKNOWN_host.isAccessible = true
		return (FIELD_UNKNOWN_host.get(script) as KotlinScriptHost<T>).target
	}
}
