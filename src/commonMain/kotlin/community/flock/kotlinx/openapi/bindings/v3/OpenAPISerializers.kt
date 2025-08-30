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

object OpenAPICallbacksSerializer : KSerializer<OpenAPICallbacks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPIPathItem.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPICallbacks) {
        val serializer = MapSerializer(String.serializer(), OpenAPIPathItem.serializer())
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPICallbacks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPICallbacks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPIPathItem.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPILinksSerializer : KSerializer<OpenAPILinks> {

    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), OpenAPILinkOrReference.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: OpenAPILinks) {
        val serializer = MapSerializer(String.serializer(), OpenAPILinkOrReference.serializer())
        encoder.encodeSerializableValue(serializer, value as Map<String, OpenAPILinkOrReference>)
    }

    override fun deserialize(decoder: Decoder): OpenAPILinks {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return OpenAPILinks(
            tree.mapValues {
                input.json.decodeFromJsonElement(
                    OpenAPILinkOrReference.serializer(),
                    it.value,
                )
            }.entries,
        )
    }
}

object OpenAPIResponseOrReferenceSerializer : KSerializer<OpenAPIResponseOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIResponseOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIResponseOrReference) {
        val serializer = when (value) {
            is OpenAPIResponse -> OpenAPIResponse.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPIResponseOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIResponseOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIResponse.serializer(), tree)
        }
    }
}

object OpenAPILinkOrReferenceSerializer : KSerializer<OpenAPILinkOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPILinkOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPILinkOrReference) {
        val serializer = when (value) {
            is OpenAPILink -> OpenAPILink.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPILinkOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPILinkOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPILink.serializer(), tree)
        }
    }
}

object OpenAPICallbackOrReferenceSerializer : KSerializer<OpenAPICallbackOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPICallbackOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPICallbackOrReference) {
        val serializer = when (value) {
            is OpenAPICallbacks -> OpenAPICallbacks.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPICallbackOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPICallbackOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPICallbacks.serializer(), tree)
        }
    }
}

object OpenAPISchemaOrReferenceSerializer : KSerializer<OpenAPISchemaOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPISchemaOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPISchemaOrReference) {
        val serializer = when (value) {
            is OpenAPISchema -> OpenAPISchema.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPISchemaOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPISchemaOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPISchema.serializer(), tree)
        }
    }
}

object OpenAPIHeaderOrReferenceSerializer : KSerializer<OpenAPIHeaderOrReference> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(OpenAPIHeaderOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIHeaderOrReference) {
        val serializer = when (value) {
            is OpenAPIHeader -> OpenAPIHeader.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPIHeaderOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIHeaderOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIHeader.serializer(), tree)
        }
    }
}

object OpenAPIParameterOrReferenceSerializer : KSerializer<OpenAPIParameterOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIParameterOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIParameterOrReference) {
        val serializer = when (value) {
            is OpenAPIParameter -> OpenAPIParameter.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPIParameterOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIParameterOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIParameter.serializer(), tree)
        }
    }
}

object OpenAPIExampleOrReferenceSerializer : KSerializer<OpenAPIExampleOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIExampleOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIExampleOrReference) {
        val serializer = when (value) {
            is OpenAPIExample -> OpenAPIExample.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPIExampleOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIExampleOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIExample.serializer(), tree)
        }
    }
}

object OpenAPIRequestBodyOrReferenceSerializer : KSerializer<OpenAPIRequestBodyOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPIRequestBodyOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPIRequestBodyOrReference) {
        val serializer = when (value) {
            is OpenAPIRequestBody -> OpenAPIRequestBody.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPIRequestBodyOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPIRequestBodyOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPIRequestBody.serializer(), tree)
        }
    }
}

object OpenAPISecuritySchemeOrReferenceSerializer : KSerializer<OpenAPISecuritySchemeOrReference> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPISecuritySchemeOrReference.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPISecuritySchemeOrReference) {
        val serializer = when (value) {
            is OpenAPISecurityScheme -> OpenAPISecurityScheme.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPISecuritySchemeOrReference>
        encoder.encodeSerializableValue(serializer, value)
    }

    override fun deserialize(decoder: Decoder): OpenAPISecuritySchemeOrReference {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement().jsonObject
        return when {
            tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
            else -> input.json.decodeFromJsonElement(OpenAPISecurityScheme.serializer(), tree)
        }
    }
}

object OpenAPISchemaOrReferenceOrBooleanSerializer : KSerializer<OpenAPISchemaOrReferenceOrBoolean> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor(OpenAPISchemaOrReferenceOrBoolean.simpleName, PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: OpenAPISchemaOrReferenceOrBoolean) {
        val serializer = when (value) {
            is OpenAPIBoolean -> Boolean.serializer()
            is OpenAPISchema -> OpenAPISchema.serializer()
            is OpenAPIReference -> OpenAPIReference.serializer()
        } as SerializationStrategy<OpenAPISchemaOrReferenceOrBoolean>
        when (value) {
            is OpenAPIBoolean -> encoder.encodeSerializableValue(Boolean.serializer(), value.value)
            else -> encoder.encodeSerializableValue(serializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): OpenAPISchemaOrReferenceOrBoolean {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement()

        return when (tree) {
            is JsonPrimitive -> OpenAPIBoolean(tree.boolean)
            is JsonArray -> TODO()
            is JsonObject -> when {
                tree.containsKey("\$ref") -> input.json.decodeFromJsonElement(OpenAPIReference.serializer(), tree)
                else -> input.json.decodeFromJsonElement(OpenAPISchema.serializer(), tree)
            }
        }
    }
}
