package ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick
import ui.widgets.BtnIcon

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBarItem(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    toggle: Boolean = true,
    modifier: Modifier = Modifier,
) {
    var active by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .width(60.dp)
            .height(46.dp)
            .background(color = if (enabled && active) MyColors.bgHover else Color.Transparent)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .bounceClick(onClick, enabled, 0.85f),
    ) {
        BtnIcon(
            icon = icon,
            modifier = Modifier.padding(top = 4.dp),
            description = description,
            buttonSize = Dimensions.btnSizeSmall,
            iconSize = Dimensions.btnIconSizeSmall,
            enabled = enabled,
            toggle = toggle,
            showTooltip = false,
            clickEffect = false,
            hoverEnabled = false
        )
        Text(
            text = description,
            fontSize = Dimensions.topMenuFontSize,
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 22.dp),
            maxLines = 1,
        )
    }
}
