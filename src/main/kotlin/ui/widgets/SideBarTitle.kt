package ui.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.Dimens

@Composable
fun SideBarTitle(title: String = "Specify title", isSelected: Boolean = false) {
    Text(
        title,
        modifier = Modifier.padding(start = 8.dp, bottom = 6.dp),
        fontSize = Dimens.sideMenuFontSize,
        textAlign = TextAlign.Start,
        color = if (isSelected) Color.White else Color.LightGray
    )
}
