package ui.sections.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.LoadingSpinner


@Composable
fun TargetSelectionPage(
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Content(coroutineScope)
        }
    }
}

@Composable
private fun Content(
    coroutineScope: CoroutineScope,
    model: AppStore = koinInject()
) {
    val state = model.state
    if (state.isDevicesLoading) {
        LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
    } else {
        AllTargetsAndRefreshButton(coroutineScope)
        TargetList(coroutineScope, { model.onDeviceClick(it) })
    }
}
