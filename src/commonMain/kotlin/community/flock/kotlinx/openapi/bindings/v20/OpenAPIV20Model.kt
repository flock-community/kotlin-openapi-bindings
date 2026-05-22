@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = OpenAPIV20ResponseOrReferenceSerializer::class)
sealed interface OpenAPIV20ResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIV20HeaderOrReferenceSerializer::class)
sealed interface OpenAPIV20HeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIV20ParameterOrReferenceSerializer::class)
sealed interface OpenAPIV20ParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPIV20SchemaOrReferenceSerializer::class)
sealed interface OpenAPIV20SchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPIV20SchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPIV20SchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPIV20CallbackOrReferenceSerializer::class)
sealed interface OpenAPIV20CallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPIV20LinkOrReferenceSerializer::class)
sealed interface OpenAPIV20LinkOrReference : LinkOrReference

@Serializable(with = OpenAPIV20RequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIV20RequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPIV20SecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPIV20SecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class OpenAPIV20ParameterLocation {
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
enum class OpenAPIV20Type {
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
enum class OpenAPIV20SecuritySchemeType {
    @SerialName("basic")
    BASIC,

    @SerialName("apiKey")
    API_KEY,

    @SerialName("oauth2")
    OAUTH2,
}

interface OpenAPIV20Base {
    val type: OpenAPIV20Type?
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
data class OpenAPIV20Model(
    override val swagger: String,
    override val host: String? = null,
    override val basePath: String? = null,
    override val schemes: List<String>? = null,
    override val consumes: List<String>? = null,
    override val produces: List<String>? = null,
    override val definitions: Map<String, OpenAPIV20SchemaOrReference>? = null,
    override val parameters: Map<String, OpenAPIV20Parameter>? = null,
    override val responses: Map<String, OpenAPIV20Response>? = null,
    override val securityDefinitions: Map<String, OpenAPIV20SecurityScheme>? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIV20PathItem>,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIV2Model

@Serializable
data class OpenAPIV20PathItem(
    override val parameters: List<OpenAPIV20ParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIV20Operation? = null,
    override val put: OpenAPIV20Operation? = null,
    override val post: OpenAPIV20Operation? = null,
    override val delete: OpenAPIV20Operation? = null,
    override val options: OpenAPIV20Operation? = null,
    override val head: OpenAPIV20Operation? = null,
    override val patch: OpenAPIV20Operation? = null,
    override val trace: OpenAPIV20Operation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class OpenAPIV20Operation(
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    override val parameters: List<OpenAPIV20ParameterOrReference>? = null,
    override val requestBody: OpenAPIV20RequestBodyOrReference? = null,
    override val responses: Map<StatusCode, OpenAPIV20ResponseOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV20CallbackOrReference>? = null,
    override val tags: List<String?>? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val operationId: String? = null,
    override val deprecated: Boolean? = null,
    override val security: List<Map<String, List<String>>>? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Operation

@Serializable
data class OpenAPIV20RequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIV20MediaType>? = null,
    override val required: Boolean,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIV20RequestBodyOrReference

@Serializable(with = OpenAPIV20CallbacksSerializer::class)
class OpenAPIV20Callbacks(override val entries: Set<Map.Entry<String, OpenAPIV20PathItem>>) :
    AbstractMap<String, OpenAPIV20PathItem>(),
    OpenAPIV20CallbackOrReference

@Serializable(with = OpenAPIV20LinksSerializer::class)
class OpenAPIV20Links(override val entries: Set<Map.Entry<String, OpenAPIV20LinkOrReference>>) : AbstractMap<String, OpenAPIV20LinkOrReference>()

@Serializable
data class OpenAPIV20Link(
    override val operationRef: String? = null,
    override val operationId: String? = null,
    override val parameters: Map<String, JsonElement>? = null,
    override val requestBody: JsonElement? = null,
    override val description: String? = null,
    override val server: Server? = null,
) : Link,
    OpenAPIV20LinkOrReference

@Serializable
data class OpenAPIV20Response(
    val schema: OpenAPIV20SchemaOrReference? = null,
    val examples: Map<String, JsonElement>? = null,
    override val description: String? = null,
    override val headers: Map<String, OpenAPIV20HeaderOrReference>? = null,
    override val links: OpenAPIV20Links? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Response,
    OpenAPIV20ResponseOrReference

@Serializable
data class OpenAPIV20Header(
    override val description: String? = null,
    override val type: OpenAPIV20Type,
    override val format: String? = null,
    val items: OpenAPIV20SchemaOrReference? = null,
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
    override val xProperties: Map<String, JsonElement>? = null,
) : Header,
    OpenAPIV20Base,
    OpenAPIV20HeaderOrReference

@Serializable
data class OpenAPIV20Parameter(
    val description: String? = null,
    val required: Boolean? = null,
    override val type: OpenAPIV20Type? = null,
    val items: OpenAPIV20SchemaOrReference? = null,
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
    val `in`: OpenAPIV20ParameterLocation,
    override val schema: OpenAPIV20SchemaOrReference? = null,
    override val name: String,
    override val xProperties: Map<String, JsonElement>? = null,
) : Parameter,
    OpenAPIV20Base,
    OpenAPIV20ParameterOrReference

@Serializable
data class OpenAPIV20MediaType(
    override val schema: OpenAPIV20SchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIV20EncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIV20EncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIV20HeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIV20SecurityScheme(
    val type: OpenAPIV20SecuritySchemeType,
    val flow: String? = null,
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val scopes: Map<String, String>? = null,
    override val description: String? = null,
    override val name: String? = null,
    override val `in`: String? = null,
) : SecurityScheme,
    OpenAPIV20SecuritySchemeOrReference

@Serializable
data class OpenAPIV20Boolean(
    override val value: Boolean,
) : BooleanValue,
    OpenAPIV20SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV20Schema(
    val discriminator: String? = null,
    override val type: OpenAPIV20Type? = null,
    override val example: JsonElement? = null,
    override val readOnly: Boolean? = null,
    override val xml: XML? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val title: String? = null,
    override val description: String? = null,
    override val default: JsonElement? = null,
    override val multipleOf: Double? = null,
    override val uniqueItems: Boolean? = null,
    override val maxProperties: Int? = null,
    override val minProperties: Int? = null,
    override val required: List<String>? = null,
    override val enum: List<JsonPrimitive>? = null,
    override val items: OpenAPIV20SchemaOrReference? = null,
    override val allOf: List<OpenAPIV20SchemaOrReference>? = null,
    override val properties: Map<String, OpenAPIV20SchemaOrReference>? = null,
    override val additionalProperties: OpenAPIV20SchemaOrReferenceOrBoolean? = null,
    override val xProperties: Map<String, JsonElement>? = null,
    override val format: String? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Boolean? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Boolean? = null,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    override val pattern: String? = null,
    override val maxItems: Int? = null,
    override val minItems: Int? = null,
) : Schema,
    OpenAPIV20Base,
    OpenAPIV20SchemaOrReference,
    OpenAPIV20SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV20Reference(
    @SerialName("\$ref")
    override val ref: Ref,
    val xml: XML? = null,
) : Reference,
    OpenAPIV20HeaderOrReference,
    OpenAPIV20SchemaOrReference,
    OpenAPIV20SchemaOrReferenceOrBoolean,
    OpenAPIV20ResponseOrReference,
    OpenAPIV20CallbackOrReference,
    OpenAPIV20LinkOrReference,
    OpenAPIV20ParameterOrReference,
    OpenAPIV20RequestBodyOrReference,
    OpenAPIV20SecuritySchemeOrReference
