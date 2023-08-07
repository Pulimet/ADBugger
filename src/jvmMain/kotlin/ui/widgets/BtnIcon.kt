package ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ui.theme.bounceClick

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BtnIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    description: String = "",
    visible: Boolean = true
) {
    if (!visible) {
        return
    }
    TooltipArea(
        tooltip = {
            Surface(
                modifier = Modifier.shadow(4.dp),
                color = Color(255, 255, 210),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = description,
                    modifier = Modifier.padding(10.dp)
                )
            }
        },
        delayMillis = 1200,
        tooltipPlacement = TooltipPlacement.CursorPoint(
            alignment = Alignment.BottomEnd,
            offset = DpOffset((-16).dp, 30.dp)
        )
    ) {
        Button(
            modifier = modifier.width(40.dp).padding(2.dp).bounceClick(onClick, enabled),
            enabled = enabled,
            onClick = {},
            shape = RoundedCornerShape(50.dp),
            contentPadding = PaddingValues(0.dp)
        )
        {
            Icon(
                icon,
                contentDescription = description
            )
        }
    }
}
