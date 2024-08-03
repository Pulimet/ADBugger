package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions

@Composable
fun FavoritePackagesTab(model: AppStore = koinInject()) {

    val list = model.state.favoritePackages
    Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
        PackagesList(
            list,
            addToFavoritesEnabled = false,
            removeFromFavoritesEnabled = true,
            forceShowSearchView = true
        )
    }

}
