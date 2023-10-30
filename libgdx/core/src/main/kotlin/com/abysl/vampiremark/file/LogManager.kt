package com.abysl.vampiremark.file

import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

object LogManager {
    private const val LOG_EXTENSION = ".log"

    fun logMessage(message: String, filename: String) {
        val file = VampireMarkDataDir.getPathFor("$filename$LOG_EXTENSION")
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        file.writeString("$timestamp: $message\n", true)
    }

    fun readLog(filename: String): String {
        val file = VampireMarkDataDir.getPathFor("$filename$LOG_EXTENSION")
        if (!file.exists()) throw FileNotFoundException("Log file $filename not found!")
        return file.readString()
    }

    fun clearLog(filename: String) {
        VampireMarkDataDir.getPathFor("$filename$LOG_EXTENSION").delete()
    }

    fun exists(filename: String): Boolean {
        return VampireMarkDataDir.getPathFor("$filename$LOG_EXTENSION").exists()
    }
}
