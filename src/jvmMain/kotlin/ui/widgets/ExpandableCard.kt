package ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.Minimize2
import ui.theme.MyColors

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    content: @Composable () -> Unit
) {
    val measurePolicy = customMeasurePolicy()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        if (!expanded) {
            Box(modifier = Modifier.clickable { expanded = !expanded }) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                    textAlign = TextAlign.Center,
                    text = title,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Icon(
                    FeatherIcons.ChevronDown,
                    contentDescription = "Expand",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }

        Card(
            backgroundColor = MyColors.bg2,
            elevation = 6.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            if (expanded) {
                Layout(
                    content = content,
                    modifier = modifier,
                    measurePolicy = measurePolicy
                )
                Icon(
                    FeatherIcons.Minimize2,
                    contentDescription = "Collapse",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp).align(Alignment.TopEnd).clickable { expanded = !expanded }
                )
            }
        }
    }
}


fun customMeasurePolicy() = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.copy(
        minWidth = 0,
        minHeight = 0,
    )
    val placaebles = measurables.map { measurable ->
        measurable.measure(constraints = looseConstraints)
    }

    var height = 0
    placaebles.forEach { placeable ->
        height += placeable.height
    }

    layout(constraints.maxWidth, height) {
        var y = 0
        placaebles.forEach { placeable ->
            placeable.place(0, y)
            y += placeable.height
        }
    }
}
