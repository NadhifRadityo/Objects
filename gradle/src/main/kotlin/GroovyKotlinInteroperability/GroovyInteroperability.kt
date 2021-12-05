package GroovyKotlinInteroperability

import Common.addOnConfigFinished
import Common.groovyKotlinCaches
import Context
import GroovyKotlinInteroperability.GroovyManipulation.setKotlinToGroovy
import org.gradle.api.Project
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties

object GroovyInteroperability {
	@JvmStatic private var cache: GroovyKotlinCache<GroovyInteroperability>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(GroovyInteroperability)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

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
				val originalOverloads = KotlinClosure.getKFunctionOverloads(arrayOf(obj), function)
				injected.closure.overloads += originalOverloads
				if(annotation == null || annotation.additionalOverloads == 1)
					injected.closure.overloads += originalOverloads.map { KotlinClosure.IgnoreSelfOverload(Any::class.java, it) }
				if(annotation != null && annotation.additionalOverloads == 2)
					injected.closure.overloads += originalOverloads.map { KotlinClosure.WithSelfOverload(null, it) }
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
				val originalOverloads = KotlinClosure.getKFunctionOverloads(arrayOf(obj), function)
				injected.methodClosure.overloads += originalOverloads
				if(annotation == null || annotation.additionalOverloads == 1)
					injected.methodClosure.overloads += originalOverloads.map { KotlinClosure.IgnoreSelfOverload(Any::class.java, it) }
				if(annotation != null && annotation.additionalOverloads == 2)
					injected.methodClosure.overloads += originalOverloads.map { KotlinClosure.WithSelfOverload(null, it) }
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
				if(annotation == null || annotation.additionalOverloads == 1)
					injected.closure.overloads += KotlinClosure.IgnoreSelfOverload(Any::class.java, originalOverload)
				if(annotation != null && annotation.additionalOverloads == 2)
					injected.closure.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
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
				if(annotation == null || annotation.additionalOverloads == 1)
					injected.closure.overloads += KotlinClosure.IgnoreSelfOverload(Any::class.java, originalOverload)
				if(annotation != null && annotation.additionalOverloads == 2)
					injected.closure.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
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
				val names = mutableSetOf(entry.key)
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
				val names = mutableSetOf("get${entry.key.replaceFirstChar { c -> c.uppercase() }}")
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
				val names = mutableSetOf("set${entry.key.replaceFirstChar { c -> c.uppercase() }}")
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