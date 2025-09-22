@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v3

import community.flock.kotlinx.openapi.bindings.common.BooleanValue
import community.flock.kotlinx.openapi.bindings.common.CallbackOrReference
import community.flock.kotlinx.openapi.bindings.common.CommonModel
import community.flock.kotlinx.openapi.bindings.common.EncodingProperty
import community.flock.kotlinx.openapi.bindings.common.ExampleOrReference
import community.flock.kotlinx.openapi.bindings.common.ExternalDocumentation
import community.flock.kotlinx.openapi.bindings.common.Header
import community.flock.kotlinx.openapi.bindings.common.HeaderOrReference
import community.flock.kotlinx.openapi.bindings.common.InfoObject
import community.flock.kotlinx.openapi.bindings.common.Link
import community.flock.kotlinx.openapi.bindings.common.LinkOrReference
import community.flock.kotlinx.openapi.bindings.common.MediaType
import community.flock.kotlinx.openapi.bindings.common.MediaTypeObject
import community.flock.kotlinx.openapi.bindings.common.Operation
import community.flock.kotlinx.openapi.bindings.common.Parameter
import community.flock.kotlinx.openapi.bindings.common.ParameterOrReference
import community.flock.kotlinx.openapi.bindings.common.Path
import community.flock.kotlinx.openapi.bindings.common.PathItem
import community.flock.kotlinx.openapi.bindings.common.Ref
import community.flock.kotlinx.openapi.bindings.common.Reference
import community.flock.kotlinx.openapi.bindings.common.RequestBody
import community.flock.kotlinx.openapi.bindings.common.RequestBodyOrReference
import community.flock.kotlinx.openapi.bindings.common.Response
import community.flock.kotlinx.openapi.bindings.common.ResponseOrReference
import community.flock.kotlinx.openapi.bindings.common.Schema
import community.flock.kotlinx.openapi.bindings.common.SchemaOrReference
import community.flock.kotlinx.openapi.bindings.common.SchemaOrReferenceOrBoolean
import community.flock.kotlinx.openapi.bindings.common.SecurityScheme
import community.flock.kotlinx.openapi.bindings.common.SecuritySchemeOrReference
import community.flock.kotlinx.openapi.bindings.common.Server
import community.flock.kotlinx.openapi.bindings.common.StatusCode
import community.flock.kotlinx.openapi.bindings.common.TagObject
import community.flock.kotlinx.openapi.bindings.common.XML
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = OpenAPIV3ResponseOrReferenceSerializer::class)
sealed interface OpenAPIV3ResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIV3HeaderOrReferenceSerializer::class)
sealed interface OpenAPIV3HeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIV3ParameterOrReferenceSerializer::class)
sealed interface OpenAPIV3ParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPIV3SchemaOrReferenceSerializer::class)
sealed interface OpenAPIV3SchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPIV3SchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPIV3SchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPIV3CallbackOrReferenceSerializer::class)
sealed interface OpenAPIV3CallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPIV3LinkOrReferenceSerializer::class)
sealed interface OpenAPIV3LinkOrReference : LinkOrReference

@Serializable(with = OpenAPIV3ExampleOrReferenceSerializer::class)
sealed interface OpenAPIV3ExampleOrReference : ExampleOrReference

