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
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_clear_logs
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_copy_all
import org.jetbrains.compose.resources.stringResource
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
                description = stringResource(Res.string.logcat_btn_copy_all),
                width = buttonWidth,
            )
            BtnWithText(
                icon = EvaIcons.Fill.Trash2,
                onClick = { onClearClick() },
                description = stringResource(Res.string.logcat_btn_clear_logs),
                width = buttonWidth,
            )
        }
    }
}
