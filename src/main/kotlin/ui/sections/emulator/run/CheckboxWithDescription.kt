package ui.sections.emulator.run

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.widgets.TextExample


@Composable
fun CheckboxWithDescription(
    title: String,
    description: String,
    minWidth: Dp = 100.dp,
    modifier: Modifier = Modifier,
    defaultState: Boolean = true,
    onCheckChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(defaultState) }
    Column(modifier = modifier.widthIn(min = minWidth)) {
        TextExample(description, TextAlign.Center, modifier.widthIn(min = minWidth))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheckChange(it)
                }
            )
            Text(
                text = title,
                lineHeight = 12.sp,
                fontSize = 12.sp,
                color = Color.LightGray,
            )
        }
    }
}