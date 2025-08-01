package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.IO.readFile
import community.flock.kotlinx.openapi.bindings.Version.V2
import community.flock.kotlinx.openapi.bindings.Version.V3
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class OpenAPITests {

    @Test
    fun petstore() = runTest("petstore.json")

    @Test
    fun uber() = runTest("uber.json")

    @Test
    fun `swagger is not valid`() {
        val input = readFile("petstore.json", V3)
        shouldThrow<IllegalStateException> {
            OpenAPI.decodeFromString(input)
        }.message shouldBe "No valid openapi v2 element 'swagger' is missing"
    }

    private fun runTest(fileName: String) {
        readFile(fileName, V2).let {
            it shouldEqualJson it
                .let(OpenAPI::decodeFromString)
                .let(OpenAPI::encodeToString)
        }
    }
}
