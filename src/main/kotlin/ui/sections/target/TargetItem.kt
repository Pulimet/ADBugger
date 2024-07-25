package ui.sections.target

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.EvaIcons
import compose.icons.FontAwesomeIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.CheckmarkCircle2
import compose.icons.evaicons.outline.RadioButtonOff
import compose.icons.evaicons.outline.RadioButtonOn
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import model.TargetInfo
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.MyColors
import ui.widgets.BtnWithText

@Composable
fun TargetItem(
    item: TargetInfo,
    isSelected: Boolean,
    onClicked: (device: TargetInfo, isSelected: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item, !isSelected) })
    ) {
        IconToggleButton(
            checked = isSelected,
            onCheckedChange = { onClicked(item, it) },
        ) {
            val selectedVector = if (model.state.selectedTargetsList.size > 1) {
                EvaIcons.Outline.CheckmarkCircle2
            } else {
                EvaIcons.Outline.RadioButtonOn
            }
            val imageVector = if (isSelected) selectedVector else EvaIcons.Outline.RadioButtonOff
            Icon(
                imageVector = imageVector,
                contentDescription = "Radio Button",
                tint = if (isSelected) MyColors.radioButtonSelected else MyColors.radioButtonUnselected
            )
        }

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
