import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin")
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

signing {
    setRequired { System.getenv("GPG_PRIVATE_KEY") != null }
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PASSPHRASE"),
    )
    sign(publishing.publications)
}
