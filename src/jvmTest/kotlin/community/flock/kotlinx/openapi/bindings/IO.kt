package community.flock.kotlinx.openapi.bindings

import java.io.File

expect object IO {
    fun readFile(fileName: String, version:Version):String
}

actual object IO {
    actual fun readFile(fileName:String, version:Version): String {
        val dir = "examples/${version.name.lowercase()}/$fileName"
        val path = this.javaClass.classLoader.getResource(dir)?.path
        val file = File(path)
        return file.readText()
    }
}