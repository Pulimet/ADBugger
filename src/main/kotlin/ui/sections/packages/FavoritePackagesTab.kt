package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.Package
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun FavoritePackagesTab(model: AppStore = koinInject()) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = Modifier.padding(Dimensions.selectedPagePadding),
    ) {
        val list = listOf(Package("Testing 1"), Package("Testing 2"), Package("Testing 3"))
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            PackagesList(list)
        }
    }
}
