package store

import adb.Adb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.malinskiy.adam.request.device.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppStore {

    companion object {
        const val ALL_DEVICES = "All"
        private const val MY_ATT_PACKAGE = "com.att.myWirelessTest"
    }

    private val adb = Adb()
    var state: AppState by mutableStateOf(initialState())
        private set

    // Public
    fun onLaunchedEffect(coroutineScope: CoroutineScope) {
        coroutineScope.launch { adb.startAdbInteract() }
    }

    // User Actions
    fun onGetDevicesListClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            setState { copy(selectedDevice = ALL_DEVICES, isDevicesLoading = true, devicesList = emptyList()) }
            delay(500)
            setState { copy(isDevicesLoading = false, devicesList = adb.devices()) }
        }
    }

    fun onDeviceClick(device: Device) {
        setState { copy(selectedDevice = device.serial) }
    }

    fun onOpenClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.openPackage(MY_ATT_PACKAGE)
        }
    }

    fun onCloseClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.closePackage(MY_ATT_PACKAGE)
        }
    }

    // Private
    private fun initialState() = AppState()

    private inline fun setState(update: AppState.() -> AppState) {
        state = state.update()
    }

    data class AppState(
        val devicesList: List<Device> = emptyList(),
        val selectedDevice: String = ALL_DEVICES,
        val isDevicesLoading: Boolean = false
    )
}