package ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ui.theme.Dimens

@Composable
fun SideBarItem(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    toggle: Boolean = true,
    onClick: () -> Unit = {},
    title: String = "",
    visible: Boolean = true,
) {
    Row {
        BtnIcon(
            icon = icon,
            modifier = modifier,
            onClick = onClick,
            enabled = enabled,
            visible = visible,
            showTooltip = false,
            description = title,
            toggle = toggle,
            buttonSize = Dimens.btnSizeSmall,
            iconSize = Dimens.btnIconSizeSmall
        )
        SideBarTitle(title = title, isSelected = toggle)
    }
}
