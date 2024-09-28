package ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.ChevronUp
import pref.preference


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

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).clickable { toggleState() },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = title,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = Color.White
            )
            Icon(
                if (state) FeatherIcons.ChevronUp else FeatherIcons.ChevronDown,
                contentDescription = "Expand",
                tint = Color.White,
                modifier = Modifier.size(16.dp).padding(top = 2.dp, start = 4.dp)
            )
        }

        if (expanded) {
            Layout(
                content = content,
                measurePolicy = measurePolicy
            )
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
