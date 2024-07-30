package ui.sections.logcat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.HoverButton

@Composable
fun LogcatCommands(model: AppStore = koinInject()) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        HoverButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.startLogcat() },
            enabled = true,
            text = "Start Logcat"
        )
        HoverButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.stopLogcat() },
            enabled = true,
            text = "Stop Logcat"
        )
        HoverButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            //   adb logcat -d > [path_to_file] // Save the logcat output to a file on the local system.
            onClick = {},
            enabled = true,
            text = "Save to file (-)"
        )
        HoverButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            //  adb bugreport > [path_to_file] // Will dump the whole device information like dumpstate, dumpsys and logcat output.
            onClick = {},
            enabled = true,
            text = "Bugreport (-)"
        )
        HoverButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {/* adb logcat -c */ },
            enabled = true,
            text = "Clear logcat (-)"
        )
    }
}
