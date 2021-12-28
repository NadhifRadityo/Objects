package io.github.NadhifRadityo.Objects.Library

import Gradle.Project
import java.io.File

open class LibraryProject(
	parent: Project?,
	directory: File,
	name: String
): Project(parent, directory, name)
