import org.gradle.api.initialization.IncludedBuild
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.File
import java.util.*
import kotlin.collections.HashMap

object Import {
	private val importFiles = HashMap<String, ImportFile>()
	private val importStack = ThreadLocal.withInitial<LinkedList<ImportInfo>> { LinkedList() }
	private val importActions = HashMap<String, ((ImportInfo) -> Unit)?>()

	fun __getLastImport(): ImportFile? {
		val stack = importStack.get()
		return if(stack.size > 0) importFiles[stack.last.importId] else null
	}
	fun __getScriptFile(file: Any?): Pair<IncludedBuild?, File> {
		val that = Common.lastContext()
		val lastImport = __getLastImport()
		val build: IncludedBuild?
		val script: File

		if(file is String && file.contains(':')) {
			val split = file.indexOf(':')
			val buildName = file.substring(0, split)
			val path = file.substring(split + 1)
			build = that.gradle.includedBuild(buildName)
			script = File(build.projectDir, path)
		} else if(lastImport?.build != null) {
			build = lastImport.build
			script = if(file is String && file.startsWith('/'))
				File(build.projectDir, file)
			else if(file is String)
				File(that.buildscript.sourceFile!!.parentFile, file)
			else if(file is File)
				file
			else
				throw IllegalArgumentException("Unsupported argument type")
		} else if(file is String && file.startsWith('/')) {
			build = null
			script = File(that.rootDir, file)
		} else {
			build = null
			script = that.file(file)
		}
		return Pair(build, script)
	}

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
	fun require(vararg scripts: Any) {
		val that = Common.lastContext()
		val stack = importStack.get()
		for(i in 0..scripts.size) {
			val actions: Array<(ImportInfo) -> Unit>
			val pair: Pair<IncludedBuild?, File>
			if(scripts[i] is List<*>) {
				val arg = scripts[i] as List<*>
				actions = arg[0] as Array<(ImportInfo) -> Unit>
				pair = __getScriptFile(arg[1])
			} else {
				actions = arrayOf()
				pair = __getScriptFile(scripts[i])
			}
			val (build, script) = pair

			val scriptPath = script.canonicalPath
			val scriptName = script.name
			val scriptNameSplit = scriptName.lastIndexOf('.')
			val scriptId = if(scriptNameSplit == -1) scriptName
					else scriptName.substring(0, scriptNameSplit)

			val importFile = importFiles.computeIfAbsent(scriptId) {
				ImportFile(build, script, scriptId, null, null, null, HashMap(), ArrayList())
			}
			val importInfo = ImportInfo(that, scriptId, actions.toList())
			if(importFile.file.canonicalPath != scriptPath)
				throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
						"import is from \"${importFile.file.canonicalPath}\"")

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
			val catchCheck: (Throwable) -> Throwable? = catchCheck@{ e ->
				importFile.imports.remove(importInfo)
				return@catchCheck e
			}
			val preAction: () -> Unit = preAction@{
				stack.addLast(importInfo)
				for(j in actions.indices step 2)
					actions[j](importInfo)
			}
			val postAction: () -> Unit = postAction@{
				for(j in actions.size - 1 downTo 0 step 2)
					actions[j](importInfo)
				val lastStack = stack.removeLast()
				if(importInfo != lastStack)
					throw IllegalStateException("Must not happen")
			}

			try {
				val preCheckResult = preCheck()
				preAction()
				if(preCheckResult)
					that.apply { it.from(that.relativePath(script)) }
				importActions[scriptPath]?.let { it(importInfo) }
				postAction()
				postCheck()
			} catch(e: Throwable) {
				val e0 = catchCheck(e)
				if(e0 != null) throw e0
			}
		}
	}

	fun addImportAction(vararg scripts: Any, action: (ImportInfo) -> Unit) {
		for(i in 0..scripts.size) {
			val (_, script) = __getScriptFile(scripts[i])
			val scriptPath = script.canonicalPath
			importActions[scriptPath] = action
		}
	}
	fun removeImportAction(vararg scripts: Any) {
		for(i in 0..scripts.size) {
			val (_, script) = __getScriptFile(scripts[i])
			val scriptPath = script.canonicalPath
			importActions.remove(scriptPath)
		}
	}

	fun includeFlags(vararg flags: String): Array<(ImportInfo) -> Unit> {
		return arrayOf(
			{ importInfo -> for(flag in flags) (importInfo.context as ExtraPropertiesExtension).set("${importInfo.importId}_${flag}", true) },
			{ importInfo -> for(flag in flags) (importInfo.context as ExtraPropertiesExtension).set("${importInfo.importId}_${flag}", null) }
		)
	}
	fun containsFlag(flag: String): Boolean {
		val that = Common.lastContext()
		val lastImport = __getLastImport()
		return if(lastImport == null)
			(that as ExtraPropertiesExtension).has(flag)
		else
			(that as ExtraPropertiesExtension).has("${lastImport.id}_${flag}")
	}
}
