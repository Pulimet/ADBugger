package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard


@Composable
fun SendTextToDevices(
    textInputSendTextState: MutableState<TextFieldValue>,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    ExpandableCard(
        title = "Send text to device",
        modifier = Modifier.padding(
            horizontal = 12.dp, vertical = 4.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            TextField(
                modifier = Modifier.padding(8.dp).weight(1f),
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                value = textInputSendTextState.value,
                label = { Text("Send text to device/s") },
                onValueChange = { value -> textInputSendTextState.value = value }
            )

            BtnIcon(
                icon = Icons.Rounded.Send,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = textInputSendTextState.value.text.isNotEmpty(),
                onClick = { model.onSendTextClick(coroutineScope, textInputSendTextState.value.text) },
                description = "Send text to device"
            )

            BtnIcon(
                icon = Icons.Rounded.ArrowBack,
                modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                enabled = textInputSendTextState.value.text.isNotEmpty(),
                onClick = {
                    model.onBackSpaceClick(coroutineScope)
                    val text = textInputSendTextState.value.text
                    val textLength = text.length
                    if (textLength > 0) {
                        textInputSendTextState.value = TextFieldValue(text.substring(0, textLength - 1))
                    }
                },
                description = "Backspace"
            )
        }
    }
}
