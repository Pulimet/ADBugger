package ui.sections

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.*
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimens
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
@Preview
fun Numbers(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(Dimens.selectedPagePadding),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().height(160.dp)
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
        description = i.toString(),
        showTooltip = false
    )
}
