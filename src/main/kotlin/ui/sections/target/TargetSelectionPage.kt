package ui.sections.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.tabs.Tabs

@Composable
fun TargetSelectionPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Tabs(
                listOf("Select", "Connect"),
                saveSelectedTabKey = "TargetTabs"
            ) {
                when (it) {
                    0 -> TargetSelectionTab()
                    1 -> TargetConnectionTab()
                }
            }

        }
    }
}