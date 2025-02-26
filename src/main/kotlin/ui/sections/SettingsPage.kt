package ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Refresh
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.settings_desc_check
import net.alexandroid.adbugger.adbugger.generated.resources.settings_desc_refresh
import net.alexandroid.adbugger.adbugger.generated.resources.settings_emulators_path
import net.alexandroid.adbugger.adbugger.generated.resources.settings_env_vars
import net.alexandroid.adbugger.adbugger.generated.resources.settings_platform_tools
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pref.preference
import store.AppStore
import terminal.DefaultPath
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnIcon
import ui.widgets.buttons.StatusIcon
import ui.widgets.list.ListX

@Composable
fun SettingsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    var adbPath: String by preference("adbPath", DefaultPath.getPlatformToolsDefaultPath())
    var emulatorPath: String by preference("emulatorPath", DefaultPath.getEmulatorDefaultPath())

    var adbPathTextField by remember { mutableStateOf(TextFieldValue(adbPath)) }
    var emulatorPathTextField by remember { mutableStateOf(TextFieldValue(emulatorPath)) }

    val list by remember(model.state.environmentVariables) {
        derivedStateOf {
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
    }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(Res.string.settings_platform_tools),
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Start,
                    color = Color.LightGray,
                    modifier = Modifier.width(200.dp)
                )
                TextFieldX(
                    value = adbPathTextField,
                    onValueChange = { newText ->
                        adbPathTextField = newText
                        adbPath = newText.text
                    },
                    padding = PaddingValues(top = 8.dp),
                    modifier = Modifier.width(400.dp).padding(vertical = 5.dp, horizontal = 4.dp)
                )

                StatusIcon(
                    status = model.state.isAdbAccessOk,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.checkPlatformTools() },
                    description = stringResource(Res.string.settings_desc_check)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(Res.string.settings_emulators_path),
                    fontSize = Dimensions.subtitleFontSize,
                    textAlign = TextAlign.Start,
                    color = Color.LightGray,
                    modifier = Modifier.width(200.dp)
                )
                TextFieldX(
                    value = emulatorPathTextField,
                    onValueChange = { newText ->
                        emulatorPathTextField = newText
                        emulatorPath = newText.text
                    },
                    padding = PaddingValues(top = 8.dp),
                    modifier = Modifier.width(400.dp).padding(vertical = 5.dp, horizontal = 4.dp)
                )
                StatusIcon(
                    status = model.state.isEmulatorAccessOk,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.checkEmulators() },
                    description = stringResource(Res.string.settings_desc_check)
                )
            }

            Divider(thickness = 1.dp)

            Text(
                text = stringResource(Res.string.settings_env_vars),
                fontSize = Dimensions.titleFontSize,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
            if (list.value.isNotEmpty()) {
                ListX(list.value)
            } else {
                BtnIcon(
                    icon = TablerIcons.Refresh,
                    description = stringResource(Res.string.settings_desc_refresh),
                    onClick = { model.onGetEnvironmentVariables() }
                )
            }

        }
    }
}
