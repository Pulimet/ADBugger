package ui.sections.emulator

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.lineawesomeicons.Android
import compose.icons.tablericons.Wiper
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.BtnWithText

@Composable
fun EmulatorItem(
    emulatorName: String,
    coroutineScope: CoroutineScope,
    model: AppStore = koinInject()
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().height(50.dp)) {
        Text(
            text = emulatorName,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )

        if (emulatorName != AppStore.EMULATOR_NONE) {
            BtnWithText(
                icon = TablerIcons.Wiper,
                onClick = { model.onWipeAndLaunch(coroutineScope, emulatorName) },
                description = "Wipe Data",
                modifier = Modifier.padding(end = 4.dp),
                width = 100.dp
            )
            BtnWithText(
                icon = LineAwesomeIcons.Android,
                onClick = { model.onLaunchEmulatorClick(coroutineScope, emulatorName) },
                description = "Launch Emulator",
                modifier = Modifier.padding(end = 8.dp),
                width = 100.dp
            )
        }
    }
}
