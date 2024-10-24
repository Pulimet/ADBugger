package ui.sections.emulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_save_selected_tab_key
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_tabs
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import ui.sections.emulator.add.EmulatorsCreateTab
import ui.sections.emulator.create.EmulatorsInstallTab
import ui.sections.emulator.run.EmulatorsRunTab
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.tabs.Tabs

@Composable
fun EmulatorsPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Tabs(
                stringArrayResource(Res.array.emulators_tabs),
                saveSelectedTabKey = stringResource(Res.string.emulators_save_selected_tab_key),
                showTabs = false
            ) {
                when (it) {
                    0 -> EmulatorsRunTab()
                    1 -> EmulatorsCreateTab()
                    2 -> EmulatorsInstallTab()
                }
            }

        }
    }
}
