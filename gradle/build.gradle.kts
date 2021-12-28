plugins {
	id("java")
	id("java-gradle-plugin")
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

	testRuntimeOnly(gradleApi())
	testRuntimeOnly(gradleKotlinDsl())
	testRuntimeOnly(localGroovy())
	testRuntimeOnly(gradleTestKit())

	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("com.google.code.gson:gson:2.8.9")
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		showStandardStreams = true
	}
}

gradlePlugin {
	testSourceSets(sourceSets.test.get())
}
