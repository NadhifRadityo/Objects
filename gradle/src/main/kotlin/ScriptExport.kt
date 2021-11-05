open class ScriptExport(
	val scriptId: String,
	val what: Any?,
	val being: String,
	val with: MutableList<(ScriptExport) -> Unit>
)