@Serializable(with = OpenAPIV3RequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIV3RequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPIV3SecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPIV3SecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class OpenAPIV3Style {
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
    DEEP_OBJECT,
}

@Serializable
enum class OpenAPIV3ParameterLocation {
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
enum class OpenAPIV3Type {
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
}

@Serializable
enum class OpenAPIV3SecuritySchemeType {
    @SerialName("apiKey")
    API_KEY,

    @SerialName("http")
    HTTP,

    @SerialName("oauth2")
    OAUTH2,

    @SerialName("openIdConnect")
    OPEN_ID_CONNECT,
}

@Serializable
data class OpenAPIV3Model(
    val openapi: String,
    val servers: List<Server>? = null,
    val components: OpenAPIV3Components? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIV3PathItem>,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : CommonModel

@Serializable
data class OpenAPIV3PathItem(
    override val parameters: List<OpenAPIV3ParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIV3Operation? = null,
    override val put: OpenAPIV3Operation? = null,
    override val post: OpenAPIV3Operation? = null,
    override val delete: OpenAPIV3Operation? = null,
    override val options: OpenAPIV3Operation? = null,
    override val head: OpenAPIV3Operation? = null,
    override val patch: OpenAPIV3Operation? = null,
    override val trace: OpenAPIV3Operation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class OpenAPIV3Operation(
    override val parameters: List<OpenAPIV3ParameterOrReference>? = null,
    override val requestBody: OpenAPIV3RequestBodyOrReference? = null,
    override val responses: Map<StatusCode, OpenAPIV3ResponseOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV3CallbackOrReference>? = null,
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
data class OpenAPIV3RequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIV3MediaType>? = null,
    override val required: Boolean? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIV3RequestBodyOrReference

@Serializable(with = OpenAPIV3CallbacksSerializer::class)
class OpenAPIV3Callbacks(override val entries: Set<Map.Entry<String, OpenAPIV3PathItem>>) :
    AbstractMap<String, OpenAPIV3PathItem>(),
    OpenAPIV3CallbackOrReference

@Serializable(with = OpenAPIV3LinksSerializer::class)
class OpenAPIV3Links(override val entries: Set<Map.Entry<String, OpenAPIV3LinkOrReference>>) : AbstractMap<String, OpenAPIV3LinkOrReference>()

@Serializable
data class OpenAPIV3Link(
    override val operationRef: String? = null,
    override val operationId: String? = null,
    override val parameters: Map<String, JsonElement>? = null,
    override val requestBody: JsonElement? = null,
    override val description: String? = null,
    override val server: Server? = null,
) : Link,
    OpenAPIV3LinkOrReference

@Serializable
data class OpenAPIV3Response(
    val content: Map<MediaType, OpenAPIV3MediaType>? = null,
    override val description: String? = null,
    override val headers: Map<String, OpenAPIV3HeaderOrReference>? = null,
    override val links: OpenAPIV3Links? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Response,
    OpenAPIV3ResponseOrReference

@Serializable
data class OpenAPIV3Header(
    override val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV3Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: OpenAPIV3SchemaOrReference? = null,
    val examples: Map<String, OpenAPIV3ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV3MediaType>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Header,
    OpenAPIV3HeaderOrReference

@Serializable
data class OpenAPIV3Parameter(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV3Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val examples: Map<String, OpenAPIV3ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV3MediaType>? = null,
    val `in`: OpenAPIV3ParameterLocation,
    override val schema: OpenAPIV3SchemaOrReference? = null,
    override val name: String,
    override val xProperties: Map<String, JsonElement>? = null,
) : Parameter,
    OpenAPIV3ParameterOrReference

@Serializable
data class OpenAPIV3MediaType(
    override val schema: OpenAPIV3SchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIV3EncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIV3EncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIV3HeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIV3Example(
    val summary: String? = null,
    val description: String? = null,
    val value: JsonElement? = null,
    val externalValue: String? = null,
) : OpenAPIV3ExampleOrReference

@Serializable
data class OpenAPIV3Components(
    val schemas: Map<String, OpenAPIV3SchemaOrReference>? = null,
    val responses: Map<String, OpenAPIV3ResponseOrReference>? = null,
    val parameters: Map<String, OpenAPIV3ParameterOrReference>? = null,
    val examples: Map<String, OpenAPIV3ExampleOrReference>? = null,
    val requestBodies: Map<String, OpenAPIV3RequestBodyOrReference>? = null,
    val headers: Map<String, OpenAPIV3HeaderOrReference>? = null,
    val securitySchemes: Map<String, OpenAPIV3SecuritySchemeOrReference>? = null,
    val links: Map<String, OpenAPIV3LinkOrReference>? = null,
    val callbacks: Map<String, OpenAPIV3CallbackOrReference>? = null,
)

@Serializable
data class OpenAPIV3SecurityScheme(
    val type: OpenAPIV3SecuritySchemeType,
    val scheme: String? = null,
    val bearerFormat: String? = null,
    val flows: OpenAPIV3OAuthFlows? = null,
    val openIdConnectUrl: String? = null,
    override val description: String? = null,
    override val name: String? = null,
    override val `in`: String? = null,
) : SecurityScheme,
    OpenAPIV3SecuritySchemeOrReference

@Serializable
data class OpenAPIV3OAuthFlows(
    val implicit: OpenAPIV3OAuthFlow? = null,
    val password: OpenAPIV3OAuthFlow? = null,
    val clientCredentials: OpenAPIV3OAuthFlow? = null,
    val authorizationCode: OpenAPIV3OAuthFlow? = null,
)

@Serializable
data class OpenAPIV3OAuthFlow(
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val refreshUrl: String? = null,
    val scopes: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV3Boolean(
    override val value: Boolean,
) : BooleanValue,
    OpenAPIV3SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV3Schema(
    val nullable: Boolean? = null,
    val deprecated: Boolean? = null,
    val not: OpenAPIV3SchemaOrReference? = null,
    val oneOf: List<OpenAPIV3SchemaOrReference>? = null,
    val anyOf: List<OpenAPIV3SchemaOrReference>? = null,
    val discriminator: OpenAPIV3Discriminator? = null,
    val writeOnly: Boolean? = null,
    val type: OpenAPIV3Type? = null,
    val examples: List<JsonElement>? = null,
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
    override val items: OpenAPIV3SchemaOrReference? = null,
    override val allOf: List<OpenAPIV3SchemaOrReference>? = null,
    override val properties: Map<String, OpenAPIV3SchemaOrReference>? = null,
    override val additionalProperties: OpenAPIV3SchemaOrReferenceOrBoolean? = null,
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
    OpenAPIV3SchemaOrReference,
    OpenAPIV3SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV3Discriminator(
    val propertyName: String,
    val mapping: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV3Reference(
    @SerialName("\$ref")
    override val ref: Ref,
) : Reference,
    OpenAPIV3SchemaOrReference,
    OpenAPIV3SchemaOrReferenceOrBoolean,
    OpenAPIV3ResponseOrReference,
    OpenAPIV3HeaderOrReference,
    OpenAPIV3CallbackOrReference,
    OpenAPIV3LinkOrReference,
    OpenAPIV3ParameterOrReference,
    OpenAPIV3ExampleOrReference,
    OpenAPIV3RequestBodyOrReference,
    OpenAPIV3SecuritySchemeOrReference
