package ui.widgets

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
) {
    TextField(
        modifier = modifier,
        singleLine = true,
        label = { Text("Search your package") },
        value = state.value,
        onValueChange = { value -> state.value = value })
}