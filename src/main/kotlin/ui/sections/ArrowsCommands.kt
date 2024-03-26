package ui.sections

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.octicons.ArrowDown24
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.ArrowUp24
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimens
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
@Preview
fun ArrowsCommands(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(Dimens.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPadding)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                BtnIcon(
                    icon = Octicons.ArrowUp24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onUpClick(coroutineScope) },
                    description = "Up"
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                BtnIcon(
                    icon = Octicons.ArrowLeft24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onLeftClick(coroutineScope) },
                    description = "Left"
                )
                BtnIcon(
                    icon = Octicons.ArrowDown24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onDownClick(coroutineScope) },
                    description = "Down"
                )
                BtnIcon(
                    icon = Octicons.ArrowRight24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onRightClick(coroutineScope) },
                    description = "Right"
                )
            }
        }
    }
}
