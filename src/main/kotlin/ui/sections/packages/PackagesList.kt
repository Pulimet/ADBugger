package ui.sections.packages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import model.Package
import store.AppStore
import ui.widgets.SearchView

@Composable
fun PackagesList(state: AppStore.AppState, onClicked: (pckg: Package) -> Unit) {
    val listState = rememberLazyListState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val query = textState.value.text

    if (state.packageList.isNotEmpty()) {
        SearchView(state = textState, modifier = Modifier.fillMaxWidth())
    }

    Box(
        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, color = Color.DarkGray))
    ) {
        val items = state.packageList.filter { it.name.contains(query, ignoreCase = true) }
        LazyColumn(state = listState) {
            items(items, key = { device -> device.name }) { item ->
                PackageItem(item, state.selectedPackage == item.name, { onClicked(it) }, Modifier.fillMaxWidth())
            }
        }
        if (items.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(), adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}
