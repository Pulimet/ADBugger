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
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_launch_props
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.sections.emulator.run.list.EmulatorsList
import ui.theme.Dimensions
import ui.widgets.ExpandableCard
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorsRunTab(model: AppStore = koinInject()) {
    var params by remember { mutableStateOf(RunEmulatorParams()) }

    Column {
        ExpandableCard(title = stringResource(Res.string.emulators_launch_props)) {
            EmulatorsRunTopMenu(params = params) { params = it }
        }

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
