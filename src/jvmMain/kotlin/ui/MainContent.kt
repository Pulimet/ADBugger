package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import store.AppStore
import ui.sections.*
import ui.theme.MyColors

@Composable
@Preview
fun MainContent() {
    val coroutineScope = rememberCoroutineScope()

    val model = remember { AppStore() }
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.PACKAGE_NONE
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES

    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    MaterialTheme(colors = darkColors(background = MyColors.bg, primary = Color.White, secondary = Color.White)) {
        Column {
            DeviceListSection(coroutineScope, model)
            DeviceCommands(model, coroutineScope, isDeviceSelected)
            EmulatorLauncher(model, coroutineScope)
            ArrowsCommands(model, coroutineScope)
            Numbers(model, coroutineScope)
            SendTextAndInputToDevices(model, coroutineScope)
            if (isDeviceSelected || isPackageSelected) {
                PackageListAndCommands(coroutineScope, model, isPackageSelected)
            }
            PortForwarding(model, coroutineScope)
            if (state.logs.isNotEmpty()) {
                LoggerField(model)
            }
        }
    }
}






