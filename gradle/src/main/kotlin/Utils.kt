import Common.addOnConfigFinished
import Common.context
import Common.groovyKotlinCaches
import Common.lastContext
import GroovyInteroperability.setKotlinToGroovy
import KotlinClosure.Companion.getKFunctionOverloads
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.invocation.Gradle
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.groovy.scripts.BasicScript
import org.gradle.internal.scripts.GradleScript
import org.gradle.launcher.daemon.server.scaninfo.DaemonScanInfo
import java.io.*
import java.lang.management.ManagementFactory
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties


object Utils {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(Utils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
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
	@JvmStatic
	fun parseProperty(name: String): Triple<Boolean, Boolean, Boolean> {
		val isGetter = name.startsWith("get") && (!name[3].isLetter() || name[3].isUpperCase())
		val isGetterBoolean = name.startsWith("is") && (!name[2].isLetter() || name[2].isUpperCase())
		val isSetter = name.startsWith("set") && (!name[3].isLetter() || name[3].isUpperCase())
		return Triple(isGetter, isGetterBoolean, isSetter)
	}

	// IO things
	@ExportGradle
	@JvmStatic
	fun copyStream(inputStream: InputStream, outputStream: OutputStream) {
		val buffer = ByteArray(8192)
		var length = 0L
		var read = 0
		while(!Thread.currentThread().isInterrupted && inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
			outputStream.write(buffer, 0, read)
			length += read.toLong()
		}
		if(Thread.currentThread().isInterrupted) throw InterruptedIOException()
	}
	@ExportGradle
	@JvmStatic
	fun fileBytes(file: File): ByteArray {
		FileInputStream(file).use { inputStream ->
			val outputStream = ByteArrayOutputStream()
			return try {
				copyStream(inputStream, outputStream)
				outputStream.close()
				outputStream.toByteArray()
			} finally {
				outputStream.reset()
			}
		}
	}
	@ExportGradle
	@JvmStatic
	fun checksumJava(digest: String, bytes: ByteArray): String {
		val messageDigest = MessageDigest.getInstance(digest)
		messageDigest.update(bytes, 0, bytes.size)
		val digestResult = messageDigest.digest()
		val builder = StringBuilder()
		for(byte in digestResult)
			builder.append(((byte.toInt() and 0xff) + 0x100).toString(16).substring(1))
		return builder.toString()
	}

	// Function manipulation
	@ExportGradle
	@JvmStatic
	fun lash(that: Any, closure: KotlinClosure, vararg prependArgs: Any?): KotlinClosure {
		val result = KotlinClosure("lash ${closure.name}")
		result.overloads += KotlinClosure.KLambdaOverload lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(prependArgs.size + args.size)
			System.arraycopy(prependArgs, 0, finalArgs, 0, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size, args.size)
			return@lambda context(that) lambda1@{ return@lambda1 closure.call(*finalArgs) }
		}
		return result
	}
	@ExportGradle
	@JvmStatic
	fun lash(closure: KotlinClosure): KotlinClosure {
		return lash(lastContext().that, closure)
	}
	@ExportGradle
	@JvmStatic
	fun bind(self: Any?, closure: KotlinClosure, vararg prependArgs: Any?): KotlinClosure {
		val result = KotlinClosure("bind ${closure.name}")
		result.overloads += KotlinClosure.KLambdaOverload lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(1 + prependArgs.size + args.size)
			finalArgs[0] = self
			System.arraycopy(prependArgs, 0, finalArgs, 1, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size + 1, args.size)
			return@lambda closure.call(*finalArgs)
		}
		return result
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
	fun <T : Any> prepareGroovyKotlinCache(obj: T): GroovyKotlinCache<T> {
		val kclass = obj::class
		val jclass = obj::class.java
		val cache = GroovyKotlinCache(obj, kclass, jclass)
		for(function in kclass.functions) {
			val qualifiedName = kclass.qualifiedName!!
			val functionName = function.name
			val annotation = function.findAnnotation<ExportGradle>()

			if(annotation != null && !annotation.asProperty) {
				val id = "${qualifiedName}.${functionName}"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedMethod)
					throw Error("Id $id is defined but not as an InjectedMethod")
				var injected = injected0 as? GroovyKotlinCache.InjectedMethod
				if(injected == null) {
					val names = mutableSetOf("__INTERNAL_${qualifiedName.replace(".", "$")}_${functionName}")
					val closure = KotlinClosure(functionName)
					injected = GroovyKotlinCache.InjectedMethod(names, closure)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += functionName
					else injected.names += annotation.names
				val originalOverloads = getKFunctionOverloads(arrayOf(obj), function)
				injected.closure.overloads += originalOverloads
				if(annotation != null) {
					if(annotation.additionalOverloads == 1)
						injected.closure.overloads += originalOverloads.map { KotlinClosure.IgnoreSelfOverload(GradleScript::class.java, it) }
					if(annotation.additionalOverloads == 2)
						injected.closure.overloads += originalOverloads.map { KotlinClosure.WithSelfOverload(null, it) }
				}
			} else {
				val id = "${qualifiedName}.${functionName}.get"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedMethodAsProperty)
					throw Error("Id $id is defined but not as an InjectedMethodAsProperty")
				var injected = injected0 as? GroovyKotlinCache.InjectedMethodAsProperty
				if(injected == null) {
					val names = mutableSetOf("get__INTERNAL_${qualifiedName.replace(".", "$")}_${functionName}")
					val closure = KotlinClosure(functionName)
					val methodClosure = KotlinClosure(functionName)
					closure.overloads += KotlinClosure.KLambdaOverload { methodClosure }
					injected = GroovyKotlinCache.InjectedMethodAsProperty(names, closure, methodClosure)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += "get${functionName.replaceFirstChar { c -> c.uppercase() }}"
					else injected.names += annotation.names.map { i -> "get${i.replaceFirstChar { c -> c.uppercase() }}" }
				val originalOverloads = getKFunctionOverloads(arrayOf(obj), function)
				injected.methodClosure.overloads += originalOverloads
				if(annotation != null) {
					if(annotation.additionalOverloads == 1)
						injected.closure.overloads += originalOverloads.map { KotlinClosure.IgnoreSelfOverload(GradleScript::class.java, it) }
					if(annotation.additionalOverloads == 2)
						injected.closure.overloads += originalOverloads.map { KotlinClosure.WithSelfOverload(null, it) }
				}
			}
		}
		for(member in kclass.memberProperties) {
			val qualifiedName = kclass.qualifiedName!!
			val memberName = member.name
			val annotation = member.findAnnotation<ExportGradle>()

			if(true) {
				val id = "${qualifiedName}.${memberName}.get"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertyGetter)
					throw Error("Id $id is defined but not as an InjectedPropertyGetter")
				var injected = injected0 as? GroovyKotlinCache.InjectedPropertyGetter
				if(injected == null) {
					val names = mutableSetOf("get__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}")
					val closure = KotlinClosure(memberName)
					injected = GroovyKotlinCache.InjectedPropertyGetter(names, closure)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += "get${memberName.replaceFirstChar { c -> c.uppercase() }}"
					else injected.names += annotation.names.map { i -> "get${i.replaceFirstChar { c -> c.uppercase() }}" }
				val originalOverload = KotlinClosure.KProperty1Overload(obj, member as KProperty1<T, *>)
				injected.closure.overloads += originalOverload
				if(annotation != null) {
					if(annotation.additionalOverloads == 1)
						injected.closure.overloads += KotlinClosure.IgnoreSelfOverload(GradleScript::class.java, originalOverload)
					if(annotation.additionalOverloads == 2)
						injected.closure.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
				}
			}
			if(member is KMutableProperty1<*, *> && annotation != null && annotation.allowSet) {
				val id = "${qualifiedName}.${memberName}.set"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertySetter)
					throw Error("Id $id is defined but not as an InjectedPropertySetter")
				var injected = injected0 as? GroovyKotlinCache.InjectedPropertySetter
				if(injected == null) {
					val names = mutableSetOf("set__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}")
					val closure = KotlinClosure(memberName)
					injected = GroovyKotlinCache.InjectedPropertySetter(names, closure)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += "set${memberName.replaceFirstChar { c -> c.uppercase() }}"
					else injected.names += annotation.names.map { i -> "set${i.replaceFirstChar { c -> c.uppercase() }}" }
				val originalOverload = KotlinClosure.KMutableProperty1Overload(obj, member as KMutableProperty1<T, *>)
				injected.closure.overloads += originalOverload
				if(annotation != null) {
					if(annotation.additionalOverloads == 1)
						injected.closure.overloads += KotlinClosure.IgnoreSelfOverload(GradleScript::class.java, originalOverload)
					if(annotation.additionalOverloads == 2)
						injected.closure.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
				}
			}
		}
		return cache
	}
	@JvmStatic
	fun prepareGroovyKotlinCache(obj: Any, methods: Map<String, (Array<out Any?>) -> Any?>, getter: Map<String, (Array<out Any?>) -> Any?>, setter: Map<String, (Array<out Any?>) -> Any?>): GroovyKotlinCache<*> {
		val kclass = obj::class
		val jclass = obj::class.java
		val cache = GroovyKotlinCache(obj, kclass, jclass)
		for(entry in methods) {
			val id = entry.key
			val callback = entry.value
			val injected0 = cache.pushed[id]
			if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedMethod)
				throw Error("Id $id is defined but not as an InjectedMethod")
			var injected = injected0 as? GroovyKotlinCache.InjectedMethod
			if(injected == null) {
				val names = mutableSetOf("__INTERNAL_${entry.key}")
				val closure = KotlinClosure(id)
				injected = GroovyKotlinCache.InjectedMethod(names, closure)
				cache.pushed[id] = injected
			}
			injected.names += entry.key
			val originalOverload = KotlinClosure.KLambdaOverload(callback)
			injected.closure.overloads += originalOverload
		}
		for(entry in getter) {
			val id = "${entry.key}.get"
			val callback = entry.value
			val injected0 = cache.pushed[id]
			if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertyGetter)
				throw Error("Id $id is defined but not as an InjectedPropertyGetter")
			var injected = injected0 as? GroovyKotlinCache.InjectedPropertyGetter
			if(injected == null) {
				val names = mutableSetOf("get__INTERNAL_${entry.key}")
				val closure = KotlinClosure(id)
				injected = GroovyKotlinCache.InjectedPropertyGetter(names, closure)
				cache.pushed[id] = injected
			}
			injected.names += "get${entry.key.replaceFirstChar { c -> c.uppercase() }}"
			val originalOverload = KotlinClosure.KLambdaOverload(callback)
			injected.closure.overloads += originalOverload
		}
		for(entry in setter) {
			val id = "${entry.key}.set"
			val callback = entry.value
			val injected0 = cache.pushed[id]
			if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertySetter)
				throw Error("Id $id is defined but not as an InjectedPropertySetter")
			var injected = injected0 as? GroovyKotlinCache.InjectedPropertySetter
			if(injected == null) {
				val names = mutableSetOf("set__INTERNAL_${entry.key}")
				val closure = KotlinClosure(id)
				injected = GroovyKotlinCache.InjectedPropertySetter(names, closure)
				cache.pushed[id] = injected
			}
			injected.names += "set${entry.key.replaceFirstChar { c -> c.uppercase() }}"
			val originalOverload = KotlinClosure.KLambdaOverload(callback)
			injected.closure.overloads += originalOverload
		}
		return cache
	}

	@ExportGradle
	@JvmStatic
	fun attachObject(context: Context, cache: GroovyKotlinCache<*>) {
		val klass = context.that::class.java
		val METHOD_BuildScript_target = klass.getMethod("getScriptTarget")
		METHOD_BuildScript_target.isAccessible = true
		val target = METHOD_BuildScript_target.invoke(context.that)
		// Mostly, any property getter will use context.that
		// As shown in BasicScript$ScriptDynamicObject.tryGetProperty
		attachAnyObject(context.that, cache)
		// But, the setter isn't going through the scriptObject
		// As shown in BasicScript$ScriptDynamicObject.trySetProperty
		// So we need to inject to script target. Call to script target
		// is shown in CompositeDynamicObject.trySetProperty
		attachAnyObject(target, cache)
		attachProjectObject(context.project, cache)
	}
	@ExportGradle
	@JvmStatic
	fun attachAnyObject(that: Any, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(that, null, pushed.names.toTypedArray(), pushed.closure)
		addOnConfigFinished(0) { detachAnyObject(that, cache) }
	}
	@ExportGradle
	@JvmStatic
	fun attachProjectObject(project: Project, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(null, project, pushed.names.toTypedArray(), pushed.closure)
		addOnConfigFinished(0) { detachProjectObject(project, cache) }
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
			setKotlinToGroovy(that, null, pushed.names.toTypedArray(), null)
	}
	@ExportGradle
	@JvmStatic
	fun detachProjectObject(project: Project, cache: GroovyKotlinCache<*>) {
		for(pushed in cache.pushed.values)
			setKotlinToGroovy(null, project, pushed.names.toTypedArray(), null)
	}
}
