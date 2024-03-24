import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
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
        width = 500.dp,
        height = 50.dp,
        position = WindowPosition(alignment = Alignment.TopEnd)
    )

    val model = remember { AppStore() }
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    ApplicationTray(state)

    Window(
        title = "ADBugger",
        state = state,
        onCloseRequest = ::exitApplication,
        onKeyEvent = { model.onKeyEvent(coroutineScope, it) }
    ) {
        LaunchedEffect(Unit) {
            window.background = Color(0x252734)
        }
        Surface(color = MyColors.bg, modifier = Modifier.composed {
            Modifier.onSizeChanged {
                window.setSize(window.width, (it.height / density.density).toInt() + 32)
            }
        }) {
            MainContent(model, coroutineScope)
        }
    }
}



