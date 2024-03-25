package ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SideBarLogo() {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp), horizontalArrangement = Arrangement.Center) {
        Text(
            "ADB",
            fontSize = 24.sp,
            color = Color.White,
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
