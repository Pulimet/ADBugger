package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.sections.*
import ui.sections.adblogs.AdbLogsPage
import ui.sections.emulator.EmulatorsPage
import ui.sections.input.InputPage
import ui.sections.packages.PackagesPage
import ui.sections.target.TargetSelectionPage

@Composable
fun SelectedPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val state = model.state

    when (state.menuItemSelected) {
        MenuItemId.WELCOME -> WelcomePage(modifier)
        MenuItemId.DEVICES -> TargetSelectionPage(modifier)
        MenuItemId.EMULATORS -> EmulatorsPage(modifier)
        MenuItemId.PACKAGES -> PackagesPage(modifier)
        MenuItemId.PERMISSIONS -> PermissionsPage(modifier)
        MenuItemId.KEYBOARD -> InputPage(modifier)
        MenuItemId.PORTS -> PortsPage(modifier)
        MenuItemId.SCALING -> ScalingPage(modifier)
        MenuItemId.LOGCAT -> LogcatPage(modifier)
        MenuItemId.LOGS -> AdbLogsPage(modifier)
        MenuItemId.SETTINGS -> SettingsPage(modifier)
    }
}
