import org.gradle.api.Project
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
 * [ImportInfo] to identify file.
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
 * [imports] all references to [ImportInfo] where the [ImportInfo.importId] is [id]
 */
open class ImportFile(
	val build: IncludedBuild?,
	val file: File,
	val id: String,
	context: Project?,
	construct: (() -> Unit)?,
	destruct: (() -> Unit)?,
	exports: MutableMap<String, Any?>,
	imports: MutableList<ImportInfo>,
) {
	var context: Project? = context
		internal set
	var construct: (() -> Unit)? = construct
		internal set
	var destruct: (() -> Unit)? = destruct
		internal set
	var exports: MutableMap<String, Any?> = exports
		internal set
	var imports: MutableList<ImportInfo> = imports
		internal set
}
