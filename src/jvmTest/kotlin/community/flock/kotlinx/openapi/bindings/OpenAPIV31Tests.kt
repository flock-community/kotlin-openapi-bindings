package community.flock.kotlinx.openapi.bindings

import community.flock.kotlinx.openapi.bindings.IO.readFile
import community.flock.kotlinx.openapi.bindings.Version.V31
import io.kotest.assertions.json.shouldEqualJson
import kotlin.test.Test

class OpenAPIV31Tests {

    @Test
    fun mastodon() = openAPIv31("mastodon.json")

    @Test
    fun `feature-coverage`() = openAPIv31("feature-coverage.json")

    private fun openAPIv31(fileName: String) {
        readFile(fileName, V31).let {
            it shouldEqualJson it
                .let(OpenAPIV3::decodeFromString)
                .let(OpenAPIV3::encodeToString)
        }
    }
}
