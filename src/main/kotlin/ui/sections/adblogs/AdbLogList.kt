package ui.sections.adblogs

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import java.awt.datatransfer.Clipboard

@Composable
fun AdbLogList(clipboard: Clipboard, model: AppStore = koinInject()) {
    val logsList = model.state.logs
    val listState = rememberLazyListState()

    LaunchedEffect(logsList) {
        if (logsList.size > 2) {
            listState.scrollToItem(logsList.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(state = listState) {
            items(logsList) { item ->
                AdbLogItem(item, clipboard)
            }
        }
        if (logsList.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}
