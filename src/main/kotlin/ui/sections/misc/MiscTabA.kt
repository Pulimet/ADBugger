package ui.sections.misc

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import ui.theme.Dimensions

@Composable
fun MiscTabA() {
    Text(
        text = "Misc tab 1",
        fontSize = Dimensions.titleFontSize,
        textAlign = TextAlign.Center,
        color = Color.LightGray,
    )
}