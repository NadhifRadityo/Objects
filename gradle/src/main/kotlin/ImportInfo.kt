/**
 * Store from where the script is imported from.
 *
 * Identify the target script from [scriptId].
 *
 * [context] the value `this` from which the script is imported.
 *
 * [actions] an even sized array, where `n/2` is pre action, and `n/2+1` is post action.
 */
data class ImportInfo(
	val context: Context,
	val scriptId: String,
	val actions: List<(ImportInfo) -> Unit>,
)
