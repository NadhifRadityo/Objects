annotation class ExportGradle(
	val names: Array<String> = [], // FIELD, METHOD
	val allowSet: Boolean = true, // FIELD
	val asProperty: Boolean = true, // METHOD
	val includeSelf: Boolean = false // FIELD, METHOD
)
