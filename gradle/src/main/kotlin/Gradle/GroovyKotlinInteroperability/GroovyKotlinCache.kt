package Gradle.GroovyKotlinInteroperability

import kotlin.reflect.KClass

class GroovyKotlinCache<T: Any>(
	val owner: T?,
	val ownerKotlinClass: KClass<out T>,
	val ownerJavaClass: Class<out T>,
) {
	val pushed = HashMap<String, Injected>()

	abstract class Injected(
		val names: MutableSet<String>,
		val closure: KotlinClosure
	)
	open class InjectedMethod(
		names: MutableSet<String>,
		closure: KotlinClosure
	): Injected(names, closure)
	open class InjectedMethodAsProperty(
		names: MutableSet<String>,
		closure: KotlinClosure,
		val methodClosure: KotlinClosure
	): Injected(names, closure)
	open class InjectedPropertyGetter(
		names: MutableSet<String>,
		closure: KotlinClosure
	): Injected(names, closure)
	open class InjectedPropertySetter(
		names: MutableSet<String>,
		closure: KotlinClosure
	): Injected(names, closure)
}
