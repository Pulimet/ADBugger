package ui.sections.logcat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.theme.Dimensions
import ui.widgets.list.ListX

@Composable
fun BoxWithLogsList(list: List<String>, searchQuery: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (list.isEmpty()) {
            Text(
                text = "No logs yet.",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = Dimensions.titleFontSize
            )
        }

        ListX(list, searchQuery)
    }
}