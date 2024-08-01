package ui.sections.logcat

import adb.Commands
import adb.Logcat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.PlayerPlay
import compose.icons.tablericons.PlayerStop
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.MyColors
import ui.widgets.Dropdown
import ui.widgets.buttons.BtnIcon


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

@Composable
fun LogcatTopBar(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val isLogcatRunning = model.state.isLogcatRunning
    val selectedTargetsList = model.state.selectedTargetsList

    val selectedBuffer = remember { mutableStateOf("default") }
    val selectedFormat = remember { mutableStateOf("threadtime") }
    val command = Commands.getLogCatCommand(
        if (selectedTargetsList.isEmpty()) "" else selectedTargetsList[0],
        selectedBuffer.value,
        selectedFormat.value
    )

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().background(MyColors.bg).padding(horizontal = 8.dp)
    ) {

        Dropdown(
            options = Logcat.bufferList,
            optionsDetails = Logcat.bufferListDetails,
            title = "Buffer",
            onOptionSelected = { selectedBuffer.value = it }
        )
        Dropdown(
            options = Logcat.formatLst,
            title = "Format",
            onOptionSelected = { selectedFormat.value = it }
        )

        BtnIcon(
            icon = if (isLogcatRunning) TablerIcons.PlayerStop else TablerIcons.PlayerPlay,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = selectedTargetsList.size == 1,
            onClick = {
                model.startStopLogcat(selectedBuffer.value, selectedFormat.value)
            },
            description = if (isLogcatRunning) "Stop" else "Start"
        )

        Text(
            text = command,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
        )

    }
}

