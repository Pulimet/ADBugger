package ui.sections.input

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.*
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.CardX
import ui.widgets.buttons.BtnIcon

@Composable
fun InputNumbers(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(TablerIcons.Number1, 1)
                Num(TablerIcons.Number2, 2)
                Num(TablerIcons.Number3, 3)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(TablerIcons.Number4, 4)
                Num(TablerIcons.Number5, 5)
                Num(TablerIcons.Number6, 6)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(TablerIcons.Number7, 7)
                Num(TablerIcons.Number8, 8)
                Num(TablerIcons.Number9, 9)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(TablerIcons.Number0, 0)
            }
        }
    }
}

@Composable
private fun Num(icon: ImageVector, i: Int, model: AppStore = koinInject()) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 4.dp),
        onClick = { model.onNumberClick(i) },
        description = i.toString(),
        showTooltip = false
    )
}
