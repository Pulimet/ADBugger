import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

@Composable
fun FrameWindowScope.TopMenuBar(applicationScope: ApplicationScope) {
    MenuBar {
        Menu("File") {
            Item("Exit", onClick = applicationScope::exitApplication)
        }
    }
}