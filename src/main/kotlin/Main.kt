import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import koin.appModule
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import pref.preference
import store.AppStore
import ui.MainContent
import ui.theme.MyColors
import ui.widgets.FileDialog

fun main() = application {
    startKoin { modules(appModule()) }

    var width: Float by preference("WindowWidth", 1200f)
    var height: Float by preference("WindowHeight", 800f)
    var x: Float by preference("WindowX", 0f)
    var y: Float by preference("WindowY", 0f)

    val windowPosition = if (x != 0f && y != 0f) WindowPosition(
        x.dp,
        y.dp
    ) else {
        WindowPosition(alignment = Alignment.Center)
    }

    val windowState = rememberWindowState(
        width = width.dp,
        height = height.dp,
        position = windowPosition
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
        LaunchedEffect(windowState) {
            snapshotFlow { windowState.size }.onEach {
                width = it.width.value
                height = it.height.value
            }.launchIn(this)
            snapshotFlow { windowState.position }.filter { it.isSpecified }.onEach {
                x = it.x.value
                y = it.y.value
            }.launchIn(this)
        }

        Surface(color = MyColors.bg) {
            MainContent()
        }

        if (model.state.isFilePickerShown) {
            FileDialog { dir, file -> model.onFilePickerResult(dir, file) }
        }

        TopMenuBar(this@application)
    }


}
