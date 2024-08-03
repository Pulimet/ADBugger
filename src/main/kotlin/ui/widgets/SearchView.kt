package ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
        TextField(singleLine = true,
            modifier = Modifier.weight(1f),
            label = { Text("Search your package") },
            textStyle = TextStyle(color = Color.White),
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
