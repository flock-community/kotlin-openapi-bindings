package community.flock.kotlinx.openapi.bindings

import java.io.File

actual object IO {
    actual fun readFile(fileName:String): String {
        val path = this.javaClass.classLoader.getResource(fileName)?.path
        val file = File(path)
        return file.readText()
    }
}