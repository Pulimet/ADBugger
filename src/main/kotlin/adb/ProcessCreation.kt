package adb

import java.io.BufferedReader
import java.io.InputStreamReader

class ProcessCreation {
    fun createProcessAndWaitForResult(path: String, command: String): Process {
        val processBuilder: ProcessBuilder = createProcessBuilder(path + command)

        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        processBuilder.redirectError(ProcessBuilder.Redirect.PIPE)

        val process = processBuilder.start()
        process.waitFor()
        return process
    }

    fun executeAndGetData(pathAndCommand: String, callback: (String) -> Unit) {
        val processBuilder = createProcessBuilder(pathAndCommand)
        val process = processBuilder.start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        try {
            for (line in reader.lines()) {
                callback(line)
            }
        } catch (e: Exception) {
            println("Error executing command: $pathAndCommand")
            callback.invoke("Error executing command: $pathAndCommand")
            e.printStackTrace()
        } finally {
            process.destroy()
        }
    }

    private fun createProcessBuilder(pathAndCommand: String): ProcessBuilder {
        val isWindows = System.getProperty("os.name").startsWith("Windows")
        val processBuilder: ProcessBuilder = if (isWindows) {
            // Windows-specific configuration
            ProcessBuilder("cmd", "/c", pathAndCommand)
        } else {
            // Unix-like configuration
            ProcessBuilder("/bin/zsh", "-c", pathAndCommand)
        }
        return processBuilder
    }
}
