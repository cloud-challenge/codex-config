import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
	id("org.sonarqube") version "2.8"
}


group = "org.cloudchallenge.codex"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-config-server")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client"){
		exclude(group = "org.springframework.cloud", module = "spring-cloud-starter-netflix-ribbon")
		exclude(group = "org.springframework.cloud", module = "spring-cloud-netflix-ribbon")
		exclude(group = "com.netflix.ribbon", module = "ribbon-eureka")
	}
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR3")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

sonarqube {
	properties {
		property("sonar.projectKey", "cloud-challenge_codex-config")
		property("sonar.organization", "cloud-challenge")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.login", System.getProperty("sonar.login", ""))
		property("sonar.scm.provider", "git")
	}
}

tasks.register("project-version") {
	doFirst {
		println("version:${project.version}")
	}
}