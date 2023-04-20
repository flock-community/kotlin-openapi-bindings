@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

@Serializable(with = ResponseOrReferenceObjectSerializer::class)
sealed interface ResponseOrReferenceObject

@Serializable(with = HeaderOrReferenceObjectSerializer::class)
sealed interface HeaderOrReferenceObject

@Serializable(with = SchemaOrReferenceObjectSerializer::class)
sealed interface SchemaOrReferenceObject

@Serializable(with = SchemaOrReferenceOrBooleanObjectSerializer::class)
sealed interface SchemaOrReferenceOrBooleanObject

@Serializable
data class OpenAPIObject(
    val openapi: String,
    val info: InfoObject,
    val servers: List<ServerObject>? = null,
    val paths: Map<String, PathItemObject>,
    val components: ComponentsObject? = null,
//    val security: List<SecurityRequirementObject>? = null,
//    val tags: List<TagObject>? = null,
//    val externalDocs: ExternalDocumentationObject? = null
)

@Serializable
data class InfoObject(
    val title: String,
    val description: String? = null,
    val termsOfService: String? = null,
    val contact: ContactObject? = null,
    val license: LicenseObject? = null,
    val version: String
)

@Serializable
data class PathItemObject(
    val ref: String? = null,
    val summary: String? = null,
    val description: String? = null,
    val get: OperationObject? = null,
    val put: OperationObject? = null,
    val post: OperationObject? = null,
    val delete: OperationObject? = null,
    val options: OperationObject? = null,
    val head: OperationObject? = null,
    val patch: OperationObject? = null,
    val trace: OperationObject? = null,
    val servers: List<ServerObject>? = null,
    val parameters: List<JsonElement>? = null
)

@Serializable
data class OperationObject(
    val tags: List<String?>? = null,
    val summary: String? = null,
    val description: String? = null,
    val externalDocs: ExternalDocumentationObject? = null,
    val operationId: String? = null,
    val parameters: List<JsonElement>? = null,
    val requestBody: JsonElement? = null,
    val responses: Map<String, ResponseOrReferenceObject>,
//    val callbacks: CallbacksObject? = null,
    val deprecated: Boolean? = null,
//    val security: List<SecurityRequirementObject>? = null,
    val servers: List<ServerObject>? = null
)

@Serializable
data class ResponseObject(
    val description: String? = null,
    val headers: Map<String, HeaderOrReferenceObject>? = null,
    val content: Map<String, MediaTypeObject>? = null,
//    val links: LinksObject?
): ResponseOrReferenceObject

@Serializable
data class BaseParameterObject(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: String? = null, // "matrix" | "label" | "form" | "simple" | "spaceDelimited" | "pipeDelimited" | "deepObject"
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: SchemaObject? = null,
    val examples: Map<String, ExampleObject>? = null,
    val example: JsonElement? = null,
//    val content: ContentObject?
): HeaderOrReferenceObject

@Serializable
data class MediaTypeObject(
    val schema: SchemaOrReferenceObject? = null,
    val examples: Map<String, JsonElement>? = null,
    val example: JsonElement? = null,
//    val encoding: EncodingObject?
)

@Serializable
data class ExampleObject(
    val summary: String? = null,
    val description: String? = null,
    val value: JsonElement? = null,
    val externalValue: String?
)

@Serializable
data class ContactObject(
    val name: String? = null,
    val url: String? = null,
    val email: String? = null
)

@Serializable
data class LicenseObject(
    val name: String,
    val url: String? = null
)

@Serializable
data class ServerObject(
    val url: String,
    val description: String? = null,
    val variables: Map<String, ServerVariableObject>? = null
)

@Serializable
data class ServerVariableObject(
    val enum: List<JsonElement>? = null,
    val default: JsonElement? = null,
    val description: String? = null
)

