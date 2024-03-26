package ui.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.sections.*

@Composable
fun SelectedPage(model: AppStore, coroutineScope: CoroutineScope) {
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.PACKAGE_NONE
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES


    when (state.menuItemSelected) {
        MenuItemId.DEVICES -> DeviceListSection(model, coroutineScope)
        MenuItemId.EMULATORS -> EmulatorLauncher(model, coroutineScope)
        MenuItemId.PACKAGES -> PackageListAndCommands(coroutineScope, model, isPackageSelected, isDeviceSelected)
        MenuItemId.PERMISSIONS -> PermissionsCommands(model, coroutineScope)
        MenuItemId.KEYBOARD -> {
            ArrowsCommands(model, coroutineScope)
            Numbers(model, coroutineScope)
            Keyboard(model, coroutineScope)
            SendTextAndInputToDevices(model, coroutineScope)
        }

        MenuItemId.PORTS -> PortForwarding(model, coroutineScope)
        MenuItemId.LOGS -> LoggerField(model)
    }
}
