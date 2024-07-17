package ui.sections.packages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.HoverButton

@Composable
fun PackageCommands(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    val coroutineScope = rememberCoroutineScope()
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.PACKAGE_NONE
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES

    Column(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HoverButton(
            enabled = isPackageSelected,
            onClick = { model.onOpenClick(coroutineScope) },
            text = "Open"
        )
        HoverButton(
            enabled = isPackageSelected,
            onClick = { model.onCloseClick(coroutineScope) },
            text = "Close"
        )
        HoverButton(
            enabled = isPackageSelected,
            onClick = { model.onRestartClick(coroutineScope) },
            text = "Restart"
        )
        HoverButton(
            enabled = isPackageSelected,
            onClick = { model.onClearDataClick(coroutineScope) },
            text = "Clear Data"
        )
        HoverButton(
            enabled = isPackageSelected,
            onClick = { model.onClearAndRestartClick(coroutineScope) },
            text = "Clear&Restart"
        )
        HoverButton(
            enabled = isPackageSelected,
            onClick = { model.onUninstallClick(coroutineScope) },
            text = "Uninstall"
        )
        HoverButton(
            enabled = isPackageSelected && isDeviceSelected,
            onClick = { model.onApkPath(coroutineScope) },
            text = "APK Path"
        )
    }
}
