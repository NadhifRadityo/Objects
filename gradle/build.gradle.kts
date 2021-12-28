plugins {
	id("java")
	id("java-gradle-plugin")
	id("java-test-fixtures")
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
	testCompileOnly("org.junit.jupiter:junit-jupiter:5.8.2")
	testFixturesCompileOnly(gradleTestKit())
	testFixturesCompileOnly("org.junit.jupiter:junit-jupiter:5.8.2")

	testRuntimeOnly(gradleApi())
	testRuntimeOnly(gradleKotlinDsl())
	testRuntimeOnly(localGroovy())
	testRuntimeOnly(gradleTestKit())
	testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.8.2")
	testFixturesRuntimeOnly(gradleApi())
	testFixturesRuntimeOnly(gradleKotlinDsl())
	testFixturesRuntimeOnly(localGroovy())
	testFixturesRuntimeOnly(gradleTestKit())
	testFixturesRuntimeOnly("org.junit.jupiter:junit-jupiter:5.8.2")

	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("com.google.code.gson:gson:2.8.9")
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		events("STARTED", "PASSED", "SKIPPED", "FAILED")
	}
}

gradlePlugin {
	testSourceSets(sourceSets.test.get())
}
