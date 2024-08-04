package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.SideBar
import ui.navigation.topbar.TopBar
import ui.theme.MyColors

@Composable
fun MainContent(model: AppStore = koinInject()) {
    LaunchedEffect(Unit) {
        model.onLaunchedEffect()
    }

    MaterialTheme(colors = darkColors(background = MyColors.bg, primary = Color.White, secondary = Color.White)) {
        Column {
            TopBar()
            Row {
                SideBar()
                Content()
            }
        }
    }
}






