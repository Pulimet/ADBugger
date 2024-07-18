package adb

import model.DeviceInfo
import model.Package
import store.AppStore

class Adb(private val cmd: Cmd) {

    private var log: (String) -> Unit = { println(it) }

    // Public
    fun setLogger(logger: (String) -> Unit) {
        log = logger
    }

    suspend fun devicesInfo() =
        cmd.execute(Commands.getDeviceList(), log, Commands.getPlatformToolsPath()).drop(1).dropLast(1).map {
            // For case I need it later
            // val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
            val serialAndType = it.split("\\s+".toRegex())
            DeviceInfo(serialAndType[0], serialAndType[1])
        }

    suspend fun packages(serial: String): ArrayList<Package> {
        return launchShellCommand(serial, Commands.getPackageList())
            .map { Package(it.split(":").last()) }
            .filter { it.name.isNotEmpty() } as ArrayList<Package>
    }

    suspend fun emulators(): List<String> {
        return cmd.execute(Commands.getEmulatorList(), log, Commands.getEmulatorPath())
    }

    suspend fun openPackage(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getLaunchCommand(selectedPackage))
    }

    suspend fun getApkPath(selectedPackage: String, selectedDevice: String) {
        val result = cmd.execute(
            Commands.getApkPathCommand(selectedPackage, selectedDevice),
            log,
            Commands.getPlatformToolsPath()
        )
        result.forEach {
            log(it)
        }
    }

    suspend fun closePackage(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getClearDataCommand(selectedPackage))
    }

    suspend fun uninstall(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getUninstallCommand(selectedPackage))
    }

    suspend fun killAllEmulators() {
        devicesInfo().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    suspend fun killEmulatorBySerial(serial: String) {
        cmd.execute(Commands.getKillEmulatorBySerial(serial), log, path = Commands.getPlatformToolsPath())
    }

    suspend fun launchEmulator(emulatorName: String) {
        cmd.execute(Commands.getLaunchEmulator(emulatorName), log, path = Commands.getEmulatorPath())
    }

    suspend fun wipeAndLaunchEmulator(emulatorName: String) {
        cmd.execute(Commands.getWipeDataEmulatorByName(emulatorName), log, path = Commands.getEmulatorPath())
    }

    suspend fun showHome(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getShowHome())
    }

    suspend fun showSettings(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getShowSettings())
    }

    suspend fun pressBack(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressBack())
    }

    suspend fun pressTab(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressTab())
    }

    suspend fun pressEnter(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressEnter())
    }

    suspend fun pressPower(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressPower())
    }

    suspend fun takeSnapshot(selectedDevice: String) {
        val filename = "snap_$selectedDevice.png"
        launchShellCommand(selectedDevice, "screencap -p /sdcard/$filename")
        val pullCommand = "adb -s $selectedDevice pull /sdcard/$filename ~/Desktop/$filename"
        cmd.execute(pullCommand, log, path = Commands.getPlatformToolsPath())
        launchShellCommand(selectedDevice, "rm /sdcard/$filename")
    }


    suspend fun setDarkModeOff(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDarkModeOff())
    }

    suspend fun setDarkModeOn(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDarkModeOn())
    }

    suspend fun pressUp(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getUp())
    }

    suspend fun pressDown(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDown())
    }

    suspend fun pressLeft(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getLeft())
    }

    suspend fun pressRight(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getRight())
    }

    suspend fun pressDelete(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDelete())
    }

    suspend fun sendText(selectedDevice: String, value: String) {
        launchShell(selectedDevice, Commands.sendTextCommand(value))
    }

    suspend fun sendInput(selectedDevice: String, value: Int) {
        launchShell(selectedDevice, Commands.sendInputCommand(value))
    }

    suspend fun sendInputNum(selectedDevice: String, num: Int) {
        launchShell(selectedDevice, Commands.sendInputCommand((num + 7)))
    }

    suspend fun reversePort(port: Int) {
        devicesInfo().forEach { device ->
            val resultList = cmd.execute(Commands.adbReverse(device.serial, port), log, Commands.getPlatformToolsPath())
            resultList.forEach {
                log(it)
            }
        }
    }

    suspend fun reverseList() {
        val resultList = cmd.execute(Commands.adbReverseList(), log, Commands.getPlatformToolsPath())
        resultList.forEach {
            log(it)
        }
    }

    suspend fun removeAllPermissions(selectedDevice: String, selectedPackage: String) {
        launchShell(selectedDevice, Commands.getRevokeAllPermissions(selectedPackage))
    }

    suspend fun addPermission(selectedDevice: String, permission: String, selectedPackage: String) {
        launchShell(selectedDevice, Commands.addSpecificPermission(selectedPackage, permission))
    }

    suspend fun removePermission(selectedDevice: String, permission: String, selectedPackage: String) {
        launchShell(selectedDevice, Commands.revokeSpecificPermission(selectedPackage, permission))
    }

    suspend fun getPermissions(selectedDevice: String, selectedPackage: String) {
        val result = launchShellCommand(selectedDevice, Commands.getGrantedPermissions(selectedPackage))
        log("output :$result")
    }


    suspend fun changeFontSize(d: Double, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getChangeFontSize(d))
    }

    suspend fun changeDisplayDensity(density: Int, selectedDevice: String) {
        val d = if (density == 0) "reset" else "$density"
        launchShell(selectedDevice, Commands.getChangeDisplayDensity(d))
    }

    // Private
    private suspend fun launchShell(selectedDevice: String, command: String) {
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchShellCommandOnAllDevices(command)
        } else {
            launchShellCommand(selectedDevice, command)
        }
    }

    private suspend fun launchShellCommandOnAllDevices(command: String) {
        devicesInfo().forEach {
            launchShellCommand(it.serial, command)
        }
    }

    private suspend fun launchShellCommand(serial: String, command: String): ArrayList<String> {
        val commandToExecute = "adb -s $serial shell $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, Commands.getPlatformToolsPath())
    }
}
