import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.launcher.daemon.server.scaninfo.DaemonScanInfo
import java.io.File
import java.lang.management.ManagementFactory
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.nio.file.Paths
import java.util.function.Consumer
import java.util.function.Predicate

object Utils {
	private fun __invalid_type(): Throwable {
		throw Error("Invalid type")
	}
	fun asProject(project: Any? = null): Project {
		if(project == null) return Common.lastContext()
		if(project is Project) return project
		if(project is String) return Common.lastContext().project(project)
		throw __invalid_type()
	}
	fun asTask(task: Any?, project: Any? = null): Task {
		if(task is Task) return task
		if(task is String) return asProject(project).tasks.getByName(task)
		throw __invalid_type()
	}
	fun <T> asService(clazz: Class<T>, project: Any? = null): T {
		return (asProject(project) as ProjectInternal).services.get(clazz)
	}
	fun hasGlobalExt(key: String, project: Any? = null): Boolean {
		val project0 = asProject(project) as ExtraPropertiesExtension
		return project0.has(key)
	}
	fun <T> getGlobalExt(key: String, project: Any? = null): T? {
		val project0 = asProject(project) as ExtraPropertiesExtension
		return if(project0.has(key)) project0.get(key) as T? else null
	}
	fun <T> setGlobalExt(key: String, obj: T, project: Any? = null) {
		val project0 = asProject(project) as ExtraPropertiesExtension
		project0.set(key, obj)
	}
	fun forwardTask(project: Any?, filter: Predicate<Task>, exec: Consumer<Task>) {
		val project0 = Common.lastContext()
		val project1 = asProject(project)
		project1.tasks.filter { filter.test(it) }.map { task -> project0.task(task.name) {
			it.group = task.group; it.dependsOn(task) } }.forEach(exec)
	}
	fun runAfterAnotherTask(tasks: Collection<Any?>, project: Any? = null) {
		val tasks0 = tasks.map { asTask(it, project) }
		for(i in 1 until tasks0.size) tasks0[i].mustRunAfter(tasks0[i - 1])
	}
	fun isSingleUseDaemon(project: Any? = null): Boolean {
		return asService(DaemonScanInfo::class.java, project).isSingleUse
	}
	fun isRunningOnDebug(): Boolean {
		return ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
			.filter { it.indexOf("-agentlib:jdwp") != -1 }.count() > 0
	}
	fun isDaemonProbablyUnstable(project: Any? = null): Boolean {
		return isSingleUseDaemon(project) || isRunningOnDebug()
	}
	fun getRelativeFile(from: File, what: File): File {
		return Paths.get(from.canonicalPath).relativize(Paths.get(what.canonicalPath)).toFile()
	}
	fun purgeThreadLocal(threadLocal: ThreadLocal<*>) {
		Thread.getAllStackTraces().keys.forEach {
			try {
				val FIELD_Thread_threadLocals: Field = it.javaClass.getDeclaredField("threadLocals")
				FIELD_Thread_threadLocals.isAccessible = true
				val threadLocals = FIELD_Thread_threadLocals.get(it) ?: return
				val `FIELD_ThreadLocal$ThreadLocalMap_remove`: Method = threadLocals.javaClass.getDeclaredMethod("remove", ThreadLocal::class.java)
				`FIELD_ThreadLocal$ThreadLocalMap_remove`.isAccessible = true
				`FIELD_ThreadLocal$ThreadLocalMap_remove`.invoke(threadLocals, threadLocal)
			} catch(ignored: Throwable) { }
		}
	}
}
