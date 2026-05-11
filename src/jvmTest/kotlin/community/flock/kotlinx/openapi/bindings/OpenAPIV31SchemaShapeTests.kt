package community.flock.kotlinx.openapi.bindings

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.serialization.json.Json
import kotlin.test.Test

class OpenAPIV31SchemaShapeTests {

    private val json = Json { prettyPrint = true }

    @Test
    fun `exclusiveMinimum and exclusiveMaximum decode as Double`() {
        val src = """{"type":"number","exclusiveMinimum":0.0,"exclusiveMaximum":10.0}"""
        val schema = json.decodeFromString(OpenAPIV31Schema.serializer(), src)
        schema.exclusiveMinimum shouldBe 0.0
        schema.exclusiveMaximum shouldBe 10.0
    }

    @Test
    fun `nullable field is absent (replaced by type array)`() {
        // type can be ["string","null"] — already supported via OpenAPIV3TypeDefinition.
        val src = """{"type":["string","null"]}"""
        json.decodeFromString(OpenAPIV31Schema.serializer(), src) // should not throw
    }

    @Test
    fun `2020-12 keywords decode`() {
        val src = """
            {
              "type": "object",
              "const": {"k":"v"},
              "prefixItems": [{"type":"string"}, {"type":"number"}],
              "contentEncoding": "base64",
              "contentMediaType": "image/png",
              "contentSchema": {"type":"string"},
              "dependentRequired": {"a": ["b","c"]},
              "dependentSchemas": {"a": {"required": ["b"]}},
              "unevaluatedProperties": false,
              "unevaluatedItems": {"type":"string"},
              "${'$'}defs": {"X": {"type":"string"}}
            }
        """.trimIndent()
        val schema = json.decodeFromString(OpenAPIV31Schema.serializer(), src)
        schema.const shouldNotBe null
        schema.prefixItems?.size shouldBe 2
        schema.contentEncoding shouldBe "base64"
        schema.contentMediaType shouldBe "image/png"
        schema.contentSchema shouldNotBe null
        schema.dependentRequired?.get("a") shouldBe listOf("b", "c")
        schema.dependentSchemas?.get("a") shouldNotBe null
        schema.unevaluatedProperties shouldNotBe null
        schema.unevaluatedItems shouldNotBe null
        schema.defs?.get("X") shouldNotBe null
    }
}
