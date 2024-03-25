package ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.SettingsAutomation
import store.AppStore
import ui.widgets.SideBarItem

@Composable
fun SideBar(model: AppStore) {
    Column(modifier = Modifier.width(200.dp).padding(vertical = 12.dp, horizontal =16.dp)) {
        SideBarLogo()
        SideBarItem(icon = TablerIcons.SettingsAutomation, title = "Devices Control")
    }
}
