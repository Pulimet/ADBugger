package ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.sections.DeviceCommands
import ui.theme.Dimens
import ui.theme.MyColors


@Composable
fun TopBar(model: AppStore, coroutineScope: CoroutineScope) {
    Row(
        modifier = Modifier.fillMaxWidth().height(Dimens.topBarHeight).background(MyColors.bg2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SideBarLogo()
        DeviceCommands(model, coroutineScope)
    }
}