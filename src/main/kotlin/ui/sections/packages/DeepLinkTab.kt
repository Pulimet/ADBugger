package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandAndroid
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.buttons.BtnWithText

@Composable
fun DeepLinkTab(model: AppStore = koinInject()) {
    Column(modifier = Modifier.fillMaxSize()) {
        BtnWithText(
            icon = TablerIcons.BrandAndroid,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { },
            description = "Test",
            width = 120.dp,
        )
    }
}