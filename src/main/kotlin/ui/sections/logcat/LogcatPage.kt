package ui.sections.logcat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.list.ListX

@Composable
fun LogcatPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    val searchQuery = remember { mutableStateOf("") }

    Card(
        backgroundColor = MyColors.bg2,
        elevation = Dimensions.pageElevation,
        shape = RoundedCornerShape(Dimensions.pageCornerRadius),
        modifier = modifier.padding(Dimensions.selectedPagePadding),
    ) {
        val list = model.state.logcatLogs

        Row(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Column(modifier = Modifier.weight(0.8f)) {
                LogcatTopBar { searchQuery.value = it }
                Box(modifier = Modifier.fillMaxSize()) {
                    if (list.isEmpty()) {
                        Text(
                            text = "No logs yet.",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White,
                            fontSize = Dimensions.titleFontSize
                        )
                    }

                    ListX(list, searchQuery.value)
                }
            }
            LogcatSideBar(list)
        }

    }
}
