package terminal

import model.Package
import model.RunEmulatorParams
import model.TargetInfo
import pref.preference
import terminal.commands.AmCommands
import terminal.commands.CmdCommands
import terminal.commands.Commands
import terminal.commands.ContentCommands
import terminal.commands.EmulatorCommands
import terminal.commands.InputCommands
import terminal.commands.LogcatCommands
import terminal.commands.PackagesCommands
import terminal.commands.PermissionsCommands
import terminal.commands.SettingsCommands
import terminal.commands.WmCommands
import utils.Escaping

class Terminal(private val launcher: CommandLauncher) {
    private var emulatorPath: String by preference(
        "emulatorPath",
        DefaultPath.getEmulatorDefaultPath()
    )

    private var log: (String) -> Unit = { println(it) }

    // Public
    fun setupCallBacks(logger: (String) -> Unit, updateTargetsList: (List<TargetInfo>) -> Unit) {
        log = logger
        launcher.setupCallBacks(logger, updateTargetsList)
    }

    suspend fun devicesInfo() {
        launcher.devicesInfo()
    }

    suspend fun killAllEmulators() {
        launcher.devicesInfo().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    suspend fun takeSnapshot(selectedTargetsList: List<String>) {
        val list = selectedTargetsList.ifEmpty { launcher.devicesInfo().map { it.serial } }
        list.forEach {
            val filename = "snap_$it.png"
            launcher.runAdbShellCommand(it, "screencap -p /sdcard/$filename")
            launcher.runAdbCommand(it, Commands.getAdbPull(filename))
            launcher.runAdbShellCommand(it, "rm /sdcard/$filename")
        }
    }

    suspend fun logcat(
        selectedTarget: String,
        buffer: String,
        format: String,
        priorityLevel: String,
        tag: String,
        logcatCallback: (String) -> Unit
    ) {
        val command =
            LogcatCommands.getLogCatCommand(selectedTarget, buffer, format, priorityLevel, tag)
        launcher.executeAndGetData(command, log, logcatCallback)
    }


    suspend fun getEnvironmentVariables() = launcher.getEnvironmentVariables()


    // ========>>> llauncher.runAdbCommand
    suspend fun reversePort(port: Int) {
        launcher.devicesInfo().forEach { device ->
            val resultList = launcher.runAdbCommand(device.serial, Commands.adbReverse(port))
            resultList.forEach {
                log(it)
            }
        }
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

    // ========>>> llauncher.run
    suspend fun emulators() =
        launcher.run(EmulatorCommands.getEmulatorList(), emulatorPath)
            .filter { !it.contains("crashdata", ignoreCase = true) }

    suspend fun launchEmulator(emulatorName: String, params: RunEmulatorParams) {
        launcher.run(EmulatorCommands.getLaunchEmulator(emulatorName, params), emulatorPath)
    }

    suspend fun wipeAndLaunchEmulator(emulatorName: String) {
        launcher.run(EmulatorCommands.getWipeDataEmulatorByName(emulatorName), emulatorPath)
    }

    suspend fun reverseList() {
        val resultList = launcher.run(Commands.adbReverseList())
        resultList.forEach {
            log(it)
        }
    }

    suspend fun checkPlatformTools() =
        launcher.run("adb version | grep \"Android Debug Bridge version\"", printResults = true)

    suspend fun checkEmulators() = launcher.run(
        "emulator -version | grep \"Android emulator version\"",
        path = emulatorPath,
        printResults = true
    )

    // ========>>> llauncher.runAdb
    suspend fun uninstall(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdb(selectedTargetsList, PackagesCommands.getUninstallCommand(selectedPackage))
    }

    suspend fun killEmulatorBySerial(serial: String) {
        launcher.runAdb(listOf(serial), EmulatorCommands.getKillEmulatorBySerial())
    }

    suspend fun installApk(pathApk: String, selectedTarget: List<String>) {
        launcher.runAdb(selectedTarget, PackagesCommands.getAdbInstall(pathApk))
    }

    // ========>>> launcher.runAdbShell
    suspend fun packages(serial: String): List<Package> {
        return launcher.runAdbShellCommand(serial, PackagesCommands.getPackageList())
            .map { Package(it.split(":").last()) }
            .filter { it.name.isNotEmpty() }
    }

    suspend fun openPackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(
            selectedTargetsList,
            PackagesCommands.getLaunchCommand(selectedPackage)
        )
    }

    suspend fun getApkPath(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(
            selectedTargetsList,
            PackagesCommands.getApkPathCommand(selectedPackage)
        )
    }

    suspend fun closePackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, PackagesCommands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(
            selectedTargetsList,
            PackagesCommands.getClearDataCommand(selectedPackage)
        )
    }

