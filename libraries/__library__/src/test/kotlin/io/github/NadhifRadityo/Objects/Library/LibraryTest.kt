package io.github.NadhifRadityo.Objects.Library

import Gradle.AbstractGradleTest
import org.junit.jupiter.api.Test

class LibraryTest: AbstractGradleTest() {

	@Test
	fun `basic library`() {
		withRootProject {
			"library" / {
				withProject {
					withBuildSource {
						+"""
							plugins {
								id 'java'
								id 'org.jetbrains.kotlin.jvm' version '1.5.30'
							}

							buildDir = '../__target__/__sources__/'

							context(this) {
								scriptImport from('Objects:libraries/__library__/libraryLoad.gradle')
								scriptImport listOf('module'), from('Objects:libraries/__library__/libraryModules.gradle'), being('libraryModules')

								libraryModules.module('Computing - LWJGL')

								scriptImport from('Objects:libraries/__library__/library.gradle')
							}
						"""
					}
				}
			}
		}
	}
}
