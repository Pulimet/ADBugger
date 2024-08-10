package ui.sections.emulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorsRunTab(model: AppStore = koinInject()) {
    var proxy by remember { mutableStateOf("") }
    var num by remember { mutableStateOf(0) }

    Column {
        EmulatorsRunTopMenu(
            proxy,
            num,
            onProxyChange = { proxy = it },
            onRamChange = { num = it }
        )

        if (model.state.isEmulatorsLoading) {
            LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
        } else {
            Row(modifier = Modifier.padding(top = 4.dp)) {
                EmulatorsList(Modifier.weight(1f)) {
                    model.onLaunchEmulatorClick(emulatorName = it, proxy, num)
                }
                EmulatorsRunSideMenu()
            }
        }
    }
}
