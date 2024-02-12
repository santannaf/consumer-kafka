import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("application")
    id("java")

    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.graalvm.buildtools.native") version "0.9.28"

    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.2.0"
}

group = "com.example.consumer.process"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {

    implementation("com.tanna.spring:kafka:0.0.4") {
        implementation("org.apache.avro:avro:1.11.0")
        implementation("com.github.avro-kotlin.avro4k:avro4k-core:1.6.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
        implementation("io.confluent:monitoring-interceptors:7.5.1")
        implementation("org.yaml:snakeyaml:2.0")
        implementation("com.google.guava:guava:33.0.0-jre")
    }

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//tasks.distZip { enabled = false }
//tasks.startScripts { enabled = false }
//tasks.distTar { enabled = false }
//
//tasks.getByName<Jar>("jar") {
//    enabled = false
//}