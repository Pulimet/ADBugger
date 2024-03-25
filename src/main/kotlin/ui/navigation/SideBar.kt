package ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.octicons.Package24
import compose.icons.tablericons.*
import pref.preference
import store.AppStore
import ui.theme.MyColors

enum class MenuItemId {
    DEVICES, EMULATORS, COMMANDS, PACKAGES, PERMISSIONS, KEYBOARD, PORTS, LOGS;

    companion object {
        fun getByOrdinal(ordinal: Int): MenuItemId {
            return MenuItemId.entries.firstOrNull { it.ordinal == ordinal } ?: DEVICES
        }
    }
}

@Composable
fun SideBar(
    model: AppStore,
    modifier: Modifier = Modifier
        .width(200.dp)
        .fillMaxHeight()
        .background(MyColors.bg2)
        .padding(vertical = 12.dp, horizontal = 24.dp)
) {
    var selectedId: Int by preference("SideMenuSelectedItem", 0)
    var selected by remember { mutableStateOf(selectedId) }

    LaunchedEffect(Unit) {
        model.showPage(MenuItemId.getByOrdinal(selected))
    }

    fun toggleState(menuItemId: MenuItemId) {
        selectedId = menuItemId.ordinal
        selected = menuItemId.ordinal
        model.showPage(menuItemId)
    }

    fun isSelected(menuItemId: MenuItemId) = menuItemId.ordinal == selected

    Column(modifier = modifier) {
        SideBarLogo()
        SideBarItem(
            toggle = isSelected(MenuItemId.DEVICES),
            icon = TablerIcons.SettingsAutomation,
            title = "Device Selection",
            onClick = { toggleState(MenuItemId.DEVICES) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.EMULATORS),
            icon = TablerIcons.BrandAndroid,
            title = "Emulators",
            onClick = { toggleState(MenuItemId.EMULATORS) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.COMMANDS),
            icon = TablerIcons.Command,
            title = "Basic Commands",
            onClick = { toggleState(MenuItemId.COMMANDS) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.PACKAGES),
            icon = Octicons.Package24,
            title = "Packages",
            onClick = { toggleState(MenuItemId.PACKAGES) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.PERMISSIONS),
            icon = TablerIcons.Ruler,
            title = "Permissions",
            onClick = { toggleState(MenuItemId.PERMISSIONS) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.KEYBOARD),
            icon = FontAwesomeIcons.Regular.Keyboard,
            title = "Keyboard",
            onClick = { toggleState(MenuItemId.KEYBOARD) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.PORTS),
            icon = TablerIcons.ArrowsRightLeft,
            title = "Ports",
            onClick = { toggleState(MenuItemId.PORTS) },
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.LOGS),
            icon = TablerIcons.Notes,
            title = "Logs",
            onClick = { toggleState(MenuItemId.LOGS) },
        )
    }
}
