package ui.sections.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.LoadingSpinner

@Composable
fun TargetSelectionPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Content()
        }
    }
}

@Composable
private fun Content(model: AppStore = koinInject()) {
    val state = model.state
    if (state.isDevicesLoading) {
        LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
    } else {
        AllTargetsAndRefreshButton()
        TargetList()
    }
}
