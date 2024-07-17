package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.navigation.SelectedPage
import ui.sections.AdbLogsPage

@Composable
@Preview
fun Content(coroutineScope: CoroutineScope, model: AppStore = koinInject()) {
    val isBottomLogsShown = model.state.isLogsAlwaysShown && model.state.menuItemSelected != MenuItemId.LOGS
    val selectedPageWeight = if (isBottomLogsShown) 0.75f else 1f

    Column {
        SelectedPage(coroutineScope, Modifier.weight(selectedPageWeight))

        if (isBottomLogsShown) {
            AdbLogsPage(Modifier.weight(0.25f))
        }
    }

}
