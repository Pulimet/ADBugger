package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.bounceClick

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
        Button(
            modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected,
            onClick = { model.onOpenClick(coroutineScope) }) { Text(text = "Open") }
        Button(
            modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected,
            onClick = { model.onCloseClick(coroutineScope) }) { Text(text = "Close") }
        Button(modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected,
            onClick = { model.onRestartClick(coroutineScope) }) { Text(text = "Restart") }
        Button(modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected,
            onClick = { model.onClearDataClick(coroutineScope) }) { Text(text = "Clear Data") }
        Button(modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected,
            onClick = { model.onClearAndRestartClick(coroutineScope) }) { Text(text = "Clear&Restart") }
        Button(modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected,
            onClick = { model.onUninstallClick(coroutineScope) }) { Text(text = "Uninstall") }
        Button(modifier = Modifier.bounceClick().width(Dimensions.buttonWidth),
            enabled = isPackageSelected && isDeviceSelected,
            onClick = { model.onApkPath(coroutineScope) }) { Text(text = "APK Path") }
    }
}
