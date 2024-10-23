package ui.sections.welcome

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions

@Composable
fun WelcomeVersion(title: String, description: String) {
    Text(
        text = title,
        fontSize = Dimensions.releaseNotesFontSize,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )
    Text(
        text = description,
        fontSize = Dimensions.releaseNotesFontSize,
        textAlign = TextAlign.Start,
        color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}

