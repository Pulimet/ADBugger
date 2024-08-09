package ui.sections.emulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.tabs.Tabs

@Composable
fun EmulatorsPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Tabs(listOf("Run", "Add", "Create")) {
                when (it) {
                    0 -> EmulatorsRunTab()
                    1 -> EmulatorsAddTab()
                    3 -> EmulatorsCreateTab()
                }
            }

        }
    }
}
