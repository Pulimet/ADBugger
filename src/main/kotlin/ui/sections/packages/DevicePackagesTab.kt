package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore

@Composable
fun DevicePackagesTab(model: AppStore = koinInject()) {
    Column(modifier = Modifier.fillMaxSize()) {
        PackageNoneAndRefreshButton()
        PackagesList(model.state.packageList)
    }
}
