package community.flock.kotlinx.openapi.bindings.v3

import community.flock.kotlinx.openapi.bindings.Version.V3
import community.flock.kotlinx.openapi.bindings.common.CommonSpecification
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

open class OpenAPI(
    val json: Json = Json { prettyPrint = true },
) : CommonSpecification {

    fun decodeFromJsonString(string: String): OpenAPIModel = json
        .decodeFromString<JsonObject>(string)
        .decode(V3)
        .let(json::decodeFromJsonElement)

    fun encodeToString(value: OpenAPIModel): String = json
        .encodeToJsonElement(value)
        .encode()
        .let(json::encodeToString)

    companion object : OpenAPI()
}
