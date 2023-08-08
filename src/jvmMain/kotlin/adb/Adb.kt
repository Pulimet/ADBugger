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
        ),
        serial = serial
    )

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
            killEmulator(it.serial)
        }
    }

    private fun killEmulator(serial: String) {
        execCommand(Commands.getKillEmulator(serial))
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
        execCommand("adb -s $selectedDevice pull /sdcard/$filename")
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

    suspend fun reversePort(port: Int, log: (String) -> Unit) {
        devices().forEach {
            execCommand(Commands.adbReverse(it.serial, port), log)
        }
    }

    fun reverseList(log: (String) -> Unit) {
        execCommand(Commands.adbReverseList(), log)
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

    private suspend fun launchShellCommand(serial: String, command: String): ShellCommandResult =
        adb.execute(
            request = ShellCommandRequest(command),
            serial = serial
        )

    private fun execCommand(command: String, log: (String) -> Unit = {}) {
        log(command)
        val p = Runtime.getRuntime().exec(arrayOf("/bin/zsh", "-c", "~/Library/Android/sdk/platform-tools/$command"))
        p.waitFor()
        val bufferedReader = BufferedReader(InputStreamReader(p.inputStream))
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            line?.let { log(it) }
        }
    }

    private suspend fun devices() = adb.execute(request = ListDevicesRequest())

}
