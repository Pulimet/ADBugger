package ui.sections.packages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
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
        PackageNoneAndRefreshButton(state, model, coroutineScope)
        PackagesList(state) { model.onPackageClick(it) }
    }
}
