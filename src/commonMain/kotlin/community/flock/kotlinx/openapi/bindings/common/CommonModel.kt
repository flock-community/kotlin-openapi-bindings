@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.common

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlin.jvm.JvmInline

interface HeaderOrReferenceObject
interface SchemaOrReferenceObject

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

interface CommonModel {
    val info: InfoObject
    val paths: Map<Path, PathItemObject>
    val security: List<Map<String, List<String>>>?
    val tags: List<TagObject>?
    val externalDocs: ExternalDocumentationObject?
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

interface PathItemObject {
    val ref: String?
    val summary: String?
    val description: String?
    val get: OperationObject?
    val put: OperationObject?
    val post: OperationObject?
    val delete: OperationObject?
    val options: OperationObject?
    val head: OperationObject?
    val patch: OperationObject?
    val trace: OperationObject?
    val servers: List<ServerObject>?
    val xProperties: Map<String, JsonElement>?
}

@Serializable
data class ServerObject(
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

interface OperationObject {
    val tags: List<String?>?
    val summary: String?
    val description: String?
    val externalDocs: ExternalDocumentationObject?
    val operationId: String?
    val deprecated: Boolean?
    val security: List<Map<String, List<String>>>?
    val servers: List<ServerObject>?
    val xProperties: Map<String, JsonElement>?
}

interface RequestBodyObject {
    val description: String?
    val content: Map<MediaType, MediaTypeObject>?
    val required: Boolean?
    val xProperties: Map<String, JsonElement>?
}

interface MediaTypeObject {
    val schema: SchemaOrReferenceObject?
    val examples: Map<String, JsonElement>?
    val example: JsonElement?
    val encoding: Map<String, EncodingPropertyObject>?
}

interface EncodingPropertyObject {
    val contentType: String?
    val headers: Map<String, HeaderOrReferenceObject>?
    val style: String?
    val explode: Boolean?
    val allowReserved: Boolean?
}

@Serializable
data class TagObject(
    val name: String,
    val description: String? = null,
    val externalDocs: ExternalDocumentationObject? = null,
)

@Serializable
data class ExternalDocumentationObject(
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
