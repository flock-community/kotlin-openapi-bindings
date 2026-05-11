package community.flock.kotlinx.openapi.bindings

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.Test

class SharedTypeAdditionsTests {

    private val doc = """
        {
          "openapi": "3.0.0",
          "info": {
            "title": "T",
            "summary": "a short summary",
            "version": "1",
            "license": { "name": "MIT", "identifier": "MIT" }
          },
          "paths": {}
        }
    """.trimIndent()

    @Test
    fun `info summary and license identifier survive roundtrip`() {
        val decoded = OpenAPIV3.decodeFromString(doc)
        decoded.info.summary shouldBe "a short summary"
        decoded.info.license shouldNotBe null
        decoded.info.license?.identifier shouldBe "MIT"

        OpenAPIV3.encodeToString(decoded) shouldEqualJson doc
    }
}
