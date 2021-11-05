import Common.groovyKotlinCaches
import Common.lastContext
import Common.onBuildFinished
import Keywords.being
import Keywords.with
import Utils.__must_not_happen
import Utils.attachAnyObject
import Utils.attachObject
import Utils.prepareGroovyKotlinCache
import groovy.lang.Closure
import org.gradle.api.initialization.IncludedBuild
import java.io.File
import java.util.*
import kotlin.collections.HashMap

object Import {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null
	@JvmStatic
	private val scripts = HashMap<String, Script>()
	@JvmStatic
	private val stack = ThreadLocal.withInitial<LinkedList<ScriptImport>> { LinkedList() }
	@JvmStatic
	private val actions = HashMap<String, ((ScriptImport) -> Unit)?>()

	@JvmStatic
	fun init() {
		cache = prepareGroovyKotlinCache(Import)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun deinit() {
		groovyKotlinCaches -= cache!!
		cache = null
		scripts.clear()
		Utils.purgeThreadLocal(stack)
		actions.clear()
	}

	@ExportGradle
	@JvmStatic
	fun getScript(id: String): Script {
		return scripts[id]!!
	}
	@ExportGradle
	@JvmStatic
	fun getScript(info: ScriptImport): Script {
		return getScript(info.scriptId)
	}
	@ExportGradle
	@JvmStatic
	fun __getLastImport(): ScriptImport? {
		val stack = stack.get()
		return if(stack.size > 0) stack.last else null
	}
	@ExportGradle
	@JvmStatic
	fun __getLastImportFile(): Script? {
		val lastImport = __getLastImport()
		return if(lastImport != null) scripts[lastImport.scriptId] else null
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
	@JvmStatic
	fun __applyImport(script: Script, what: List<String>?, being: String?) {
		val context = lastContext()
		val interfaces = HashMap<String, (Array<out Any?>) -> Any?>(script.exports.size)
		what?.forEach { if(script.exports.find { e -> e.being == it } == null)
			throw IllegalArgumentException("There's no such export '$it' from ${script.file}") }
		for(export in script.exports) {
			if(what != null && !what.contains(export.being))
				continue
			interfaces["get${export.being.replaceFirstChar { c -> c.uppercase() }}"] = {
				export.what
			}
			interfaces["set${export.being.replaceFirstChar { c -> c.uppercase() }}"] = { args ->
				TODO()
			}
		}
		val cache = prepareGroovyKotlinCache(interfaces)
		if(being == null) {
			attachObject(context, cache)
		} else {
			val dummy = GroovyInteroperability.DummyGroovyObject()
			attachAnyObject(dummy, cache)
			dummy.finalize()
			context.project.extensions.extraProperties.set(being, dummy)
		}
	}

	/**
	 * Import script. If the script is already imported before, then it will only extract [Script.exports].
	 * If not, it loads the script, calls the [Script.construct], and extracts the exported list.
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
	fun scriptImport(what: List<String>?, from: Any, being: String? = null, with: List<(ScriptImport) -> Unit> = listOf()): List<ScriptExport> {
		val context = lastContext()
		val project = context.project
		val stack = stack.get()
		val (build, scriptFile) = __getScriptFile(from)

		val scriptPath = scriptFile.canonicalPath
		val scriptName = scriptFile.name
		val scriptNameSplit = scriptName.lastIndexOf('.')
		val scriptId = if(scriptNameSplit == -1) scriptName
				else scriptName.substring(0, scriptNameSplit)

		val script = scripts.computeIfAbsent(scriptId) {
			Script(build, scriptFile, scriptId)
		}
		val scriptImport = ScriptImport(context, scriptId, what, being, ArrayList(with))
		if(script.file.canonicalPath != scriptPath)
			throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
					"import is from \"${script.file.canonicalPath}\"")

		val preCheck: () -> Boolean = preCheck@{
			return@preCheck script.imports.size == 0
		}
		val postCheck: () -> Boolean = postCheck@{
			if(script.context == null)
				throw IllegalStateException("Imported script does not call scriptApply()")
			__applyImport(script, what, being)
			return@postCheck script.imports.add(scriptImport)
		}
		val preAction: () -> Unit = preAction@{
			for(j in with.indices step 2)
				with[j](scriptImport)
		}
		val postAction: () -> Unit = postAction@{
			for(j in with.size - 1 downTo 0 step 2)
				with[j](scriptImport)
		}
		val catchCheck: (Throwable) -> Throwable? = catchCheck@{ e ->
			script.imports.remove(scriptImport)
			return@catchCheck e
		}

		try {
			stack.addLast(scriptImport)
			val preCheckResult = preCheck()
			preAction()
			if(preCheckResult)
				project.apply { it.from(project.relativePath(scriptFile)) }
			this.actions[scriptPath]?.let { it(scriptImport) }
			postAction()
			postCheck()
		} catch(e: Throwable) {
			val e0 = catchCheck(e)
			if(e0 != null) throw e0
		} finally {
			val lastStack = stack.removeLast()
			if(scriptImport != lastStack)
				__must_not_happen()
		}
		return Collections.unmodifiableList(script.exports)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(from: Any, being: String? = null, with: List<(ScriptImport) -> Unit> = listOf()): List<ScriptExport> {
		return scriptImport(null as List<String>?, from, being, with)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: Keywords.From<Any>, being: Keywords.Being<String?> = being(null),
					 with: Keywords.With<List<(ScriptImport) -> Unit>> = with(listOf())): List<ScriptExport>{
		return scriptImport(what, from.user, being.user, with.user)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(from: Keywords.From<Any>, being: Keywords.Being<String?> = being(null),
					 with: Keywords.With<List<(ScriptImport) -> Unit>> = with(listOf())): List<ScriptExport> {
		return scriptImport(null as List<String>?, from.user, being.user, with.user)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: String, with: List<(ScriptExport) -> Unit> = listOf()) {
		val lastImportFile = __getLastImportFile()!!
		lastImportFile.exports += ScriptExport(lastImportFile.id, what, being, ArrayList(with))
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: Keywords.Being<String>, with: Keywords.With<List<(ScriptExport) -> Unit>> = with(listOf())) {
		scriptExport(what, being.user, with.user)
	}
	@ExportGradle
	@JvmStatic
	fun scriptApply() {
		val lastImportFile = __getLastImportFile()!!
		val context = lastContext()
		lastImportFile.context = context
		for(cache in groovyKotlinCaches)
			attachObject(context, cache)
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
	fun addImportAction(script0: Any, action: (ScriptImport) -> Unit) {
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
	fun includeFlags(vararg flags: String): Array<(ScriptImport) -> Unit> {
		return arrayOf(
			{ importInfo -> for(flag in flags) importInfo.context.project.extensions.extraProperties.set("${importInfo.scriptId}_${flag}", true) },
			{ importInfo -> for(flag in flags) importInfo.context.project.extensions.extraProperties.set("${importInfo.scriptId}_${flag}", null) }
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
			ext.has("${lastImport.scriptId}_${flag}")
	}
}
