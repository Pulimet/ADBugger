package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.widgets.HoverButton

@Composable
fun PackageCommands(
    isPackageSelected: Boolean,
    model: AppStore,
    coroutineScope: CoroutineScope,
    isDeviceSelected: Boolean,
    modifier: Modifier = Modifier
) {
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
