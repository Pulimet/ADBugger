package ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import ui.theme.Dimensions
import ui.widgets.CardX

@Composable
fun SettingsPage(modifier: Modifier = Modifier) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Text(
                text = "Settings will be added here soon!",
                fontSize = Dimensions.titleFontSize,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
