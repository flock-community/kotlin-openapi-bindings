package community.flock.kotlinx.openapi.bindings.v3

import community.flock.kotlinx.openapi.bindings.IO.readFile
import community.flock.kotlinx.openapi.bindings.Version.V2
import community.flock.kotlinx.openapi.bindings.Version.V3
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class OpenAPITests {

    @Test
    fun api_with_examples() = runTest("api-with-examples.json")

    @Test
    fun callback_example() = runTest("callback-example.json")

    @Test
    fun gh() = runTest("gh.json")

    @Test
    fun keto() = runTest("keto.json")

    @Test
    fun link_example() = runTest("link-example.json")

    @Test
    fun petstore() = runTest("petstore.json")

    @Test
    fun petstore_custom() = runTest("petstore_custom.json")

    @Test
    fun stripe() = runTest("stripe.json")

    @Test
    fun uspto() = runTest("uspto.json")

    @Test
    fun workday() = runTest("workday.json")

    @Test
    fun `tempo-core`() = runTest("tempo-core.json")

    @Test
    fun `openapi is not valid`() {
        val input = readFile("petstore.json", V2)
        shouldThrow<IllegalStateException> {
            OpenAPI.decodeFromString(input)
        }.message shouldBe "No valid openapi v3 element 'openapi' is missing"
    }

    private fun runTest(fileName: String) {
        readFile(fileName, V3).let {
            it shouldEqualJson it
                .let(OpenAPI::decodeFromString)
                .let(OpenAPI::encodeToString)
        }
    }
}
