package Gradle.GroovyKotlinInteroperability

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda
import Gradle.Strategies.ClassUtils.boxedToPrimitive
import groovy.lang.Closure
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.*
import kotlin.reflect.jvm.jvmErasure

open class KotlinClosure(
	val name: String,
	vararg _overloads: Overload
): Closure<Any?>(this) {
	val overloads = arrayListOf(*_overloads)

	override fun call(vararg args: Any?): Any? {
		val matched = doOverloading(overloads, args)
		return matched.first.callback(matched.second)
	}
	override fun getProperty(property: String): Any? {
		return when(property) {
			"name" -> name
			"overloads" -> overloads
			else -> super.getProperty(property)
		}
	}
	override fun setProperty(property: String, value: Any?) {
		when(property) {
			"overloads" -> { overloads.clear(); overloads += (value as Collection<Overload>) }
			else -> super.setProperty(property, value)
		}
	}
	override fun toString(): String {
		val result = StringBuilder()
		result.append("$name[")
		for(overload in overloads) {
			result.append("(")
			for(parameterType in overload.parameterTypes)
				result.append(parameterType.toString()).append(", ")
			if(overload.parameterTypes.isNotEmpty())
				result.setLength(result.length - 2)
			result.append("), ")
		}
		if(overloads.isNotEmpty())
			result.setLength(result.length - 2)
		result.append("]")
		return result.toString()
	}

	open class Overload(
		val parameterTypes: Array<out Class<*>>,
		val callback: (Array<out Any?>) -> Any?,
		var priority: Int = 0
	) {
		val parameterCount = parameterTypes.size
	}
	open class IgnoreSelfOverload(
		val selfClass: Class<*>,
		val overload: Overload
	): Overload(arrayOf(selfClass, *overload.parameterTypes), { overload.callback(it.copyOfRange(1, it.size)) }, -1) {
		override fun toString(): String {
			return "^$overload"
		}
	}
	open class WithSelfOverload(
		val self: Any?,
		val overload: Overload
	): Overload(overload.parameterTypes.copyOfRange(1, overload.parameterTypes.size), { overload.callback(arrayOf(self, *it)) }, -1) {
		override fun toString(): String {
			return "*$overload"
		}
	}
	open class MethodOverload(
		val owner: Any?,
		val method: Method
	): Overload(method.parameterTypes, { method.invoke(owner, *it) }) {
		override fun toString(): String {
			return method.toString()
		}
	}
	open class FieldGetOverload(
		val owner: Any?,
		val field: Field
	): Overload(arrayOf(), { field.get(owner) }) {
		override fun toString(): String {
			return "get${field.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class FieldSetOverload(
		val owner: Any?,
		val field: Field
	): Overload(arrayOf(field.type), { field.set(owner, it[0]) }) {
		override fun toString(): String {
			return "set${field.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KLambdaOverload(
		val lambda: (Array<out Any?>) -> Any?
	): Overload(arrayOf(Array<Any?>::class.java), { val args = it[0] ?: arrayOf<Any?>(); lambda(args as Array<out Any?>) }) {
		override fun toString(): String {
			return lambda.toString()
		}
	}
	open class KFunctionOverload<R>(
		val owners: Array<Any?>,
		val function: KFunction<R>,
		val kParameters: Array<KParameter>
	): Overload(kParameters.filter { it.kind != KParameter.Kind.INSTANCE }.map { it.type.jvmErasure.java }.toTypedArray(),
		{
			val args = HashMap<KParameter, Any?>();
			var offset = 0
			kParameters.forEach { v ->
				if(v.kind != KParameter.Kind.INSTANCE) args[v] = it[v.index - offset]
				else args[v] = owners[offset++]
			}
			function.callBy(args)
		}
	) {
		override fun toString(): String {
			val stringBuilder = StringBuilder()
			with(stringBuilder) {
				append("fun ").append(function.name).append("(")
				var count = 0
				for(param in kParameters) {
					if(param.kind == KParameter.Kind.INSTANCE) continue
					append(param.type).append(", ")
					count++
				}
				if(count != 0)
					setLength(length - 2)
				append(")").append(": ")
				append(function.returnType.toString())
			}
			return stringBuilder.toString()
		}
	}
	open class KProperty0Overload<V>(
		val property: KProperty0<V>
	): Overload(arrayOf(), { property.get() }) {
		override fun toString(): String {
			return "get${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KMutableProperty0Overload<V>(
		val property: KMutableProperty0<V>
	): Overload(arrayOf(property.returnType.jvmErasure.java), { property.set(it[0] as V) }) {
		override fun toString(): String {
			return "set${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KProperty1Overload<T, V>(
		val owner: T,
		val property: KProperty1<T, V>
	): Overload(arrayOf(), { property.get(owner) }) {
		override fun toString(): String {
			return "get${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KMutableProperty1Overload<T, V>(
		val owner: T,
		val property: KMutableProperty1<T, V>
	): Overload(arrayOf(property.returnType.jvmErasure.java), { property.set(owner, it[0] as V) }) {
		override fun toString(): String {
			return "set${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KProperty2Overload<D, E, V>(
		val owner1: D,
		val owner2: E,
		val property: KProperty2<D, E, V>
	): Overload(arrayOf(), { property.get(owner1, owner2) }) {
		override fun toString(): String {
			return "get${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KMutableProperty2Overload<D, E, V>(
		val owner1: D,
		val owner2: E,
		val property: KMutableProperty2<D, E, V>
	): Overload(arrayOf(property.returnType.jvmErasure.java), { property.set(owner1, owner2, it[0] as V) }) {
		override fun toString(): String {
			return "set${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}

	companion object KotlinClosureUtils {
		@JvmStatic private var cache: GroovyKotlinCache<KotlinClosureUtils>? = null

		@JvmStatic
		fun construct() {
			cache = prepareGroovyKotlinCache(KotlinClosureUtils)
			addInjectScript(cache!!)
		}
		@JvmStatic
		fun destruct() {
			removeInjectScript(cache!!)
			cache = null
		}

		@ExportGradle @JvmStatic
		fun <R> getKFunctionOverloads(owners: Array<Any?>, function: KFunction<R>): Array<Overload> {
			val result = ArrayList<KFunctionOverload<out R>>()
			val parameters = function.parameters
			val lastOptionalParameters = arrayListOf(*parameters.toTypedArray())
			result += KFunctionOverload(owners, function, lastOptionalParameters.toTypedArray())
			for(i in (parameters.size - 1) downTo 0) {
				val parameter = parameters[i]
				if(parameter.kind == KParameter.Kind.INSTANCE) continue
				// Optional parameters in the middle is a bit confusing
				if(!parameter.isOptional) break
				lastOptionalParameters -= parameter
				result += KFunctionOverload(owners, function, lastOptionalParameters.toTypedArray())
			}
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun <V> getKProperty0Overloads(property: KProperty0<V>): Array<Overload> {
			val result = ArrayList<Overload>()
			result += KProperty0Overload(property)
			if(property is KMutableProperty0)
				result += KMutableProperty0Overload(property)
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun <T, V> getKProperty1Overloads(owner: T, property: KProperty1<T, V>): Array<Overload> {
			val result = ArrayList<Overload>()
			result += KProperty1Overload(owner, property)
			if(property is KMutableProperty1)
				result += KMutableProperty1Overload(owner, property)
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun <D, E, V> getKProperty2Overloads(owner1: D, owner2: E, property: KProperty2<D, E, V>): Array<Overload> {
			val result = ArrayList<Overload>()
			result += KProperty2Overload(owner1, owner2, property)
			if(property is KMutableProperty2)
				result += KMutableProperty2Overload(owner1, owner2, property)
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun doOverloading(overloads: List<Overload>, args: Array<out Any?>): Pair<Overload, Array<Any?>> {
			data class CallData(
				var overload: Overload,
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
			val filteredOverloads = mutableListOf<CallData>()
			for(overload in overloads) {
				val types = overload.parameterTypes
				// empty arguments
				if(types.isEmpty()) {
					if(args.isEmpty())
						filteredOverloads += CallData(overload)
					continue
				}
				val lastParam = types.last()
				// vararg type
				if(lastParam.isArray) {
					// vararg length may vary from 0...n
					if(args.size >= types.size - 1) {
						filteredOverloads += CallData(overload)
						continue
					}
				}
				// if length at least the same
				if(types.size == args.size)
					filteredOverloads += CallData(overload)
			}
			/* Type analysis
			 * every parameter will be checked if it's assignable from args
			 */
			val pushArg: (CallData, Int, Any?) -> Unit = { data, i, arg ->
				if(data.args == null)
					data.args = arrayOfNulls(data.overload.parameterCount)
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
			Outer@for(data in filteredOverloads) {
				val overload = data.overload
				val types = overload.parameterTypes
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
						data.changedArgsCount++
						val vararg = java.lang.reflect.Array.newInstance(componentType, args.size - i)
						System.arraycopy(args, i, vararg, 0, java.lang.reflect.Array.getLength(vararg))
						pushArg(data, i, vararg)
						continue
					}
					data.error = "Cannot cast ${arg.javaClass} to $type"
					continue@Outer
				}
			}
			val notError = filteredOverloads.filter { it.error == null }
			// Only happen when the parameter is empty,
			// thus args.size always zero. But whatever.
			for(data in notError) if(data.args == null)
				data.args = arrayOfNulls(args.size)
			if(notError.isEmpty())
				throw IllegalStateException("No matched method definition for [${args.joinToString(", ") { if(it != null) it::class.java.toString() else "NULL" }}]\n" +
						"\t\twith values [${args.joinToString(", ") { it.toString() }}]\n" +
						"Filtered overloads: \n${filteredOverloads.joinToString("\n") { "\t- ${it.overload} (${it.error})" }}\n" +
						"Non-filtered overloads: \n${overloads.filter { o -> filteredOverloads.find { it.overload == o } == null }.joinToString("\n") { "\t- $it" }}")
			if(notError.size > 1) {
				/* Exact match
				 */
				val minNotExactMatchCount = notError.minOf { it.notExactMatchCount }
				val notExactMatch = notError.filter { it.notExactMatchCount == minNotExactMatchCount }
				if(notExactMatch.size == 1) {
					val first = notExactMatch.first()
					return Pair(first.overload, first.args!!)
				}
				/* Prefer args not changed
				 */
				val minChangedArgsCount = notExactMatch.minOf { it.changedArgsCount }
				val notChangedArgs = notExactMatch.filter { it.changedArgsCount == minChangedArgsCount }
				if(notChangedArgs.size == 1) {
					val first = notChangedArgs.first()
					return Pair(first.overload, first.args!!)
				}
				/* Priorites
				 */
				val maxPriorityValue = notChangedArgs.maxOf { it.overload.priority }
				val maxPriority = notChangedArgs.filter { it.overload.priority == maxPriorityValue }
				if(maxPriority.size == 1) {
					val first = maxPriority.first()
					return Pair(first.overload, first.args!!)
				}
				throw IllegalStateException("Ambiguous overloads call for [${args.joinToString(", ") { if(it != null) it::class.java.toString() else "NULL" }}]\n" +
						"\t\twith values [${args.joinToString(", ") { it.toString() }}]\n" +
						"Matched overloads: \n${notError.joinToString("\n") { "\t- ${it.overload} (Not Exact: ${it.notExactMatchCount}, Changed Args: ${it.changedArgsCount}, Priority: ${it.overload.priority})" }}")
			}
			val first = notError.first()
			return Pair(first.overload, first.args!!)
		}
	}
}
