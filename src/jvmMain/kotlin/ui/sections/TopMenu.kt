package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.tablericons.ArrowsRightLeft
import compose.icons.tablericons.LetterA
import compose.icons.tablericons.Notes
import compose.icons.tablericons.SettingsAutomation
import pref.preference
import store.AppStore
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun TopMenu(model: AppStore) {
    var devicesControlState: Boolean by preference("Btn_DevicesControl", false)
    var keysState: Boolean by preference("Btn_KeyboardInput", false)
    var forwardUserInputState: Boolean by preference("Btn_ForwardUserInput", false)
    var portsState: Boolean by preference("Btn_PortForwarding", false)
    var logsState: Boolean by preference("Btn_Logs", false)

    LaunchedEffect(Unit) {
        model.onKeyboardInputToggle(keysState)
        model.onPortForwardingToggle(portsState)
        model.onLogsToggle(logsState)
        model.onDevicesControlToggle(devicesControlState)
        model.onForwardUserInputToggle(forwardUserInputState)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
    ) {
        BtnIcon(
            icon = TablerIcons.SettingsAutomation,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {
                devicesControlState = !devicesControlState
                model.onDevicesControlToggle(devicesControlState)
            },
            description = "Devices Control",
            toggle = devicesControlState,
            buttonSize = 26.dp,
            iconSize = 16.dp
        )
        BtnIcon(
            icon = FontAwesomeIcons.Regular.Keyboard,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {
                keysState = !keysState
                model.onKeyboardInputToggle(keysState)
            },
            description = "Keyboard Input",
            toggle = keysState,
            buttonSize = 26.dp,
            iconSize = 16.dp
        )
        BtnIcon(
            icon = TablerIcons.LetterA,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {
                forwardUserInputState = !forwardUserInputState
                model.onForwardUserInputToggle(forwardUserInputState)
            },
            description = "Forward User Input",
            toggle = forwardUserInputState,
            buttonSize = 26.dp,
            iconSize = 16.dp
        )
        BtnIcon(
            icon = TablerIcons.ArrowsRightLeft,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {
                portsState = !portsState
                model.onPortForwardingToggle(portsState)
            },
            description = "Port Forwarding",
            toggle = portsState,
            buttonSize = 26.dp,
            iconSize = 16.dp
        )
        BtnIcon(
            icon = TablerIcons.Notes,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {
                logsState = !logsState
                model.onLogsToggle(logsState)
            },
            description = "Logs",
            toggle = logsState,
            buttonSize = 26.dp,
            iconSize = 16.dp
        )
    }
}
