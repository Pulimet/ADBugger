package ui.sections.emulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorsPage(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            if (model.state.isEmulatorsLoading) {
                LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxSize())
            } else {
                Content()
            }
        }
    }
}

@Composable
private fun Content() {
    Column {
        EmulatorsTopMenu()
        EmulatorsList()
    }
}
