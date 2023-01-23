import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowState

@Composable
fun ApplicationScope.ApplicationTray(state: WindowState) {
    val icon = painterResource("sample.png")

    Tray(
        icon = icon,
        onAction = { state.isMinimized = !state.isMinimized },
        menu = {
            Item("Quit", onClick = ::exitApplication)
        }
    )
}