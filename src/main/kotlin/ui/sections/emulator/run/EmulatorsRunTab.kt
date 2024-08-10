package ui.sections.emulator.run

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.EmulatorCommands
import ui.sections.emulator.run.list.EmulatorsList
import ui.theme.Dimensions
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorsRunTab(model: AppStore = koinInject()) {
    var proxy by remember { mutableStateOf("") }
    var ram by remember { mutableStateOf(0) }
    var latency by remember { mutableStateOf("none") }
    var speed by remember { mutableStateOf("full") }

    val command = EmulatorCommands.getLaunchEmulator("emuName", proxy, ram, latency, speed)

    Column {
        EmulatorsRunTopMenu(
            command,
            onProxyChange = { proxy = it },
            onRamChange = { ram = it },
            onSetProxyClick = { model.setProxy(proxy) },
            onLatencyChange = { latency = it },
            onSpeedChange = { speed = it },
        )

        if (model.state.isEmulatorsLoading) {
            LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
        } else {
            Row(modifier = Modifier.padding(top = 4.dp)) {
                EmulatorsList(Modifier.weight(1f)) {
                    model.onLaunchEmulatorClick(emulatorName = it, proxy, ram, latency, speed)
                }
                EmulatorsRunSideMenu()
            }
        }
    }
}
