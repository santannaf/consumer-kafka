import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    kotlin("plugin.jpa") version "2.3.0"
    kotlin("plugin.serialization") version "2.3.0"

    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.11.3"

    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.example.consumer.process"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
    implementation("com.tanna.spring:kafka:1.0.0") {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
        implementation("com.github.avro-kotlin.avro4k:avro4k-core:1.10.1")
        implementation("org.apache.avro:avro:1.12.0")
    }

//    implementation("com.tanna.spring:kafka:0.0.4") {
//        implementation("org.apache.avro:avro:1.11.0")
//        implementation("com.github.avro-kotlin.avro4k:avro4k-core:1.6.0")
//        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
//        implementation("io.confluent:monitoring-interceptors:7.5.1")
//        implementation("org.yaml:snakeyaml:2.0")
//        implementation("com.google.guava:guava:33.0.0-jre")
//    }

    implementation("org.springframework.boot:spring-boot-starter-opentelemetry")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-restclient")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // Unit Tests
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-opentelemetry-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
        jvmTarget.set(JvmTarget.JVM_25)
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