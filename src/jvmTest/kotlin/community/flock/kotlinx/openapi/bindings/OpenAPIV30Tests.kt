package community.flock.kotlinx.openapi.bindings

import community.flock.kotlinx.openapi.bindings.IO.readFile
import community.flock.kotlinx.openapi.bindings.Version.V20
import community.flock.kotlinx.openapi.bindings.Version.V30
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class OpenAPIV30Tests {

    @Test
    fun api_with_examples() = openAPIv3("api-with-examples.json")

    @Test
    fun callback_example() = openAPIv3("callback-example.json")

    @Test
    fun gh() = openAPIv3("gh.json")

    @Test
    fun keto() = openAPIv3("keto.json")

    @Test
    fun link_example() = openAPIv3("link-example.json")

    @Test
    fun petstore() = openAPIv3("petstore.json")

    @Test
    fun petstore_custom() = openAPIv3("petstore_custom.json")

    @Test
    fun stripe() = openAPIv3("stripe.json")

    @Test
    fun uspto() = openAPIv3("uspto.json")

    @Test
    fun workday() = openAPIv3("workday.json")

    @Test
    fun `tempo-core`() = openAPIv3("tempo-core.json")

    // mastodon.json is a 3.1.0 doc — moved to V31 in Task 9.

    @Test
    fun `openapi v2 is not valid`() {
        val input = readFile("petstore.json", V20)
        shouldThrow<IllegalStateException> {
            OpenAPIV3.decodeFromString(input)
        }.message shouldBe "No valid openapi v3 element 'openapi' is missing"
    }

    private fun openAPIv3(fileName: String) {
        readFile(fileName, V30).let {
            it shouldEqualJson it
                .let(OpenAPIV3::decodeFromString)
                .let(OpenAPIV3::encodeToString)
        }
    }
}
