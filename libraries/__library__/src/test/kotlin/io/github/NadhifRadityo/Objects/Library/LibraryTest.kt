package io.github.NadhifRadityo.Objects.Library

import Gradle.Strategies.StringUtils.randomString
import org.junit.jupiter.api.Test

class LibraryTest: AbstractLibraryTest() {

	@Test
	fun `basic library`() {
		val randomVariable = randomString(50)
		val randomVariable2 = randomString(50)
		val objectBuild = withRootProject("Objects") {
			withDefaultSettingsSource()
			withDefaultBuildSource()
		}
		withRootProject(builds=listOf(objectBuild)) {
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
	}
}
