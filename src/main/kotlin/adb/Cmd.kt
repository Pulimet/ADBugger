package adb

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors

object Cmd {
    fun execute(command: String, log: ((String) -> Unit)? = {}, path: String = ""): ArrayList<String> {
        return try {
            log?.invoke(command)

            val process = createProcess(path, command)

            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                reader.lines()
                    .filter { !it.contains("crashdata", ignoreCase = true) }
                    .collect(Collectors.toList()) as ArrayList<String>
            }

        } catch (e: IOException) {
            log?.invoke("Error executing command: $command")
            e.printStackTrace()
            ArrayList()
        } catch (e: InterruptedException) {
            log?.invoke("Command interrupted: $command")
            e.printStackTrace()
            ArrayList()
        }
    }

    private fun createProcess(path: String, command: String): Process {
        val isWindows = System.getProperty("os.name").startsWith("Windows")

        val processBuilder: ProcessBuilder = if (isWindows) {
            // Windows-specific configuration
            ProcessBuilder("cmd", "/c", "${path}$command")
        } else {
            // Unix-like configuration
            ProcessBuilder("/bin/zsh", "-c", "${path}$command")
        }

        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        processBuilder.redirectError(ProcessBuilder.Redirect.PIPE)

        val process = processBuilder.start()
        process.waitFor()
        return process
    }
}
