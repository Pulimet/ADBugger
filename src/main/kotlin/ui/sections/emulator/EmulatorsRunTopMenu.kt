package ui.sections.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
fun EmulatorsRunTopMenu(
    proxyText: String,
    ramInt: Int,
    onProxyChange: (String) -> Unit,
    onRamChange: (Int) -> Unit,
    model: AppStore = koinInject()
) {
    val command = EmulatorCommands.getLaunchEmulator("emuName", proxyText, ramInt)

    Column(
        modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row {
            TextFieldWithExample(
                label = "Proxy (used on launch)",
                example = "ex: server:port\nusername:password@server:port",
                minWidth = 170.dp,
            ) { onProxyChange(it) }

            ProxyButtons { model.setProxy(proxyText) }

            TextFieldWithExample(
                label = "RAM",
                example = "Set RAM on launch\n1536 - 8192 (MBs)",
                minWidth = 50.dp,
                maxLength = 4,
                keyboardType = KeyboardType.Number,
            ) { onRamChange(it.toIntOrNull() ?: 0) }
        }
        Text(
            text = command,
            fontSize = 12.sp,
            color = Color.LightGray,
        )
    }
}


@Composable
private fun TextFieldWithExample(
    label: String = "",
    example: String = "",
    minWidth: Dp = 100.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = -1,
    onChange: (String) -> Unit = {},
) {
    var textField by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        TextExample(example, textAlign = TextAlign.Center, modifier = Modifier.widthIn(min = minWidth))
        TextFieldX(
            value = textField,
            onValueChange = { newText ->
                textField = newText
                onChange(newText.text)

            },
            label = label,
            maxLength = maxLength,
            keyboardType = keyboardType,
            modifier = Modifier.background(MyColors.bg).widthIn(min = minWidth),
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
