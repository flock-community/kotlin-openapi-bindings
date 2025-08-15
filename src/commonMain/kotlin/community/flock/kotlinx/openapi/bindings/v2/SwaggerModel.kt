@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.common.CommonModel
import community.flock.kotlinx.openapi.bindings.common.EncodingPropertyObject
import community.flock.kotlinx.openapi.bindings.common.ExternalDocumentationObject
import community.flock.kotlinx.openapi.bindings.common.HeaderOrReferenceObject
import community.flock.kotlinx.openapi.bindings.common.InfoObject
import community.flock.kotlinx.openapi.bindings.common.MediaType
import community.flock.kotlinx.openapi.bindings.common.MediaTypeObject
import community.flock.kotlinx.openapi.bindings.common.OperationObject
import community.flock.kotlinx.openapi.bindings.common.Path
import community.flock.kotlinx.openapi.bindings.common.PathItemObject
import community.flock.kotlinx.openapi.bindings.common.Ref
import community.flock.kotlinx.openapi.bindings.common.RequestBodyObject
import community.flock.kotlinx.openapi.bindings.common.SchemaOrReferenceObject
import community.flock.kotlinx.openapi.bindings.common.ServerObject
import community.flock.kotlinx.openapi.bindings.common.StatusCode
import community.flock.kotlinx.openapi.bindings.common.TagObject
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

@Serializable(with = SwaggerHeaderOrReferenceObjectSerializer::class)
sealed interface SwaggerHeaderOrReferenceObject : HeaderOrReferenceObject

@Serializable(with = ParameterOrReferenceObjectSerializer::class)
sealed interface ParameterOrReferenceObject

@Serializable(with = SwaggerSchemaOrReferenceObjectSerializer::class)
sealed interface SwaggerSchemaOrReferenceObject : SchemaOrReferenceObject

@Serializable(with = SchemaOrReferenceOrBooleanObjectSerializer::class)
sealed interface SchemaOrReferenceOrBooleanObject

@Serializable(with = CallbackOrReferenceObjectSerializer::class)
sealed interface CallbackOrReferenceObject

@Serializable(with = LinkOrReferenceObjectSerializer::class)
sealed interface LinkOrReferenceObject

@Serializable(with = RequestBodyOrReferenceObjectSerializer::class)
sealed interface RequestBodyOrReferenceObject

@Serializable(with = SecuritySchemeOrReferenceObjectSerializer::class)
sealed interface SecuritySchemeOrReferenceObject

@Serializable
enum class ParameterLocation {
    @SerialName("query")
    QUERY,

    @SerialName("header")
    HEADER,

    @SerialName("path")
    PATH,

    @SerialName("formData")
    FORM_DATA,

    @SerialName("body")
    BODY,
}

@Serializable
enum class Type {
    @SerialName("string")
    STRING,

    @SerialName("number")
    NUMBER,

    @SerialName("integer")
    INTEGER,

    @SerialName("boolean")
    BOOLEAN,

    @SerialName("array")
    ARRAY,

    @SerialName("object")
    OBJECT,

    @SerialName("file")
    FILE,
}

@Serializable
enum class SecuritySchemeType {
    @SerialName("basic")
    BASIC,

    @SerialName("apiKey")
    API_KEY,

    @SerialName("oauth2")
    OAUTH2,
}

interface BaseObject {
    val type: Type?
    val format: String?
    val pattern: String?
    val maximum: Double?
    val exclusiveMaximum: Boolean?
    val minimum: Double?
    val exclusiveMinimum: Boolean?
    val maxLength: Int?
    val minLength: Int?
    val maxItems: Int?
    val minItems: Int?
}

