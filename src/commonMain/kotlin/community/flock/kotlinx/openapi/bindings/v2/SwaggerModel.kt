@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.common.CallbackOrReference
import community.flock.kotlinx.openapi.bindings.common.CommonModel
import community.flock.kotlinx.openapi.bindings.common.EncodingProperty
import community.flock.kotlinx.openapi.bindings.common.ExternalDocumentation
import community.flock.kotlinx.openapi.bindings.common.HeaderOrReference
import community.flock.kotlinx.openapi.bindings.common.InfoObject
import community.flock.kotlinx.openapi.bindings.common.LinkOrReference
import community.flock.kotlinx.openapi.bindings.common.MediaType
import community.flock.kotlinx.openapi.bindings.common.MediaTypeObject
import community.flock.kotlinx.openapi.bindings.common.Operation
import community.flock.kotlinx.openapi.bindings.common.ParameterOrReference
import community.flock.kotlinx.openapi.bindings.common.Path
import community.flock.kotlinx.openapi.bindings.common.PathItem
import community.flock.kotlinx.openapi.bindings.common.Ref
import community.flock.kotlinx.openapi.bindings.common.RequestBody
import community.flock.kotlinx.openapi.bindings.common.RequestBodyOrReference
import community.flock.kotlinx.openapi.bindings.common.ResponseOrReference
import community.flock.kotlinx.openapi.bindings.common.SchemaOrReference
import community.flock.kotlinx.openapi.bindings.common.SchemaOrReferenceOrBoolean
import community.flock.kotlinx.openapi.bindings.common.SecuritySchemeOrReference
import community.flock.kotlinx.openapi.bindings.common.Server
import community.flock.kotlinx.openapi.bindings.common.StatusCode
import community.flock.kotlinx.openapi.bindings.common.TagObject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = SwaggerResponseOrReferenceSerializer::class)
sealed interface SwaggerResponseOrReference : ResponseOrReference

@Serializable(with = SwaggerHeaderOrReferenceSerializer::class)
sealed interface SwaggerHeaderOrReference : HeaderOrReference

@Serializable(with = SwaggerParameterOrReferenceSerializer::class)
sealed interface SwaggerParameterOrReference : ParameterOrReference

@Serializable(with = SwaggerSchemaOrReferenceSerializer::class)
sealed interface SwaggerSchemaOrReference : SchemaOrReference

@Serializable(with = SwaggerSchemaOrReferenceOrBooleanSerializer::class)
sealed interface SwaggerSchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = SwaggerCallbackOrReferenceSerializer::class)
sealed interface SwaggerCallbackOrReference : CallbackOrReference

@Serializable(with = SwaggerLinkOrReferenceSerializer::class)
sealed interface SwaggerLinkOrReference : LinkOrReference

@Serializable(with = SwaggerRequestBodyOrReferenceSerializer::class)
sealed interface SwaggerRequestBodyOrReference : RequestBodyOrReference

@Serializable(with = SwaggerSecuritySchemeOrReferenceSerializer::class)
sealed interface SwaggerSecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class SwaggerParameterLocation {
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
enum class SwaggerType {
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
enum class SwaggerSecuritySchemeType {
    @SerialName("basic")
    BASIC,

    @SerialName("apiKey")
    API_KEY,

