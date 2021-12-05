package GroovyKotlinInteroperability

annotation class ExportGradle(
	val names: Array<String> = [], // FIELD, METHOD
	val allowSet: Boolean = true, // FIELD
	val asProperty: Boolean = true, // METHOD
	val additionalOverloads: Int = 1 // FIELD, METHOD, 0=disabled, 1=IgnoreSelfOverload, 2=WithSelfOverload
)
