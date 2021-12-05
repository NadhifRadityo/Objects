plugins {
	id("java")
	kotlin("jvm") version "1.6.0"
}

group = "io.github.NadhifRadityo.Objects"
version = "LATEST"

repositories {
	mavenCentral()
}

dependencies {
	compileOnly(gradleApi())
	compileOnly(gradleKotlinDsl())
	compileOnly(localGroovy())
	testCompileOnly(gradleTestKit())

	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("com.google.code.gson:gson:2.8.9")
}
