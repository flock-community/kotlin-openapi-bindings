package community.flock.kotlinx.openapi.bindings.common

import community.flock.kotlinx.openapi.bindings.Version
import community.flock.kotlinx.openapi.bindings.Version.V2
import community.flock.kotlinx.openapi.bindings.Version.V3
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject

private val regex = """
   ^\|x-[^\|]*\|
   ^\|info\|x-[^\|]*\|$
   \|requestBody\|x-[^\|]*\|$
   \|responses\|x-[^\|]*\|$
   \|items\|x-[^\|]*\|$
   \|schema\|x-[^\|]*\|$
   \|schemas\|[^\|]*\|x-[^\|]*\|$
   \|headers\|[^\|]*\|x-[^\|]*\|$
   \|properties\|[^\|]*\|x-[^\|]*\|$
   \|properties\|[^\|]*\|[^\|]*\|x-[^\|]*\|$
   \|parameters\|[^\|]*\|x-[^\|]*\|$
   \|paths\|[^\|]*\|[^\|]*\|x-[^\|]*\|$
""".trimIndent().split("\n").map { it.toRegex() }

interface CommonSpecification {

    fun JsonObject.decode(version: Version): JsonElement = validate(version)
        .traverse { path, obj -> obj.encodeExtensions(path) }

    fun JsonElement.encode(): JsonElement = traverse { _, obj -> obj.decodeExtensions() }
}

private fun JsonObject.validate(version: Version) = apply {
    when (version) {
        V2 -> check("swagger" in keys) { "No valid openapi v2 element 'swagger' is missing" }
        V3 -> check("openapi" in keys) { "No valid openapi v3 element 'openapi' is missing" }
    }
}

private fun JsonObject.encodeExtensions(path: String) = toList()
    .partition { (key, _) -> !regex.hasMatchedRegex("$path$key|") }
    .let { (known, unknown) -> known.toMap() + unknown.toXProperties() }
    .let(::JsonObject)

private fun List<Pair<String, JsonElement>>.toXProperties() = takeIf { it.isNotEmpty() }
    ?.let { mapOf("xProperties" to JsonObject(it.toMap())) }
    .orEmpty()

private fun JsonObject.decodeExtensions() = JsonObject(filter { it.key != "xProperties" } + (get("xProperties")?.jsonObject ?: emptyMap()))

private fun List<Regex>.hasMatchedRegex(path: String) = find { it.containsMatchIn(path) }.found()

private fun Any?.found() = this?.let { true } ?: false

private fun JsonElement.traverse(path: String = "|", block: (String, JsonObject) -> JsonObject): JsonElement = when (this) {
    is JsonPrimitive -> this
    is JsonArray -> JsonArray(mapIndexed { idx, value -> value.traverse("$path$idx|", block) })
    is JsonObject -> map { it.key to it.value.traverse("$path${it.key}|", block) }.toMap()
        .let(::JsonObject)
        .let { block(path, it) }
}
