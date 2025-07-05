plugins {
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    kotlin("multiplatform") version "1.9.25"
    kotlin("plugin.serialization") version "1.5.21"
}

group = "community.flock.kotlinx.openapi.bindings"
version = "0.1.1"

repositories {
    mavenCentral()
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            username.set(System.getenv("SONATYPE_USERNAME"))
            password.set(System.getenv("SONATYPE_PASSWORD"))
            stagingProfileId.set(System.getenv("SONATYPE_STAGING_PROFILE_ID"))
        }
    }
}

signing {
    setRequired { System.getenv("GPG_PRIVATE_KEY") != null }
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PASSPHRASE"),
    )
    sign(publishing.publications)
}

publishing {
    publications {
        withType<MavenPublication> {
            artifact(
                tasks.register("${name}JavadocJar", Jar::class) {
                    archiveClassifier.set("javadoc")
                    archiveAppendix.set(this@withType.name)
                },
            )
            pom {
                name.set("Flock. community")
                description.set("Kotlin openapi bindings")
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

