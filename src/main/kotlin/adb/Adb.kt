package adb

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.pkg.PmListRequest
import model.DeviceInfo
import store.AppStore

class Adb(private val log: (String) -> Unit) {

    private val adb: AndroidDebugBridgeClient by lazy { AndroidDebugBridgeClientFactory().build() }

    // Public
    suspend fun startAdbInteract() {
        StartAdbInteractor().execute()
    }

    fun devicesInfo() =
        Cmd.execute(Commands.getDeviceList(), log, Commands.getPlatformToolsPath()).drop(1).dropLast(1).map {
            // For case I need it later
            // val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
            val serialAndType = it.split("\\s+".toRegex())
            DeviceInfo(serialAndType[0], serialAndType[1])
        }

    suspend fun packages(serial: String) = adb.execute(
        request = PmListRequest(
            includePath = false
        ), serial = serial
    )

    fun emulators(): List<String> {
        return Cmd.execute(Commands.getEmulatorList(), log, Commands.getEmulatorPath())
    }

    suspend fun openPackage(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getLaunchCommand(selectedPackage))
    }

    fun getApkPath(selectedPackage: String, selectedDevice: String) {
        val result = Cmd.execute(
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
        devices().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    fun killEmulatorBySerial(serial: String) {
        Cmd.execute(Commands.getKillEmulatorBySerial(serial), path = Commands.getPlatformToolsPath())
    }

    fun launchEmulator(emulatorName: String) {
        Cmd.execute(Commands.getLaunchEmulator(emulatorName), path = Commands.getEmulatorPath())
    }

    fun wipeAndLaunchEmulator(emulatorName: String) {
        Cmd.execute(Commands.getWipeDataEmulatorByName(emulatorName), path = Commands.getEmulatorPath())
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

    fun takeSnapshot(selectedDevice: String) {
        val filename = "snap_$selectedDevice.png"
        launchShellCommand(selectedDevice, "screencap -p /sdcard/$filename")
        val pullCommand = "adb -s $selectedDevice pull /sdcard/$filename ~/Desktop/$filename"
        log(pullCommand)
        Cmd.execute(
            pullCommand,
            path = Commands.getPlatformToolsPath()
        )
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
        devices().forEach { device ->
            val resultList = Cmd.execute(Commands.adbReverse(device.serial, port), log, Commands.getPlatformToolsPath())
            resultList.forEach {
                log(it)
            }
        }
    }

    fun reverseList() {
        val resultList = Cmd.execute(Commands.adbReverseList(), log, Commands.getPlatformToolsPath())
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

    fun getPermissions(selectedDevice: String, selectedPackage: String) {
        val command = "adb shell " + Commands.getGrantedPermissions(selectedPackage)
        log(command)
        val result = launchShellCommand(selectedDevice, command)
        log("output :$result")
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
        devices().forEach {
            launchShellCommand(it.serial, command)
        }
    }

    private fun launchShellCommand(serial: String, command: String): ArrayList<String> {
        val commandToExecute = "adb -s $serial shell $command"
        log(commandToExecute)
        return Cmd.execute(commandToExecute)
    }


    private suspend fun devices() = adb.execute(request = ListDevicesRequest())
}
