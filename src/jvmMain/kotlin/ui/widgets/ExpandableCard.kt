package ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import pref.preference
import ui.theme.MyColors
import ui.theme.bounceClick


@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    content: @Composable () -> Unit
) {
    var state: Boolean by preference("Card_$title", false)
    val measurePolicy = customMeasurePolicy()
    var expanded by remember { mutableStateOf(state) }

    fun toggleState() {
        expanded = !expanded
        state = expanded
    }

    Box {
        if (expanded) {
            Card(
                modifier = modifier,
                backgroundColor = MyColors.bg2,
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            .bounceClick(onClick = ::toggleState),
                        textAlign = TextAlign.Center,
                        text = title,
                        fontSize = 10.sp,
                        color = Color.LightGray
                    )
                    Layout(
                        content = content,
                        modifier = modifier,
                        measurePolicy = measurePolicy
                    )
                }
                Icon(
                    FeatherIcons.Minimize2,
                    contentDescription = "Collapse",
                    tint = Color.White,
                    modifier = Modifier.padding(4.dp).size(16.dp).align(Alignment.TopEnd)
                        .bounceClick(onClick = ::toggleState)
                )
            }
        } else {
            Box(modifier = Modifier.clickable { toggleState() }.padding(vertical = 8.dp, horizontal = 12.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = title,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Icon(
                    FeatherIcons.ChevronDown,
                    contentDescription = "Expand",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp).align(Alignment.TopEnd)
                        .bounceClick(onClick = ::toggleState)
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
