package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.MyColors

@Composable
fun PackageCommands(
    isPackageSelected: Boolean,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
        ) {
            Button(
                enabled = isPackageSelected,
                onClick = { model.onOpenClick(coroutineScope) }) { Text(text = "Open") }
            Button(
                enabled = isPackageSelected,
                onClick = { model.onCloseClick(coroutineScope) }) { Text(text = "Close") }
            Button(
                enabled = isPackageSelected,
                onClick = { model.onRestartClick(coroutineScope) }) { Text(text = "Restart") }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
        ) {
            Button(
                enabled = isPackageSelected,
                onClick = { model.onClearDataClick(coroutineScope) }) { Text(text = "Clear Data") }
            Button(
                enabled = isPackageSelected,
                onClick = { model.onClearAndRestartClick(coroutineScope) }) { Text(text = "Clear&Restart") }
            Button(
                enabled = isPackageSelected,
                onClick = { model.onUninstallClick(coroutineScope) }) { Text(text = "Uninstall") }
        }
    }
}
