package ui.sections.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputPage(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        InputLetters()
        Row {
            InputNumbers(Modifier.weight(0.5f).height(180.dp))
            InputArrows(Modifier.weight(0.5f).height(180.dp))
        }
        InputCustom()
    }
}

