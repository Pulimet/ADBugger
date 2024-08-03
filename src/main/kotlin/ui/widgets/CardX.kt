package ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import ui.theme.Dimensions
import ui.theme.MyColors


@Composable
fun CardX(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Dimensions.pageCornerRadius),
    backgroundColor: Color = MyColors.bg2,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = Dimensions.pageElevation,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.padding(Dimensions.selectedPagePadding),
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        border = border,
        content = content
    )
}
