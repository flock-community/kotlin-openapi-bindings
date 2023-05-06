@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject

@Serializable(with = ResponseOrReferenceObjectSerializer::class)
sealed interface ResponseOrReferenceObject

@Serializable(with = HeaderOrReferenceObjectSerializer::class)
sealed interface HeaderOrReferenceObject

@Serializable(with = ParameterOrReferenceObjectSerializer::class)
sealed interface ParameterOrReferenceObject

@Serializable(with = SchemaOrReferenceObjectSerializer::class)
sealed interface SchemaOrReferenceObject

@Serializable(with = SchemaOrReferenceOrBooleanObjectSerializer::class)
sealed interface SchemaOrReferenceOrBooleanObject

@Serializable(with = CallbackOrReferenceObjectSerializer::class)
sealed interface CallbackOrReferenceObject

@Serializable(with = LinkOrReferenceObjectSerializer::class)
sealed interface LinkOrReferenceObject

@Serializable
data class OpenAPIObject(
    val openapi: String,
    val info: InfoObject,
    val servers: List<ServerObject>? = null,
    val paths: Map<String, PathItemObject>,
    val components: ComponentsObject? = null,
    val security: List<Map<String, List<String>>>? = null,
    val tags: List<TagObject>? = null,
    val externalDocs: ExternalDocumentationObject? = null,
    val xProperties: Map<String, JsonElement>? = null
)

@Serializable
data class TagObject(
    val name: String,
    val description: String? = null,
    val externalDocs: ExternalDocumentationObject? = null,
)

@Serializable
data class InfoObject(
    val title: String,
    val description: String? = null,
    val termsOfService: String? = null,
    val contact: ContactObject? = null,
    val license: LicenseObject? = null,
    val version: String,
    val xProperties: Map<String, JsonElement>? = null
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
    val parameters: List<ParameterOrReferenceObject>? = null,
    val xProperties: Map<String, JsonElement>? = null
)

@Serializable
data class OperationObject(
    val tags: List<String?>? = null,
    val summary: String? = null,
    val description: String? = null,
    val externalDocs: ExternalDocumentationObject? = null,
    val operationId: String? = null,
    val parameters: List<ParameterOrReferenceObject>? = null,
    val requestBody: JsonElement? = null,
    val responses: Map<String, ResponseOrReferenceObject>,
    val callbacks: Map<String, CallbackOrReferenceObject>? = null,
    val deprecated: Boolean? = null,
//    val security: List<SecurityRequirementObject>? = null,
    val servers: List<ServerObject>? = null,
    val xProperties: Map<String, JsonElement>? = null
)

@Serializable(with = CallbacksObjectSerializer::class)
class CallbacksObject(override val entries: Set<Map.Entry<String, PathItemObject>>) : CallbackOrReferenceObject,
    AbstractMap<String, PathItemObject>()

@Serializable(with = LinksObjectSerializer::class)
class LinksObject(override val entries: Set<Map.Entry<String, LinkOrReferenceObject>>
) : AbstractMap<String, LinkOrReferenceObject>()

@Serializable
data class LinkObject(
    val operationRef: String? = null,
    val operationId: String? = null,
    val parameters: Map<String, JsonElement>? = null,
    val requestBody: JsonElement? = null,
    val description: String? = null,
    val server: ServerObject? = null,
) : LinkOrReferenceObject

@Serializable
data class ResponseObject(
    val description: String? = null,
    val headers: Map<String, HeaderOrReferenceObject>? = null,
    val content: Map<String, MediaTypeObject>? = null,
    val links: LinksObject? = null,
    val xProperties: Map<String, JsonElement>? = null
) : ResponseOrReferenceObject

@Serializable
data class HeaderObject(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: String? = null, // "matrix" | "label" | "form" | "simple" | "spaceDelimited" | "pipeDelimited" | "deepObject"
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: SchemaOrReferenceObject? = null,
    val examples: Map<String, ExampleObject>? = null,
    val example: JsonElement? = null,
//    val content: ContentObject?
    val xProperties: Map<String, JsonElement>? = null,
) : HeaderOrReferenceObject

@Serializable
data class ParameterObject(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: String? = null, // "matrix" | "label" | "form" | "simple" | "spaceDelimited" | "pipeDelimited" | "deepObject"
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: SchemaOrReferenceObject? = null,
    val examples: Map<String, ExampleObject>? = null,
    val example: JsonElement? = null,
//    val content: ContentObject?
    val name: String,
    val `in`: ParameterLocation,
    val xProperties: Map<String, JsonElement>? = null,
) : ParameterOrReferenceObject

