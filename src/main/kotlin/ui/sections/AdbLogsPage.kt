package ui.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.adb_logs_no_logs_yet
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.list.CopyAllAndClearBox
import ui.widgets.list.ListX

@Composable
fun AdbLogsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val logsList = model.state.adbLogs

    CardX(modifier = modifier) {
        Box(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            if (logsList.isEmpty()) {
                Text(
                    text = stringResource(Res.string.adb_logs_no_logs_yet),
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = Dimensions.titleFontSize
                )
            }

            ListX(model.state.adbLogs, modifier = Modifier.padding(end = 60.dp))
            CopyAllAndClearBox(logsList, Modifier.align(Alignment.TopEnd)) {
                model.clearAdbLogs()
            }
        }
    }
}
