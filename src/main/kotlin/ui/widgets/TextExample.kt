package ui.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TextExample(example: String) {
    Text(
        text = example,
        lineHeight = 10.sp,
        fontSize = 10.sp,
        color = Color.Gray,
    )
}
