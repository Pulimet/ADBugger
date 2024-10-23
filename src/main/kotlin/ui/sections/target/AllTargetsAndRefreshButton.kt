package ui.sections.target

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import model.TargetInfo
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.target_btn_refresh_list
import net.alexandroid.adbugger.adbugger.generated.resources.target_type_all_devices
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.buttons.BtnWithText

@Composable
fun AllTargetsAndRefreshButton(model: AppStore = koinInject()) {
    val state = model.state
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(vertical = 8.dp)
    ) {
        val allDevices =
            TargetInfo(AppStore.ALL_DEVICES, stringResource(Res.string.target_type_all_devices))
        TargetItem(
            allDevices,
            state.selectedTargetsList.isEmpty(),
            { item, isSelected -> model.onTargetClick(item, isSelected) },
            Modifier.weight(1f),
        )

        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = true,
            onClick = { model.getDevicesList() },
            description = stringResource(Res.string.target_btn_refresh_list),
            width = 120.dp,
        )
    }
}
