package Gradle

import Gradle.Common.constructNoContext
import Gradle.Common.destructNoContext
import Gradle.Strategies.FileUtils
import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.FileUtils.fileRelative
import Gradle.Strategies.FileUtils.mkdir
import Gradle.Strategies.LoggerUtils
import Gradle.Strategies.LoggerUtils.lwarn
import Gradle.Strategies.StringUtils
import Gradle.Strategies.StringUtils.randomString
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.UnexpectedBuildFailure
import org.gradle.testkit.runner.UnexpectedBuildSuccess
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.io.TempDir
import java.io.File

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
	val buildFileContent = FileUtils.fileString(buildFile)
	if(!buildFileContent.contains("group = \"io.github.NadhifRadityo.Objects\""))
		throw Error("Gradle build path is not a valid expected project!")
	GRADLE_BUILD_PATH_VALIDATED = true
}
fun ROOTPROJECT_SETTINGS(name: String, directory: File, rootBuild: Boolean, children: List<ProjectInterface>): String {
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
			def gradleBuildPath = "${StringUtils.escape(GRADLE_BUILD_PATH.canonicalPath)}"
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
			rootProject.name = '${name}'
			${children.map { "extend(${fileRelative(directory, it.directory).path}, '${it.name}')" }.joinToString("\n")}
			apply(this)
		}
	"""
	else """
		def original_export_functions_only = ext.has('export_functions_only') ? ext['export_functions_only'] : null
		ext.set('export_functions_only', true)
		apply from: relativePath(File(startParameter.includedBuilds.get(0), 'settings.gradle'))
		ext.set('export_functions_only', original_export_functions_only)
		
		def extend = ext.get('extend')
		def applyScript = ext.get('apply')
		if(!ext.has('export_functions_only') || ext['export_functions_only'] == null || !(ext['export_functions_only'] as Boolean)) {
			rootProject.name = '${name}'
			${children.map { "extend(${fileRelative(directory, it.directory).path}, '${it.name}')" }.joinToString("\n")}
			applyScript(this)
		}
	"""
}
fun ROOTPROJECT_BUILD(name: String): String {
	return """
		import Gradle.Common
		
		plugins {
			id 'java'
		}
		
		Common.context(this) {
			Common.construct()
		}
	"""
}

typealias FileDSLExpression<T> = FileDSL.() -> T

fun <T> File.withDSL(expression: FileDSLExpression<T>): T =
	FileDSL(this).expression()

open class FileDSL(val root: File) {
	operator fun String.not(): File {
		return if(!this.startsWith("/")) mkfile(this)
			else mkdir(this)
	}
	operator fun <T> String.times(expression: FileDSLExpression<T>): T {
		return (!this).withDSL(expression)
	}
	// Technically same as `"/testDir" * `
	operator fun <T> String.div(expression: FileDSLExpression<T>): T {
		return (!("/$this")).withDSL(expression)
	}

	fun file(vararg name: String): File {
		return file(root, *name)
	}
	fun fileRelative(file: File): File {
		return fileRelative(root, file)
	}
	fun mkfile(vararg name: String): File {
		return FileUtils.mkfile(file(*name))
	}
	fun mkdir(vararg name: String): File {
		return FileUtils.mkdir(file(*name))
	}
	fun delfile(vararg name: String): File {
		return FileUtils.delfile(file(*name))
	}
	fun readBytes(vararg name: String): ByteArray {
		return FileUtils.fileBytes(file(*name))
	}
	fun readString(vararg name: String): String {
		return FileUtils.fileString(file(*name))
	}
	fun writeBytes(content: ByteArray) {
		FileUtils.writeFileBytes(file(), content)
	}
	fun writeString(content: String) {
		FileUtils.writeFileString(file(), content)
	}

	// "test.txt" * { -"replace content" }
	operator fun String.unaryMinus() {
		writeString(this)
	}
	// "test.txt" * { +"append content" }
	operator fun String.unaryPlus() {
		val old = readString()
		writeString(old + this)
	}
	// "test.txt" * { -"replace content".encodeToByteArray() }
	operator fun ByteArray.unaryMinus() {
		writeBytes(this)
	}
	// "test.txt" * { +"append content".encodeToByteArray() }
	operator fun ByteArray.unaryPlus() {
		val old = readBytes()
		writeBytes(old + this)
	}
	// "test.txt" -= "replace content"
	operator fun String.minusAssign(content: String) {
		(!this).withDSL { -content }
	}
	// "test.txt" += "append content"
	operator fun String.plusAssign(content: String) {
		(!this).withDSL { +content }
	}
	// "test.txt" % "replace content".encodeToByteArray()
	operator fun String.rem(content: ByteArray) {
		(!this).withDSL { -content }
	}
	// "test.txt" .. "append content".encodeToByteArray()
	operator fun String.rangeTo(content: ByteArray) {
		(!this).withDSL { +content }
	}
}

typealias ScriptSourceDSLExpression = ScriptSourceDSL.() -> Unit
class ScriptSourceDSL(file: File): FileDSL(file) { }

open class BaseTest {
	@TempDir
	lateinit var testDir: File
	private var currentProject: ProjectInterface? = null
	val currentProjectDir: File
		get() = currentProject?.directory ?: testDir

	fun withRootProject(name: String = "TestRootProject_${randomString()}", callback: RootProject.() -> Unit): RootProject {
		val rootProjectDir = file(testDir, "/${name}")
		val rootProject = RootProject(rootProjectDir, name)
		val oldCurrentProject = currentProject
		try {
			currentProject = rootProject
			rootProject.callback()
			return rootProject
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun ProjectInterface.withProject(name: String = "TestProject_${randomString()}", callback: ChildProject.() -> Unit): ChildProject {
		val projectDir = file(directory, "/${name}")
		val project = ChildProject(this, projectDir, name)
		val oldCurrentProject = currentProject
		try {
			currentProject = project
			project.callback()
			return project
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun RootProjectInterface.withDefaultSettingsSource() {
		withSettingsSource {
			-ROOTPROJECT_SETTINGS(name, directory, builds.isEmpty(), children)
		}
	}
	fun RootProjectInterface.withDefaultBuildSource() {
		withBuildSource {
			-ROOTPROJECT_BUILD(name)
		}
	}
	fun <T> ProjectInterface.withFileDSL(expression: FileDSLExpression<T>): T {
		return mkdir(directory).withDSL(expression)
	}

	fun ProjectInterface.newScript(name: String = randomString(10), expression: ScriptSourceDSLExpression): File {
		return withFileDSL {
			ScriptSourceDSL(!name).apply {
				-"""
					context(this) {
						scriptApply()
					}
				"""
				expression()
			}
			!name
		}
	}

	fun run(rootProject: RootProjectInterface, expectSucceed: Boolean = true): BuildResult {
		var throwable: Throwable? = null
		var result: BuildResult? = null
		try {
			result = rootProject.run(expectSucceed)
		} catch(e: Throwable) {
			when(e) {
				is UnexpectedBuildFailure -> throwable = e
				is UnexpectedBuildSuccess -> throwable = e
				else -> throw e
			}
		} finally {
			try {
				// TODO: Manual destruct call, see Bugs#2
				if(expectSucceed && throwable != null)
					lwarn("Was expecting to not fail but failed, will manually call destruct")
				if(!expectSucceed || throwable != null)
					manualDestruct()
			} catch(e2: Throwable) {
				if(throwable == null)
					throwable = e2
				else {
					e2.addSuppressed(throwable)
					throwable = e2
				}
			}
			if(throwable != null)
				throw throwable
		}
		return result!!
	}
	fun manualDestruct() {
		LoggerUtils.lwarn("Manual destructing")
		withRootProject {
			withDefaultSettingsSource()
			withBuildSource {
				-"""
				import Gradle.Common
				
				plugins {
					id 'java'
				}
				
				Common.context(this) {
					Common.destruct()
				}
				"""
			}
			run()
		}
	}

	companion object {
		@BeforeAll @JvmStatic
		fun beforeAll() {
			constructNoContext()
		}

		@AfterAll @JvmStatic
		fun afterAll() {
			destructNoContext()
		}
	}
}
