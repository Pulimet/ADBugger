package ui.sections

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malinskiy.adam.request.pkg.Package
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.MyColors
import ui.theme.Dimensions
import ui.widgets.BtnIcon
import ui.widgets.LoadingSpinner
import ui.widgets.SearchView

@Composable
fun PackageListSection(
    coroutineScope: CoroutineScope,
    model: AppStore,
    modifier: Modifier = Modifier
) {
    val state = model.state
    Box(
        modifier = modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .background(MyColors.bg2)
    ) {

        if (state.isPackagesLoading) {
            LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
        } else {
            Content(model, coroutineScope)
        }
    }
}

@Composable
private fun Content(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    val state = model.state
    Column(
        modifier = Modifier.fillMaxWidth(),
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val nonePackage = Package(AppStore.PACKAGE_NONE)
        PackageItem(
            nonePackage,
            state.selectedPackage == AppStore.PACKAGE_NONE,
            { model.onPackageClick(nonePackage) },
            modifier = Modifier.weight(1f)
        )

        BtnIcon(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(end = 8.dp),
            visible = !state.isPackagesLoading && state.selectedDevice != AppStore.ALL_DEVICES,
            onClick = { model.onGetPackageListClick(coroutineScope) },
            description = "Refresh Package List",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall,
        )
    }
}

@Composable
private fun PackageList(state: AppStore.AppState, onClicked: (pckg: Package) -> Unit) {
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
            items(
                items,
                key = { device -> device.name }
            ) { item ->
                PackageItem(item, state.selectedPackage == item.name, { onClicked(it) }, Modifier.fillMaxWidth())
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
        RadioButton(
            selected = isSelected, onClick = { onClicked(item) }, colors = RadioButtonDefaults.colors(
                selectedColor = MyColors.radioButtonSelected,
                unselectedColor = MyColors.radioButtonUnselected
            )
        )
        Text(text = item.name, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(bottom = 6.dp))
    }
}
