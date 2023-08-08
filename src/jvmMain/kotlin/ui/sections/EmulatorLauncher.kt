package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.PowerOffSolid
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.MyColors
import ui.theme.Paddings
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard

@Composable
fun EmulatorLauncher(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    ExpandableCard(
        title = "Emulator Launcher",
        modifier = Modifier.padding(
            horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 4.dp).background(MyColors.bg2)
        ) {
            BtnIcon(
                icon = LineAwesomeIcons.PowerOffSolid,
                onClick = { model.onKillEmulatorClick(coroutineScope) },
                description = "Kill All Emulators"
            )
        }
    }
}
