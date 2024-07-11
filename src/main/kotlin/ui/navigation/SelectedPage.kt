package ui.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.sections.*

@Composable
fun SelectedPage(model: AppStore, coroutineScope: CoroutineScope) {
    val state = model.state

    when (state.menuItemSelected) {
        MenuItemId.WELCOME -> Welcome()
        MenuItemId.DEVICES -> DeviceListSection(model, coroutineScope)
        MenuItemId.EMULATORS -> EmulatorLauncher(model, coroutineScope)
        MenuItemId.PACKAGES -> PackageListAndCommands(model, coroutineScope)
        MenuItemId.PERMISSIONS -> PermissionsCommands(model, coroutineScope)
        MenuItemId.KEYBOARD -> Keyboard(model, coroutineScope)
        MenuItemId.PORTS -> PortForwarding(model, coroutineScope)
        MenuItemId.LOGS -> LoggerField(model)
        MenuItemId.SETTINGS -> Settings()
    }
}
