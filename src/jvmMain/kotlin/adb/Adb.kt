package adb

import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.pkg.PmListRequest
import com.malinskiy.adam.request.shell.v2.ShellCommandRequest

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

    suspend fun openPackage(packageName: String) {
        launchOnAllDevices(Commands.getLaunchCommandByPackageName(packageName))
    }


    suspend fun closePackage(packageName: String) {
        launchOnAllDevices(Commands.getCloseCommandByPackageName(packageName))
    }


    // Private
    private suspend fun launchOnAllDevices(command: String) {
        devices().forEach {
            launchCommand(it.serial, command)
        }
    }

    private suspend fun launchCommand(serial: String, command: String) {
        adb.execute(
            request = ShellCommandRequest(command),
            serial = serial
        )
    }

}