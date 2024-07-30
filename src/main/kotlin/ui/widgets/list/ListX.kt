package ui.widgets.list

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
import java.awt.Toolkit

@Composable
fun ListX(list: List<String>) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val listState = rememberLazyListState()

    LaunchedEffect(list) {
        if (list.size > 2) {
            listState.scrollToItem(list.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(state = listState) {
            items(list) { item ->
                ItemX(item, clipboard)
            }
        }
        if (list.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}
