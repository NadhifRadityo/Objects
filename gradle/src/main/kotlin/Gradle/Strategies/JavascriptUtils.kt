package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.KotlinClosure
import Gradle.Strategies.ClassUtils.classForName
import Gradle.Strategies.ExceptionUtils.exception
import Gradle.Strategies.LoggerUtils.ldebug
import Gradle.Strategies.LoggerUtils.lwarn
import Gradle.Strategies.ProgressUtils.progress
import Gradle.Strategies.ProgressUtils.progress_id
import Gradle.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import Gradle.Strategies.RuntimeUtils.vmArguments
import groovy.lang.Closure
import java.lang.reflect.Method

object JavascriptUtils {
    @JvmStatic private var cache: GroovyKotlinCache<JavascriptUtils>? = null
    @ExportGradle @JvmStatic val javascriptGraalVM: ((String, Array<out Any?>) -> Any?)?
    @ExportGradle @JvmStatic val javascriptNashorn: ((String, Array<out Any?>) -> Any?)?

    @JvmStatic
    fun construct() {
        cache = prepareGroovyKotlinCache(JavascriptUtils)
        addInjectScript(cache!!)
    }
    @JvmStatic
    fun destruct() {
        removeInjectScript(cache!!)
        cache = null
    }

    init {
        val CLASS_GRAALVM_Context = classForName<Any>("org.graalvm.polyglot.Context")
        val CLASS_GRAALVM_Value = classForName<Any>("org.graalvm.polyglot.Value")
        javascriptGraalVM = (lambda@{
            if(CLASS_GRAALVM_Context == null) return@lambda null
            if(CLASS_GRAALVM_Value == null) return@lambda null
            val METHOD_GRAALVM_Context_create = CLASS_GRAALVM_Context.getMethod("create", Array<String>::class.java)
            val METHOD_GRAALVM_Context_eval = CLASS_GRAALVM_Context.getMethod("eval", String::class.java, CharSequence::class.java)
            val METHOD_GRAALVM_Value_execute = CLASS_GRAALVM_Value.getMethod("execute", Array<Any>::class.java)
            val convertToJavaType: MutableMap<Method, Method> = HashMap()
            val methods = CLASS_GRAALVM_Value.methods
            for(method in methods) {
                val name = method.name
                if(!name.startsWith("is")) continue
                if(method.parameterCount != 0) continue
                val returnType = method.returnType
                if(returnType != Boolean::class.javaPrimitiveType && returnType != Boolean::class.java) continue
                val converter: Method = try { CLASS_GRAALVM_Value.getMethod("as" + name.replaceFirst("is", "")) }
                    catch(e: Exception) { exception(e); continue }
                convertToJavaType[method] = converter
            }
            return@lambda lambda2@{ source: String, args: Array<out Any?> ->
                val context = METHOD_GRAALVM_Context_create.invoke(null, arrayOfNulls<String>(0) as Any)
                (context as AutoCloseable).use { _ ->
                    val function = METHOD_GRAALVM_Context_eval.invoke(context, "js", source)
                    val result = METHOD_GRAALVM_Value_execute.invoke(function, args as Any)
                    for((key, value) in convertToJavaType) {
                        try { if(!(key.invoke(result) as Boolean)) continue }
                            catch(e: Exception) { exception(e); continue }
                        return@lambda2 value.invoke(result)
                    }
                    lwarn("GraalVM object not found! value=$result")
                    return@lambda2 result
                }
            }
        })()
        val CLASS_NASHORN_ScriptEngineManager = classForName<Any>("javax.script.ScriptEngineManager")
        val CLASS_NASHORN_ScriptEngine = classForName<Any>("javax.script.ScriptEngine")
        val CLASS_NASHORN_JSObject = classForName<Any>("jdk.nashorn.api.scripting.JSObject")
        javascriptNashorn = (lambda@{
            if(CLASS_NASHORN_ScriptEngineManager == null) return@lambda null
            if(CLASS_NASHORN_ScriptEngine == null) return@lambda null
            if(CLASS_NASHORN_JSObject == null) return@lambda null
            val CONSTRUCTOR_NASHORN_ScriptEngineManager = CLASS_NASHORN_ScriptEngineManager.getConstructor()
            val METHOD_NASHORN_ScriptEngineManager_getEngineByName = CLASS_NASHORN_ScriptEngineManager.getMethod("getEngineByName", String::class.java)
            val METHOD_NASHORN_ScriptEngine_eval = CLASS_NASHORN_ScriptEngine.getMethod("eval", String::class.java)
            val METHOD_NASHORN_JSObject_call = CLASS_NASHORN_JSObject.getMethod("call", Any::class.java, Array<Any>::class.java)
            return@lambda lambda2@{ source: String, args: Array<out Any?> ->
                var engine = CONSTRUCTOR_NASHORN_ScriptEngineManager.newInstance()
                engine = METHOD_NASHORN_ScriptEngineManager_getEngineByName.invoke(engine, "nashorn")
                val function = METHOD_NASHORN_ScriptEngine_eval.invoke(engine, source)
                return@lambda2 METHOD_NASHORN_JSObject_call.invoke(function, null, args)
            }
        })()
        if(javascriptNashorn != null) {
            val isEs6 = vmArguments.find { it == "-Dnashorn.args=--language=es6" } != null
            if(!isEs6) lwarn("Error may occur, Please add \"-Dnashorn.args=--language=es6\" to your JVM arguments")
            else if(JAVA_DETECTION_VERSION <= 8) lwarn("Error may occur, javascript functionality may be limited. It is recommended to use java >= 9")
        }
    }

    @ExportGradle
    @JvmStatic
    @Throws(Exception::class)
    fun runJavascript(source: String, vararg args: Any?): Any? {
        progress(progress_id(source, args)).use { prog0 ->
            prog0.inherit()
            prog0.category = JavascriptUtils::class.java.toString()
            prog0.description = "Running javascript"
            prog0.pstart()
            if(javascriptGraalVM != null) {
                prog0.pdo("Running javascript (GraalVM)")
                ldebug("Running javascript (GraalVM)")
                return javascriptGraalVM!!(source, args)
            }
            if(javascriptNashorn != null) {
                prog0.pdo("Running javascript (Nashorn)")
                ldebug("Running javascript (Nashorn)")
                return javascriptNashorn!!(source, args)
            }
            throw UnsupportedOperationException("Supported javascript runtime is not available!")
        }
    }

    @ExportGradle
    @JvmStatic
    fun <T> runJavascriptAsCallback(source: String): (Array<Any?>) -> T? {
        return { args: Array<Any?> -> runJavascript(source, *args) as T? }
    }
    @ExportGradle
    @JvmStatic
    fun <T> runJavascriptAsClosure(source: String): Closure<T?> {
        val result = KotlinClosure("js ($source)")
        result.overloads += KotlinClosure.KLambdaOverload { args -> runJavascript(source, *args) }
        return result as Closure<T?>
    }
}
