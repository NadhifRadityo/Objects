package Gradle.DynamicScripting

import Gradle.Context

/**
 * Store from where the script is imported from.
 *
 * Identify the target script from [scriptId].
 *
 * [context] is the value of `this` from which the script is imported.
 *
 * [with] is an even sized array, where `n/2` is pre action, and `n/2+1` is post action.
 */
open class ScriptImport(
	val id: String,
	val context: Context,
	val scriptId: String,
	val what: List<String>?,
	val being: String?,
	val with: MutableList<ImportAction>
) {
	var imported: Imported? = null
		internal set
}
