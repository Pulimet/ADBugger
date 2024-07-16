package ui.sections.packages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import model.Package
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnWithText
import ui.widgets.LoadingSpinner

@Composable
fun PackagesMain(
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
        PackageNotSelectedAndRefreshButton(state, model, coroutineScope)
        PackagesList(state) { model.onPackageClick(it) }
    }
}

@Composable
private fun PackageNotSelectedAndRefreshButton(
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

        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            visible = !state.isPackagesLoading && state.selectedDevice != AppStore.ALL_DEVICES,
            enabled = true,
            onClick = { model.onGetPackageListClick(coroutineScope) },
            description = "Get Packages List",
            width = 120.dp,
        )
    }
}
