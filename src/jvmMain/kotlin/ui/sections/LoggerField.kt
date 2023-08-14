package ui.sections

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import ui.theme.Dimens
import ui.theme.bounceClick
import ui.widgets.ExpandableCard

@Composable
fun LoggerField(model: AppStore) {
    val stateVertical = rememberScrollState(0)

    val logsList = model.state.logs
    var logs = ""
    logsList.forEach {
        logs += it + "\n"
    }

    ExpandableCard(
        title = "Logs",
        modifier = Modifier.padding(
            horizontal = Dimens.cardHorizontal, vertical = Dimens.cardVertical
        )
    ) {
        Box(modifier = Modifier.heightIn(max = 250.dp)) {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(stateVertical)) {
                SelectionContainer {
                    Text(
                        text = logs,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            if (logsList.isNotEmpty()) {
                Icon(
                    EvaIcons.Fill.Trash2,
                    contentDescription = "Clear logs",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp).align(Alignment.TopEnd).padding(top = 4.dp)
                        .bounceClick(onClick = { model.clearLogs() })
                )
            }
            if (logsList.size > 15) {
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(top = 32.dp).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical)
                )
            }
        }
    }
}
