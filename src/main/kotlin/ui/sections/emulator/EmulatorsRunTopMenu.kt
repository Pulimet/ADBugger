package ui.sections.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.EmulatorCommands
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.TextFieldX

@Composable
fun EmulatorsRunTopMenu() {
    var proxyText by remember { mutableStateOf("") }
    val command = EmulatorCommands.getLaunchEmulator("emuName", proxyText)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        ProxyTextField { proxyText = it }
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
        TextFieldX(
            value = proxyTextField,
            onValueChange = { newText ->
                proxyTextField = newText
                model.onProxyChange(newText.text)
                onProxyChange(newText.text)
            },
            label = "Proxy (used on launch)",
            modifier = Modifier.background(MyColors.bg).width(250.dp),
        )
        TextFieldExample("ex: server:port/username:password@server:port")
    }
}

@Composable
private fun TextFieldExample(title: String) {
    Text(
        text = title,
        lineHeight = 10.sp,
        fontSize = 10.sp,
        color = Color.Gray,
    )
}


