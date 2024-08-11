package ui.sections.misc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.tabs.Tabs

@Composable
fun MiscPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Tabs(listOf("Tab 1"), showTabs = false) {
                when (it) {
                    0 -> MiscTabA()
                }
            }
        }
    }
}