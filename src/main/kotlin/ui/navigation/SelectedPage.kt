package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.sections.*
import ui.sections.emulator.EmulatorsPage
import ui.sections.input.InputPage
import ui.sections.packages.PackagesPage
import ui.sections.target.TargetSelectionPage

@Composable
fun SelectedPage(model: AppStore, coroutineScope: CoroutineScope, modifier: Modifier = Modifier) {
    val state = model.state

    when (state.menuItemSelected) {
        MenuItemId.WELCOME -> WelcomePage(modifier)
        MenuItemId.DEVICES -> TargetSelectionPage(model, coroutineScope, modifier)
        MenuItemId.EMULATORS -> EmulatorsPage(model, coroutineScope, modifier)
        MenuItemId.PACKAGES -> PackagesPage(model, coroutineScope, modifier)
        MenuItemId.PERMISSIONS -> PermissionsPage(model, coroutineScope, modifier)
        MenuItemId.KEYBOARD -> InputPage(model, coroutineScope, modifier)
        MenuItemId.PORTS -> PortsPage(model, coroutineScope, modifier)
        MenuItemId.LOGS -> AdbLogsPage(model, modifier)
        MenuItemId.SETTINGS -> SettingsPage(modifier)
    }
}
