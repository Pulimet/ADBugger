import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import store.AppStore
import ui.MainContent
import ui.theme.MyColors
import java.awt.Color

fun main() = application {
    val state = rememberWindowState(
        width = 1200.dp,
        height = 800.dp,
        position = WindowPosition(alignment = Alignment.Center)
    )

    val model = remember { AppStore() }
    val coroutineScope = rememberCoroutineScope()

    ApplicationTray(state)

    Window(
        title = "ADBugger",
        state = state,
        onCloseRequest = ::exitApplication,
        onKeyEvent = { model.onKeyEvent(coroutineScope, it) }
    ) {
        LaunchedEffect(Unit) {
            window.background = Color(MyColors.bg.toArgb())
        }
        Surface(color = MyColors.bg) {
            MainContent(model, coroutineScope)
        }
    }
}



