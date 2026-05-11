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

object OpenAPIV30CallbacksSerializer : KSerializer<OpenAPIV30Callbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV30PathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV30Callbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV30PathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30Callbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV30Callbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV30PathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV30LinksSerializer : KSerializer<OpenAPIV30Links> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV30LinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV30Links) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV30LinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPIV30LinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30Links {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV30Links(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV30LinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV30ResponseOrReferenceSerializer : KSerializer<OpenAPIV30ResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30ResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30ResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Response -> OpenAPIV30Response.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30ResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30ResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Response.serializer(), tree)
        }
    }
}

object OpenAPIV30LinkOrReferenceSerializer : KSerializer<OpenAPIV30LinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV30LinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30LinkOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Link -> OpenAPIV30Link.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30LinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30LinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Link.serializer(), tree)
        }
    }
}

object OpenAPIV30CallbackOrReferenceSerializer : KSerializer<OpenAPIV30CallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30CallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30CallbackOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Callbacks -> OpenAPIV30Callbacks.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30CallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30CallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Callbacks.serializer(), tree)
        }
    }
}

object OpenAPIV30SchemaOrReferenceSerializer : KSerializer<OpenAPIV30SchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV30SchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30SchemaOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Schema -> OpenAPIV30Schema.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30SchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30SchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Schema.serializer(), tree)
        }
    }
}

object OpenAPIV30HeaderOrReferenceSerializer : KSerializer<OpenAPIV30HeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV30HeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30HeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Header -> OpenAPIV30Header.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30HeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30HeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Header.serializer(), tree)
        }
    }
}

object OpenAPIV30ParameterOrReferenceSerializer : KSerializer<OpenAPIV30ParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30ParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30ParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Parameter -> OpenAPIV30Parameter.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30ParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30ParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Parameter.serializer(), tree)
        }
    }
}

object OpenAPIV30ExampleOrReferenceSerializer : KSerializer<OpenAPIV30ExampleOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30ExampleOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30ExampleOrReference) {
        val serializer = when (value) {
            is OpenAPIV30Example -> OpenAPIV30Example.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30ExampleOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30ExampleOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30Example.serializer(), tree)
        }
    }
}

object OpenAPIV30RequestBodyOrReferenceSerializer : KSerializer<OpenAPIV30RequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30RequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30RequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIV30RequestBody -> OpenAPIV30RequestBody.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30RequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30RequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30RequestBody.serializer(), tree)
        }
    }
}

object OpenAPIV30SecuritySchemeOrReferenceSerializer : KSerializer<OpenAPIV30SecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30SecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30SecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPIV30SecurityScheme -> OpenAPIV30SecurityScheme.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30SecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30SecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV30SecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPIV30SchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPIV30SchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV30SchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30SchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIV30Boolean -> Boolean.serializer()
            is OpenAPIV30Schema -> OpenAPIV30Schema.serializer()
            is OpenAPIV30Reference -> OpenAPIV30Reference.serializer()
        } as SerializationStrategy<OpenAPIV30SchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIV30Boolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30SchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIV30Boolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV30Reference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPIV30Schema.serializer(), tree)
            }
        }
    }
}

object OpenAPIV30TypeDefinitionSerializer : KSerializer<OpenAPIV30TypeDefinition> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("OpenAPIV30TypeDefinition", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV30TypeDefinition) {
        val output = encoder as? JsonEncoder ?: throw SerializationException("This class can be saved only by Json")
        when (value) {
            is OpenAPIV30SingleType -> output.encodeSerializableValue(OpenAPIV30Type.serializer(), value.value)
            is OpenAPIV30TypeArray -> output.encodeSerializableValue(ListSerializer(OpenAPIV30Type.serializer()), value.values)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV30TypeDefinition {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> OpenAPIV30SingleType(
                input.json.decodeFromJsonElement(OpenAPIV30Type.serializer(), element),
            )
            is JsonArray -> OpenAPIV30TypeArray(
                element.map { input.json.decodeFromJsonElement(OpenAPIV30Type.serializer(), it.jsonPrimitive) },
            )
            else -> throw SerializationException("Expected string or array for type, got: $element")
        }
    }
}
