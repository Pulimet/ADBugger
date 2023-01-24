package ui.sections

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.malinskiy.adam.request.pkg.Package
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.widgets.LoadingSpinner
import ui.widgets.SearchView
import ui.widgets.SectionTitle

@Composable
fun PackageListSection(
    coroutineScope: CoroutineScope,
    model: AppStore,
    state: AppStore.AppState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Content(state, model, coroutineScope)
        SectionTitle("Package selection")
        if (state.isPackagesLoading) {
            LoadingSpinner()
        }
    }
}

@Composable
private fun Content(
    state: AppStore.AppState,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp, 26.dp, 16.dp, 16.dp)
            .border(1.dp, Color.LightGray, RectangleShape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AllOptionAndRefreshButton(state, model, coroutineScope)
        PackageList(state) { model.onPackageClick(it) }
    }
}

@Composable
private fun AllOptionAndRefreshButton(
    state: AppStore.AppState,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = Color.LightGray)
    ) {
        val nonePackage = Package(AppStore.NONE)
        PackageItem(
            nonePackage,
            state.selectedPackage == AppStore.NONE,
            { model.onPackageClick(nonePackage) },
        )
        Button(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.onGetPackageListClick(coroutineScope) })
        { Text(text = "Refresh") }
    }
}

@Composable
private fun PackageList(state: AppStore.AppState, onClicked: (pckg: Package) -> Unit) {
    val listState = rememberLazyListState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val query = textState.value.text
    SearchView(state = textState, modifier = Modifier.fillMaxWidth())

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = listState) {
            items(
                state.packageList.filter { it.name.contains(query, ignoreCase = true) },
                key = { device -> device.name }
            ) { item ->
                PackageItem(item, state.selectedPackage == item.name, { onClicked(it) }, Modifier.fillMaxWidth())
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = listState
            )
        )
    }
}

@Composable
private fun PackageItem(
    item: Package,
    isSelected: Boolean,
    onClicked: (device: Package) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(item) })
    ) {
        RadioButton(selected = isSelected, { onClicked(item) })
        Text(item.name)
    }
}