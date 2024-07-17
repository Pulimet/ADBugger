package ui.sections.emulator

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore

@Composable
fun EmulatorsList(model: AppStore = koinInject()) {
    val listState = rememberLazyListState()
    val state = model.state
    Box(modifier = Modifier.fillMaxSize()) {
        val items = state.emulatorsList
        LazyColumn(state = listState) {
            items(
                items,
                key = { item -> item }
            ) { item ->
                EmulatorItem(item)
            }
        }
        if (items.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}

