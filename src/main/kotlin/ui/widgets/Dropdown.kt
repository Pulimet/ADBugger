package ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.MyColors

@Composable
fun Dropdown(
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    title: String = "",
    optionsDetails: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    if (options.isEmpty()) return

    Surface(modifier = modifier) {
        DropdownContent(options, optionsDetails, onOptionSelected)
        DropdownTitle(title)
    }
}


@Composable
private fun DropdownContent(
    options: List<String>,
    optionsDetails: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedText = remember { mutableStateOf(options[0]) }
    val isOptionDetailsEnabled = options.size == optionsDetails.size

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.background(MyColors.bg).padding(16.dp).widthIn(80.dp).clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Color.DarkGray), RoundedCornerShape(4.dp))
            .clickable { expanded.value = !expanded.value },
    ) {
        DropdownSelected(selectedText.value, iconModifier = Modifier.align(Alignment.CenterEnd))
        DropdownMenu(expanded = expanded.value,
            modifier = Modifier.background(MyColors.bg),
            onDismissRequest = { expanded.value = false }) {
            options.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(onClick = {
                    selectedText.value = selectionOption
                    expanded.value = false
                    onOptionSelected(selectionOption)
                }) {
                    DropdownMenuItemContent(
                        selectionOption,
                        if (isOptionDetailsEnabled) optionsDetails[index] else ""
                    )
                }
            }
        }
    }
}

@Composable
private fun DropdownTitle(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 28.dp, bottom = 28.dp).background(MyColors.bg)
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun DropdownSelected(selectedText: String, iconModifier: Modifier = Modifier) {
    Text(
        text = selectedText,
        fontSize = 13.sp,
        color = Color.LightGray,
        modifier = Modifier.padding(10.dp, 2.dp, 24.dp, 8.dp)
    )
    Icon(
        Icons.Filled.ArrowDropDown, "dropdown", modifier = iconModifier, tint = Color.White
    )
}

@Composable
private fun DropdownMenuItemContent(option: String, optionDescription: String) {
    Column {
        Text(
            text = option,
            color = Color.LightGray,
            style = LocalTextStyle.current.copy(
                fontSize = 13.sp, lineHeight = 0.sp, lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.Both
                )
            ),
        )
        if (optionDescription.isNotEmpty()) {
            Text(
                text = optionDescription,
                color = Color.Gray,
                style = LocalTextStyle.current.copy(
                    fontSize = 10.sp, lineHeight = 0.sp, lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.Both
                    )
                ),
            )
        }
    }
}
