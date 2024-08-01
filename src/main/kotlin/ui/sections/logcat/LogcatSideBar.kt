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
        CopyAllAndClearBox(list, buttonWidth = 120.dp) {
            model.clearLocalLogcatLogs()
        }
        BtnWithText(
            icon = EvaIcons.Fill.Save,
            //   adb logcat -d > [path_to_file] // Save the logcat output to a file on the local system.
            onClick = {},
            description = "Save to file",
            width = 120.dp,
        )
        BtnWithText(
            icon = EvaIcons.Fill.FileAdd,
            //  adb bugreport > [path_to_file] // Will dump the whole device information like dumpstate, dumpsys and logcat output.
            onClick = {},
            description = "Bugreport",
            width = 120.dp,
        )
        BtnWithText(
            icon = EvaIcons.Fill.PersonDelete,
            onClick = { model.clearTargetLogcatLogs() },
            description = "Clear logcat",
            width = 120.dp,
        )
    }
}
