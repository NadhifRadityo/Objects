import Common.lastContext
import Common.onBuildFinished
import Utils.__must_not_happen
import Utils.attachObject
import org.gradle.api.initialization.IncludedBuild
import java.io.File
import java.util.*

object Import {
	@JvmStatic
	private val files = HashMap<String, ImportFile>()
	@JvmStatic
	private val stack = ThreadLocal.withInitial<LinkedList<ImportInfo>> { LinkedList() }
	@JvmStatic
	private val actions = HashMap<String, ((ImportInfo) -> Unit)?>()

	@JvmStatic
	fun init() {
		Utils.pushKotlinToGradle(Import)
	}
	@JvmStatic
	fun deinit() {
		Utils.pullKotlinFromGradle(Import)
		files.clear()
		Utils.purgeThreadLocal(stack)
		actions.clear()
	}

	@JvmStatic
	fun __getLastImport(): ImportInfo? {
		val stack = stack.get()
		return if(stack.size > 0) stack.last else null
	}
	@JvmStatic
	fun __getLastImportFile(): ImportFile? {
		val lastImport = __getLastImport()
		return if(lastImport != null) files[lastImport.importId] else null
	}
	@JvmStatic
	fun __getScriptFile(scriptObj: Any): Pair<IncludedBuild?, File> {
		val project = lastContext().project
		val lastImportFile = __getLastImportFile()
		val build: IncludedBuild?
		val scriptFile: File

		if(scriptObj is String && scriptObj.contains(':')) {
			val split = scriptObj.indexOf(':')
			val buildName = scriptObj.substring(0, split)
			val path = scriptObj.substring(split + 1)
			build = project.gradle.includedBuild(buildName)
			scriptFile = File(build.projectDir, path)
		} else if(lastImportFile?.build != null) {
			build = lastImportFile.build
			scriptFile = if(scriptObj is String && scriptObj.startsWith('/'))
				File(build.projectDir, scriptObj)
			else if(scriptObj is String)
				File(project.buildscript.sourceFile!!.parentFile, scriptObj)
			else if(scriptObj is File)
				scriptObj
			else
				throw IllegalArgumentException("Unsupported argument type")
		} else if(scriptObj is String && scriptObj.startsWith('/')) {
			build = null
			scriptFile = File(project.rootDir, scriptObj)
		} else {
			build = null
			scriptFile = project.file(scriptObj)
		}
		return Pair(build, scriptFile)
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
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(scriptObj: Any, asVariable: String? = null, actions: Array<(ImportInfo) -> Unit> = arrayOf()): Map<String, Any?> {
		val context = lastContext()
		val project = context.project
		val ext = project.extensions.extraProperties
		val stack = stack.get()
		val (build, scriptFile) = __getScriptFile(scriptObj)

		val scriptPath = scriptFile.canonicalPath
		val scriptName = scriptFile.name
		val scriptNameSplit = scriptName.lastIndexOf('.')
		val scriptId = if(scriptNameSplit == -1) scriptName
				else scriptName.substring(0, scriptNameSplit)

		val importFile = files.computeIfAbsent(scriptId) {
			ImportFile(build, scriptFile, scriptId, null, null, null, HashMap(), ArrayList())
		}
		val importInfo = ImportInfo(context, scriptId, actions.toList())
		if(importFile.file.canonicalPath != scriptPath)
			throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
					"import is from \"${importFile.file.canonicalPath}\"")

		val preCheck: () -> Boolean = preCheck@{
			return@preCheck importFile.imports.size == 0
		}
		val postCheck: () -> Boolean = postCheck@{
			if(importFile.context == null)
				throw IllegalStateException("Imported script does not call apply()")
			if(asVariable == null)
				for(entry in importFile.exports)
					ext.set(entry.key, entry.value)
			else
				ext.set(asVariable, Collections.unmodifiableMap(importFile.exports))
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
				__must_not_happen()
		}

		try {
			val preCheckResult = preCheck()
			preAction()
			if(preCheckResult)
				project.apply { it.from(project.relativePath(scriptFile)) }
			this.actions[scriptPath]?.let { it(importInfo) }
			postAction()
			postCheck()
		} catch(e: Throwable) {
			val e0 = catchCheck(e)
			if(e0 != null) throw e0
		}
		return Collections.unmodifiableMap(importFile.exports)
	}
	@ExportGradle
	@JvmStatic
	fun scriptExport(key: String, value: Any?) {
		val lastImportFile = __getLastImportFile()!!
		lastImportFile.exports[key] = value
	}
	@ExportGradle
	@JvmStatic
	fun scriptApply() {
		val lastImportFile = __getLastImportFile()!!
		val context = lastContext()
		lastImportFile.context = context
		attachObject(context)
	}
	@ExportGradle
	@JvmStatic
	fun scriptConstruct(callback: () -> Unit) {
		val lastImportFile = __getLastImportFile()!!
		lastImportFile.construct = callback
		callback()
	}
	@ExportGradle
	@JvmStatic
	fun scriptDestruct(callback: () -> Unit) {
		val lastImportFile = __getLastImportFile()!!
		lastImportFile.destruct = callback
		onBuildFinished += callback
	}

	@ExportGradle
	@JvmStatic
	fun addImportAction(script0: Any, action: (ImportInfo) -> Unit) {
		val (_, script) = __getScriptFile(script0)
		val scriptPath = script.canonicalPath
		actions[scriptPath] = action
	}
	@ExportGradle
	@JvmStatic
	fun removeImportAction(script0: Any) {
		val (_, script) = __getScriptFile(script0)
		val scriptPath = script.canonicalPath
		actions.remove(scriptPath)
	}

	@ExportGradle
	@JvmStatic
	fun includeFlags(vararg flags: String): Array<(ImportInfo) -> Unit> {
		return arrayOf(
			{ importInfo -> for(flag in flags) importInfo.context.project.extensions.extraProperties.set("${importInfo.importId}_${flag}", true) },
			{ importInfo -> for(flag in flags) importInfo.context.project.extensions.extraProperties.set("${importInfo.importId}_${flag}", null) }
		)
	}
	@ExportGradle
	@JvmStatic
	fun containsFlag(flag: String): Boolean {
		val lastImport = __getLastImport()
		val context = lastImport?.context ?: lastContext()
		val ext = context.project.extensions.extraProperties
		return if(lastImport == null)
			ext.has(flag)
		else
			ext.has("${lastImport.importId}_${flag}")
	}
}
