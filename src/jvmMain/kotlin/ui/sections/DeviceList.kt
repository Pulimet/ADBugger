package ui.sections

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
import com.malinskiy.adam.request.device.DeviceState
import kotlinx.coroutines.CoroutineScope
import model.DeviceInfo
import store.AppStore
import ui.widgets.LoadingSpinner
import ui.widgets.SectionTitle


@Composable
fun DeviceListSection(
    coroutineScope: CoroutineScope,
    model: AppStore,
    state: AppStore.AppState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Content(state, model, coroutineScope)
        SectionTitle("Device/s selection")
        if (state.isDevicesLoading) {
            LoadingSpinner()
        }
    }
}


@Composable
private fun Content(
    state: AppStore.AppState,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp, 26.dp, 16.dp, 16.dp)
            .border(1.dp, Color.LightGray, RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AllOptionAndRefreshButton(state, model, coroutineScope)
        DeviceList(state) { model.onDeviceClick(it) }
    }
}

@Composable
private fun AllOptionAndRefreshButton(
    state: AppStore.AppState,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = Color.LightGray)
    ) {
        val allDevices = DeviceInfo(AppStore.ALL_DEVICES, DeviceState.UNKNOWN, "")
        DeviceItem(
            allDevices,
            state.selectedDevice == AppStore.ALL_DEVICES,
            { model.onDeviceClick(allDevices) },
        )
        Button(onClick = { model.onKillEmulatorClick(coroutineScope) }) { Text(text = "Kill Emu.") }
        Button(
            modifier = Modifier.padding(end = 8.dp),
            enabled = !state.isDevicesLoading,
            onClick = { model.onGetDevicesListClick(coroutineScope) })
        { Text(text = "Refresh") }
    }
}

@Composable
private fun DeviceList(state: AppStore.AppState, onClicked: (device: DeviceInfo) -> Unit) {
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
    item: DeviceInfo,
    isSelected: Boolean,
    onClicked: (device: DeviceInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(selected = isSelected, { onClicked(item) })
        val name = if (item.name.isEmpty()) "" else " (${item.name})"
        Text("${item.serial}$name")
    }
}
