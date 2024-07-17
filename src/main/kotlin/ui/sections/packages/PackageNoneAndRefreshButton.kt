package ui.sections.packages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Package
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.BtnWithText

@Composable
fun PackageNoneAndRefreshButton(
    state: AppStore.AppState,
    model: AppStore = koinInject()
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val nonePackage = Package(AppStore.PACKAGE_NONE)
        PackageItem(
            nonePackage,
            state.selectedPackage == AppStore.PACKAGE_NONE,
            { model.onPackageClick(nonePackage) },
            modifier = Modifier.weight(1f)
        )

        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            visible = !state.isPackagesLoading && state.selectedDevice != AppStore.ALL_DEVICES,
            enabled = true,
            onClick = { model.onGetPackageListClick() },
            description = "Get Packages List",
            width = 120.dp,
        )
    }
}
