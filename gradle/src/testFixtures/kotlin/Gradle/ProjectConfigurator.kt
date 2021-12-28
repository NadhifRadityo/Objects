package Gradle

import Gradle.Strategies.FileUtils.file
import Gradle.Strategies.FileUtils.mkfile
import Gradle.Strategies.LoggerUtils.loggerAppend
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File
import java.io.Writer

fun newOutputWriter(name: String): Writer {
	val logId = "Project ${name}"
	return object: Writer() {
		override fun close() { }
		override fun flush() { }
		override fun write(cbuf: CharArray, off: Int, len: Int) {
			loggerAppend(logId, cbuf.joinToString(""), off, len)
		}
	}
}
fun newErrorWriter(name: String): Writer {
	val logId = "Project ${name}"
	return object: Writer() {
		override fun close() { }
		override fun flush() { }
		override fun write(cbuf: CharArray, off: Int, len: Int) {
			loggerAppend(logId, cbuf.joinToString(""), off, len)
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

typealias ProjectFileDSLExpression<RESULT> = FileDSLExpression<ProjectFileDSL, RESULT>
typealias ProjectFileDSLGenerator = FileDSLGenerator<ProjectFileDSL>
fun ProjectFileDSLGenerator(): ProjectFileDSLGenerator { lateinit var generator: ProjectFileDSLGenerator;
	generator = { file, dsl -> ProjectFileDSL(dsl._instance.project, file, generator) }; return generator }
open class ProjectFileDSL(internal val _project: Project?, root: File, generator: ProjectFileDSLGenerator = ProjectFileDSLGenerator()): FileDSL<ProjectFileDSL>(generator, root) {
	val project: Project
		get() = _project ?: _instance as Project
}

enum class DSL(val extension: String) {
	GROOVY("gradle"),
	KOTLIN("gradle.kts")
}
open class Project(
	val parent: Project?,
	val directory: File,
	val name: String
): ProjectFileDSL(null, directory) {
	open var buildFileDSL: DSL = DSL.GROOVY
	open val buildFile: File
		get() = file(directory, "build.${buildFileDSL.extension}")

	open fun <RESULT> withBuildSource(expression: ProjectFileDSLExpression<RESULT>): RESULT {
		return mkfile(buildFile).files(expression)
	}

	open val configureCallbacks = mutableListOf<GradleRunner.(GradleRunner) -> Unit>()
	open fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) {
		this.configureCallbacks += configureCallbacks
	}

	open fun config(runner: GradleRunner) {
		for(configureCallback in configureCallbacks)
			runner.configureCallback(runner)
	}
}
open class RootProject(
	directory: File,
	name: String,
	val builds: List<RootProject> = listOf()
): Project(null, directory, name) {
	val children: MutableList<Project> = mutableListOf()
	open var settingsFileDSL: DSL = DSL.GROOVY
	open val settingsFile: File
		get() = file(directory, "settings.${settingsFileDSL.extension}")
	override var buildFileDSL: DSL = DSL.GROOVY
	override val buildFile: File
		get() = file(directory, "build.${buildFileDSL.extension}")

	open fun <RESULT> withSettingsSource(expression: ProjectFileDSLExpression<RESULT>): RESULT {
		return mkfile(settingsFile).files(expression)
	}
	override fun <RESULT> withBuildSource(expression: ProjectFileDSLExpression<RESULT>): RESULT {
		return mkfile(buildFile).files(expression)
	}

	override val configureCallbacks = mutableListOf<GradleRunner.(GradleRunner) -> Unit>()
	override fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) {
		this.configureCallbacks += configureCallbacks
	}

	override fun config(runner: GradleRunner) {
		for(build in builds)
			build.config(runner)
		for(child in children)
			child.config(runner)
		for(configureCallback in configureCallbacks)
			runner.configureCallback(runner)
	}

	open fun run(expectSucceed: Boolean = true): BuildResult {
		val runner = GradleRunner.create()
		runner.withProjectDir(directory)
		runner.withPluginClasspath()
		runner.forwardStdOutput(newOutputWriter(name))
		runner.forwardStdError(newErrorWriter(name))
		runner.appendArgs("--stacktrace")
		config(runner)
		return if(expectSucceed) runner.build()
			else runner.buildAndFail()
	}
}
