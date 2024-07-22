package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun DevicePackagesTab(model: AppStore = koinInject()) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = Modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            PackageNoneAndRefreshButton()
            PackagesList(model.state.packageList, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
