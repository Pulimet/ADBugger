package ui.widgets.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun Tabs(
    tabLabels: List<String>,
    content: @Composable (Int) -> Unit = {},
) {
    var selectedIndex by remember { mutableStateOf(0) }
    Column {
        TabsLabels(tabLabels, selectedIndex) { selectedIndex = it }
        Box(modifier = Modifier.fillMaxSize()) {
            content(selectedIndex)
        }
    }
}

