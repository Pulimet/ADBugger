package ui.sections

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.*
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Paddings
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard

@Composable
@Preview
fun Numbers(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    ExpandableCard(
        title = "Numbers",
        modifier = Modifier.padding(horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Num(model, coroutineScope, TablerIcons.Number1, 1)
            Num(model, coroutineScope, TablerIcons.Number2, 2)
            Num(model, coroutineScope, TablerIcons.Number3, 3)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Num(model, coroutineScope, TablerIcons.Number4, 4)
            Num(model, coroutineScope, TablerIcons.Number5, 5)
            Num(model, coroutineScope, TablerIcons.Number6, 6)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Num(model, coroutineScope, TablerIcons.Number7, 7)
            Num(model, coroutineScope, TablerIcons.Number8, 8)
            Num(model, coroutineScope, TablerIcons.Number9, 9)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Num(model, coroutineScope, TablerIcons.Number0, 0)
        }
    }
}

@Composable
fun Num(model: AppStore, coroutineScope: CoroutineScope, icon: ImageVector, i: Int) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 4.dp),
        onClick = { model.onNumberClick(coroutineScope, i) },
        description = i.toString()
    )
}
