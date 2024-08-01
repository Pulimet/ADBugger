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

@Composable
fun LogcatTopBar(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val isLogcatRunning = model.state.isLogcatRunning
    val selectedTargetsList = model.state.selectedTargetsList

    val selectedBuffer = remember { mutableStateOf("default") }
    val selectedFormat = remember { mutableStateOf("threadtime") }
    val selectedPriorityLevel = remember { mutableStateOf("V") }

    val command = Commands.getLogCatCommand(
        if (selectedTargetsList.isEmpty()) "" else selectedTargetsList[0],
        selectedBuffer.value,
        selectedFormat.value,
        selectedPriorityLevel.value
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
            optionsDetails = Logcat.formatLstDetails,
            title = "Format",
            onOptionSelected = { selectedFormat.value = it }
        )
        Dropdown(
            options = Logcat.priorityLevelList,
            optionsDetails = Logcat.priorityLevelListDetails,
            title = "Level",
            onOptionSelected = { selectedPriorityLevel.value = it }
        )

        BtnIcon(
            icon = if (isLogcatRunning) TablerIcons.PlayerStop else TablerIcons.PlayerPlay,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = selectedTargetsList.size == 1,
            onClick = {
                model.startStopLogcat(selectedBuffer.value, selectedFormat.value, selectedPriorityLevel.value)
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

    //  TODO Add tag filter
    //  TODO Add query filter
}

