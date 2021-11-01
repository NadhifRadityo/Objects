import Utils.__invalid_type
import Utils.__must_not_happen
import Utils.pullKotlinFromGradle
import Utils.pushKotlinToGradle
import groovy.lang.*
import org.codehaus.groovy.reflection.CachedClass
import org.codehaus.groovy.reflection.ReflectionCache
import org.codehaus.groovy.runtime.MethodClosure
import org.codehaus.groovy.runtime.metaclass.ClosureMetaClass
import org.codehaus.groovy.runtime.metaclass.MetaMethodIndex
import org.gradle.api.Project
import java.lang.reflect.Modifier
import kotlin.jvm.functions.FunctionN

object GroovyInteroperability {

	@JvmStatic
	fun init() {
		pushKotlinToGradle(GroovyInteroperability)
	}
	@JvmStatic
	fun deinit() {
		pullKotlinFromGradle(GroovyInteroperability)
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
	init {
		FIELD_MetaClassImpl_metaMethodIndex.isAccessible = true
		FIELD_MetaClassImpl_allMethods.isAccessible = true
		METHOD_MetaClassImpl_addMetaMethodToIndex.isAccessible = true
	}

	class GroovyInteroperabilityMetaMethodImpl(
		val name0: String,
		val declaringClass0: Class<*>,
		val closure: Closure<*>
	): MetaMethod(arrayOf(Array<Any>::class.java)) {
		override fun getModifiers(): Int {
			return Modifier.PUBLIC or Modifier.STATIC or Modifier.FINAL
		}
		override fun getName(): String {
			return name0
		}
		override fun getReturnType(): Class<*> {
			return Any::class.java
		}
		override fun getDeclaringClass(): CachedClass? {
			return ReflectionCache.getCachedClass(declaringClass0)
		}
		override fun invoke(self: Any?, args: Array<out Any?>): Any? {
			return closure.call(*(args[0] as Array<*>))
		}
	}
	@JvmStatic
	fun setMetaMethod(metaClass: MetaClassImpl, name: String, declaringClass: Class<*>, closure: Closure<*>?) {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		if(closure == null) {
			val metaMethod = allMethods.firstOrNull { it is GroovyInteroperabilityMetaMethodImpl &&
					it.name0 == name && it.declaringClass0 == declaringClass } ?: return
			allMethods -= metaMethod
			return
		}

		val metaMethod = GroovyInteroperabilityMetaMethodImpl(name, declaringClass, closure)
		val header = metaMethodIndex.getHeader(declaringClass)
		allMethods += metaMethod
		METHOD_MetaClassImpl_addMetaMethodToIndex.invoke(metaClass, metaMethod, header)
	}
	@JvmStatic
	fun setKotlinToGroovy(that: Any, reflnames: Array<String>?, reflname: String, classCanonical: String, value: Any?, nameProcessor: (String) -> String) {
		val ext = (that as? Project)?.extensions?.extraProperties
		val metaClass = (that as? GroovyObject)?.metaClass as? MetaClassImpl
		val names = if(reflnames != null) if(reflnames.isNotEmpty()) reflnames else arrayOf(reflname) else arrayOf()

		for(name in names) {
			val processedName = nameProcessor(name)
//			ext?.set(processedName, value)
			if(metaClass != null && (value == null || value is Closure<*>))
				setMetaMethod(metaClass, processedName, that.javaClass, value as Closure<*>?)
		}
		val internalName = nameProcessor("__INTERNAL_${classCanonical.replace(".", "$")}_${reflname}")
//		ext?.set(internalName, value)
		if(metaClass != null && (value == null || value is Closure<*>))
			setMetaMethod(metaClass, internalName, that.javaClass, value as Closure<*>?)
	}
}
