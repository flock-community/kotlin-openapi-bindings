@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

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

object OpenAPIV20CallbacksSerializer : KSerializer<OpenAPIV20Callbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV20PathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV20Callbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV20PathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20Callbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV20Callbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV20PathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV20LinksSerializer : KSerializer<OpenAPIV20Links> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV20LinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV20Links) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV20LinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPIV20LinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20Links {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV20Links(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV20LinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV20ResponseOrReferenceSerializer : KSerializer<OpenAPIV20ResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV20ResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20ResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIV20Response -> OpenAPIV20Response.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20ResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20ResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20Response.serializer(), tree)
        }
    }
}

object OpenAPIV20LinkOrReferenceSerializer : KSerializer<OpenAPIV20LinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV20LinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20LinkOrReference) {
        val serializer = when (value) {
            is OpenAPIV20Link -> OpenAPIV20Link.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20LinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20LinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20Link.serializer(), tree)
        }
    }
}

object OpenAPIV20CallbackOrReferenceSerializer : KSerializer<OpenAPIV20CallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV20CallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20CallbackOrReference) {
        val serializer = when (value) {
            is OpenAPIV20Callbacks -> OpenAPIV20Callbacks.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20CallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20CallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20Callbacks.serializer(), tree)
        }
    }
}

object OpenAPIV20SchemaOrReferenceSerializer : KSerializer<OpenAPIV20SchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV20SchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20SchemaOrReference) {
        val serializer = when (value) {
            is OpenAPIV20Schema -> OpenAPIV20Schema.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20SchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20SchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20Schema.serializer(), tree)
        }
    }
}

object OpenAPIV20HeaderOrReferenceSerializer : KSerializer<OpenAPIV20HeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV20HeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20HeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIV20Header -> OpenAPIV20Header.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20HeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20HeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20Header.serializer(), tree)
        }
    }
}

object OpenAPIV20ParameterOrReferenceSerializer : KSerializer<OpenAPIV20ParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV20ParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20ParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIV20Parameter -> OpenAPIV20Parameter.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20ParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20ParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20Parameter.serializer(), tree)
        }
    }
}

object OpenAPIV20RequestBodyOrReferenceSerializer : KSerializer<OpenAPIV20RequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV20RequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20RequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIV20RequestBody -> OpenAPIV20RequestBody.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20RequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20RequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20RequestBody.serializer(), tree)
        }
    }
}

object OpenAPIV20SecuritySchemeOrReferenceSerializer : KSerializer<OpenAPIV20SecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV20SecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20SecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPIV20SecurityScheme -> OpenAPIV20SecurityScheme.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20SecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20SecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV20SecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPIV20SchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPIV20SchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV20SchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV20SchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIV20Boolean -> Boolean.serializer()
            is OpenAPIV20Schema -> OpenAPIV20Schema.serializer()
            is OpenAPIV20Reference -> OpenAPIV20Reference.serializer()
        } as SerializationStrategy<OpenAPIV20SchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIV20Boolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV20SchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIV20Boolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV20Reference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPIV20Schema.serializer(), tree)
            }
        }
    }
}
