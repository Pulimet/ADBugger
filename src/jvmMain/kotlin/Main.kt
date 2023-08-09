import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.MainContent
import ui.theme.MyColors

fun main() = application {
    val state = rememberWindowState(
        width = 460.dp,
        height = 200.dp,
        position = WindowPosition(alignment = Alignment.TopCenter)
    )

    ApplicationTray(state)

    Window(title = "ADBugger", state = state, onCloseRequest = ::exitApplication) {
        Surface(color = MyColors.bg, modifier = Modifier.wrapContentSize().onSizeChanged {
            if (it.height > 100) {
                window.setSize(window.width, it.height / 2 + 32)
            }
        }) {
            MainContent()
        }
    }
}



