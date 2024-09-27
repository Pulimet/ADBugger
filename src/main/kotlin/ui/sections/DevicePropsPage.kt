package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Refresh
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.buttons.BtnIcon
import ui.widgets.list.ListX

// TODO Save selected target and package if available and load on launch
// TODO Packages save selected tab in prefs
// TODO Allow search props
// TODO Parse each string in a device prop list and show UI friendly
// TODO Parse each string in a permissions list and show UI friendly

@Composable
fun DevicePropsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Text(
                text = "Device properties",
                fontSize = Dimensions.titleFontSize,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
            if (model.state.deviceProps.isNotEmpty()) {
                ListX(model.state.deviceProps)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    BtnIcon(
                        icon = TablerIcons.Refresh,
                        description = "Get Props",
                        onClick = { model.onGetDeviceProps() }
                    )
                }
            }
        }
        if (model.state.deviceProps.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                BtnIcon(
                    icon = TablerIcons.Refresh,
                    description = "Get Props",
                    buttonSize = Dimensions.btnSizeSmall,
                    iconSize = Dimensions.btnIconSizeSmall,
                    modifier = Modifier.padding(16.dp),
                    onClick = { model.onGetDeviceProps() }
                )
            }
        }
    }
}
