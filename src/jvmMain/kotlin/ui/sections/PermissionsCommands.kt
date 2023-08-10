package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Paddings
import ui.theme.bounceClick
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard


@Composable
fun PermissionsCommands(
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    val textInputPermissionState = remember { mutableStateOf(TextFieldValue("")) }

    ExpandableCard(
        title = "Permissions",
        modifier = Modifier.padding(
            horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            TextField(
                modifier = Modifier.padding(6.dp).weight(1f),
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                value = textInputPermissionState.value,
                label = { Text("Permission") },
                onValueChange = { value -> textInputPermissionState.value = value }
            )

            BtnIcon(
                icon = Icons.Rounded.Add,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = textInputPermissionState.value.text.isNotEmpty(),
                onClick = { model.onAddPermission(coroutineScope, textInputPermissionState.value.text) },
                description = "Add"
            )
            BtnIcon(
                icon = Icons.Rounded.Clear,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = textInputPermissionState.value.text.isNotEmpty(),
                onClick = { model.onRemovePermission(coroutineScope, textInputPermissionState.value.text) },
                description = "Remove"
            )
            Button(
                onClick = { model.onRemoveAllPermissions(coroutineScope) },
                modifier = Modifier.padding(horizontal = 4.dp).bounceClick()
            ) { Text(text = "Remove all") }
        }
    }
}
