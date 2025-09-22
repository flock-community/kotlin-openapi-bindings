@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlin.jvm.JvmInline

sealed interface ResponseOrReference
sealed interface HeaderOrReference
sealed interface ParameterOrReference
sealed interface SchemaOrReference
sealed interface SchemaOrReferenceOrBoolean
sealed interface CallbackOrReference
sealed interface LinkOrReference
sealed interface ExampleOrReference
sealed interface RequestBodyOrReference
sealed interface SecuritySchemeOrReference

@JvmInline
@Serializable
value class Ref(val value: String)

@JvmInline
@Serializable
value class MediaType(val value: String)

@JvmInline
@Serializable
value class StatusCode(val value: String)

@JvmInline
@Serializable
value class Path(val value: String)

sealed interface CommonModel {
    val info: InfoObject
    val paths: Map<Path, PathItem>
    val security: List<Map<String, List<String>>>?
    val tags: List<TagObject>?
    val externalDocs: ExternalDocumentation?
    val xProperties: Map<String, JsonElement>?
}

@Serializable
data class InfoObject(
    val title: String,
    val description: String? = null,
    val termsOfService: String? = null,
    val contact: ContactObject? = null,
    val license: LicenseObject? = null,
    val version: String,
    val xProperties: Map<String, JsonElement>? = null,
)

sealed interface PathItem {
    val parameters: List<ParameterOrReference>?
    val ref: String?
    val summary: String?
    val description: String?
    val get: Operation?
    val put: Operation?
    val post: Operation?
    val delete: Operation?
    val options: Operation?
    val head: Operation?
    val patch: Operation?
    val trace: Operation?
    val servers: List<Server>?
    val xProperties: Map<String, JsonElement>?
}

@Serializable
data class Server(
    val url: String,
    val description: String? = null,
    val variables: Map<String, ServerVariableObject>? = null,
)

@Serializable
data class ServerVariableObject(
    val enum: List<JsonPrimitive>? = null,
    val default: JsonElement? = null,
    val description: String? = null,
)

sealed interface Operation {
    val parameters: List<ParameterOrReference>?
    val requestBody: RequestBodyOrReference?
    val responses: Map<StatusCode, ResponseOrReference>?
    val callbacks: Map<String, CallbackOrReference>?
    val tags: List<String?>?
    val summary: String?
    val description: String?
    val externalDocs: ExternalDocumentation?
    val operationId: String?
    val deprecated: Boolean?
    val security: List<Map<String, List<String>>>?
    val servers: List<Server>?
    val xProperties: Map<String, JsonElement>?
}

sealed interface RequestBody {
    val description: String?
    val content: Map<MediaType, MediaTypeObject>?
    val required: Boolean?
    val xProperties: Map<String, JsonElement>?
}

sealed interface MediaTypeObject {
    val schema: SchemaOrReference?
    val examples: Map<String, JsonElement>?
    val example: JsonElement?
    val encoding: Map<String, EncodingProperty>?
}

sealed interface EncodingProperty {
    val contentType: String?
    val headers: Map<String, HeaderOrReference>?
    val style: String?
    val explode: Boolean?
    val allowReserved: Boolean?
}

sealed interface Link {
    val operationRef: String?
    val operationId: String?
    val parameters: Map<String, JsonElement>?
    val requestBody: JsonElement?
    val description: String?
    val server: Server?
}

sealed interface Response {
    val description: String?
    val headers: Map<String, HeaderOrReference>?
    val links: Map<String, LinkOrReference>?
    val xProperties: Map<String, JsonElement>?
}

sealed interface Header {
    val description: String?
    val xProperties: Map<String, JsonElement>?
}

sealed interface Parameter {
    val schema: SchemaOrReference?
    val name: String
    val xProperties: Map<String, JsonElement>?
}

sealed interface SecurityScheme {
    val description: String?
    val name: String?
    val `in`: String?
}

sealed interface BooleanValue {
    val value: Boolean
}

sealed interface Schema {
    val example: JsonElement?
    val readOnly: Boolean?
    val xml: XML?
    val externalDocs: ExternalDocumentation?
    val title: String?
    val description: String?
    val default: JsonElement?
    val multipleOf: Double?
    val uniqueItems: Boolean?
    val maxProperties: Int?
    val minProperties: Int?
    val required: List<String>?
    val enum: List<JsonPrimitive>?
    val items: SchemaOrReference?
    val allOf: List<SchemaOrReference>?
    val properties: Map<String, SchemaOrReference>?
    val additionalProperties: SchemaOrReferenceOrBoolean?
    val xProperties: Map<String, JsonElement>?
    val format: String?
    val maximum: Double?
    val exclusiveMaximum: Boolean?
    val minimum: Double?
    val exclusiveMinimum: Boolean?
    val maxLength: Int?
    val minLength: Int?
    val pattern: String?
    val maxItems: Int?
    val minItems: Int?
}

sealed interface Reference {
    val ref: Ref
}

@Serializable
data class TagObject(
    val name: String,
    val description: String? = null,
    val externalDocs: ExternalDocumentation? = null,
)

@Serializable
data class ExternalDocumentation(
    val description: String? = null,
    val url: String,
)

@Serializable
data class ContactObject(
    val name: String? = null,
    val url: String? = null,
    val email: String? = null,
)

@Serializable
data class LicenseObject(
    val name: String,
    val url: String? = null,
)

@Serializable
data class XML(
    val name: String? = null,
    val namespace: String? = null,
    val prefix: String? = null,
    val attribute: Boolean? = null,
    val wrapped: Boolean? = null,
)

inline val <reified T> T.simpleName get() = T::class.simpleName!!
