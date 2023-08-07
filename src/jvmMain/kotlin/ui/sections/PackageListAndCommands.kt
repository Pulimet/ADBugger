package ui.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.widgets.ExpandableCard

@Composable
fun PackageListAndCommands(
    coroutineScope: CoroutineScope,
    model: AppStore,
    state: AppStore.AppState,
    isPackageSelected: Boolean,
) {
    ExpandableCard(
        title = "Apps Control",
        modifier = Modifier.padding(
            horizontal = 12.dp, vertical = 4.dp
        )
    ) {
        PackageListSection(
            coroutineScope, model, state,
            modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp)
        )
        PackageCommands(isPackageSelected, model, coroutineScope)
    }
}
