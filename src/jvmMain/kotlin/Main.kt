import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.launch

fun main() = application {
    val state = rememberWindowState(width = 200.dp, height = 200.dp)
    ShowTray(state)
    Window(
        title = "ADBugger",
        state = state,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}

@Composable
@Preview
fun App() {
    var textVal by remember { mutableStateOf("Hello, World!") }
    val coroutineScope = rememberCoroutineScope()
    val adb = Adb()

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { coroutineScope.launch { adb.openAtt() } }) { Text(text = "Open") }
            Button(onClick = { coroutineScope.launch { adb.closeAtt() } }) { Text(text = "Close") }
        }
    }
}

@Composable
private fun ApplicationScope.ShowTray(state: WindowState) {
    val icon = painterResource("sample.png")

    Tray(
        icon = icon,
        onAction = { state.isMinimized = !state.isMinimized },
        menu = {
            Item("Quit", onClick = ::exitApplication)
        }
    )
}

