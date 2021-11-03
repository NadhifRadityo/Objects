import kotlin.reflect.KClass

class GroovyKotlinCache<T : Any>(
	val owner: T?,
	val ownerKotlinClass: KClass<out T>,
	val ownerJavaClass: Class<out T>,
) {
	val pushed = HashMap<String, Pair<Array<String>, KotlinClosure>>()
}