@Serializable
data class ComponentsObject(
    val schemas: Map<String, JsonElement>? = null,
    val responses: Map<String, JsonElement>? = null,
    val parameters: Map<String, JsonElement>? = null,
    val examples: Map<String, JsonElement>? = null,
    val requestBodies: Map<String, JsonElement>? = null,
    val headers: Map<String, JsonElement>? = null,
    val securitySchemes: Map<String, JsonElement>? = null,
    val links: Map<String, JsonElement>? = null,
    val callbacks: Map<String, JsonElement>? = null
)

@Serializable
data class ExternalDocumentationObject(
    val description: String? = null,
    val url: String
)

@Serializable
data class BooleanObject(
    val value: Boolean
): SchemaOrReferenceOrBooleanObject

@Serializable
data class SchemaObject(
    val nullable: Boolean? = null,
//    val discriminator: DiscriminatorObject? = null,
    val readOnly: Boolean? = null,
    val writeOnly: Boolean? = null,
//    val xml: XmlObject? = null,
    val externalDocs: ExternalDocumentationObject? = null,
    val example: JsonElement? = null,
    val examples: Array<JsonElement>? = null,
    val deprecated: Boolean? = null,

    val type: String? = null,
    val allOf: Array<SchemaOrReferenceObject>? = null,
    val oneOf: Array<SchemaOrReferenceObject>? = null,
    val anyOf: Array<SchemaOrReferenceObject>? = null,
    val not: SchemaOrReferenceObject? = null,
    val items: SchemaOrReferenceObject? = null,
    val properties: Map<String, SchemaOrReferenceObject>? = null,
    val additionalProperties: SchemaOrReferenceOrBooleanObject? = null,
    val description: String? = null,
    val format: String? = null,
    val default: JsonElement? = null,

    val title: String? = null,
    val multipleOf: Double? = null,
    val maximum: Double? = null,
    val exclusiveMaximum: Boolean? = null,
    val minimum: Double? = null,
    val exclusiveMinimum: Boolean? = null,
    val maxLength: Int? = null,
    val minLength: Int? = null,
    val pattern: String? = null,
    val maxItems: Int? = null,
    val minItems: Int? = null,
    val uniqueItems: Boolean? = null,
    val maxProperties: Int? = null,
    val minProperties: Int? = null,
    val required: Array<String>? = null,
    val enum: Array<JsonElement>? = null,
) : SchemaOrReferenceObject, SchemaOrReferenceOrBooleanObject

@Serializable
data class ReferenceObject(
    val `$ref`: String
) : SchemaOrReferenceObject, SchemaOrReferenceOrBooleanObject, ResponseOrReferenceObject, HeaderOrReferenceObject


object ResponseOrReferenceObjectSerializer : KSerializer<ResponseOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("ResponseOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: ResponseOrReferenceObject) {
        val serializer = serializer(value::class.javaObjectType)
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): ResponseOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ResponseObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(ResponseObject.serializer(), tree)
        }
    }
}

object SchemaOrReferenceObjectSerializer : KSerializer<SchemaOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("SchemaOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SchemaOrReferenceObject) {
        val serializer = serializer(value::class.javaObjectType)
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SchemaOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SchemaObject.serializer(), tree)
        }
    }
}

object HeaderOrReferenceObjectSerializer : KSerializer<HeaderOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("HeaderOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: HeaderOrReferenceObject) {
        val serializer = serializer(value::class.javaObjectType)
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): HeaderOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(BaseParameterObject.serializer(), tree)
        }
    }
}

object SchemaOrReferenceOrBooleanObjectSerializer : KSerializer<SchemaOrReferenceOrBooleanObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("SchemaOrReferenceOrBooleanObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SchemaOrReferenceOrBooleanObject) {
        val serializer = serializer(value::class.javaObjectType)
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SchemaOrReferenceOrBooleanObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        input.decodeJsonElement().jsonObject
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SchemaObject.serializer(), tree)
        }
    }
}