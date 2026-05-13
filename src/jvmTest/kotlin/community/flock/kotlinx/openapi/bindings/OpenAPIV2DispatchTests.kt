package community.flock.kotlinx.openapi.bindings

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test

class OpenAPIV2DispatchTests {

    @Test
    fun `2 dot 0 doc decodes to OpenAPIV20Model`() {
        val doc = """{"swagger":"2.0","info":{"title":"t","version":"1"},"paths":{}}"""
        OpenAPIV2.decodeFromString(doc).shouldBeInstanceOf<OpenAPIV20Model>()
    }

    @Test
    fun `missing swagger field throws IllegalStateException`() {
        val doc = """{"info":{"title":"t","version":"1"},"paths":{}}"""
        shouldThrow<IllegalStateException> { OpenAPIV2.decodeFromString(doc) }
            .message shouldBe "No valid openapi v2 element 'swagger' is missing"
    }

    @Test
    fun `unsupported major minor throws with version string`() {
        val doc = """{"swagger":"2.1","info":{"title":"t","version":"1"},"paths":{}}"""
        shouldThrow<IllegalStateException> { OpenAPIV2.decodeFromString(doc) }
            .message shouldBe "Unsupported Swagger version: 2.1"
    }

    @Test
    fun `malformed swagger version throws`() {
        val doc = """{"swagger":"not-a-version","info":{"title":"t","version":"1"},"paths":{}}"""
        shouldThrow<IllegalStateException> { OpenAPIV2.decodeFromString(doc) }
            .message shouldBe "Unsupported Swagger version: not-a-version"
    }
}
