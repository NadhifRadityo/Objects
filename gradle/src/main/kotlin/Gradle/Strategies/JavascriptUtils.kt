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
import Gradle.Strategies.ProgressUtils.prog
import Gradle.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import Gradle.Strategies.RuntimeUtils.vmArguments
import groovy.lang.Closure
import java.lang.reflect.Method

object JavascriptUtils {
    @JvmStatic private var cache: GroovyKotlinCache<JavascriptUtils>? = null
    @ExportGradle @JvmStatic
    val javascriptGraalVm: Lazy<((String, Array<out Any?>) -> Any?)?> = lazy lazy@{
        val CLASS_Context = classForName<Any>("org.graalvm.polyglot.Context")
        val CLASS_Value = classForName<Any>("org.graalvm.polyglot.Value")
        if(CLASS_Context == null) return@lazy null
        if(CLASS_Value == null) return@lazy null
        val METHOD_Context_create = CLASS_Context.getMethod("create", Array<String>::class.java)
        val METHOD_Context_eval = CLASS_Context.getMethod("eval", String::class.java, CharSequence::class.java)
        val METHOD_Value_execute = CLASS_Value.getMethod("execute", Array<Any>::class.java)
        val convertToJavaType: MutableMap<Method, Method> = HashMap()
        val methods = CLASS_Value.methods
        for(method in methods) {
            val name = method.name
            if(!name.startsWith("is")) continue
            if(method.parameterCount != 0) continue
            val returnType = method.returnType
            if(returnType != Boolean::class.javaPrimitiveType && returnType != Boolean::class.java) continue
            val converter: Method = try { CLASS_Value.getMethod("as" + name.replaceFirst("is", "")) }
            catch(e: Exception) { exception(e); continue }
            convertToJavaType[method] = converter
        }
        return@lazy lambda@{ source: String, args: Array<out Any?> ->
            val context = METHOD_Context_create.invoke(null, arrayOfNulls<String>(0) as Any)
            (context as AutoCloseable).use { _ ->
                val function = METHOD_Context_eval.invoke(context, "js", source)
                val result = METHOD_Value_execute.invoke(function, args as Any)
                for((key, value) in convertToJavaType) {
                    try { if(!(key.invoke(result) as Boolean)) continue }
                    catch(e: Exception) { exception(e); continue }
                    return@lambda value.invoke(result)
                }
                lwarn("GraalVM object not found! value=$result")
                return@lambda result
            }
        }
    }
    @ExportGradle @JvmStatic
    val javascriptNashorn: Lazy<((String, Array<out Any?>) -> Any?)?> = lazy lazy@{
        val CLASS_ScriptEngineManager = classForName<Any>("javax.script.ScriptEngineManager")
        val CLASS_ScriptEngine = classForName<Any>("javax.script.ScriptEngine")
        val CLASS_JSObject = classForName<Any>("jdk.nashorn.api.scripting.JSObject")
        if(CLASS_ScriptEngineManager == null) return@lazy null
        if(CLASS_ScriptEngine == null) return@lazy null
        if(CLASS_JSObject == null) return@lazy null
        val CONSTRUCTOR_ScriptEngineManager = CLASS_ScriptEngineManager.getConstructor()
        val METHOD_ScriptEngineManager_getEngineByName = CLASS_ScriptEngineManager.getMethod("getEngineByName", String::class.java)
        val METHOD_ScriptEngine_eval = CLASS_ScriptEngine.getMethod("eval", String::class.java)
        val METHOD_JSObject_call = CLASS_JSObject.getMethod("call", Any::class.java, Array<Any>::class.java)
        run {
            val isEs6 = vmArguments.find { it == "-Dnashorn.args=--language=es6" } != null
            if(!isEs6) lwarn("Error may occur, Please add \"-Dnashorn.args=--language=es6\" to your JVM arguments")
            else if(JAVA_DETECTION_VERSION <= 8) lwarn("Error may occur, javascript functionality may be limited. It is recommended to use java >= 9")
        }
        return@lazy lambda@{ source: String, args: Array<out Any?> ->
            var engine = CONSTRUCTOR_ScriptEngineManager.newInstance()
            engine = METHOD_ScriptEngineManager_getEngineByName.invoke(engine, "nashorn")
            val function = METHOD_ScriptEngine_eval.invoke(engine, source)
            return@lambda METHOD_JSObject_call.invoke(function, null, args)
        }
    }

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

    @ExportGradle @JvmStatic @Throws(Exception::class)
    fun runJavascript(source: String, vararg args: Any?): Any? {
        prog(arrayOf(source, args), JavascriptUtils::class.java, "Running javascript", true).use { prog0 ->
            val jsGraalVM = javascriptGraalVm.value
            if(jsGraalVM != null) {
                prog0.pdo("Running javascript (GraalVM)")
                ldebug("Running javascript (GraalVM)")
                return jsGraalVM(source, args)
            }
            val jsNashorn = javascriptNashorn.value
            if(jsNashorn != null) {
                prog0.pdo("Running javascript (Nashorn)")
                ldebug("Running javascript (Nashorn)")
                return jsNashorn(source, args)
            }
            throw UnsupportedOperationException("Supported javascript runtime is not available!")
        }
    }

    @ExportGradle @JvmStatic
    fun <T> runJavascriptAsCallback(source: String): Closure<T?> {
        val result = KotlinClosure("js ($source)")
        result.overloads += KotlinClosure.KLambdaOverload { args -> runJavascript(source, *args) }
        return result as Closure<T?>
    }
    @ExportGradle @JvmStatic
    fun <T> runJavascriptAsCallbackF(source: String): (Array<out Any?>) -> Any? {
        return { args -> runJavascript(source, *args) }
    }
}
