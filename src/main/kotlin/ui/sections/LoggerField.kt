package ui.sections

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Trash2
import store.AppStore
import ui.navigation.MenuItemId
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnWithText

@Composable
fun LoggerField(model: AppStore, modifier: Modifier = Modifier) {
    val stateVertical = rememberScrollState(0)
    val isSelectedPageNotLogs = model.state.menuItemSelected != MenuItemId.LOGS
    val logsList = model.state.logs
    var logs = ""
    logsList.forEachIndexed { index, log ->
        logs += log
        if (index != logsList.size - 1) logs += "\n"
    }

    LaunchedEffect(logsList) {
        if (logsList.isNotEmpty()) {
            stateVertical.animateScrollTo(stateVertical.maxValue)
        }
    }

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
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(stateVertical)) {
                SelectionContainer {
                    Text(
                        text = logs,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            if (logsList.isNotEmpty()) {
                BtnWithText(
                    icon = EvaIcons.Fill.Trash2,
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { model.clearLogs() },
                    description = "Clear logs",
                    width = 60.dp,
                )
            }
            if (logsList.size > 3) {
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(top = 16.dp).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical)
                )
            }
        }
    }
}
