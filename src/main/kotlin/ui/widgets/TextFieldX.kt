package ui.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldX(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.OutlinedTextFieldShape,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    BasicTextField(
        modifier = if (label.isNotEmpty()) {
            modifier
                .semantics(mergeDescendants = true) {}
                .padding(top = 8.dp)
        } else {
            modifier
        },
        value = value,
        textStyle = TextStyle(color = Color.White),
        singleLine = singleLine,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(Color.White),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value.text,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = singleLine,
                enabled = enabled,
                label = {
                    TextFieldLabel(label)
                },
                interactionSource = interactionSource,
                colors = TextFieldDefaults.outlinedTextFieldColors(),
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 2.dp,
                    bottom = 6.dp
                ),
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape
                    )
                }
            )
        }
    )
}

@Composable
private fun TextFieldLabel(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            fontSize = 10.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
