package ui.sections.emulator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.BtnWithText

@Composable
fun EmulatorsTopMenu(model: AppStore = koinInject()) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    model.onGetEmulatorsListClick()
                }
            },
            description = "Refresh Emulators List",
            width = 160.dp
        )
        BtnWithText(
            icon = FontAwesomeIcons.Solid.BookDead,
            onClick = { model.onKillAllEmulatorClick() },
            description = "Kill All Emulators",
            modifier = Modifier.padding(horizontal = 8.dp),
            width = 160.dp
        )
    }
}
