package ui.sections.emulator.run.list

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
import compose.icons.LineAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.lineawesomeicons.Android
import compose.icons.tablericons.Wiper
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_btn_launch_emulator
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_btn_wipe_data
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.buttons.BtnWithText

@Composable
fun EmulatorItem(
    emulatorName: String,
    model: AppStore = koinInject(),
    onLaunchEmulatorClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {
        Text(
            text = emulatorName,
            color = Color.White,
            fontSize = Dimensions.emulatorItemFontSize,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )

        if (emulatorName != AppStore.EMULATOR_NONE) {
            BtnWithText(
                icon = TablerIcons.Wiper,
                onClick = { model.onWipeAndLaunch(emulatorName) },
                description = stringResource(Res.string.emulators_btn_wipe_data),
                modifier = Modifier.padding(end = 4.dp),
                width = Dimensions.btnEmulatorWidth
            )
            BtnWithText(
                icon = LineAwesomeIcons.Android,
                onClick = { onLaunchEmulatorClick(emulatorName) },
                description = stringResource(Res.string.emulators_btn_launch_emulator),
                modifier = Modifier.padding(end = 8.dp),
                width = Dimensions.btnEmulatorWidth
            )
        }
    }
}
