package ui.widgets

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import ui.theme.MyColors

@Composable
fun TextFieldAuto(
    modifier: Modifier,
    value: TextFieldValue,
    label: String,
    onValueChange: (TextFieldValue) -> Unit,
    suggestionsList: List<String> = listOf(),
    onSuggestionSelected: (TextFieldValue) -> Unit = {},
    width: Dp = 360.dp
) {
    val focusRequester = remember { FocusRequester() }
    var expanded by remember { mutableStateOf(false) }

    val filteredSuggestions = remember(value.text) { // Recalculate when value changes
        suggestionsList.filter { suggestion ->
            suggestion.contains(value.text, ignoreCase = true)
        }
    }

    val stateVertical = rememberScrollState(0)

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier, contentAlignment = Alignment.TopStart) {
        Box {
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
            if (filteredSuggestions.isNotEmpty()) {
                DropdownMenu(
                    expanded = expanded,
                    scrollState = stateVertical,
                    modifier = Modifier.background(MyColors.bg).width(width).heightIn(max = 200.dp),
                    properties = PopupProperties(focusable = false),
                    onDismissRequest = { expanded = false })
                {
                    DropdownMenuContent(filteredSuggestions) {
                        expanded = false
                        onSuggestionSelected(it)
                    }
                }
            }
        }
        PopupScrollbar(expanded, filteredSuggestions, stateVertical, width)
    }
}

@Composable
fun DropdownMenuContent(
    filteredSuggestions: List<String>,
    onSuggestionSelected: (TextFieldValue) -> Unit = {},
) {
    filteredSuggestions.forEach { selectionOption ->
        DropdownMenuItem(onClick = {
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

@Composable
private fun BoxScope.PopupScrollbar(
    expanded: Boolean,
    filteredSuggestions: List<String>,
    stateVertical: ScrollState,
    width: Dp
) {
    if (expanded && filteredSuggestions.size > 4) {
        val offset = with(LocalDensity.current) {
            IntOffset(width.roundToPx(), 36.dp.roundToPx())
        }

        Popup(offset = offset) {
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .height(200.dp)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState = stateVertical)
            )
        }
    }
}