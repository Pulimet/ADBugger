package ui.sections.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.*
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun InputNumbers(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(horizontal = Dimensions.selectedPagePadding, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(coroutineScope, TablerIcons.Number1, 1)
                Num(coroutineScope, TablerIcons.Number2, 2)
                Num(coroutineScope, TablerIcons.Number3, 3)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(coroutineScope, TablerIcons.Number4, 4)
                Num(coroutineScope, TablerIcons.Number5, 5)
                Num(coroutineScope, TablerIcons.Number6, 6)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(coroutineScope, TablerIcons.Number7, 7)
                Num(coroutineScope, TablerIcons.Number8, 8)
                Num(coroutineScope, TablerIcons.Number9, 9)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Num(coroutineScope, TablerIcons.Number0, 0)
            }
        }
    }
}

@Composable
private fun Num(coroutineScope: CoroutineScope, icon: ImageVector, i: Int, model: AppStore = koinInject()) {
    BtnIcon(
        icon = icon,
        modifier = Modifier.padding(horizontal = 4.dp),
        onClick = { model.onNumberClick(coroutineScope, i) },
        description = i.toString(),
        showTooltip = false
    )
}
