package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.HoverButton

@Composable
fun ScalingPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val selectedDevice = model.state.selectedDevice
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Text(
                text = "Scaling page",
                fontSize = Dimensions.titleFontSize,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(0.85, selectedDevice) },
                    enabled = true,
                    text = "Font small"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.0, selectedDevice) },
                    enabled = true,
                    text = "Font default"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.15, selectedDevice) },
                    enabled = true,
                    text = "Font large"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.3, selectedDevice) },
                    enabled = true,
                    text = "Font largest"
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(0, selectedDevice) },
                    enabled = true,
                    text = "Density reset"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(300, selectedDevice) },
                    enabled = true,
                    text = "Density 300"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(400, selectedDevice) },
                    enabled = true,
                    text = "Density 400"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(500, selectedDevice) },
                    enabled = true,
                    text = "Density 500"
                )
                HoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(600, selectedDevice) },
                    enabled = true,
                    text = "Density 600"
                )

            }
        }
    }
}
