package ui.sections.target

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun TargetList(
    model: AppStore = koinInject()
) {
    val state = model.state
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(state.targetsList, key = { device -> device.serial }) { item ->
                val isSelected = state.selectedTargetsList.contains(item.serial)
                TargetItem(
                    item,
                    isSelected,
                    { itemClicked, isSelectedNew -> model.onTargetClick(itemClicked, isSelectedNew) },
                    Modifier.fillMaxWidth(),
                )
            }
        }
        if (state.targetsList.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}
