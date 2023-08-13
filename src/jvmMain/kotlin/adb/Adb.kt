package adb

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.pkg.PmListRequest
import com.malinskiy.adam.request.shell.v2.ShellCommandRequest
import com.malinskiy.adam.request.shell.v2.ShellCommandResult
import model.DeviceInfo
import store.AppStore
import java.io.BufferedReader
import java.io.InputStreamReader


class Adb {

    private val adb: AndroidDebugBridgeClient by lazy { AndroidDebugBridgeClientFactory().build() }


    // Public
    suspend fun startAdbInteract() {
        StartAdbInteractor().execute()
    }

    suspend fun devicesInfo() = devices().map {
        val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
        DeviceInfo(it.serial, it.state, avdName)
    }

    suspend fun packages(serial: String) = adb.execute(
        request = PmListRequest(
            includePath = false
        ), serial = serial
    )

    fun emulators(log: (String) -> Unit): List<String> {
        return execCommand(Commands.getEmulatorList(), log, Commands.getEmulatorPath())
    }

    suspend fun openPackage(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getLaunchCommand(selectedPackage))
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
        execCommand(Commands.getKillEmulatorBySerial(serial), path = Commands.getPlatformToolsPath())
    }

    fun launchEmulator(emulatorName: String) {
        execCommand(Commands.getLaunchEmulator(emulatorName), path = Commands.getEmulatorPath())
    }

    fun wipeAndLaunchEmulator(emulatorName: String) {
        execCommand(Commands.getWipeDataEmulatorByName(emulatorName), path = Commands.getEmulatorPath())
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
        execCommand("adb -s $selectedDevice pull /sdcard/$filename", path = Commands.getPlatformToolsPath())
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

    suspend fun reversePort(port: Int, log: (String) -> Unit) {
        devices().forEach {
            val resultList = execCommand(Commands.adbReverse(it.serial, port), log, Commands.getPlatformToolsPath())
            resultList.forEach {
                log(it)
            }
        }
    }

    fun reverseList(log: (String) -> Unit) {
        val resultList = execCommand(Commands.adbReverseList(), log, Commands.getPlatformToolsPath())
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

    suspend fun getPermissions(selectedDevice: String, selectedPackage: String, log: (String) -> Unit) {
        val command = "adb shell " + Commands.getGrantedPermissions(selectedPackage)
        log(command)
        val result = launchShellCommand(selectedDevice, command)
        log("output :" + result.output)
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

    private suspend fun launchShellCommand(serial: String, command: String): ShellCommandResult = adb.execute(
        request = ShellCommandRequest(command), serial = serial
    )

    private fun execCommand(command: String, log: (String) -> Unit = {}, path: String = ""): ArrayList<String> {
        log(command)
        val p = Runtime.getRuntime().exec(arrayOf("/bin/zsh", "-c", "${path}$command"))
        p.waitFor()
        val reader = BufferedReader(InputStreamReader(p.inputStream))
        val responseList = arrayListOf<String>()

        reader.use { bufferedReader ->
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let { responseList.add(it) }
            }
        }

        return responseList
    }

    private suspend fun devices() = adb.execute(request = ListDevicesRequest())
}
