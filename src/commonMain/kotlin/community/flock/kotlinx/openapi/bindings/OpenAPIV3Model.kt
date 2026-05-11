@file:OptIn(InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Sealed umbrella over all supported OpenAPI 3.x minor-version document shapes.
 *
 * Concrete implementers live in the `v30/`, `v31/`, `v32/` subdirectories of
 * this package. Selection is driven by the `openapi` version string at parse
 * time — see [OpenAPIV3.decodeFromString]. The umbrella's own serializer only
 * handles encode; decode is always routed through [OpenAPIV3].
 */
@Serializable(with = OpenAPIV3ModelSerializer::class)
sealed interface OpenAPIV3Model : OpenAPIModel {
    val openapi: String
}

object OpenAPIV3ModelSerializer : KSerializer<OpenAPIV3Model> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("OpenAPIV3Model", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3Model) {
        @Suppress("UNCHECKED_CAST")
        val strategy: SerializationStrategy<OpenAPIV3Model> = when (value) {
            is OpenAPIV30Model -> OpenAPIV30Model.serializer()
        } as SerializationStrategy<OpenAPIV3Model>
        encoder.encodeSerializableValue(strategy, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3Model =
        throw SerializationException(
            "OpenAPIV3Model is dispatched by version string; call OpenAPIV3.decodeFromString instead.",
        )
}
