package ui.sections.target

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import model.DeviceInfo
import store.AppStore
import ui.widgets.BtnWithText

@Composable
fun AllTargetsAndRefreshButton(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    val state = model.state
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val allDevices = DeviceInfo(AppStore.ALL_DEVICES, "All Devices")
        TargetItem(
            allDevices,
            state.selectedDevice == AppStore.ALL_DEVICES,
            { model.onDeviceClick(allDevices) },
            Modifier.weight(1f),
            model,
            coroutineScope
        )

        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = true,
            onClick = { model.getDevicesList(coroutineScope) },
            description = "Refresh List",
            width = 120.dp,
        )
    }
}