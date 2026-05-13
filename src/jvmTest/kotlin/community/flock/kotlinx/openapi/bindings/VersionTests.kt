package community.flock.kotlinx.openapi.bindings

import community.flock.kotlinx.openapi.bindings.Version.V30
import community.flock.kotlinx.openapi.bindings.Version.V31
import community.flock.kotlinx.openapi.bindings.Version.V32
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class VersionTests {

    @Test
    fun `parses 3 dot 0 dot x as V30`() {
        Version.fromOpenApiString("3.0.0") shouldBe V30
        Version.fromOpenApiString("3.0.3") shouldBe V30
    }

    @Test
    fun `parses 3 dot 1 dot x as V31`() {
        Version.fromOpenApiString("3.1.0") shouldBe V31
        Version.fromOpenApiString("3.1.1") shouldBe V31
    }

    @Test
    fun `parses 3 dot 2 dot x as V32`() {
        Version.fromOpenApiString("3.2.0") shouldBe V32
    }

    @Test
    fun `rejects unsupported major minor`() {
        shouldThrow<IllegalStateException> {
            Version.fromOpenApiString("3.3.0")
        }.message shouldBe "Unsupported OpenAPI version: 3.3.0"

        shouldThrow<IllegalStateException> {
            Version.fromOpenApiString("4.0.0")
        }.message shouldBe "Unsupported OpenAPI version: 4.0.0"
    }

    @Test
    fun `rejects malformed version string`() {
        shouldThrow<IllegalStateException> {
            Version.fromOpenApiString("not-a-version")
        }.message shouldBe "Unsupported OpenAPI version: not-a-version"
    }
}
