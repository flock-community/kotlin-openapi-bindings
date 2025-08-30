@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v3

import community.flock.kotlinx.openapi.bindings.common.CallbackOrReference
import community.flock.kotlinx.openapi.bindings.common.CommonModel
import community.flock.kotlinx.openapi.bindings.common.EncodingProperty
import community.flock.kotlinx.openapi.bindings.common.ExampleOrReference
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

@Serializable(with = OpenAPIResponseOrReferenceSerializer::class)
sealed interface OpenAPIResponseOrReference : ResponseOrReference

@Serializable(with = OpenAPIHeaderOrReferenceSerializer::class)
sealed interface OpenAPIHeaderOrReference : HeaderOrReference

@Serializable(with = OpenAPIParameterOrReferenceSerializer::class)
sealed interface OpenAPIParameterOrReference : ParameterOrReference

@Serializable(with = OpenAPISchemaOrReferenceSerializer::class)
sealed interface OpenAPISchemaOrReference : SchemaOrReference

@Serializable(with = OpenAPISchemaOrReferenceOrBooleanSerializer::class)
sealed interface OpenAPISchemaOrReferenceOrBoolean : SchemaOrReferenceOrBoolean

@Serializable(with = OpenAPICallbackOrReferenceSerializer::class)
sealed interface OpenAPICallbackOrReference : CallbackOrReference

@Serializable(with = OpenAPILinkOrReferenceSerializer::class)
sealed interface OpenAPILinkOrReference : LinkOrReference

@Serializable(with = OpenAPIExampleOrReferenceSerializer::class)
sealed interface OpenAPIExampleOrReference : ExampleOrReference

@Serializable(with = OpenAPIRequestBodyOrReferenceSerializer::class)
sealed interface OpenAPIRequestBodyOrReference : RequestBodyOrReference

@Serializable(with = OpenAPISecuritySchemeOrReferenceSerializer::class)
sealed interface OpenAPISecuritySchemeOrReference : SecuritySchemeOrReference

