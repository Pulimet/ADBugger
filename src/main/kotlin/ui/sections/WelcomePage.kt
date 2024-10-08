package ui.sections

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions
import ui.widgets.CardX

@Composable
fun WelcomePage(modifier: Modifier = Modifier) {
    val stateVertical = rememberScrollState(0)

    CardX(modifier = modifier) {
        Box {
            Column(
                modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()
                    .verticalScroll(stateVertical)
            ) {
                WelcomeAndDescription()
                Text(
                    text = "Release notes",
                    fontSize = Dimensions.titleFontSize,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 8.dp)
                )
                Version("Version 1.0.4 (22 September 2024)", desc1_0_4)
                Version("Version 1.0.3 (01 August 2024)", desc1_0_3)
                Version("Version 1.0.2 (23 July 2024)", desc1_0_2)
                Version("Version 1.0.1 (17 July 2024)", desc1_0_1)
                Version("Version 1.0.0 (16 July 2024)", desc1_0_0)
            }
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight().align(Alignment.TopEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = stateVertical
                )
            )
        }
    }
}

const val desc1_0_4 = "- Allow setting a path for platform tools and emulators.\n" +
        "- Support all useful configurations for emulator launch.\n" +
        "- Allow changing the following configurations: Location mode, airplane mode, Wifi mode, Rotation.\n" +
        "- Scaling page refactoring, supporting display size change, better UI, and supporting custom values.\n" +
        "- Windows OS support"

const val desc1_0_3 = "- New Logcat section with the ability to filter logs by priority and tag.\n" +
        "- Support multiple target selection for executing commands."

const val desc1_0_2 = "- Support installing APK chosen with file picker.\n" +
        "- Allow to add package names to favorites and to view them in separate tab."

const val desc1_0_1 = "- Support forwarding input of: Enter, Dot, Comma.\n" +
        "- New status line with selected target and package name.\n" +
        "- Adb Logger - allow copying all or specific log."

const val desc1_0_0 = "- Get a list of connected devices and running emulators.\n" +
        "- Retrieve a list of installed packages on the selected target.\n" +
        "- Launch and terminate emulators.\n" +
        "- Grant and remove permissions for specific packages.\n" +
        "- Send input events using buttons in the app or via keyboard.\n" +
        "- List and adb reverse specific ports.\n" +
        "- Launch commands simultaneously across all connected devices and emulators.\n" +
        "- Show which command is actually launched under the hood."


@Composable
private fun Version(title: String, description: String) {
    Text(
        text = title,
        fontSize = Dimensions.releaseNotesFontSize,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )
    Text(
        text = description,
        fontSize = Dimensions.releaseNotesFontSize,
        textAlign = TextAlign.Start,
        color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}

@Composable
private fun WelcomeAndDescription() {
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
