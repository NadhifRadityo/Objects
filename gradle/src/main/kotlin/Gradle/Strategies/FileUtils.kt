package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.ExceptionUtils.exception
import Gradle.Strategies.LoggerUtils.ldebug
import Gradle.Strategies.ProgressUtils.progress
import Gradle.Strategies.ProgressUtils.progress_id
import Gradle.Strategies.StreamUtils.streamBytes
import Gradle.Strategies.StreamUtils.streamString
import Gradle.Strategies.StreamUtils.writeBytes
import Gradle.Strategies.StreamUtils.writeString
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

object FileUtils {
    @JvmStatic private var cache: GroovyKotlinCache<FileUtils>? = null

    @JvmStatic
    fun construct() {
        cache = prepareGroovyKotlinCache(FileUtils)
        addInjectScript(cache!!)
    }
    @JvmStatic
    fun destruct() {
        removeInjectScript(cache!!)
        cache = null
    }

    @ExportGradle
    @JvmStatic
    @Throws(IOException::class)
    fun fileBytes(file: File): ByteArray {
        progress(progress_id(file)).use { prog0 ->
            prog0.inherit()
            prog0.category = FileUtils::class.java.toString()
            prog0.description = "Reading file"
            prog0.pstart()
            prog0.pdo("Reading ${file.path}")
            ldebug("Getting contents from file: ${file.path}")
            FileInputStream(file).use { fis -> return streamBytes(fis) }
        }
    }
    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun fileBytes(path: String): ByteArray { return fileBytes(File(path)) }

    @ExportGradle
    @JvmStatic @JvmOverloads
    @Throws(IOException::class)
    fun fileString(file: File, charset: Charset = StandardCharsets.UTF_8): String {
        progress(progress_id(file, charset)).use { prog0 ->
            prog0.inherit()
            prog0.category = FileUtils::class.java.toString()
            prog0.description = "Reading file"
            prog0.pstart()
            prog0.pdo("Reading ${file.path}")
            ldebug("Getting contents from file: ${file.path}")
            FileInputStream(file).use { fis ->
                return streamString(fis, charset)
            }
        }
    }
    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun fileString(path: String, charset: Charset = StandardCharsets.UTF_8): String { return fileString(File(path), charset) }

    @ExportGradle
    @JvmStatic
    @Throws(IOException::class)
    fun writeFileBytes(file: File, bytes: ByteArray, off: Int, len: Int) {
        progress(progress_id(file, bytes, off, len)).use { prog0 ->
            prog0.inherit()
            prog0.category = FileUtils::class.java.toString()
            prog0.description = "Writing file"
            prog0.pstart()
            prog0.pdo("Writing ${file.path}")
            ldebug("Writing contents to file: ${file.path}")
            FileOutputStream(file).use { fos ->
                writeBytes(bytes, off, len, fos)
            }
        }
    }
    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun writeFileBytes(path: String, bytes: ByteArray, off: Int, len: Int) { writeFileBytes(File(path), bytes, off, len) }

    @ExportGradle
    @JvmStatic @JvmOverloads
    @Throws(IOException::class)
    fun writeFileString(file: File, string: String, charset: Charset = StandardCharsets.UTF_8) {
        progress(progress_id(file, string, charset)).use { prog0 ->
            prog0.inherit()
            prog0.category = FileUtils::class.java.toString()
            prog0.description = "Writing file"
            prog0.pstart()
            prog0.pdo("Writing ${file.path}")
            ldebug("Writing contents to file: ${file.path}")
            FileOutputStream(file).use { fos ->
                writeString(string, fos, charset)
            }
        }
    }
    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun writeFileString(path: String, string: String, charset: Charset = StandardCharsets.UTF_8) { writeFileString(File(path), string, charset) }

    @ExportGradle @JvmStatic fun fileName(fileName: String): String { return if(fileName.contains(".")) fileName.substring(0, fileName.lastIndexOf(".")) else fileName }
    @ExportGradle @JvmStatic fun fileName(file: File): String { return fileName(file.name) }
    @ExportGradle @JvmStatic fun fileExtension(fileName: String): String { return if(fileName.contains(".")) fileName.substring(fileName.lastIndexOf(".") + 1) else "" }
    @ExportGradle @JvmStatic fun fileExtension(file: File): String { return fileExtension(file.name) }
    @ExportGradle @JvmStatic fun file(parent: File, vararg children: String): File { return File(parent, children.joinToString("/")) }
    @ExportGradle @JvmStatic fun file(vararg children: String): File { return File(children.joinToString("/")) }
    @ExportGradle @JvmStatic fun fileRelative(from: File, what: File): File { return Paths.get(from.canonicalPath).relativize(Paths.get(what.canonicalPath)).toFile() }

    @ExportGradle
    @JvmStatic
    fun mkfile(parent: File, vararg children: String): File {
        val result = file(parent, *children)
        if(result.exists()) return result
        ldebug("Making file: ${result.path}")
        mkdir(result.parentFile)
        try { if(result.createNewFile()) return result }
            catch(e: IOException) { throw Error(exception(e)) }
        throw IllegalStateException("Cannot make file")
    }
    @ExportGradle
    @JvmStatic
    fun mkfile(vararg children: String): File {
        val result = file(*children)
        if(result.exists()) return result
        ldebug("Making file: ${result.path}")
        mkdir(result.parentFile)
        try { if(result.createNewFile()) return result }
            catch(e: IOException) { throw Error(exception(e)) }
        throw IllegalStateException("Cannot make file")
    }

    @ExportGradle
    @JvmStatic
    fun mkdir(parent: File, vararg children: String): File {
        val result = file(parent, *children)
        if(result.exists()) return result
        ldebug("Making directory: ${result.path}")
        if(result.mkdirs()) return result
        throw IllegalStateException("Cannot make directory")
    }
    @ExportGradle
    @JvmStatic
    fun mkdir(vararg children: String): File {
        val result = file(*children)
        if(result.exists()) return result
        ldebug("Making directory: ${result.path}")
        if(result.mkdirs()) return result
        throw IllegalStateException("Cannot make directory")
    }

    @JvmStatic
    internal fun delFile0(file: File) {
        if(!file.exists()) return
        ldebug("Deleting file: ${file.path}")
        if(file.delete()) return
        throw IllegalStateException("Cannot delete file")
    }
    @ExportGradle
    @JvmStatic
    fun delFile(file: File) {
        if(file.isFile) { delFile0(file); return }
        val children = file.listFiles()
        if(children == null) { delFile0(file); return }
        for(child in children) {
            if(child.isDirectory) delFile(child)
            delFile0(child)
        }
        delFile0(file)
    }
}
