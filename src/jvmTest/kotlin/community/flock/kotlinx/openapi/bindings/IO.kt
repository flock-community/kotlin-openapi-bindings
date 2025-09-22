package community.flock.kotlinx.openapi.bindings

import java.io.File

object IO {
    fun readFile(fileName: String, version: Version): String = toURL("examples/${version.name.lowercase()}/$fileName")
        .path
        .let(::File)
        .readText()

    private fun toURL(dir: String) = requireNotNull(javaClass.classLoader.getResource(dir)) {
        "No resource found for $dir"
    }
}
