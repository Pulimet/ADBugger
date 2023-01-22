import com.malinskiy.adam.AndroidDebugBridgeClient
import com.malinskiy.adam.AndroidDebugBridgeClientFactory
import com.malinskiy.adam.interactor.StartAdbInteractor
import com.malinskiy.adam.request.device.ListDevicesRequest
import com.malinskiy.adam.request.shell.v2.ShellCommandRequest

class Adb {
    companion object {
        private const val MY_ATT_PACKAGE = "com.att.myWirelessTest"
    }

    private val adb: AndroidDebugBridgeClient by lazy { AndroidDebugBridgeClientFactory().build() }


    // Public
    suspend fun openAtt() {
        startAdbInteract()
        val command = getLaunchCommandByPackageName(MY_ATT_PACKAGE)
        devices().forEach {
            launchCommand(it.serial, command)
        }
    }

    suspend fun closeAtt() {
        startAdbInteract()
        val command = getCloseCommandByPackageName(MY_ATT_PACKAGE)
        devices().forEach {
            launchCommand(it.serial, command)
        }
    }


    // Private
    private suspend fun startAdbInteract() {
        StartAdbInteractor().execute()
    }

    @Suppress("SameParameterValue")
    private fun getLaunchCommandByPackageName(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    @Suppress("SameParameterValue")
    private fun getCloseCommandByPackageName(packageName: String) =
        "am force-stop  $packageName"

    private suspend fun devices() = adb.execute(request = ListDevicesRequest())

    private suspend fun launchCommand(serial: String, command: String) {
        adb.execute(
            request = ShellCommandRequest(command),
            serial = serial
        )
    }

}