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
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_release_notes
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_0
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_0_desc
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_1
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_1_desc
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_2
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_2_desc
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_3
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_3_desc
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_4
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_4_desc
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_5
import net.alexandroid.adbugger.adbugger.generated.resources.welcome_v1_0_5_desc
import org.jetbrains.compose.resources.stringResource
import ui.theme.Dimensions

@Composable
fun WelcomeReleaseNotes() {
    Text(
        text = stringResource(Res.string.welcome_release_notes),
        fontSize = Dimensions.titleFontSize,
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 8.dp)
    )
    WelcomeVersion(Res.string.welcome_v1_0_5, Res.string.welcome_v1_0_5_desc)
    WelcomeVersion(Res.string.welcome_v1_0_4, Res.string.welcome_v1_0_4_desc)
    WelcomeVersion(Res.string.welcome_v1_0_3, Res.string.welcome_v1_0_3_desc)
    WelcomeVersion(Res.string.welcome_v1_0_2, Res.string.welcome_v1_0_2_desc)
    WelcomeVersion(Res.string.welcome_v1_0_1, Res.string.welcome_v1_0_1_desc)
    WelcomeVersion(Res.string.welcome_v1_0_0, Res.string.welcome_v1_0_0_desc)
}

