package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.device.DeviceState
import store.AppStore

@Composable
fun DeviceList(state: AppStore.AppState, onClicked: (device: Device) -> Unit) {
    val listState = rememberLazyListState()
    DeviceItem(
        Device(AppStore.ALL_DEVICES, DeviceState.UNKNOWN),
        state.selectedDevice == AppStore.ALL_DEVICES,
        onClicked
    )
    LazyColumn(state = listState) {
        items(state.devicesList, key = { device -> device.serial }) { item ->
            DeviceItem(item, state.selectedDevice == item.serial) { onClicked(it) }
        }
    }
}

@Composable
private fun DeviceItem(item: Device, isSelected: Boolean, onClicked: (device: Device) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(selected = isSelected, { onClicked(item) })
        val state = if (item.state == DeviceState.UNKNOWN) "" else " (${item.state.name})"
        Text("${item.serial}$state")
    }
}
