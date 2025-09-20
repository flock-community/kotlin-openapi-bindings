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

object OpenAPIV2CallbacksSerializer : KSerializer<OpenAPIV2Callbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV2PathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV2Callbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV2PathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2Callbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV2Callbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV2PathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV2LinksSerializer : KSerializer<OpenAPIV2Links> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV2LinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV2Links) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV2LinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPIV2LinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2Links {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV2Links(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV2LinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV2ResponseOrReferenceSerializer : KSerializer<OpenAPIV2ResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV2ResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2ResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIV2Response -> OpenAPIV2Response.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2ResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2ResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2Response.serializer(), tree)
        }
    }
}

object OpenAPIV2LinkOrReferenceSerializer : KSerializer<OpenAPIV2LinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV2LinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2LinkOrReference) {
        val serializer = when (value) {
            is OpenAPIV2Link -> OpenAPIV2Link.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2LinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2LinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2Link.serializer(), tree)
        }
    }
}

object OpenAPIV2CallbackOrReferenceSerializer : KSerializer<OpenAPIV2CallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV2CallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2CallbackOrReference) {
        val serializer = when (value) {
            is OpenAPIV2Callbacks -> OpenAPIV2Callbacks.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2CallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2CallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2Callbacks.serializer(), tree)
        }
    }
}

object OpenAPIV2SchemaOrReferenceSerializer : KSerializer<OpenAPIV2SchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV2SchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2SchemaOrReference) {
        val serializer = when (value) {
            is OpenAPIV2Schema -> OpenAPIV2Schema.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2SchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2SchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2Schema.serializer(), tree)
        }
    }
}

object OpenAPIV2HeaderOrReferenceSerializer : KSerializer<OpenAPIV2HeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV2HeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2HeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIV2Header -> OpenAPIV2Header.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2HeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2HeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2Header.serializer(), tree)
        }
    }
}

object OpenAPIV2ParameterOrReferenceSerializer : KSerializer<OpenAPIV2ParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV2ParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2ParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIV2Parameter -> OpenAPIV2Parameter.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2ParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2ParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2Parameter.serializer(), tree)
        }
    }
}

object OpenAPIV2RequestBodyOrReferenceSerializer : KSerializer<OpenAPIV2RequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV2RequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2RequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIV2RequestBody -> OpenAPIV2RequestBody.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2RequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2RequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2RequestBody.serializer(), tree)
        }
    }
}

object OpenAPIV2SecuritySchemeOrReferenceSerializer : KSerializer<OpenAPIV2SecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV2SecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2SecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPIV2SecurityScheme -> OpenAPIV2SecurityScheme.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2SecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2SecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV2SecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPIV2SchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPIV2SchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV2SchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV2SchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIV2Boolean -> Boolean.serializer()
            is OpenAPIV2Schema -> OpenAPIV2Schema.serializer()
            is OpenAPIV2Reference -> OpenAPIV2Reference.serializer()
        } as SerializationStrategy<OpenAPIV2SchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIV2Boolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV2SchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIV2Boolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV2Reference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPIV2Schema.serializer(), tree)
            }
        }
    }
}