    @SerialName("oauth2")
    OAUTH2,
}

interface SwaggerBase {
    val type: SwaggerType?
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
data class SwaggerModel(
    val swagger: String,
    val host: String? = null,
    val basePath: String? = null,
    val schemes: List<String>? = null,
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val definitions: Map<String, SwaggerSchemaOrReference>? = null,
    val parameters: Map<String, SwaggerParameter>? = null,
    val responses: Map<String, SwaggerResponse>? = null,
    val securityDefinitions: Map<String, SwaggerSecurityScheme>? = null,
    override val info: InfoObject,
    override val paths: Map<Path, SwaggerPathItem>,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : CommonModel

@Serializable
data class SwaggerPathItem(
    val parameters: List<SwaggerParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: SwaggerOperation? = null,
    override val put: SwaggerOperation? = null,
    override val post: SwaggerOperation? = null,
    override val delete: SwaggerOperation? = null,
    override val options: SwaggerOperation? = null,
    override val head: SwaggerOperation? = null,
    override val patch: SwaggerOperation? = null,
    override val trace: SwaggerOperation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class SwaggerOperation(
    val consumes: List<String>? = null,
    val produces: List<String>? = null,
    val parameters: List<SwaggerParameterOrReference>? = null,
    val requestBody: SwaggerRequestBodyOrReference? = null,
    val responses: Map<StatusCode, SwaggerResponseOrReference>? = null,
    val callbacks: Map<String, SwaggerCallbackOrReference>? = null,
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
data class SwaggerRequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, SwaggerMediaType>? = null,
    override val required: Boolean,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    SwaggerRequestBodyOrReference

@Serializable(with = SwaggerCallbacksSerializer::class)
class SwaggerCallbacks(override val entries: Set<Map.Entry<String, SwaggerPathItem>>) :
    AbstractMap<String, SwaggerPathItem>(),
    SwaggerCallbackOrReference

@Serializable(with = SwaggerLinksSerializer::class)
class SwaggerLinks(
    override val entries: Set<Map.Entry<String, SwaggerLinkOrReference>>,
) : AbstractMap<String, SwaggerLinkOrReference>()

@Serializable
data class SwaggerLink(
    val operationRef: String? = null,
    val operationId: String? = null,
    val parameters: Map<String, JsonElement>? = null,
    val requestBody: JsonElement? = null,
    val description: String? = null,
    val server: Server? = null,
) : SwaggerLinkOrReference

@Serializable
data class SwaggerResponse(
    val description: String? = null,
    val schema: SwaggerSchemaOrReference? = null,
    val headers: Map<String, SwaggerHeaderOrReference>? = null,
    val links: SwaggerLinks? = null,
    val examples: Map<String, JsonElement>? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : SwaggerResponseOrReference

@Serializable
data class SwaggerHeader(
    val description: String? = null,
    override val type: SwaggerType,
    override val format: String? = null,
    val items: SwaggerSchemaOrReference? = null,
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
) : SwaggerBase,
    SwaggerHeaderOrReference

@Serializable
data class SwaggerParameter(
    val name: String,
    val `in`: SwaggerParameterLocation,
    val description: String? = null,
    val required: Boolean? = null,
    val schema: SwaggerSchemaOrReference? = null,
    override val type: SwaggerType? = null,
    val items: SwaggerSchemaOrReference? = null,
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
) : SwaggerBase,
    SwaggerParameterOrReference

@Serializable
data class SwaggerMediaType(
    override val schema: SwaggerSchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, SwaggerEncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class SwaggerEncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, SwaggerHeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class SwaggerSecurityScheme(
    val type: SwaggerSecuritySchemeType,
    val description: String? = null,
    val name: String? = null,
    val `in`: String? = null,
    val flow: String? = null,
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val scopes: Map<String, String>? = null,
) : SwaggerSecuritySchemeOrReference

@Serializable
data class SwaggerBoolean(
    val value: Boolean,
) : SwaggerSchemaOrReferenceOrBoolean

@Serializable
data class SwaggerSchema(
    val discriminator: String? = null,
    val readOnly: Boolean? = null,
    val xml: SwaggerXml? = null,
    val externalDocs: ExternalDocumentation? = null,
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
    override val type: SwaggerType? = null,

    val items: SwaggerSchemaOrReference? = null,
    val allOf: List<SwaggerSchemaOrReference>? = null,
    val properties: Map<String, SwaggerSchemaOrReference>? = null,
    val additionalProperties: SwaggerSchemaOrReferenceOrBoolean? = null,

    val xProperties: Map<String, JsonElement>? = null,
) : SwaggerBase,
    SwaggerSchemaOrReference,
    SwaggerSchemaOrReferenceOrBoolean

@Serializable
data class SwaggerXml(
    val name: String? = null,
    val namespace: String? = null,
    val prefix: String? = null,
    val attribute: Boolean? = null,
    val wrapped: Boolean? = null,
)

@Serializable
data class SwaggerReference(
    @SerialName("\$ref")
    val ref: Ref,
    val xml: SwaggerXml? = null,
) : SwaggerHeaderOrReference,
    SwaggerSchemaOrReference,
    SwaggerSchemaOrReferenceOrBoolean,
    SwaggerResponseOrReference,
    SwaggerCallbackOrReference,
    SwaggerLinkOrReference,
    SwaggerParameterOrReference,
    SwaggerRequestBodyOrReference,
    SwaggerSecuritySchemeOrReference
