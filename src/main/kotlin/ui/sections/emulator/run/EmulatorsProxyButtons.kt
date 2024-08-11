package ui.sections.emulator.run

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.TextExample
import ui.widgets.buttons.BtnWithTextSmall

@Composable
fun EmulatorsProxyButtons(
    showDescription: Boolean,
    model: AppStore = koinInject(),
    modifier: Modifier = Modifier,
    onClickSetProxy: () -> Unit
) {
    Column(modifier = modifier) {
        if (showDescription) {
            TextExample("Proxy control after emulator launched", TextAlign.Center, Modifier.width(150.dp))
        }
        Row(modifier = Modifier.padding(top = 2.dp)) {
            BtnWithTextSmall(
                icon = Icons.AutoMirrored.Rounded.Send,
                onClick = { onClickSetProxy() },
                description = "Set",
            )
            BtnWithTextSmall(
                icon = Icons.Rounded.Delete,
                onClick = { model.removeProxy() },
                description = "Clear",
            )
            BtnWithTextSmall(
                icon = Icons.Rounded.Check,
                onClick = { model.getProxy() },
                description = "Get",
            )
        }
    }
}
