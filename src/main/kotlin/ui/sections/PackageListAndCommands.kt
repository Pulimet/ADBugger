package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun PackageListAndCommands(
    coroutineScope: CoroutineScope,
    model: AppStore,
    isPackageSelected: Boolean,
    isDeviceSelected: Boolean,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Row(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            PackageListSection(coroutineScope, model, Modifier.weight(0.8f))
            PackageCommands(isPackageSelected, model, coroutineScope, isDeviceSelected, Modifier.weight(0.2f))
        }
    }
}
