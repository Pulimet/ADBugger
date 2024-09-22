package ui.widgets.buttons

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick

/**
 * Regular button that changes color on hover.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SmallHoverButton(enabled: Boolean, onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    var active by remember { mutableStateOf(false) }
    val color = if (enabled && active) MyColors.accent else Color.LightGray

    TextButton(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .bounceClick(),
        enabled = enabled,
        onClick = onClick) {
        Text(
            text = text,
            color = color,
            fontSize = Dimensions.subtitleFontSize,
            maxLines = 1,
        )
    }
}
