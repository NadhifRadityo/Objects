package io.github.NadhifRadityo.Objects.Library

import Gradle.Strategies.FileUtils.fileCopy
import Gradle.Strategies.FileUtils.fileCopyDir
import Gradle.Strategies.StringUtils.randomString
import Gradle.includeBuild
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

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
			fileCopyDir(File(LIBRARY_BUILD_PATH, "../.."), ".", "common.gradle")
			"libraries" / {
				fileCopyDir(LIBRARY_BUILD_PATH, "__library__", "library.gradle", "libraryLoad.gradle", "libraryModules.gradle", "libraryUtilsGenerate.gradle")
			}
		}
		val project = withRootProject(builds=listOf(objectsProject)) {
			configure {
				includeBuild(objectsProject)
			}
			"library" / {
				withLibraryProject {
					withDefaultBuildSource()
					withBuildSource {
						+"llog '${randomVariable2}'"
					}
				}
			}
			withDefaultSettingsSource()
			withDefaultBuildSource()
		}
		val result = run(project)
		Assertions.assertTrue(result.output.contains(randomVariable))
		Assertions.assertTrue(result.output.contains(randomVariable2))
	}
}
