package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.IO.readFile
import community.flock.kotlinx.openapi.bindings.Version.V2
import community.flock.kotlinx.openapi.bindings.Version.V3
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class OpenAPIV2Tests {

    @Test
    fun petstore() {
        swagger("petstore.json")
    }

    @Test
    fun uber() {
        swagger("uber.json")
    }

    @Test
    fun `swagger is not valid`() {
        val input = readFile("petstore.json", V3)
        shouldThrow<IllegalStateException> {
            OpenAPIV2.decodeFromString(input)
        }.message shouldBe "No valid openapi v2 element 'swagger' is missing"
    }

    @Test
    fun `openapi v3 is not valid`() {
        val input = readFile("petstore.json", V3)
        shouldThrow<IllegalStateException> {
            OpenAPIV2.decodeFromString(input)
        }.message shouldBe "No valid openapi v2 element 'swagger' is missing"
    }

    private fun swagger(fileName: String) {
        readFile(fileName, V2).let {
            it shouldEqualJson it
                .let(OpenAPIV2::decodeFromString)
                .let(OpenAPIV2::encodeToString)
        }
    }
}
