@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.common.simpleName
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject

object SwaggerCallbacksSerializer : KSerializer<SwaggerCallbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), SwaggerPathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: SwaggerCallbacks) {
        val serializer = MapSerializer(String.serializer(), SwaggerPathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerCallbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return SwaggerCallbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    SwaggerPathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object SwaggerLinksSerializer : KSerializer<SwaggerLinks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), SwaggerLinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: SwaggerLinks) {
        val serializer = MapSerializer(String.serializer(), SwaggerLinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, SwaggerLinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): SwaggerLinks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return SwaggerLinks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    SwaggerLinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object SwaggerResponseOrReferenceSerializer : KSerializer<SwaggerResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(SwaggerResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerResponseOrReference) {
        val serializer = when (value) {
            is SwaggerResponse -> SwaggerResponse.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerResponse.serializer(), tree)
        }
    }
}

object SwaggerLinkOrReferenceSerializer : KSerializer<SwaggerLinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(SwaggerLinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerLinkOrReference) {
        val serializer = when (value) {
            is SwaggerLink -> SwaggerLink.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerLinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerLinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerLink.serializer(), tree)
        }
    }
}

object SwaggerCallbackOrReferenceSerializer : KSerializer<SwaggerCallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(SwaggerCallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerCallbackOrReference) {
        val serializer = when (value) {
            is SwaggerCallbacks -> SwaggerCallbacks.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerCallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerCallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerCallbacks.serializer(), tree)
        }
    }
}

object SwaggerSchemaOrReferenceSerializer : KSerializer<SwaggerSchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(SwaggerSchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerSchemaOrReference) {
        val serializer = when (value) {
            is SwaggerSchema -> SwaggerSchema.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerSchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerSchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerSchema.serializer(), tree)
        }
    }
}

object SwaggerHeaderOrReferenceSerializer : KSerializer<SwaggerHeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(SwaggerHeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerHeaderOrReference) {
        val serializer = when (value) {
            is SwaggerHeader -> SwaggerHeader.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerHeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerHeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerHeader.serializer(), tree)
        }
    }
}

object SwaggerParameterOrReferenceSerializer : KSerializer<SwaggerParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(SwaggerParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerParameterOrReference) {
        val serializer = when (value) {
            is SwaggerParameter -> SwaggerParameter.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerParameter.serializer(), tree)
        }
    }
}

object SwaggerRequestBodyOrReferenceSerializer : KSerializer<SwaggerRequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(SwaggerRequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerRequestBodyOrReference) {
        val serializer = when (value) {
            is SwaggerRequestBody -> SwaggerRequestBody.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerRequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerRequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerRequestBody.serializer(), tree)
        }
    }
}

object SwaggerSecuritySchemeOrReferenceSerializer : KSerializer<SwaggerSecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(SwaggerSecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerSecuritySchemeOrReference) {
        val serializer = when (value) {
            is SwaggerSecurityScheme -> SwaggerSecurityScheme.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerSecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): SwaggerSecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(SwaggerSecurityScheme.serializer(), tree)
        }
    }
}

object SwaggerSchemaOrReferenceOrBooleanSerializer : KSerializer<SwaggerSchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(SwaggerSchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: SwaggerSchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is SwaggerBoolean -> Boolean.serializer()
            is SwaggerSchema -> SwaggerSchema.serializer()
            is SwaggerReference -> SwaggerReference.serializer()
        } as SerializationStrategy<SwaggerSchemaOrReferenceOrBoolean>
        when (value) {
            is SwaggerBoolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): SwaggerSchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> SwaggerBoolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(SwaggerReference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(SwaggerSchema.serializer(), tree)
            }
        }
    }
}
