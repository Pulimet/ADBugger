package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.sections.*

@Composable
fun SelectedPage(model: AppStore, coroutineScope: CoroutineScope, modifier: Modifier = Modifier) {
    val state = model.state

    when (state.menuItemSelected) {
        MenuItemId.WELCOME -> Welcome(modifier)
        MenuItemId.DEVICES -> DeviceListSection(model, coroutineScope, modifier)
        MenuItemId.EMULATORS -> EmulatorLauncher(model, coroutineScope, modifier)
        MenuItemId.PACKAGES -> PackageListAndCommands(model, coroutineScope, modifier)
        MenuItemId.PERMISSIONS -> PermissionsCommands(model, coroutineScope, modifier)
        MenuItemId.KEYBOARD -> Keyboard(model, coroutineScope, modifier)
        MenuItemId.PORTS -> PortForwarding(model, coroutineScope, modifier)
        MenuItemId.LOGS -> LoggerField(model, modifier)
        MenuItemId.SETTINGS -> Settings(modifier)
    }
}
