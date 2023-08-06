package ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.MyColors

@Composable
fun DeviceCommands(
    model: AppStore,
    coroutineScope: CoroutineScope,
    isDeviceSelected: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp).background(MyColors.bg2)
    ) {
        Button(onClick = { model.onHomeClick(coroutineScope) }) { Text(text = "Home") }
        Button(onClick = { model.onSettingsClick(coroutineScope) }) { Text(text = "Settings") }
        Button(onClick = { model.onBackClick(coroutineScope) }) { Text(text = "Back") }
        Button(onClick = { model.onTabClick(coroutineScope) }) { Text(text = "Tab") }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp).background(MyColors.bg2)
    ) {
        Button(onClick = { model.onEnterClick(coroutineScope) }) { Text(text = "Enter") }
        Button(onClick = { model.onPowerClick(coroutineScope) }) { Text(text = "Power") }
        // TODO Investigate why it not working
        /*Button(
            enabled = isDeviceSelected,
            onClick = { model.onSnapClick(coroutineScope) }) { Text(text = "Snap") }*/
        Button(onClick = { model.onDayClick(coroutineScope) }) { Text(text = "Day") }
        Button(onClick = { model.onNightClick(coroutineScope) }) { Text(text = "Night") }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp).background(MyColors.bg2)
    ) {
        Button(onClick = { model.onLeftClick(coroutineScope) }) { Text(text = "←") }
        Button(onClick = { model.onUpClick(coroutineScope) }) { Text(text = "↑") }
        Button(onClick = { model.onRightClick(coroutineScope) }) { Text(text = "→") }
        Button(onClick = { model.onDownClick(coroutineScope) }) { Text(text = "↓") }
    }
}
