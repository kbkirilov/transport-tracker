plugins {
	java
	id("org.springframework.boot") version "4.0.6"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.diffplug.spotless") version "8.6.0"
}

group = "com.kiril"
version = "0.0.1-SNAPSHOT"
description = "transport-tracker"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

spotless {
	java {
		googleJavaFormat()
		removeUnusedImports()
		trimTrailingWhitespace()
		endWithNewline()
	}
}

repositories {
	gradlePluginPortal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	testImplementation("org.springframework.boot:spring-boot-starter-websocket-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named("compileJava") {
	dependsOn("spotlessApply")
}
