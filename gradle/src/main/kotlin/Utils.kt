import Common.onBuildFinished
import GroovyInteroperability.closureToLambda
import GroovyInteroperability.setKotlinToGroovy
import groovy.lang.Closure
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
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaMethod

object Utils {

	@JvmStatic
	fun init() {
		pushKotlinToGradle(Utils)
	}
	@JvmStatic
	fun deinit() {
		pullKotlinFromGradle(Utils)
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
	@JvmStatic
	fun doOverloading(methods: Array<Method>, args: Array<out Any?>): Pair<Method, Array<Any?>> {
		data class CallData(
			var method: Method,
			var args: Array<Any?>? = null,
			var error: String? = null,
			var notExactMatchCount: Int = 0,
			var changedArgsCount: Int = 0
		)
		/* Length analysis
		 * n-1, if last param is vararg, then that vararg's length is 0
		 * n  , equal length, need to check more about the inheritance
		 * n.., vararg argument
		 */
		val filteredMethods = mutableListOf<CallData>()
		for(method in methods) {
			val types = method.parameterTypes
			// empty arguments
			if(types.isEmpty()) {
				if(args.isEmpty())
					filteredMethods += CallData(method)
				continue
			}
			val lastParam = types.last()
			// vararg type
			if(lastParam.isArray) {
				// vararg length may vary from 0...n
				if(args.size >= types.size - 1) {
					filteredMethods += CallData(method)
					continue
				}
			}
			// if length at least the same
			if(types.size == args.size)
				filteredMethods += CallData(method)
		}
		/* Type analysis
		 * every parameter will be checked if it's assignable from args
		 */
		val pushArg: (CallData, Int, Any?) -> Unit = { data, i, arg ->
			if(data.args == null)
				data.args = arrayOfNulls(data.method.parameterCount)
			data.args!![i] = arg
		}
		val tryChangeArg: (CallData, Any?, Class<*>) -> Any? = { data, arg, type ->
			var result = arg
			if(arg is Closure<*> && Function::class.java.isAssignableFrom(type)) {
				result = closureToLambda(arg, type as Class<Function<*>>)
				data.changedArgsCount++
			}
			result
		}
		Outer@for(data in filteredMethods) {
			val method = data.method
			val types = method.parameterTypes
			for(i in types.indices) {
				val type = types[i]
				val arg = tryChangeArg(data, if(i < args.size) args[i] else null, type)
				// Strict primitive checking
				if(type.isPrimitive) {
					if(arg == null) {
						data.error = "Cannot cast null to $type (primitive)"
						continue@Outer
					}
					if(!boxedToPrimitive.containsKey(arg.javaClass)) {
						data.error = "Cannot cast ${arg.javaClass} to $type (primitive)"
						continue@Outer
					}
					pushArg(data, i, arg)
					continue
				}
				// Empty vararg
				if(type.isArray && i == args.size) {
					val componentType = type.componentType
					if(i != types.size - 1) {
						data.error = "Vararg must be at the end of parameters"
						continue@Outer
					}
					data.notExactMatchCount++
					val vararg = java.lang.reflect.Array.newInstance(componentType, 0)
					pushArg(data, i, vararg)
					continue
				}
				// Inheritance
				if(arg == null || type.isAssignableFrom(arg.javaClass)) {
					if(arg != null && type != arg.javaClass)
						data.notExactMatchCount++
					pushArg(data, i, arg)
					continue
				}
				// Non-empty vararg
				if(type.isArray && type.componentType.isAssignableFrom(arg.javaClass)) {
					val componentType = type.componentType
					if(i != types.size - 1) {
						data.error = "Vararg must be at the end of parameters"
						continue@Outer
					}
					for(j in i until args.size) {
						val varargv = args[j]
						if(componentType.isPrimitive) {
							if(varargv == null) {
								data.error = "Cannot cast null to $componentType (primitive) [vararg $j]"
								continue@Outer
							}
							if(!boxedToPrimitive.containsKey(varargv.javaClass)) {
								data.error = "Cannot cast ${varargv.javaClass} to $componentType (primitive) [vararg $j]"
								continue@Outer
							}
							continue
						}
						if(varargv == null || componentType.isAssignableFrom(varargv.javaClass))
							continue
						data.error = "Cannot cast ${varargv.javaClass} to $componentType [vararg $j]"
						continue@Outer
					}
					val vararg = java.lang.reflect.Array.newInstance(componentType, args.size - i)
					System.arraycopy(args, i, vararg, 0, java.lang.reflect.Array.getLength(vararg))
					pushArg(data, i, vararg)
					continue
				}
				data.error = "Cannot cast ${arg.javaClass} to $type"
				continue@Outer
			}
		}
		val notError = filteredMethods.filter { it.error == null }
		// Only happen when the parameter is empty,
		// thus args.size always zero. But whatever.
		for(data in notError) if(data.args == null)
			data.args = arrayOfNulls(args.size)
		if(notError.isEmpty())
			throw IllegalStateException("No matched method definition for [${args.joinToString(", ") { if(it != null) it::class.java.toString() else "NULL" }}]\n" +
					"\t\twith values [${args.joinToString(", ") { it.toString() }}]\n" +
					"Filtered methods: \n${filteredMethods.joinToString("\n") { "\t- ${it.method} (${it.error})" }}\n" +
					"Non-filtered methods: \n${methods.filter { m -> filteredMethods.find { it.method == m } == null }.joinToString("\n") { "\t- $it" }}")
		if(notError.size > 1) {
			/* Exact match
			 */
			val minNotExactMatchCount = notError.minOf { it.notExactMatchCount }
			val exactMatch = notError.filter { it.notExactMatchCount == minNotExactMatchCount }
			if(exactMatch.size == 1) {
				val first = exactMatch.first()
				return Pair(first.method, first.args!!)
			}
			/* Prefer args not changed
			 */
			val minChangedArgsCount = exactMatch.minOf { it.changedArgsCount }
			val notChangedArgs = exactMatch.filter { it.changedArgsCount == minChangedArgsCount }
			if(notChangedArgs.size == 1) {
				val first = notChangedArgs.first()
				return Pair(first.method, first.args!!)
			}
			throw IllegalStateException("Ambiguous method calls [${args.joinToString(", ") { it.toString() }}] \n " +
					"Matched methods: \n${notError.joinToString("\n") { "- ${it.method}" }}")
		}
		val first = notError.first()
		return Pair(first.method, first.args!!)
	}
	data class PushedGroovy(
		val reference: Any,
		val names: Array<String>?,
		val name: String,
		val qualifiedName: String,
		val callback: Closure<*>,
		val nameProcessor: (String) -> String
	)
	@JvmStatic
	val pushedGroovies = ArrayList<PushedGroovy>()
	@JvmStatic
	val kotlinFunctionOverloading = HashMap<String, ArrayList<Method>>()
	@JvmStatic
	fun <T : Any> pushKotlinToGradle(obj: T) {
		val kclass = obj::class as KClass<T>
		val jclass = obj::class.java
		val functions = kclass.functions
		for(function in functions) {
			val id = "${kclass.qualifiedName}.${function.name}"
			var overloads = kotlinFunctionOverloading[id]
			// JvmOverloads does not available in the runtime
//			val jvmOverloadsAnnotated = function.hasAnnotation<JvmOverloads>()
//			val containsOptional = function.parameters.any { function.isOptional }
//			if(containsOptional && !jvmOverloadsAnnotated)
//				System.err.println("Method $function contains optional parameters but doesn't annotate @JvmOverloads")
			if(overloads != null) continue
			overloads = ArrayList()
			kotlinFunctionOverloading[id] = overloads
			val annotation = function.findAnnotation<ExportGradle>()
			val callback = object: Closure<Any?>(null, null) {
				override fun call(vararg args: Any?): Any? {
					val matched = doOverloading(overloads.toTypedArray(), args)
					return matched.first.invoke(null, *matched.second)
				}
				override fun toString(): String {
					return function.toString()
				}
			}
			pushedGroovies += PushedGroovy(function, annotation?.names, function.name, kclass.qualifiedName!!, callback) { i -> i }
			val baseName = function.javaMethod?.name ?: function.name
			jclass.declaredMethods.filter { m -> m.name == baseName }.forEach {
					m -> if(!overloads.contains(m)) overloads += m; }
		}
		for(member in kclass.memberProperties) {
			val annotation = member.findAnnotation<ExportGradle>()
			val getCallback = object: Closure<Any?>(null, null) {
				override fun call(vararg args: Any?): Any? { return member.get(obj) }
				override fun toString(): String { return member.toString() }
			}
			pushedGroovies += PushedGroovy(member, annotation?.names, member.name, kclass.qualifiedName!!, getCallback) { i -> "get${i.replaceFirstChar { c -> c.uppercase() }}" }
			if(member is KMutableProperty<*>) {
				val setCallback = object: Closure<Any?>(null, null) {
					override fun call(vararg args: Any?): Any? { return member.setter.call(obj, *args) }
					override fun toString(): String { return member.toString() }
				}
				pushedGroovies += PushedGroovy(member, annotation?.names, member.name, kclass.qualifiedName!!, setCallback) { i -> "set${i.replaceFirstChar { c -> c.uppercase() }}" }
			}
		}
	}
	@JvmStatic
	fun <T : Any> pullKotlinFromGradle(obj: T) {
		val kclass = obj::class as KClass<T>
		kotlinFunctionOverloading.clear()
		for(function in kclass.functions) {
			pushedGroovies.removeIf { it.reference == function }
		}
		for(member in kclass.memberProperties) {
			pushedGroovies.removeIf { it.reference == member }
		}
	}

	@JvmStatic
	internal val injectedAnyObjects = HashMap<Any, Array<PushedGroovy>>()
	@JvmStatic
	internal val injectedProjectObjects = HashMap<Project, Array<PushedGroovy>>()
	@ExportGradle
	@JvmStatic
	fun attachObject(context: Context) {
		attachAnyObject(context.that)
		attachProjectObject(context.project)
	}
	@ExportGradle
	@JvmStatic
	fun attachAnyObject(that: Any) {
		if(injectedAnyObjects.containsKey(that))
			detachAnyObject(that)
		if(pushedGroovies.isEmpty())
			return
		val appliedGroovies = pushedGroovies.toTypedArray()
		injectedAnyObjects[that] = appliedGroovies
		for(pushed in appliedGroovies)
			setKotlinToGroovy(that, null, pushed.names, pushed.name, pushed.qualifiedName, pushed.callback, pushed.nameProcessor)
		onBuildFinished += { detachAnyObject(that) }
	}
	@ExportGradle
	@JvmStatic
	fun attachProjectObject(project: Project) {
		if(injectedProjectObjects.containsKey(project))
			detachProjectObject(project)
		if(pushedGroovies.isEmpty())
			return
		val appliedGroovies = pushedGroovies.toTypedArray()
		injectedProjectObjects[project] = appliedGroovies
		for(pushed in appliedGroovies)
			setKotlinToGroovy(null, project, pushed.names, pushed.name, pushed.qualifiedName, pushed.callback, pushed.nameProcessor)
		onBuildFinished += { detachProjectObject(project) }
	}
	@ExportGradle
	@JvmStatic
	fun detachObject(context: Context) {
		detachAnyObject(context.that)
		detachProjectObject(context.project)
	}
	@ExportGradle
	@JvmStatic
	fun detachAnyObject(that: Any) {
		val appliedGroovies = injectedAnyObjects.remove(that) ?: return
		for(pushed in appliedGroovies)
			setKotlinToGroovy(that, null, pushed.names, pushed.name, pushed.qualifiedName, null, pushed.nameProcessor)
	}
	@ExportGradle
	@JvmStatic
	fun detachProjectObject(project: Project) {
		val appliedGroovies = injectedProjectObjects.remove(project) ?: return
		for(pushed in appliedGroovies)
			setKotlinToGroovy(null, project, pushed.names, pushed.name, pushed.qualifiedName, null, pushed.nameProcessor)
	}
}
