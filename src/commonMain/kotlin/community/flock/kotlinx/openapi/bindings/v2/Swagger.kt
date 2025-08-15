package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.Version.V2
import community.flock.kotlinx.openapi.bindings.common.CommonSpecification
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

open class Swagger(
    val json: Json = Json { prettyPrint = true },
) : CommonSpecification {
    fun decodeFromString(string: String): SwaggerObject = json
        .decodeFromString<JsonObject>(string)
        .decode(V2)
        .let(json::decodeFromJsonElement)

    fun encodeToString(value: SwaggerObject): String = json
        .encodeToJsonElement(value)
        .encode()
        .let(json::encodeToString)

    companion object Default : Swagger()
}
