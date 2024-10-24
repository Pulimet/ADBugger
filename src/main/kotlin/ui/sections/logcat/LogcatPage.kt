package ui.sections.logcat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX

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


