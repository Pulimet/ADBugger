package ui.sections

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Trash2
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun LoggerField(model: AppStore) {
    val stateVertical = rememberScrollState(0)

    val logsList = model.state.logs
    var logs = ""
    logsList.forEach {
        logs += it + "\n"
    }

    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = Modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Box(
            modifier = Modifier.padding(Dimensions.cardPadding).border(BorderStroke(1.dp, color = Color.DarkGray))
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(stateVertical)) {
                SelectionContainer {
                    Text(
                        text = logs,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            if (logsList.isNotEmpty()) {
                BtnIcon(
                    EvaIcons.Fill.Trash2,
                    description = "Clear logs",
                    onClick = { model.clearLogs() },
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp),
                    showTooltip = false,
                )
            }
            if (logsList.size > 15) {
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(top = 68.dp).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical)
                )
            }
        }
    }
}
