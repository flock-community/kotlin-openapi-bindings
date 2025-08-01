rootProject.name = "kotlin-openapi-bindings"

pluginManagement {
    includeBuild("gradle/plugins/publish-sonatype")
    includeBuild("gradle/plugins/spotless")
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
