import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nolambda.sourescanner"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.5.32"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("sourcescanner") {
            id = "nolambda.stream.sourcescanner"
            implementationClass = "nolambda.stream.sourcescanner.SourceScannerPlugin"
        }
    }
}

java {
    withSourcesJar()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("junit:junit:4.13.2")
}


val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

