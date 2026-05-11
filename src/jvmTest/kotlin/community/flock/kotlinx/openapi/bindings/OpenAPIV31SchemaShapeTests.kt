package community.flock.kotlinx.openapi.bindings

import io.kotest.matchers.shouldBe
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
}
