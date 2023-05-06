import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.8.10"
    kotlin("multiplatform") version "1.8.21"
    kotlin("plugin.serialization") version "1.5.21"
}

group = "community.flock.kotlinx.openapi.bindings"
version = "0.0.5"


val dokkaOutputDir = "$buildDir/dokka"

tasks.getByName<DokkaTask>("dokkaHtml") {
    outputDirectory.set(file(dokkaOutputDir))
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

repositories {
    mavenCentral()
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

publishing {
    publications {
        repositories {
            maven {
                name="oss"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                credentials {
                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
            }
        }
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("Flock. community")
                description.set("KODEIN Dependency Injection Core")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                url.set("https://flock.community")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/flock-community/kotlin-openapi-bindings/issues")
                }
                scm {
                    connection.set("https://github.com/flock-community/kotlin-openapi-bindings.git")
                    url.set("https://github.com/flock-community/kotlin-openapi-bindings")
                }
                developers {
                    developer {
                        name.set("Willem Veelenturf")
                        email.set("willem.veelenturf@flock.community")
                    }
                }
            }
        }
    }
}


kotlin {
    macosX64()
    macosArm64()
    linuxX64()
    mingwX64()
    js(IR) {
        nodejs()
    }
    jvm {
        withJava()
    }
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
}

