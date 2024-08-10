package ui.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TextExample(description: String, textAlign: TextAlign? = null, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = description,
        textAlign = textAlign,
        lineHeight = 10.sp,
        fontSize = 10.sp,
        color = Color.Gray,
    )
}
