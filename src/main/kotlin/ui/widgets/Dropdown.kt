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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.MyColors

@Composable
fun Dropdown(
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    title: String = "",
    optionsDetails: List<String> = emptyList(),
    minWidth: Dp = 80.dp,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    titleBgColor: Color = MyColors.bg,
) {
    if (options.isEmpty()) return

    Surface(modifier = modifier) {
        DropdownContent(options, optionsDetails, onOptionSelected, minWidth, contentModifier)
        DropdownTitle(title, titleBgColor)
    }
}


@Composable
private fun DropdownContent(
    options: List<String>,
    optionsDetails: List<String>,
    onOptionSelected: (String) -> Unit,
    minWidth: Dp = 80.dp,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options[0]) }
    val isOptionDetailsEnabled = options.size == optionsDetails.size

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .padding(6.dp)
            .widthIn(minWidth)
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(4.dp))
            .clickable { expanded = !expanded },
    ) {
        DropdownSelected(selectedText, iconModifier = Modifier.align(Alignment.CenterEnd))
        DropdownMenu(expanded = expanded,
            modifier = Modifier.background(MyColors.bg),
            onDismissRequest = { expanded = false }) {
            options.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(onClick = {
                    selectedText = selectionOption
                    expanded = false
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
private fun DropdownTitle(title: String, bgColor: Color) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, bottom = 0.dp).background(bgColor)
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
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                ),
            )
        }
    }
}
