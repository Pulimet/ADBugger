package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.LoadingSpinner

@Composable
fun DevicePackagesTab(model: AppStore = koinInject()) {
    Column(modifier = Modifier.fillMaxSize()) {
        PackageNoneAndRefreshButton()
        if (model.state.isPackagesLoading) {
            LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
        } else {
            PackagesList(model.state.packageList)
        }
    }
}
