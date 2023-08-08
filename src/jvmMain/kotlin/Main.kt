import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
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
        height = 1000.dp,
        position = WindowPosition(alignment = Alignment.Center)
    )

    ApplicationTray(state)

    Window(title = "ADBugger", state = state, onCloseRequest = ::exitApplication) {
        Surface(color = MyColors.bg, modifier = Modifier.fillMaxWidth().wrapContentHeight().onSizeChanged {
            if (it.height > 0) {
                window.setSize(window.width, it.height + 30 )
            }
        }) {
            MainContent()
        }
    }
}



