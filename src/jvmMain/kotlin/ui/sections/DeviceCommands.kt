package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.WeatherIcons
import compose.icons.octicons.ArrowSwitch24
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.Power
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.NightClear
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.MyColors
import ui.theme.Paddings
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard

@Composable
fun DeviceCommands(
    model: AppStore,
    coroutineScope: CoroutineScope,
    isDeviceSelected: Boolean
) {
    ExpandableCard(
        title = "Device Commands",
        modifier = Modifier.padding(
            horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
        ) {
            BtnIcon(
                icon = Icons.Rounded.Home,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onHomeClick(coroutineScope) },
                description = "Home"
            )
            BtnIcon(
                icon = Icons.Rounded.Settings,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onSettingsClick(coroutineScope) },
                description = "Settings"
            )
            BtnIcon(
                icon = Icons.Rounded.KeyboardArrowLeft,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onBackClick(coroutineScope) },
                description = "Back"
            )
            BtnIcon(
                icon = Octicons.ArrowSwitch24,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onTabClick(coroutineScope) },
                description = "Tab"
            )
            BtnIcon(
                icon = TablerIcons.ArrowBack,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onEnterClick(coroutineScope) },
                description = "Enter"
            )
            BtnIcon(
                icon = TablerIcons.Power,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onPowerClick(coroutineScope) },
                description = "Power"
            )
            BtnIcon(
                icon = WeatherIcons.DaySunny,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onDayClick(coroutineScope) },
                description = "Day"
            )
            BtnIcon(
                icon = WeatherIcons.NightClear,
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = { model.onNightClick(coroutineScope) },
                description = "Night"
            )

            // TODO Investigate why it not working
            /*Button(
            enabled = isDeviceSelected,
            onClick = { model.onSnapClick(coroutineScope) }) { Text(text = "Snap") }*/
        }
    }
}
