package community.flock.kotlinx.openapi.bindings

import community.flock.kotlinx.openapi.bindings.IO.readFile
import community.flock.kotlinx.openapi.bindings.Version.V32
import io.kotest.assertions.json.shouldEqualJson
import kotlin.test.Test

class OpenAPIV32Tests {

    @Test
    fun `feature-coverage`() = openAPIv32("feature-coverage.json")

    private fun openAPIv32(fileName: String) {
        readFile(fileName, V32).let {
            it shouldEqualJson it
                .let(OpenAPIV3::decodeFromString)
                .let(OpenAPIV3::encodeToString)
        }
    }
}
