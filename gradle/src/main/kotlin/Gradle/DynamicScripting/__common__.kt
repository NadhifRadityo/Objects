package Gradle.DynamicScripting

fun construct() {
	Scripting.construct()
}
fun destruct() {
	Scripting.destruct()
}

typealias ImportAction = (ScriptImport) -> Unit
typealias ExportAction = (ScriptExport, MutableMap<String, (Array<out Any?>) -> Any?>, MutableMap<String, (Array<out Any?>) -> Any?>, MutableMap<String, (Array<out Any?>) -> Any?>) -> Unit
