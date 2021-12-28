package Gradle

import Gradle.Strategies.StringUtils.randomString
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DynamicScriptingTest: AbstractGradleTest() {

	@Test
	fun `import script ()`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				llog '${randomVariable}'
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport from('${script.name}')
				}
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
	}

	@Test
	fun `import script () and not exposing exports`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport from('${script.name}')
				}
				llog testVariable
				"""
			}
		}
		val result = run(project, false)
		assertTrue(!result.output.contains(randomVariable))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable'"))
	}

	@Test
	fun `import script (being)`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport from('${script.name}'), being('testScript')
				}
				llog testScript.testVariable
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
	}

	@Test
	fun `import script (being) and not exposing exports`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport from('${script.name}'), being('testScript')
				}
				llog testVariable
				"""
			}
		}
		val result = run(project, false)
		assertTrue(!result.output.contains(randomVariable))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable'"))
	}

	@Test
	fun `import script (listOf)`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable'), from('${script.name}')
				}
				llog testVariable
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
	}

	@Test
	fun `import script (listOf) and non existent export`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('NON_EXISTENT_EXPORT'), from('${script.name}')
				}
				llog testVariable
				"""
			}
		}
		val result = run(project, false)
		// java.lang.IllegalArgumentException: There's no such export 'NON_EXISTENT_EXPORT'
		assertTrue(result.output.contains("NON_EXISTENT_EXPORT"))
		assertFalse(result.output.contains(randomVariable))
	}

	@Test
	fun `import script (listOf) and not exposing exports`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				def test2 = '${randomVariable2}'
				scriptExport test, being('testVariable')
				scriptExport test2, being('testVariable2')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable'), from('${script.name}')
				}
				llog testVariable
				llog testVariable2
				"""
			}
		}
		val result = run(project, false)
		assertTrue(result.output.contains(randomVariable))
		assertFalse(result.output.contains(randomVariable2))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable2'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable2'"))
	}

	@Test
	fun `import script (listOf, being)`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable'), from('${script.name}'), being('testScript')
				}
				llog testScript.testVariable
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
	}

	@Test
	fun `import script (listOf, being) and not exposing exports`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				def test2 = '${randomVariable2}'
				scriptExport test, being('testVariable')
				scriptExport test2, being('testVariable2')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable'), from('${script.name}'), being('testScript')
				}
				llog testScript.testVariable
				llog testScript.testVariable2
				"""
			}
		}
		val result = run(project, false)
		assertTrue(result.output.contains(randomVariable))
		assertFalse(result.output.contains(randomVariable2))
		// groovy.lang.MissingPropertyException: No such property: testVariable2
		assertTrue(result.output.contains("No such property: testVariable2"))
	}

	@Test
	fun `import script (with={includeFlags={expose_exports}})`() {
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport from('${script.name}'), with(includeFlags('expose_exports'))
				}
				llog testVariable
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
	}

	@Test
	fun `import script (being, with={includeFlags={expose_exports}})`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				def test2 = '${randomVariable2}'
				scriptExport test, being('testVariable')
				scriptExport test2, being('testVariable2')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport from('${script.name}'), being('testScript'), with(includeFlags('expose_exports'))
				}
				llog testScript.testVariable
				llog testVariable2
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
		assertTrue(result.output.contains(randomVariable2))
	}

	@Test
	fun `import script (listOf, with={includeFlags={expose_exports}})`() {
		// Flag `expose_exports` is redundant, listOf automatically exposes variables
		// Exposed variables `expose_exports` also corresponds to `listOf`
		val randomVariable = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				scriptExport test, being('testVariable')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable'), from('${script.name}'), with(includeFlags('expose_exports'))
				}
				llog testVariable
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
	}

	@Test
	fun `import script (listOf, with={includeFlags={expose_exports}}) and not exposing exports`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				def test2 = '${randomVariable2}'
				scriptExport test, being('testVariable')
				scriptExport test2, being('testVariable2')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable'), from('${script.name}'), with(includeFlags('expose_exports'))
				}
				llog testVariable
				llog testVariable2
				"""
			}
		}
		val result = run(project, false)
		assertTrue(result.output.contains(randomVariable))
		assertFalse(result.output.contains(randomVariable2))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable2'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable2'"))
	}

	@Test
	fun `import script (listOf, being, with={includeFlags={expose_exports}})`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				def test2 = '${randomVariable2}'
				scriptExport test, being('testVariable')
				scriptExport test2, being('testVariable2')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable', 'testVariable2'), from('${script.name}'), being('testScript'), with(includeFlags('expose_exports'))
				}
				llog testScript.testVariable
				llog testVariable2
				"""
			}
		}
		val result = run(project)
		assertTrue(result.output.contains(randomVariable))
		assertTrue(result.output.contains(randomVariable2))
	}

	@Test
	fun `import script (listOf, being, with={includeFlags={expose_exports}}) and not exposing exports`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val randomVariable3 = randomString(50)
		val project = withRootProject {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			val script = newScript {
				+"""
				def test = '${randomVariable}'
				def test2 = '${randomVariable2}'
				def test3 = '${randomVariable3}'
				scriptExport test, being('testVariable')
				scriptExport test2, being('testVariable2')
				scriptExport test3, being('testVariable3')
				"""
			}
			withBuildSource {
				+"""
				context(this) {
					scriptImport listOf('testVariable', 'testVariable2'), from('${script.name}'), being('testScript'), with(includeFlags('expose_exports'))
				}
				llog testScript.testVariable
				llog testVariable2
				llog testVariable3
				"""
			}
		}
		val result = run(project, false)
		assertTrue(result.output.contains(randomVariable))
		assertTrue(result.output.contains(randomVariable2))
		assertFalse(result.output.contains(randomVariable3))
		// groovy.lang.MissingPropertyException: Could not get unknown property 'testVariable3'
		assertTrue(result.output.contains("Could not get unknown property 'testVariable3'"))
	}
}
