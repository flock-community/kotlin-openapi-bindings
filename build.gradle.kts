plugins {
    id("root.publication")
    id("module.spotless")
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

group = libs.versions.group.id.get()
version = System.getenv(libs.versions.from.env.get()) ?: libs.versions.default.get()

repositories {
    mavenCentral()
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
        jvm {
            withJava()
            java {
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
                }
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.bundles.kotlin.test)
                implementation(libs.bundles.kotest)
            }
        }
    }
}
