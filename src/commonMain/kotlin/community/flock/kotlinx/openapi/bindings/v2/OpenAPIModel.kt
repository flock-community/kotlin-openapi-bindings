@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v2

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
import kotlin.jvm.JvmInline

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

@Serializable(with = RequestBodyOrReferenceObjectSerializer::class)
sealed interface RequestBodyOrReferenceObject

@Serializable(with = SecuritySchemeOrReferenceObjectSerializer::class)
sealed interface SecuritySchemeOrReferenceObject

@JvmInline
@Serializable
value class Ref(val value: String)

@JvmInline
@Serializable
value class Path(val value: String)

@JvmInline
@Serializable
value class MediaType(val value: String)

@JvmInline
@Serializable
value class StatusCode(val value: String)

enum class Style {
    @SerialName("matrix")
    MATRIX,

    @SerialName("label")
    LABEL,

    @SerialName("form")
    FORM,

    @SerialName("simple")
    SIMPLE,

    @SerialName("spaceDelimited")
    SPACE_DELIMITED,

    @SerialName("pipeDelimited")
    PIPE_DELIMITED,

    @SerialName("deepObject")
    DEEP_OBJECT
}

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

enum class SecuritySchemeType {
    @SerialName("basic")
    BASIC,

    @SerialName("apiKey")
    API_KEY,

    @SerialName("oauth2")
    OAUTH2,
}

@Serializable
data class SwaggerObject(
    val swagger: String,
    val info: InfoObject,
    val host: String? = null,
    val basePath: String? = null,
    val schemes: List<String>? = null,
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val paths: Map<Path, PathItemObject>,
    val definitions: Map<String, SchemaObject>? = null,
    val parameters: Map<String, ParameterObject>? = null,
    val responses: Map<String, ResponseObject>? = null,
    val securityDefinitions: Map<String, SecuritySchemeObject>? = null,
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
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val parameters: List<ParameterOrReferenceObject>? = null,
    val responses: Map<StatusCode, ResponseOrReferenceObject>? = null,
    val callbacks: Map<String, CallbackOrReferenceObject>? = null,
    val deprecated: Boolean? = null,
    val security: List<Map<String, List<String>>>? = null,
    val servers: List<ServerObject>? = null,
    val xProperties: Map<String, JsonElement>? = null
)

@Serializable
data class RequestBodyObject(
    val description: String? = null,
    val content: Map<MediaType, MediaTypeObject>? = null,
    val required: Boolean
) : RequestBodyOrReferenceObject

@Serializable(with = CallbacksObjectSerializer::class)
class CallbacksObject(override val entries: Set<Map.Entry<String, PathItemObject>>) : CallbackOrReferenceObject,
    AbstractMap<String, PathItemObject>()

@Serializable(with = LinksObjectSerializer::class)
class LinksObject(
    override val entries: Set<Map.Entry<String, LinkOrReferenceObject>>
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
    val schema: SchemaOrReferenceObject? = null,
    val headers: Map<String, HeaderOrReferenceObject>? = null,
    val links: LinksObject? = null,
    val examples: Map<String, JsonElement>? = null,
    val xProperties: Map<String, JsonElement>? = null
) : ResponseOrReferenceObject

@Serializable
data class HeaderObject(
    val description: String? = null,
    val type: String,
    val format: String? = null,
    val items: SchemaOrReferenceObject? = null,
    val collectionFormat: String? = null,
    val default: JsonElement? = null,
    val maximum: Int? = null,
    val exclusiveMaximum: Boolean? = null,
    val minimum: Int? = null,
    val exclusiveMinimum: Boolean? = null,
    val maxLength: Int? = null,
    val minLength: Int? = null,
    val pattern: String? = null,
    val maxItems: Int? = null,
    val minItems: Int? = null,
    val uniqueItems: Boolean? = null,
    val enum: JsonElement? = null,
    val multipleOf: Int? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : HeaderOrReferenceObject

@Serializable
data class ParameterObject(
    val name: String,
    val `in`: ParameterLocation,
    val description: String? = null,
    val required: Boolean? = null,
    val schema: SchemaOrReferenceObject? = null,
    val type: Type? = null,
    val items: SchemaOrReferenceObject? = null,
    val format: String? = null,
    val allowEmptyValue: Boolean? = null,
    val collectionFormat: String? = null,
    val default: JsonElement? = null,
    val maximum: Int? = null,
    val exclusiveMaximum: Boolean? = null,
    val minimum: Int? = null,
    val exclusiveMinimum: Boolean? = null,
    val maxLength: Int? = null,
    val minLength: Int? = null,
    val pattern: String? = null,
    val maxItems: Int? = null,
    val minItems: Int? = null,
    val uniqueItems: Boolean? = null,
    val enum: JsonElement? = null,
    val multipleOf: Int? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : ParameterOrReferenceObject

@Serializable
data class MediaTypeObject(
    val schema: SchemaOrReferenceObject? = null,
    val examples: Map<String, JsonElement>? = null,
    val example: JsonElement? = null,
    val encoding: Map<String, EncodingPropertyObject>? = null
)

@Serializable
data class EncodingPropertyObject(
    val contentType: String? = null,
    val headers: Map<String, HeaderOrReferenceObject>? = null,
    val style: String? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
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
    val discriminator: DiscriminatorObject? = null,
    val readOnly: Boolean? = null,
    val xml: XmlObject? = null,
    val externalDocs: ExternalDocumentationObject? = null,
    val example: JsonElement? = null,


    val format: String? = null,
    val title: String? = null,
    val description: String? = null,
    val default: JsonElement? = null,
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
    val type: Type? = null,

    val items: SchemaOrReferenceObject? = null,
    val allOf: List<SchemaOrReferenceObject>? = null,
    val properties: Map<String, SchemaOrReferenceObject>? = null,
    val additionalProperties: SchemaOrReferenceOrBooleanObject? = null,

    val xProperties: Map<String, JsonElement>? = null
) : SchemaOrReferenceObject, SchemaOrReferenceOrBooleanObject

@Serializable
data class DiscriminatorObject(
    val propertyName: String? = null,
    val mapping: Map<String, String>
)

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
) : SchemaOrReferenceObject, SchemaOrReferenceOrBooleanObject, ResponseOrReferenceObject, HeaderOrReferenceObject,
    CallbackOrReferenceObject, LinkOrReferenceObject, ParameterOrReferenceObject,
    RequestBodyOrReferenceObject, SecuritySchemeOrReferenceObject


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

object SchemaOrReferenceObjectSerializer : KSerializer<SchemaOrReferenceObject> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor("SchemaOrReferenceObject", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SchemaOrReferenceObject) {
        val serializer = when (value) {
            is SchemaObject -> SchemaObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<SchemaOrReferenceObject>
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
        val serializer = when (value) {
            is HeaderObject -> HeaderObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<HeaderOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
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
            is RequestBodyObject -> RequestBodyObject.serializer()
            is ReferenceObject -> ReferenceObject.serializer()
        } as SerializationStrategy<RequestBodyOrReferenceObject>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): RequestBodyOrReferenceObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(ReferenceObject.serializer(), tree)
            else -> input.json.decodeFromJsonElement(RequestBodyObject.serializer(), tree)
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
