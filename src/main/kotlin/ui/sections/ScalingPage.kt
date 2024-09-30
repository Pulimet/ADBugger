package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.PlayerPlay
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnIcon
import ui.widgets.buttons.SmallHoverButton

@Composable
fun ScalingPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title("Font Scale:")
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(0.85) },
                    enabled = true,
                    text = "Small 0.85"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.0) },
                    enabled = true,
                    text = "Default 1.0"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.15) },
                    enabled = true,
                    text = "Large 1.15"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.3) },
                    enabled = true,
                    text = "Largest 1.3"
                )
                TextFieldWithBtn(
                    "Custom (ex: 1.0)",
                    onClick = { model.scaleFontTo(it.toDouble()) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title("Density:")
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(0) },
                    enabled = true,
                    text = "Reset"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(300) },
                    enabled = true,
                    text = "300"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(400) },
                    enabled = true,
                    text = "400"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(500) },
                    enabled = true,
                    text = "500"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(600) },
                    enabled = true,
                    text = "600"
                )
                TextFieldWithBtn(
                    "Custom (ex: 300)",
                    onClick = { model.setDensity(it.toInt()) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title("Display size:")
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setSize("0") },
                    enabled = true,
                    text = "Reset"
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setSize("2048x1536") },
                    enabled = true,
                    text = "2048x1536"
                )
                TextFieldWithBtn(
                    "Custom (ex: 10x10)",
                    onClick = { model.setSize(it) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        color = Color.LightGray,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .width(90.dp)
            .padding(bottom = 3.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
    )
}

@Composable
private fun TextFieldWithBtn(
    label: String,
    onClick: (String) -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchTextField by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = modifier, horizontalArrangement = Arrangement.End) {
        TextFieldX(
            value = searchTextField,
            onValueChange = { newText ->
                searchTextField = newText
                onSearchQueryChange(newText.text)
            },
            padding = PaddingValues(top = 2.dp),
            label = label,
        )

        BtnIcon(
            icon = TablerIcons.PlayerPlay,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { onClick(searchTextField.text) },
            description = "Run"
        )
    }
}
