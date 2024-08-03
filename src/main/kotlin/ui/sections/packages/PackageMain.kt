package ui.sections.packages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.LoadingSpinner
import ui.widgets.tabs.Tabs

@Composable
fun PackagesMain(
    modifier: Modifier = Modifier, model: AppStore = koinInject()
) {
    val state = model.state
    Box(
        modifier = modifier.background(MyColors.bg2)
    ) {
        if (state.isPackagesLoading) {
            LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
        } else {
            Content()
        }
    }
}


@Composable
private fun Content() {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Tabs(listOf("Device Packages", "Favorites")) {
            when (it) {
                0 -> DevicePackagesTab()
                1 -> FavoritePackagesTab()
            }
        }
    }
}


