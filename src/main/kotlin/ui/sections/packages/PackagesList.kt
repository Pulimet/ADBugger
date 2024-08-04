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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Package
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.SearchView

@Composable
fun PackagesList(
    packageList: List<Package> = emptyList(),
    modifier: Modifier = Modifier,
    addToFavoritesEnabled: Boolean = true,
    removeFromFavoritesEnabled: Boolean = false,
    forceShowSearchView: Boolean = false,
    model: AppStore = koinInject()
) {

    val listState = rememberLazyListState()
    var textState by remember { mutableStateOf("") }

    val filteredList by remember(packageList) {
        derivedStateOf {
            packageList.filter { it.name.contains(textState, ignoreCase = true) }
        }
    }

    if (packageList.isNotEmpty() || forceShowSearchView) {
        SearchView(modifier = modifier.fillMaxWidth()) {
            textState = it
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, color = Color.DarkGray))
    ) {
        LazyColumn(state = listState) {
            items(filteredList) { item ->
                val isSelected = model.state.selectedPackage == item.name
                PackageItem(
                    item,
                    isSelected,
                    { model.onPackageClick(it) },
                    addToFavoritesEnabled,
                    removeFromFavoritesEnabled,
                    Modifier.fillMaxWidth(),
                    { model.addPackageNameToFavorites(it.name) },
                    { model.removePackageNamFromFavorites(it.name) },
                )
            }
        }
        if (filteredList.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(), adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}
