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

object OpenAPIV32CallbacksSerializer : KSerializer<OpenAPIV32Callbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV32PathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV32Callbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV32PathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32Callbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV32Callbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV32PathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV32LinksSerializer : KSerializer<OpenAPIV32Links> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIV32LinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPIV32Links) {
        val serializer = MapSerializer(String.serializer(), OpenAPIV32LinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPIV32LinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32Links {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPIV32Links(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIV32LinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIV32ResponseOrReferenceSerializer : KSerializer<OpenAPIV32ResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32ResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32ResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Response -> OpenAPIV32Response.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32ResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32ResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Response.serializer(), tree)
        }
    }
}

object OpenAPIV32LinkOrReferenceSerializer : KSerializer<OpenAPIV32LinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV32LinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32LinkOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Link -> OpenAPIV32Link.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32LinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32LinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Link.serializer(), tree)
        }
    }
}

object OpenAPIV32CallbackOrReferenceSerializer : KSerializer<OpenAPIV32CallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32CallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32CallbackOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Callbacks -> OpenAPIV32Callbacks.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32CallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32CallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Callbacks.serializer(), tree)
        }
    }
}

object OpenAPIV32SchemaOrReferenceSerializer : KSerializer<OpenAPIV32SchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV32SchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32SchemaOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Schema -> OpenAPIV32Schema.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32SchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32SchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Schema.serializer(), tree)
        }
    }
}

object OpenAPIV32HeaderOrReferenceSerializer : KSerializer<OpenAPIV32HeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIV32HeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32HeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Header -> OpenAPIV32Header.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32HeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32HeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Header.serializer(), tree)
        }
    }
}

object OpenAPIV32ParameterOrReferenceSerializer : KSerializer<OpenAPIV32ParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32ParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32ParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Parameter -> OpenAPIV32Parameter.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32ParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32ParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Parameter.serializer(), tree)
        }
    }
}

object OpenAPIV32ExampleOrReferenceSerializer : KSerializer<OpenAPIV32ExampleOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32ExampleOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32ExampleOrReference) {
        val serializer = when (value) {
            is OpenAPIV32Example -> OpenAPIV32Example.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32ExampleOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32ExampleOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32Example.serializer(), tree)
        }
    }
}

object OpenAPIV32RequestBodyOrReferenceSerializer : KSerializer<OpenAPIV32RequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32RequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32RequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIV32RequestBody -> OpenAPIV32RequestBody.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32RequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32RequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32RequestBody.serializer(), tree)
        }
    }
}

object OpenAPIV32SecuritySchemeOrReferenceSerializer : KSerializer<OpenAPIV32SecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32SecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32SecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPIV32SecurityScheme -> OpenAPIV32SecurityScheme.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32SecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32SecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32SecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPIV32SchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPIV32SchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32SchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32SchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIV32Boolean -> Boolean.serializer()
            is OpenAPIV32Schema -> OpenAPIV32Schema.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32SchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIV32Boolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32SchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIV32Boolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPIV32Schema.serializer(), tree)
            }
        }
    }
}

object OpenAPIV32PathItemOrReferenceSerializer : KSerializer<OpenAPIV32PathItemOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIV32PathItemOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32PathItemOrReference) {
        val serializer = when (value) {
            is OpenAPIV32PathItem -> OpenAPIV32PathItem.serializer()
            is OpenAPIV32Reference -> OpenAPIV32Reference.serializer()
        } as SerializationStrategy<OpenAPIV32PathItemOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32PathItemOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIV32Reference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIV32PathItem.serializer(), tree)
        }
    }
}

object OpenAPIV32TypeDefinitionSerializer : KSerializer<OpenAPIV32TypeDefinition> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("OpenAPIV32TypeDefinition", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIV32TypeDefinition) {
        val output = encoder as? JsonEncoder ?: throw SerializationException("This class can be saved only by Json")
        when (value) {
            is OpenAPIV32SingleType -> output.encodeSerializableValue(OpenAPIV32Type.serializer(), value.value)
            is OpenAPIV32TypeArray -> output.encodeSerializableValue(ListSerializer(OpenAPIV32Type.serializer()), value.values)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPIV32TypeDefinition {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> OpenAPIV32SingleType(
                input.json.decodeFromJsonElement(OpenAPIV32Type.serializer(), element),
            )
            is JsonArray -> OpenAPIV32TypeArray(
                element.map { input.json.decodeFromJsonElement(OpenAPIV32Type.serializer(), it.jsonPrimitive) },
            )
            else -> throw SerializationException("Expected string or array for type, got: $element")
        }
    }
}
