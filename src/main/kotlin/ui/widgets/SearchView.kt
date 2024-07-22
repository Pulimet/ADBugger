package ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    model: AppStore = koinInject()
) {
    Row(modifier = modifier) {
        TextField(singleLine = true,
            modifier = Modifier.weight(1f),
            label = { Text("Search your package") },
            textStyle = TextStyle(color = Color.White),
            value = state.value,
            onValueChange = { value -> state.value = value })

        BtnWithText(
            icon = Icons.Rounded.Star,
            modifier = Modifier.padding(horizontal = 8.dp),
            enabled = state.value.text.isNotEmpty(),
            onClick = { model.addPackageNameToFavorites(state.value.text) },
            description = "Save",
        )
    }

}
