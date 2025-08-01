plugins {
    id("com.diffplug.spotless")
}

spotless {
    val exclude = listOf(
        "**/build/**",
        "**/generated/**",
        "**/generated-sources/**",
        "**/resources/**",
    ).toTypedArray()

    kotlin {
        target("**/*.kt", "**/*.kts")
        targetExclude(*exclude)
        ktlint().editorConfigOverride(
            mapOf("ktlint_code_style" to "intellij_idea"),
        )
        suppressLintsFor {
            step = "ktlint"
            shortCode = "standard:enum-entry-name-case"
        }
    }
}
