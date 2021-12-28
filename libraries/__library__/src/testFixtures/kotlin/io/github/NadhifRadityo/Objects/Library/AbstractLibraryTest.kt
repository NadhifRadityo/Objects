package io.github.NadhifRadityo.Objects.Library

import Gradle.*
import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.FileUtils.fileCopyDir
import Gradle.Strategies.FileUtils.fileExists
import Gradle.Strategies.StringUtils
import java.nio.file.StandardCopyOption

val LIBRARY_BUILD_PATH = file(System.getProperty("libraryutils.project_path", System.getProperty("user.dir")))
var LIBRARY_BUILD_PATH_VALIDATED: Boolean = false
val `$` = '$'

fun validate_library_build_path() {
	if(LIBRARY_BUILD_PATH_VALIDATED) return
	if(!LIBRARY_BUILD_PATH.exists())
		throw Error("Library build path does not exists! Try reconfigure property -Dlibraryutils.project_path=\"...\"")
	val buildFiles = fileExists(GRADLE_BUILD_PATH, "build.gradle", "build.gradle.kts")
	if(buildFiles.size != 1)
		throw Error("Library build path is not a valid project! The directory must contains exactly one \"build.gradle(.kts)\"")
	val scripts = fileExists(GRADLE_BUILD_PATH, "library.gradle", "libraryLoad.gradle", "libraryModules.gradle", "libraryUtilsGenerate.gradle")
	if(scripts.size != 4)
		throw Error("Library build path is not a valid project! The directory must contains \"library.gradle\", \"libraryLoad.gradle\", \"libraryModules.gradle\", \"libraryUtilsGenerate.gradle\"")
	LIBRARY_BUILD_PATH_VALIDATED = true
}

fun LIBRARYPROJECT_BUILD(name: String, asCurrent: Boolean): String {
	validate_library_build_path()
	return """
		plugins {
			id 'java'
			id 'org.jetbrains.kotlin.jvm' version '1.5.30'
		}
		
		buildDir = '../__target__/__sources__/'
		
		context(this) {
			scriptImport from('${if(asCurrent) "Objects:libraries/__library__/" else ""}libraryLoad.gradle')
			scriptImport listOf('module'), from('${if(asCurrent) "Objects:libraries/__library__/" else ""}libraryModules.gradle'), being('libraryModules')
			scriptImport from('${if(asCurrent) "Objects:libraries/__library__/" else ""}library.gradle')
		}
	"""
}

open class AbstractLibraryTest: AbstractProjectTest() {

	fun ProjectFileDSL.withLibraryProject(name: String = "LibraryProject_${StringUtils.randomString()}", callback: LibraryProject.() -> Unit): LibraryProject {
		var parent: Project? = project
		while(parent != null && parent !is RootProject)
			parent = parent.parent
		if(parent == null || parent !is RootProject)
			throw IllegalStateException("Cannot find parent project!")
		val projectDir = file(root, "/${name}")
		val project = LibraryProject(parent, projectDir, name)
		parent.builds.firstOrNull() { it.name == "Objects" }
			?: throw IllegalStateException("Must include build 'Objects'!")
		if(root == parent.directory)
			throw IllegalStateException("Library project must be in a folder (not in project root!)")
		parent.children += project
		val oldCurrentProject = currentProject
		try {
			currentProject = project
			project.callback()
			return project
		} finally {
			currentProject = oldCurrentProject
		}
	}

	fun LibraryProject.withDefaultBuildSource(asCurrent: Boolean = false) {
		withBuildSource {
			-LIBRARYPROJECT_BUILD(name, asCurrent)
			if(asCurrent)
				fileCopyDir(LIBRARY_BUILD_PATH, directory, arrayOf("/src/main", "library.gradle",
					"libraryModules.gradle", "libraryUtilsGenerate.gradle"), StandardCopyOption.REPLACE_EXISTING)
		}
	}
}
