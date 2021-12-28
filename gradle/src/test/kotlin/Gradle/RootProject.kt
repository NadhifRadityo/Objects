package Gradle

import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.FileUtils.fileRelative
import Gradle.Strategies.FileUtils.fileString
import Gradle.Strategies.FileUtils.writeFileString
import Gradle.Strategies.LoggerUtils.loggerAppend
import Gradle.Strategies.StringUtils.escape
import Gradle.Strategies.StringUtils.randomString
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File
import java.io.Writer

val GRADLE_BUILD_PATH = file(System.getProperty("gradleutils.project_path", System.getProperty("user.dir")))
var GRADLE_BUILD_PATH_VALIDATED: Boolean = false
val `$` = '$'

fun validate_gradle_build_path() {
	if(GRADLE_BUILD_PATH_VALIDATED) return
	if(!GRADLE_BUILD_PATH.exists())
		throw Error("Gradle build path does not exists! Try reconfigure property -Dgradleutils.project_path=\"...\"")
	val filtered = GRADLE_BUILD_PATH.listFiles()!!.filter { it.isFile && (it.name == "build.gradle" || it.name == "build.gradle.kts") }
	if(filtered.size != 1)
		throw Error("Gradle build path is not a valid project! The directory must contains exactly one \"build.gradle(.kts)\"")
	val buildFile = filtered.first()
	val buildFileContent = fileString(buildFile)
	if(!buildFileContent.contains("group = \"io.github.NadhifRadityo.Objects\""))
		throw Error("Gradle build path is not a valid expected project!")
	GRADLE_BUILD_PATH_VALIDATED = true
}
fun ROOTPROJECT_SETTINGS(id: String, directory: File, rootBuild: Boolean, children: List<ProjectInterface>, appendSource: String): String {
	validate_gradle_build_path()
	return if(rootBuild) """
import java.nio.file.Paths

class GLOBAL {
	protected static final Map<String, Boolean> extendedProjects = new HashMap<>()
}
GLOBAL.extendedProjects.clear()

def extend = { String projectPath, String projectName = null ->
	int splitIndex = projectPath.indexOf(':')
	List<String> path = Arrays.asList((splitIndex != -1 ? projectPath.substring(0, splitIndex) : projectPath).split('/'))
	String name = projectName != null ? projectName : splitIndex != -1 ? projectPath.substring(splitIndex + 1) : String.join('${'$'}', path.stream()
			.map{it.substring(0, 1).toUpperCase() + it.substring(1)}.toArray(String[]::new))
	String id = "$`$`{String.join('#', path)}?$`$`name"
	GLOBAL.extendedProjects.put(id, false)
	include id
}
def apply = { that ->
	println "Configure for project '$`$`{rootProject.name}'"
	rootProject.children.each { project ->
		String projectName = project.name
		Boolean applied = GLOBAL.extendedProjects.get(projectName)
		if(applied == null || applied) return
		GLOBAL.extendedProjects.put(projectName, true)
		int splitIndex = projectName.indexOf('?')
		List<String> path = Arrays.asList(projectName.substring(0, splitIndex).split("#"))
		String name = project.name.substring(splitIndex + 1)
		project.projectDir = new File(String.join('/', path))
		project.name = name
		println "Imported $`$`{String.join('/', path)} -> $`$`name settings"
		assert project.projectDir.isDirectory()
		assert project.buildFile.isFile()
	}
	def gradleBuildPath = "${escape(GRADLE_BUILD_PATH.canonicalPath)}"
	includeBuild gradleBuildPath
}
def extended_projects = { ->
	return Collections.unmodifiableMap(GLOBAL.extendedProjects)
}

ext {
	set('extend', extend)
	set('apply', apply)
	set('extended_projects', extended_projects)
	gradle.ext.set('settings_extended_projects', extended_projects)
}

gradle.allprojects {
	it.buildscript {
		dependencies {
			classpath 'io.github.NadhifRadityo.Objects:gradle:LATEST'
		}
	}
}

if(ext.find('export_functions_only') == null || !ext.find('export_functions_only')) {
	rootProject.name = 'TestProject_${id}'
${children.map { "\textend(${fileRelative(directory, it.directory).path}, 'TestProject_${it.id}')" }.joinToString("\n")}
	apply(this)
}
$appendSource
	""".trimIndent()
	else """
def original_export_functions_only = ext.has('export_functions_only') ? ext['export_functions_only'] : null
ext.set('export_functions_only', true)
apply from: relativePath(File(startParameter.includedBuilds.get(0), 'settings.gradle'))
ext.set('export_functions_only', original_export_functions_only)

def extend = ext.get('extend')
def applyScript = ext.get('apply')
if(!ext.has('export_functions_only') || ext['export_functions_only'] == null || !(ext['export_functions_only'] as Boolean)) {
	rootProject.name = 'TestProject_${id}'
${children.map { "\textend(${fileRelative(directory, it.directory).path}, 'TestProject_${it.id}')" }.joinToString("\n")}
	applyScript(this)
}
$appendSource
	""".trimIndent()
}
fun ROOTPROJECT_BUILD(id: String, appendSource: String): String {
	return """
import Gradle.Common

plugins {
	id 'java'
}

Common.context(this) {
	Common.construct()
}

dependencies {

}
$appendSource
	""".trimIndent()
}
fun CHILDPROJECT_BUILD(id: String, appendSource: String): String {
	return """
${appendSource}
	""".trimIndent()
}
fun newOutputWriter(id: String): Writer {
	val id = "Project ${id}"
	return object: Writer() {
		val buffer = StringBuffer()
		override fun close() { }
		override fun flush() { }
		override fun write(cbuf: CharArray, off: Int, len: Int) {
			loggerAppend(id, cbuf.joinToString(""), off, len)
		}
	}
}
fun newErrorWriter(id: String): Writer {
	val id = "Project ${id}"
	return object: Writer() {
		val buffer = StringBuffer()
		override fun close() { }
		override fun flush() { }
		override fun write(cbuf: CharArray, off: Int, len: Int) {
			loggerAppend(id, cbuf.joinToString(""), off, len)
		}
	}
}

