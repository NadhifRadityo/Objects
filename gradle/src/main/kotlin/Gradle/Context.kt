package Gradle

import org.gradle.api.Project

open class Context(
	private val _that: Any,
	private val _project: Project?
) {
	val that: Any
		get() = _that
	val project: Project?
		get() = _project
	val project0: Project
		get() = _project!!

	operator fun component1(): Any {
		return that
	}
	operator fun component2(): Project? {
		return project
	}
	operator fun component3(): Project {
		return project!!
	}
}
