package ui.sections.emulator.run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_btn_kill_all
import net.alexandroid.adbugger.adbugger.generated.resources.emulators_btn_refresh_list
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.buttons.BtnWithText

@Composable
fun EmulatorsRunSideMenu(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        BtnWithText(
            icon = Icons.Rounded.Refresh,
            onClick = { model.getEmulatorsListClick() },
            description = stringResource(Res.string.emulators_btn_refresh_list),
            width = 70.dp
        )
        BtnWithText(
            icon = FontAwesomeIcons.Solid.BookDead,
            onClick = { model.onKillAllEmulatorClick() },
            description = stringResource(Res.string.emulators_btn_kill_all),
            width = 70.dp
        )
    }
}
