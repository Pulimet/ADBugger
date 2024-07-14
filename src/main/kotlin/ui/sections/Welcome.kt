package ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
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
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            )
            Row {
                Text(
                    text = "Here are the existing features:\n" +
                            "\n" +
                            "- Get a list of connected devices and running emulators.\n" +
                            "- Retrieve a list of installed packages on the selected target.\n" +
                            "- Launch and terminate emulators.\n" +
                            "- Grant and remove permissions for specific packages.\n" +
                            "- Send input events using buttons in the app or via keyboard.\n" +
                            "- List and adb reverse specific ports.\n" +
                            "- Launch commands simultaneously across all connected devices and emulators.\n" +
                            "- Show which command is actually launched under the hood.",
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Start,
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth(0.55f).padding(16.dp),
                )
                Image(
                    painter = painterResource("icon.png"),
                    contentDescription = "ADBugger Logo",
                    modifier = Modifier.fillMaxWidth(1f).padding(24.dp),
                )
            }
        }
    }
}
