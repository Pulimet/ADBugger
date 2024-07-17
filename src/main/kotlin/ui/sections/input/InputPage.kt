package ui.sections.input

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject
import store.AppStore

@Composable
@Preview
fun InputPage(
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    Column(modifier = modifier) {
        InputLetters(model, coroutineScope)
        Row {
            InputNumbers(model, coroutineScope, Modifier.weight(0.5f).height(180.dp))
            InputArrows(model, coroutineScope, Modifier.weight(0.5f).height(180.dp))
        }
        InputCustom(model, coroutineScope)
    }
}

