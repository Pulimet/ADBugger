package ui.sections.misc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.misc_tab_1
import org.jetbrains.compose.resources.stringResource
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.tabs.Tabs

@Composable
fun MiscPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Tabs(listOf(stringResource(Res.string.misc_tab_1)), showTabs = false) {
                when (it) {
                    0 -> MiscTabA()
                }
            }
        }
    }
}