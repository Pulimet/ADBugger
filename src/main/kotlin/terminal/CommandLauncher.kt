package terminal

import model.TargetInfo
import pref.preference
import terminal.commands.Commands
import terminal.process.Cmd

class CommandLauncher(private val cmd: Cmd) {
    private var adbPath: String by preference("adbPath", DefaultPath.getPlatformToolsDefaultPath())

    private var log: (String) -> Unit = { println(it) }
    private var updateTargets: (List<TargetInfo>) -> Unit = {}

    fun setupCallBacks(logger: (String) -> Unit, updateTargetsList: (List<TargetInfo>) -> Unit) {
        log = logger
        updateTargets = updateTargetsList
    }

    suspend fun devicesInfo() =
        run(Commands.getDeviceList()).drop(1).dropLast(1).map {
            // For case I need it later
            // val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
            val serialAndType = it.split("\\s+".toRegex())
            TargetInfo(serialAndType[0], serialAndType[1])
        }.also { updateTargets(it) }


    // - Launch nonAdb commands
    suspend fun run(
        command: String,
        path: String = adbPath,
        printResults: Boolean = false
    ): List<String> {
        return cmd.execute(command, log, path).also { printResults(printResults, it) }
    }

    // - Launch regular adb  commands
    suspend fun runAdb(
        selectedTargetsList: List<String>,
        command: String,
        path: String = adbPath,
        printResults: Boolean = false
    ) {
        if (selectedTargetsList.isEmpty()) {
            runAdbOnAllDevices(command, path, printResults)
        } else {
            selectedTargetsList.forEach {
                runAdbCommand(it, command, path, printResults)
            }
        }
    }

    private suspend fun runAdbOnAllDevices(
        command: String,
        path: String = adbPath,
        printResults: Boolean = false
    ) {
        devicesInfo().forEach {
            runAdbCommand(it.serial, command, path, printResults)
        }
    }

    suspend fun runAdbCommand(
        serial: String,
        command: String,
        path: String = adbPath,
        printResults: Boolean = false
    ): List<String> {
        val commandToExecute = "adb -s $serial $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, path).also { printResults(printResults, it) }
    }

    // - Launch adb shell commands
    suspend fun runAdbShell(
        selectedTargetsList: List<String>,
        command: String,
        printResults: Boolean = false
    ) {
        if (selectedTargetsList.isEmpty()) {
            runAdbShellOnAllDevices(command, printResults)
        } else {
            selectedTargetsList.forEach {
                runAdbShellCommand(it, command, printResults)
            }
        }
    }

    private suspend fun runAdbShellOnAllDevices(command: String, printResults: Boolean = false) {
        devicesInfo().forEach {
            runAdbShellCommand(it.serial, command, printResults)
        }
    }

    suspend fun runAdbShellCommand(
        serial: String,
        command: String,
        printResults: Boolean = false
    ): List<String> {
        val commandToExecute = "adb -s $serial shell $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, adbPath).also { printResults(printResults, it) }
    }

    private fun printResults(printResults: Boolean, resultsList: List<String>) {
        if (resultsList.isEmpty()
            || (resultsList.size == 1 && resultsList[0].trim().isEmpty())
        ) {
            return
        }
        if (printResults) log(resultsList.joinToString(prefix = ">>> ", separator = "\n"))
    }

    suspend fun executeAndGetData(
        command: String,
        log: (String) -> Unit,
        commandRunCallBack: (String) -> Unit,
        platformToolsPath: String = adbPath
    ) {
        cmd.executeAndGetData(command, platformToolsPath, log, commandRunCallBack)
    }

    suspend fun getEnvironmentVariables() = cmd.getEnvironmentVariables()
}
