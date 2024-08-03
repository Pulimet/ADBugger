package ui.navigation.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick
import ui.widgets.buttons.BtnIcon

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SideBarItem(
    icon: ImageVector,
    enabled: Boolean = true,
    toggle: Boolean = true,
    onClick: () -> Unit = {},
    title: String = "",
    visible: Boolean = true,
    collapsed: Boolean = false
) {
    var active by remember { mutableStateOf(false) }

    Row {
        if (!collapsed) {
            Spacer(
                modifier = Modifier
                    .height(Dimensions.sideBarItemHeight)
                    .width(Dimensions.sideBarSpacerWidth)
                    .background(color = getSpacerBgColor(toggle, active))
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(color = getBgColor(toggle, active))
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .padding(start = if (collapsed) 8.dp else 18.dp)
                .height(Dimensions.sideBarItemHeight)
                .bounceClick(onClick, enabled, 0.85f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BtnIcon(
                icon = icon,
                enabled = enabled,
                visible = visible,
                showTooltip = collapsed,
                description = title,
                toggle = toggle,
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
                clickEffect = false,
                hoverEnabled = false,
            )
            if (!collapsed) {
                SideBarTitle(title = title, isSelected = toggle)
            }
        }
    }
}

private fun getBgColor(toggle: Boolean, active: Boolean): Color {
    return if (toggle) {
        MyColors.bgSelected
    } else {
        if (active) MyColors.bgHover else Color.Transparent
    }
}

private fun getSpacerBgColor(toggle: Boolean, active: Boolean): Color {
    return if (toggle) {
        MyColors.accent
    } else {
        if (active) MyColors.bgHover else Color.Transparent
    }
}
