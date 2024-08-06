package terminal.process

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import utils.PlatformX
import java.io.BufferedReader
import java.io.InputStreamReader

class ProcessCreation {
    fun createProcessAndWaitForResult(path: String = "", command: String = ""): Process {
        val processBuilder: ProcessBuilder = createProcessBuilder(path + command)
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

    fun getEnvironmentVariables(): Map<String, String> = createProcessBuilder().environment()


    private fun createProcessBuilder(pathAndCommand: String = "") = if (PlatformX.isWindows) {
        ProcessBuilder("cmd", "/c", pathAndCommand)
    } else {
        //ProcessBuilder("/bin/zsh", "-c", pathAndCommand)
        ProcessBuilder("sh", "-c", pathAndCommand)
    }.apply {
        redirectOutput(ProcessBuilder.Redirect.PIPE)
        redirectError(ProcessBuilder.Redirect.PIPE)
    }
}

