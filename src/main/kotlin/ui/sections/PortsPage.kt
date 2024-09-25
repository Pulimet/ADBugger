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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.LineaIcons
import compose.icons.lineaicons.Basic
import compose.icons.lineaicons.basic.Home
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
    var textInputCustomPortState by remember { mutableStateOf(TextFieldValue("")) }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextFieldX(
                    modifier = Modifier.padding(6.dp).weight(1f),
                    singleLine = true,
                    paddingTop = 0.dp,
                    value = textInputCustomPortState,
                    label = "Custom port",
                    onValueChange = { value -> textInputCustomPortState = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputCustomPortState.text.isNotEmpty(),
                    onClick = { model.onAdbReverse(textInputCustomPortState.text.toIntOrNull()) },
                    description = "Open specified port"
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverse(8081) },
                    description = "8081",
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverse(9090) },
                    description = "9090",
                )
                BtnWithText(
                    icon = LineaIcons.Basic.Home,
                    onClick = { model.onAdbReverseList() },
                    description = "List",
                )
            }
        }
    }
}
