package adb

import model.Package
import model.TargetInfo

class Adb(private val cmd: Cmd) {

    private var log: (String) -> Unit = { println(it) }
    private var updateTargets: (List<TargetInfo>) -> Unit = {}

    // Public
    fun setupCallBacks(logger: (String) -> Unit, updateTargetsList: (List<TargetInfo>) -> Unit) {
        log = logger
        updateTargets = updateTargetsList
    }

    suspend fun devicesInfo() =
        launchCommandInTerminal(Commands.getDeviceList()).drop(1).dropLast(1).map {
            // For case I need it later
            // val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
            val serialAndType = it.split("\\s+".toRegex())
            TargetInfo(serialAndType[0], serialAndType[1])
        }.also { updateTargets(it) }

    suspend fun packages(serial: String): ArrayList<Package> {
        return launchAdbShellCommand(serial, Commands.getPackageList())
            .map { Package(it.split(":").last()) }
            .filter { it.name.isNotEmpty() } as ArrayList<Package>
    }

    suspend fun emulators() = launchCommandInTerminal(Commands.getEmulatorList(), Commands.getEmulatorPath())


    suspend fun openPackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getLaunchCommand(selectedPackage))
    }

    suspend fun getApkPath(selectedPackage: String, selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getApkPathCommand(selectedPackage))
    }

    suspend fun closePackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getClearDataCommand(selectedPackage))
    }

    suspend fun uninstall(selectedPackage: String, selectedTargetsList: List<String>) {
        launchAdb(selectedTargetsList, Commands.getUninstallCommand(selectedPackage))
    }

    suspend fun killAllEmulators() {
        devicesInfo().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    suspend fun killEmulatorBySerial(serial: String) {
        launchAdb(listOf(serial), Commands.getKillEmulatorBySerial())
    }

    suspend fun launchEmulator(emulatorName: String) {
        launchCommandInTerminal(Commands.getLaunchEmulator(emulatorName), Commands.getEmulatorPath())
    }

    suspend fun wipeAndLaunchEmulator(emulatorName: String) {
        launchCommandInTerminal(Commands.getWipeDataEmulatorByName(emulatorName), Commands.getEmulatorPath())
    }

    suspend fun showHome(selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getShowHome())
    }

    suspend fun showSettings(selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getShowSettings())
    }

    suspend fun pressBack(selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getPressBack())
    }

    suspend fun pressTab(selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getPressTab())
    }

    suspend fun pressEnter(selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getPressEnter())
    }

    suspend fun pressPower(selectedTargetsList: List<String>) {
        launchAdbShell(selectedTargetsList, Commands.getPressPower())
    }

    suspend fun takeSnapshot(selectedTargetsList: List<String>) {
        selectedTargetsList.forEach {
            val filename = "snap_$it.png"
            launchAdbShellCommand(it, "screencap -p /sdcard/$filename")
            launchAdbCommand(it, Commands.getAdbPull(filename))
            launchAdbShellCommand(it, "rm /sdcard/$filename")
        }
    }

    suspend fun setDarkModeOff(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getDarkModeOff())
    }

    suspend fun setDarkModeOn(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getDarkModeOn())
    }

    suspend fun pressUp(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getUp())
    }

    suspend fun pressDown(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getDown())
    }

    suspend fun pressLeft(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getLeft())
    }

    suspend fun pressRight(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getRight())
    }

    suspend fun pressDelete(selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getDelete())
    }

    suspend fun sendText(selectedDevice: List<String>, value: String) {
        launchAdbShell(selectedDevice, Commands.sendTextCommand(value))
    }

    suspend fun sendInput(selectedDevice: List<String>, value: Int) {
        launchAdbShell(selectedDevice, Commands.sendInputCommand(value))
    }

    suspend fun sendInputNum(selectedDevice: List<String>, num: Int) {
        launchAdbShell(selectedDevice, Commands.sendInputCommand((num + 7)))
    }

    suspend fun reversePort(port: Int) {
        devicesInfo().forEach { device ->
            val resultList = launchAdbCommand(device.serial, Commands.adbReverse(port))
            resultList.forEach {
                log(it)
            }
        }
    }

    suspend fun reverseList() {
        val resultList = launchCommandInTerminal(Commands.adbReverseList())
        resultList.forEach {
            log(it)
        }
    }

    suspend fun removeAllPermissions(selectedDevice: List<String>, selectedPackage: String) {
        launchAdbShell(selectedDevice, Commands.getRevokeAllPermissions(selectedPackage))
    }

    suspend fun addPermission(selectedDevice: List<String>, permission: String, selectedPackage: String) {
        launchAdbShell(selectedDevice, Commands.addSpecificPermission(selectedPackage, permission))
    }

    suspend fun removePermission(selectedDevice: List<String>, permission: String, selectedPackage: String) {
        launchAdbShell(selectedDevice, Commands.revokeSpecificPermission(selectedPackage, permission))
    }

    suspend fun getPermissions(selectedTargetsList: List<String>, selectedPackage: String) {
        selectedTargetsList.forEach {
            val result = launchAdbShellCommand(it, Commands.getGrantedPermissions(selectedPackage))
            log("output :$result")
        }
    }

    suspend fun changeFontSize(d: Double, selectedDevice: List<String>) {
        launchAdbShell(selectedDevice, Commands.getChangeFontSize(d))
    }

    suspend fun changeDisplayDensity(density: Int, selectedDevice: List<String>) {
        val d = if (density == 0) "reset" else "$density"
        launchAdbShell(selectedDevice, Commands.getChangeDisplayDensity(d))
    }

    suspend fun installApk(pathApk: String, selectedDevice: List<String>) {
        launchAdb(selectedDevice, Commands.getAdbInstall(pathApk))
    }

    suspend fun logcat() {
        cmd.executeAndGetData("adb logcat", Commands.getPlatformToolsPath(), log)
    }

    // Private - Launch nonAdb commands
    private suspend fun launchCommandInTerminal(
        command: String,
        path: String = Commands.getPlatformToolsPath()
    ): ArrayList<String> {
        return cmd.execute(command, log, path)
    }

    // Private - Launch regular adb  commands
    private suspend fun launchAdb(
        selectedTargetsList: List<String>,
        command: String,
        path: String = Commands.getPlatformToolsPath()
    ) {
        if (selectedTargetsList.isEmpty()) {
            launchAdbOnAllDevices(command, path)
        } else {
            selectedTargetsList.forEach {
                launchAdbCommand(it, command, path)
            }
        }
    }

    private suspend fun launchAdbOnAllDevices(command: String, path: String = Commands.getPlatformToolsPath()) {
        devicesInfo().forEach {
            launchAdbCommand(it.serial, command, path)
        }
    }


    private suspend fun launchAdbCommand(
        serial: String,
        command: String,
        path: String = Commands.getPlatformToolsPath()
    ): ArrayList<String> {
        val commandToExecute = "adb -s $serial $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, path)
    }

    // Private - Launch shell commands
    private suspend fun launchAdbShell(selectedTargetsList: List<String>, command: String) {
        if (selectedTargetsList.isEmpty()) {
            launchAdbShellCommandOnAllDevices(command)
        } else {
            selectedTargetsList.forEach {
                launchAdbShellCommand(it, command)
            }
        }
    }

    private suspend fun launchAdbShellCommandOnAllDevices(command: String) {
        devicesInfo().forEach {
            launchAdbShellCommand(it.serial, command)
        }
    }

    private suspend fun launchAdbShellCommand(serial: String, command: String): ArrayList<String> {
        val commandToExecute = "adb -s $serial shell $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, Commands.getPlatformToolsPath())
    }
}
