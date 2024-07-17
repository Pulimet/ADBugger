package ui.sections.target

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import kotlinx.coroutines.CoroutineScope
import model.DeviceInfo
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.MyColors
import ui.widgets.BtnWithText

@Composable
fun TargetItem(
    item: DeviceInfo,
    isSelected: Boolean,
    onClicked: (device: DeviceInfo) -> Unit,
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
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

        Text(
            text = "${item.serial} - ${item.type}",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 6.dp).weight(1f)
        )

        if (item.serial.contains("emulator")) {
            BtnWithText(
                icon = FontAwesomeIcons.Solid.BookDead,
                onClick = { model.onKillEmulatorClick(item.serial) },
                description = "Kill Emulator",
                modifier = Modifier.padding(horizontal = 8.dp),
                width = 120.dp,
            )
        }
    }
}
