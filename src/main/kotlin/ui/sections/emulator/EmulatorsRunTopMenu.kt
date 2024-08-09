package ui.sections.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.EmulatorCommands
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.TextExample
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithTextSmall

@Composable
fun EmulatorsRunTopMenu(model: AppStore = koinInject()) {
    var proxyText by remember { mutableStateOf("") }
    val command = EmulatorCommands.getLaunchEmulator("emuName", proxyText)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row {
            ProxyTextField { proxyText = it }
            ProxyButtons { model.setProxy(proxyText) }
        }
        Text(
            text = command,
            fontSize = 12.sp,
            color = Color.LightGray,
        )
    }
}


@Composable
private fun ProxyTextField(model: AppStore = koinInject(), onProxyChange: (String) -> Unit = {}) {
    var proxyTextField by remember { mutableStateOf(TextFieldValue("")) }

    Column {
        TextExample("ex: server:port\nusername:password@server:port")
        TextFieldX(
            value = proxyTextField,
            onValueChange = { newText ->
                proxyTextField = newText
                model.onProxyChange(newText.text)
                onProxyChange(newText.text)
            },
            label = "Proxy (used on launch)",
            modifier = Modifier.background(MyColors.bg).widthIn(min = 170.dp),
        )
    }
}

@Composable
private fun ProxyButtons(model: AppStore = koinInject(), onClickSetProxy: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        TextExample("Proxy control after emulator launched", TextAlign.Center, Modifier.width(150.dp))
        Row(modifier = Modifier.padding(top = 6.dp)) {
            BtnWithTextSmall(
                icon = Icons.AutoMirrored.Rounded.Send,
                onClick = { onClickSetProxy() },
                description = "Set",
            )
            BtnWithTextSmall(
                icon = Icons.Rounded.Delete,
                onClick = { model.removeProxy() },
                description = "Clear",
            )
            BtnWithTextSmall(
                icon = Icons.Rounded.Check,
                onClick = { model.getProxy() },
                description = "Get",
            )
        }
    }
}
