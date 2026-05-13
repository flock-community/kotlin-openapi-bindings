package community.flock.kotlinx.openapi.bindings

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.serialization.SerializationException
import kotlin.test.Test

class OpenAPIV3StrictModeTests {

    @Test
    fun `webhooks in a 3 dot 0 doc throws`() {
        val doc = """
            {
              "openapi": "3.0.0",
              "info": {"title":"t","version":"1"},
              "paths": {},
              "webhooks": {}
            }
        """.trimIndent()
        // OpenAPIV30Model does not have a webhooks field — strict mode rejects it.
        shouldThrow<SerializationException> { OpenAPIV3.decodeFromString(doc) }
            .message!! shouldContain "webhooks"
    }

    @Test
    fun `dynamicAnchor in a 3 dot 1 schema throws`() {
        val doc = """
            {
              "openapi": "3.1.0",
              "info": {"title":"t","version":"1"},
              "components": {
                "schemas": {
                  "X": { "type": "string", "${'$'}dynamicAnchor": "x" }
                }
              }
            }
        """.trimIndent()
        shouldThrow<SerializationException> { OpenAPIV3.decodeFromString(doc) }
            .message!! shouldContain "dynamicAnchor"
    }

    @Test
    fun `unsupported version throws with version string`() {
        val doc = """{"openapi":"3.3.0","info":{"title":"t","version":"1"}}"""
        shouldThrow<IllegalStateException> { OpenAPIV3.decodeFromString(doc) }
            .message shouldBe "Unsupported OpenAPI version: 3.3.0"
    }

    @Test
    fun `missing openapi field throws`() {
        val doc = """{"info":{"title":"t","version":"1"},"paths":{}}"""
        shouldThrow<IllegalStateException> { OpenAPIV3.decodeFromString(doc) }
            .message shouldBe "No valid openapi v3 element 'openapi' is missing"
    }
}
