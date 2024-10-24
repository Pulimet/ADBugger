package terminal

import model.Extras
import model.Package
import model.RunEmulatorParams
import model.TargetInfo
import pref.preference
import terminal.commands.AmCommands
import terminal.commands.ApkCommands
import terminal.commands.CmdCommands
import terminal.commands.Commands
import terminal.commands.ContentCommands
import terminal.commands.DumpsysCommands
import terminal.commands.EmulatorCommands
import terminal.commands.ForwardReverseCommands
import terminal.commands.InputCommands
import terminal.commands.LogcatCommands
import terminal.commands.PmCommands
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


    // ========>>> launcher.runAdbCommand
    suspend fun reversePort(portFrom: Int, portTo: Int) {
        launcher.devicesInfo().forEach { device ->
            val resultList =
                launcher.runAdbCommand(
                    device.serial,
                    ForwardReverseCommands.adbReverse(portFrom, portTo)
                )
            logResults(resultList)
        }
    }

    suspend fun forwardPort(portFrom: Int, portTo: Int) {
        launcher.devicesInfo().forEach { device ->
            val resultList =
                launcher.runAdbCommand(
                    device.serial,
                    ForwardReverseCommands.adbForward(portFrom, portTo)
                )
            logResults(resultList)
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

    private fun logResults(resultList: List<String>) {
        resultList.forEach {
            if (it.isNotEmpty()) {
                log(it)
            }
        }
    }


    // ========>>> launcher.run
    suspend fun emulators() =
        launcher.run(EmulatorCommands.getEmulatorList(), emulatorPath)
            .filter { !it.contains("crashdata", ignoreCase = true) }

    suspend fun launchEmulator(emulatorName: String, params: RunEmulatorParams) {
        launcher.run(EmulatorCommands.getLaunchEmulator(emulatorName, params), emulatorPath)
    }

    suspend fun wipeAndLaunchEmulator(emulatorName: String) {
        launcher.run(EmulatorCommands.getWipeDataEmulatorByName(emulatorName), emulatorPath)
    }

    suspend fun checkPlatformTools() =
        launcher.run("adb version | grep \"Android Debug Bridge version\"", printResults = true)

    suspend fun checkEmulators() = launcher.run(
        "emulator -version | grep \"Android emulator version\"",
        path = emulatorPath,
        printResults = true
    )

    // ========>>> launcher.runAdb
    suspend fun uninstall(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdb(selectedTargetsList, ApkCommands.getUninstallCommand(selectedPackage))
    }

    suspend fun killEmulatorBySerial(serial: String) {
        launcher.runAdb(listOf(serial), EmulatorCommands.getKillEmulatorBySerial())
    }

    suspend fun installApk(pathApk: String, selectedTarget: List<String>) {
        launcher.runAdb(selectedTarget, ApkCommands.getAdbInstall(pathApk))
    }

    suspend fun reverseList(selectedTarget: List<String>) {
        launcher.runAdb(
            selectedTarget,
            ForwardReverseCommands.adbReverseList(),
            printResults = true
        )
    }

    suspend fun forwardList(selectedTarget: List<String>) {
        launcher.runAdb(
            selectedTarget,
            ForwardReverseCommands.adbForwardList(),
            printResults = true
        )
    }

    suspend fun forwardListClear(selectedTarget: List<String>) {
        launcher.runAdb(selectedTarget, ForwardReverseCommands.adbForwardListClear())
    }

    suspend fun reverseListClear(selectedTarget: List<String>) {
        launcher.runAdb(selectedTarget, ForwardReverseCommands.adbReverseListClear())
    }

    // ========>>> launcher.runAdbShell
    suspend fun packages(serial: String, selectedOption: String): List<Package> {
        return launcher.runAdbShellCommand(serial, PmCommands.getPackageList(selectedOption))
            .map {
                val rowList = it.split(":").toMutableList()
                rowList.removeFirst()
                val result = rowList.joinToString(" - ")
                Package(result)
            }
            .filter { it.name.isNotEmpty() }
    }

    suspend fun openPackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(
            selectedTargetsList,
            Commands.getLaunchCommand(selectedPackage)
        )
    }

    suspend fun getApkPath(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(
            selectedTargetsList,
            PmCommands.getApkPathCommand(selectedPackage),
            true
        )
    }

    suspend fun closePackage(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, AmCommands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedTargetsList: List<String>) {
        launcher.runAdbShell(
            selectedTargetsList,
            PmCommands.getClearDataCommand(selectedPackage)
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
            PmCommands.getRevokeAllPermissions(selectedPackage)
        )
    }

    suspend fun addPermission(
        selectedTarget: List<String>,
        permission: String,
        selectedPackage: String
    ) {
        launcher.runAdbShell(
            selectedTarget,
            PmCommands.addSpecificPermission(selectedPackage, permission)
        )
    }

    suspend fun removePermission(
        selectedTarget: List<String>,
        permission: String,
        selectedPackage: String
    ) {
        launcher.runAdbShell(
            selectedTarget,
            PmCommands.revokeSpecificPermission(selectedPackage, permission)
        )
    }

    suspend fun getPermissions(selectedTargetsList: List<String>, selectedPackage: String) =
        launcher.runAdbShellCommand(
            selectedTargetsList[0],
            DumpsysCommands.getGrantedPermissions(selectedPackage)
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

    suspend fun testLaunch(
        selectedTargetsList: List<String>,
        selectedActivity: String,
        extrasList: List<Extras> = emptyList()
    ) {
        launcher.runAdbShell(
            selectedTargetsList,
            AmCommands.getOpenPackageCommand(selectedActivity, extrasList)
        )
    }

    suspend fun getPackageActivities(selectedTargetsList: List<String>, selectedPackage: String) {
        launcher.runAdbShell(
            selectedTargetsList,
            DumpsysCommands.getPackageActivities(selectedPackage),
            printResults = true,
        )
    }

    suspend fun startAdbServer() {
        launcher.run(Commands.getAdbStartServer(), printResults = true)
    }

    suspend fun killAdbServer() {
        launcher.run(Commands.getAdbKillServer(), printResults = true)
    }

    suspend fun adbUsb(selectedTargetsList: List<String>) {
        launcher.runAdb(selectedTargetsList, Commands.getAdbUsb(), printResults = true)
    }

    suspend fun adbTcpIp(port: String, selectedTargetsList: List<String>) {
        launcher.runAdb(selectedTargetsList, Commands.getAdbTcpIp(port), printResults = true)
    }

    suspend fun disconnectAll() {
        launcher.run(Commands.getAdbDisconnectAll(), printResults = true)
    }

    suspend fun adbConnect(ip: String, port: String) {
        launcher.run(Commands.getAdbConnect(ip, port), printResults = true)
    }

    suspend fun adbDisconnect(ip: String, port: String) {
        launcher.run(Commands.getAdbDisconnect(ip, port), printResults = true)
    }

    suspend fun adbPair(ip: String, port: String, code: String) {
        launcher.run(Commands.getAdbPair(ip, port, code), printResults = true)
    }

    suspend fun getDeviceIp(selectedTargetsList: List<String>) {
        launcher.runAdbShell(selectedTargetsList, Commands.getDeviceIp(), printResults = true)
    }

    suspend fun openDeepLink(selectedTargetsList: List<String>, url: String, packageName: String) {
        launcher.runAdbShell(selectedTargetsList, AmCommands.getOpenDeepLink(url, packageName))
    }

}
