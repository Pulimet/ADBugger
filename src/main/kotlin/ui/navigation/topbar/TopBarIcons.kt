package ui.navigation.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
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
import compose.icons.tablericons.LayersSubtract
import compose.icons.tablericons.LetterA
import compose.icons.tablericons.Notes
import compose.icons.tablericons.Power
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.NightClear
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.back
import net.alexandroid.adbugger.adbugger.generated.resources.day_mode
import net.alexandroid.adbugger.adbugger.generated.resources.enter
import net.alexandroid.adbugger.adbugger.generated.resources.home
import net.alexandroid.adbugger.adbugger.generated.resources.input
import net.alexandroid.adbugger.adbugger.generated.resources.logs
import net.alexandroid.adbugger.adbugger.generated.resources.night_mode
import net.alexandroid.adbugger.adbugger.generated.resources.power
import net.alexandroid.adbugger.adbugger.generated.resources.recent
import net.alexandroid.adbugger.adbugger.generated.resources.settings
import net.alexandroid.adbugger.adbugger.generated.resources.snapshot
import net.alexandroid.adbugger.adbugger.generated.resources.tab
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pref.preference
import store.AppStore
import ui.theme.MyColors
import ui.widgets.buttons.BtnWithText

@Composable
fun TopBarIcons(model: AppStore = koinInject()) {
    val stateHorizontal = rememberScrollState(0)

    var forwardUserInputState: Boolean by preference("Btn_ForwardUserInput", false)
    var alwaysShowLogsEnabled: Boolean by preference("Btn_alwaysShowLogsEnabled", false)

    LaunchedEffect(Unit) {
        model.onForwardUserInputToggle(forwardUserInputState)
        model.onChangeAlwaysShowLog(alwaysShowLogsEnabled)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().background(MyColors.bg2)
            .horizontalScroll(stateHorizontal),
    ) {
        BtnWithText(
            icon = Icons.Rounded.Home,
            onClick = { model.onHomeClick() },
            description = stringResource(Res.string.home),
        )
        BtnWithText(
            icon = Icons.Rounded.Settings,
            onClick = { model.onSettingsClick() },
            description = stringResource(Res.string.settings),
        )
        BtnWithText(
            icon = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            onClick = { model.onBackClick() },
            description = stringResource(Res.string.back),
        )
        BtnWithText(
            icon = TablerIcons.LayersSubtract,
            onClick = { model.onRecentClick() },
            description = stringResource(Res.string.recent),
        )
        BtnWithText(
            icon = Octicons.ArrowSwitch24,
            onClick = { model.onTabClick() },
            description = stringResource(Res.string.tab),
        )
        BtnWithText(
            icon = TablerIcons.ArrowBack,
            onClick = { model.onEnterClick() },
            description = stringResource(Res.string.enter),
        )
        BtnWithText(
            icon = TablerIcons.Power,
            onClick = { model.onPowerClick() },
            description = stringResource(Res.string.power),
        )
        BtnWithText(
            icon = WeatherIcons.DaySunny,
            onClick = { model.onDayClick() },
            description = stringResource(Res.string.day_mode),
        )
        BtnWithText(
            icon = WeatherIcons.NightClear,
            onClick = { model.onNightClick() },
            description = stringResource(Res.string.night_mode),
        )
        BtnWithText(
            icon = LineaIcons.Basic.Photo,
            onClick = { model.onSnapClick() },
            description = stringResource(Res.string.snapshot),
        )
        BtnWithText(
            icon = TablerIcons.LetterA,
            onClick = {
                forwardUserInputState = !forwardUserInputState
                model.onForwardUserInputToggle(forwardUserInputState)
            },
            description = stringResource(Res.string.input),
            toggle = model.state.isUserForwardInputEnabled,
        )
        BtnWithText(
            icon = TablerIcons.Notes,
            onClick = {
                alwaysShowLogsEnabled = !alwaysShowLogsEnabled
                model.onChangeAlwaysShowLog(alwaysShowLogsEnabled)
            },
            description = stringResource(Res.string.logs),
            toggle = alwaysShowLogsEnabled,
        )
    }
}
