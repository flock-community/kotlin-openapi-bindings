package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject

fun JsonObject.encodeExtensions(path: String, regex:List<Regex>): JsonObject {
    val (known, unknown) = this.toList()
        .partition { !regex.hasMatchedRegex("$path${it.first}|") }
    return if (unknown.isNotEmpty()) {
        JsonObject(known.toMap() + ("xProperties" to JsonObject(unknown.toMap())))
    } else {
        JsonObject(known.toMap())
    }
}

fun JsonObject.decodeExtensions(): JsonObject {
    return JsonObject(filter { it.key != "xProperties" } + (get("xProperties")?.jsonObject ?: emptyMap()))
}

fun JsonElement.traverse(func: (String, JsonObject) -> JsonObject, path: String = "|"): JsonElement {
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

fun List<Regex>.hasMatchedRegex(path: String): Boolean {
    for (regex in this) {
        if (regex.containsMatchIn(path)) {
            return true
        }
    }
    return false
}

