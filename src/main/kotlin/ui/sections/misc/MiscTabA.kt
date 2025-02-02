package ui.sections.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.LineaIcons
import compose.icons.lineaicons.Basic
import compose.icons.lineaicons.basic.Home
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.misc_airplane
import net.alexandroid.adbugger.adbugger.generated.resources.misc_auto_off
import net.alexandroid.adbugger.adbugger.generated.resources.misc_auto_on
import net.alexandroid.adbugger.adbugger.generated.resources.misc_land_2
import net.alexandroid.adbugger.adbugger.generated.resources.misc_landscape
import net.alexandroid.adbugger.adbugger.generated.resources.misc_location_mode
import net.alexandroid.adbugger.adbugger.generated.resources.misc_off
import net.alexandroid.adbugger.adbugger.generated.resources.misc_on
import net.alexandroid.adbugger.adbugger.generated.resources.misc_portrait
import net.alexandroid.adbugger.adbugger.generated.resources.misc_portrait_2
import net.alexandroid.adbugger.adbugger.generated.resources.misc_rotation
import net.alexandroid.adbugger.adbugger.generated.resources.misc_wifi_mode
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.buttons.BtnWithText

@Composable
fun MiscTabA(model: AppStore = koinInject()) {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionTitle(stringResource(Res.string.misc_airplane))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onAirplaneOn() },
                description = stringResource(Res.string.misc_on),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onAirplaneOff() },
                description = stringResource(Res.string.misc_off),
            )
        }
        SectionTitle(stringResource(Res.string.misc_wifi_mode))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onWifiOn() },
                description = stringResource(Res.string.misc_on),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onWifiOff() },
                description = stringResource(Res.string.misc_off),
            )
        }
        SectionTitle(stringResource(Res.string.misc_rotation))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationAutoTurnOn() },
                description = stringResource(Res.string.misc_auto_on),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationAutoTurnOff() },
                description = stringResource(Res.string.misc_auto_off),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationLandscape() },
                description = stringResource(Res.string.misc_landscape),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationPortrait() },
                description = stringResource(Res.string.misc_portrait),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationPortraitUpSideDown() },
                description = stringResource(Res.string.misc_portrait_2),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationLandscapeUpSideDow() },
                description = stringResource(Res.string.misc_land_2),
            )
        }
        SectionTitle(stringResource(Res.string.misc_location_mode))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onGpsOn() },
                description = stringResource(Res.string.misc_on),
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onGpsOff() },
                description = stringResource(Res.string.misc_off),
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Column {
        Divider(thickness = 1.dp)
        Text(
            text = title,
            fontSize = Dimensions.titleFontSize,
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth()
        )
        Divider(thickness = 1.dp)
    }
}