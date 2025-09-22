package community.flock.kotlinx.openapi.bindings

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
        val input = IO.readFile("petstore.json", Version.V3)
        shouldThrow<IllegalStateException> {
            OpenAPIV2.decodeFromString(input)
        }.message shouldBe "No valid openapi v2 element 'swagger' is missing"
    }

    @Test
    fun `openapi v3 is not valid`() {
        val input = IO.readFile("petstore.json", Version.V3)
        shouldThrow<IllegalStateException> {
            OpenAPIV2.decodeFromString(input)
        }.message shouldBe "No valid openapi v2 element 'swagger' is missing"
    }

    private fun swagger(fileName: String) {
        IO.readFile(fileName, Version.V2).let {
            it shouldEqualJson it
                .let(OpenAPIV2::decodeFromString)
                .let(OpenAPIV2::encodeToString)
        }
    }
}
