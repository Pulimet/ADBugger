package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.LineaIcons
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.WeatherIcons
import compose.icons.lineaicons.Basic
import compose.icons.lineaicons.basic.Photo
import compose.icons.octicons.ArrowSwitch24
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.LetterA
import compose.icons.tablericons.Power
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.NightClear
import kotlinx.coroutines.CoroutineScope
import pref.preference
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon

@Composable
fun DeviceCommands(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    val isDeviceSelected = model.state.selectedDevice != AppStore.ALL_DEVICES
    var forwardUserInputState: Boolean by preference("Btn_ForwardUserInput", false)

    LaunchedEffect(Unit) {
        model.onForwardUserInputToggle(forwardUserInputState)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
    ) {
        BtnIcon(
            icon = Icons.Rounded.Home,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onHomeClick(coroutineScope) },
            description = "Home",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = Icons.Rounded.Settings,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onSettingsClick(coroutineScope) },
            description = "Settings",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onBackClick(coroutineScope) },
            description = "Back",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = Octicons.ArrowSwitch24,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onTabClick(coroutineScope) },
            description = "Tab",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = TablerIcons.ArrowBack,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onEnterClick(coroutineScope) },
            description = "Enter",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = TablerIcons.Power,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onPowerClick(coroutineScope) },
            description = "Power Button",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = WeatherIcons.DaySunny,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onDayClick(coroutineScope) },
            description = "Day Mode",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = WeatherIcons.NightClear,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onNightClick(coroutineScope) },
            description = "Night Mode",
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = LineaIcons.Basic.Photo,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { model.onSnapClick(coroutineScope) },
            description = "Take a snapshot",
            enabled = isDeviceSelected,
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
        BtnIcon(
            icon = TablerIcons.LetterA,
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = {
                forwardUserInputState = !forwardUserInputState
                model.onForwardUserInputToggle(forwardUserInputState)
            },
            description = "Forward User Input",
            toggle = forwardUserInputState,
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall
        )
    }
}
