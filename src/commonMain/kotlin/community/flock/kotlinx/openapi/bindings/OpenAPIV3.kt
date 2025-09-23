package community.flock.kotlinx.openapi.bindings

import community.flock.kotlinx.openapi.bindings.Version.V3
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

open class OpenAPIV3(
    val json: Json = Json { prettyPrint = true },
) : OpenAPISpecification {

    fun decodeFromString(string: String): OpenAPIV3Model = json
        .decodeFromString<JsonObject>(string)
        .decode(V3)
        .let(json::decodeFromJsonElement)

    fun encodeToString(value: OpenAPIV3Model): String = json
        .encodeToJsonElement(value)
        .encode()
        .let(json::encodeToString)

    companion object : OpenAPIV3()
}
