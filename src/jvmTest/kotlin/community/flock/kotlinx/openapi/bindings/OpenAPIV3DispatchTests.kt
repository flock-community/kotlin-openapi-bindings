package community.flock.kotlinx.openapi.bindings

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test

class OpenAPIV3DispatchTests {

    @Test
    fun `3 dot 0 doc decodes to OpenAPIV30Model`() {
        val doc = """{"openapi":"3.0.0","info":{"title":"t","version":"1"},"paths":{}}"""
        OpenAPIV3.decodeFromString(doc).shouldBeInstanceOf<OpenAPIV30Model>()
    }

    @Test
    fun `3 dot 1 doc decodes to OpenAPIV31Model`() {
        val doc = """{"openapi":"3.1.0","info":{"title":"t","version":"1"}}"""
        OpenAPIV3.decodeFromString(doc).shouldBeInstanceOf<OpenAPIV31Model>()
    }

    @Test
    fun `3 dot 2 doc throws NotImplementedError until Task 13 wires V32`() {
        val doc = """{"openapi":"3.2.0","info":{"title":"t","version":"1"}}"""
        shouldThrow<NotImplementedError> { OpenAPIV3.decodeFromString(doc) }
    }

    @Test
    fun `missing openapi field throws IllegalStateException`() {
        val doc = """{"info":{"title":"t","version":"1"},"paths":{}}"""
        shouldThrow<IllegalStateException> { OpenAPIV3.decodeFromString(doc) }
            .message shouldBe "No valid openapi v3 element 'openapi' is missing"
    }

    @Test
    fun `unsupported major minor throws with version string`() {
        val doc = """{"openapi":"3.3.0","info":{"title":"t","version":"1"}}"""
        shouldThrow<IllegalStateException> { OpenAPIV3.decodeFromString(doc) }
            .message shouldBe "Unsupported OpenAPI version: 3.3.0"
    }
}
