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
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_300
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_400
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_500
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_600
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_custom_density
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_custom_display_size
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_custom_font
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_default
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_default_density
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_density
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_display_size
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_font_scale
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_large
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_largest
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_reset
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_run
import net.alexandroid.adbugger.adbugger.generated.resources.scaling_small
import org.jetbrains.compose.resources.stringResource
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
                Title(stringResource(Res.string.scaling_font_scale))
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(0.85) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_small)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.0) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_default)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.15) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_large)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.scaleFontTo(1.3) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_largest)
                )
                TextFieldWithBtn(
                    stringResource(Res.string.scaling_custom_font),
                    onClick = { model.scaleFontTo(it.toDouble()) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title(stringResource(Res.string.scaling_density))
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(0) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_reset)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(300) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_300)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(400) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_400)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(500) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_500)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setDensity(600) },
                    enabled = true,
                    text = stringResource(Res.string.scaling_600)
                )
                TextFieldWithBtn(
                    stringResource(Res.string.scaling_custom_density),
                    onClick = { model.setDensity(it.toInt()) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title(stringResource(Res.string.scaling_display_size))
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setSize("0") },
                    enabled = true,
                    text = stringResource(Res.string.scaling_reset)
                )
                SmallHoverButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { model.setSize("2048x1536") },
                    enabled = true,
                    text = stringResource(Res.string.scaling_default_density)
                )
                TextFieldWithBtn(
                    stringResource(Res.string.scaling_custom_display_size),
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
            description = stringResource(Res.string.scaling_run)
        )
    }
}
