package adb

import model.DeviceInfo
import model.Package
import store.AppStore

class Adb(private val cmd: Cmd) {

    private var log: (String) -> Unit = { println(it) }
    private var updateDevices: (List<DeviceInfo>) -> Unit = {}

    // Public
    fun setupCallBacks(logger: (String) -> Unit, updateDevicesList: (List<DeviceInfo>) -> Unit) {
        log = logger
        updateDevices = updateDevicesList
    }

    suspend fun devicesInfo() =
        launchNonAdb(Commands.getDeviceList()).drop(1).dropLast(1).map {
            // For case I need it later
            // val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
            val serialAndType = it.split("\\s+".toRegex())
            DeviceInfo(serialAndType[0], serialAndType[1])
        }.also { updateDevices(it) }

    suspend fun packages(serial: String): ArrayList<Package> {
        return launchAdbShellCommand(serial, Commands.getPackageList())
            .map { Package(it.split(":").last()) }
            .filter { it.name.isNotEmpty() } as ArrayList<Package>
    }

    suspend fun emulators() = launchNonAdb(Commands.getEmulatorList(), Commands.getEmulatorPath())


    suspend fun openPackage(selectedPackage: String, selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getLaunchCommand(selectedPackage))
    }

    suspend fun getApkPath(selectedPackage: String, selectedDevice: String) {
        launchNonAdb(Commands.getApkPathCommand(selectedPackage, selectedDevice)).forEach {
            log(it)
        }
    }

    suspend fun closePackage(selectedPackage: String, selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getClearDataCommand(selectedPackage))
    }

    suspend fun uninstall(selectedPackage: String, selectedDevice: String) {
        launchAdb(selectedDevice, Commands.getUninstallCommand(selectedPackage))
    }

    suspend fun killAllEmulators() {
        devicesInfo().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    suspend fun killEmulatorBySerial(serial: String) {
        launchAdb(serial, Commands.getKillEmulatorBySerial())
    }

    suspend fun launchEmulator(emulatorName: String) {
        launchNonAdb(Commands.getLaunchEmulator(emulatorName), Commands.getEmulatorPath())
    }

    suspend fun wipeAndLaunchEmulator(emulatorName: String) {
        launchNonAdb(Commands.getWipeDataEmulatorByName(emulatorName), Commands.getEmulatorPath())
    }

    suspend fun showHome(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getShowHome())
    }

    suspend fun showSettings(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getShowSettings())
    }

    suspend fun pressBack(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getPressBack())
    }

    suspend fun pressTab(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getPressTab())
    }

    suspend fun pressEnter(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getPressEnter())
    }

    suspend fun pressPower(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getPressPower())
    }

    suspend fun takeSnapshot(selectedDevice: String) {
        val filename = "snap_$selectedDevice.png"
        launchAdbShellCommand(selectedDevice, "screencap -p /sdcard/$filename")
        val pullCommand = "adb -s $selectedDevice pull /sdcard/$filename ~/Desktop/$filename"
        launchNonAdb(pullCommand)
        launchAdbShellCommand(selectedDevice, "rm /sdcard/$filename")
    }


    suspend fun setDarkModeOff(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getDarkModeOff())
    }

    suspend fun setDarkModeOn(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getDarkModeOn())
    }

    suspend fun pressUp(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getUp())
    }

    suspend fun pressDown(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getDown())
    }

    suspend fun pressLeft(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getLeft())
    }

    suspend fun pressRight(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getRight())
    }

    suspend fun pressDelete(selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getDelete())
    }

    suspend fun sendText(selectedDevice: String, value: String) {
        launchAdbShell(selectedDevice, Commands.sendTextCommand(value))
    }

    suspend fun sendInput(selectedDevice: String, value: Int) {
        launchAdbShell(selectedDevice, Commands.sendInputCommand(value))
    }

    suspend fun sendInputNum(selectedDevice: String, num: Int) {
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
        val resultList = launchNonAdb(Commands.adbReverseList())
        resultList.forEach {
            log(it)
        }
    }

    suspend fun removeAllPermissions(selectedDevice: String, selectedPackage: String) {
        launchAdbShell(selectedDevice, Commands.getRevokeAllPermissions(selectedPackage))
    }

    suspend fun addPermission(selectedDevice: String, permission: String, selectedPackage: String) {
        launchAdbShell(selectedDevice, Commands.addSpecificPermission(selectedPackage, permission))
    }

    suspend fun removePermission(selectedDevice: String, permission: String, selectedPackage: String) {
        launchAdbShell(selectedDevice, Commands.revokeSpecificPermission(selectedPackage, permission))
    }

    suspend fun getPermissions(selectedDevice: String, selectedPackage: String) {
        val result = launchAdbShellCommand(selectedDevice, Commands.getGrantedPermissions(selectedPackage))
        log("output :$result")
    }

    suspend fun changeFontSize(d: Double, selectedDevice: String) {
        launchAdbShell(selectedDevice, Commands.getChangeFontSize(d))
    }

    suspend fun changeDisplayDensity(density: Int, selectedDevice: String) {
        val d = if (density == 0) "reset" else "$density"
        launchAdbShell(selectedDevice, Commands.getChangeDisplayDensity(d))
    }

    suspend fun installApk(pathApk: String, selectedDevice: String) {
        launchAdb(selectedDevice, Commands.getAdbInstall(pathApk))
    }

    // Private - Launch nonAdb commands
    private suspend fun launchNonAdb(
        command: String,
        path: String = Commands.getPlatformToolsPath()
    ): ArrayList<String> {
        return cmd.execute(command, log, path)
    }

    // Private - Launch regular adb  commands
    private suspend fun launchAdb(
        selectedDevice: String,
        command: String,
        path: String = Commands.getPlatformToolsPath()
    ) {
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchAdbOnAllDevices(command, path)
        } else {
            launchAdbCommand(selectedDevice, command, path)
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
    private suspend fun launchAdbShell(selectedDevice: String, command: String) {
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchAdbShellCommandOnAllDevices(command)
        } else {
            launchAdbShellCommand(selectedDevice, command)
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
