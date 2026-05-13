@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object OpenAPIV31CallbacksSerializer : KSerializer<OpenAPIV31Callbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV31PathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV31Callbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV31PathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31Callbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV31Callbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV31PathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV31LinksSerializer : KSerializer<OpenAPIV31Links> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV31LinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV31Links) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV31LinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPIV31LinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31Links {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV31Links(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV31LinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV31ResponseOrReferenceSerializer : KSerializer<OpenAPIV31ResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31ResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31ResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Response -> OpenAPIV31Response.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31ResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31ResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Response.serializer(), tree)
        }
    }
}

object OpenAPIV31LinkOrReferenceSerializer : KSerializer<OpenAPIV31LinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV31LinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31LinkOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Link -> OpenAPIV31Link.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31LinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31LinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Link.serializer(), tree)
        }
    }
}

object OpenAPIV31CallbackOrReferenceSerializer : KSerializer<OpenAPIV31CallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31CallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31CallbackOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Callbacks -> OpenAPIV31Callbacks.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31CallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31CallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Callbacks.serializer(), tree)
        }
    }
}

object OpenAPIV31SchemaOrReferenceSerializer : KSerializer<OpenAPIV31SchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV31SchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31SchemaOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Schema -> OpenAPIV31Schema.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31SchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31SchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Schema.serializer(), tree)
        }
    }
}

object OpenAPIV31HeaderOrReferenceSerializer : KSerializer<OpenAPIV31HeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV31HeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31HeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Header -> OpenAPIV31Header.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31HeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31HeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Header.serializer(), tree)
        }
    }
}

object OpenAPIV31ParameterOrReferenceSerializer : KSerializer<OpenAPIV31ParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31ParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31ParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Parameter -> OpenAPIV31Parameter.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31ParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31ParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Parameter.serializer(), tree)
        }
    }
}

object OpenAPIV31ExampleOrReferenceSerializer : KSerializer<OpenAPIV31ExampleOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31ExampleOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31ExampleOrReference) {
        val serializer = when (value) {
            is OpenAPIV31Example -> OpenAPIV31Example.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31ExampleOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31ExampleOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31Example.serializer(), tree)
        }
    }
}

object OpenAPIV31RequestBodyOrReferenceSerializer : KSerializer<OpenAPIV31RequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31RequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31RequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIV31RequestBody -> OpenAPIV31RequestBody.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31RequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31RequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31RequestBody.serializer(), tree)
        }
    }
}

object OpenAPIV31SecuritySchemeOrReferenceSerializer : KSerializer<OpenAPIV31SecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31SecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31SecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPIV31SecurityScheme -> OpenAPIV31SecurityScheme.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31SecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31SecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31SecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPIV31SchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPIV31SchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31SchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31SchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIV31Boolean -> Boolean.serializer()
            is OpenAPIV31Schema -> OpenAPIV31Schema.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31SchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIV31Boolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31SchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIV31Boolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPIV31Schema.serializer(), tree)
            }
        }
    }
}

object OpenAPIV31PathItemOrReferenceSerializer : KSerializer<OpenAPIV31PathItemOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV31PathItemOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31PathItemOrReference) {
        val serializer = when (value) {
            is OpenAPIV31PathItem -> OpenAPIV31PathItem.serializer()
            is OpenAPIV31Reference -> OpenAPIV31Reference.serializer()
        } as SerializationStrategy<OpenAPIV31PathItemOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31PathItemOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV31Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV31PathItem.serializer(), tree)
        }
    }
}

object OpenAPIV31TypeDefinitionSerializer : KSerializer<OpenAPIV31TypeDefinition> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("OpenAPIV31TypeDefinition", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV31TypeDefinition) {
        val output = encoder as? JsonEncoder ?: throw SerializationException("This class can be saved only by Json")
        when (value) {
            is OpenAPIV31SingleType -> output.encodeSerializableValue(OpenAPIV31Type.serializer(), value.value)
            is OpenAPIV31TypeArray -> output.encodeSerializableValue(ListSerializer(OpenAPIV31Type.serializer()), value.values)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV31TypeDefinition {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> OpenAPIV31SingleType(
                input.json.decodeFromJsonElement(OpenAPIV31Type.serializer(), element),
            )
            is JsonArray -> OpenAPIV31TypeArray(
                element.map { input.json.decodeFromJsonElement(OpenAPIV31Type.serializer(), it.jsonPrimitive) },
            )
            else -> throw SerializationException("Expected string or array for type, got: $element")
        }
    }
}
