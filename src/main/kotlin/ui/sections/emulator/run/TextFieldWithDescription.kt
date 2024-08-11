package ui.sections.emulator.run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.MyColors
import ui.widgets.TextExample
import ui.widgets.TextFieldX

@Composable
fun TextFieldWithDescription(
    label: String = "",
    description: String = "",
    showDescription: Boolean = true,
    minWidth: Dp = 100.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = -1,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit = {}
) {
    var textField by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = modifier) {
        if (showDescription) {
            TextExample(description, TextAlign.Center, Modifier.widthIn(min = minWidth))
        }
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