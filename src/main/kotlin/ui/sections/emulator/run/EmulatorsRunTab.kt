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
import model.RunEmulatorParams
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.EmulatorCommands
import ui.sections.emulator.run.list.EmulatorsList
import ui.theme.Dimensions
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorsRunTab(model: AppStore = koinInject()) {
    var params by remember { mutableStateOf(RunEmulatorParams()) }
    Column {
        EmulatorsRunTopMenu(
            params = params,
            onParamsChanged = {params = it},
            onSetProxyClick = { model.setProxy(params.proxy) },
        )

        if (model.state.isEmulatorsLoading) {
            LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
        } else {
            Row(modifier = Modifier.padding(top = 4.dp)) {
                EmulatorsList(Modifier.weight(1f)) {
                    model.onLaunchEmulatorClick(emulatorName = it, params)
                }
                EmulatorsRunSideMenu()
            }
        }
    }
}
