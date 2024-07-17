package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
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
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon
import ui.widgets.HoverButton


@Composable
fun PermissionsPage(
    model: AppStore,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val textInputPermissionState = remember { mutableStateOf(TextFieldValue("")) }

    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
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
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HoverButton(
                    onClick = { model.onRemoveAllPermissions(coroutineScope) },
                    enabled = true,
                    text = "Remove all",
                    modifier = Modifier.padding(6.dp)
                )
                HoverButton(
                    onClick = { model.onGetPermissions(coroutineScope) },
                    enabled = true,
                    text = "Show granted",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}
