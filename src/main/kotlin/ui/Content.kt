package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.navigation.MenuItemId
import ui.navigation.SelectedPage
import ui.sections.LoggerField

@Composable
@Preview
fun Content(model: AppStore, coroutineScope: CoroutineScope) {
    val isBottomLogsShown = model.state.isLogsAlwaysShown && model.state.menuItemSelected != MenuItemId.LOGS
    val selectedPageWeight = if (isBottomLogsShown) 0.75f else 1f

    Column {
        SelectedPage(model, coroutineScope, Modifier.weight(selectedPageWeight))

        if (isBottomLogsShown) {
            LoggerField(model, Modifier.weight(0.25f))
        }
    }

}
