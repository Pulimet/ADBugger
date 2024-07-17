package ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions

@Composable
fun StatusBar(model: AppStore = koinInject()) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "Selected target: ${model.state.selectedDevice}",
            fontSize = Dimensions.statusBarFontSize,
            color = Color.LightGray,
        )
        Text(
            "Selected package: ${model.state.selectedPackage}",
            fontSize = Dimensions.statusBarFontSize,
            color = Color.LightGray,
        )
    }
}
