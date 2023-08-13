import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import store.AppStore
import ui.MainContent
import ui.theme.MyColors

fun main() = application {
    val state = rememberWindowState(
        width = 460.dp,
        height = 200.dp,
        position = WindowPosition(alignment = Alignment.TopCenter)
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
        Surface(color = MyColors.bg, modifier = Modifier.wrapContentSize().onSizeChanged {
            if (it.height > 100) {
                window.setSize(window.width, it.height + 32)
            }
        }) {
            MainContent(model, coroutineScope)
        }
    }
}



