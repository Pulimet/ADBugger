package store

import adb.Adb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.pkg.Package
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppStore {

    companion object {
        const val ALL_DEVICES = "All"
        const val NONE = "None"
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
        coroutineScope.launch(Dispatchers.IO) {
            setState { copy(isDevicesLoading = true, devicesList = emptyList(), selectedDevice = ALL_DEVICES) }
            delay(500)
            setState { copy(isDevicesLoading = false, devicesList = adb.devices()) }
        }
    }

    fun onDeviceClick(device: Device) {
        setState { copy(selectedDevice = device.serial) }
    }

    fun onGetPackageListClick(coroutineScope: CoroutineScope) {
        if (state.selectedDevice == ALL_DEVICES) {
            return
        }
        coroutineScope.launch(Dispatchers.IO) {
            setState { copy(isPackagesLoading = true, packageList = emptyList(), selectedPackage = NONE) }
            delay(500)
            setState { copy(isPackagesLoading = false, packageList = adb.packages(state.selectedDevice)) }
        }
    }

    fun onPackageClick(pckg: Package) {
        setState { copy(selectedPackage = pckg.name) }
    }

    fun onOpenClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onCloseClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
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
        val packageList: List<Package> = emptyList(),
        val selectedPackage: String = NONE,
        val isDevicesLoading: Boolean = false,
        val isPackagesLoading: Boolean = false,
    )
}