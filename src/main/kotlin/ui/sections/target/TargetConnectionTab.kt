package ui.sections.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_kill
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_start
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_tcp_ip
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_usb
import net.alexandroid.adbugger.adbugger.generated.resources.target_label_port
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText

@Composable
fun TargetConnectionTab(model: AppStore = koinInject()) {
    var inputPortTcpIp by remember { mutableStateOf(TextFieldValue("5555")) }

    Column {

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
        Row(modifier = Modifier.padding(top = 6.dp)) {
            TextFieldX(
                modifier = Modifier.padding(6.dp),
                singleLine = true,
                value = inputPortTcpIp,
                padding = PaddingValues(top = 3.dp),
                keyboardType = KeyboardType.Number,
                label = stringResource(Res.string.target_label_port),
                onValueChange = { value -> inputPortTcpIp = value }
            )
            BtnWithText(
                onClick = { model.onAdbTcpIp(inputPortTcpIp.text) },
                icon = Icons.Rounded.CheckCircle,
                description = stringResource(Res.string.target_adb_tcp_ip),
                width = Dimensions.targetCommandsBtnWidth,
            )
        }
    }
}
