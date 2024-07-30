package ui.sections.logcat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.PlayerPlay
import compose.icons.tablericons.PlayerStop
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.buttons.BtnIcon

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
fun LogcatTopBar(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val isLogcatRunning = model.state.isLogcatRunning
    val selectedTargetsList = model.state.selectedTargetsList

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {

        BtnIcon(
            icon = if (isLogcatRunning) TablerIcons.PlayerStop else TablerIcons.PlayerPlay,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = selectedTargetsList.size == 1,
            onClick = { model.startStopLogcat() },
            description = if (isLogcatRunning) "Stop" else "Start"
        )
    }
}
