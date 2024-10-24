package ui.sections.packages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandAndroid
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.packages_btn_open
import net.alexandroid.adbugger.adbugger.generated.resources.packages_input_deep_link
import net.alexandroid.adbugger.adbugger.generated.resources.packages_save_value_key_input_deep_link
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText

@Composable
fun DeepLinkTab(model: AppStore = koinInject()) {
    var selectedUrl by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = Modifier.fillMaxSize()) {
        TextFieldX(
            value = selectedUrl,
            onValueChange = { newText ->
                selectedUrl = newText
            },
            label = stringResource(Res.string.packages_input_deep_link),
            modifier = Modifier.weight(1f),
            saveValueKey = stringResource(Res.string.packages_save_value_key_input_deep_link),
            padding = PaddingValues(top = 6.dp)
        )
        BtnWithText(
            icon = TablerIcons.BrandAndroid,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.onOpenDeepLink(selectedUrl.text) },
            description = stringResource(Res.string.packages_btn_open),
        )
    }
}
