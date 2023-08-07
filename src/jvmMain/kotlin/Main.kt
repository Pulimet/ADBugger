import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        width = 450.dp,
        height = 800.dp,
        position = WindowPosition(alignment = Alignment.Center)
    )

    ApplicationTray(state)

    Window(title = "ADBugger", state = state, onCloseRequest = ::exitApplication) {
        Surface(color = MyColors.bg, modifier = Modifier.fillMaxSize()) {
            MainContent()
        }
    }
}



