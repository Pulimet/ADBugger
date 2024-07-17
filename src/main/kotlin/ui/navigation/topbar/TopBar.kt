package ui.navigation.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.theme.Dimensions
import ui.theme.MyColors

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth().height(Dimensions.topBarHeight).background(MyColors.bg2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Logo()
        TopBarIcons()
    }
}
