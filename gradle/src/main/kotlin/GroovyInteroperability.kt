import Common.groovyKotlinCaches
import Utils.__invalid_type
import Utils.__must_not_happen
import Utils.prepareGroovyKotlinCache
import groovy.lang.*
import org.codehaus.groovy.reflection.CachedClass
import org.codehaus.groovy.reflection.ReflectionCache
import org.codehaus.groovy.runtime.MethodClosure
import org.codehaus.groovy.runtime.metaclass.ClosureMetaClass
import org.codehaus.groovy.runtime.metaclass.MetaMethodIndex
import org.gradle.api.Project
import org.gradle.internal.reflect.NoSuchPropertyException
import java.lang.reflect.Modifier
import kotlin.jvm.functions.FunctionN

object GroovyInteroperability {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null

	@JvmStatic
	fun init() {
		cache = prepareGroovyKotlinCache(GroovyInteroperability)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun deinit() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	/*
	console.log(new Array(23).fill(null).map((v, i) => `@ExportGradle
	@JvmStatic
	fun <${new Array(i).fill(null).map((_v, _i) => `A${_i + 1}`).join(", ")}, R> closureToLambda${i}(closure: Closure<R>): (${new Array(i).fill(null).map((_v, _i) => `A${_i + 1}`).join(", ")}) -> R {
		return { ${new Array(i).fill(null).map((_v, _i) => `a${_i + 1}`).join(", ")} -> closure.call(${new Array(i).fill(null).map((_v, _i) => `a${_i + 1}`).join(", ")}) }
	}`).join("\n"))
	 */
	@ExportGradle
	@JvmStatic
	fun <R> closureToLambda0(closure: Closure<R>): () -> R {
		return { -> closure.call() }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, R> closureToLambda1(closure: Closure<R>): (A1) -> R {
		return { a1 -> closure.call(a1) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, R> closureToLambda2(closure: Closure<R>): (A1, A2) -> R {
		return { a1, a2 -> closure.call(a1, a2) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, R> closureToLambda3(closure: Closure<R>): (A1, A2, A3) -> R {
		return { a1, a2, a3 -> closure.call(a1, a2, a3) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, R> closureToLambda4(closure: Closure<R>): (A1, A2, A3, A4) -> R {
		return { a1, a2, a3, a4 -> closure.call(a1, a2, a3, a4) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, R> closureToLambda5(closure: Closure<R>): (A1, A2, A3, A4, A5) -> R {
		return { a1, a2, a3, a4, a5 -> closure.call(a1, a2, a3, a4, a5) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, R> closureToLambda6(closure: Closure<R>): (A1, A2, A3, A4, A5, A6) -> R {
		return { a1, a2, a3, a4, a5, a6 -> closure.call(a1, a2, a3, a4, a5, a6) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, R> closureToLambda7(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7) -> R {
		return { a1, a2, a3, a4, a5, a6, a7 -> closure.call(a1, a2, a3, a4, a5, a6, a7) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, R> closureToLambda8(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, R> closureToLambda9(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, R> closureToLambda10(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, R> closureToLambda11(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, R> closureToLambda12(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, R> closureToLambda13(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, R> closureToLambda14(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, R> closureToLambda15(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, R> closureToLambda16(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, R> closureToLambda17(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, R> closureToLambda18(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, R> closureToLambda19(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, R> closureToLambda20(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, R> closureToLambda21(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) }
	}
	@ExportGradle
	@JvmStatic
	fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, R> closureToLambda22(closure: Closure<R>): (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) -> R {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) }
	}
	@ExportGradle
	@JvmStatic
	fun <R> closureToLambdaN(closure: Closure<R>, arity: Int): FunctionN<R> {
		return object: FunctionN<R> {
			override val arity: Int
				get() = arity

			override fun invoke(vararg args: Any?): R {
				return closure.call(*args)
			}
		}
	}

	@ExportGradle
	@JvmStatic
	fun <R> closureToLambda(closure: Closure<R>, target: Class<Function<*>>): Function<R> {
		run {
			/*
			console.log(new Array(23).fill(null).map((v, i) => `if(Function${i}::class.java.isAssignableFrom(target)) return closureToLambda${i}<${new Array(i).fill(null).map((_v, _i) => `Any?`).join(", ")}, R>(closure)`).join("\n"))
			 */
			if(Function0::class.java.isAssignableFrom(target)) return closureToLambda0<R>(closure)
			if(Function1::class.java.isAssignableFrom(target)) return closureToLambda1<Any?, R>(closure)
			if(Function2::class.java.isAssignableFrom(target)) return closureToLambda2<Any?, Any?, R>(closure)
			if(Function3::class.java.isAssignableFrom(target)) return closureToLambda3<Any?, Any?, Any?, R>(closure)
			if(Function4::class.java.isAssignableFrom(target)) return closureToLambda4<Any?, Any?, Any?, Any?, R>(closure)
			if(Function5::class.java.isAssignableFrom(target)) return closureToLambda5<Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function6::class.java.isAssignableFrom(target)) return closureToLambda6<Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function7::class.java.isAssignableFrom(target)) return closureToLambda7<Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function8::class.java.isAssignableFrom(target)) return closureToLambda8<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function9::class.java.isAssignableFrom(target)) return closureToLambda9<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function10::class.java.isAssignableFrom(target)) return closureToLambda10<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function11::class.java.isAssignableFrom(target)) return closureToLambda11<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function12::class.java.isAssignableFrom(target)) return closureToLambda12<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function13::class.java.isAssignableFrom(target)) return closureToLambda13<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function14::class.java.isAssignableFrom(target)) return closureToLambda14<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function15::class.java.isAssignableFrom(target)) return closureToLambda15<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function16::class.java.isAssignableFrom(target)) return closureToLambda16<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function17::class.java.isAssignableFrom(target)) return closureToLambda17<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function18::class.java.isAssignableFrom(target)) return closureToLambda18<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function19::class.java.isAssignableFrom(target)) return closureToLambda19<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function20::class.java.isAssignableFrom(target)) return closureToLambda20<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function21::class.java.isAssignableFrom(target)) return closureToLambda21<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(Function22::class.java.isAssignableFrom(target)) return closureToLambda22<Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, R>(closure)
			if(FunctionN::class.java.isAssignableFrom(target)) {
				val arity = when(val metaClass = closure.metaClass) {
					is ClosureMetaClass -> metaClass.methods.filter { it.name == "doCall" }
						.minOf { it.parameterTypes.size }
					is MethodClosure -> {
						val methodName = metaClass.method
						val delegate = metaClass.delegate as Class<*>
						delegate.methods.filter { it.name == methodName }
							.minOf { it.parameterTypes.size }
					}
					else -> throw __invalid_type()
				}
				return closureToLambdaN(closure, arity)
			}
		}
		throw __must_not_happen()
	}

	@JvmStatic
	val FIELD_MetaClassImpl_metaMethodIndex = MetaClassImpl::class.java.getDeclaredField("metaMethodIndex")
	@JvmStatic
	val FIELD_MetaClassImpl_allMethods = MetaClassImpl::class.java.getDeclaredField("allMethods")
	@JvmStatic
	val METHOD_MetaClassImpl_addMetaMethodToIndex = MetaClassImpl::class.java.getDeclaredMethod("addMetaMethodToIndex", MetaMethod::class.java, MetaMethodIndex.Header::class.java)
	@JvmStatic
	val METHOD_MetaClassImpl_reinitialize = MetaClassImpl::class.java.getDeclaredMethod("reinitialize")
	@JvmStatic
	val FIELD_MetaMethodIndex_size = MetaMethodIndex::class.java.getDeclaredField("size")
	init {
		FIELD_MetaClassImpl_metaMethodIndex.isAccessible = true
		FIELD_MetaClassImpl_allMethods.isAccessible = true
		METHOD_MetaClassImpl_addMetaMethodToIndex.isAccessible = true
		METHOD_MetaClassImpl_reinitialize.isAccessible = true
		FIELD_MetaMethodIndex_size.isAccessible = true
	}

	open class DummyGroovyObject : GroovyObjectSupport() {
		init {
			metaClass = MetaClassImpl(DummyGroovyObject::class.java)
		}
		fun finalize() {
			METHOD_MetaClassImpl_reinitialize.invoke(metaClass)
		}
	}
	open class KotlinMetaMethod(
		val name0: String,
		val declaringClass0: CachedClass,
		val closure: Closure<*>
	): MetaMethod() {
		override fun getModifiers(): Int {
			return Modifier.PUBLIC or Modifier.STATIC or Modifier.FINAL
		}
		override fun getName(): String {
			return name0
		}
		override fun getPT(): Array<Class<*>> {
			return arrayOf(Any::class.java)
		}
		override fun getReturnType(): Class<*> {
			return Any::class.java
		}
		override fun getDeclaringClass(): CachedClass {
			return declaringClass0
		}
		override fun invoke(self: Any?, args: Array<out Any?>): Any? {
			return closure.call(self, *args)
		}
	}
	@JvmStatic
	fun metaMethodSame(metaMethod: MetaMethod, name: String, declaringClass: CachedClass, parameterTypes: Array<CachedClass>): Boolean {
		if(metaMethod.name != name) return false
		if(!metaMethod.declaringClass.equals(declaringClass)) return false
		val methodParameterTypes = metaMethod.parameterTypes
		if(methodParameterTypes.size != parameterTypes.size) return false
		val size = methodParameterTypes.size
		for(i in 0 until size)
			if(methodParameterTypes[i] != parameterTypes[i])
				return false
		return true
	}
	@JvmStatic
	fun metaMethodSame(metaMethod1: MetaMethod, metaMethod2: MetaMethod): Boolean {
		return metaMethodSame(metaMethod1, metaMethod2.name, metaMethod2.declaringClass, metaMethod2.parameterTypes)
	}
	@JvmStatic
	fun setMetaMethod0(metaClass: MetaClassImpl, metaMethod: MetaMethod) {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		val header = metaMethodIndex.getHeader(metaMethod.declaringClass.theClass)

		for(method in metaClass.methods) {
			if(!metaMethodSame(method, metaMethod))
				continue
			System.err.println("Redefining meta method '${metaMethod.name}' for '${metaMethod.declaringClass}' at '$metaClass' with `$metaMethod`")
			break
		}
		allMethods += metaMethod
		METHOD_MetaClassImpl_addMetaMethodToIndex.invoke(metaClass, metaMethod, header)
	}
	@JvmStatic
	fun deleteMetaMethod0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass, parameterTypes: Array<CachedClass>) {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		val header = metaMethodIndex.getHeader(declaringClass.theClass)

		if(true) {
			val iterator = allMethods.iterator()
			while(iterator.hasNext()) {
				val elem = iterator.next()
				if(!metaMethodSame(elem, name, declaringClass, parameterTypes))
					continue
				iterator.remove()
				break
			}
		}
		if(true) {
			val table = metaMethodIndex.table
			val hash = MetaMethodIndex.hash(31 * declaringClass.theClass.hashCode() + name.hashCode())
			val index = hash and (table.size - 1)
			val size = metaMethodIndex.size()
			var elem = table[index]
			var prev: MetaMethodIndex.Entry? = null
			while(elem != null) {
				val asImpl = elem.methods as? MetaMethod
				if(elem.hash != hash || asImpl == null || !metaMethodSame(asImpl,
						name, declaringClass, parameterTypes)) {
					prev = elem
					elem = elem.nextHashEntry
					continue
				}
				if(prev == null)
					table[index] = elem.nextHashEntry
				else prev.nextHashEntry = elem.nextHashEntry
				elem.nextHashEntry = null
				FIELD_MetaMethodIndex_size.set(metaMethodIndex, size - 1)
				break
			}
		}
		if(true) {
			var head = header.head
			var prev: MetaMethodIndex.Entry? = null
			while(head != null) {
				val asImpl = head.methods as? MetaMethod
				if(asImpl == null || !metaMethodSame(asImpl, name,
						declaringClass, parameterTypes)) {
					prev = head
					head = head.nextClassEntry
					continue
				}
				if(prev == null)
					header.head = head.nextClassEntry
				else prev.nextClassEntry = head.nextClassEntry
				head.nextClassEntry = null
				break
			}
		}
		if(header.head == null)
			metaMethodIndex.methodHeaders.remove(declaringClass)
	}
	@JvmStatic
	fun modifyMetaMethod(metaClass: MetaClassImpl, name: String, declaringClass: Class<*>, closure: Closure<*>?) {
		val parameterTypes = arrayOf(Any::class.java)
		val cachedDeclaringClass = ReflectionCache.getCachedClass(declaringClass)
		val cachedParameterTypes = parameterTypes.map { ReflectionCache.getCachedClass(it) }.toTypedArray()
		if(closure == null) {
			deleteMetaMethod0(metaClass, name, cachedDeclaringClass, cachedParameterTypes)
			return
		}
		val metaMethod = KotlinMetaMethod(name, cachedDeclaringClass, closure)
		setMetaMethod0(metaClass, metaMethod)
	}
	open class KotlinPropertyMetaMethod(
		name: String,
		declaringClass: CachedClass,
		val override: MetaMethod?,
		closure: Closure<*>
	): KotlinMetaMethod(name, declaringClass, closure)
	@JvmStatic
	fun overridePropertyAccessor(metaClass: MetaClassImpl, declaringClass: CachedClass, name: String, validate: (MetaMethod) -> Boolean, value: Closure<*>?) {
		val propertyAccessors = metaClass.methods.filter(validate)
		var firstPropertyAccessor: MetaMethod? = null
		var superClass: CachedClass? = declaringClass
		while(superClass != null && firstPropertyAccessor == null) {
			firstPropertyAccessor = propertyAccessors.first { it.declaringClass == superClass }
			if(firstPropertyAccessor == null)
				superClass = superClass.cachedSuperClass
		}

		if(firstPropertyAccessor is KotlinPropertyMetaMethod && value == null) {
			deleteMetaMethod0(metaClass, name, firstPropertyAccessor.declaringClass, firstPropertyAccessor.parameterTypes)
			val override = firstPropertyAccessor.override
			if(override != null) setMetaMethod0(metaClass, override)
		} else if(firstPropertyAccessor !is KotlinPropertyMetaMethod && value != null) {
			if(firstPropertyAccessor != null)
				deleteMetaMethod0(metaClass, name, firstPropertyAccessor.declaringClass, firstPropertyAccessor.parameterTypes)
			setMetaMethod0(metaClass, KotlinPropertyMetaMethod(name, firstPropertyAccessor?.declaringClass ?: declaringClass, firstPropertyAccessor, value))
		}
	}
	@JvmStatic
	fun overridePropertyGetter(metaClass: MetaClassImpl, declaringClass: CachedClass, getter: Closure<*>?) {
		overridePropertyAccessor(metaClass, declaringClass, "getProperty", validate@{
			if(it.name != "getProperty") return@validate false
			val modifiers = it.modifiers
			if(!Modifier.isPublic(modifiers)) return@validate false
			val parameters = it.nativeParameterTypes
			if(parameters.size != 1) return@validate false
			if(parameters[0] != String::class.java) return@validate false
			return@validate true
		}, getter)
	}
	@JvmStatic
	fun overridePropertySetter(metaClass: MetaClassImpl, declaringClass: CachedClass, setter: Closure<*>?) {
		overridePropertyAccessor(metaClass, declaringClass, "setProperty", validate@{
			if(it.name != "setProperty") return@validate false
			val modifiers = it.modifiers
			if(!Modifier.isPublic(modifiers)) return@validate false
			val parameters = it.nativeParameterTypes
			if(parameters.size != 2) return@validate false
			if(parameters[0] != String::class.java) return@validate false
			if(parameters[1] != Any::class.java) return@validate false
			return@validate true
		}, setter)
	}
	@JvmStatic
	val VALUE_INACCESSIBLE = KotlinClosure("VALUE_INACCESSIBLE")
	open class KotlinPropertyGetterClosure(
		val map: MutableMap<String, Pair<Closure<*>?, Closure<*>?>>,
		val override: MetaMethod?
	): KotlinClosure("getProperty") {
		init {
			overloads += KLambdaOverload lambda@{ args ->
				val self = args[0]
				val name = args[1] as String
				val value = map[name]?.first
				if(value != null) {
					if(value == VALUE_INACCESSIBLE)
						throw IllegalAccessException("Getter to $name is not allowed")
					return@lambda value.call(*args)
				}
				if(override == null) throw NoSuchPropertyException("No such property $name")
				return@lambda override.invoke(self, args.copyOfRange(1, args.size))
			}
		}
	}
	open class KotlinPropertySetterClosure(
		val map: MutableMap<String, Pair<Closure<*>?, Closure<*>?>>,
		val override: MetaMethod?
	): KotlinClosure("setProperty") {
		init {
			overloads += KLambdaOverload lambda@{ args ->
				val self = args[0]
				val name = args[1] as String
				val value = map[name]?.first
				if(value != null) {
					if(value == VALUE_INACCESSIBLE)
						throw IllegalAccessException("Setter to $name is not allowed")
					return@lambda value.call(*args)
				}
				if(override == null) throw NoSuchPropertyException("No such property $name")
				return@lambda override.invoke(self, args.copyOfRange(1, args.size))
			}
		}
	}
	@JvmStatic
	fun setPropertyAccessor(metaClass: MetaClassImpl, declaringClass: CachedClass, name: String, getter: Closure<*>?, setter: Closure<*>?) {
		var propertyGetterMetaMethod = metaClass.getMethodWithoutCaching(metaClass.theClass, "getProperty", arrayOf(String::class.java), true)
		var propertySetterMetaMethod = metaClass.getMethodWithoutCaching(metaClass.theClass, "setProperty", arrayOf(String::class.java, Any::class.java), true)
		var propertyGetterMetaMethodAsImpl = propertyGetterMetaMethod as? KotlinPropertyMetaMethod
		var propertySetterMetaMethodAsImpl = propertySetterMetaMethod as? KotlinPropertyMetaMethod
		var interceptMap = (propertyGetterMetaMethodAsImpl?.closure as? KotlinPropertyGetterClosure)?.map ?:
				(propertySetterMetaMethodAsImpl?.closure as? KotlinPropertySetterClosure)?.map
		if(propertyGetterMetaMethodAsImpl == null && propertySetterMetaMethodAsImpl == null && getter == null && setter == null)
			return
		if(interceptMap == null)
			interceptMap = HashMap()
		if(propertyGetterMetaMethodAsImpl == null) {
			overridePropertyGetter(metaClass, declaringClass, KotlinPropertyGetterClosure(interceptMap, propertyGetterMetaMethod))
			propertyGetterMetaMethod = metaClass.getMethodWithoutCaching(metaClass.theClass, "getProperty", arrayOf(String::class.java), true)
			propertyGetterMetaMethodAsImpl = propertyGetterMetaMethod as? KotlinPropertyMetaMethod
		}
		if(propertySetterMetaMethodAsImpl == null) {
			overridePropertySetter(metaClass, declaringClass, KotlinPropertySetterClosure(interceptMap, propertySetterMetaMethod))
			propertySetterMetaMethod = metaClass.getMethodWithoutCaching(metaClass.theClass, "setProperty", arrayOf(String::class.java, Any::class.java), true)
			propertySetterMetaMethodAsImpl = propertySetterMetaMethod as? KotlinPropertyMetaMethod
		}
		if(getter == null && setter == null) {
			interceptMap.remove(name)
			if(interceptMap.isNotEmpty())
				return
			overridePropertyGetter(metaClass, declaringClass, null)
			overridePropertySetter(metaClass, declaringClass, null)
			return
		}
		interceptMap[name] = Pair(getter, setter)
	}
	@JvmStatic
	fun setKotlinToGroovy(that: Any?, project: Project?, names: Array<String>, value: Any?) {
		val metaClass = (that as? GroovyObject)?.metaClass as? MetaClassImpl
		val ext = project?.extensions?.extraProperties

		for(name in names) {
			if(metaClass != null && (value == null || value is Closure<*>))
				modifyMetaMethod(metaClass, name, that.javaClass, value as Closure<*>?)
			ext?.set(name, value)
		}
	}
}
