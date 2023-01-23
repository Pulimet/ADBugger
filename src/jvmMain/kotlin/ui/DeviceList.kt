package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.malinskiy.adam.request.device.Device
import com.malinskiy.adam.request.device.DeviceState
import kotlinx.coroutines.CoroutineScope
import store.AppStore


@Composable
fun DeviceListSection(
    coroutineScope: CoroutineScope,
    model: AppStore,
    state: AppStore.AppState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            val allDevices = Device(AppStore.ALL_DEVICES, DeviceState.UNKNOWN)
            DeviceItem(
                allDevices,
                state.selectedDevice == AppStore.ALL_DEVICES,
                { model.onDeviceClick(allDevices) },
                Modifier.fillMaxWidth(0.6f)
            )
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                onClick = { model.onGetDevicesListClick(coroutineScope) })
            { Text(text = "Refresh") }
        }
        DeviceList(state) { model.onDeviceClick(it) }
    }
    if (state.isDevicesLoading) {
        LoadingSpinner()
    }
}

@Composable
private fun DeviceList(state: AppStore.AppState, onClicked: (device: Device) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(state.devicesList, key = { device -> device.serial }) { item ->
                DeviceItem(item, state.selectedDevice == item.serial, { onClicked(it) }, Modifier.fillMaxWidth())
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = listState
            )
        )
    }
}

@Composable
private fun DeviceItem(
    item: Device,
    isSelected: Boolean,
    onClicked: (device: Device) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(selected = isSelected, { onClicked(item) })
        val state = if (item.state == DeviceState.UNKNOWN) "" else " (${item.state.name})"
        Text("${item.serial}$state")
    }
}
