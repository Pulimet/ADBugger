package ui.sections.adblogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Copy
import compose.icons.evaicons.fill.Trash2
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnWithText
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

@Composable
fun AdbLogsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val isSelectedPageNotLogs = model.state.menuItemSelected != MenuItemId.LOGS
    val logsList = model.state.logs
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard

    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = if (isSelectedPageNotLogs) {
            modifier.padding(
                Dimensions.selectedPagePadding,
                0.dp,
                Dimensions.selectedPagePadding,
                Dimensions.selectedPagePadding
            )
        } else {
            modifier.padding(Dimensions.selectedPagePadding)
        },
    ) {
        Box(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            if (logsList.isEmpty()) {
                Text(
                    text = "No logs yet. Start by running a command.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = Dimensions.titleFontSize
                )
            }

            AdbLogList(clipboard)

            if (logsList.isNotEmpty()) {
                Row(modifier = Modifier.align(Alignment.TopEnd)) {
                    BtnWithText(
                        icon = EvaIcons.Fill.Copy,
                        onClick = { copyAllLogsToClipboard(clipboard, logsList) },
                        description = "Copy All",
                        width = 60.dp,
                    )
                    BtnWithText(
                        icon = EvaIcons.Fill.Trash2,
                        onClick = { model.clearLogs() },
                        description = "Clear logs",
                        width = 60.dp,
                    )
                }
            }
        }
    }
}

private fun copyAllLogsToClipboard(clipboard: Clipboard, logsList: ArrayList<String>) {
    val logs = logsList.joinToString("\n")
    clipboard.setContents(StringSelection(logs), null)
}