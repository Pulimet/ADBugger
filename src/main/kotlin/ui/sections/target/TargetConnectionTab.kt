package ui.sections.target

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_kill
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_start
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_usb
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.buttons.BtnWithText

@Composable
fun TargetConnectionTab(model: AppStore = koinInject()) {
    Row {
        BtnWithText(
            onClick = { model.onStartServer() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.target_adb_start),
            width = Dimensions.targetCommandsBtnWidth,
        )
        BtnWithText(
            onClick = { model.onKillServer() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.target_adb_kill),
            width = Dimensions.targetCommandsBtnWidth,
        )
        BtnWithText(
            onClick = { model.onAdbUsb() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.target_adb_usb),
            width = Dimensions.targetCommandsBtnWidth,
        )
    }
}
