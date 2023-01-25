package adb

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.pkg.PmListRequest
import com.malinskiy.adam.request.shell.v2.ShellCommandRequest
import store.AppStore

class Adb {

    private val adb: AndroidDebugBridgeClient by lazy { AndroidDebugBridgeClientFactory().build() }


    // Public
    suspend fun startAdbInteract() {
        StartAdbInteractor().execute()
    }

    suspend fun devices() = adb.execute(request = ListDevicesRequest())
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
        launchShell(selectedDevice,  Commands.getCloseCommand(selectedPackage))
    }


    suspend fun clearData(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice,  Commands.getClearDataCommand(selectedPackage))
    }

    suspend fun uninstall(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice,  Commands.getUninstallCommand(selectedPackage))
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

    private suspend fun launchShellCommand(serial: String, command: String) {
        adb.execute(
            request = ShellCommandRequest(command),
            serial = serial
        )
    }

    private fun execCommand(command: String) {
        Runtime.getRuntime().exec(command)
    }

}