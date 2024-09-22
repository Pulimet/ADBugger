package ui.widgets.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Check
import compose.icons.tablericons.Fall
import compose.icons.tablericons.QuestionMark
import store.Status
import store.Status.FAIL
import store.Status.OK
import store.Status.UNKNOWN
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.theme.bounceClick

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StatusIcon(
    status: Status = Status.UNKNOWN,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    description: String = "",
    buttonSize: Dp = Dimensions.btnSizeSmall,
    iconSize: Dp = Dimensions.btnIconSizeSmall,
) {
    var active by remember { mutableStateOf(false) }
    val icon = when (status) {
        UNKNOWN -> TablerIcons.QuestionMark
        OK -> TablerIcons.Check
        FAIL -> TablerIcons.Fall
    }

    val bgColor = when (status) {
        UNKNOWN -> if (active) MyColors.accent else colors.primary
        OK -> MyColors.ok
        FAIL -> MyColors.fail
    }

    Button(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .width(buttonSize)
            .height(buttonSize)
            .padding(2.dp)
            .bounceClick(onClick, true, 0.85f),
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
