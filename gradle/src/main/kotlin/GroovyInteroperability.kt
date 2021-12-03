import Common.groovyKotlinCaches
import Utils.__invalid_type
import Utils.__must_not_happen
import Utils.prepareGroovyKotlinCache
import groovy.lang.*
import org.codehaus.groovy.reflection.CachedClass
import org.codehaus.groovy.reflection.ReflectionCache.getCachedClass
import org.codehaus.groovy.runtime.MethodClosure
import org.codehaus.groovy.runtime.metaclass.ClosureMetaClass
import org.codehaus.groovy.runtime.metaclass.MetaMethodIndex
import org.gradle.api.Project
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
	val FIELD_MetaClassImpl_classPropertyIndex = MetaClassImpl::class.java.getDeclaredField("classPropertyIndex")
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
		FIELD_MetaClassImpl_classPropertyIndex.isAccessible = true
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
	open class KotlinMetaProperty(
		val modifiers0: Int,
		val name0: String,
		val type0: Class<*>,
		var getter: ((Any?) -> Any?)?,
		var setter: ((Any?, Any?) -> Unit)?
	): MetaProperty(name0, type0) {
		override fun getModifiers(): Int {
			return modifiers0
		}
		override fun getName(): String {
			return name0
		}
		override fun getType(): Class<*> {
			return type0
		}
		override fun getProperty(self: Any?): Any? {
			return (getter ?: throw Error("Cannot read write-only property: $name"))(self)
		}
		override fun setProperty(self: Any?, value: Any?) {
			(setter ?: throw Error("Cannot set read-only property: $name"))(self, value)
		}
	}
	@JvmStatic
	fun metaPropertySame(metaProperty: MetaProperty, name: String): Boolean {
		if(metaProperty.name != name) return false
		return true
	}
	@JvmStatic
	fun metaPropertySame(metaProperty1: MetaProperty, metaProperty2: MetaProperty): Boolean {
		return metaPropertySame(metaProperty1, metaProperty2.name)
	}
	@JvmStatic
	fun setMetaProperty0(metaClass: MetaClassImpl, metaProperty: MetaProperty, declaringClass: CachedClass) {
		val classPropertyIndex = FIELD_MetaClassImpl_classPropertyIndex.get(metaClass) as MetaClassImpl.Index
		val propertyMap = classPropertyIndex.getNotNull(declaringClass)

		val oldMetaProperty = propertyMap.get(metaProperty.name) as? MetaProperty
		if(oldMetaProperty != null && metaPropertySame(metaProperty, oldMetaProperty))
			System.err.println("Redefining meta property '${metaProperty.name}' for '${declaringClass}' at '$metaClass' with '$metaProperty'")
		propertyMap.put(metaProperty.name, metaProperty);
	}
	@JvmStatic
	fun deleteMetaProperty0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass) {
		val classPropertyIndex = FIELD_MetaClassImpl_classPropertyIndex.get(metaClass) as MetaClassImpl.Index
		val propertyMap = classPropertyIndex.getNullable(declaringClass) ?: return

		propertyMap.remove(name)
		if(propertyMap.size() == 0)
			classPropertyIndex.remove(declaringClass)
	}
	fun getMetaProperty0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass): MetaProperty? {
		val classPropertyIndex = FIELD_MetaClassImpl_classPropertyIndex.get(metaClass) as MetaClassImpl.Index
		val propertyMap = classPropertyIndex.getNullable(declaringClass) ?: return null
		return propertyMap.get(name) as? MetaProperty
	}
	open class KotlinMetaMethod(
		val modifiers0: Int,
		val name0: String,
		val declaringClass0: CachedClass,
		val parameterTypes0: Array<CachedClass>,
		val returnType0: Class<*>,
		var callback: ((Any?, Array<out Any?>) -> Any?)?
	): MetaMethod() {
		override fun getModifiers(): Int {
			return modifiers0
		}
		override fun getName(): String {
			return name0
		}
		override fun getDeclaringClass(): CachedClass {
			return declaringClass0
		}
		override fun getParameterTypes(): Array<CachedClass> {
			return parameterTypes0
		}
		override fun getReturnType(): Class<*> {
			return returnType0
		}
		override fun invoke(self: Any?, args: Array<out Any?>): Any? {
			return (callback ?: throw Error("Unimplemented method: $name"))(self, args)
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
			System.err.println("Redefining meta method '${metaMethod.name}' for '${metaMethod.declaringClass}' at '$metaClass' with '$metaMethod'")
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
		if(header.head == null)
			metaMethodIndex.methodHeaders.remove(declaringClass)
	}
	@JvmStatic
	fun getMetaMethod0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass, parameterTypes: Array<CachedClass>): MetaMethod? {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		if(true) {
			val table = metaMethodIndex.table
			val hash = MetaMethodIndex.hash(31 * declaringClass.theClass.hashCode() + name.hashCode())
			val index = hash and (table.size - 1)
			var elem = table[index]
			while(elem != null) {
				val asImpl = elem.methods as? MetaMethod
				if(elem.hash != hash || asImpl == null || !metaMethodSame(asImpl,
						name, declaringClass, parameterTypes)) {
					elem = elem.nextHashEntry
					continue
				}
				return asImpl
			}
		}

		val header = metaMethodIndex.getHeader(declaringClass.theClass)
		if(header != null) {
			var head = header.head
			while(head != null) {
				val asImpl = head.methods as? MetaMethod
				if(asImpl == null || !metaMethodSame(asImpl, name,
						declaringClass, parameterTypes)) {
					head = head.nextClassEntry
					continue
				}
				return asImpl
			}
		}

		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		if(true) {
			val iterator = allMethods.iterator()
			while(iterator.hasNext()) {
				val elem = iterator.next()
				if(!metaMethodSame(elem, name, declaringClass, parameterTypes))
					continue
				return elem
			}
		}

		return null
	}
	@JvmStatic
	fun setKotlinToGroovy(that: Any?, project: Project?, names: Array<String>, value: Any?) {
		val METHOD_UNKNOWN_getMetaClass = try { that?.javaClass?.getDeclaredMethod("getMetaClass") } catch(e: NoSuchMethodException) { null }
		var metaClass = (that as? GroovyObject)?.metaClass as? MetaClassImpl
		if(that != null && metaClass == null && METHOD_UNKNOWN_getMetaClass != null)
			metaClass = METHOD_UNKNOWN_getMetaClass.invoke(that) as? MetaClassImpl
		val declaringClass = metaClass?.theCachedClass
		val ext = project?.extensions?.extraProperties

		for(name in names) {
			val isGetter = name.startsWith("get") && (!name[3].isLetter() || name[3].isUpperCase())
			val isGetterBoolean = name.startsWith("is") && (!name[2].isLetter() || name[2].isUpperCase())
			val isSetter = name.startsWith("set") && (!name[3].isLetter() || name[3].isUpperCase())
			val asProperty = isGetter || isGetterBoolean || isSetter
			val asMethod = !asProperty

			if(metaClass != null && declaringClass != null && asProperty) {
				// getTest -> test, getCONFIG -> CONFIG, get ->, getA -> a
				val propertyName0 = if(isGetterBoolean) name.substring(2) else name.substring(3)
				val propertyName = if(propertyName0.length <= 1) propertyName0.lowercase() else
					if(propertyName0.uppercase() == propertyName0) propertyName0 else propertyName0.replaceFirstChar { it.lowercase() }
				if(value == null) {
					val metaProperty = getMetaProperty0(metaClass, propertyName, declaringClass)
					if(metaProperty is KotlinMetaProperty) {
						if(isGetter || isGetterBoolean)
							metaProperty.getter = null
						if(isSetter)
							metaProperty.setter = null
					}
					if(metaProperty !is KotlinMetaProperty || (metaProperty.getter == null && metaProperty.setter == null))
						deleteMetaProperty0(metaClass, propertyName, declaringClass)
				}
				if(value is KotlinClosure) {
					var metaProperty = getMetaProperty0(metaClass, propertyName, declaringClass) as? KotlinMetaProperty
					if(metaProperty == null) {
						val modifiers = Modifier.PUBLIC or Modifier.STATIC or Modifier.FINAL
						metaProperty = KotlinMetaProperty(modifiers, propertyName, Any::class.java, null, null)
						setMetaProperty0(metaClass, metaProperty, declaringClass)
					}
					if(isGetter || isGetterBoolean)
						metaProperty.getter = { self -> value.call(self) }
					if(isSetter)
						metaProperty.setter = { self, newVal -> value.call(self, newVal) }
				}
			}
			if(metaClass != null && declaringClass != null && asMethod) {
				val methodName = name
				val parameterTypes = arrayOf(/* KotlinClosure */getCachedClass(Array<Any>::class.java))
				if(value == null) {
					deleteMetaMethod0(metaClass, methodName, declaringClass, parameterTypes)
				}
				if(value is KotlinClosure) {
					var metaMethod = getMetaMethod0(metaClass, methodName, declaringClass, parameterTypes) as? KotlinMetaMethod
					if(metaMethod == null) {
						val modifiers = Modifier.PUBLIC or Modifier.STATIC or Modifier.FINAL
						metaMethod = KotlinMetaMethod(modifiers, methodName, declaringClass, parameterTypes, /* KotlinClosure */Any::class.java, null)
						setMetaMethod0(metaClass, metaMethod)
					}
					metaMethod.callback = { self, args -> value.call(self, args) }
				}
			}
			ext?.set(name, value)
		}
	}
}
