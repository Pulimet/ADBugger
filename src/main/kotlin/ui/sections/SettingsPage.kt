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
import org.koin.compose.koinInject
import pref.preference
import store.AppStore
import terminal.DefaultPath
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.TextFieldX
import ui.widgets.list.ListX

@Composable
fun SettingsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    var adbPath: String by preference("adbPath", DefaultPath.getPlatformToolsDefaultPath())
    var emulatorPath: String by preference("emulatorPath", DefaultPath.getEmulatorDefaultPath())

    var adbPathTextField by remember { mutableStateOf(TextFieldValue(adbPath)) }
    var emulatorPathTextField by remember { mutableStateOf(TextFieldValue(emulatorPath)) }

    val list by remember {
        val result = mutableListOf<String>()

        model.state.environmentVariables.forEach {
            if (it.value.contains(':')) {
                result += "--------- START of ${it.key} ------------"
                it.value.split(':').forEach { value ->
                    result += value
                }
                result += "--------END of ${it.key}---------"
            } else {
                result += "${it.key} => ${it.value}"
            }
        }
        mutableStateOf(result)
    }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Row {
                Text(
                    text = "Platform tools path (ADB):",
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Start,
                    color = Color.LightGray,
                    modifier = Modifier.width(200.dp).padding(vertical = 4.dp).height(26.dp)
                )
                TextFieldX(
                    value = adbPathTextField,
                    onValueChange = { newText ->
                        adbPathTextField = newText
                        adbPath = newText.text
                    },
                    modifier = Modifier.width(400.dp).padding(vertical = 5.dp, horizontal = 4.dp)
                )
            }
            Row {
                Text(
                    text = "Emulators path:",
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Start,
                    color = Color.LightGray,
                    modifier = Modifier.width(200.dp).padding(vertical = 4.dp).height(26.dp)
                )
                TextFieldX(
                    value = emulatorPathTextField,
                    onValueChange = { newText ->
                        emulatorPathTextField = newText
                        emulatorPath = newText.text
                    },
                    modifier = Modifier.width(400.dp).padding(vertical = 5.dp, horizontal = 4.dp)
                )
            }

            Text(
                text = "Environment variables",
                fontSize = Dimensions.titleFontSize,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
            ListX(list)

        }
    }
}
