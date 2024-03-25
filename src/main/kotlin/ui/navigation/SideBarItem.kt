package ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ui.theme.Dimens
import ui.theme.bounceClick
import ui.widgets.BtnIcon

@Composable
fun SideBarItem(
    icon: ImageVector,
    enabled: Boolean = true,
    toggle: Boolean = true,
    onClick: () -> Unit = {},
    title: String = "",
    visible: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(Dimens.sideBarItemHeight).bounceClick(onClick, enabled, 0.85f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BtnIcon(
            icon = icon,
            onClick = onClick,
            enabled = enabled,
            visible = visible,
            showTooltip = false,
            description = title,
            toggle = toggle,
            buttonSize = Dimens.btnSizeSmall,
            iconSize = Dimens.btnIconSizeSmall,
            clickEffect = false,
        )
        SideBarTitle(title = title, isSelected = toggle)
    }
}
