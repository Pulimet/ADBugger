package ui.sections.logcat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.list.ListX

@Composable
fun LogcatPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    var searchQuery by remember { mutableStateOf("") }

    CardX(modifier = modifier) {
        val list = model.state.logcatLogs

        Row(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Column(modifier = Modifier.weight(0.8f)) {
                LogcatTopBar { searchQuery = it }
                BoxWithLogsList(list, searchQuery)
            }
            LogcatSideBar(list)
        }

    }
}

@Composable
private fun BoxWithLogsList(list: List<String>, searchQuery: String) {
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
