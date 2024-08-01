package ui.sections.logcat

import adb.Commands
import adb.Logcat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.PlayerPlay
import compose.icons.tablericons.PlayerStop
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.MyColors
import ui.widgets.Dropdown
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnIcon

@Composable
fun LogcatTopBar(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val isLogcatRunning = model.state.isLogcatRunning
    val selectedTargetsList = model.state.selectedTargetsList

    val selectedBuffer = remember { mutableStateOf("default") }
    val selectedFormat = remember { mutableStateOf("threadtime") }
    val selectedPriorityLevel = remember { mutableStateOf("V") }
    var tagTextField by remember { mutableStateOf(TextFieldValue("")) }

    val command = Commands.getLogCatCommand(
        if (selectedTargetsList.isEmpty()) "" else selectedTargetsList[0],
        selectedBuffer.value,
        selectedFormat.value,
        selectedPriorityLevel.value,
        tagTextField.text
    )

    Column(modifier = modifier.fillMaxWidth().background(MyColors.bg).padding(horizontal = 8.dp, vertical = 4.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Dropdown(options = Logcat.bufferList,
                optionsDetails = Logcat.bufferListDetails,
                title = "Buffer",
                onOptionSelected = { selectedBuffer.value = it })
            Dropdown(options = Logcat.formatLst,
                optionsDetails = Logcat.formatLstDetails,
                title = "Format",
                onOptionSelected = { selectedFormat.value = it })
            Dropdown(options = Logcat.priorityLevelList,
                optionsDetails = Logcat.priorityLevelListDetails,
                title = "Level",
                onOptionSelected = { selectedPriorityLevel.value = it })

            TextFieldX(
                value = tagTextField,
                onValueChange = { newText -> tagTextField = newText },
                label = "Tag",
            )

            BtnIcon(
                icon = if (isLogcatRunning) TablerIcons.PlayerStop else TablerIcons.PlayerPlay,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = selectedTargetsList.size == 1,
                onClick = {
                    model.startStopLogcat(
                        selectedBuffer.value,
                        selectedFormat.value,
                        selectedPriorityLevel.value,
                        tagTextField.text
                    )
                },
                description = if (isLogcatRunning) "Stop" else "Start"
            )
        }

        Text(
            text = command,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )
    }

    //  TODO Add query filter
}
