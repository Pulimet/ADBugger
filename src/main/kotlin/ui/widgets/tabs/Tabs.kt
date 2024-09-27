package ui.widgets.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pref.preference

@Composable
fun Tabs(
    tabLabels: List<String>,
    saveSelectedTabKey: String = "",
    showTabs: Boolean = true,
    content: @Composable (Int) -> Unit = {},
) {
    var selectedIndex by remember { mutableStateOf(0) }

    var selectedTabPref: Int by preference(saveSelectedTabKey, 0)
    LaunchedEffect(saveSelectedTabKey) {
        if (saveSelectedTabKey.isNotEmpty()) {
            selectedIndex = selectedTabPref
        }
    }

    Column {
        TabsLabels(tabLabels, selectedIndex, showTabs) {
            selectedIndex = it
            if (saveSelectedTabKey.isNotEmpty()) {
                selectedTabPref = it
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(top = 4.dp)) {
            content(selectedIndex)
        }
    }
}

