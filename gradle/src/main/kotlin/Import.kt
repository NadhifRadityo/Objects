import org.gradle.api.initialization.IncludedBuild
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.File
import java.util.*
import java.util.function.Consumer
import kotlin.collections.HashMap

object Import {
	private val importFiles = HashMap<String, ImportFile>()
	private val importStack = ThreadLocal.withInitial<LinkedList<ImportInfo>> { LinkedList() }
	private val importActions = HashMap<String, Consumer<ImportInfo>>()

	/**
	 * Import script. If the script is already imported before, then it will only extract [ImportFile.exports].
	 * If not, it loads the script, calls the [ImportFile.construct], and extracts the exported list.
	 *
	 * Example:
	 * - `require('test.gradle')` loads `test.gradle` from current path
	 * - `require('./test.gradle')` loads `test.gradle` from project root
	 * - `require('Test:test.gradle')` or `require('Test:./test.gradle')` loads `test.gradle` from project root build `Test`
	 * - `require('Test:test.gradle')` and `test.gradle` contains `require('test2.gradle')`, loads `test.gradle` from project
	 *   root build `Test`, then loads `test2.gradle` from the same build too.
	 */
	fun require(vararg scripts: Array<Any>) {
		val that = Common.lastContext()
		val stack = importStack.get()
		val lastImportInfo = if(stack.size > 0) stack.last else null
		val lastImportFile = if(lastImportInfo != null) importFiles[lastImportInfo.importId] else null
		for(i in 0..scripts.size) {
			var script: Any? = scripts[i]
			var actions: Array<Consumer<ImportInfo>> = arrayOf()
			var build: IncludedBuild? = lastImportFile?.build
			if(script is List<*>) {
				actions = script[0] as Array<Consumer<ImportInfo>>
				script = script[1]
			}

			if(script is String && script.contains(':')) {
				val split = script.indexOf(':')
				val buildName = script.substring(0, split)
				val path = script.substring(split + 1)
				build = that.gradle.includedBuild(buildName)
				script = File(build.projectDir, path)
			} else if(build != null) {
				if(script is String && script.startsWith('/'))
					script = File(build.projectDir, script)
				else if(script is String)
					script = File(that.buildscript.sourceFile!!.parentFile, script)
				else if(script !is File)
					throw IllegalArgumentException("Unsupported argument type")
			} else if(script is String && script.startsWith('/'))
				script = File(that.rootDir, script)
			else script = that.file(script)

			val scriptFile = script as File
			val scriptFilePath = scriptFile.canonicalPath
			val scriptFileName = scriptFile.name
			val scriptFileNameSplit = scriptFileName.lastIndexOf('.')
			val scriptId = if(scriptFileNameSplit == -1) scriptFileName
			else scriptFileName.substring(0, scriptFileNameSplit)

			var importFile = importFiles[scriptId]
			if(importFile != null && importFile.file.canonicalPath != scriptFilePath)
				throw Error(
					"Duplicate gradle script id \"$scriptId\", another " +
							"import is from \"${importFile.file.canonicalPath}\""
				)
			if(importFile == null)
				importFile = ImportFile(
					build, scriptFile, scriptId,
					null, null, null, HashMap(), ArrayList()
				)
			val importInfo = ImportInfo(that, scriptId, actions.toList())
			importFile.imports.add(importInfo)

			val preCheck: () -> Boolean = preCheck@{
				return@preCheck importFile.imports.size == 0
			}
			val postCheck: () -> Boolean = postCheck@{
				if(importFile.context == null)
					throw IllegalStateException("Imported script does not call scriptApply")
				if(that is ExtraPropertiesExtension)
					for(entry in importFile.exports)
						that.set(entry.key, entry.value)
				return@postCheck importFile.imports.add(importInfo)
			}
			val preAction: () -> Unit = preAction@{
				stack.addLast(importInfo)
				for(j in actions.indices step 2)
					actions[j].accept(importInfo)
			}
			val postAction: () -> Unit = postAction@{
				for(j in actions.size - 1 downTo 0 step 2)
					actions[j].accept(importInfo)
				val lastStack = stack.removeLast()
				if(importInfo != lastStack)
					throw IllegalStateException("Must not happen")
			}
			val preCheckResult = preCheck()
			preAction()
			if(preCheckResult)
				that.apply { it.from(that.relativePath(scriptFile)) }
			importActions[scriptFilePath]?.accept(importInfo)
			postAction()
			postCheck()
		}
	}
}
