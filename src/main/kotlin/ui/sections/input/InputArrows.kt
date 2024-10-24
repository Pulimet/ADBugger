package ui.sections.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.octicons.ArrowDown24
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.ArrowUp24
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_down
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_left
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_right
import net.alexandroid.adbugger.adbugger.generated.resources.input_btn_up
import org.jetbrains.compose.resources.stringResource
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
                    description = stringResource(Res.string.input_btn_up)
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
                    description = stringResource(Res.string.input_btn_left)
                )
                BtnIcon(
                    icon = Octicons.ArrowDown24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onDownClick() },
                    description = stringResource(Res.string.input_btn_down)
                )
                BtnIcon(
                    icon = Octicons.ArrowRight24,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { model.onRightClick() },
                    description = stringResource(Res.string.input_btn_right)
                )
            }
        }
    }
}
