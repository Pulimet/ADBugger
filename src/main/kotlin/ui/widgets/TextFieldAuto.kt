package ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import ui.theme.MyColors

@Composable
fun TextFieldAuto(
    modifier: Modifier,
    value: TextFieldValue,
    label: String,
    onValueChange: (TextFieldValue) -> Unit,
    suggestionsList: List<String> = listOf("Abc", "Def", "Ghi"),
    onSuggestionSelected: (TextFieldValue) -> Unit = {},
    width: Dp = 400.dp
) {
    val focusRequester = remember { FocusRequester() }
    var expanded by remember { mutableStateOf(false) }

    val filteredSuggestions = remember(value.text) { // Recalculate when value changes
        suggestionsList.filter { suggestion ->
            suggestion.contains(value.text, ignoreCase = true)
        }
    }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier) {
        TextFieldX(
            modifier = Modifier.width(width).focusRequester(focusRequester),
            value = value,
            label = label,
            onValueChange = {
                if (!expanded && it != value) {
                    expanded = true
                }
                onValueChange(it)
            }
        )
        DropdownMenu(
            expanded = expanded,
            modifier = Modifier.background(MyColors.bg).width(width),
            properties = PopupProperties(focusable = false),
            onDismissRequest = { expanded = false }) {
            filteredSuggestions.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSuggestionSelected(
                        TextFieldValue(
                            selectionOption,
                            selection = TextRange(selectionOption.length)
                        )
                    )
                }) {
                    SuggestionItemContent(selectionOption)
                }
            }
        }
    }
}

@Composable
private fun SuggestionItemContent(option: String) {
    Text(
        text = option,
        color = Color.LightGray,
        style = LocalTextStyle.current.copy(
            fontSize = 13.sp, lineHeight = 0.sp, lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.Both
            )
        )
    )
}