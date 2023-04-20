import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.serialization") version "1.5.21"
    kotlin("jvm") version "1.7.22"
}

group = "community.flock.kotlinx.openapi.bindings"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.skyscreamer:jsonassert:1.2.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
