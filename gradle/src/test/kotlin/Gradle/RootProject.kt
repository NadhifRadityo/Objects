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

typealias GradleSourceDSLExpression = GradleSourceDSL.() -> Unit
class GradleSourceDSL(file: File): FileDSL(file) { }

enum class DSL(val extension: String) {
	GROOVY("gradle"),
	KOTLIN("gradle.kts")
}
interface ProjectInterface {
	val directory: File
	val name: String
	val buildFile: File

	fun withBuildSource(expression: GradleSourceDSLExpression)
	fun config(runner: GradleRunner)
}
interface RootProjectInterface: ProjectInterface {
	val builds: List<RootProject>
	val children: List<ChildProject>
	val settingsFile: File

	fun withSettingsSource(expression: GradleSourceDSLExpression)
	fun run(expectSucceed: Boolean = true): BuildResult
}
open class RootProject(
	override val directory: File,
	override val name: String,
	override val builds: List<RootProject> = listOf()
): RootProjectInterface {
	override val children: List<ChildProject> = mutableListOf()
	var settingsFileDSL: DSL = DSL.GROOVY
	var buildFileDSL: DSL = DSL.GROOVY
	override val settingsFile: File
		get() = file(directory, "settings.${settingsFileDSL.extension}")
	override val buildFile: File
		get() = file(directory, "build.${buildFileDSL.extension}")

	override fun withSettingsSource(expression: GradleSourceDSLExpression) {
		return GradleSourceDSL(mkfile(settingsFile)).expression()
	}
	override fun withBuildSource(expression: GradleSourceDSLExpression) {
		return GradleSourceDSL(mkfile(buildFile)).expression()
	}

	val configureCallbacks = mutableListOf<GradleRunner.(GradleRunner) -> Unit>()
	fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) {
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

	override fun run(expectSucceed: Boolean): BuildResult {
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
open class ChildProject(
	val parent: ProjectInterface,
	override val directory: File,
	override val name: String
): ProjectInterface {
	var buildFileDSL: DSL = DSL.GROOVY
	override val buildFile: File
		get() = file(directory, "build.${buildFileDSL.extension}")

	override fun withBuildSource(expression: GradleSourceDSLExpression) {
		return GradleSourceDSL(mkfile(buildFile)).expression()
	}

	val configureCallbacks = mutableListOf<GradleRunner.(GradleRunner) -> Unit>()
	fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) {
		this.configureCallbacks += configureCallbacks
	}

	override fun config(runner: GradleRunner) {
		for(configureCallback in configureCallbacks)
			runner.configureCallback(runner)
	}
}
