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
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaMethod

object Utils {

	@ExportGradle
	@JvmStatic fun init() {
		pushKotlinToGradle(Utils)
	}
	@ExportGradle
	@JvmStatic fun deinit() {
		pullKotlinFromGradle(Utils)
	}

	@JvmStatic internal fun __invalid_type(): Throwable {
		throw Error("Invalid type")
	}
	@JvmStatic internal fun __must_not_happen(): Throwable {
		throw Error("Must not happen")
	}

	// Gradle Object Conversion
	@ExportGradle
	@JvmStatic fun asProject(project: Any? = null): Project {
		if(project == null) return Common.lastContext()
		if(project is Project) return project
		if(project is String) return Common.lastContext().project(project)
		if(project is BasicScript) return (project.scriptTarget as ProjectInternal)
		throw __invalid_type()
	}
	@ExportGradle
	@JvmStatic fun asGradle(project: Any? = null): Gradle {
		return asProject(project).gradle
	}
	@ExportGradle
	@JvmStatic fun asTask(task: Any?, project: Any? = null): Task {
		if(task is Task) return task
		if(task is String) return asProject(project).tasks.getByName(task)
		throw __invalid_type()
	}
	@ExportGradle
	@JvmStatic fun <T> asService(clazz: Class<T>, project: Any? = null): T {
		return (asProject(project) as ProjectInternal).services.get(clazz)
	}

	// Gradle Global Ext
	@ExportGradle
	@JvmStatic fun hasGlobalExt(key: String, project: Any? = null): Boolean {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		return gradleExt.has(key)
	}
	@ExportGradle
	@JvmStatic fun <T> getGlobalExt(key: String, project: Any? = null): T? {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		return if(gradleExt.has(key)) gradleExt.get(key) as T? else null
	}
	@ExportGradle
	@JvmStatic fun <T> setGlobalExt(key: String, obj: T, project: Any? = null) {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		gradleExt.set(key, obj)
	}

	// Gradle Task
	@ExportGradle
	@JvmStatic fun forwardTask(project: Any?, filter: (Task) -> Boolean, exec: (Task) -> Unit) {
		val project0 = Common.lastContext()
		val project1 = asProject(project)
		project1.tasks.filter(filter).map { task -> project0.task(task.name) {
			it.group = task.group; it.dependsOn(task) } }.forEach(exec)
	}
	@ExportGradle
	@JvmStatic fun runAfterAnotherTask(tasks: Collection<Any?>, project: Any? = null) {
		val tasks0 = tasks.map { asTask(it, project) }
		for(i in 1 until tasks0.size) tasks0[i].mustRunAfter(tasks0[i - 1])
	}

	// Gradle Daemon
	@ExportGradle
	@JvmStatic fun isSingleUseDaemon(project: Any? = null): Boolean {
		return asService(DaemonScanInfo::class.java, project).isSingleUse
	}
	@ExportGradle
	@JvmStatic fun isRunningOnDebug(): Boolean {
		return ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
			.filter { it.indexOf("-agentlib:jdwp") != -1 }.count() > 0
	}
	@ExportGradle
	@JvmStatic fun isDaemonProbablyUnstable(project: Any? = null): Boolean {
		return isSingleUseDaemon(project) || isRunningOnDebug()
	}

