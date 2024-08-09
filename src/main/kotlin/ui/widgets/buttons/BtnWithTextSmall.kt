package ui.widgets.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions

@Composable
fun BtnWithTextSmall(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
) {
    BtnWithText(
        icon = icon,
        onClick = onClick,
        description = description,
        width = 50.dp,
        height = 36.dp,
        buttonSize = Dimensions.btnSizeExtraSmall,
        iconSize = Dimensions.btnIconSizeExtraSmall,
        textTopPadding = 14.dp
    )
}
