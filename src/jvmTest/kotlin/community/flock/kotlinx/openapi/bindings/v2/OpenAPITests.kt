package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.IO
import community.flock.kotlinx.openapi.bindings.Version
import io.kotest.assertions.json.shouldEqualJson

import kotlin.test.Test

class OpenAPITests {

    @Test
    fun `petstore`() = runTest("petstore.json")

    @Test
    fun `uber`() = runTest("uber.json")

    private fun runTest(fileName:String) {
        val input = IO.readFile(fileName, Version.V2)
        val openapi = OpenAPI.decodeFromString(input)
        val string = OpenAPI.encodeToString(openapi)
        input shouldEqualJson string
    }
}




