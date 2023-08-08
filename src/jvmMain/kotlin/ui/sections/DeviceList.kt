package ui.sections

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.malinskiy.adam.request.device.DeviceState
import kotlinx.coroutines.CoroutineScope
import model.DeviceInfo
import store.AppStore
import ui.theme.Paddings
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard
import ui.widgets.LoadingSpinner


@Composable
fun DeviceListSection(
    coroutineScope: CoroutineScope,
    model: AppStore,
) {
    ExpandableCard(
        title = "Device Selection",
        modifier = Modifier.padding(
            horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical
        )
    ) {
        Content(model, coroutineScope)
    }
}


@Composable
private fun Content(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AllOptionAndRefreshButton(model, coroutineScope)
        DeviceList(model.state) { model.onDeviceClick(it) }
    }
}

@Composable
private fun AllOptionAndRefreshButton(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    val state = model.state
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (state.isDevicesLoading) {
            LoadingSpinner(Modifier.padding(4.dp).fillMaxWidth())
        } else {
            val allDevices = DeviceInfo(AppStore.ALL_DEVICES, DeviceState.UNKNOWN, "")
            DeviceItem(
                allDevices,
                state.selectedDevice == AppStore.ALL_DEVICES,
                { model.onDeviceClick(allDevices) },
                Modifier.weight(1f)
            )

            BtnIcon(
                icon = Icons.Rounded.Refresh,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = !state.isDevicesLoading,
                onClick = { model.getDevicesList(coroutineScope) },
                description = "Refresh Device List"
            )

        }
    }
}

@Composable
private fun DeviceList(state: AppStore.AppState, onClicked: (device: DeviceInfo) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().heightIn(max = 120.dp)) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(state.devicesList, key = { device -> device.serial }) { item ->
                DeviceItem(item, state.selectedDevice == item.serial, { onClicked(it) }, Modifier.fillMaxWidth())
            }
        }
        if (state.devicesList.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
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
        RadioButton(
            selected = isSelected, onClick = { onClicked(item) }, colors = RadioButtonDefaults.colors(
                selectedColor = Color.White,
                unselectedColor = Color.LightGray
            )
        )
        val name = if (item.name.isEmpty()) "" else " (${item.name})"
        Text(text = "${item.serial}$name", color = Color.White)
    }
}
