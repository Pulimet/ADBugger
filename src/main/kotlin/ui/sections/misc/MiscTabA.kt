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
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.buttons.BtnWithText

@Composable
fun MiscTabA(model: AppStore = koinInject()) {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionTitle("Airplane mode")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onAirplaneOn() },
                description = "On",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onAirplaneOff() },
                description = "Off",
            )
        }
        SectionTitle("Wifi mode")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onWifiOn() },
                description = "On",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onWifiOff() },
                description = "Off",
            )
        }
        SectionTitle("Rotation")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationAutoTurnOn() },
                description = "Auto On",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationAutoTurnOff() },
                description = "Auto Off",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationLandscape() },
                description = "Landscape",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationPortrait() },
                description = "Portrait",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationPortraitUpSideDown() },
                description = "Portrait 2",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onRotationLandscapeUpSideDow() },
                description = "Land. 2",
            )
        }
        SectionTitle("Location mode")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onGpsOn() },
                description = "On",
            )
            BtnWithText(
                icon = LineaIcons.Basic.Home,
                onClick = { model.onGpsOff() },
                description = "Off",
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