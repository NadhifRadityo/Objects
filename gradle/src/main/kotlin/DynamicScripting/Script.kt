package DynamicScripting

import Context
import org.gradle.api.initialization.IncludedBuild
import java.io.File

/**
 * Store descriptions of imported script
 *
 * [build] the project where the script is located.
 * The reference will be the relative path to build's project root.
 *
 * [file] resolved script path.
 *
 * [id] the identifier of the import. May be referenced from
 * [ScriptImport] to identify file.
 *
 * [context] the context when the script is loaded. `context(this) { scriptApply() }`
 *
 * [construct] callback when the script is loaded for the first time.
 *
 * [destruct] callback when the all gradle build is finished. May be useful when
 * using daemon, and use the same object.
 *
 * [exports] objects exported by the script. This map will be copied to `ext`.
 *
 * [imports] all references to [ScriptImport] where the [ScriptImport.scriptId] is [id]
 */
open class Script(
	val build: IncludedBuild?,
	val file: File,
	val id: String
) {
	var context: Context? = null
		internal set
	var construct: (() -> Unit)? = null
		internal set
	var destruct: (() -> Unit)? = null
		internal set
	var exports: MutableList<ScriptExport> = ArrayList()
		internal set
	var imports: MutableList<ScriptImport> = ArrayList()
		internal set
}
