package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

val regex = """
   ^\|x-[^\|]*\|
   ^\|info\|x-[^\|]*\|$
   \|responses\|x-[^\|]*\|$
   \|responses\|x-[^\|]*\|$
   \|items\|x-[^\|]*\|$
   \|schema\|x-[^\|]*\|$
   \|schemas\|[^\|]*\|x-[^\|]*\|$
   \|properties\|[^\|]*\|x-[^\|]*\|$
   \|parameters\|[^\|]*\|x-[^\|]*\|$
   \|paths\|[^\|]*\|[^\|]*\|x-[^\|]*\|${'$'}
""".trimIndent().split("\n").map { it.toRegex() }

sealed class OpenAPI(
    val json: Json = Json{ prettyPrint = true }
) {

    fun decodeFromString(string: String): OpenAPIObject {
        return json
            .decodeFromString<JsonElement>(string)
            .traverse({ path, obj -> obj.encodeExtensions(path) })
            .let { json.decodeFromJsonElement(it) }
    }

    fun encodeToString(value: OpenAPIObject): String {
        return json
            .encodeToJsonElement(value)
            .traverse({ _, obj -> obj.decodeExtensions() })
            .let { json.encodeToString(it) }

    }

    companion object Default : OpenAPI()
}

private fun JsonObject.encodeExtensions(path: String): JsonObject {
    val (known, unknown) = this.toList()
        .partition { !regex.hasMatchedRegex("$path${it.first}|") }
    return if (unknown.isNotEmpty()) {
        JsonObject(known.toMap() + ("xProperties" to JsonObject(unknown.toMap())))
    } else {
        JsonObject(known.toMap())
    }
}

private fun JsonObject.decodeExtensions(): JsonObject {
    return JsonObject(filter { it.key != "xProperties" } + (get("xProperties")?.jsonObject ?: emptyMap()))
}

private fun JsonElement.traverse(func: (String, JsonObject) -> JsonObject, path: String = "|"): JsonElement {
    return when (this) {
        is JsonObject -> {
            val content = map { it.key to it.value.traverse(func, "$path${it.key}|") }.toMap()
            func(path, JsonObject(content))
        }

        is JsonArray -> {
            JsonArray(mapIndexed { idx, value -> value.traverse(func, "$path$idx|") })
        }

        is JsonPrimitive -> {
            this
        }
    }
}

private fun List<Regex>.hasMatchedRegex(path: String): Boolean {
    for (regex in this) {
        if (regex.containsMatchIn(path)) {
            return true
        }
    }
    return false
}

