package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import store.AppStore
import ui.sections.DeviceListSection
import ui.sections.PackageListSection

@Composable
@Preview
fun MainContent() {
    val coroutineScope = rememberCoroutineScope()

    val model = remember { AppStore() }
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.NONE

    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    MaterialTheme {
        Column {
            Row {
                DeviceListSection(
                    coroutineScope, model, state,
                    modifier = Modifier.fillMaxWidth(0.5f).height(300.dp)
                )
                PackageListSection(
                    coroutineScope, model, state,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { model.onHomeClick(coroutineScope) }) { Text(text = "Home") }
                Button(onClick = { model.onSettingsClick(coroutineScope) }) { Text(text = "Settings") }
                Button(onClick = { model.onBackClick(coroutineScope) }) { Text(text = "Back") }
                Button(onClick = { model.onTabClick(coroutineScope) }) { Text(text = "Tab") }
                Button(onClick = { model.onEnterClick(coroutineScope) }) { Text(text = "Enter") }
                Button(onClick = { model.onPowerClick(coroutineScope) }) { Text(text = "Power") }
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { model.onDayClick(coroutineScope) }) { Text(text = "Day") }
                Button(onClick = { model.onNightClick(coroutineScope) }) { Text(text = "Night") }
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(
                    enabled = isPackageSelected,
                    onClick = { model.onOpenClick(coroutineScope) }) { Text(text = "Open") }
                Button(
                    enabled = isPackageSelected,
                    onClick = { model.onCloseClick(coroutineScope) }) { Text(text = "Close") }
                Button(
                    enabled = isPackageSelected,
                    onClick = { model.onRestartClick(coroutineScope) }) { Text(text = "Restart") }
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
}



