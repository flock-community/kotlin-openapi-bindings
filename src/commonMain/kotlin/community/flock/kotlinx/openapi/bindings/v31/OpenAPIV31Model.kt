@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = OpenAPIV31ResponseOrReferenceSerializer::class)
sealed interface OpenAPIV31ResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIV31HeaderOrReferenceSerializer::class)
sealed interface OpenAPIV31HeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIV31ParameterOrReferenceSerializer::class)
sealed interface OpenAPIV31ParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPIV31SchemaOrReferenceSerializer::class)
sealed interface OpenAPIV31SchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPIV31SchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPIV31SchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPIV31CallbackOrReferenceSerializer::class)
sealed interface OpenAPIV31CallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPIV31LinkOrReferenceSerializer::class)
sealed interface OpenAPIV31LinkOrReference : LinkOrReference

@Serializable(with = OpenAPIV31ExampleOrReferenceSerializer::class)
sealed interface OpenAPIV31ExampleOrReference : ExampleOrReference

@Serializable(with = OpenAPIV31RequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIV31RequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPIV31SecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPIV31SecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class OpenAPIV31Style {
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
enum class OpenAPIV31ParameterLocation {
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
enum class OpenAPIV31Type {
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

    @SerialName("null")
    NULL,
}

@Serializable(with = OpenAPIV31TypeDefinitionSerializer::class)
sealed interface OpenAPIV31TypeDefinition

data class OpenAPIV31SingleType(val value: OpenAPIV31Type) : OpenAPIV31TypeDefinition

data class OpenAPIV31TypeArray(val values: List<OpenAPIV31Type>) : OpenAPIV31TypeDefinition

@Serializable
enum class OpenAPIV31SecuritySchemeType {
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
data class OpenAPIV31Model(
    override val openapi: String,
    val servers: List<Server>? = null,
    val components: OpenAPIV31Components? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIV31PathItem>? = null,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIModel,
    OpenAPIV3Model

@Serializable
data class OpenAPIV31PathItem(
    override val parameters: List<OpenAPIV31ParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIV31Operation? = null,
    override val put: OpenAPIV31Operation? = null,
    override val post: OpenAPIV31Operation? = null,
    override val delete: OpenAPIV31Operation? = null,
    override val options: OpenAPIV31Operation? = null,
    override val head: OpenAPIV31Operation? = null,
    override val patch: OpenAPIV31Operation? = null,
    override val trace: OpenAPIV31Operation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class OpenAPIV31Operation(
    override val parameters: List<OpenAPIV31ParameterOrReference>? = null,
    override val requestBody: OpenAPIV31RequestBodyOrReference? = null,
    override val responses: Map<StatusCode, OpenAPIV31ResponseOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV31CallbackOrReference>? = null,
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
data class OpenAPIV31RequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIV31MediaType>? = null,
    override val required: Boolean? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIV31RequestBodyOrReference

@Serializable(with = OpenAPIV31CallbacksSerializer::class)
class OpenAPIV31Callbacks(override val entries: Set<Map.Entry<String, OpenAPIV31PathItem>>) :
    AbstractMap<String, OpenAPIV31PathItem>(),
    OpenAPIV31CallbackOrReference

@Serializable(with = OpenAPIV31LinksSerializer::class)
class OpenAPIV31Links(override val entries: Set<Map.Entry<String, OpenAPIV31LinkOrReference>>) : AbstractMap<String, OpenAPIV31LinkOrReference>()

@Serializable
data class OpenAPIV31Link(
    override val operationRef: String? = null,
    override val operationId: String? = null,
    override val parameters: Map<String, JsonElement>? = null,
    override val requestBody: JsonElement? = null,
    override val description: String? = null,
    override val server: Server? = null,
) : Link,
    OpenAPIV31LinkOrReference

@Serializable
data class OpenAPIV31Response(
    val content: Map<MediaType, OpenAPIV31MediaType>? = null,
    override val description: String? = null,
    override val headers: Map<String, OpenAPIV31HeaderOrReference>? = null,
    override val links: OpenAPIV31Links? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Response,
    OpenAPIV31ResponseOrReference

@Serializable
data class OpenAPIV31Header(
    override val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV31Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: OpenAPIV31SchemaOrReference? = null,
    val examples: Map<String, OpenAPIV31ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV31MediaType>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Header,
    OpenAPIV31HeaderOrReference

@Serializable
data class OpenAPIV31Parameter(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV31Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val examples: Map<String, OpenAPIV31ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV31MediaType>? = null,
    val `in`: OpenAPIV31ParameterLocation,
    override val schema: OpenAPIV31SchemaOrReference? = null,
    override val name: String,
    override val xProperties: Map<String, JsonElement>? = null,
) : Parameter,
    OpenAPIV31ParameterOrReference

@Serializable
data class OpenAPIV31MediaType(
    override val schema: OpenAPIV31SchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIV31EncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIV31EncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIV31HeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIV31Example(
    val summary: String? = null,
    val description: String? = null,
    val value: JsonElement? = null,
    val externalValue: String? = null,
) : OpenAPIV31ExampleOrReference

@Serializable
data class OpenAPIV31Components(
    val schemas: Map<String, OpenAPIV31SchemaOrReference>? = null,
    val responses: Map<String, OpenAPIV31ResponseOrReference>? = null,
    val parameters: Map<String, OpenAPIV31ParameterOrReference>? = null,
    val examples: Map<String, OpenAPIV31ExampleOrReference>? = null,
    val requestBodies: Map<String, OpenAPIV31RequestBodyOrReference>? = null,
    val headers: Map<String, OpenAPIV31HeaderOrReference>? = null,
    val securitySchemes: Map<String, OpenAPIV31SecuritySchemeOrReference>? = null,
    val links: Map<String, OpenAPIV31LinkOrReference>? = null,
    val callbacks: Map<String, OpenAPIV31CallbackOrReference>? = null,
)

@Serializable
data class OpenAPIV31SecurityScheme(
    val type: OpenAPIV31SecuritySchemeType,
    val scheme: String? = null,
    val bearerFormat: String? = null,
    val flows: OpenAPIV31OAuthFlows? = null,
    val openIdConnectUrl: String? = null,
    override val description: String? = null,
    override val name: String? = null,
    override val `in`: String? = null,
) : SecurityScheme,
    OpenAPIV31SecuritySchemeOrReference

@Serializable
data class OpenAPIV31OAuthFlows(
    val implicit: OpenAPIV31OAuthFlow? = null,
    val password: OpenAPIV31OAuthFlow? = null,
    val clientCredentials: OpenAPIV31OAuthFlow? = null,
    val authorizationCode: OpenAPIV31OAuthFlow? = null,
)

@Serializable
data class OpenAPIV31OAuthFlow(
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val refreshUrl: String? = null,
    val scopes: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV31Boolean(
    override val value: Boolean,
) : BooleanValue,
    OpenAPIV31SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV31Schema(
    val deprecated: Boolean? = null,
    val not: OpenAPIV31SchemaOrReference? = null,
    val oneOf: List<OpenAPIV31SchemaOrReference>? = null,
    val anyOf: List<OpenAPIV31SchemaOrReference>? = null,
    val discriminator: OpenAPIV31Discriminator? = null,
    val writeOnly: Boolean? = null,
    val type: OpenAPIV31TypeDefinition? = null,
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
    override val items: OpenAPIV31SchemaOrReference? = null,
    override val allOf: List<OpenAPIV31SchemaOrReference>? = null,
    override val properties: Map<String, OpenAPIV31SchemaOrReference>? = null,
    override val additionalProperties: OpenAPIV31SchemaOrReferenceOrBoolean? = null,
    val propertyNames: OpenAPIV31SchemaOrReference? = null,
    override val xProperties: Map<String, JsonElement>? = null,
    override val format: String? = null,
    override val maximum: Double? = null,
    val exclusiveMaximum: Double? = null,
    override val minimum: Double? = null,
    val exclusiveMinimum: Double? = null,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    override val pattern: String? = null,
    override val maxItems: Int? = null,
    override val minItems: Int? = null,
) : Schema,
    OpenAPIV31SchemaOrReference,
    OpenAPIV31SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV31Discriminator(
    val propertyName: String,
    val mapping: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV31Reference(
    @SerialName("\$ref")
    override val ref: Ref,
    val summary: String? = null,
    val description: String? = null,
    val type: OpenAPIV31TypeDefinition? = null,
    val default: JsonElement? = null,
) : Reference,
    OpenAPIV31SchemaOrReference,
    OpenAPIV31SchemaOrReferenceOrBoolean,
    OpenAPIV31ResponseOrReference,
    OpenAPIV31HeaderOrReference,
    OpenAPIV31CallbackOrReference,
    OpenAPIV31LinkOrReference,
    OpenAPIV31ParameterOrReference,
    OpenAPIV31ExampleOrReference,
    OpenAPIV31RequestBodyOrReference,
    OpenAPIV31SecuritySchemeOrReference
