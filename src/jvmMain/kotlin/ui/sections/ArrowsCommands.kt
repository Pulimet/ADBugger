package ui.sections

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.LineaIcons
import compose.icons.lineaicons.Arrows
import compose.icons.lineaicons.arrows.`KeyboardDown-28`
import compose.icons.lineaicons.arrows.KeyboardLeft
import compose.icons.lineaicons.arrows.KeyboardRight
import compose.icons.lineaicons.arrows.KeyboardUp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard

@Composable
@Preview
fun ArrowsCommands(
    model: AppStore,
    coroutineScope: CoroutineScope,
    isDeviceSelected: Boolean
) {
    ExpandableCard(
        title = "Keyboard Arrows",
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp
        )) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            BtnIcon(
                icon = LineaIcons.Arrows.KeyboardUp,
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
                icon = LineaIcons.Arrows.KeyboardLeft,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onLeftClick(coroutineScope) },
                description = "Left"
            )
            BtnIcon(
                icon = LineaIcons.Arrows.`KeyboardDown-28`,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onDownClick(coroutineScope) },
                description = "Down"
            )
            BtnIcon(
                icon = LineaIcons.Arrows.KeyboardRight,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onRightClick(coroutineScope) },
                description = "Right"
            )
        }

    }
}
