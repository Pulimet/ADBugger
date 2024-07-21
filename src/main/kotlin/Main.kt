import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import koin.appModule
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import store.AppStore
import ui.MainContent
import ui.theme.MyColors
import ui.widgets.FileDialog

fun main() = application {
    startKoin { modules(appModule()) }

    val windowState = rememberWindowState(
        width = 1200.dp,
        height = 800.dp,
        position = WindowPosition(alignment = Alignment.Center)
    )

    val appStore = koinInject<AppStore>()
    val model = remember { appStore }

    ApplicationTray(windowState)

    Window(
        title = "ADBugger",
        state = windowState,
        onCloseRequest = ::exitApplication,
        onKeyEvent = { model.onKeyEvent(it) }
    ) {
        Surface(color = MyColors.bg) {
            MainContent()
        }

        if (model.state.isFilePickerShown) {
            FileDialog { dir, file -> model.onFilePickerResult(dir, file) }
        }
    }
}
