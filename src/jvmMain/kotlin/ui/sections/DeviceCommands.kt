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
import compose.icons.LineaIcons
import compose.icons.TablerIcons
import compose.icons.WeatherIcons
import compose.icons.lineaicons.Arrows
import compose.icons.lineaicons.arrows.KeyboardTab
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.Power
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.NightClear
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.MyColors
import ui.theme.bounceClick
import ui.theme.pressClickEffect
import ui.widgets.BtnIcon

@Composable
fun DeviceCommands(
    model: AppStore,
    coroutineScope: CoroutineScope,
    isDeviceSelected: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp).background(MyColors.bg2)
    ) {
        BtnIcon(
            icon = Icons.Rounded.Home,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onHomeClick(coroutineScope) },
            description = "Home"
        )
        BtnIcon(
            icon = Icons.Rounded.Settings,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onSettingsClick(coroutineScope) },
            description = "Settings"
        )
        BtnIcon(
            icon = Icons.Rounded.KeyboardArrowLeft,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onBackClick(coroutineScope) },
            description = "Back"
        )
        BtnIcon(
            icon = LineaIcons.Arrows.KeyboardTab,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onTabClick(coroutineScope) },
            description = "Tab"
        )
        BtnIcon(
            icon = TablerIcons.ArrowBack,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onEnterClick(coroutineScope) },
            description = "Enter"
        )
        BtnIcon(
            icon = TablerIcons.Power,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onPowerClick(coroutineScope) },
            description = "Power"
        )
        BtnIcon(
            icon = WeatherIcons.DaySunny,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onDayClick(coroutineScope) },
            description = "Day"
        )
        BtnIcon(
            icon = WeatherIcons.NightClear,
            modifier = Modifier.padding(horizontal = 4.dp).bounceClick(),
            onClick = { model.onNightClick(coroutineScope) },
            description = "Night"
        )

        // TODO Investigate why it not working
        /*Button(
            enabled = isDeviceSelected,
            onClick = { model.onSnapClick(coroutineScope) }) { Text(text = "Snap") }*/
    }
}