	// ETC
	@ExportGradle
	@JvmStatic fun getRelativeFile(from: File, what: File): File {
		return Paths.get(from.canonicalPath).relativize(Paths.get(what.canonicalPath)).toFile()
	}
	@ExportGradle
	@JvmStatic fun purgeThreadLocal(threadLocal: ThreadLocal<*>) {
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
	@ExportGradle
	@JvmStatic fun <R> closureToLambda0(closure: Closure<R>): () -> R {
		return { closure.call() }
	}
	@ExportGradle
	@JvmStatic fun <A0, R> closureToLambda1(closure: Closure<R>): (A0) -> R {
		return { closure.call(it) }
	}

	@JvmStatic val kotlinFunctionOverloading = HashMap<String, MutableMap<Method, KFunction<Any?>>>()
	@ExportGradle
	@JvmStatic val boxedToPrimitive = mapOf(
		Int::class.javaObjectType to Int::class.javaPrimitiveType,
		Long::class.javaObjectType to Long::class.javaPrimitiveType,
		Short::class.javaObjectType to Short::class.javaPrimitiveType,
		Float::class.javaObjectType to Float::class.javaPrimitiveType,
		Double::class.javaObjectType to Double::class.javaPrimitiveType,
		Char::class.javaObjectType to Char::class.javaPrimitiveType,
		Byte::class.javaObjectType to Byte::class.javaPrimitiveType,
		Boolean::class.javaObjectType to Boolean::class.javaPrimitiveType)
	@JvmStatic fun parseArgsVararg(method: Method, args: Array<out Any?>): Array<Any?> {
		val result = arrayOfNulls<Any>(args.size)
		var count = 0
		val types = method.parameterTypes
		for(i in types.indices) {
			val type = types[i]
			val arg = args[i]
			if(type.isPrimitive) { // shallow check primitive types
				if(arg == null)
					throw IllegalArgumentException("Cannot cast null to $type (primitive)")
				if(!boxedToPrimitive.containsKey(arg.javaClass))
					throw IllegalArgumentException("Cannot cast ${arg.javaClass} to $type (primitive)")
				// primitive will be boxed anyway, don't bother to convert it
				result[count++] = arg
				continue
			} else {
				// exact match
				if(arg == null || type.isAssignableFrom(arg.javaClass)) {
					result[count++] = arg
					continue
				}
				// vararg type
				if(type.isArray && type.componentType.isAssignableFrom(arg.javaClass)) {
					val componentType = type.componentType
					if(i != types.size - 1)
						throw IllegalArgumentException("Vararg must be at the end of parameters")
					for(j in i until args.size) {
						val varargv = args[j]
						if(componentType.isPrimitive) {
							if(varargv == null)
								throw IllegalArgumentException("Cannot cast null to $componentType (primitive) [vararg $j]")
							if(!boxedToPrimitive.containsKey(varargv.javaClass))
								throw IllegalArgumentException("Cannot cast ${varargv.javaClass} to $componentType (primitive) [vararg $j]")
						}
						if(varargv != null && !componentType.isAssignableFrom(varargv.javaClass))
							throw IllegalArgumentException("Cannot cast ${varargv.javaClass} to $componentType [vararg $j]")
					}
					val vararg = java.lang.reflect.Array.newInstance(type.componentType, args.size - i)
					System.arraycopy(args, i, vararg, 0, java.lang.reflect.Array.getLength(vararg))
					result[count++] = vararg
					continue
				}
				throw IllegalArgumentException("Cannot cast ${arg.javaClass} to $type")
			}
		}
		return result.copyOfRange(0, count)
	}
	@JvmStatic fun doOverloading(methods: Array<Method>, args: Array<out Any?>): Method {
		/* Length analysis
		 * n-1, if last param is vararg, then that vararg's length is 0
		 * n  , equal length, need to check more about the inheritance
		 * n.., vararg argument
		 */
		val filteredMethods = arrayOfNulls<Method>(methods.size)
		var filteredCount = 0
		val argsLength = args.size
		for(method in methods) {
			val types = method.parameterTypes
			// empty arguments
			if(types.isEmpty()) {
				if(args.isEmpty())
					filteredMethods[filteredCount++] = method
				continue
			}
			val lastParam = types.last()
			// vararg type
			if(lastParam.isArray) {
				// vararg length may vary from 0...n
				if(args.size >= types.size - 1) {
					filteredMethods[filteredCount++] = method
					continue
				}
			}
			// if length at least the same
			if(types.size == argsLength)
				filteredMethods[filteredCount++] = method
		}
		/* Type analysis
		 * every parameter will be checked if it's assignable from args
		 */
		val filteredMethods0 = arrayOfNulls<Method>(filteredCount)
		var filteredCount0 = 0
		Outer@for(i in 0 until filteredCount) {
			val method = filteredMethods[i]!!
			val types = method.parameterTypes
			for(j in types.indices) {
				val type = types[j]
				val arg = args[j]
				if(type.isPrimitive) {
					if(arg == null) continue@Outer
					if(!boxedToPrimitive.containsKey(arg.javaClass)) continue@Outer
					continue
				} else {
					if(arg == null || type.isAssignableFrom(arg.javaClass))
						continue
					if(type.isArray && type.componentType.isAssignableFrom(arg.javaClass)) {
						val componentType = type.componentType
						if(j != types.size - 1)
							continue@Outer
						for(k in j until args.size) {
							val varargv = args[k]
							if(componentType.isPrimitive) {
								if(varargv == null) continue@Outer
								if(!boxedToPrimitive.containsKey(varargv.javaClass)) continue@Outer
							}
							if(varargv != null && !componentType.isAssignableFrom(varargv.javaClass))
								continue@Outer
						}
						continue
					}
					continue@Outer
				}
			}
			filteredMethods0[filteredCount0++] = method
		}
		if(filteredCount0 == 0)
			throw IllegalStateException("No matched method definition for [${args.joinToString(", ") { it.toString() }}] \n " +
					"Available methods: \n${methods.joinToString("\n") { "- $it" }}")
		if(filteredCount0 > 1)
			throw IllegalStateException("Ambiguous method calls: \n${filteredMethods0.copyOfRange(0, filteredCount0).joinToString("\n") { "- $it" }}")
		return filteredMethods0[0]!!
	}
	@JvmStatic fun replaceArguments(args: Array<out Any?>): Array<out Any?> {
		val result = arrayOfNulls<Any?>(args.size)
		for(i in args.indices) {
			val value = args[i]
			result[i] = value
		}
		return result
	}
	@JvmStatic fun setKotlinToGradle(reflnames: Array<String>?, reflname: String, classCanonical: String, value: Any?, nameProcessor: (String) -> String) {
		val that = Common.lastContext()
		val ext = that.extensions.extraProperties
		val names = if(reflnames != null) if(reflnames.isNotEmpty()) reflnames else arrayOf(reflname) else arrayOf()
		for(name in names) ext.set(nameProcessor(name), value)
		val internalName = "__INTERNAL_${classCanonical.replace(".", "$")}_${reflname}"
		ext.set(nameProcessor(internalName), value)
	}
	@JvmStatic fun <T : Any> pushKotlinToGradle(obj: T) {
		val that = Common.lastContext()
		val kclass = obj::class as KClass<T>
		kclass.functions.forEach {
			val id = "${kclass.qualifiedName}.${it.name}"
			var overloads = kotlinFunctionOverloading[id]
			if(overloads == null) {
				overloads = mutableMapOf()
				kotlinFunctionOverloading[id] = overloads
				val annotation = it.findAnnotation<ExportGradle>()
				val callback = object: Closure<Any?>(null, that) {
					override fun call(vararg args0: Any?): Any? {
						val args = replaceArguments(args0)
						val matched = doOverloading(overloads.keys.toTypedArray(), args)
						return overloads[matched]!!.call(obj, *parseArgsVararg(matched, args))
					}
				}
				setKotlinToGradle(annotation?.names, it.name, kclass.qualifiedName!!, callback) { i -> i }
			}
			overloads[it.javaMethod!!] = it
		}
		kclass.memberProperties.forEach {
			val annotation = it.findAnnotation<ExportGradle>()
			val getCallback = object: Closure<Any?>(null, that) {
				override fun call(vararg args: Any?): Any? { return it.get(obj) }
			}
			setKotlinToGradle(annotation?.names, it.name, kclass.qualifiedName!!, getCallback) { i -> "get${i.replaceFirstChar { c -> c.uppercase() }}" }
			if(it is KMutableProperty<*>) {
				val setCallback = object: Closure<Any?>(null, that) {
					override fun call(vararg args: Any?): Any? { return it.setter.call(obj, *args) }
				}
				setKotlinToGradle(annotation?.names, it.name, kclass.qualifiedName!!, setCallback) { i -> "set${i.replaceFirstChar { c -> c.uppercase() }}" }
			}
		}
	}
	@JvmStatic fun <T : Any> pullKotlinFromGradle(obj: T) {
		val kclass = obj::class as KClass<T>
		kclass.functions.forEach {
			val annotation = it.findAnnotation<ExportGradle>()
			setKotlinToGradle(annotation?.names, it.name, kclass.qualifiedName!!, null) { i -> i }
		}
		kclass.memberProperties.forEach {
			val annotation = it.getter.findAnnotation<ExportGradle>()
			setKotlinToGradle(annotation?.names, it.name, kclass.qualifiedName!!, null) { i -> "get${i.replaceFirstChar { c -> c.uppercase() }}" }
			if(it is KMutableProperty<*>)
				setKotlinToGradle(annotation?.names, it.name, kclass.qualifiedName!!, null) { i -> "set${i.replaceFirstChar { c -> c.uppercase() }}" }
		}
	}
}
