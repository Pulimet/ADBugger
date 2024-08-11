package ui.sections.emulator.run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.RunEmulatorParams
import org.koin.compose.koinInject
import pref.preference
import store.AppStore
import terminal.commands.EmulatorCommands
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.buttons.BtnWithTextSmall


@Composable
fun EmulatorsRunTopMenu(params: RunEmulatorParams, onParamsChanged: (RunEmulatorParams) -> Unit) {

    var showDescriptionPref: Boolean by preference("Btn_ShowDescription", false)
    var showDescription by remember { mutableStateOf(showDescriptionPref) }

    Column(
        modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(top = 4.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
    ) {
        FirstRow(showDescription, params, onParamsChanged)
        Divider(thickness = 1.dp)
        SecondRow(showDescription, params, onParamsChanged)
        Divider(thickness = 1.dp)
        BottomRow(showDescription, params, onParamsChanged) {
            showDescriptionPref = it
            showDescription = it
        }
    }
}

@Composable
private fun FirstRow(
    showDescription: Boolean,
    params: RunEmulatorParams,
    onParamsChanged: (RunEmulatorParams) -> Unit,
) {
    Row {
        TextFieldWithDescription(
            label = "Proxy (used on launch)",
            description = "ex: server:port\nusername:password@server:port",
            minWidth = 170.dp,
            showDescription = showDescription
        ) { onParamsChanged(params.copy(proxy = it)) }

        TextFieldWithDescription(
            label = "RAM",
            description = "Set RAM on launch\n1536(default) - 8192 (MBs)",
            maxLength = 4,
            keyboardType = KeyboardType.Number,
            minWidth = 120.dp,
            modifier = Modifier.padding(start = 12.dp),
            showDescription = showDescription
        ) { onParamsChanged(params.copy(ram = it.toIntOrNull() ?: 0)) }

        DropDownWithDescription(
            options = EmulatorCommands.networkDelayList,
            optionsDetails = EmulatorCommands.networkDelayListDetails,
            title = "Latency",
            description = "Sets network latency emulation",
            modifier = Modifier.width(115.dp).padding(start = 6.dp),
            minWidth = 115.dp,
            showDescription = showDescription
        ) { onParamsChanged(params.copy(latency = it)) }

        DropDownWithDescription(
            options = EmulatorCommands.networkSpeedList,
            optionsDetails = EmulatorCommands.networkSpeedListDetails,
            title = "Speed",
            description = "Sets the network speed emulation",
            modifier = Modifier.width(115.dp).padding(start = 6.dp),
            minWidth = 115.dp,
            showDescription = showDescription
        ) { onParamsChanged(params.copy(speed = it)) }

        DropDownWithDescription(
            options = EmulatorCommands.quickBootList,
            optionsDetails = EmulatorCommands.quickBootListDetails,
            title = "Quick Boot",
            description = "Allows to disable quick boot saving/loading or both",
            modifier = Modifier.width(184.dp).padding(start = 6.dp),
            minWidth = 184.dp,
            showDescription = showDescription
        ) { onParamsChanged(params.copy(quickBoot = it)) }
    }
}

@Composable
private fun SecondRow(
    showDescription: Boolean,
    params: RunEmulatorParams,
    onParamsChanged: (RunEmulatorParams) -> Unit,
    model: AppStore = koinInject(),
) {
    Row(modifier = Modifier.padding(top = 4.dp)) {
        EmulatorsProxyButtons(showDescription) { model.setProxy(params.proxy) }

        CheckboxWithDescription(
            title = "Animation",
            description = "Disables the boot anim-\nation on emulator startup",
            modifier = Modifier.padding(start = 8.dp),
            showDescription = showDescription
        ) { onParamsChanged(params.copy(bootAnimEnabled = it)) }

        CheckboxWithDescription(
            title = "Audio",
            description = "Disables audio support\non emulator startup",
            modifier = Modifier.padding(start = 8.dp),
            showDescription = showDescription
        ) { onParamsChanged(params.copy(audioEnabled = it)) }

        DropDownWithDescription(
            options = EmulatorCommands.touchList,
            optionsDetails = EmulatorCommands.touchListDetails,
            title = "Touch mode",
            description = "Sets emulated \n touch screen mode",
            modifier = Modifier.width(130.dp).padding(start = 6.dp),
            minWidth = 130.dp,
            showDescription = showDescription
        ) { onParamsChanged(params.copy(touchMode = it)) }

        TextFieldWithDescription(
            label = "Data partition",
            description = "Specifies the system data\npartition size (default: 800 MBs)",
            maxLength = 4,
            keyboardType = KeyboardType.Number,
            minWidth = 160.dp,
            modifier = Modifier.padding(start = 12.dp),
            showDescription = showDescription
        ) { onParamsChanged(params.copy(partition = it.toIntOrNull() ?: 0)) }
    }
}

@Composable
private fun BottomRow(
    showDescription: Boolean,
    params: RunEmulatorParams,
    onParamsChanged: (RunEmulatorParams) -> Unit,
    onShowDescriptionChanged: (Boolean) -> Unit,
) {
    val command = EmulatorCommands.getLaunchEmulator("emuName", params)
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
        Text(
            text = command,
            lineHeight = 12.sp,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 8.dp).weight(1f)
        )

        Row {
            BtnWithTextSmall(
                icon = Icons.Default.Info,
                toggle = showDescription,
                description = "Info",
                width = 60.dp
            ) { onShowDescriptionChanged(!showDescription) }

            BtnWithTextSmall(
                icon = Icons.Rounded.Clear,
                description = "Reset",
                width = 60.dp
            ) { onParamsChanged(RunEmulatorParams()) }
        }
    }
}