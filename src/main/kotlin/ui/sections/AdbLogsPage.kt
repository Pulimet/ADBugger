package ui.sections

import androidx.compose.foundation.layout.Box
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
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.sidebar.MenuItemId
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.list.CopyAllAndClearBox
import ui.widgets.list.ListX

@Composable
fun AdbLogsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val isSelectedPageNotLogs = model.state.menuItemSelected != MenuItemId.LOGS
    val logsList = model.state.adbLogs

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

            ListX(model.state.adbLogs)
            CopyAllAndClearBox(logsList, Modifier.align(Alignment.TopEnd)) {
                model.clearAdbLogs()
            }
        }
    }
}
