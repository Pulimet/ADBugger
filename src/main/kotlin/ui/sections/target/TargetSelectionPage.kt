package ui.sections.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.LoadingSpinner


@Composable
fun TargetSelectionPage(
    model: AppStore,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Content(model, coroutineScope)
        }
    }
}

@Composable
private fun Content(model: AppStore, coroutineScope: CoroutineScope) {
    val state = model.state
    if (state.isDevicesLoading) {
        LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
    } else {
        AllTargetsAndRefreshButton(model, coroutineScope)
        TargetList(model, coroutineScope) { model.onDeviceClick(it) }
    }
}
