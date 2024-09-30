package ui.sections.packages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.theme.MyColors
import ui.widgets.tabs.Tabs

@Composable
fun PackagesMain(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(MyColors.bg2)) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Tabs(
                listOf("Device Packages", "Favorites", "Launcher", "Deep Links"),
                saveSelectedTabKey = "PackagesTabs"
            ) {
                when (it) {
                    0 -> DevicePackagesTab()
                    1 -> FavoritePackagesTab()
                    2 -> LauncherPackagesTab()
                    3 -> DeepLinkTab()
                }
            }
        }
    }
}



