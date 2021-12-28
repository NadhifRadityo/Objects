package Gradle

import Gradle.Common.constructNoContext
import Gradle.Common.destructNoContext
import Gradle.Strategies.FileUtils
import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.FileUtils.fileExists
import Gradle.Strategies.FileUtils.fileRelative
import Gradle.Strategies.LoggerUtils
import Gradle.Strategies.LoggerUtils.lwarn
import Gradle.Strategies.RuntimeUtils.env_getFile
import Gradle.Strategies.StringUtils
import Gradle.Strategies.StringUtils.randomString
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.UnexpectedBuildFailure
import org.gradle.testkit.runner.UnexpectedBuildSuccess
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.io.TempDir
import java.io.File

val GRADLE_BUILD_PATH: File = run {
	val buildPath = env_getFile("GRADLEUTILS_PROJECTPATH")
	if(buildPath == null || !buildPath.exists())
		throw Error("Gradle build path does not exists! Try reconfigure gradle environment variable set/export GRADLEUTILS_PROJECTPATH=\"...\"")
	val buildFiles = fileExists(buildPath, "build.gradle", "build.gradle.kts")
	if(buildFiles.size != 1)
		throw Error("Gradle build path is not a valid project! The directory must contains exactly one \"build.gradle(.kts)\"")
	val buildFile = buildFiles.first()
	val buildFileContent = FileUtils.fileString(buildFile)
	if(!buildFileContent.contains("group = \"io.github.NadhifRadityo.Objects\""))
		throw Error("Gradle build path is not a valid expected project!")
	buildPath
}
val `$` = '$'

fun ROOTPROJECT_SETTINGS(name: String, directory: File, rootBuild: Boolean, children: List<Project>): String {
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

typealias FileDSLExpression<RECEIVER, RESULT> = RECEIVER.() -> RESULT
typealias FileDSLGenerator<RECEIVER> = (File, FileDSL<RECEIVER>) -> RECEIVER
open class FileDSL<RECEIVER: FileDSL<RECEIVER>>(val generator: FileDSLGenerator<RECEIVER>, val root: File) {
	internal val _instance: RECEIVER = this as RECEIVER
	open operator fun String.not(): File {
		return if(!this.startsWith("/")) mkfile(this)
			else mkdir(this)
	}
	open operator fun <RESULT> String.times(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT {
		return generator(!this, _instance).expression()
	}
	// Technically same as `"/testDir" * `
	open operator fun <RESULT> String.div(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT {
		return generator(!"/$this", _instance).expression()
	}

	open fun <RESULT> files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT =
		generator(root, _instance).expression()
	open fun <RESULT> File.files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT =
		generator(this, _instance).expression()

	open fun file(vararg name: String): File {
		return file(root, *name)
	}
	open fun fileRelative(file: File): File {
		return fileRelative(root, file)
	}
	open fun mkfile(vararg name: String): File {
		return FileUtils.mkfile(file(*name))
	}
	open fun mkdir(vararg name: String): File {
		return FileUtils.mkdir(file(*name))
	}
	open fun delfile(vararg name: String): File {
		return FileUtils.delfile(file(*name))
	}
	open fun readBytes(vararg name: String): ByteArray {
		return FileUtils.fileBytes(file(*name))
	}
	open fun readString(vararg name: String): String {
		return FileUtils.fileString(file(*name))
	}
	open fun writeBytes(content: ByteArray) {
		FileUtils.writeFileBytes(file(), content)
	}
	open fun writeString(content: String) {
		FileUtils.writeFileString(file(), content)
	}

	// "test.txt" * { -"replace content" }
	open operator fun String.unaryMinus() {
		writeString(this)
	}
	// "test.txt" * { +"append content" }
	open operator fun String.unaryPlus() {
		val old = readString()
		writeString(old + this)
	}
	// "test.txt" * { -"replace content".encodeToByteArray() }
	open operator fun ByteArray.unaryMinus() {
		writeBytes(this)
	}
	// "test.txt" * { +"append content".encodeToByteArray() }
	open operator fun ByteArray.unaryPlus() {
		val old = readBytes()
		writeBytes(old + this)
	}
	// "test.txt" -= "replace content"
	open operator fun String.minusAssign(content: String) {
		this * { -content }
	}
	// "test.txt" += "append content"
	open operator fun String.plusAssign(content: String) {
		this * { +content }
	}
	// "test.txt" % "replace content".encodeToByteArray()
	open operator fun String.rem(content: ByteArray) {
		this * { -content }
	}
	// "test.txt" .. "append content".encodeToByteArray()
	open operator fun String.rangeTo(content: ByteArray) {
		this * { +content }
	}
}

typealias DefaultFileDSLExpression<RESULT> = FileDSLExpression<DefaultFileDSL, RESULT>
typealias DefaultFileDSLGenerator = FileDSLGenerator<DefaultFileDSL>
fun DefaultFileDSLGenerator(): DefaultFileDSLGenerator { lateinit var generator: DefaultFileDSLGenerator;
	generator = { file, _ -> DefaultFileDSL(file, generator) }; return generator }
open class DefaultFileDSL(root: File, generator: DefaultFileDSLGenerator = DefaultFileDSLGenerator()): FileDSL<DefaultFileDSL>(generator, root) { }

fun <T> File.files(expression: DefaultFileDSLExpression<T>) {
	DefaultFileDSL(this).expression()
}

open class AbstractProjectTest {
	@TempDir
	lateinit var testDir: File
	protected var currentProject: Project? = null
	val currentProjectDir: File
		get() = currentProject?.directory ?: testDir

	fun withRootProject(name: String = "RootProject_${randomString()}", builds: List<RootProject> = listOf(), callback: RootProject.() -> Unit): RootProject {
		val rootProjectDir = file(testDir, "/${name}")
		val rootProject = RootProject(rootProjectDir, name, builds)
		val oldCurrentProject = currentProject
		try {
			currentProject = rootProject
			rootProject.callback()
			return rootProject
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun ProjectFileDSL.withProject(name: String = "Project_${randomString()}", callback: Project.() -> Unit): Project {
		var parent: Project? = project
		while(parent != null && parent !is RootProject)
			parent = parent.parent
		if(parent == null || parent !is RootProject)
			throw IllegalStateException("Cannot find parent project!")
		val projectDir = file(root, "/${name}")
		val project = Project(parent, projectDir, name)
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
	fun RootProject.withDefaultSettingsSource() {
		withSettingsSource {
			-ROOTPROJECT_SETTINGS(name, directory, builds.isEmpty(), children)
		}
	}
	fun RootProject.withDefaultBuildSource() {
		withBuildSource {
			-ROOTPROJECT_BUILD(name)
		}
	}

	fun Project.newScript(name: String = randomString(10), expression: ProjectFileDSLExpression<Unit>): File {
		name * {
			-"""
				context(this) {
					scriptApply()
				}
			"""
			expression()
		}
		return !name
	}

	fun run(rootProject: RootProject, expectSucceed: Boolean = true): BuildResult {
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
					if(Common.currentSession() != null)				
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
