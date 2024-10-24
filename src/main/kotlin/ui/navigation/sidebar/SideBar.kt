package ui.navigation.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.fontawesomeicons.solid.Cat
import compose.icons.octicons.Package24
import compose.icons.tablericons.ArrowNarrowLeft
import compose.icons.tablericons.ArrowNarrowRight
import compose.icons.tablericons.ArrowsRightLeft
import compose.icons.tablericons.Braces
import compose.icons.tablericons.BrandAndroid
import compose.icons.tablericons.FileInfo
import compose.icons.tablericons.Home
import compose.icons.tablericons.Notes
import compose.icons.tablericons.Resize
import compose.icons.tablericons.Ruler
import compose.icons.tablericons.Settings
import compose.icons.tablericons.SettingsAutomation
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.side_adb_ogs
import net.alexandroid.adbugger.adbugger.generated.resources.side_device
import net.alexandroid.adbugger.adbugger.generated.resources.side_emulators
import net.alexandroid.adbugger.adbugger.generated.resources.side_keyboard
import net.alexandroid.adbugger.adbugger.generated.resources.side_logcat
import net.alexandroid.adbugger.adbugger.generated.resources.side_misc
import net.alexandroid.adbugger.adbugger.generated.resources.side_packages
import net.alexandroid.adbugger.adbugger.generated.resources.side_permissions
import net.alexandroid.adbugger.adbugger.generated.resources.side_ports
import net.alexandroid.adbugger.adbugger.generated.resources.side_scaling
import net.alexandroid.adbugger.adbugger.generated.resources.side_settings
import net.alexandroid.adbugger.adbugger.generated.resources.side_targets
import net.alexandroid.adbugger.adbugger.generated.resources.side_version
import net.alexandroid.adbugger.adbugger.generated.resources.side_welcome
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pref.preference
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.buttons.BtnIcon

enum class MenuItemId {
    WELCOME, DEVICES, EMULATORS, PACKAGES, PERMISSIONS, KEYBOARD, PORTS, SCALING, LOGCAT, LOGS, PROPS, MISC, SETTINGS;

    companion object {
        fun getByOrdinal(ordinal: Int): MenuItemId {
            return MenuItemId.entries.firstOrNull { it.ordinal == ordinal } ?: DEVICES
        }
    }
}

@Composable
fun SideBar(
    model: AppStore = koinInject(),
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
            title = stringResource(Res.string.side_welcome),
            onClick = { toggleState(MenuItemId.WELCOME) },
            collapsed = isBarClosedState,
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.DEVICES),
            icon = TablerIcons.SettingsAutomation,
            title = stringResource(Res.string.side_targets),
            onClick = { toggleState(MenuItemId.DEVICES) },
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.BrandAndroid,
            toggle = isSelected(MenuItemId.EMULATORS),
            onClick = { toggleState(MenuItemId.EMULATORS) },
            title = stringResource(Res.string.side_emulators),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = Octicons.Package24,
            toggle = isSelected(MenuItemId.PACKAGES),
            onClick = { toggleState(MenuItemId.PACKAGES) },
            title = stringResource(Res.string.side_packages),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.Ruler,
            toggle = isSelected(MenuItemId.PERMISSIONS),
            onClick = { toggleState(MenuItemId.PERMISSIONS) },
            title = stringResource(Res.string.side_permissions),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = FontAwesomeIcons.Regular.Keyboard,
            toggle = isSelected(MenuItemId.KEYBOARD),
            onClick = { toggleState(MenuItemId.KEYBOARD) },
            title = stringResource(Res.string.side_keyboard),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.ArrowsRightLeft,
            toggle = isSelected(MenuItemId.PORTS),
            onClick = { toggleState(MenuItemId.PORTS) },
            title = stringResource(Res.string.side_ports),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.Resize,
            toggle = isSelected(MenuItemId.SCALING),
            onClick = { toggleState(MenuItemId.SCALING) },
            title = stringResource(Res.string.side_scaling),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = FontAwesomeIcons.Solid.Cat,
            toggle = isSelected(MenuItemId.LOGCAT),
            onClick = { toggleState(MenuItemId.LOGCAT) },
            title = stringResource(Res.string.side_logcat),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.Notes,
            toggle = isSelected(MenuItemId.LOGS),
            onClick = { toggleState(MenuItemId.LOGS) },
            title = stringResource(Res.string.side_adb_ogs),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.FileInfo,
            toggle = isSelected(MenuItemId.PROPS),
            onClick = { toggleState(MenuItemId.PROPS) },
            title = stringResource(Res.string.side_device),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            icon = TablerIcons.Braces,
            toggle = isSelected(MenuItemId.MISC),
            onClick = { toggleState(MenuItemId.MISC) },
            title = stringResource(Res.string.side_misc),
            collapsed = isBarClosedState,
        )
        SideBarItem(
            toggle = isSelected(MenuItemId.SETTINGS),
            icon = TablerIcons.Settings,
            title = stringResource(Res.string.side_settings),
            onClick = { toggleState(MenuItemId.SETTINGS) },
            collapsed = isBarClosedState,
        )
        if (!isBarClosedState) {
            Text(
                text = stringResource(Res.string.side_version, model.version),
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
                modifier = Modifier.padding(
                    horizontal = if (isBarClosedState) 4.dp else 12.dp,
                    vertical = 12.dp
                ),
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
                showTooltip = false
            )
        }
    }
}