fun GradleRunner.appendArgs(vararg args: String) {
	val argsList = arguments.toMutableList()
	argsList += args
	withArguments(argsList)
}
fun GradleRunner.appendArgs(args: List<String>) {
	val argsList = arguments.toMutableList()
	argsList += args
	withArguments(argsList)
}

interface ProjectInterface {
	val directory: File
	val id: String
	val buildFile: File
	val appendBuildSource: String
	val buildSource: String

	fun config(runner: GradleRunner)
}
interface RootProjectInterface: ProjectInterface {
	val builds: List<RootProject>
	val children: List<ChildProject>
	val settingsFile: File
	val appendSettingsSource: String
	val settingsSource: String

	fun run(expectSucceed: Boolean = true): BuildResult
}
open class RootProject(
	override val directory: File,
	override val builds: List<RootProject> = listOf()
): RootProjectInterface {
	override val id = randomString()
	override val children: List<ChildProject> = mutableListOf()
	override val settingsFile: File
		get() = file(directory, "settings.gradle")
	override val buildFile: File
		get() = file(directory, "build.gradle")
	override var appendSettingsSource: String = ""
	override var appendBuildSource: String = ""
	override val settingsSource: String
		get() = ROOTPROJECT_SETTINGS(id, directory, builds.size == 0, children, appendSettingsSource)
	override val buildSource: String
		get() = ROOTPROJECT_BUILD(id, appendBuildSource)

	val configureCallbacks = mutableListOf<GradleRunner.(GradleRunner) -> Unit>()
	fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) {
		this.configureCallbacks += configureCallbacks
	}

	override fun config(runner: GradleRunner) {
		writeFileString(settingsFile, settingsSource)
		writeFileString(buildFile, buildSource)
		for(build in builds)
			build.config(runner)
		for(child in children)
			child.config(runner)
		for(configureCallback in configureCallbacks)
			runner.configureCallback(runner)
	}

	override fun run(expectSucceed: Boolean): BuildResult {
		val runner = GradleRunner.create()
		runner.withProjectDir(directory)
		runner.withPluginClasspath()
		runner.forwardStdOutput(newOutputWriter(id))
		runner.forwardStdError(newErrorWriter(id))
		runner.appendArgs("--stacktrace")
		config(runner)
		return if(expectSucceed) runner.build() else runner.buildAndFail()
	}
}
open class ChildProject(
	val parent: ProjectInterface,
	override val directory: File
): ProjectInterface {
	override val id = randomString()
	override val buildFile: File
		get() = file(directory, "build.gradle")
	override var appendBuildSource: String = ""
	override val buildSource: String
		get() = CHILDPROJECT_BUILD(id, appendBuildSource)

	val configureCallbacks = mutableListOf<GradleRunner.(GradleRunner) -> Unit>()
	fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) {
		this.configureCallbacks += configureCallbacks
	}

	override fun config(runner: GradleRunner) {
		writeFileString(buildFile, buildSource)
		for(configureCallback in configureCallbacks)
			runner.configureCallback(runner)
	}
}
