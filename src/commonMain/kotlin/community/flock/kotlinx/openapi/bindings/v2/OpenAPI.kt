package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.decodeExtensions
import community.flock.kotlinx.openapi.bindings.encodeExtensions
import community.flock.kotlinx.openapi.bindings.traverse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

val regex = """
   ^\|x-[^\|]*\|
   ^\|info\|x-[^\|]*\|$
   \|responses\|x-[^\|]*\|$
   \|responses\|x-[^\|]*\|$
   \|items\|x-[^\|]*\|$
   \|schema\|x-[^\|]*\|$
   \|schemas\|[^\|]*\|x-[^\|]*\|$
   \|properties\|[^\|]*\|x-[^\|]*\|$
   \|properties\|[^\|]*\|[^\|]*\|x-[^\|]*\|$
   \|parameters\|[^\|]*\|x-[^\|]*\|$
   \|paths\|[^\|]*\|[^\|]*\|x-[^\|]*\|${'$'}
""".trimIndent().split("\n").map { it.toRegex() }

sealed class OpenAPI(
    val json: Json = Json { prettyPrint = true }
) {

    fun decodeFromJsonElement(jsonElement: JsonElement): SwaggerObject = jsonElement
        .traverse({ path, obj -> obj.encodeExtensions(path, regex) })
        .let { json.decodeFromJsonElement(it) }

    fun decodeFromString(string: String): SwaggerObject = json
        .decodeFromString<JsonElement>(string)
        .traverse({ path, obj -> obj.encodeExtensions(path, regex) })
        .let { json.decodeFromJsonElement(it) }

    fun encodeToString(value: SwaggerObject): String = json
        .encodeToJsonElement(value)
        .traverse({ _, obj -> obj.decodeExtensions() })
        .let { json.encodeToString(it) }

    companion object Default : OpenAPI()
}
