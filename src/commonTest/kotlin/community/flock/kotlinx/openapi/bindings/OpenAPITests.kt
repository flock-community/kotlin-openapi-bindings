package community.flock.kotlinx.openapi.bindings

import io.kotest.assertions.json.shouldEqualJson

import java.io.File
import kotlin.test.Test

class OpenAPITests {

    @Test
    fun `api-with-examples`() = runTest("api-with-examples.json")

    @Test
    fun `callback-example`() = runTest("callback-example.json")

    @Test
    fun `gh`() = runTest("gh.json")

    @Test
    fun `link-example`() = runTest("link-example.json")

    @Test
    fun `petstore`() = runTest("petstore.json")

    @Test
    fun `stripe`() = runTest("stripe.json")

    @Test
    fun `uspto`() = runTest("uspto.json")

    private fun runTest(fileName:String) {
        val path = this.javaClass.classLoader.getResource(fileName)?.path
        val file = File(path)
        val input = file.readText()

        val openapi = OpenAPI.decodeFromString(input)
        val string = OpenAPI.encodeToString(openapi)

        input shouldEqualJson string
    }
}




