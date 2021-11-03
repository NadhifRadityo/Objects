import Common.groovyKotlinCaches
import Common.onBuildFinished
import GroovyInteroperability.setKotlinToGroovy
import KotlinClosure.Companion.getKFunctionOverloads
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.invocation.Gradle
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.groovy.scripts.BasicScript
import org.gradle.launcher.daemon.server.scaninfo.DaemonScanInfo
import java.io.File
import java.lang.management.ManagementFactory
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.nio.file.Paths
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

object Utils {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null

	@JvmStatic
	fun init() {
		cache = prepareGroovyKotlinCache(Utils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun deinit() {
		groovyKotlinCaches -= cache!!
	}

	@JvmStatic
	internal fun __invalid_type(): Throwable {
		throw Error("Invalid type")
	}
	@JvmStatic
	internal fun __must_not_happen(): Throwable {
		throw Error("Must not happen")
	}

	// Gradle Object Conversion
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun asProject(project: Any? = null): Project {
		if(project == null) return Common.lastContext().project
		if(project is Project) return project
		if(project is String) return Common.lastContext().project.project(project)
		if(project is BasicScript) return (project.scriptTarget as ProjectInternal)
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
	@JvmStatic
	fun forwardTask(project: Any?, filter: (Task) -> Boolean, exec: (Task) -> Unit) {
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

	// ETC
	@ExportGradle
	@JvmStatic
	fun getRelativeFile(from: File, what: File): File {
		return Paths.get(from.canonicalPath).relativize(Paths.get(what.canonicalPath)).toFile()
	}
	@ExportGradle
	@JvmStatic
	fun purgeThreadLocal(threadLocal: ThreadLocal<*>) {
		for(thread in Thread.getAllStackTraces().keys) { try {
			val FIELD_Thread_threadLocals: Field = thread.javaClass.getDeclaredField("threadLocals")
			FIELD_Thread_threadLocals.isAccessible = true
			val threadLocals = FIELD_Thread_threadLocals.get(thread) ?: continue
			val `FIELD_ThreadLocal$ThreadLocalMap_remove`: Method = threadLocals.javaClass.getDeclaredMethod("remove", ThreadLocal::class.java)
			`FIELD_ThreadLocal$ThreadLocalMap_remove`.isAccessible = true
			`FIELD_ThreadLocal$ThreadLocalMap_remove`.invoke(threadLocals, threadLocal)
		} catch(ignored: Throwable) { } }
	}

	@ExportGradle
	@JvmStatic
	val boxedToPrimitive = mapOf(
		Int::class.javaObjectType to Int::class.javaPrimitiveType,
		Long::class.javaObjectType to Long::class.javaPrimitiveType,
		Short::class.javaObjectType to Short::class.javaPrimitiveType,
		Float::class.javaObjectType to Float::class.javaPrimitiveType,
		Double::class.javaObjectType to Double::class.javaPrimitiveType,
		Char::class.javaObjectType to Char::class.javaPrimitiveType,
		Byte::class.javaObjectType to Byte::class.javaPrimitiveType,
		Boolean::class.javaObjectType to Boolean::class.javaPrimitiveType)
	fun <T : Any> prepareGroovyKotlinCache(obj: T): GroovyKotlinCache<T> {
		val kclass = obj::class
		val jclass = obj::class.java
		val cache = GroovyKotlinCache(obj, kclass, jclass)
		for(function in kclass.functions) {
			if(!function.hasAnnotation<JvmStatic>()) continue
			val qualifiedName = kclass.qualifiedName!!
			val functionName = function.name
			val annotation = function.findAnnotation<ExportGradle>()

			val id = "${qualifiedName}.${functionName}"
			if(cache.pushed.containsKey(id)) continue

			val names = ArrayList<String>()
			if(annotation?.names != null) names += annotation.names
			if(annotation != null) names += functionName
			names += "__INTERNAL_${qualifiedName.replace(".", "$")}_${functionName}"

			val closure = KotlinClosure(functionName)
			closure.overloads += getKFunctionOverloads(arrayOf(obj), function)

			cache.pushed[id] = Pair(names.toTypedArray(), closure)
		}
		for(member in kclass.memberProperties) {
			if(!member.hasAnnotation<JvmStatic>()) continue
			val qualifiedName = kclass.qualifiedName!!
			val memberName = member.name
			val annotation = member.findAnnotation<ExportGradle>()

			if(true) {
				val id = "${qualifiedName}.${memberName}.get"
				if(cache.pushed.containsKey(id)) continue

				val names = ArrayList<String>()
				if(annotation?.names != null) names += annotation.names.map { i -> "get${i.replaceFirstChar { c -> c.uppercase() }}" }
				if(annotation != null) names += "get${memberName.replaceFirstChar { c -> c.uppercase() }}"
				names += "get__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}"

				val closure = KotlinClosure(memberName)
				closure.overloads += KotlinClosure.KProperty1Overload(obj, member as KProperty1<T, *>)

				cache.pushed[id] = Pair(names.toTypedArray(), closure)
			}
			if(member is KMutableProperty1<*, *>) {
				val id = "${qualifiedName}.${memberName}.set"
				if(cache.pushed.containsKey(id)) continue

				val names = ArrayList<String>()
				if(annotation?.names != null) names += annotation.names.map { i -> "set${i.replaceFirstChar { c -> c.uppercase() }}" }
				if(annotation != null) names += "set${memberName.replaceFirstChar { c -> c.uppercase() }}"
				names += "set__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}"

				val closure = KotlinClosure(memberName)
				closure.overloads += KotlinClosure.KMutableProperty1Overload(obj, member as KMutableProperty1<T, *>)

				cache.pushed[id] = Pair(names.toTypedArray(), closure)
			}
		}
		return cache
	}

	@ExportGradle
	@JvmStatic
	fun attachObject(context: Context, cache: GroovyKotlinCache<*>) {
		attachAnyObject(context.that, cache)
		attachProjectObject(context.project, cache)
	}
	@ExportGradle
	@JvmStatic
	fun attachAnyObject(that: Any, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(that, null, pushed.first, pushed.second)
		onBuildFinished += { detachAnyObject(that, cache) }
	}
	@ExportGradle
	@JvmStatic
	fun attachProjectObject(project: Project, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(null, project, pushed.first, pushed.second)
		onBuildFinished += { detachProjectObject(project, cache) }
	}
	@ExportGradle
	@JvmStatic
	fun detachObject(context: Context, cache: GroovyKotlinCache<*>) {
		detachAnyObject(context.that, cache)
		detachProjectObject(context.project, cache)
	}
	@ExportGradle
	@JvmStatic
	fun detachAnyObject(that: Any, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(that, null, pushed.first, null)
	}
	@ExportGradle
	@JvmStatic
	fun detachProjectObject(project: Project, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(null, project, pushed.first, null)
	}
}
