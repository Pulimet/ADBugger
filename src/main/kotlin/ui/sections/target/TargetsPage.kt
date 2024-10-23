package ui.sections.target

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.target_save_selected_tab_key
import net.alexandroid.adbugger.adbugger.generated.resources.target_tabs
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.tabs.Tabs

@Composable
fun TargetsPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Tabs(
                stringArrayResource(Res.array.target_tabs),
                saveSelectedTabKey = stringResource(Res.string.target_save_selected_tab_key)
            ) {
                when (it) {
                    0 -> TargetSelectionTab()
                    1 -> TargetConnectionTab()
                }
            }

        }
    }
}