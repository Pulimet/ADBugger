package ui.widgets.tabs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TabsLabels(tabLabels: List<String>, selectedIndex: Int, onClick: (Int) -> Unit = {}) {
    Row(modifier = Modifier.fillMaxWidth()) {
        tabLabels.forEachIndexed { index, label ->
            val isSelected = selectedIndex == index
            val weightModifier = Modifier.weight(1 / tabLabels.size.toFloat())
            TabLabel(label, isSelected, weightModifier, onClick = { onClick(index) })
        }
    }
}
