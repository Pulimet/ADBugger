package ui.sections.input

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
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun InputArrows(
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(horizontal = Dimensions.selectedPagePadding, vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.Center
        ) {
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
