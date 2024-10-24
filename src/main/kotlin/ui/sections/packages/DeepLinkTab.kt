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
            label = "Deep Link (example://site_url)",
            modifier = Modifier.weight(1f),
            saveValueKey = "deep_link_url",
            padding = PaddingValues(top = 6.dp)
        )
        BtnWithText(
            icon = TablerIcons.BrandAndroid,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.onOpenDeepLink(selectedUrl.text) },
            description = "Open",
        )
    }
}
