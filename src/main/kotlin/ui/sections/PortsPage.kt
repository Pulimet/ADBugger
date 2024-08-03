package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.buttons.BtnIcon
import ui.widgets.buttons.HoverButton


@Composable
fun PortsPage(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    var textInputCustomPortState by remember { mutableStateOf(TextFieldValue("")) }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextField(
                    modifier = Modifier.padding(6.dp).weight(1f),
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White),
                    value = textInputCustomPortState,
                    label = { Text("Custom port") },
                    onValueChange = { value -> textInputCustomPortState = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputCustomPortState.text.isNotEmpty(),
                    onClick = { model.onAdbReverse(textInputCustomPortState.text.toIntOrNull()) },
                    description = "Open specified port"
                )
                HoverButton(
                    onClick = { model.onAdbReverse(8081) },
                    enabled = true,
                    text = "8081",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                HoverButton(
                    onClick = { model.onAdbReverse(9090) },
                    enabled = true,
                    text = "9090",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                HoverButton(
                    onClick = { model.onAdbReverseList() },
                    enabled = true,
                    text = "List",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}
