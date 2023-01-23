import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.MainContent

fun main() = application {
    val state = rememberWindowState(
        width = 300.dp,
        height = 200.dp,
        position = WindowPosition(alignment = Alignment.Center)
    )

    ApplicationTray(state)
    Window(
        title = "ADBugger",
        state = state,
        onCloseRequest = ::exitApplication
    ) {
        MainContent()
    }
}



