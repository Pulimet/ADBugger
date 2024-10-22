package ui.sections.packages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions
import ui.widgets.CardX

@Composable
fun PackagesPage(
    modifier: Modifier = Modifier,
) {
    CardX(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(
                    top = Dimensions.cardPadding,
                    bottom = Dimensions.cardPadding,
                    start = Dimensions.cardPadding,
                    end = 0.dp
                )
                .fillMaxSize()
        ) {
            PackagesMain(Modifier.weight(1f))
            PackageCommands()
        }
    }
}
