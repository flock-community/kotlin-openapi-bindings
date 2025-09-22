@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = OpenAPIV2ResponseOrReferenceSerializer::class)
sealed interface OpenAPIV2ResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIV2HeaderOrReferenceSerializer::class)
sealed interface OpenAPIV2HeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIV2ParameterOrReferenceSerializer::class)
sealed interface OpenAPIV2ParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPIV2SchemaOrReferenceSerializer::class)
sealed interface OpenAPIV2SchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPIV2SchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPIV2SchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPIV2CallbackOrReferenceSerializer::class)
sealed interface OpenAPIV2CallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPIV2LinkOrReferenceSerializer::class)
sealed interface OpenAPIV2LinkOrReference : LinkOrReference

@Serializable(with = OpenAPIV2RequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIV2RequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPIV2SecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPIV2SecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class OpenAPIV2ParameterLocation {
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
enum class OpenAPIV2Type {
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
enum class OpenAPIV2SecuritySchemeType {
    @SerialName("basic")
    BASIC,

    @SerialName("apiKey")
    API_KEY,

    @SerialName("oauth2")
    OAUTH2,
}

interface OpenAPIV2Base {
    val type: OpenAPIV2Type?
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
data class OpenAPIV2Model(
    val swagger: String,
    val host: String? = null,
    val basePath: String? = null,
    val schemes: List<String>? = null,
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val definitions: Map<String, OpenAPIV2SchemaOrReference>? = null,
    val parameters: Map<String, OpenAPIV2Parameter>? = null,
    val responses: Map<String, OpenAPIV2Response>? = null,
    val securityDefinitions: Map<String, OpenAPIV2SecurityScheme>? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIV2PathItem>,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIModel

@Serializable
data class OpenAPIV2PathItem(
    override val parameters: List<OpenAPIV2ParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIV2Operation? = null,
    override val put: OpenAPIV2Operation? = null,
    override val post: OpenAPIV2Operation? = null,
    override val delete: OpenAPIV2Operation? = null,
    override val options: OpenAPIV2Operation? = null,
    override val head: OpenAPIV2Operation? = null,
    override val patch: OpenAPIV2Operation? = null,
    override val trace: OpenAPIV2Operation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class OpenAPIV2Operation(
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    override val parameters: List<OpenAPIV2ParameterOrReference>? = null,
    override val requestBody: OpenAPIV2RequestBodyOrReference? = null,
    override val responses: Map<StatusCode, OpenAPIV2ResponseOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV2CallbackOrReference>? = null,
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
data class OpenAPIV2RequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIV2MediaType>? = null,
    override val required: Boolean,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIV2RequestBodyOrReference

@Serializable(with = OpenAPIV2CallbacksSerializer::class)
class OpenAPIV2Callbacks(override val entries: Set<Map.Entry<String, OpenAPIV2PathItem>>) :
    AbstractMap<String, OpenAPIV2PathItem>(),
    OpenAPIV2CallbackOrReference

@Serializable(with = OpenAPIV2LinksSerializer::class)
class OpenAPIV2Links(override val entries: Set<Map.Entry<String, OpenAPIV2LinkOrReference>>) : AbstractMap<String, OpenAPIV2LinkOrReference>()

@Serializable
data class OpenAPIV2Link(
    override val operationRef: String? = null,
    override val operationId: String? = null,
    override val parameters: Map<String, JsonElement>? = null,
    override val requestBody: JsonElement? = null,
    override val description: String? = null,
    override val server: Server? = null,
) : Link,
    OpenAPIV2LinkOrReference

@Serializable
data class OpenAPIV2Response(
    val schema: OpenAPIV2SchemaOrReference? = null,
    val examples: Map<String, JsonElement>? = null,
    override val description: String? = null,
    override val headers: Map<String, OpenAPIV2HeaderOrReference>? = null,
    override val links: OpenAPIV2Links? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Response,
    OpenAPIV2ResponseOrReference

@Serializable
data class OpenAPIV2Header(
    override val description: String? = null,
    override val type: OpenAPIV2Type,
    override val format: String? = null,
    val items: OpenAPIV2SchemaOrReference? = null,
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
    OpenAPIV2Base,
    OpenAPIV2HeaderOrReference

@Serializable
data class OpenAPIV2Parameter(
    val description: String? = null,
    val required: Boolean? = null,
    override val type: OpenAPIV2Type? = null,
    val items: OpenAPIV2SchemaOrReference? = null,
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
    val `in`: OpenAPIV2ParameterLocation,
    override val schema: OpenAPIV2SchemaOrReference? = null,
    override val name: String,
    override val xProperties: Map<String, JsonElement>? = null,
) : Parameter,
    OpenAPIV2Base,
    OpenAPIV2ParameterOrReference

@Serializable
data class OpenAPIV2MediaType(
    override val schema: OpenAPIV2SchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIV2EncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIV2EncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIV2HeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIV2SecurityScheme(
    val type: OpenAPIV2SecuritySchemeType,
    val flow: String? = null,
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val scopes: Map<String, String>? = null,
    override val description: String? = null,
    override val name: String? = null,
    override val `in`: String? = null,
) : SecurityScheme,
    OpenAPIV2SecuritySchemeOrReference

@Serializable
data class OpenAPIV2Boolean(
    override val value: Boolean,
) : BooleanValue,
    OpenAPIV2SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV2Schema(
    val discriminator: String? = null,
    override val type: OpenAPIV2Type? = null,
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
    override val items: OpenAPIV2SchemaOrReference? = null,
    override val allOf: List<OpenAPIV2SchemaOrReference>? = null,
    override val properties: Map<String, OpenAPIV2SchemaOrReference>? = null,
    override val additionalProperties: OpenAPIV2SchemaOrReferenceOrBoolean? = null,
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
    OpenAPIV2Base,
    OpenAPIV2SchemaOrReference,
    OpenAPIV2SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV2Reference(
    @SerialName("\$ref")
    override val ref: Ref,
    val xml: XML? = null,
) : Reference,
    OpenAPIV2HeaderOrReference,
    OpenAPIV2SchemaOrReference,
    OpenAPIV2SchemaOrReferenceOrBoolean,
    OpenAPIV2ResponseOrReference,
    OpenAPIV2CallbackOrReference,
    OpenAPIV2LinkOrReference,
    OpenAPIV2ParameterOrReference,
    OpenAPIV2RequestBodyOrReference,
    OpenAPIV2SecuritySchemeOrReference
