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
import ui.widgets.buttons.HoverButton

// Buffer selection, default: -b main,system,crash,kernel.
//radio: Views the buffer that contains radio/telephony related messages.
//events: Views the interpreted binary system event buffer messages.
//main: Views the main log buffer (default), which doesn't contain system and crash log messages.
//system: Views the system log buffer (default).
//crash: Views the crash log buffer (default).
//all: Views all buffers.
//default: Reports main, system, and crash buffers.

// Tags selection, default: *:V

// adb logcat "*:I"
// adb logcat "tagName:I"
// adb logcat ActivityManager:I MyApp:D *:S

// The priority is one of the following character values, ordered from lowest to highest priority:
//V: Verbose (lowest priority)
//D: Debug
//I: Info
//W: Warning
//E: Error
//F: Fatal
//S: Silent (highest priority, where nothing is ever printed)

// adb logcat "*:W" -v brief output
// Only one could be selected
//brief: Displays priority, tag, and PID of the process issuing the message.
//long: Displays all metadata fields and separate messages with blank lines.
//process: Displays PID only.
//raw: Displays the raw log message with no other metadata fields.
//tag: Displays the priority and tag only.
//thread: A legacy format that shows priority, PID, and TID of the thread issuing the message.
//threadtime (default): Displays the date, invocation time, priority, tag, PID, and TID of the thread issuing the message.
//time: Displays the date, invocation time, priority, tag, and PID of the process issuing the message.

@Composable
fun LogcatCommands(model: AppStore = koinInject()) {
    val isLogcatRunning = model.state.isLogcatRunning

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        HoverButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.startStopLogcat() },
            enabled = true,
            text = if (isLogcatRunning) "Stop" else "Start"
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