enum class ParameterLocation {
    @SerialName("query")
    QUERY,
    @SerialName("header")
    HEADER,
    @SerialName("path")
    PATH,
    @SerialName("cookie")
    COOKIE,
}

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
    val externalValue: String? = null,
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
    val schemas: Map<String, SchemaOrReferenceObject>? = null,
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
    val url: String,
)

@Serializable
data class BooleanObject(
    val value: Boolean
) : SchemaOrReferenceOrBooleanObject

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

    val xProperties: Map<String, JsonElement>? = null
) : SchemaOrReferenceObject, SchemaOrReferenceOrBooleanObject

@Serializable
data class ReferenceObject(
    @SerialName("\$ref")
    val ref: String
) : SchemaOrReferenceObject, SchemaOrReferenceOrBooleanObject, ResponseOrReferenceObject, HeaderOrReferenceObject,
    CallbackOrReferenceObject, LinkOrReferenceObject, ParameterOrReferenceObject


object CallbacksObjectSerializer : KSerializer<CallbacksObject> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), PathItemObject.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: CallbacksObject) {
        val serializer = MapSerializer(String.serializer(), PathItemObject.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): CallbacksObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return CallbacksObject(tree.mapValues {
            input.json.decodeFromJsonElement(
                PathItemObject.serializer(),
                it.value
            )
        }.entries)
    }
}

object LinksObjectSerializer : KSerializer<LinksObject> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), LinkOrReferenceObject.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: LinksObject) {
        val serializer = MapSerializer(String.serializer(), LinkOrReferenceObject.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, LinkOrReferenceObject>)
    }

    override fun deserialize(decoder: Decoder): LinksObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return LinksObject(tree.mapValues {
            input.json.decodeFromJsonElement(
                LinkOrReferenceObject.serializer(),
                it.value
            )
        }.entries)
    }
}

object ResponseOrReferenceObjectSerializer : KSerializer<ResponseOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("ResponseOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: ResponseOrReferenceObject) {
        val serializer = when(value) {
            is ResponseObject -> ResponseObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<ResponseOrReferenceObject>
        encoder.encodeSerializableValue(serializer , value)
    }

    override fun deserialize(decoder: Decoder): ResponseOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(ResponseObject.serializer(), tree)
        }
    }
}

object LinkOrReferenceObjectSerializer : KSerializer<LinkOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("LinkOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: LinkOrReferenceObject) {
        val serializer = when(value) {
            is LinkObject -> LinkObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<LinkOrReferenceObject>
        encoder.encodeSerializableValue(serializer , value)
    }

    override fun deserialize(decoder: Decoder): LinkOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(LinkOrReferenceObject.serializer(), tree)
        }
    }
}

object CallbackOrReferenceObjectSerializer : KSerializer<CallbackOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("CallbackOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: CallbackOrReferenceObject) {
        val serializer = when(value) {
            is CallbacksObject -> CallbacksObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<CallbackOrReferenceObject>
        encoder.encodeSerializableValue(serializer , value)
    }

    override fun deserialize(decoder: Decoder): CallbackOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(CallbacksObject.serializer(), tree)
        }
    }
}

object SchemaOrReferenceObjectSerializer : KSerializer<SchemaOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("SchemaOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SchemaOrReferenceObject) {
        val serializer = when(value) {
            is SchemaObject -> SchemaObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<SchemaOrReferenceObject>
        encoder.encodeSerializableValue(serializer , value)
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
        val serializer = when(value) {
            is HeaderObject -> HeaderObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<HeaderOrReferenceObject>
        encoder.encodeSerializableValue(serializer , value)
    }

    override fun deserialize(decoder: Decoder): HeaderOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(HeaderObject.serializer(), tree)
        }
    }
}

object ParameterOrReferenceObjectSerializer : KSerializer<ParameterOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("ParameterOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: ParameterOrReferenceObject) {
        val serializer = when(value) {
            is ParameterObject -> ParameterObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<ParameterOrReferenceObject>
        encoder.encodeSerializableValue(serializer , value)
    }

    override fun deserialize(decoder: Decoder): ParameterOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(ParameterObject.serializer(), tree)
        }
    }
}

object SchemaOrReferenceOrBooleanObjectSerializer : KSerializer<SchemaOrReferenceOrBooleanObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("SchemaOrReferenceOrBooleanObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SchemaOrReferenceOrBooleanObject) {
        val serializer = when (value) {
            is BooleanObject -> Boolean.serializer()
            is SchemaObject -> SchemaObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<SchemaOrReferenceOrBooleanObject>
        when (value) {
            is BooleanObject -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): SchemaOrReferenceOrBooleanObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> BooleanObject(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
                else -> input.json.decodeFromJsonElement(SchemaObject.serializer(), tree)
            }
        }
    }
}
