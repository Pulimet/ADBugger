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

    suspend fun openPackage(packageName: String, selectedDevice: String) {
        val command = Commands.getLaunchCommandByPackageName(packageName)
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchShellCommandOnAllDevices(command)
        } else {
            launchShellCommand(selectedDevice, command)
        }
    }

    suspend fun closePackage(packageName: String, selectedDevice: String) {
        val command = Commands.getCloseCommandByPackageName(packageName)
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchShellCommandOnAllDevices(command)
        } else {
            launchShellCommand(selectedDevice, command)
        }
    }


    suspend fun clearData(packageName: String, selectedDevice: String) {
        val command = Commands.getClearDataCommandByPackageName(packageName)
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchShellCommandOnAllDevices(command)
        } else {
            launchShellCommand(selectedDevice, command)
        }
    }

    suspend fun killAllEmulators() {
        devices().forEach {
            execCommand(Commands.getKillEmulator(it.serial))
        }
    }

    // Private
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