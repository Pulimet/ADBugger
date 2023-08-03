package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
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
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES

    val textInputSendTextState = remember { mutableStateOf(TextFieldValue("")) }

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
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
            )
            {
                TextField(
                    modifier = Modifier.padding(8.dp),
                    singleLine = true,
                    value = textInputSendTextState.value,
                    label = { Text("Send text to device/s") },
                    onValueChange = { value -> textInputSendTextState.value = value }
                )
                Button(onClick = { model.onSendTextClick(coroutineScope, textInputSendTextState.value.text) }) {
                    Text(text = "Send")
                }
                Button(onClick = { model.onBackSpaceClick(coroutineScope) }, modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = "Backspace")
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { model.onHomeClick(coroutineScope) }) { Text(text = "Home") }
                Button(onClick = { model.onSettingsClick(coroutineScope) }) { Text(text = "Settings") }
                Button(onClick = { model.onBackClick(coroutineScope) }) { Text(text = "Back") }
                Button(onClick = { model.onTabClick(coroutineScope) }) { Text(text = "Tab") }
                Button(onClick = { model.onEnterClick(coroutineScope) }) { Text(text = "Enter") }
                Button(onClick = { model.onPowerClick(coroutineScope) }) { Text(text = "Power") }
                Button(
                    enabled = isDeviceSelected,
                    onClick = { model.onSnapClick(coroutineScope) }) { Text(text = "Snap") }
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { model.onDayClick(coroutineScope) }) { Text(text = "Day") }
                Button(onClick = { model.onNightClick(coroutineScope) }) { Text(text = "Night") }
                Button(onClick = { model.onLeftClick(coroutineScope) }) { Text(text = "←") }
                Button(onClick = { model.onUpClick(coroutineScope) }) { Text(text = "↑") }
                Button(onClick = { model.onRightClick(coroutineScope) }) { Text(text = "→") }
                Button(onClick = { model.onDownClick(coroutineScope) }) { Text(text = "↓") }
            }

            PackageCommands(isPackageSelected, model, coroutineScope)

        }
    }
}

@Composable
private fun PackageCommands(
    isPackageSelected: Boolean,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
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



