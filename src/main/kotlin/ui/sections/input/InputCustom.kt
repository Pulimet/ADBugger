package ui.sections.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun InputCustom(
    coroutineScope: CoroutineScope,
    model: AppStore = koinInject()
) {
    val textInputSendTextState = remember { mutableStateOf(TextFieldValue("")) }
    val textInputSendInputState = remember { mutableStateOf(TextFieldValue("")) }
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = Dimensions.selectedPagePadding, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
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
                    value = textInputSendTextState.value,
                    label = { Text("Send text to device/s") },
                    onValueChange = { value -> textInputSendTextState.value = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputSendTextState.value.text.isNotEmpty(),
                    onClick = { model.onSendTextClick(coroutineScope, textInputSendTextState.value.text) },
                    description = "Send text to device"
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.ArrowBack,
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
                    value = textInputSendInputState.value,
                    label = { Text("Send input/key to device/s") },
                    onValueChange = { value -> textInputSendInputState.value = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    enabled = textInputSendInputState.value.text.isNotEmpty(),
                    onClick = { model.onSendInputClick(coroutineScope, textInputSendInputState.value.text.toInt()) },
                    description = "Send input/key to device"
                )
            }
        }
    }
}
