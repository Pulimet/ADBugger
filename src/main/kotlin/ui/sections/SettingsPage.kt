package ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pref.preference
import terminal.commands.Commands
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.TextFieldX

@Composable
fun SettingsPage(modifier: Modifier = Modifier) {
    var adbPath: String by preference("adbPath", Commands.getPlatformToolsDefaultPath())
    var emulatorPath: String by preference("emulatorPath", Commands.getEmulatorDefaultPath())

    var adbPathTextField by remember { mutableStateOf(TextFieldValue(adbPath)) }
    var emulatorPathTextField by remember { mutableStateOf(TextFieldValue(emulatorPath)) }

    CardX(modifier = modifier) {
        Row(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Column(modifier = Modifier.width(200.dp)) {
                Text(
                    text = "Platform tools path (ADB):",
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 4.dp).height(26.dp)
                )
                Text(
                    text = "Emulators path:",
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 4.dp).height(26.dp)
                )
            }
            Column(modifier = Modifier.width(300.dp)) {
                TextFieldX(
                    value = adbPathTextField,
                    onValueChange = { newText ->
                        adbPathTextField = newText
                        adbPath = newText.text
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 4.dp)
                )
                TextFieldX(
                    value = emulatorPathTextField,
                    onValueChange = { newText ->
                        emulatorPathTextField = newText
                        emulatorPath = newText.text
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 4.dp)
                )
            }

        }
    }
}
