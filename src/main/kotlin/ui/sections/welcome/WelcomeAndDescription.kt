package ui.sections.welcome

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions

@Composable
fun WelcomeAndDescription() {
    Text(
        text = "Welcome to ADBugger!",
        fontSize = Dimensions.titleFontSize,
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        text = "ADBugger is a desktop tool for debugging and QA of Android devices and emulators. It simplifies testing, debugging, and performance analysis, offering device management, automated testing, log analysis, and remote control capabilities to ensure smooth app performance across different setups.",
        fontSize = Dimensions.subtitleFontSize,
        textAlign = TextAlign.Start,
        color = Color.LightGray,
        modifier = Modifier.padding(16.dp),
    )
}