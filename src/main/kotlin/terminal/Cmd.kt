package terminal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Cmd(private val processCreation: ProcessCreation) {
    suspend fun execute(command: String, log: ((String) -> Unit)?, path: String) = withContext(Dispatchers.IO) {
        executeAndWait(command, log, path)
    }

    private suspend fun executeAndWait(command: String, log: ((String) -> Unit)?, path: String) =
        suspendCoroutine { continuation ->
            val result = try {
                println("Invoking command: $command")
                log?.invoke(command)

                val process = processCreation.createProcessAndWaitForResult(path, command)

                BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                    reader.lines().filter { !it.contains("crashdata", ignoreCase = true) }
                        .collect(Collectors.toList()) as List<String>
                }

            } catch (e: IOException) {
                println("Error executing command: $command")
                log?.invoke("Error executing command: $command")
                e.printStackTrace()
                emptyList()
            } catch (e: InterruptedException) {
                println("Command interrupted: $command")
                log?.invoke("Command interrupted: $command")
                e.printStackTrace()
                emptyList()
            }
            continuation.resume(result)
        }

    suspend fun executeAndGetData(
        command: String, path:
        String,
        log: (String) -> Unit,
        logcatCallback: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        log.invoke(command)
        processCreation.executeAndGetData(path + command, logcatCallback)
    }
}