    suspend fun showHome(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, AmCommands.getShowHome())
    }

    suspend fun showSettings(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, AmCommands.getShowSettings())
    }

    suspend fun showRecentApps(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, InputCommands.getShowRecentApps())
    }

    suspend fun pressBack(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, InputCommands.getPressBack())
    }

    suspend fun pressTab(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, InputCommands.getPressTab())
    }

    suspend fun pressEnter(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, InputCommands.getPressEnter())
    }

    suspend fun pressPower(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, InputCommands.getPressPower())
    }

    suspend fun setDarkModeOff(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, CmdCommands.getDarkModeOff())
    }

    suspend fun setDarkModeOn(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, CmdCommands.getDarkModeOn())
    }

    suspend fun pressUp(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, InputCommands.getUp())
    }

    suspend fun pressDown(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, InputCommands.getDown())
    }

    suspend fun pressLeft(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, InputCommands.getLeft())
    }

    suspend fun pressRight(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, InputCommands.getRight())
    }

    suspend fun pressDelete(selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, InputCommands.getDelete())
    }

    suspend fun sendText(selectedTarget: List<String>, value: String) {
        val text = Escaping.escapeForAdbShellInputText(value)
        launcher.runAdbShell(selectedTarget, InputCommands.sendTextCommand("\"$text\""))
    }

    suspend fun sendInput(selectedTarget: List<String>, value: Int) {
        launcher.runAdbShell(selectedTarget, InputCommands.sendInputCommand(value))
    }

    suspend fun sendInputNum(selectedTarget: List<String>, num: Int) {
        launcher.runAdbShell(selectedTarget, InputCommands.sendInputCommand((num + 7)))
    }

    suspend fun removeAllPermissions(selectedTarget: List<String>, selectedPackage: String) {
        launcher.runAdbShell(
            selectedTarget,
            PermissionsCommands.getRevokeAllPermissions(selectedPackage)
        )
    }

    suspend fun addPermission(
        selectedTarget: List<String>,
        permission: String,
        selectedPackage: String
    ) {
        launcher.runAdbShell(
            selectedTarget,
            PermissionsCommands.addSpecificPermission(selectedPackage, permission)
        )
    }

    suspend fun removePermission(
        selectedTarget: List<String>,
        permission: String,
        selectedPackage: String
    ) {
        launcher.runAdbShell(
            selectedTarget,
            PermissionsCommands.revokeSpecificPermission(selectedPackage, permission)
        )
    }

    suspend fun getPermissions(selectedTargetsList: List<String>, selectedPackage: String) =
        launcher.runAdbShellCommand(
            selectedTargetsList[0],
            PermissionsCommands.getGrantedPermissions(selectedPackage)
        )

    suspend fun changeFontSize(d: Double, selectedTarget: List<String>) {
        launcher.runAdbShell(selectedTarget, SettingsCommands.getChangeFontSize(d))
    }

    suspend fun changeDisplayDensity(density: Int, selectedTarget: List<String>) {
        val d = if (density == 0) "reset" else "$density"
        launcher.runAdbShell(selectedTarget, WmCommands.getChangeDisplayDensity(d))
    }

    suspend fun changeDisplaySize(size: String, selectedTarget: List<String>) {
        val d = if (size == "0") "reset" else size
        launcher.runAdbShell(selectedTarget, WmCommands.getChangeDisplaySize(d))
    }

    suspend fun setProxy(proxyText: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, EmulatorCommands.getSetProxy(proxyText))
    }

    suspend fun removeProxy(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, EmulatorCommands.getRemoveProxy())
    }

    suspend fun getProxy(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, EmulatorCommands.getFetchProxy(), true)
    }

    suspend fun onAirplaneOn(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, CmdCommands.getAirplaneOn())
    }

    suspend fun onAirplaneOff(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, CmdCommands.getAirplaneOff())
    }

    suspend fun onWifiOn(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, CmdCommands.getWifiOn())
    }

    suspend fun onWifiOff(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, CmdCommands.getWifiOff())
    }

    suspend fun onGpsOn(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, SettingsCommands.getGpdOn())
    }

    suspend fun onGpsOff(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, SettingsCommands.getGpsOff())
    }

    suspend fun onRotationAutoTurnOn(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, ContentCommands.getRotationAutoTurnOn())
    }

    suspend fun onRotationAutoTurnOff(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, ContentCommands.getRotationAutoTurnOff())
    }

    suspend fun onRotationLandscape(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, ContentCommands.getRotationLandscape())
    }

    suspend fun onRotationPortrait(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, ContentCommands.getRotationPortrait())
    }

    suspend fun onRotationPortraitUpSideDown(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, ContentCommands.getRotationUpSideDown())
    }

    suspend fun onRotationLandscapeUpSideDow(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, ContentCommands.getRotationLandscape2())
    }

    suspend fun getDeviceProps(selectedTarget: String) =
        launcher.runAdbShellCommand(selectedTarget, Commands.getDeviceProps())


}
