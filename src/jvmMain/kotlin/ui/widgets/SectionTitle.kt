package ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}