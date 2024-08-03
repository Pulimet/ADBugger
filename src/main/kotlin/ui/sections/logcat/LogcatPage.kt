package ui.sections.logcat

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
            LogcatSideBar(list)
        }

    }
}
