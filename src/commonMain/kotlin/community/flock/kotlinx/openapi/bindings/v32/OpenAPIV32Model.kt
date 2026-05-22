@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = OpenAPIV32ResponseOrReferenceSerializer::class)
sealed interface OpenAPIV32ResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIV32HeaderOrReferenceSerializer::class)
sealed interface OpenAPIV32HeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIV32ParameterOrReferenceSerializer::class)
sealed interface OpenAPIV32ParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPIV32SchemaOrReferenceSerializer::class)
sealed interface OpenAPIV32SchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPIV32SchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPIV32SchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPIV32CallbackOrReferenceSerializer::class)
sealed interface OpenAPIV32CallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPIV32LinkOrReferenceSerializer::class)
sealed interface OpenAPIV32LinkOrReference : LinkOrReference

@Serializable(with = OpenAPIV32ExampleOrReferenceSerializer::class)
sealed interface OpenAPIV32ExampleOrReference : ExampleOrReference

@Serializable(with = OpenAPIV32RequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIV32RequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPIV32SecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPIV32SecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable(with = OpenAPIV32PathItemOrReferenceSerializer::class)
sealed interface OpenAPIV32PathItemOrReference : PathItemOrReference

@Serializable
enum class OpenAPIV32Style {
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
enum class OpenAPIV32ParameterLocation {
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
enum class OpenAPIV32Type {
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

@Serializable(with = OpenAPIV32TypeDefinitionSerializer::class)
sealed interface OpenAPIV32TypeDefinition

data class OpenAPIV32SingleType(val value: OpenAPIV32Type) : OpenAPIV32TypeDefinition

data class OpenAPIV32TypeArray(val values: List<OpenAPIV32Type>) : OpenAPIV32TypeDefinition

@Serializable
enum class OpenAPIV32SecuritySchemeType {
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
data class OpenAPIV32Model(
    override val openapi: String,
    val jsonSchemaDialect: String? = null,
    val webhooks: Map<String, OpenAPIV32PathItemOrReference>? = null,
    val servers: List<Server>? = null,
    override val components: OpenAPIV32Components? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIV32PathItem>? = null,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIV3Model

@Serializable
data class OpenAPIV32PathItem(
    override val parameters: List<OpenAPIV32ParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIV32Operation? = null,
    override val put: OpenAPIV32Operation? = null,
    override val post: OpenAPIV32Operation? = null,
    override val delete: OpenAPIV32Operation? = null,
    override val options: OpenAPIV32Operation? = null,
    override val head: OpenAPIV32Operation? = null,
    override val patch: OpenAPIV32Operation? = null,
    override val trace: OpenAPIV32Operation? = null,
    val query: OpenAPIV32Operation? = null,
    val additionalOperations: Map<String, OpenAPIV32Operation>? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem,
    OpenAPIV32PathItemOrReference

@Serializable
data class OpenAPIV32Operation(
    override val parameters: List<OpenAPIV32ParameterOrReference>? = null,
    override val requestBody: OpenAPIV32RequestBodyOrReference? = null,
    override val responses: Map<StatusCode, OpenAPIV32ResponseOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV32CallbackOrReference>? = null,
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
data class OpenAPIV32RequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIV32MediaType>? = null,
    override val required: Boolean? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIV32RequestBodyOrReference

@Serializable(with = OpenAPIV32CallbacksSerializer::class)
class OpenAPIV32Callbacks(override val entries: Set<Map.Entry<String, OpenAPIV32PathItem>>) :
    AbstractMap<String, OpenAPIV32PathItem>(),
    OpenAPIV32CallbackOrReference

@Serializable(with = OpenAPIV32LinksSerializer::class)
class OpenAPIV32Links(override val entries: Set<Map.Entry<String, OpenAPIV32LinkOrReference>>) : AbstractMap<String, OpenAPIV32LinkOrReference>()

@Serializable
data class OpenAPIV32Link(
    override val operationRef: String? = null,
    override val operationId: String? = null,
    override val parameters: Map<String, JsonElement>? = null,
    override val requestBody: JsonElement? = null,
    override val description: String? = null,
    override val server: Server? = null,
) : Link,
    OpenAPIV32LinkOrReference

@Serializable
data class OpenAPIV32Response(
    val content: Map<MediaType, OpenAPIV32MediaType>? = null,
    override val description: String? = null,
    override val headers: Map<String, OpenAPIV32HeaderOrReference>? = null,
    override val links: OpenAPIV32Links? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Response,
    OpenAPIV32ResponseOrReference

@Serializable
data class OpenAPIV32Header(
    override val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV32Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: OpenAPIV32SchemaOrReference? = null,
    val examples: Map<String, OpenAPIV32ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV32MediaType>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Header,
    OpenAPIV32HeaderOrReference

@Serializable
data class OpenAPIV32Parameter(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV32Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val examples: Map<String, OpenAPIV32ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV32MediaType>? = null,
    val `in`: OpenAPIV32ParameterLocation,
    override val schema: OpenAPIV32SchemaOrReference? = null,
    override val name: String,
    override val xProperties: Map<String, JsonElement>? = null,
) : Parameter,
    OpenAPIV32ParameterOrReference

@Serializable
data class OpenAPIV32MediaType(
    override val schema: OpenAPIV32SchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIV32EncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIV32EncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIV32HeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIV32Example(
    val summary: String? = null,
    val description: String? = null,
    val value: JsonElement? = null,
    val externalValue: String? = null,
) : OpenAPIV32ExampleOrReference

@Serializable
data class OpenAPIV32Components(
    override val schemas: Map<String, OpenAPIV32SchemaOrReference>? = null,
    override val responses: Map<String, OpenAPIV32ResponseOrReference>? = null,
    override val parameters: Map<String, OpenAPIV32ParameterOrReference>? = null,
    override val examples: Map<String, OpenAPIV32ExampleOrReference>? = null,
    override val requestBodies: Map<String, OpenAPIV32RequestBodyOrReference>? = null,
    override val headers: Map<String, OpenAPIV32HeaderOrReference>? = null,
    override val securitySchemes: Map<String, OpenAPIV32SecuritySchemeOrReference>? = null,
    override val links: Map<String, OpenAPIV32LinkOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV32CallbackOrReference>? = null,
    val pathItems: Map<String, OpenAPIV32PathItemOrReference>? = null,
) : Components

@Serializable
data class OpenAPIV32SecurityScheme(
    val type: OpenAPIV32SecuritySchemeType,
    val scheme: String? = null,
    val bearerFormat: String? = null,
    val flows: OpenAPIV32OAuthFlows? = null,
    val openIdConnectUrl: String? = null,
    override val description: String? = null,
    override val name: String? = null,
    override val `in`: String? = null,
) : SecurityScheme,
    OpenAPIV32SecuritySchemeOrReference

@Serializable
data class OpenAPIV32OAuthFlows(
    val implicit: OpenAPIV32OAuthFlow? = null,
    val password: OpenAPIV32OAuthFlow? = null,
    val clientCredentials: OpenAPIV32OAuthFlow? = null,
    val authorizationCode: OpenAPIV32OAuthFlow? = null,
)

@Serializable
data class OpenAPIV32OAuthFlow(
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val refreshUrl: String? = null,
    val scopes: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV32Boolean(
    override val value: Boolean,
) : BooleanValue,
    OpenAPIV32SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV32Schema(
    val deprecated: Boolean? = null,
    val not: OpenAPIV32SchemaOrReference? = null,
    val oneOf: List<OpenAPIV32SchemaOrReference>? = null,
    val anyOf: List<OpenAPIV32SchemaOrReference>? = null,
    val discriminator: OpenAPIV32Discriminator? = null,
    val writeOnly: Boolean? = null,
    val type: OpenAPIV32TypeDefinition? = null,
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
    override val items: OpenAPIV32SchemaOrReference? = null,
    override val allOf: List<OpenAPIV32SchemaOrReference>? = null,
    override val properties: Map<String, OpenAPIV32SchemaOrReference>? = null,
    override val additionalProperties: OpenAPIV32SchemaOrReferenceOrBoolean? = null,
    val propertyNames: OpenAPIV32SchemaOrReference? = null,
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
    val const: JsonElement? = null,
    val prefixItems: List<OpenAPIV32SchemaOrReference>? = null,
    val contentEncoding: String? = null,
    val contentMediaType: String? = null,
    val contentSchema: OpenAPIV32SchemaOrReference? = null,
    val dependentRequired: Map<String, List<String>>? = null,
    val dependentSchemas: Map<String, OpenAPIV32SchemaOrReference>? = null,
    val unevaluatedProperties: OpenAPIV32SchemaOrReferenceOrBoolean? = null,
    val unevaluatedItems: OpenAPIV32SchemaOrReference? = null,
    @SerialName("\$defs")
    val defs: Map<String, OpenAPIV32SchemaOrReference>? = null,
) : Schema,
    OpenAPIV32SchemaOrReference,
    OpenAPIV32SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV32Discriminator(
    val propertyName: String,
    val mapping: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV32Reference(
    @SerialName("\$ref")
    override val ref: Ref,
    val summary: String? = null,
    val description: String? = null,
    val type: OpenAPIV32TypeDefinition? = null,
    val default: JsonElement? = null,
) : Reference,
    OpenAPIV32SchemaOrReference,
    OpenAPIV32SchemaOrReferenceOrBoolean,
    OpenAPIV32ResponseOrReference,
    OpenAPIV32HeaderOrReference,
    OpenAPIV32CallbackOrReference,
    OpenAPIV32LinkOrReference,
    OpenAPIV32ParameterOrReference,
    OpenAPIV32ExampleOrReference,
    OpenAPIV32RequestBodyOrReference,
    OpenAPIV32SecuritySchemeOrReference,
    OpenAPIV32PathItemOrReference
