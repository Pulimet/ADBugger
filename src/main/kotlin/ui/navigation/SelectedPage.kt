package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.sections.AdbLogsPage
import ui.sections.DevicePropsPage
import ui.sections.PermissionsPage
import ui.sections.PortsPage
import ui.sections.ScalingPage
import ui.sections.SettingsPage
import ui.sections.WelcomePage
import ui.sections.emulator.EmulatorsPage
import ui.sections.input.InputPage
import ui.sections.logcat.LogcatPage
import ui.sections.misc.MiscPage
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
        MenuItemId.PROPS -> DevicePropsPage(modifier)
        MenuItemId.MISC -> MiscPage(modifier)
        MenuItemId.SETTINGS -> SettingsPage(modifier)
    }
}
