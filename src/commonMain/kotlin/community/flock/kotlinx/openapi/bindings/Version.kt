package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.Serializable

@Serializable
enum class Version {
    V20,
    V30,
    V31,
    V32,
    ;

    companion object {
        fun fromOpenApiString(value: String): Version {
            val match = Regex("""^(\d+)\.(\d+)(?:\..*)?$""").matchEntire(value)
                ?: error("Unsupported OpenAPI version: $value")
            val (major, minor) = match.destructured
            return when (major to minor) {
                "3" to "0" -> V30
                "3" to "1" -> V31
                "3" to "2" -> V32
                else -> error("Unsupported OpenAPI version: $value")
            }
        }

        fun fromSwaggerString(value: String): Version {
            val match = Regex("""^(\d+)\.(\d+)(?:\..*)?$""").matchEntire(value)
                ?: error("Unsupported Swagger version: $value")
            val (major, minor) = match.destructured
            return when (major to minor) {
                "2" to "0" -> V20
                else -> error("Unsupported Swagger version: $value")
            }
        }
    }
}
