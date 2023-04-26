plugins {
    id("maven-publish")
    kotlin("multiplatform") version "1.8.21"
    kotlin("plugin.serialization") version "1.5.21"
}

group = "community.flock.kotlinx.openapi.bindings"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
                implementation("io.kotest:kotest-framework-engine:5.6.1")
                implementation("io.kotest:kotest-assertions-core:5.6.1")
                implementation("io.kotest:kotest-assertions-json:5.6.1")
            }
        }
    }
    jvm()
}

