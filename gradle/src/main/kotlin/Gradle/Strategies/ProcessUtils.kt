package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.LoggerUtils.ldebug
import Gradle.Strategies.LoggerUtils.lerror
import Gradle.Strategies.ProgressUtils.prog
import Gradle.Strategies.StreamUtils.streamString
import groovy.transform.ThreadInterrupt
import org.apache.commons.lang3.SystemUtils
import java.io.File
import java.nio.charset.StandardCharsets

object ProcessUtils {
    @JvmStatic private var cache: GroovyKotlinCache<ProcessUtils>? = null

    @JvmStatic
    fun construct() {
        cache = prepareGroovyKotlinCache(ProcessUtils)
        addInjectScript(cache!!)
    }
    @JvmStatic
    fun destruct() {
        removeInjectScript(cache!!)
        cache = null
    }

    @ExportGradle @JvmStatic @ThreadInterrupt @Throws(Exception::class)
    fun getCommandOutput(basedir: File?, vararg arguments: String): String? {
        prog(arrayOf(basedir, arguments), ProcessUtils::class.java, "Reading file", true).use { prog0 ->
            prog0.pdo("Executing command `${java.lang.String.join(" ", *arguments)}`")
            ldebug("Executing command: ${java.lang.String.join(" ", *arguments)}")
            val processBuilder = ProcessBuilder(*arguments)
            if(basedir != null) processBuilder.directory(basedir)
            val process = processBuilder.start()
            val returnCode = process.waitFor()
            val error: String = streamString(process.errorStream, StandardCharsets.UTF_8)
            if(!error.isEmpty()) lerror(error)
            if(returnCode != 0) return null
            val result: String = streamString(process.inputStream, StandardCharsets.UTF_8)
            ldebug("$result")
            return result
        }
    }
    @ExportGradle @JvmStatic @Throws(Exception::class)
    fun getCommandOutput(vararg arguments: String): String? { return getCommandOutput(null, *arguments) }

    @ExportGradle @JvmStatic @Throws(Exception::class)
    fun searchPath(executable: String): File? {
        if(SystemUtils.IS_OS_WINDOWS) {
            val path = getCommandOutput("where", executable)
            return if(path == null || path.isEmpty()) null
                else File(path.trim().split("\r\n").toTypedArray()[0])
        }
        val path = getCommandOutput("which", executable)
        return if(path == null || path.isEmpty()) null
            else File(path.trim())
    }
}
