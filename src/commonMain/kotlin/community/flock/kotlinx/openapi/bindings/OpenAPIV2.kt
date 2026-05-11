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

open class OpenAPIV2(
    val json: Json = Json { prettyPrint = true },
) : OpenAPISpecification {

    fun decodeFromString(string: String): OpenAPIV2Model {
        val tree = json.decodeFromString<JsonObject>(string)
        val swagger = tree["swagger"]?.jsonPrimitive?.contentOrNull
            ?: error("No valid openapi v2 element 'swagger' is missing")
        val version = Version.fromSwaggerString(swagger)
        val decoded = tree.decode(version)
        return when (version) {
            V20 -> json.decodeFromJsonElement<OpenAPIV20Model>(decoded)
            V30, V31, V32 -> error("V3 documents are not supported by OpenAPIV2")
        }
    }

    fun encodeToString(value: OpenAPIV2Model): String = json
        .encodeToJsonElement(OpenAPIV2ModelSerializer, value)
        .encode()
        .let(json::encodeToString)

    companion object : OpenAPIV2()
}
