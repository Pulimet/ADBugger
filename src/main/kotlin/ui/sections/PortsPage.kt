package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon
import ui.widgets.HoverButton


@Composable
fun PortsPage(
    model: AppStore,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val textInputCustomPortState = remember { mutableStateOf(TextFieldValue("")) }

    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
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
                    value = textInputCustomPortState.value,
                    label = { Text("Custom port") },
                    onValueChange = { value -> textInputCustomPortState.value = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputCustomPortState.value.text.isNotEmpty(),
                    onClick = { model.onAdbReverse(coroutineScope, textInputCustomPortState.value.text.toIntOrNull()) },
                    description = "Open specified port"
                )
                HoverButton(
                    onClick = { model.onAdbReverse(coroutineScope, 8081) },
                    enabled = true,
                    text = "8081",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                HoverButton(
                    onClick = { model.onAdbReverse(coroutineScope, 9090) },
                    enabled = true,
                    text = "9090",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                HoverButton(
                    onClick = { model.onAdbReverseList(coroutineScope) },
                    enabled = true,
                    text = "List",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}
