package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.LineaIcons
import compose.icons.lineaicons.Basic
import compose.icons.lineaicons.basic.Home
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.ports_8081
import net.alexandroid.adbugger.adbugger.generated.resources.ports_9090
import net.alexandroid.adbugger.adbugger.generated.resources.ports_clear_all
import net.alexandroid.adbugger.adbugger.generated.resources.ports_forward_device
import net.alexandroid.adbugger.adbugger.generated.resources.ports_forward_machine
import net.alexandroid.adbugger.adbugger.generated.resources.ports_forward_port
import net.alexandroid.adbugger.adbugger.generated.resources.ports_list
import net.alexandroid.adbugger.adbugger.generated.resources.ports_revers_port
import net.alexandroid.adbugger.adbugger.generated.resources.ports_reverse_device
import net.alexandroid.adbugger.adbugger.generated.resources.ports_reverse_machine
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnIcon
import ui.widgets.buttons.BtnWithText


@Composable
fun PortsPage(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    var inputReverseFrom by remember { mutableStateOf(TextFieldValue("")) }
    var inputReverseTo by remember { mutableStateOf(TextFieldValue("")) }
    var inputForwardFrom by remember { mutableStateOf(TextFieldValue("")) }
    var inputForwardTo by remember { mutableStateOf(TextFieldValue("")) }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextFieldX(
                    modifier = Modifier.padding(6.dp).weight(0.5f),
                    singleLine = true,
                    value = inputReverseFrom,
                    keyboardType = KeyboardType.Number,
                    maxLength = 5,
                    label = stringResource(Res.string.ports_reverse_device),
                    onValueChange = { value -> inputReverseFrom = value }
                )
                TextFieldX(
                    modifier = Modifier.padding(6.dp).weight(0.5f),
                    singleLine = true,
                    value = inputReverseTo,
                    keyboardType = KeyboardType.Number,
                    maxLength = 5,
                    label = stringResource(Res.string.ports_reverse_machine),
                    onValueChange = { value -> inputReverseTo = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = inputReverseFrom.text.isNotEmpty() && inputReverseTo.text.isNotEmpty(),
                    onClick = {
                        model.onAdbReverse(
                            inputReverseFrom.text.toIntOrNull(),
                            inputReverseFrom.text.toIntOrNull()
                        )
                    },
                    description = stringResource(Res.string.ports_revers_port)
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverse(8081, 8081) },
                    description = stringResource(Res.string.ports_8081),
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverse(9090, 9090) },
                    description = stringResource(Res.string.ports_9090),
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverseList() },
                    description = stringResource(Res.string.ports_list),
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverseClear() },
                    description = stringResource(Res.string.ports_clear_all),
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextFieldX(
                    modifier = Modifier.padding(6.dp).weight(0.5f),
                    singleLine = true,
                    value = inputForwardFrom,
                    maxLength = 5,
                    keyboardType = KeyboardType.Number,
                    label = stringResource(Res.string.ports_forward_machine),
                    onValueChange = { value -> inputForwardFrom = value }
                )
                TextFieldX(
                    modifier = Modifier.padding(6.dp).weight(0.5f),
                    singleLine = true,
                    value = inputForwardTo,
                    maxLength = 5,
                    keyboardType = KeyboardType.Number,
                    label = stringResource(Res.string.ports_forward_device),
                    onValueChange = { value -> inputForwardTo = value }
                )
                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = inputForwardFrom.text.isNotEmpty() && inputForwardTo.text.isNotEmpty(),
                    onClick = {
                        model.onAdbForward(
                            inputForwardFrom.text.toIntOrNull(),
                            inputForwardTo.text.toIntOrNull()
                        )
                    },
                    description = stringResource(Res.string.ports_forward_port)
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbForwardList() },
                    description = stringResource(Res.string.ports_list),
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbForwardClear() },
                    description = stringResource(Res.string.ports_clear_all),
                )
            }

        }
    }
}
