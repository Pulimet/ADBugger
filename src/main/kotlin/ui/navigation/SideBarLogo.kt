package ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun SideBarLogo() {
    Row(modifier = Modifier.width(Dimensions.sideBarWidth), horizontalArrangement = Arrangement.Center) {
        Text(
            "ADB",
            fontSize = 24.sp,
            color = MyColors.accent,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            "ugger",
            fontSize = 24.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.ExtraBold
        )

    }
}
