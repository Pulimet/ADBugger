package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun PackageListAndCommands(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.PACKAGE_NONE
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES

    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = Modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Row(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            PackageListSection(coroutineScope, model, Modifier.weight(1f))
            PackageCommands(isPackageSelected, model, coroutineScope, isDeviceSelected)
        }
    }
}
