package ui.sections.logcat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.PlayerPlay
import compose.icons.tablericons.PlayerStop
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_start
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_stop
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_dropdown_buffer
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_dropdown_format
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_dropdown_level
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_label_search
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_label_tag_
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.LogcatCommands
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.Dropdown
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnIcon

@Composable
fun LogcatTopBar(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject(),
    onSearchQueryChange: (String) -> Unit
) {
    val isLogcatRunning = model.state.isLogcatRunning
    val selectedTargetsList = model.state.selectedTargetsList

    var selectedBuffer by remember { mutableStateOf("default") }
    var selectedFormat by remember { mutableStateOf("threadtime") }
    var selectedPriorityLevel by remember { mutableStateOf("V") }
    var tagTextField by remember { mutableStateOf(TextFieldValue("")) }
    var searchTextField by remember { mutableStateOf(TextFieldValue("")) }

    val command = LogcatCommands.getLogCatCommand(
        if (selectedTargetsList.isEmpty()) "" else selectedTargetsList[0],
        selectedBuffer,
        selectedFormat,
        selectedPriorityLevel,
        tagTextField.text
    )

    Column(
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Dropdown(
                options = LogcatCommands.bufferList,
                optionsDetails = LogcatCommands.bufferListDetails,
                title = stringResource(Res.string.logcat_dropdown_buffer),
                onOptionSelected = { selectedBuffer = it },
                contentModifier = Modifier.background(MyColors.bg)
            )
            Dropdown(
                options = LogcatCommands.formatLst,
                optionsDetails = LogcatCommands.formatLstDetails,
                title = stringResource(Res.string.logcat_dropdown_format),
                onOptionSelected = { selectedFormat = it },
                contentModifier = Modifier.background(MyColors.bg)
            )
            Dropdown(
                options = LogcatCommands.priorityLevelList,
                optionsDetails = LogcatCommands.priorityLevelListDetails,
                title = stringResource(Res.string.logcat_dropdown_level),
                onOptionSelected = { selectedPriorityLevel = it },
                contentModifier = Modifier.background(MyColors.bg)
            )

            TextFieldX(
                value = tagTextField,
                onValueChange = { newText -> tagTextField = newText },
                padding = PaddingValues(top = 8.dp),
                label = stringResource(Res.string.logcat_label_tag_),
                modifier = Modifier.background(MyColors.bg).padding(bottom = 8.dp)
            )

            TextFieldX(
                value = searchTextField,
                onValueChange = { newText ->
                    searchTextField = newText
                    onSearchQueryChange(newText.text)
                },
                padding = PaddingValues(top = 8.dp),
                label = stringResource(Res.string.logcat_label_search),
                modifier = Modifier.background(MyColors.bg).padding(bottom = 8.dp, start = 16.dp)
            )

            BtnIcon(
                icon = if (isLogcatRunning) TablerIcons.PlayerStop else TablerIcons.PlayerPlay,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = selectedTargetsList.size == 1,
                onClick = {
                    model.startStopLogcat(
                        selectedBuffer,
                        selectedFormat,
                        selectedPriorityLevel,
                        tagTextField.text
                    )
                },
                description = stringResource(if (isLogcatRunning) Res.string.logcat_btn_stop else Res.string.logcat_btn_start)
            )
        }

        Text(
            text = command,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )
    }
}
