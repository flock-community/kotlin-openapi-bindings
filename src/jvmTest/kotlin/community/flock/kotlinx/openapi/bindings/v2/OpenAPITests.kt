package community.flock.kotlinx.openapi.bindings.v2

import community.flock.kotlinx.openapi.bindings.IO
import community.flock.kotlinx.openapi.bindings.Version
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

import kotlin.test.Test

class OpenAPITests {

    @Test
    fun `petstore`() = runTest("petstore.json")

    @Test
    fun `uber`() = runTest("uber.json")

    @Test
    fun `swagger is not valid`(){
        val input = IO.readFile("petstore.json", Version.V3)
        val exception = shouldThrow<IllegalStateException> {
            OpenAPI.decodeFromString(input)
        }
        exception.message shouldBe  "No valid openapi v2 element 'swagger' is missing"

    }

    private fun runTest(fileName:String) {
        val input = IO.readFile(fileName, Version.V2)
        val openapi = OpenAPI.decodeFromString(input)
        val string = OpenAPI.encodeToString(openapi)
        input shouldEqualJson string
    }
}




