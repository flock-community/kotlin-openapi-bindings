package community.flock.kotlinx.openapi.bindings

import io.kotest.assertions.json.shouldEqualJson

import kotlin.test.Test

expect object IO {
    fun readFile(fileName: String):String
}

class OpenAPITests {

    @Test
    fun `api_with_examples`() = runTest("api-with-examples.json")

    @Test
    fun `callback_example`() = runTest("callback-example.json")

    @Test
    fun `gh`() = runTest("gh.json")

    @Test
    fun `link_example`() = runTest("link-example.json")

    @Test
    fun `petstore`() = runTest("petstore.json")

    @Test
    fun `stripe`() = runTest("stripe.json")

    @Test
    fun `uspto`() = runTest("uspto.json")

    private fun runTest(fileName:String) {
        val input = IO.readFile(fileName)
        val openapi = OpenAPI.decodeFromString(input)
        val string = OpenAPI.encodeToString(openapi)
        input shouldEqualJson string
    }
}




