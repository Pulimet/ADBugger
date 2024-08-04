package ui.widgets.list

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.awt.Toolkit

@Composable
fun ListX(list: List<String>, filter: String? = null) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val listState = rememberLazyListState()

    val filteredList by remember(list) {
        derivedStateOf {
            list.filter { filter == null || it.contains(filter, ignoreCase = true) }
        }
    }

    LaunchedEffect(list) {
        if (filteredList.size > 2) {
            listState.scrollToItem(filteredList.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(state = listState) {
            items(filteredList) { item ->
                ItemX(item, clipboard)
            }
        }
        if (filteredList.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}
