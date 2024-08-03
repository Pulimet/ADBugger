package ui.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.buttons.BtnWithText


@Composable
fun PermissionsPage(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    val textInputPermissionState = remember { mutableStateOf(TextFieldValue("")) }

    CardX(modifier = modifier) {
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

                BtnWithText(
                    icon = Icons.Rounded.Add,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputPermissionState.value.text.isNotEmpty(),
                    onClick = { model.onAddPermission(textInputPermissionState.value.text) },
                    description = "Add",
                    width = Dimensions.permissionsCommandsBtnWidthShort,
                )

                BtnWithText(
                    icon = Icons.Rounded.Clear,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputPermissionState.value.text.isNotEmpty(),
                    onClick = { model.onRemovePermission(textInputPermissionState.value.text) },
                    description = "Remove",
                    width = Dimensions.permissionsCommandsBtnWidthShort,
                )

                BtnWithText(
                    icon = Icons.Rounded.Delete,
                    onClick = { model.onRemoveAllPermissions() },
                    enabled = model.state.selectedPackage != AppStore.PACKAGE_NONE,
                    description = "Remove all",
                    modifier = Modifier.padding(6.dp),
                    width = Dimensions.permissionsCommandsBtnWidth
                )
                BtnWithText(
                    icon = Icons.Rounded.DateRange,
                    onClick = { model.onGetPermissions() },
                    enabled = model.state.selectedPackage != AppStore.PACKAGE_NONE,
                    description = "Show granted",
                    modifier = Modifier.padding(6.dp),
                    width = Dimensions.permissionsCommandsBtnWidth,
                )
            }
        }
    }
}
