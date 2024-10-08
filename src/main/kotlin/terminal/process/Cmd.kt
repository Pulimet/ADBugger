package terminal.process

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.stream.Collectors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Cmd(private val processCreation: ProcessCreation) {
    suspend fun execute(command: String, log: ((String) -> Unit)?, path: String) =
        withContext(Dispatchers.IO) {
            executeAndWait(command, log, path)
        }

    private suspend fun executeAndWait(command: String, log: ((String) -> Unit)?, path: String) =
        suspendCoroutine { continuation ->
            val result = try {
                println("Invoking command: $command")
                log?.invoke(command)

                val process = processCreation.createProcessAndWaitForResult(path, command)

                getInputStream(process.inputStream).ifEmpty {
                    log?.invoke("No output. Checking error stream.")
                    log?.invoke("====== START =====")
                    val errorStream = getInputStream(process.errorStream)
                    errorStream.forEach { log?.invoke(it) }
                    log?.invoke("====== END =====")
                    emptyList()
                }
            } catch (e: IOException) {
                printAndEmptyList("Error executing command: $command", log, e)
            } catch (e: InterruptedException) {
                printAndEmptyList("Command interrupted: $command", log, e)
            } catch (e: RuntimeException) {
                printAndEmptyList("RuntimeException during: $command (${e.message})", log, e)
            }
            continuation.resume(result)
        }

    private fun printAndEmptyList(msg: String, log: ((String) -> Unit)?, e: Exception): List<String> {
        println(msg)
        log?.invoke(msg)
        e.printStackTrace()
        return emptyList()
    }

    private fun getInputStream(inputStream: InputStream) =
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.lines().collect(Collectors.toList()) as List<String>
        }

    suspend fun getEnvironmentVariables() = withContext(Dispatchers.IO) {
        processCreation.getEnvironmentVariables()
    }

    suspend fun executeAndGetData(
        command: String, path:
        String,
        log: (String) -> Unit,
        commandRunCallback: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        log.invoke(command)
        processCreation.executeAndGetData(path + command, commandRunCallback)
    }
}
