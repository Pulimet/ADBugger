package ui.widgets.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Tabs(
    tabLabels: List<String>,
    showTabs: Boolean = true,
    content: @Composable (Int) -> Unit = {},
) {
    var selectedIndex by remember { mutableStateOf(0) }
    Column {
        TabsLabels(tabLabels, selectedIndex, showTabs) { selectedIndex = it }
        Box(modifier = Modifier.fillMaxSize().padding(top = 4.dp)) {
            content(selectedIndex)
        }
    }
}

