package ui.widgets.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TabLabel(
    name: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    var active by remember { mutableStateOf(false) }

    val unHoverColor = if (isSelected) MyColors.tabBgSelected else MyColors.tabBg
    val bgColor = if (enabled && active) MyColors.tabBgHover else unHoverColor
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(36.dp)
            .padding(horizontal = 2.dp)
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius))
            .background(color = bgColor)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .bounceClick(onClick, enabled, 0.85f),
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
