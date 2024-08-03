package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions

@Composable
fun DevicePackagesTab(model: AppStore = koinInject()) {
    Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
        PackageNoneAndRefreshButton()
        PackagesList(model.state.packageList, modifier = Modifier.padding(top = 8.dp))
    }

}
