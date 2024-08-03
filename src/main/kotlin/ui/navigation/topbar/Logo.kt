package ui.navigation.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun Logo() {
    Row(modifier = Modifier.width(Dimensions.sideBarWidth), horizontalArrangement = Arrangement.Center) {
        Text(
            "ADB",
            fontSize = Dimensions.logoFontSize,
            color = MyColors.accent,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            "ugger",
            fontSize = Dimensions.logoFontSize,
            color = Color.LightGray,
            fontWeight = FontWeight.ExtraBold
        )

    }
}
