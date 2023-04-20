package community.flock.kotlinx.openapi.bindings

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import java.io.File


class OpenAPIModelTests {

    @Test
    fun deserialize() {
        val path = this.javaClass.classLoader.getResource("petstore.json")?.path
        val file = File(path)
        val openapi = Json.decodeFromStream<OpenAPIObject>(file.inputStream())
        println(openapi)
        val json = Json.encodeToString(openapi)
        JSONAssert.assertEquals(file.readText(), json, false);
    }
}




