package terminal.process

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import utils.PlatformX
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

    suspend fun executeAndGetData(
        pathAndCommand: String,
        callback: (String) -> Unit,
    ) = withContext(Dispatchers.IO) {
        val processBuilder = createProcessBuilder(pathAndCommand)
        val process = processBuilder.start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        try {
            for (line in reader.lines()) {
                callback(line)
                if (isActive.not()) {
                    process.destroy()
                    break
                }
                yield() // Check for cancellation and give up control
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
        val processBuilder: ProcessBuilder = if (PlatformX.isWindows) {
            // Windows-specific configuration
            ProcessBuilder("cmd", "/c", pathAndCommand)
        } else {
            // Unix-like configuration
            ProcessBuilder("/bin/zsh", "-c", pathAndCommand)
        }
        return processBuilder
    }
}
