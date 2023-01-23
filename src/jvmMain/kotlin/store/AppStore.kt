package store

import adb.Adb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppStore {

    companion object {
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

    fun updateDevicesList(devicesList: List<String>) {
        setState { copy(devicesList = devicesList) }
    }


    // Private
    private fun initialState() =
        AppState(
            devicesList = emptyList()
        )

    private inline fun setState(update: AppState.() -> AppState) {
        state = state.update()
    }



    data class AppState(
        val devicesList: List<String> = emptyList()
    )
}