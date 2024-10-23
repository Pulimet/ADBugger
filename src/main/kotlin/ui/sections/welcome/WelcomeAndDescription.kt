package ui.sections.welcome

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_description
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_title
import org.jetbrains.compose.resources.stringResource
import ui.theme.Dimensions

@Composable
fun WelcomeAndDescription() {
    Text(
        text = stringResource(Res.string.welcome_title),
        fontSize = Dimensions.titleFontSize,
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        text = stringResource(Res.string.welcome_description),
        fontSize = Dimensions.subtitleFontSize,
        textAlign = TextAlign.Start,
        color = Color.LightGray,
        modifier = Modifier.padding(16.dp),
    )
}