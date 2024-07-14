package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.navigation.MenuItemId
import ui.navigation.SelectedPage
import ui.navigation.SideBar
import ui.navigation.TopBar
import ui.sections.LoggerField
import ui.theme.MyColors

@Composable
@Preview
fun MainContent(model: AppStore, coroutineScope: CoroutineScope) {
    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    val isBottomLogsShown = model.state.isLogsAlwaysShown && model.state.menuItemSelected != MenuItemId.LOGS

    MaterialTheme(colors = darkColors(background = MyColors.bg, primary = Color.White, secondary = Color.White)) {
        Column {
            TopBar(model, coroutineScope)
            Row {
                SideBar(model)
                Column {
                    SelectedPage(
                        model,
                        coroutineScope,
                        if (isBottomLogsShown) Modifier.weight(0.75f) else Modifier.weight(1f)
                    )
                    if (isBottomLogsShown) {
                        LoggerField(model, Modifier.weight(0.25f))
                    }
                }
            }
        }
    }
}






