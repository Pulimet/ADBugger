package ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ui.theme.Dimens
import ui.theme.MyColors
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
        modifier = Modifier.
        fillMaxWidth()
            .background(color = if (toggle) MyColors.bgSelected else Color.Transparent)
            .padding(horizontal = 24.dp)
            .height(Dimens.sideBarItemHeight)
            .bounceClick(onClick, enabled, 0.85f),
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
