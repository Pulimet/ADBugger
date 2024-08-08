package ui.sections.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.EmulatorCommands
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText

@Composable
fun EmulatorsTopMenu(model: AppStore = koinInject()) {
    var proxyTextField by remember { mutableStateOf(TextFieldValue("")) }
    val command = EmulatorCommands.getLaunchEmulator("emuName", proxyTextField.text)

    Column(
        modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(vertical = 8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            // TODO Add explanation in UI
            // Proxy - Makes all TCP connections through a specified HTTP/HTTPS proxy.
            // http://server:port or http://username:password@server:port  (The http:// prefix can be omitted.)
            TextFieldX(
                value = proxyTextField,
                onValueChange = { newText ->
                    proxyTextField = newText
                    model.onProxyChange(newText.text)
                },
                label = "Proxy (ex: server:port)",
                modifier = Modifier.background(MyColors.bg).width(250.dp),
            )
            BtnWithText(
                icon = Icons.Rounded.Refresh,
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = { model.getEmulatorsListClick() },
                description = "Refresh",
            )
            BtnWithText(
                icon = FontAwesomeIcons.Solid.BookDead,
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = { model.onKillAllEmulatorClick() },
                description = "Kill All",
            )
        }

        Text(
            text = command,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )
    }
}
