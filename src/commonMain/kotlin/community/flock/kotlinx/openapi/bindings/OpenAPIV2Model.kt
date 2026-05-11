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
 * Sealed umbrella over all supported Swagger 2.x document shapes.
 *
 * Currently only Swagger 2.0 is published, so [OpenAPIV20Model] is the
 * single implementer. The sealed-interface shape mirrors [OpenAPIV3Model]
 * for consistency and leaves room for future 2.x minor versions without
 * breaking the public API again. Selection is driven by the `swagger`
 * version string at parse time — see [OpenAPIV2.decodeFromString].
 */
@Serializable(with = OpenAPIV2ModelSerializer::class)
sealed interface OpenAPIV2Model {
    val swagger: String
}

object OpenAPIV2ModelSerializer : KSerializer<OpenAPIV2Model> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("OpenAPIV2Model", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2Model) {
        @Suppress("UNCHECKED_CAST")
        val strategy: SerializationStrategy<OpenAPIV2Model> = when (value) {
            is OpenAPIV20Model -> OpenAPIV20Model.serializer()
        } as SerializationStrategy<OpenAPIV2Model>
        encoder.encodeSerializableValue(strategy, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2Model = throw SerializationException(
        "OpenAPIV2Model is dispatched by version string; call OpenAPIV2.decodeFromString instead.",
    )
}
