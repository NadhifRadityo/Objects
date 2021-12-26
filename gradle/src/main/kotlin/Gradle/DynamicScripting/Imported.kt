package Gradle.DynamicScripting

import Gradle.DynamicScripting.Scripting.scripts
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyManipulation

open class Imported(
	@ExportGradle val scriptId: String,
	@ExportGradle val importId: String
): GroovyManipulation.DummyGroovyObject() {
	var cache: GroovyKotlinCache<*>? = null
		internal set
	@ExportGradle val scriptImport: ScriptImport
		get() { return scripts[scriptId]!!.imports.first { it.id == importId } }

	// https://kotlinlang.org/docs/destructuring-declarations.html
	override fun getProperty(property: String): Any? {
		val componentIndex = if(property.startsWith("component")) property.substring("component".length).toIntOrNull() else null
		return super.getProperty(if(componentIndex == null) property else scriptImport.what?.getOrNull(componentIndex - 1) ?: property)
	}
	override fun setProperty(property: String, value: Any?) {
		val componentIndex = if(property.startsWith("component")) property.substring("component".length).toIntOrNull() else null
		super.setProperty(if(componentIndex == null) property else scriptImport.what?.getOrNull(componentIndex - 1) ?: property, value)
	}
	@ExportGradle
	operator fun get(property: String): Any? {
		return getProperty(property)
	}
	@ExportGradle
	operator fun set(property: String, value: Any?) {
		setProperty(property, value)
	}
}
