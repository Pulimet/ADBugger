package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.navigation.SelectedPage
import ui.navigation.StatusBar
import ui.sections.AdbLogsPage
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
@Preview
fun Content(model: AppStore = koinInject()) {

    Column(
        modifier = Modifier.background(MyColors.bg2)
            .clip(shape = RoundedCornerShape(Dimensions.pageCornerRadius, 0.dp, 0.dp, 0.dp))
            .background(MyColors.bg)
    ) {
        SelectedPage(modifier = Modifier.weight(if (model.isBottomLogsShown()) 0.75f else 1f))

        if (model.isBottomLogsShown()) {
            AdbLogsPage(Modifier.weight(0.25f))
        }

        StatusBar()
    }

}
