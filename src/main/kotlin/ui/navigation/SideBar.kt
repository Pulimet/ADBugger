package ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.octicons.Package24
import compose.icons.tablericons.*
import store.AppStore
import ui.theme.MyColors
import ui.widgets.SideBarItem

enum class MenuItemId { DEVICES, EMULATORS, COMMANDS, PACKAGES, PERMISSIONS, KEYBOARD, PORTS, LOGS }

@Composable
fun SideBar(
    model: AppStore,
    modifier: Modifier = Modifier
        .width(200.dp)
        .fillMaxHeight()
        .background(MyColors.bg2)
        .padding(vertical = 12.dp, horizontal = 24.dp)
) {
    Column(modifier = modifier) {
        SideBarLogo()
        SideBarItem(
            id = MenuItemId.DEVICES,
            toggle = false,
            icon = TablerIcons.SettingsAutomation,
            title = "Device Selection"
        )
        SideBarItem(id = MenuItemId.EMULATORS, toggle = true, icon = TablerIcons.BrandAndroid, title = "Emulators")
        SideBarItem(id = MenuItemId.COMMANDS, toggle = false, icon = TablerIcons.Command, title = "Basic Commands")
        SideBarItem(id = MenuItemId.PACKAGES, toggle = false, icon = Octicons.Package24, title = "Packages")
        SideBarItem(id = MenuItemId.PERMISSIONS, toggle = false, icon = TablerIcons.Ruler, title = "Permissions")
        SideBarItem(
            id = MenuItemId.KEYBOARD,
            toggle = false,
            icon = FontAwesomeIcons.Regular.Keyboard,
            title = "Keyboard"
        )
        SideBarItem(id = MenuItemId.PORTS, toggle = false, icon = TablerIcons.ArrowsRightLeft, title = "Ports")
        SideBarItem(id = MenuItemId.LOGS, toggle = false, icon = TablerIcons.Notes, title = "Logs")
    }
}
