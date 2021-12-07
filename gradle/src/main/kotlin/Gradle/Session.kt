package Gradle

import org.gradle.api.initialization.IncludedBuild

open class Session (
	val context: Context,
	val build: IncludedBuild?
)
