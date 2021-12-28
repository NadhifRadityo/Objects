package Gradle

import Gradle.Strategies.FileUtils.mkfile
import Gradle.Strategies.FileUtils.writeFileString
import Gradle.Strategies.LoggerUtils.lwarn
import Gradle.Strategies.StringUtils.randomString
import org.gradle.internal.impldep.org.junit.Rule
import org.gradle.internal.impldep.org.junit.rules.TestName
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class DynamicScriptingTest: BaseTest() {
	@Rule var testName = TestName()
	@TempDir lateinit var tempProjectDir: File

	fun newScript(rootProject: RootProject, source: String, import: (String, File) -> String): File {
		val scriptName = randomString(10)
		val script = mkfile(rootProject.directory, "${scriptName}.gradle")
		writeFileString(script, """
			context(this) {
				scriptApply()
			}
${source.split("\n").map { "\t\t\t$it" }.joinToString("\n")}
		""".trimIndent())
		rootProject.appendBuildSource += """
${import(scriptName, script).split("\n").map { "\t\t\t$it" }.joinToString("\n")}
		""".trimIndent()
		return script
	}
	fun manualDestruct() {
		lwarn("Manual destructing on ${testName.methodName}")
		val rootProject = RootProject(tempProjectDir)
		rootProject.configure {
			// Force rewrite file, because the implementation on `ROOTPROJECT_BUILD` forced on calling `Common.construct`
			// But, we don't want that, because it's going to throw an exception about `Constructor must be called once`
			// And we want to destruct it only. Write on this callback because the file already written, and we can override it.
			val originalSource = rootProject.buildSource
			val buildFile = rootProject.buildFile
			val modifiedSource = originalSource.replaceFirst("Common.construct()", "Common.destruct()")
			writeFileString(buildFile, modifiedSource)
		}
		rootProject.run()
	}

	@Test
	fun `import script ()`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			llog '${randomString}'
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport from('${file.name}')
			}
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
	}

	@Test
	fun `import script () and not exposing exports`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport from('${file.name}')
			}
			llog testVariable
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		assertTrue(!result.output.contains(randomString))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable'"))
	}

	@Test
	fun `import script (being)`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport from('${file.name}'), being('testScript')
			}
			llog testScript.testVariable
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
	}

	@Test
	fun `import script (being) and not exposing exports`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport from('${file.name}'), being('testScript')
			}
			llog testVariable
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		assertTrue(!result.output.contains(randomString))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable'"))
	}

	@Test
	fun `import script (listOf)`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable'), from('${file.name}')
			}
			llog testVariable
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
	}

	@Test
	fun `import script (listOf) and non existent export`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('NON_EXISTENT_EXPORT'), from('${file.name}')
			}
			llog testVariable
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		// java.lang.IllegalArgumentException: There's no such export 'NON_EXISTENT_EXPORT'
		assertTrue(result.output.contains("NON_EXISTENT_EXPORT"))
		assertFalse(result.output.contains(randomString))
	}

	@Test
	fun `import script (listOf) and not exposing exports`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		val randomString2 = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			def test2 = '${randomString2}'
			scriptExport test, being('testVariable')
			scriptExport test2, being('testVariable2')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable'), from('${file.name}')
			}
			llog testVariable
			llog testVariable2
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		assertTrue(result.output.contains(randomString))
		assertFalse(result.output.contains(randomString2))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable2'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable2'"))
	}

	@Test
	fun `import script (listOf, being)`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable'), from('${file.name}'), being('testScript')
			}
			llog testScript.testVariable
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
	}

	@Test
	fun `import script (listOf, being) and not exposing exports`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		val randomString2 = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			def test2 = '${randomString2}'
			scriptExport test, being('testVariable')
			scriptExport test2, being('testVariable2')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable'), from('${file.name}'), being('testScript')
			}
			llog testScript.testVariable
			llog testScript.testVariable2
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		assertTrue(result.output.contains(randomString))
		assertFalse(result.output.contains(randomString2))
		// groovy.lang.MissingPropertyException: No such property: testVariable2
		assertTrue(result.output.contains("No such property: testVariable2"))
	}

	@Test
	fun `import script (with={includeFlags={expose_exports}})`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport from('${file.name}'), with(includeFlags('expose_exports'))
			}
			llog testVariable
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
	}

	@Test
	fun `import script (being, with={includeFlags={expose_exports}})`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		val randomString2 = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			def test2 = '${randomString2}'
			scriptExport test, being('testVariable')
			scriptExport test2, being('testVariable2')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport from('${file.name}'), being('testScript'), with(includeFlags('expose_exports'))
			}
			llog testScript.testVariable
			llog testVariable2
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
		assertTrue(result.output.contains(randomString2))
	}

	@Test
	fun `import script (listOf, with={includeFlags={expose_exports}})`() {
		// Flag `expose_exports` is redundant, listOf automatically exposes variables
		// Exposed variables `expose_exports` also corresponds to `listOf`
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			scriptExport test, being('testVariable')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable'), from('${file.name}'), with(includeFlags('expose_exports'))
			}
			llog testVariable
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
	}

	@Test
	fun `import script (listOf, with={includeFlags={expose_exports}}) and not exposing exports`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		val randomString2 = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			def test2 = '${randomString2}'
			scriptExport test, being('testVariable')
			scriptExport test2, being('testVariable2')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable'), from('${file.name}'), with(includeFlags('expose_exports'))
			}
			llog testVariable
			llog testVariable2
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		assertTrue(result.output.contains(randomString))
		assertFalse(result.output.contains(randomString2))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable2'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable2'"))
	}

	@Test
	fun `import script (listOf, being, with={includeFlags={expose_exports}})`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		val randomString2 = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			def test2 = '${randomString2}'
			scriptExport test, being('testVariable')
			scriptExport test2, being('testVariable2')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable', 'testVariable2'), from('${file.name}'), being('testScript'), with(includeFlags('expose_exports'))
			}
			llog testScript.testVariable
			llog testVariable2
		""".trimIndent() }
		val result = rootProject.run()
		assertTrue(result.output.contains(randomString))
		assertTrue(result.output.contains(randomString2))
	}

	@Test
	fun `import script (listOf, being, with={includeFlags={expose_exports}}) and not exposing exports`() {
		val rootProject = RootProject(tempProjectDir)
		val randomString = randomString(50)
		val randomString2 = randomString(50)
		val randomString3 = randomString(50)
		newScript(rootProject, """
			def test = '${randomString}'
			def test2 = '${randomString2}'
			def test3 = '${randomString3}'
			scriptExport test, being('testVariable')
			scriptExport test2, being('testVariable2')
			scriptExport test3, being('testVariable3')
		""".trimIndent()) { _, file -> """
			context(this) {
				scriptImport listOf('testVariable', 'testVariable2'), from('${file.name}'), being('testScript'), with(includeFlags('expose_exports'))
			}
			llog testScript.testVariable
			llog testVariable2
			llog testVariable3
		""".trimIndent() }
		val result = rootProject.run(false)
		// TODO: Manual call destructuring, see Bugs#2
		manualDestruct()
		assertTrue(result.output.contains(randomString))
		assertTrue(result.output.contains(randomString2))
		assertFalse(result.output.contains(randomString3))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable3'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable3'"))
	}
}
