package ui.sections.emulator

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun EmulatorsRunTab() {
    Column {
        EmulatorsTopMenu()
        EmulatorsList()
    }
}
