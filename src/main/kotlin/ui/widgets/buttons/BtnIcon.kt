package ui.widgets.buttons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick

/**
 * Button represented by an icon.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BtnIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    toggle: Boolean = true,
    onClick: () -> Unit = {},
    description: String = "",
    visible: Boolean = true,
    buttonSize: Dp = Dimensions.btnSizeBig,
    iconSize: Dp = Dimensions.btnIconSizeBig,
    showTooltip: Boolean = true,
    clickEffect: Boolean = true,
    hoverEnabled: Boolean = true,
) {
    if (!visible) {
        return
    }
    if (showTooltip)
        TooltipArea(
            tooltip = {
                Surface(
                    modifier = Modifier.shadow(4.dp),
                    color = Color(255, 255, 210),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            },
            delayMillis = 700,
            tooltipPlacement = TooltipPlacement.CursorPoint(
                alignment = Alignment.BottomEnd,
                offset = DpOffset((-16).dp, 30.dp)
            )
        ) {
            TooltipContent(
                modifier,
                buttonSize,
                onClick,
                enabled,
                icon,
                iconSize,
                description,
                toggle,
                clickEffect,
                hoverEnabled
            )
        }
    else {
        TooltipContent(
            modifier,
            buttonSize,
            onClick,
            enabled,
            icon,
            iconSize,
            description,
            toggle,
            clickEffect,
            hoverEnabled
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TooltipContent(
    modifier: Modifier,
    buttonSize: Dp,
    onClick: () -> Unit,
    enabled: Boolean,
    icon: ImageVector,
    iconSize: Dp,
    description: String,
    toggle: Boolean,
    clickEffect: Boolean,
    hoverEnabled: Boolean,
) {
    var active by remember { mutableStateOf(false) }
    var bgColor = if (toggle) colors.primary else Color.Gray
    if (hoverEnabled && active) {
        bgColor = MyColors.accent
    }

    Button(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .width(buttonSize)
            .height(buttonSize)
            .padding(2.dp)
            .bounceClick(onClick, enabled && clickEffect, 0.85f),
        enabled = enabled,
        onClick = {},
        shape = RoundedCornerShape(50.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = bgColor)
    )
    {
        Icon(
            icon,
            modifier = Modifier.size(iconSize),
            contentDescription = description
        )
    }
}
