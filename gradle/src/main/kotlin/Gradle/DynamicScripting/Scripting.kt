package Gradle.DynamicScripting

import Gradle.Common.addOnConfigFinished
import Gradle.Common.addOnConfigStarted
import Gradle.Common.currentSession
import Gradle.Common.groovyKotlinCaches
import Gradle.Common.lastContext
import Gradle.Context
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.attachAnyObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.attachObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.KotlinClosure
import Gradle.GroovyKotlinInteroperability.parseProperty
import Gradle.Strategies.CommonUtils.purgeThreadLocal
import Gradle.Strategies.GradleUtils.asBuildProject0
import Gradle.Strategies.KeywordsUtils
import Gradle.Strategies.KeywordsUtils.being
import Gradle.Strategies.KeywordsUtils.with
import Gradle.Strategies.StringUtils.randomString
import Gradle.Strategies.Utils.__invalid_type
import Gradle.Strategies.Utils.__must_not_happen
import groovy.lang.Closure
import org.gradle.api.initialization.IncludedBuild
import org.gradle.api.internal.file.BaseDirFileResolver
import java.io.File
import java.util.*

object Scripting {
	@JvmStatic private var cache: GroovyKotlinCache<Scripting>? = null
	@JvmStatic internal val scripts = HashMap<String, Script>()
	@JvmStatic internal val stack = ThreadLocal.withInitial<LinkedList<ScriptImport>> { LinkedList() }
	@JvmStatic internal val actions = HashMap<String, ImportAction?>()
	@JvmStatic internal val builds = ArrayList<IncludedBuild>()
	@JvmStatic internal val injectScripts = ArrayList<GroovyKotlinCache<*>>()

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(Scripting)
		groovyKotlinCaches += cache!!
		addOnConfigStarted(0) {
			for(cache in groovyKotlinCaches)
				addInjectScript(cache)
		}
		addOnConfigFinished(0) {
			for(cache in groovyKotlinCaches)
				removeInjectScript(cache)
		}
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
		scripts.clear()
		purgeThreadLocal(stack)
		actions.clear()
	}

	@ExportGradle @JvmStatic
	fun getScript(id: String): Script? {
		return scripts[id]
	}
	@ExportGradle @JvmStatic
	fun getScript(info: ScriptImport): Script? {
		return getScript(info.scriptId)
	}
	@ExportGradle @JvmStatic
	fun getScript(file: File): Script? {
		return getScript(getScriptId(file))
	}
	@ExportGradle @JvmStatic
	fun getScriptId(file: File): String {
		return file.canonicalPath
	}
	@ExportGradle @JvmStatic
	fun __getLastImport(): ScriptImport? {
		val stack = stack.get()
		return if(stack.size > 0) stack.last else null
	}
	@ExportGradle @JvmStatic
	fun __getLastImportScript(): Script? {
		val lastImport = __getLastImport()
		return if(lastImport != null) getScript(lastImport) else null
	}

	@JvmStatic
	fun __checkBuild(build: IncludedBuild?) {
		if(build == null || builds.contains(build)) return
		val context = Context(build, asBuildProject0(build))
		for(injectScript in injectScripts)
			__addInjectScript(context, injectScript)
		builds += build
	}
	@JvmStatic
	fun __getScriptFile(scriptObj: Any): Pair<IncludedBuild?, File> {
		val context = lastContext()
		val (that, _, project) = context
		val lastImportScript = __getLastImportScript()
		val build: IncludedBuild?
		val scriptFile: File

		if(scriptObj is String && scriptObj.contains(':')) {
			val split = scriptObj.indexOf(':')
			val buildName = scriptObj.substring(0, split)
			val path = scriptObj.substring(split + 1)
			build = project.gradle.includedBuild(buildName)
			scriptFile = File(build.projectDir, path)
		} else if(lastImportScript != null) {
			build = lastImportScript.build
			scriptFile = if(scriptObj is String && scriptObj.startsWith('/')) File(if(build != null) build.projectDir else project.rootDir, scriptObj)
				else BaseDirFileResolver(lastImportScript.file.parentFile).resolve(scriptObj)
		} else if(scriptObj is String && scriptObj.startsWith('/')) {
			build = null
			scriptFile = File(project.rootDir, scriptObj)
		} else {
			build = null
			scriptFile = (that as org.gradle.api.Script).file(scriptObj)
		}
		__checkBuild(build)
		return Pair(build, scriptFile)
	}

	/**
	 * - `scriptImport from('test.gradle')` only run script without exporting
	 * - `scriptImport from('test.gradle'), with(includeFlags('expose_exports'))` expose all exports as global variable
	 * - `scriptImport listOf('testMethod'), from('test.gradle')` export only `testMethod` as global variable
	 * - `scriptImport from('test.gradle'), being('test')` export all exports wrapped in `test` global variable
	 * - `scriptImport listOf('testMethod'), from('test.gradle'), being('test')` export only `testMethod` wrapped in `test` global variable
	 * - `scriptImport listOf('testMethod'), from('test.gradle'), being('test'), with(includeFlags('expose_exports'))` export only `testMethod` as global variable and wrapped in `test` global variable
	 */
	@JvmStatic
	fun __applyImport(scriptImport: ScriptImport, script: Script) {
		val context = lastContext()
		val project = context.project0
		val methods = HashMap<String, (Array<out Any?>) -> Any?>()
		val getter = HashMap<String, (Array<out Any?>) -> Any?>()
		val setter = HashMap<String, (Array<out Any?>) -> Any?>()

		scriptImport.what?.forEach { if(script.exports.find { e -> e.being == it } == null)
			throw IllegalArgumentException("There's no such export '$it' from ${script.file}") }
		for(export in script.exports) {
			if(scriptImport.what != null && !scriptImport.what.contains(export.being))
				continue
			export.with.forEach { it(export, methods, getter, setter) }
		}
		val imported = Imported(script.id, scriptImport.id)
		scriptImport.imported = imported
		imported.cache = prepareGroovyKotlinCache(scriptImport, methods, getter, setter)
		imported.__start__()
		attachAnyObject(imported, imported.cache!!)
		imported.__end__()
		if((scriptImport.what != null && scriptImport.being == null) || containsFlag("expose_exports", scriptImport))
			attachObject(context, imported.cache!!)
		if(scriptImport.being != null)
			project.extensions.extraProperties.set(scriptImport.being, imported)
	}
	@JvmStatic
	fun __addInjectScript(context: Context, cache: GroovyKotlinCache<*>) {
		val project = context.project0
		val source = File(project.rootDir, "std$${cache.ownerJavaClass.simpleName}")
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
//				if(rawName.startsWith("__INTERNAL_")) continue
				var export = script.exports.find { it.being == rawName }
				if(export == null) {
					export = ScriptExport(script.id, ArrayList<Any>(), rawName, ArrayList())
					script.exports += export
				}
				(export.what as ArrayList<Any>) += value
				if(isGetter || isGetterBoolean) export.with += { exportInfo, _, getter, _ -> getter[exportInfo.being] = { args -> value.call(*args) } }
				if(isSetter) export.with += { exportInfo, _, _, setter -> setter[exportInfo.being] = { args -> value.call(*args) } }
				if(!isGetter && !isGetterBoolean && !isSetter) export.with += { exportInfo, methods, _, _ -> methods[exportInfo.being] = { args -> {
					val callback = exportInfo.what
					if(callback is Closure<*>)
						callback.call(*args)
					else __invalid_type()
				} } }
			}
		}
	}
	@JvmStatic
	fun __removeInjectScript(context: Context, cache: GroovyKotlinCache<*>) {
		val project = context.project0
		val source = File(project.rootDir, "std$${cache.ownerJavaClass.simpleName}")
		val (_, scriptFile) = __getScriptFile(source)
		val scriptId = getScriptId(scriptFile)
		scripts.remove(scriptId)
	}

	@JvmStatic
	fun addInjectScript(cache: GroovyKotlinCache<*>) {
		if(currentSession == null) return
		injectScripts += cache
		__addInjectScript(currentSession!!.context, cache)
	}
	@JvmStatic
	fun removeInjectScript(cache: GroovyKotlinCache<*>) {
		if(currentSession == null) return
		__removeInjectScript(currentSession!!.context, cache)
		injectScripts -= cache
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
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: Any, being: String? = null, with: List<ImportAction> = listOf()): Imported {
		val context = lastContext()
		val project = context.project0
		val stack = stack.get()

		val (build, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		val script = scripts.computeIfAbsent(scriptId) { Script(build, scriptFile, scriptId) }
		val scriptImportId = "$scriptId#${randomString()}"
		val scriptImport = ScriptImport(scriptImportId, context, scriptId, what, being, ArrayList(with))
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
			actions[scriptId]?.let { it(scriptImport) }
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
		return scriptImport.imported!!
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(from: Any, being: String? = null, with: List<ImportAction> = listOf()): Imported {
		return scriptImport(null as List<String>?, from, being, with)
	}
	@ExportGradle @JvmStatic
	fun scriptImport(from: Any, with: List<ImportAction>): Imported {
		return scriptImport(null as List<String>?, from, null, with)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: KeywordsUtils.From<Any>, being: KeywordsUtils.Being<String?> = being(null),
					 with: KeywordsUtils.With<List<ImportAction>> = with(listOf())): Imported {
		return scriptImport(what, from.user, being.user, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptImport(what: List<String>?, from: KeywordsUtils.From<Any>, with: KeywordsUtils.With<List<ImportAction>>): Imported {
		return scriptImport(what, from.user, null, with.user)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(from: KeywordsUtils.From<Any>, being: KeywordsUtils.Being<String?> = being(null),
					 with: KeywordsUtils.With<List<ImportAction>> = with(listOf())): Imported {
		return scriptImport(null as List<String>?, from.user, being.user, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptImport(from: KeywordsUtils.From<Any>, with: KeywordsUtils.With<List<ImportAction>>): Imported {
		return scriptImport(null as List<String>?, from.user, null, with.user)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: String, with: List<ExportAction> = listOf()) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.exports += ScriptExport(lastImportScript.id, what, being, ArrayList(with))
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: KeywordsUtils.Being<String>, with: KeywordsUtils.With<List<ExportAction>> = with(exportGetter())) {
		scriptExport(what, being.user, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptApply() {
		val lastImportScript = __getLastImportScript()!!
		val context = lastContext()
		lastImportScript.context = context
		for(cache in groovyKotlinCaches)
			attachObject(context, cache)
	}
	@ExportGradle @JvmStatic
	fun scriptConstruct(callback: () -> Unit) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.construct = callback
		callback()
	}
	@ExportGradle @JvmStatic
	fun scriptDestruct(callback: () -> Unit) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.destruct = callback
		addOnConfigFinished(1, callback)
	}

	@ExportGradle @JvmStatic
	fun addImportAction(from: Any, action: ImportAction) {
		val (_, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		actions[scriptId] = action
	}
	@ExportGradle @JvmStatic
	fun removeImportAction(from: Any) {
		val (_, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		actions.remove(scriptId)
	}

	@ExportGradle @JvmStatic
	fun includeFlags(vararg flags: String): List<ImportAction> {
		return listOf(
			{ importInfo -> for(flag in flags) importInfo.context.project0.extensions.extraProperties.set("${importInfo.scriptId}_${flag}", true) },
			{ importInfo -> for(flag in flags) importInfo.context.project0.extensions.extraProperties.set("${importInfo.scriptId}_${flag}", null) }
		)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun containsFlag(flag: String, scriptImport: ScriptImport? = __getLastImport()): Boolean {
		val context = scriptImport?.context ?: lastContext()
		val project = context.project0
		val ext = project.extensions.extraProperties
		return if(scriptImport == null) ext.has(flag)
		else ext.has("${scriptImport.scriptId}_${flag}")
	}

	@ExportGradle @JvmStatic
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
	@ExportGradle @JvmStatic
	fun exportGetter(): List<ExportAction> {
		return listOf { exportInfo, _, getter, _ -> getter[exportInfo.being] = { exportInfo.what } }
	}
	@ExportGradle @JvmStatic
	fun exportSetter(): List<ExportAction> {
		return listOf { exportInfo, _, _, setter -> setter[exportInfo.being] = { args -> exportInfo.what = args[0] } }
	}

	@ExportGradle @JvmStatic
	fun withCurrentImport(): Closure<*> {
		val lastImport = __getLastImport()!!
		val result = KotlinClosure("scriptState (${lastImport.id})")
		result.overloads += KotlinClosure.KLambdaOverload { args ->
			val stack = stack.get()
			stack.addLast(lastImport)
			try {
				(args[0] as Closure<*>).call()
			} finally {
				val lastStack = stack.removeLast()
				if(lastImport != lastStack)
					__must_not_happen()
			}
		}
		return result
	}
}
