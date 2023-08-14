package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.sections.*
import ui.theme.MyColors

@Composable
@Preview
fun MainContent(model: AppStore, coroutineScope: CoroutineScope) {
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.PACKAGE_NONE
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES

    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    MaterialTheme(colors = darkColors(background = MyColors.bg, primary = Color.White, secondary = Color.White)) {
        Column {
            TopMenu(model)
            if(state.isDevicesControlsShown) {
                DeviceListSection(coroutineScope, model)
                EmulatorLauncher(model, coroutineScope)
            }
            DeviceCommands(model, coroutineScope, isDeviceSelected)
            if (isDeviceSelected || isPackageSelected) {
                PackageListAndCommands(coroutineScope, model, isPackageSelected)
            }
            if (isPackageSelected) {
                PermissionsCommands(model, coroutineScope)
            }

            if (state.isPortForwardingShown) {
                PortForwarding(model, coroutineScope)
            }

            if (state.isKeysInputEnabled) {
                ArrowsCommands(model, coroutineScope)
                Numbers(model, coroutineScope)
                Keyboard(model, coroutineScope)
                SendTextAndInputToDevices(model, coroutineScope)
            }

            if (state.isLogsShown) {
                LoggerField(model)
            }
        }
    }
}






