package store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppStore {
    var state: AppState by mutableStateOf(initialState())
        private set

    // Public
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