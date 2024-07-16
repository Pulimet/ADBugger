import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowState

@Composable
fun ApplicationScope.ApplicationTray(state: WindowState) {
    val icon = painterResource("128.png")

    Tray(
        icon = icon,
        menu = {
            Item("Show/Hide", onClick = { state.isMinimized = !state.isMinimized })
            Item("Quit", onClick = ::exitApplication)
        }
    )
}
