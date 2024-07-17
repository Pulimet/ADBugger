package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.SelectedPage
import ui.navigation.StatusBar
import ui.navigation.sidebar.MenuItemId
import ui.sections.AdbLogsPage

@Composable
@Preview
fun Content(model: AppStore = koinInject()) {
    val isBottomLogsShown = model.state.isLogsAlwaysShown && model.state.menuItemSelected != MenuItemId.LOGS
    val selectedPageWeight = if (isBottomLogsShown) 0.75f else 1f

    Column {
        SelectedPage(Modifier.weight(selectedPageWeight))

        if (isBottomLogsShown) {
            AdbLogsPage(Modifier.weight(0.25f))
        }

        StatusBar()
    }

}
