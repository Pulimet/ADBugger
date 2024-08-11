package ui.sections.emulator.run

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.widgets.Dropdown
import ui.widgets.TextExample

@Composable
fun DropDownWithDescription(
    options: List<String>,
    title: String,
    description: String,
    showDescription: Boolean = true,
    optionsDetails: List<String>,
    modifier: Modifier = Modifier,
    minWidth: Dp = 80.dp,
    onOptionSelected: (String) -> Unit,
) {
    Column(modifier = modifier) {
        if (showDescription) {
            TextExample(description, TextAlign.Center, modifier = Modifier.widthIn(min = minWidth))
        }
        Dropdown(
            options = options,
            optionsDetails = optionsDetails,
            title = title,
            onOptionSelected = onOptionSelected,
            minWidth = minWidth,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}