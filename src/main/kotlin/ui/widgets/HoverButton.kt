package ui.widgets

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HoverButton(enabled: Boolean, onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    var active by remember { mutableStateOf(false) }
    val color = if (enabled && active) MyColors.accent else colors.primary

    Button(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .bounceClick()
            .width(Dimensions.buttonWidthMedium),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        enabled = enabled,
        onClick = onClick) { Text(text = text) }
}
