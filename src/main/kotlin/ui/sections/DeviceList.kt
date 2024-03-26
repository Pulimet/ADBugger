package ui.sections

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.unit.sp
import com.malinskiy.adam.request.device.DeviceState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import kotlinx.coroutines.CoroutineScope
import model.DeviceInfo
import store.AppStore
import ui.theme.Dimens
import ui.theme.MyColors
import ui.widgets.BtnIcon
import ui.widgets.LoadingSpinner


@Composable
fun DeviceListSection(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(Dimens.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPadding)) {
            AllOptionAndRefreshButton(model, coroutineScope)
            DeviceList(model, coroutineScope) { model.onDeviceClick(it) }
        }
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (state.isDevicesLoading) {
            LoadingSpinner(Modifier.padding(Dimens.spinnerPadding).fillMaxWidth())
        } else {
            val allDevices = DeviceInfo(AppStore.ALL_DEVICES, DeviceState.UNKNOWN, "")
            DeviceItem(
                allDevices,
                state.selectedDevice == AppStore.ALL_DEVICES,
                { model.onDeviceClick(allDevices) },
                Modifier.weight(1f),
                model,
                coroutineScope
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
private fun DeviceList(model: AppStore, coroutineScope: CoroutineScope, onClicked: (device: DeviceInfo) -> Unit) {
    val state = model.state
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(state.devicesList, key = { device -> device.serial }) { item ->
                DeviceItem(
                    item,
                    state.selectedDevice == item.serial,
                    { onClicked(it) },
                    Modifier.fillMaxWidth(),
                    model,
                    coroutineScope
                )
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
    modifier: Modifier = Modifier,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(
            selected = isSelected, onClick = { onClicked(item) }, colors = RadioButtonDefaults.colors(
                selectedColor = MyColors.radioButtonSelected,
                unselectedColor = MyColors.radioButtonUnselected
            )
        )
        val name = if (item.name.isEmpty()) "" else " (${item.name})"
        Text(text = "${item.serial}$name", color = Color.White, fontSize = 12.sp, modifier = Modifier.weight(1f))
        if (item.serial.contains("emulator")) {
            BtnIcon(
                icon = FontAwesomeIcons.Solid.BookDead,
                onClick = { model.onKillEmulatorClick(coroutineScope, item.serial) },
                description = "Kill Emulator",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
