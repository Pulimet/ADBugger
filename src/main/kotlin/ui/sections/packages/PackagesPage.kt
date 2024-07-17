package ui.sections.packages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun PackagesPage(
    modifier: Modifier = Modifier,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Row(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            PackagesMain(Modifier.weight(1f))
            PackageCommands()
        }
    }
}
