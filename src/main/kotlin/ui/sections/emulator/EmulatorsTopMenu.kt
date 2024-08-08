package ui.sections.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText

@Composable
fun EmulatorsTopMenu(model: AppStore = koinInject()) {
    var proxyTextField by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(MyColors.bg).padding(vertical = 8.dp)
    ) {
        TextFieldX(
            value = proxyTextField,
            onValueChange = { newText -> proxyTextField = newText },
            label = "Proxy",
            modifier = Modifier.background(MyColors.bg).padding(bottom = 8.dp)
        )
        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.getEmulatorsListClick() },
            description = "Refresh Emulators List",
            width = 160.dp
        )
        BtnWithText(
            icon = FontAwesomeIcons.Solid.BookDead,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.onKillAllEmulatorClick() },
            description = "Kill All Emulators",
            width = 160.dp
        )
    }
}
