package ui.widgets.list

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Copy
import compose.icons.evaicons.fill.Trash2
import ui.widgets.buttons.BtnWithText
import utils.Clipboard
import java.awt.Toolkit

@Composable
fun CopyAllAndClearBox(
    list: List<String>,
    modifier: Modifier = Modifier,
    buttonWidth: Dp = 60.dp,
    onClearClick: () -> Unit,
) {
    if (list.isNotEmpty()) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard

        Column(modifier = modifier) {
            BtnWithText(
                icon = EvaIcons.Fill.Copy,
                onClick = { Clipboard.copyAllLogsToClipboard(clipboard, list) },
                description = "Copy All",
                width = buttonWidth,
            )
            BtnWithText(
                icon = EvaIcons.Fill.Trash2,
                onClick = { onClearClick() },
                description = "Clear logs",
                width = buttonWidth,
            )
        }
    }
}
