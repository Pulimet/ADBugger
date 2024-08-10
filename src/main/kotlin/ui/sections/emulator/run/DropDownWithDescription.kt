package ui.sections.emulator.run

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
    onOptionSelected: (String) -> Unit,
    title: String,
    description: String,
    optionsDetails: List<String>,
    modifier: Modifier = Modifier,
    minWidth: Dp = 80.dp,
) {
    Column(modifier = modifier) {
        TextExample(description, TextAlign.Center)
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