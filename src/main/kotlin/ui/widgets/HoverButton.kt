package ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HoverButton(enabled: Boolean, onClick: () -> Unit, text: String) {
    var active by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .background(color = if (enabled && active) MyColors.accent else Color.Transparent)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .bounceClick()
            .width(Dimensions.buttonWidthMedium),
        enabled = enabled,
        onClick = onClick) { Text(text = text) }
}
