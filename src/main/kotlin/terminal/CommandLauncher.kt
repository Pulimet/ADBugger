package terminal

import model.TargetInfo
import pref.preference
import terminal.commands.Commands
import terminal.process.Cmd

class CommandLauncher(private val cmd: Cmd) {
    private var adbPath: String by preference("adbPath", Commands.getPlatformToolsDefaultPath())

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
    suspend fun run(command: String, path: String = adbPath): List<String> {
        return cmd.execute(command, log, path)
    }

    // - Launch regular adb  commands
    suspend fun runAdb(
        selectedTargetsList: List<String>,
        command: String,
        path: String = adbPath
    ) {
        if (selectedTargetsList.isEmpty()) {
            runAdbOnAllDevices(command, path)
        } else {
            selectedTargetsList.forEach {
                runAdbCommand(it, command, path)
            }
        }
    }

    private suspend fun runAdbOnAllDevices(command: String, path: String = adbPath) {
        devicesInfo().forEach {
            runAdbCommand(it.serial, command, path)
        }
    }

    suspend fun runAdbCommand(
        serial: String,
        command: String,
        path: String = adbPath
    ): List<String> {
        val commandToExecute = "adb -s $serial $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, path)
    }

    // - Launch shell commands
    suspend fun runAdbShell(selectedTargetsList: List<String>, command: String) {
        if (selectedTargetsList.isEmpty()) {
            runAdbShellOnAllDevices(command)
        } else {
            selectedTargetsList.forEach {
                runAdbShellCommand(it, command)
            }
        }
    }

    private suspend fun runAdbShellOnAllDevices(command: String) {
        devicesInfo().forEach {
            runAdbShellCommand(it.serial, command)
        }
    }

    suspend fun runAdbShellCommand(serial: String, command: String): List<String> {
        val commandToExecute = "adb -s $serial shell $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, adbPath)
    }

    suspend fun executeAndGetData(
        command: String,
        log: (String) -> Unit,
        commandRunCallBack: (String) -> Unit,
        platformToolsPath: String = adbPath
    ) {
        cmd.executeAndGetData(command, platformToolsPath, log, commandRunCallBack)
    }
}