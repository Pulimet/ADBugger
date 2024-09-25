package ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.buttons.BtnWithText

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject(),
    onValueChanged: (String) -> Unit = {}
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = modifier) {
        TextFieldX(singleLine = true,
            modifier = Modifier.weight(1f),
            label = "Search your package",
            value = textState,
            onValueChange = { value ->
                textState = value
                onValueChanged(value.text)
            })

        BtnWithText(
            icon = Icons.Rounded.Star,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = textState.text.isNotEmpty(),
            onClick = {
                model.addPackageNameToFavorites(textState.text)
                textState = TextFieldValue("")
                onValueChanged("")
            },
            description = "Save",
        )
    }

}
