package ui.sections.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_backspace
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_send_key
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_send_text
import net.alexandroid.adbugger.adbugger.generated.resources.input_label_send_key
import net.alexandroid.adbugger.adbugger.generated.resources.input_label_send_text
import net.alexandroid.adbugger.adbugger.generated.resources.keycodes_int
import net.alexandroid.adbugger.adbugger.generated.resources.keycodes_string
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.CardX
import ui.widgets.TextFieldAuto
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnIcon

@Composable
fun InputCustom(model: AppStore = koinInject()) {
    var textInputSendTextState by remember { mutableStateOf(TextFieldValue("")) }
    var textInputSendInputState by remember { mutableStateOf(TextFieldValue("")) }

    CardX {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextFieldX(
                    modifier = Modifier.padding(6.dp).weight(1f),
                    singleLine = true,
                    value = textInputSendTextState,
                    label = stringResource(Res.string.input_label_send_text),
                    onValueChange = { value -> textInputSendTextState = value }
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputSendTextState.text.isNotEmpty(),
                    onClick = { model.onSendTextClick(textInputSendTextState.text) },
                    description = stringResource(Res.string.input_btn_send_text)
                )

                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.ArrowBack,
                    modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                    enabled = textInputSendTextState.text.isNotEmpty(),
                    onClick = {
                        model.onBackSpaceClick()
                        val text = textInputSendTextState.text
                        val textLength = text.length
                        if (textLength > 0) {
                            textInputSendTextState =
                                TextFieldValue(text.substring(0, textLength - 1))
                        }
                    },
                    description = stringResource(Res.string.input_btn_backspace)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextFieldAuto(
                    modifier = Modifier.padding(6.dp).weight(1f),
                    value = textInputSendInputState,
                    label = stringResource(Res.string.input_label_send_key),
                    width = 500.dp,
                    onValueChange = { value -> textInputSendInputState = value },
                    suggestionsList = stringArrayResource(Res.array.keycodes_int),
                    suggestionsDescriptionList = stringArrayResource(Res.array.keycodes_string),
                    onSuggestionSelected = { value -> textInputSendInputState = value }
                )
                BtnIcon(
                    icon = Icons.AutoMirrored.Rounded.Send,
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    enabled = textInputSendInputState.text.isNotEmpty(),
                    onClick = { model.onSendInputClick(textInputSendInputState.text.toInt()) },
                    description = stringResource(Res.string.input_btn_send_key)
                )
            }
        }
    }
}
