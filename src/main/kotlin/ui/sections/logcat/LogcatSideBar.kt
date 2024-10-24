package ui.sections.logcat

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.FileAdd
import compose.icons.evaicons.fill.PersonDelete
import compose.icons.evaicons.fill.Save
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_clear_logcat
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_save_bugreport
import net.alexandroid.adbugger.adbugger.generated.resources.logcat_btn_save_logs
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.buttons.BtnWithText
import ui.widgets.list.CopyAllAndClearBox

@Composable
fun LogcatSideBar(
    list: List<String>,
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {

    Column(modifier = modifier) {
        CopyAllAndClearBox(list, buttonWidth = 100.dp) {
            model.clearLocalLogcatLogs()
        }

        BtnWithText(
            icon = EvaIcons.Fill.Save,
            onClick = { model.saveLogcatLogsToDesktop() },
            description = stringResource(Res.string.logcat_btn_save_logs),
            width = 100.dp,
        )

        BtnWithText(
            icon = EvaIcons.Fill.FileAdd,
            onClick = { model.saveBugreport() },
            description = stringResource(Res.string.logcat_btn_save_bugreport),
            width = 100.dp,
        )

        BtnWithText(
            icon = EvaIcons.Fill.PersonDelete,
            onClick = { model.clearTargetLogcatLogs() },
            description = stringResource(Res.string.logcat_btn_clear_logcat),
            width = 100.dp,
        )
    }
}
