package terminal

import model.Package
import model.TargetInfo
import pref.preference
import terminal.commands.Commands
import terminal.commands.Logcat
import utils.Escaping

class Terminal(private val launcher: CommandLauncher) {
    private var emulatorPath: String by preference("emulatorPath", Commands.getEmulatorDefaultPath())

    private var log: (String) -> Unit = { println(it) }
    private var updateTargets: (List<TargetInfo>) -> Unit = {}

    // Public
    fun setupCallBacks(logger: (String) -> Unit, updateTargetsList: (List<TargetInfo>) -> Unit) {
        log = logger
        updateTargets = updateTargetsList
        launcher.setupCallBacks(logger, updateTargetsList)
    }

    suspend fun devicesInfo() {
        launcher.devicesInfo()
    }

    suspend fun packages(serial: String): List<Package> {
        return launcher.runAdbShellCommand(serial, Commands.getPackageList())
            .map { Package(it.split(":").last()) }
            .filter { it.name.isNotEmpty() }
    }

    suspend fun emulators() = launcher.run(Commands.getEmulatorList(), emulatorPath)


    suspend fun openPackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getLaunchCommand(selectedPackage))
    }

    suspend fun getApkPath(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getApkPathCommand(selectedPackage))
    }

    suspend fun closePackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getClearDataCommand(selectedPackage))
    }

    suspend fun uninstall(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdb(selectedTargetsList, Commands.getUninstallCommand(selectedPackage))
    }

    suspend fun killAllEmulators() {
        launcher.devicesInfo().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    suspend fun killEmulatorBySerial(serial: String) {
        launcher.runAdb(listOf(serial), Commands.getKillEmulatorBySerial())
    }

    suspend fun launchEmulator(emulatorName: String) {
        launcher.run(Commands.getLaunchEmulator(emulatorName), emulatorPath)
    }

    suspend fun wipeAndLaunchEmulator(emulatorName: String) {
        launcher.run(Commands.getWipeDataEmulatorByName(emulatorName), emulatorPath)
    }

    suspend fun showHome(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getShowHome())
    }

    suspend fun showSettings(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getShowSettings())
    }

    suspend fun pressBack(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getPressBack())
    }

    suspend fun pressTab(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getPressTab())
    }

    suspend fun pressEnter(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getPressEnter())
    }

    suspend fun pressPower(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getPressPower())
    }

    suspend fun takeSnapshot(selectedTargetsList: List<String>) {
        selectedTargetsList.forEach {
            val filename = "snap_$it.png"
            launcher.runAdbShellCommand(it, "screencap -p /sdcard/$filename")
            launcher.runAdbCommand(it, Commands.getAdbPull(filename))
            launcher.runAdbShellCommand(it, "rm /sdcard/$filename")
        }
    }

    suspend fun setDarkModeOff(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getDarkModeOff())
    }

    suspend fun setDarkModeOn(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getDarkModeOn())
    }

    suspend fun pressUp(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getUp())
    }

    suspend fun pressDown(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getDown())
    }

    suspend fun pressLeft(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getLeft())
    }

    suspend fun pressRight(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getRight())
    }

    suspend fun pressDelete(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getDelete())
    }

    suspend fun sendText(selectedTarget: List<String>, value: String) {
        val text = Escaping.escapeForAdbShellInputText(value)
        launcher.runAdbShell(selectedTarget, Commands.sendTextCommand("\"$text\""))
    }

    suspend fun sendInput(selectedTarget: List<String>, value: Int) {
        launcher.runAdbShell(selectedTarget, Commands.sendInputCommand(value))
    }

    suspend fun sendInputNum(selectedTarget: List<String>, num: Int) {
        launcher.runAdbShell(selectedTarget, Commands.sendInputCommand((num + 7)))
    }

    suspend fun reversePort(port: Int) {
        launcher.devicesInfo().forEach { device ->
            val resultList = launcher.runAdbCommand(device.serial, Commands.adbReverse(port))
            resultList.forEach {
                log(it)
            }
        }
    }

    suspend fun reverseList() {
        val resultList = launcher.run(Commands.adbReverseList())
        resultList.forEach {
            log(it)
        }
    }

    suspend fun removeAllPermissions(selectedTarget: List<String>, selectedPackage: String) {
        launcher.runAdbShell(selectedTarget, Commands.getRevokeAllPermissions(selectedPackage))
    }

    suspend fun addPermission(selectedTarget: List<String>, permission: String, selectedPackage: String) {
        launcher.runAdbShell(selectedTarget, Commands.addSpecificPermission(selectedPackage, permission))
    }

    suspend fun removePermission(selectedTarget: List<String>, permission: String, selectedPackage: String) {
        launcher.runAdbShell(selectedTarget, Commands.revokeSpecificPermission(selectedPackage, permission))
    }

    suspend fun getPermissions(selectedTargetsList: List<String>, selectedPackage: String) {
        selectedTargetsList.forEach {
            val result = launcher.runAdbShellCommand(it, Commands.getGrantedPermissions(selectedPackage))
            log("output :$result")
        }
    }

    suspend fun changeFontSize(d: Double, selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, Commands.getChangeFontSize(d))
    }

    suspend fun changeDisplayDensity(density: Int, selectedTarget: List<String>) {
        val d = if (density == 0) "reset" else "$density"
        launcher.runAdbShell(selectedTarget, Commands.getChangeDisplayDensity(d))
    }

    suspend fun installApk(pathApk: String, selectedTarget: List<String>) {
        launcher.runAdb(selectedTarget, Commands.getAdbInstall(pathApk))
    }

    suspend fun logcat(
        selectedTarget: String,
        buffer: String,
        format: String,
        priorityLevel: String,
        tag: String,
        logcatCallback: (String) -> Unit
    ) {
        val command = Logcat.getLogCatCommand(selectedTarget, buffer, format, priorityLevel, tag)
        launcher.executeAndGetData(command, log, logcatCallback)
    }

    suspend fun clearLogcat(selectedTarget: String) {
        launcher.runAdbCommand(selectedTarget, "logcat -c")
    }

    suspend fun saveLogcatToDesktop(selectedTarget: String) {
        launcher.runAdbCommand(selectedTarget, "logcat -d > ~/Desktop/logs_$selectedTarget.txt")
    }

    suspend fun saveBugReport(selectedTarget: String) {
        val fileName = "bugreport_$selectedTarget"
        launcher.runAdbCommand(selectedTarget, "bugreport ~/Desktop/$fileName.zip")
    }

}
