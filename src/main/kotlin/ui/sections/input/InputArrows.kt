package ui.sections.input

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.octicons.ArrowDown24
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.ArrowUp24
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.CardX
import ui.widgets.buttons.BtnIcon

@Composable
fun InputArrows(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    CardX(modifier = modifier) {
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
                    onClick = { model.onUpClick() },
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
                    onClick = { model.onLeftClick() },
                    description = "Left"
                )
                BtnIcon(
                    icon = Octicons.ArrowDown24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onDownClick() },
                    description = "Down"
                )
                BtnIcon(
                    icon = Octicons.ArrowRight24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onRightClick() },
                    description = "Right"
                )
            }
        }
    }
}
