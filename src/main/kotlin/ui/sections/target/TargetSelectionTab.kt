package ui.sections.target

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.LoadingSpinner

@Composable
fun TargetSelectionTab(model: AppStore = koinInject()) {
    val state = model.state
    if (state.isDevicesLoading) {
        LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
    } else {
        AllTargetsAndRefreshButton()
        TargetList()
    }
}
