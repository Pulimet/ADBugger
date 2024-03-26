package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import ui.theme.Dimens
import ui.theme.MyColors
import ui.theme.bounceClick
import ui.widgets.BtnIcon


@Composable
fun PortForwarding(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    val textInputCustomPortState = remember { mutableStateOf(TextFieldValue("")) }

    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(Dimens.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPadding)) {
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
                    description = "Send text to device"
                )
                Button(
                    onClick = { model.onAdbReverse(coroutineScope, 8081) },
                    modifier = Modifier.padding(horizontal = 4.dp).bounceClick()
                ) { Text(text = "8081") }
                Button(
                    onClick = { model.onAdbReverse(coroutineScope, 9090) },
                    modifier = Modifier.padding(horizontal = 4.dp).bounceClick()
                ) { Text(text = "9090") }
                Button(
                    onClick = { model.onAdbReverseList(coroutineScope) },
                    modifier = Modifier.padding(horizontal = 4.dp).bounceClick()
                ) { Text(text = "List") }
            }
        }
    }
}
