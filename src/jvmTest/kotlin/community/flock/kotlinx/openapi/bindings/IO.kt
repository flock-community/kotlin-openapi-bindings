package community.flock.kotlinx.openapi.bindings

import java.io.File

object IO {
    fun readFile(fileName: String, version: Version): String {
        val dir = "examples/${version.name.lowercase()}/$fileName"
        val path = this.javaClass.classLoader.getResource(dir)?.path
        requireNotNull(path) { "No resource found for $dir" }
        val file = File(path)
        return file.readText()
    }
}
