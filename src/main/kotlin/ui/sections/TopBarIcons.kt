package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import compose.icons.LineaIcons
import compose.icons.Octicons
import compose.icons.TablerIcons
import compose.icons.WeatherIcons
import compose.icons.lineaicons.Basic
import compose.icons.lineaicons.basic.Photo
import compose.icons.octicons.ArrowSwitch24
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.LetterA
import compose.icons.tablericons.Notes
import compose.icons.tablericons.Power
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.NightClear
import kotlinx.coroutines.CoroutineScope
import pref.preference
import store.AppStore
import ui.theme.MyColors
import ui.widgets.BtnWithText

@Composable
fun TopBarIcons(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    val isDeviceSelected = model.state.selectedDevice != AppStore.ALL_DEVICES
    var forwardUserInputState: Boolean by preference("Btn_ForwardUserInput", false)
    var alwaysShowLogsEnabled: Boolean by preference("Btn_alwaysShowLogsEnabled", false)

    LaunchedEffect(Unit) {
        model.onForwardUserInputToggle(forwardUserInputState)
        model.onChangeAlwaysShowLog(alwaysShowLogsEnabled)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().background(MyColors.bg2),
    ) {
        BtnWithText(
            icon = Icons.Rounded.Home,
            onClick = { model.onHomeClick(coroutineScope) },
            description = "Home",
        )
        BtnWithText(
            icon = Icons.Rounded.Settings,
            onClick = { model.onSettingsClick(coroutineScope) },
            description = "Settings",
        )
        BtnWithText(
            icon = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            onClick = { model.onBackClick(coroutineScope) },
            description = "Back",
        )
        BtnWithText(
            icon = Octicons.ArrowSwitch24,
            onClick = { model.onTabClick(coroutineScope) },
            description = "Tab",
        )
        BtnWithText(
            icon = TablerIcons.ArrowBack,
            onClick = { model.onEnterClick(coroutineScope) },
            description = "Enter",
        )
        BtnWithText(
            icon = TablerIcons.Power,
            onClick = { model.onPowerClick(coroutineScope) },
            description = "Power",
        )
        BtnWithText(
            icon = WeatherIcons.DaySunny,
            onClick = { model.onDayClick(coroutineScope) },
            description = "Day",
        )
        BtnWithText(
            icon = WeatherIcons.NightClear,
            onClick = { model.onNightClick(coroutineScope) },
            description = "Night",
        )
        BtnWithText(
            icon = LineaIcons.Basic.Photo,
            onClick = { model.onSnapClick(coroutineScope) },
            description = "Snapshot",
            enabled = isDeviceSelected,
        )
        BtnWithText(
            icon = TablerIcons.LetterA,
            onClick = {
                forwardUserInputState = !forwardUserInputState
                model.onForwardUserInputToggle(forwardUserInputState)
            },
            description = "Input",
            toggle = forwardUserInputState,
        )
        BtnWithText(
            icon = TablerIcons.Notes,
            onClick = {
                alwaysShowLogsEnabled = !alwaysShowLogsEnabled
                model.onChangeAlwaysShowLog(alwaysShowLogsEnabled)
            },
            description = "Logs",
            toggle = alwaysShowLogsEnabled,
        )
    }
}
