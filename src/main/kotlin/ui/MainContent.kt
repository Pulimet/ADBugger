package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.navigation.sidebar.SideBar
import ui.navigation.topbar.TopBar
import ui.theme.MyColors

@Composable
@Preview
fun MainContent(model: AppStore, coroutineScope: CoroutineScope) {
    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    MaterialTheme(colors = darkColors(background = MyColors.bg, primary = Color.White, secondary = Color.White)) {
        Column {
            TopBar(model, coroutineScope)
            Row {
                SideBar(model)
                Content(model, coroutineScope)
            }
        }
    }
}






