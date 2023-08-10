package ui.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Paddings
import ui.widgets.ExpandableCard

@Composable
fun PackageListAndCommands(
    coroutineScope: CoroutineScope,
    model: AppStore,
    isPackageSelected: Boolean,
) {
    ExpandableCard(
        title = "Apps Control / Package selection",
        modifier = Modifier.padding(
            horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical
        )
    ) {
        PackageListSection(
            coroutineScope, model, modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp)
        )
        PackageCommands(isPackageSelected, model, coroutineScope)
    }
}
