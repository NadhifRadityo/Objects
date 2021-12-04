import Common.addOnConfigFinished
import Common.addOnConfigStarted
import Common.groovyKotlinCaches
import Common.initContext
import Common.lastContext
import Keywords.being
import Keywords.with
import Utils.__invalid_type
import Utils.__must_not_happen
import Utils.attachAnyObject
import Utils.attachObject
import Utils.parseProperty
import Utils.prepareGroovyKotlinCache
import groovy.lang.Closure
import org.gradle.api.initialization.IncludedBuild
import java.io.File
import java.util.*

typealias ImportAction = (ScriptImport) -> Unit
typealias ExportAction = (ScriptExport, MutableMap<String, (Array<out Any?>) -> Any?>, MutableMap<String, (Array<out Any?>) -> Any?>, MutableMap<String, (Array<out Any?>) -> Any?>) -> Unit

object DynamicScripting {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null
	@JvmStatic
	private val scripts = HashMap<String, Script>()
	@JvmStatic
	private val stack = ThreadLocal.withInitial<LinkedList<ScriptImport>> { LinkedList() }
	@JvmStatic
	private val actions = HashMap<String, ImportAction?>()

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(DynamicScripting)
		groovyKotlinCaches += cache!!
		addOnConfigStarted(0) {
			for(cache in groovyKotlinCaches)
				injectScript(cache)
		}
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
		scripts.clear()
		Utils.purgeThreadLocal(stack)
		actions.clear()
	}

	@ExportGradle
	@JvmStatic
	fun getScript(id: String): Script? {
		return scripts[id]
	}
	@ExportGradle
	@JvmStatic
	fun getScript(info: ScriptImport): Script? {
		return getScript(info.scriptId)
	}
	@ExportGradle
	@JvmStatic
	fun getScript(file: File): Script? {
		return getScript(getScriptId(file))
	}
	@ExportGradle
	@JvmStatic
	fun getScriptId(file: File): String {
		val split = file.name.lastIndexOf('.')
		return if(split == -1) file.name else file.name.substring(0, split)
	}
	@ExportGradle
	@JvmStatic
	fun __getLastImport(): ScriptImport? {
		val stack = stack.get()
		return if(stack.size > 0) stack.last else null
	}
	@ExportGradle
	@JvmStatic
	fun __getLastImportScript(): Script? {
		val lastImport = __getLastImport()
		return if(lastImport != null) getScript(lastImport) else null
	}

	@JvmStatic
	fun __getScriptFile(scriptObj: Any): Pair<IncludedBuild?, File> {
		val project = lastContext().project
		val lastImportScript = __getLastImportScript()
		val build: IncludedBuild?
		val scriptFile: File

		if(scriptObj is String && scriptObj.contains(':')) {
			val split = scriptObj.indexOf(':')
			val buildName = scriptObj.substring(0, split)
			val path = scriptObj.substring(split + 1)
			build = project.gradle.includedBuild(buildName)
			scriptFile = File(build.projectDir, path)
		} else if(lastImportScript?.build != null) {
			build = lastImportScript.build
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
	fun __applyImport(scriptImport: ScriptImport, script: Script) {
		val context = lastContext()
		scriptImport.what?.forEach { if(script.exports.find { e -> e.being == it } == null)
			throw IllegalArgumentException("There's no such export '$it' from ${script.file}") }
		val methods = HashMap<String, (Array<out Any?>) -> Any?>()
		val getter = HashMap<String, (Array<out Any?>) -> Any?>()
		val setter = HashMap<String, (Array<out Any?>) -> Any?>()
		for(export in script.exports) {
			if(scriptImport.what != null && !scriptImport.what.contains(export.being))
				continue
			export.with.forEach { it(export, methods, getter, setter) }
		}
		val cache = prepareGroovyKotlinCache(scriptImport, methods, getter, setter)
		if(containsFlag("expose_exports", scriptImport))
			attachObject(context, cache)
		if(scriptImport.being == null) return
		val dummy = GroovyInteroperability.DummyGroovyObject()
		dummy.__start__()
		attachAnyObject(dummy, cache)
		dummy.__end__()
		context.project.extensions.extraProperties.set(scriptImport.being, dummy)
	}
	@JvmStatic
	fun injectScript(cache: GroovyKotlinCache<*>) {
		val context = initContext!!
		val source = File(context.project.rootDir, "std$${cache.ownerJavaClass.simpleName}")
		val (build, scriptFile) = __getScriptFile(source)
		val scriptId = getScriptId(scriptFile)
		val script = scripts.computeIfAbsent(scriptId) { Script(build, scriptFile, scriptId) }
		if(script.file.canonicalPath != scriptFile.path)
			throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
					"import is from \"${script.file.canonicalPath}\"")
		script.context = context
		for(pushed in cache.pushed.values) {
			val names = pushed.names
			val value = pushed.closure
			for(name in names) {
				val (isGetter, isGetterBoolean, isSetter) = parseProperty(name)
				val rawName = if(isGetter || isGetterBoolean || isSetter) {
					val propertyName0 = if(isGetterBoolean) name.substring(2) else name.substring(3)
					if(propertyName0.length <= 1) propertyName0.lowercase()
					else if(propertyName0.uppercase() == propertyName0) propertyName0
					else propertyName0.replaceFirstChar { it.lowercase() }
				} else name
				if(rawName.startsWith("__INTERNAL_")) continue
				val with = ArrayList<ExportAction>()
				if(isGetter || isGetterBoolean) with += exportGetter()
				if(isSetter) with += exportSetter()
				if(!isGetter && !isGetterBoolean && !isSetter) with += exportMethod()
				script.exports += ScriptExport(script.id, value, rawName, with)
			}
		}
	}

	/**
	 * Import script. If the script is already imported before, then it will only extract [Script.exports].
	 * If not, it loads the script, calls the [Script.construct], and extracts the exported list.
	 *
	 * Example:
	 * - `require('test.gradle')` loads `test.gradle` from current path
	 * - `require('/test.gradle')` loads `test.gradle` from project root
	 * - `require('Test:test.gradle')` or `require('Test:/test.gradle')` loads `test.gradle` from project root build `Test`
	 * - `require('Test:test.gradle')` and `test.gradle` contains `require('test2.gradle')`, loads `test.gradle` from project
	 *   root build `Test`, then loads `test2.gradle` from the same build too.
	 */
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: Any, being: String? = null, with: List<ImportAction> = listOf()): List<ScriptExport> {
		val context = lastContext()
		val project = context.project
		val stack = stack.get()

		val (build, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		val script = scripts.computeIfAbsent(scriptId) { Script(build, scriptFile, scriptId) }
		val scriptImport = ScriptImport(context, scriptId, what, being, ArrayList(with))
		if(script.file.canonicalPath != scriptFile.path)
			throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
					"import is from \"${script.file.canonicalPath}\"")

		val preCheck: () -> Boolean = preCheck@{
			return@preCheck script.context == null
		}
		val postCheck: () -> Boolean = postCheck@{
			if(script.context == null)
				throw IllegalStateException("Imported script does not call scriptApply()")
			__applyImport(scriptImport, script)
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
			this.actions[scriptId]?.let { it(scriptImport) }
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
	fun scriptImport(from: Any, being: String? = null, with: List<ImportAction> = listOf()): List<ScriptExport> {
		return scriptImport(null as List<String>?, from, being, with)
	}
	@ExportGradle
	@JvmStatic
	fun scriptImport(from: Any, with: List<ImportAction>): List<ScriptExport> {
		return scriptImport(null as List<String>?, from, null, with)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: Keywords.From<Any>, being: Keywords.Being<String?> = being(null),
					 with: Keywords.With<List<ImportAction>> = with(listOf())): List<ScriptExport>{
		return scriptImport(what, from.user, being.user, with.user)
	}
	@ExportGradle
	@JvmStatic
	fun scriptImport(what: List<String>?, from: Keywords.From<Any>, with: Keywords.With<List<ImportAction>>): List<ScriptExport> {
		return scriptImport(what, from.user, null, with.user)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptImport(from: Keywords.From<Any>, being: Keywords.Being<String?> = being(null),
					 with: Keywords.With<List<ImportAction>> = with(listOf())): List<ScriptExport> {
		return scriptImport(null as List<String>?, from.user, being.user, with.user)
	}
	@ExportGradle
	@JvmStatic
	fun scriptImport(from: Keywords.From<Any>, with: Keywords.With<List<ImportAction>>): List<ScriptExport> {
		return scriptImport(null as List<String>?, from.user, null, with.user)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: String, with: List<ExportAction> = listOf()) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.exports += ScriptExport(lastImportScript.id, what, being, ArrayList(with))
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: Keywords.Being<String>, with: Keywords.With<List<ExportAction>> = with(exportGetter())) {
		scriptExport(what, being.user, with.user)
	}
	@ExportGradle
	@JvmStatic
	fun scriptApply() {
		val lastImportScript = __getLastImportScript()!!
		val context = lastContext()
		lastImportScript.context = context
		for(cache in groovyKotlinCaches)
			attachObject(context, cache)
	}
	@ExportGradle
	@JvmStatic
	fun scriptConstruct(callback: () -> Unit) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.construct = callback
		callback()
	}
	@ExportGradle
	@JvmStatic
	fun scriptDestruct(callback: () -> Unit) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.destruct = callback
		addOnConfigFinished(1, callback)
	}

	@ExportGradle
	@JvmStatic
	fun addImportAction(from: Any, action: ImportAction) {
		val (_, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		actions[scriptId] = action
	}
	@ExportGradle
	@JvmStatic
	fun removeImportAction(from: Any) {
		val (_, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		actions.remove(scriptId)
	}

	@ExportGradle
	@JvmStatic
	fun includeFlags(vararg flags: String): List<ImportAction> {
		return listOf(
			{ importInfo -> for(flag in flags) importInfo.context.project.extensions.extraProperties.set("${importInfo.scriptId}_${flag}", true) },
			{ importInfo -> for(flag in flags) importInfo.context.project.extensions.extraProperties.set("${importInfo.scriptId}_${flag}", null) }
		)
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun containsFlag(flag: String, import: ScriptImport? = __getLastImport()): Boolean {
		val context = import?.context ?: lastContext()
		val ext = context.project.extensions.extraProperties
		return if(import == null) ext.has(flag)
		else ext.has("${import.scriptId}_${flag}")
	}

	@ExportGradle
	@JvmStatic
	fun exportMethod(): List<ExportAction> {
		return listOf { exportInfo, methods, _, _ ->
			methods[exportInfo.being] = { args ->
				val callback = exportInfo.what
				if(callback is Closure<*>)
					callback.call(*args)
				else __invalid_type()
			}
		}
	}
	@ExportGradle
	@JvmStatic
	fun exportGetter(): List<ExportAction> {
		return listOf { exportInfo, _, getter, _ -> getter[exportInfo.being] = { exportInfo.what } }
	}
	@ExportGradle
	@JvmStatic
	fun exportSetter(): List<ExportAction> {
		return listOf { exportInfo, _, _, setter -> setter[exportInfo.being] = { args -> exportInfo.what = args[0] } }
	}
}
