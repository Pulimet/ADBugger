package ui.sections.emulator.run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.RunEmulatorParams
import terminal.commands.EmulatorCommands
import ui.theme.Dimensions
import ui.theme.MyColors


@Composable
fun EmulatorsRunTopMenu(
    params: RunEmulatorParams,
    onParamsChanged: (RunEmulatorParams) -> Unit,
    onSetProxyClick: () -> Unit
) {

    val command = EmulatorCommands.getLaunchEmulator("emuName", params)

    Column(
        modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(top = 4.dp, bottom = 6.dp, start = 16.dp)
    ) {
        Row {
            TextFieldWithDescription(
                label = "Proxy (used on launch)",
                description = "ex: server:port\nusername:password@server:port",
                minWidth = 170.dp,
            ) { onParamsChanged(params.copy(proxy = it)) }

            TextFieldWithDescription(
                label = "RAM",
                description = "Set RAM on launch\n1536(default) - 8192 (MBs)",
                maxLength = 4,
                keyboardType = KeyboardType.Number,
                minWidth = 120.dp,
                modifier = Modifier.padding(start = 12.dp)
            ) { onParamsChanged(params.copy(ram = it.toIntOrNull() ?: 0)) }

            DropDownWithDescription(
                options = EmulatorCommands.networkDelayList,
                optionsDetails = EmulatorCommands.networkDelayListDetails,
                title = "Latency",
                description = "Sets network latency emulation",
                modifier = Modifier.width(115.dp).padding(start = 6.dp),
                minWidth = 115.dp
            ) { onParamsChanged(params.copy(latency = it)) }


            DropDownWithDescription(
                options = EmulatorCommands.networkSpeedList,
                optionsDetails = EmulatorCommands.networkSpeedListDetails,
                title = "Speed",
                description = "Sets the network speed emulation",
                modifier = Modifier.width(115.dp).padding(start = 6.dp),
                minWidth = 115.dp,
            ) { onParamsChanged(params.copy(speed = it)) }

            DropDownWithDescription(
                options = EmulatorCommands.quickBootList,
                optionsDetails = EmulatorCommands.quickBootListDetails,
                title = "Quick Boot",
                description = "Allows to disable quick boot saving/loading or both",
                modifier = Modifier.width(184.dp).padding(start = 6.dp),
                minWidth = 184.dp,
            ) { onParamsChanged(params.copy(quickBoot = it)) }

        }
        Row {
            EmulatorsProxyButtons { onSetProxyClick() }
            CheckboxWithDescription(
                title = "Is disabled?",
                description = "Disables the boot anim-\nation on emulator startup.",
                modifier = Modifier.padding(start = 8.dp)
            ) { onParamsChanged(params.copy(bootAnimDisabled = it)) }
        }
        Text(
            text = command,
            lineHeight = 12.sp,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}