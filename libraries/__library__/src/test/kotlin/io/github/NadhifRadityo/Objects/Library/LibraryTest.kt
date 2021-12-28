package io.github.NadhifRadityo.Objects.Library

import Gradle.Strategies.StringUtils.randomString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LibraryTest: AbstractLibraryTest() {

	@Test
	fun `basic library`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val objectsProject = withRootProject("Objects") {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			withBuildSource {
				+"llog '${randomVariable}'"
			}
		}
		val project = withRootProject(builds=listOf(objectsProject)) {
			withDefaultSettingsSource()
			withDefaultBuildSource()
			"library" / {
				withLibraryProject {
					withDefaultBuildSource()
					withBuildSource {
						+"llog '${randomVariable2}'"
					}
				}
			}
		}
		val result = run(project, false)
		Assertions.assertTrue(result.output.contains(randomVariable))
		Assertions.assertTrue(result.output.contains(randomVariable2))
	}
}