@Serializable
data class SwaggerObject(
    val swagger: String,
    val host: String? = null,
    val basePath: String? = null,
    val schemes: List<String>? = null,
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val definitions: Map<String, SwaggerSchemaOrReferenceObject>? = null,
    val parameters: Map<String, ParameterObject>? = null,
    val responses: Map<String, ResponseObject>? = null,
    val securityDefinitions: Map<String, SecuritySchemeObject>? = null,
    override val info: InfoObject,
    override val paths: Map<Path, SwaggerPathItemObject>,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentationObject? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : CommonModel

@Serializable
data class SwaggerPathItemObject(
    val parameters: List<ParameterOrReferenceObject>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: SwaggerOperationObject? = null,
    override val put: SwaggerOperationObject? = null,
    override val post: SwaggerOperationObject? = null,
    override val delete: SwaggerOperationObject? = null,
    override val options: SwaggerOperationObject? = null,
    override val head: SwaggerOperationObject? = null,
    override val patch: SwaggerOperationObject? = null,
    override val trace: SwaggerOperationObject? = null,
    override val servers: List<ServerObject>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItemObject

@Serializable
data class SwaggerOperationObject(
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val parameters: List<ParameterOrReferenceObject>? = null,
    val requestBody: RequestBodyOrReferenceObject? = null,
    val responses: Map<StatusCode, ResponseOrReferenceObject>? = null,
    val callbacks: Map<String, CallbackOrReferenceObject>? = null,
    override val tags: List<String?>? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val externalDocs: ExternalDocumentationObject? = null,
    override val operationId: String? = null,
    override val deprecated: Boolean? = null,
    override val security: List<Map<String, List<String>>>? = null,
    override val servers: List<ServerObject>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : OperationObject

@Serializable
data class SwaggerRequestBodyObject(
    override val description: String? = null,
    override val content: Map<MediaType, SwaggerMediaTypeObject>? = null,
    override val required: Boolean,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBodyObject,
    RequestBodyOrReferenceObject

@Serializable(with = CallbacksObjectSerializer::class)
class CallbacksObject(override val entries: Set<Map.Entry<String, SwaggerPathItemObject>>) :
    AbstractMap<String, SwaggerPathItemObject>(),
    CallbackOrReferenceObject

@Serializable(with = LinksObjectSerializer::class)
class LinksObject(
    override val entries: Set<Map.Entry<String, LinkOrReferenceObject>>,
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
    val schema: SwaggerSchemaOrReferenceObject? = null,
    val headers: Map<String, SwaggerHeaderOrReferenceObject>? = null,
    val links: LinksObject? = null,
    val examples: Map<String, JsonElement>? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : ResponseOrReferenceObject

@Serializable
data class HeaderObject(
    val description: String? = null,
    override val type: Type,
    override val format: String? = null,
    val items: SwaggerSchemaOrReferenceObject? = null,
    val collectionFormat: String? = null,
    val default: JsonElement? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Boolean? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Boolean? = null,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    override val pattern: String? = null,
    override val maxItems: Int? = null,
    override val minItems: Int? = null,
    val uniqueItems: Boolean? = null,
    val enum: List<JsonPrimitive>? = null,
    val multipleOf: Int? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : BaseObject,
    SwaggerHeaderOrReferenceObject

@Serializable
data class ParameterObject(
    val name: String,
    val `in`: ParameterLocation,
    val description: String? = null,
    val required: Boolean? = null,
    val schema: SwaggerSchemaOrReferenceObject? = null,
    override val type: Type? = null,
    val items: SwaggerSchemaOrReferenceObject? = null,
    override val format: String? = null,
    val allowEmptyValue: Boolean? = null,
    val collectionFormat: String? = null,
    val default: JsonElement? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Boolean? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Boolean? = null,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    override val pattern: String? = null,
    override val maxItems: Int? = null,
    override val minItems: Int? = null,
    val uniqueItems: Boolean? = null,
    val enum: List<JsonPrimitive>? = null,
    val multipleOf: Int? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : BaseObject,
    ParameterOrReferenceObject

@Serializable
data class SwaggerMediaTypeObject(
    override val schema: SwaggerSchemaOrReferenceObject? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, SwaggerEncodingPropertyObject>? = null,
) : MediaTypeObject

@Serializable
data class SwaggerEncodingPropertyObject(
    override val contentType: String? = null,
    override val headers: Map<String, SwaggerHeaderOrReferenceObject>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingPropertyObject

@Serializable
data class SecuritySchemeObject(
    val type: SecuritySchemeType,
    val description: String? = null,
    val name: String? = null,
    val `in`: String? = null,
    val flow: String? = null,
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val scopes: Map<String, String>? = null,
) : SecuritySchemeOrReferenceObject

@Serializable
data class BooleanObject(
    val value: Boolean,
) : SchemaOrReferenceOrBooleanObject

@Serializable
data class SchemaObject(
    val discriminator: String? = null,
    val readOnly: Boolean? = null,
    val xml: XmlObject? = null,
    val externalDocs: ExternalDocumentationObject? = null,
    val example: JsonElement? = null,
    override val format: String? = null,
    val title: String? = null,
    val description: String? = null,
    val default: JsonElement? = null,
    val multipleOf: Double? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Boolean? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Boolean? = null,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    override val pattern: String? = null,
    override val maxItems: Int? = null,
    override val minItems: Int? = null,
    val uniqueItems: Boolean? = null,
    val maxProperties: Int? = null,
    val minProperties: Int? = null,
    val required: List<String>? = null,
    val enum: List<JsonPrimitive>? = null,
    override val type: Type? = null,

    val items: SwaggerSchemaOrReferenceObject? = null,
    val allOf: List<SwaggerSchemaOrReferenceObject>? = null,
    val properties: Map<String, SwaggerSchemaOrReferenceObject>? = null,
    val additionalProperties: SchemaOrReferenceOrBooleanObject? = null,

    val xProperties: Map<String, JsonElement>? = null,
) : BaseObject,
    SwaggerSchemaOrReferenceObject,
    SchemaOrReferenceOrBooleanObject

@Serializable
data class XmlObject(
    val name: String? = null,
    val namespace: String? = null,
    val prefix: String? = null,
    val attribute: Boolean? = null,
    val wrapped: Boolean? = null,
)

@Serializable
data class ReferenceObject(
    @SerialName("\$ref")
    val ref: Ref,
    val xml: XmlObject? = null,
) : SwaggerHeaderOrReferenceObject,
    SwaggerSchemaOrReferenceObject,
    SchemaOrReferenceOrBooleanObject,
    ResponseOrReferenceObject,
    CallbackOrReferenceObject,
    LinkOrReferenceObject,
    ParameterOrReferenceObject,
    RequestBodyOrReferenceObject,
    SecuritySchemeOrReferenceObject

object CallbacksObjectSerializer : KSerializer<CallbacksObject> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), SwaggerPathItemObject.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: CallbacksObject) {
        val serializer = MapSerializer(String.serializer(), SwaggerPathItemObject.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): CallbacksObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return CallbacksObject(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    SwaggerPathItemObject.serializer(),
                    it.value,
                )
            }.entries,
        )
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
        return LinksObject(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    LinkOrReferenceObject.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object ResponseOrReferenceObjectSerializer : KSerializer<ResponseOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("ResponseOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: ResponseOrReferenceObject) {
        val serializer = when (value) {
            is ResponseObject -> ResponseObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<ResponseOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
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
        val serializer = when (value) {
            is LinkObject -> LinkObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<LinkOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): LinkOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(LinkObject.serializer(), tree)
        }
    }
}

object CallbackOrReferenceObjectSerializer : KSerializer<CallbackOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("CallbackOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: CallbackOrReferenceObject) {
        val serializer = when (value) {
            is CallbacksObject -> CallbacksObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<CallbackOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
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

object SwaggerSchemaOrReferenceObjectSerializer : KSerializer<SwaggerSchemaOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("SchemaOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerSchemaOrReferenceObject) {
        val serializer = when (value) {
            is SchemaObject -> SchemaObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<SwaggerSchemaOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerSchemaOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SchemaObject.serializer(), tree)
        }
    }
}

object SwaggerHeaderOrReferenceObjectSerializer : KSerializer<SwaggerHeaderOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("HeaderOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerHeaderOrReferenceObject) {
        val serializer = when (value) {
            is HeaderObject -> HeaderObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<SwaggerHeaderOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerHeaderOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(HeaderObject.serializer(), tree)
        }
    }
}

object ParameterOrReferenceObjectSerializer : KSerializer<ParameterOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("ParameterOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: ParameterOrReferenceObject) {
        val serializer = when (value) {
            is ParameterObject -> ParameterObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<ParameterOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
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

object RequestBodyOrReferenceObjectSerializer : KSerializer<RequestBodyOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("RequestBodyOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: RequestBodyOrReferenceObject) {
        val serializer = when (value) {
            is SwaggerRequestBodyObject -> SwaggerRequestBodyObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<RequestBodyOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): RequestBodyOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerRequestBodyObject.serializer(), tree)
        }
    }
}

object SecuritySchemeOrReferenceObjectSerializer : KSerializer<SecuritySchemeOrReferenceObject> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("SecuritySchemeOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SecuritySchemeOrReferenceObject) {
        val serializer = when (value) {
            is SecuritySchemeObject -> SecuritySchemeObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<SecuritySchemeOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SecuritySchemeOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SecuritySchemeObject.serializer(), tree)
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
