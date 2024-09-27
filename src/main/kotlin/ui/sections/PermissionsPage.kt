package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText
import ui.widgets.list.ListX


@Composable
fun PermissionsPage(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    var textInputPermissionState by remember { mutableStateOf(TextFieldValue("")) }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                TextFieldX(
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    paddingTop = 0.dp,
                    value = textInputPermissionState,
                    label = "Permission",
                    onValueChange = { value -> textInputPermissionState = value }
                )

                BtnWithText(
                    icon = Icons.Rounded.Add,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputPermissionState.text.isNotEmpty(),
                    onClick = { model.onAddPermission(textInputPermissionState.text) },
                    description = "Add",
                    width = Dimensions.permissionsCommandsBtnWidthShort,
                )

                BtnWithText(
                    icon = Icons.Rounded.Clear,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = textInputPermissionState.text.isNotEmpty(),
                    onClick = { model.onRemovePermission(textInputPermissionState.text) },
                    description = "Remove",
                    width = Dimensions.permissionsCommandsBtnWidthShort,
                )

                BtnWithText(
                    icon = Icons.Rounded.Delete,
                    onClick = { model.onRemoveAllPermissions() },
                    enabled = model.state.selectedTargetsList.size == 1,
                    description = "Remove all",
                    modifier = Modifier.padding(6.dp),
                    width = Dimensions.permissionsCommandsBtnWidth
                )
                BtnWithText(
                    icon = Icons.Rounded.DateRange,
                    onClick = { model.onGetPermissions() },
                    enabled = model.state.selectedTargetsList.size == 1,
                    description = "Show granted",
                    modifier = Modifier.padding(6.dp),
                    width = Dimensions.permissionsCommandsBtnWidth,
                )
            }
            if (model.state.permissions.isNotEmpty()) {
                ListX(model.state.permissions)
            }
        }
    }
}
