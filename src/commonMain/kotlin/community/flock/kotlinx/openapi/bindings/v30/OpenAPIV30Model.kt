@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = OpenAPIV30ResponseOrReferenceSerializer::class)
sealed interface OpenAPIV30ResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIV30HeaderOrReferenceSerializer::class)
sealed interface OpenAPIV30HeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIV30ParameterOrReferenceSerializer::class)
sealed interface OpenAPIV30ParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPIV30SchemaOrReferenceSerializer::class)
sealed interface OpenAPIV30SchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPIV30SchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPIV30SchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPIV30CallbackOrReferenceSerializer::class)
sealed interface OpenAPIV30CallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPIV30LinkOrReferenceSerializer::class)
sealed interface OpenAPIV30LinkOrReference : LinkOrReference

@Serializable(with = OpenAPIV30ExampleOrReferenceSerializer::class)
sealed interface OpenAPIV30ExampleOrReference : ExampleOrReference

@Serializable(with = OpenAPIV30RequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIV30RequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPIV30SecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPIV30SecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class OpenAPIV30Style {
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
enum class OpenAPIV30ParameterLocation {
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
enum class OpenAPIV30Type {
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

@Serializable(with = OpenAPIV30TypeDefinitionSerializer::class)
sealed interface OpenAPIV30TypeDefinition

data class OpenAPIV30SingleType(val value: OpenAPIV30Type) : OpenAPIV30TypeDefinition

data class OpenAPIV30TypeArray(val values: List<OpenAPIV30Type>) : OpenAPIV30TypeDefinition

@Serializable
enum class OpenAPIV30SecuritySchemeType {
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
data class OpenAPIV30Model(
    override val openapi: String,
    val servers: List<Server>? = null,
    val components: OpenAPIV30Components? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIV30PathItem>? = null,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIV3Model

@Serializable
data class OpenAPIV30PathItem(
    override val parameters: List<OpenAPIV30ParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIV30Operation? = null,
    override val put: OpenAPIV30Operation? = null,
    override val post: OpenAPIV30Operation? = null,
    override val delete: OpenAPIV30Operation? = null,
    override val options: OpenAPIV30Operation? = null,
    override val head: OpenAPIV30Operation? = null,
    override val patch: OpenAPIV30Operation? = null,
    override val trace: OpenAPIV30Operation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class OpenAPIV30Operation(
    override val parameters: List<OpenAPIV30ParameterOrReference>? = null,
    override val requestBody: OpenAPIV30RequestBodyOrReference? = null,
    override val responses: Map<StatusCode, OpenAPIV30ResponseOrReference>? = null,
    override val callbacks: Map<String, OpenAPIV30CallbackOrReference>? = null,
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
data class OpenAPIV30RequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIV30MediaType>? = null,
    override val required: Boolean? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIV30RequestBodyOrReference

@Serializable(with = OpenAPIV30CallbacksSerializer::class)
class OpenAPIV30Callbacks(override val entries: Set<Map.Entry<String, OpenAPIV30PathItem>>) :
    AbstractMap<String, OpenAPIV30PathItem>(),
    OpenAPIV30CallbackOrReference

@Serializable(with = OpenAPIV30LinksSerializer::class)
class OpenAPIV30Links(override val entries: Set<Map.Entry<String, OpenAPIV30LinkOrReference>>) : AbstractMap<String, OpenAPIV30LinkOrReference>()

@Serializable
data class OpenAPIV30Link(
    override val operationRef: String? = null,
    override val operationId: String? = null,
    override val parameters: Map<String, JsonElement>? = null,
    override val requestBody: JsonElement? = null,
    override val description: String? = null,
    override val server: Server? = null,
) : Link,
    OpenAPIV30LinkOrReference

@Serializable
data class OpenAPIV30Response(
    val content: Map<MediaType, OpenAPIV30MediaType>? = null,
    override val description: String? = null,
    override val headers: Map<String, OpenAPIV30HeaderOrReference>? = null,
    override val links: OpenAPIV30Links? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Response,
    OpenAPIV30ResponseOrReference

@Serializable
data class OpenAPIV30Header(
    override val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV30Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: OpenAPIV30SchemaOrReference? = null,
    val examples: Map<String, OpenAPIV30ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV30MediaType>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : Header,
    OpenAPIV30HeaderOrReference

@Serializable
data class OpenAPIV30Parameter(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIV30Style? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val examples: Map<String, OpenAPIV30ExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIV30MediaType>? = null,
    val `in`: OpenAPIV30ParameterLocation,
    override val schema: OpenAPIV30SchemaOrReference? = null,
    override val name: String,
    override val xProperties: Map<String, JsonElement>? = null,
) : Parameter,
    OpenAPIV30ParameterOrReference

@Serializable
data class OpenAPIV30MediaType(
    override val schema: OpenAPIV30SchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIV30EncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIV30EncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIV30HeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIV30Example(
    val summary: String? = null,
    val description: String? = null,
    val value: JsonElement? = null,
    val externalValue: String? = null,
) : OpenAPIV30ExampleOrReference

@Serializable
data class OpenAPIV30Components(
    val schemas: Map<String, OpenAPIV30SchemaOrReference>? = null,
    val responses: Map<String, OpenAPIV30ResponseOrReference>? = null,
    val parameters: Map<String, OpenAPIV30ParameterOrReference>? = null,
    val examples: Map<String, OpenAPIV30ExampleOrReference>? = null,
    val requestBodies: Map<String, OpenAPIV30RequestBodyOrReference>? = null,
    val headers: Map<String, OpenAPIV30HeaderOrReference>? = null,
    val securitySchemes: Map<String, OpenAPIV30SecuritySchemeOrReference>? = null,
    val links: Map<String, OpenAPIV30LinkOrReference>? = null,
    val callbacks: Map<String, OpenAPIV30CallbackOrReference>? = null,
)

@Serializable
data class OpenAPIV30SecurityScheme(
    val type: OpenAPIV30SecuritySchemeType,
    val scheme: String? = null,
    val bearerFormat: String? = null,
    val flows: OpenAPIV30OAuthFlows? = null,
    val openIdConnectUrl: String? = null,
    override val description: String? = null,
    override val name: String? = null,
    override val `in`: String? = null,
) : SecurityScheme,
    OpenAPIV30SecuritySchemeOrReference

@Serializable
data class OpenAPIV30OAuthFlows(
    val implicit: OpenAPIV30OAuthFlow? = null,
    val password: OpenAPIV30OAuthFlow? = null,
    val clientCredentials: OpenAPIV30OAuthFlow? = null,
    val authorizationCode: OpenAPIV30OAuthFlow? = null,
)

@Serializable
data class OpenAPIV30OAuthFlow(
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val refreshUrl: String? = null,
    val scopes: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV30Boolean(
    override val value: Boolean,
) : BooleanValue,
    OpenAPIV30SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV30Schema(
    val nullable: Boolean? = null,
    val deprecated: Boolean? = null,
    val not: OpenAPIV30SchemaOrReference? = null,
    val oneOf: List<OpenAPIV30SchemaOrReference>? = null,
    val anyOf: List<OpenAPIV30SchemaOrReference>? = null,
    val discriminator: OpenAPIV30Discriminator? = null,
    val writeOnly: Boolean? = null,
    val type: OpenAPIV30TypeDefinition? = null,
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
    override val items: OpenAPIV30SchemaOrReference? = null,
    override val allOf: List<OpenAPIV30SchemaOrReference>? = null,
    override val properties: Map<String, OpenAPIV30SchemaOrReference>? = null,
    override val additionalProperties: OpenAPIV30SchemaOrReferenceOrBoolean? = null,
    val propertyNames: OpenAPIV30SchemaOrReference? = null,
    override val xProperties: Map<String, JsonElement>? = null,
    override val format: String? = null,
    override val maximum: Double? = null,
    val exclusiveMaximum: Boolean? = null,
    override val minimum: Double? = null,
    val exclusiveMinimum: Boolean? = null,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    override val pattern: String? = null,
    override val maxItems: Int? = null,
    override val minItems: Int? = null,
) : Schema,
    OpenAPIV30SchemaOrReference,
    OpenAPIV30SchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIV30Discriminator(
    val propertyName: String,
    val mapping: Map<String, String>? = null,
)

@Serializable
data class OpenAPIV30Reference(
    @SerialName("\$ref")
    override val ref: Ref,
    val summary: String? = null,
    val description: String? = null,
    val type: OpenAPIV30TypeDefinition? = null,
    val default: JsonElement? = null,
) : Reference,
    OpenAPIV30SchemaOrReference,
    OpenAPIV30SchemaOrReferenceOrBoolean,
    OpenAPIV30ResponseOrReference,
    OpenAPIV30HeaderOrReference,
    OpenAPIV30CallbackOrReference,
    OpenAPIV30LinkOrReference,
    OpenAPIV30ParameterOrReference,
    OpenAPIV30ExampleOrReference,
    OpenAPIV30RequestBodyOrReference,
    OpenAPIV30SecuritySchemeOrReference
