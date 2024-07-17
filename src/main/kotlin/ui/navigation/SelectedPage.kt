package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.sections.*
import ui.sections.emulator.EmulatorsPage
import ui.sections.input.InputPage
import ui.sections.packages.PackagesPage
import ui.sections.target.TargetSelectionPage

@Composable
fun SelectedPage(coroutineScope: CoroutineScope, modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val state = model.state

    when (state.menuItemSelected) {
        MenuItemId.WELCOME -> WelcomePage(modifier)
        MenuItemId.DEVICES -> TargetSelectionPage(coroutineScope, modifier)
        MenuItemId.EMULATORS -> EmulatorsPage(coroutineScope, modifier)
        MenuItemId.PACKAGES -> PackagesPage(coroutineScope, modifier)
        MenuItemId.PERMISSIONS -> PermissionsPage(coroutineScope, modifier)
        MenuItemId.KEYBOARD -> InputPage(coroutineScope, modifier)
        MenuItemId.PORTS -> PortsPage(coroutineScope, modifier)
        MenuItemId.LOGS -> AdbLogsPage(modifier)
        MenuItemId.SETTINGS -> SettingsPage(modifier)
    }
}