@Serializable
enum class OpenAPIStyle {
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
enum class OpenAPIParameterLocation {
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
enum class OpenAPIType {
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
enum class OpenAPISecuritySchemeType {
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
data class OpenAPIModel(
    val openapi: String,
    val servers: List<Server>? = null,
    val components: OpenAPIComponents? = null,
    override val info: InfoObject,
    override val paths: Map<Path, OpenAPIPathItem>,
    override val security: List<Map<String, List<String>>>? = null,
    override val tags: List<TagObject>? = null,
    override val externalDocs: ExternalDocumentation? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : CommonModel

@Serializable
data class OpenAPIPathItem(
    val parameters: List<OpenAPIParameterOrReference>? = null,
    override val ref: String? = null,
    override val summary: String? = null,
    override val description: String? = null,
    override val get: OpenAPIOperation? = null,
    override val put: OpenAPIOperation? = null,
    override val post: OpenAPIOperation? = null,
    override val delete: OpenAPIOperation? = null,
    override val options: OpenAPIOperation? = null,
    override val head: OpenAPIOperation? = null,
    override val patch: OpenAPIOperation? = null,
    override val trace: OpenAPIOperation? = null,
    override val servers: List<Server>? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : PathItem

@Serializable
data class OpenAPIOperation(
    val parameters: List<OpenAPIParameterOrReference>? = null,
    val requestBody: OpenAPIRequestBodyOrReference? = null,
    val responses: Map<StatusCode, OpenAPIResponseOrReference>? = null,
    val callbacks: Map<String, OpenAPICallbackOrReference>? = null,
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
data class OpenAPIRequestBody(
    override val description: String? = null,
    override val content: Map<MediaType, OpenAPIMediaType>? = null,
    override val required: Boolean? = null,
    override val xProperties: Map<String, JsonElement>? = null,
) : RequestBody,
    OpenAPIRequestBodyOrReference

@Serializable(with = OpenAPICallbacksSerializer::class)
class OpenAPICallbacks(override val entries: Set<Map.Entry<String, OpenAPIPathItem>>) :
    AbstractMap<String, OpenAPIPathItem>(),
    OpenAPICallbackOrReference

@Serializable(with = OpenAPILinksSerializer::class)
class OpenAPILinks(
    override val entries: Set<Map.Entry<String, OpenAPILinkOrReference>>,
) : AbstractMap<String, OpenAPILinkOrReference>()

@Serializable
data class OpenAPILink(
    val operationRef: String? = null,
    val operationId: String? = null,
    val parameters: Map<String, JsonElement>? = null,
    val requestBody: JsonElement? = null,
    val description: String? = null,
    val server: Server? = null,
) : OpenAPILinkOrReference

@Serializable
data class OpenAPIResponse(
    val description: String? = null,
    val headers: Map<String, OpenAPIHeaderOrReference>? = null,
    val content: Map<MediaType, OpenAPIMediaType>? = null,
    val links: OpenAPILinks? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIResponseOrReference

@Serializable
data class OpenAPIHeader(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIStyle? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: OpenAPISchemaOrReference? = null,
    val examples: Map<String, OpenAPIExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIMediaType>? = null,
    val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIHeaderOrReference

@Serializable
data class OpenAPIParameter(
    val description: String? = null,
    val required: Boolean? = null,
    val deprecated: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val style: OpenAPIStyle? = null,
    val explode: Boolean? = null,
    val allowReserved: Boolean? = null,
    val schema: OpenAPISchemaOrReference? = null,
    val examples: Map<String, OpenAPIExampleOrReference>? = null,
    val example: JsonElement? = null,
    val content: Map<MediaType, OpenAPIMediaType>? = null,
    val name: String,
    val `in`: OpenAPIParameterLocation,
    val xProperties: Map<String, JsonElement>? = null,
) : OpenAPIParameterOrReference

@Serializable
data class OpenAPIMediaType(
    override val schema: OpenAPISchemaOrReference? = null,
    override val examples: Map<String, JsonElement>? = null,
    override val example: JsonElement? = null,
    override val encoding: Map<String, OpenAPIEncodingProperty>? = null,
) : MediaTypeObject

@Serializable
data class OpenAPIEncodingProperty(
    override val contentType: String? = null,
    override val headers: Map<String, OpenAPIHeaderOrReference>? = null,
    override val style: String? = null,
    override val explode: Boolean? = null,
    override val allowReserved: Boolean? = null,
) : EncodingProperty

@Serializable
data class OpenAPIExample(
    val summary: String? = null,
    val description: String? = null,
    val value: JsonElement? = null,
    val externalValue: String? = null,
) : OpenAPIExampleOrReference

@Serializable
data class OpenAPIComponents(
    val schemas: Map<String, OpenAPISchemaOrReference>? = null,
    val responses: Map<String, OpenAPIResponseOrReference>? = null,
    val parameters: Map<String, OpenAPIParameterOrReference>? = null,
    val examples: Map<String, OpenAPIExampleOrReference>? = null,
    val requestBodies: Map<String, OpenAPIRequestBodyOrReference>? = null,
    val headers: Map<String, OpenAPIHeaderOrReference>? = null,
    val securitySchemes: Map<String, OpenAPISecuritySchemeOrReference>? = null,
    val links: Map<String, OpenAPILinkOrReference>? = null,
    val callbacks: Map<String, OpenAPICallbackOrReference>? = null,
)

@Serializable
data class OpenAPISecurityScheme(
    val type: OpenAPISecuritySchemeType,
    val description: String? = null,
    val name: String? = null,
    val `in`: String? = null,
    val scheme: String? = null,
    val bearerFormat: String? = null,
    val flows: OpenAPIOAuthFlows? = null,
    val openIdConnectUrl: String? = null,
) : OpenAPISecuritySchemeOrReference

@Serializable
data class OpenAPIOAuthFlows(
    val implicit: OpenAPIOAuthFlow? = null,
    val password: OpenAPIOAuthFlow? = null,
    val clientCredentials: OpenAPIOAuthFlow? = null,
    val authorizationCode: OpenAPIOAuthFlow? = null,
)

@Serializable
data class OpenAPIOAuthFlow(
    val authorizationUrl: String? = null,
    val tokenUrl: String? = null,
    val refreshUrl: String? = null,
    val scopes: Map<String, String>? = null,
)

@Serializable
data class OpenAPIBoolean(
    val value: Boolean,
) : OpenAPISchemaOrReferenceOrBoolean

@Serializable
data class OpenAPISchema(
    val nullable: Boolean? = null,
    val discriminator: OpenAPIDiscriminator? = null,
    val readOnly: Boolean? = null,
    val writeOnly: Boolean? = null,
    val xml: OpenAPIXml? = null,
    val externalDocs: ExternalDocumentation? = null,
    val example: JsonElement? = null,
    val examples: List<JsonElement>? = null,
    val deprecated: Boolean? = null,

    val type: OpenAPIType? = null,
    val allOf: List<OpenAPISchemaOrReference>? = null,
    val oneOf: List<OpenAPISchemaOrReference>? = null,
    val anyOf: List<OpenAPISchemaOrReference>? = null,
    val not: OpenAPISchemaOrReference? = null,
    val items: OpenAPISchemaOrReference? = null,
    val properties: Map<String, OpenAPISchemaOrReference>? = null,
    val additionalProperties: OpenAPISchemaOrReferenceOrBoolean? = null,
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
    val required: List<String>? = null,
    val enum: List<JsonPrimitive>? = null,

    val xProperties: Map<String, JsonElement>? = null,
) : OpenAPISchemaOrReference,
    OpenAPISchemaOrReferenceOrBoolean

@Serializable
data class OpenAPIDiscriminator(
    val propertyName: String,
    val mapping: Map<String, String>? = null,
)

@Serializable
data class OpenAPIXml(
    val name: String? = null,
    val namespace: String? = null,
    val prefix: String? = null,
    val attribute: Boolean? = null,
    val wrapped: Boolean? = null,
)

@Serializable
data class OpenAPIReference(
    @SerialName("\$ref")
    val ref: Ref,
) : OpenAPISchemaOrReference,
    OpenAPISchemaOrReferenceOrBoolean,
    OpenAPIResponseOrReference,
    OpenAPIHeaderOrReference,
    OpenAPICallbackOrReference,
    OpenAPILinkOrReference,
    OpenAPIParameterOrReference,
    OpenAPIExampleOrReference,
    OpenAPIRequestBodyOrReference,
    OpenAPISecuritySchemeOrReference
