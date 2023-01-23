import adb.Adb
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.launch

private const val MY_ATT_PACKAGE = "com.att.myWirelessTest"

fun main() = application {
    val state = rememberWindowState(width = 200.dp, height = 200.dp)
    ApplicationTray(state)
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

    LaunchedEffect(Unit) {
        coroutineScope.launch { adb.startAdbInteract() }
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { coroutineScope.launch {
                adb.openPackage(MY_ATT_PACKAGE) }
            }) { Text(text = "Open") }

            Button(onClick = { coroutineScope.launch {
                adb.closePackage(MY_ATT_PACKAGE) }
            }) { Text(text = "Close") }

            LoadingSpinner()
        }
    }
}

@Composable
fun LoadingSpinner() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .defaultMinSize(minWidth = 32.dp, minHeight = 32.dp)
        )
    }
}
