@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings.v3

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

object OpenAPIV3CallbacksSerializer : KSerializer<OpenAPIV3Callbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV3PathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV3Callbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV3PathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3Callbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV3Callbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV3PathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV3LinksSerializer : KSerializer<OpenAPIV3Links> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV3LinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV3Links) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV3LinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPIV3LinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3Links {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV3Links(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV3LinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV3ResponseOrReferenceSerializer : KSerializer<OpenAPIV3ResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3ResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3ResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Response -> OpenAPIV3Response.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3ResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3ResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Response.serializer(), tree)
        }
    }
}

object OpenAPIV3LinkOrReferenceSerializer : KSerializer<OpenAPIV3LinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV3LinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3LinkOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Link -> OpenAPIV3Link.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3LinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3LinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Link.serializer(), tree)
        }
    }
}

object OpenAPIV3CallbackOrReferenceSerializer : KSerializer<OpenAPIV3CallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3CallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3CallbackOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Callbacks -> OpenAPIV3Callbacks.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3CallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3CallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Callbacks.serializer(), tree)
        }
    }
}

object OpenAPIV3SchemaOrReferenceSerializer : KSerializer<OpenAPIV3SchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV3SchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3SchemaOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Schema -> OpenAPIV3Schema.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3SchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3SchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Schema.serializer(), tree)
        }
    }
}

object OpenAPIV3HeaderOrReferenceSerializer : KSerializer<OpenAPIV3HeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV3HeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3HeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Header -> OpenAPIV3Header.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3HeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3HeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Header.serializer(), tree)
        }
    }
}

object OpenAPIV3ParameterOrReferenceSerializer : KSerializer<OpenAPIV3ParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3ParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3ParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Parameter -> OpenAPIV3Parameter.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3ParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3ParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Parameter.serializer(), tree)
        }
    }
}

object OpenAPIV3ExampleOrReferenceSerializer : KSerializer<OpenAPIV3ExampleOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3ExampleOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3ExampleOrReference) {
        val serializer = when (value) {
            is OpenAPIV3Example -> OpenAPIV3Example.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3ExampleOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3ExampleOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3Example.serializer(), tree)
        }
    }
}

object OpenAPIV3RequestBodyOrReferenceSerializer : KSerializer<OpenAPIV3RequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3RequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3RequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIV3RequestBody -> OpenAPIV3RequestBody.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3RequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3RequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3RequestBody.serializer(), tree)
        }
    }
}

object OpenAPIV3SecuritySchemeOrReferenceSerializer : KSerializer<OpenAPIV3SecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3SecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3SecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPIV3SecurityScheme -> OpenAPIV3SecurityScheme.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3SecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3SecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV3SecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPIV3SchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPIV3SchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV3SchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV3SchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIV3Boolean -> Boolean.serializer()
            is OpenAPIV3Schema -> OpenAPIV3Schema.serializer()
            is OpenAPIV3Reference -> OpenAPIV3Reference.serializer()
        } as SerializationStrategy<OpenAPIV3SchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIV3Boolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV3SchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIV3Boolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV3Reference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPIV3Schema.serializer(), tree)
            }
        }
    }
}
