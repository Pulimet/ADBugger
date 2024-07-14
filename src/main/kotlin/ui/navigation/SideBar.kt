package ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

enum class MenuItemId {
    WELCOME, DEVICES, EMULATORS, PACKAGES, PERMISSIONS, KEYBOARD, PORTS, LOGS, SETTINGS;

    companion object {
        fun getByOrdinal(ordinal: Int): MenuItemId {
            return MenuItemId.entries.firstOrNull { it.ordinal == ordinal } ?: DEVICES
        }
    }
}

@Composable
fun SideBar(
    model: AppStore,
    modifier: Modifier = Modifier.fillMaxHeight().background(MyColors.bg2)
) {
    val stateVertical = rememberScrollState(0)
    var isBarCollapsed: Boolean by preference("isSideBarCollapsed", false)
    var isBarClosedState by remember { mutableStateOf(isBarCollapsed) }

    fun toggleBar() {
        isBarCollapsed = !isBarCollapsed
        isBarClosedState = isBarCollapsed
    }

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

    Column(
        modifier = modifier
            .width(if (isBarClosedState) Dimensions.sideBarWidthCollapsed else Dimensions.sideBarWidth)
            .verticalScroll(stateVertical)
    ) {
        SideBarItem(
            toggle = isSelected(MenuItemId.WELCOME),
            icon = TablerIcons.Home,
            title = "Welcome",
            onClick = { toggleState(MenuItemId.WELCOME) },
            collapsed = isBarClosedState,
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.DEVICES),
            icon = TablerIcons.SettingsAutomation,
            title = "Target Selection",
            onClick = { toggleState(MenuItemId.DEVICES) },
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.BrandAndroid,
            toggle = isSelected(MenuItemId.EMULATORS),
            onClick = { toggleState(MenuItemId.EMULATORS) },
            title = "Emulators",
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = Octicons.Package24,
            toggle = isSelected(MenuItemId.PACKAGES),
            onClick = { toggleState(MenuItemId.PACKAGES) },
            title = "Packages",
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.Ruler,
            toggle = isSelected(MenuItemId.PERMISSIONS),
            onClick = { toggleState(MenuItemId.PERMISSIONS) },
            title = "Permissions",
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = FontAwesomeIcons.Regular.Keyboard,
            toggle = isSelected(MenuItemId.KEYBOARD),
            onClick = { toggleState(MenuItemId.KEYBOARD) },
            title = "Keyboard",
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.ArrowsRightLeft,
            toggle = isSelected(MenuItemId.PORTS),
            onClick = { toggleState(MenuItemId.PORTS) },
            title = "Ports",
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.Notes,
            toggle = isSelected(MenuItemId.LOGS),
            onClick = { toggleState(MenuItemId.LOGS) },
            title = "ADB Logs",
            collapsed = isBarClosedState,
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.SETTINGS),
            icon = TablerIcons.Settings,
            title = "Settings",
            onClick = { toggleState(MenuItemId.SETTINGS) },
            collapsed = isBarClosedState,
        )
        if (!isBarClosedState) {
            Text(
                text = "Version: ${model.version}",
                fontSize = Dimensions.versionFontSize,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            BtnIcon(
                icon = if (isBarClosedState) TablerIcons.ArrowNarrowRight else TablerIcons.ArrowNarrowLeft,
                onClick = { toggleBar() },
                modifier = Modifier.padding(horizontal = if (isBarClosedState) 4.dp else 12.dp, vertical = 12.dp),
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
                showTooltip = false
            )
        }
    }
}
