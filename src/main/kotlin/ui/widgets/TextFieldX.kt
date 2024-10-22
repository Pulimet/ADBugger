package ui.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pref.preference

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldX(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 34.dp,
    padding: PaddingValues = PaddingValues(),
    label: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.OutlinedTextFieldShape,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = -1,
    saveValueKey: String = "",
) {

    var inputValuePref: String by preference(saveValueKey, "")
    LaunchedEffect(saveValueKey) {
        if (saveValueKey.isNotEmpty()) {
            onValueChange(TextFieldValue(inputValuePref))
        }
    }

    BasicTextField(
        modifier = if (label.isNotEmpty()) {
            modifier.semantics(mergeDescendants = true) {}
                .padding(padding)
                .height(height)
        } else {
            modifier.height(height)
        },
        maxLines = maxLines,
        value = value,
        textStyle = TextStyle(color = Color.White),
        singleLine = singleLine,
        onValueChange = {
            if (maxLength > -1 && it.text.length > maxLength) return@BasicTextField
            if (keyboardType == KeyboardType.Number && it.isContainsNonDigits()) return@BasicTextField
            onValueChange(it)
            if (saveValueKey.isNotEmpty()) {
                inputValuePref = it.text
            }
        },
        cursorBrush = SolidColor(Color.White),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value.text,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = singleLine,
                enabled = enabled,
                label = { TextFieldLabel(label) },
                interactionSource = interactionSource,
                colors = TextFieldDefaults.outlinedTextFieldColors(),
                contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 0.dp),
                border = {
                    TextFieldDefaults.BorderBox(enabled, isError, interactionSource, colors, shape)
                }
            )
        }
    )
}

private fun TextFieldValue.isContainsNonDigits() = this.text.contains(Regex("\\D"))

@Composable
private fun TextFieldLabel(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            color = Color.LightGray,
        )
    }
}
