package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.ExceptionUtils.exception
import Gradle.Strategies.LoggerUtils.ldebug
import Gradle.Strategies.ProgressUtils.prog
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
import java.nio.file.CopyOption
import java.nio.file.Files
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

    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun fileBytes(file: File): ByteArray {
        prog(arrayOf(file), FileUtils::class.java, "Reading file", true).use { prog0 ->
            prog0.pdo("Reading ${file.path}")
            ldebug("Getting contents from file: ${file.path}")
            FileInputStream(file).use { fis -> return streamBytes(fis) }
        }
    }
    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun fileBytes(parent: File, vararg children: String): ByteArray { return fileBytes(file(parent, *children)) }
    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun fileBytes(vararg children: String): ByteArray { return fileBytes(file(*children)) }

    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun fileString(file: File, charset: Charset = StandardCharsets.UTF_8): String {
        prog(arrayOf(file, charset), FileUtils::class.java, "Reading file", true).use { prog0 ->
            prog0.pdo("Reading ${file.path}")
            ldebug("Getting contents from file: ${file.path}")
            FileInputStream(file).use { fis ->
                return streamString(fis, charset)
            }
        }
    }
    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun fileString(parent: File, vararg children: String, charset: Charset = StandardCharsets.UTF_8): String { return fileString(file(parent, *children), charset) }
    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun fileString(vararg children: String, charset: Charset = StandardCharsets.UTF_8): String { return fileString(file(*children), charset) }

    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun writeFileBytes(file: File, bytes: ByteArray, off: Int = 0, len: Int = bytes.size) {
        prog(arrayOf(file, bytes, off, len), FileUtils::class.java, "Writing file", true).use { prog0 ->
            prog0.pdo("Writing ${file.path}")
            ldebug("Writing contents to file: ${file.path}")
            FileOutputStream(file).use { fos ->
                writeBytes(bytes, off, len, fos)
            }
        }
    }
    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun writeFileBytes(parent: File, vararg children: String, bytes: ByteArray, off: Int = 0, len: Int = bytes.size) { writeFileBytes(file(parent, *children), bytes, off, len) }
    @ExportGradle @JvmStatic @Throws(IOException::class)
    fun writeFileBytes(vararg children: String, bytes: ByteArray, off: Int = 0, len: Int = bytes.size) { writeFileBytes(file(*children), bytes, off, len) }

    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun writeFileString(file: File, string: String, charset: Charset = StandardCharsets.UTF_8) {
        prog(arrayOf(file, string, charset), FileUtils::class.java, "Writing file", true).use { prog0 ->
            prog0.pdo("Writing ${file.path}")
            ldebug("Writing contents to file: ${file.path}")
            FileOutputStream(file).use { fos ->
                writeString(string, fos, charset)
            }
        }
    }
    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun writeFileString(parent: File, vararg children: String, string: String, charset: Charset = StandardCharsets.UTF_8) { writeFileString(file(parent, *children), string, charset) }
    @ExportGradle @JvmStatic @JvmOverloads @Throws(IOException::class)
    fun writeFileString(vararg children: String, string: String, charset: Charset = StandardCharsets.UTF_8) { writeFileString(file(*children), string, charset) }

    @ExportGradle @JvmStatic fun fileName(fileName: String): String { return if(fileName.contains(".")) fileName.substring(0, fileName.lastIndexOf(".")) else fileName }
    @ExportGradle @JvmStatic fun fileName(file: File): String { return fileName(file.name) }
    @ExportGradle @JvmStatic fun fileExtension(fileName: String): String { return if(fileName.contains(".")) fileName.substring(fileName.lastIndexOf(".") + 1) else "" }
    @ExportGradle @JvmStatic fun fileExtension(file: File): String { return fileExtension(file.name) }
    @ExportGradle @JvmStatic fun file(parent: File, vararg children: String): File { return File(parent, children.joinToString("/")) }
    @ExportGradle @JvmStatic fun file(vararg children: String): File { return File(children.joinToString("/")) }
    @ExportGradle @JvmStatic fun fileRelative(from: File, what: File): File { return Paths.get(from.canonicalPath).relativize(Paths.get(what.canonicalPath)).toFile() }
    @ExportGradle @JvmStatic fun fileExists(parent: File, vararg what: String, matchDirectory: Boolean = false): List<File> { return parent.listFiles()?.filter { it.isDirectory == matchDirectory && what.contains(it.name) } ?: listOf() }
    @ExportGradle @JvmStatic fun fileExists(parent: File, what: String, matchDirectory: Boolean = false): File? { return parent.listFiles()?.first { it.isDirectory == matchDirectory && it.name == what } }

    @ExportGradle @JvmStatic fun fileCopy(from: File, to: File, vararg options: CopyOption): File {
        ldebug("Copying file: ${from.path} to ${to.path} with options ${options.joinToString { it.toString() }}")
        if(from.parentFile.isDirectory && !from.parentFile.exists())
            mkdir(from.parentFile)
        if(to.parentFile.isDirectory && !to.parentFile.exists())
            mkdir(to.parentFile)
        Files.copy(Paths.get(from.canonicalPath), Paths.get(to.canonicalPath), *options);
        return to
    }
    @ExportGradle @JvmStatic fun fileCopyDir(fromDir: File, toDir: File, what: Array<String>, vararg options: CopyOption): Array<File> {
        return what.map { name ->
            val from = file(fromDir, name)
            val to = file(toDir, name)
            fileCopy(from, to, *options)
        }.toTypedArray()
    }

    @ExportGradle @JvmStatic
    fun mkfile(parent: File, vararg children: String): File {
        val result = file(parent, *children)
        if(result.exists()) return result
        ldebug("Making file: ${result.path}")
        mkdir(result.parentFile)
        try { if(result.createNewFile()) return result }
            catch(e: IOException) { throw Error(exception(e)) }
        throw IllegalStateException("Cannot make file")
    }
    @ExportGradle @JvmStatic
    fun mkfile(vararg children: String): File {
        val result = file(*children)
        if(result.exists()) return result
        ldebug("Making file: ${result.path}")
        mkdir(result.parentFile)
        try { if(result.createNewFile()) return result }
            catch(e: IOException) { throw Error(exception(e)) }
        throw IllegalStateException("Cannot make file")
    }

    @ExportGradle @JvmStatic
    fun mkdir(parent: File, vararg children: String): File {
        val result = file(parent, *children)
        if(result.exists()) return result
        ldebug("Making directory: ${result.path}")
        if(result.mkdirs()) return result
        throw IllegalStateException("Cannot make directory")
    }
    @ExportGradle @JvmStatic
    fun mkdir(vararg children: String): File {
        val result = file(*children)
        if(result.exists()) return result
        ldebug("Making directory: ${result.path}")
        if(result.mkdirs()) return result
        throw IllegalStateException("Cannot make directory")
    }

    @JvmStatic
    internal fun delfile0(file: File) {
        if(!file.exists()) return
        ldebug("Deleting file: ${file.path}")
        if(file.delete()) return
        throw IllegalStateException("Cannot delete file ${file.canonicalPath}")
    }
    @ExportGradle @JvmStatic
    fun delfile(parent: File, vararg children: String): File {
        val result = file(parent, *children)
        if(!result.exists()) return result
        if(result.isFile) { delfile0(result); return result; }
        val childrenFile = result.listFiles()
        if(childrenFile == null) { delfile0(result); return result; }
        for(child in childrenFile) {
            if(child.isDirectory) delfile(child)
            else delfile0(child)
        }
        delfile0(result)
        return result
    }
    @ExportGradle @JvmStatic
    fun delfile(vararg children: String): File {
        val result = file(*children)
        if(!result.exists()) return result
        if(result.isFile) { delfile0(result); return result; }
        val childrenFile = result.listFiles()
        if(childrenFile == null) { delfile0(result); return result; }
        for(child in childrenFile) {
            if(child.isDirectory) delfile(child)
            else delfile0(child)
        }
        delfile0(result)
        return result
    }
}
