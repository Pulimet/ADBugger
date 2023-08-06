package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import ui.theme.MyColors


@Composable
fun SendTextToDevices(
    textInputSendTextState: MutableState<TextFieldValue>,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp).background(MyColors.bg2)
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
        Button(onClick = { model.onSendTextClick(coroutineScope, textInputSendTextState.value.text) }) {
            Text(text = "Send")
        }
        Button(
            onClick = {
                model.onBackSpaceClick(coroutineScope)
                val text = textInputSendTextState.value.text
                val textLength = text.length
                if (textLength > 0) {
                    textInputSendTextState.value = TextFieldValue(text.substring(0, textLength - 1))
                }
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "←")
        }
    }
}
