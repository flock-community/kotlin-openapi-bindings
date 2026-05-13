package community.flock.kotlinx.openapi.bindings

import community.flock.kotlinx.openapi.bindings.Version.V20
import community.flock.kotlinx.openapi.bindings.Version.V30
import community.flock.kotlinx.openapi.bindings.Version.V31
import community.flock.kotlinx.openapi.bindings.Version.V32
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonPrimitive

open class OpenAPIV3(
    val json: Json = Json { prettyPrint = true },
) : OpenAPISpecification {

    fun decodeFromString(string: String): OpenAPIV3Model {
        val tree = json.decodeFromString<JsonObject>(string)
        val openapi = tree["openapi"]?.jsonPrimitive?.contentOrNull
            ?: error("No valid openapi v3 element 'openapi' is missing")
        val version = Version.fromOpenApiString(openapi)
        val decoded = tree.decode(version)
        return when (version) {
            V30 -> json.decodeFromJsonElement<OpenAPIV30Model>(decoded)
            V31 -> json.decodeFromJsonElement<OpenAPIV31Model>(decoded)
            V32 -> json.decodeFromJsonElement<OpenAPIV32Model>(decoded)
            V20 -> error("V2 documents are not supported by OpenAPIV3")
        }
    }

    fun encodeToString(value: OpenAPIV3Model): String = json
        .encodeToJsonElement(OpenAPIV3ModelSerializer, value)
        .encode()
        .let(json::encodeToString)

    companion object : OpenAPIV3()
}
