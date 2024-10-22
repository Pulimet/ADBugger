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
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_connect
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_disconnect
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_kill
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_pair
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_start
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_tcp_ip
import net.alexandroid.adbugger.adbugger.generated.resources.target_adb_usb
import net.alexandroid.adbugger.adbugger.generated.resources.target_disconnect_all
import net.alexandroid.adbugger.adbugger.generated.resources.target_get_device_ip
import net.alexandroid.adbugger.adbugger.generated.resources.target_label_ip
import net.alexandroid.adbugger.adbugger.generated.resources.target_label_paring_code
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
    var inputPortConnect by remember { mutableStateOf(TextFieldValue("5555")) }
    var inputIpConnect by remember { mutableStateOf(TextFieldValue("")) }
    var inputParingCode by remember { mutableStateOf(TextFieldValue("")) }

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
            BtnWithText(
                onClick = { model.onDisconnectAll() },
                icon = Icons.Rounded.CheckCircle,
                description = stringResource(Res.string.target_disconnect_all),
                width = Dimensions.targetCommandsBtnWidth,
            )
            BtnWithText(
                onClick = { model.onGetDeviceIp() },
                icon = Icons.Rounded.CheckCircle,
                description = stringResource(Res.string.target_get_device_ip),
                width = Dimensions.targetCommandsBtnWidth,
            )
        }
        Row(modifier = Modifier.padding(top = 6.dp)) {
            TextFieldX(
                modifier = Modifier.padding(6.dp),
                singleLine = true,
                value = inputPortTcpIp,
                maxLength = 5,
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
        Row(modifier = Modifier.padding(top = 6.dp)) {
            TextFieldX(
                modifier = Modifier.padding(6.dp),
                singleLine = true,
                value = inputIpConnect,
                padding = PaddingValues(top = 3.dp),
                keyboardType = KeyboardType.Uri,
                label = stringResource(Res.string.target_label_ip),
                onValueChange = { value -> inputIpConnect = value }
            )
            TextFieldX(
                modifier = Modifier.padding(6.dp),
                singleLine = true,
                value = inputPortConnect,
                padding = PaddingValues(top = 3.dp),
                maxLength = 5,
                keyboardType = KeyboardType.Number,
                label = stringResource(Res.string.target_label_port),
                onValueChange = { value -> inputPortConnect = value }
            )
            BtnWithText(
                onClick = { model.onAdbConnect(inputIpConnect.text, inputPortConnect.text) },
                icon = Icons.Rounded.CheckCircle,
                description = stringResource(Res.string.target_adb_connect),
                width = Dimensions.targetCommandsBtnWidth,
            )
            BtnWithText(
                onClick = { model.onAdbDisconnect(inputIpConnect.text, inputPortConnect.text) },
                icon = Icons.Rounded.CheckCircle,
                description = stringResource(Res.string.target_adb_disconnect),
                width = Dimensions.targetCommandsBtnWidth,
            )
            TextFieldX(
                modifier = Modifier.padding(6.dp),
                singleLine = true,
                value = inputParingCode,
                maxLength = 6,
                padding = PaddingValues(top = 3.dp),
                keyboardType = KeyboardType.Number,
                label = stringResource(Res.string.target_label_paring_code),
                onValueChange = { value -> inputParingCode = value }
            )
            BtnWithText(
                onClick = {
                    model.onAdbPair(
                        inputIpConnect.text,
                        inputPortConnect.text,
                        inputParingCode.text
                    )
                },
                icon = Icons.Rounded.CheckCircle,
                description = stringResource(Res.string.target_adb_pair),
                width = Dimensions.targetCommandsBtnWidth,
            )
        }
    }
}
