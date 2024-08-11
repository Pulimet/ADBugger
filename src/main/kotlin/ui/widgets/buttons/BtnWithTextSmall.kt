package ui.widgets.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions

@Composable
fun BtnWithTextSmall(
    icon: ImageVector,
    description: String,
    toggle: Boolean = true,
    width: Dp = 50.dp,
    onClick: () -> Unit,
) {
    BtnWithText(
        icon = icon,
        onClick = onClick,
        description = description,
        width = width,
        height = 36.dp,
        toggle = toggle,
        buttonSize = Dimensions.btnSizeExtraSmall,
        iconSize = Dimensions.btnIconSizeExtraSmall,
        textTopPadding = 14.dp
    )
}
