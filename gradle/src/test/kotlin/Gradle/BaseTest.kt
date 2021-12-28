package Gradle

import Gradle.Common.constructNoContext
import Gradle.Common.destructNoContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

open class BaseTest {
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
