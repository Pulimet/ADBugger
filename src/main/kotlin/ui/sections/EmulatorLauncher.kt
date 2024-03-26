package ui.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.LineAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookDead
import compose.icons.lineawesomeicons.Android
import compose.icons.tablericons.Wiper
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.BtnIcon
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorLauncher(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    Card(
        backgroundColor = MyColors.bg2,
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(Dimensions.selectedPagePadding),
    ) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding)) {
            if (model.state.isEmulatorsLoading) {
                LoadingSpinner(Modifier.padding(Dimensions.spinnerPadding).fillMaxWidth())
            } else {
                ContentEmulator(model, coroutineScope)
            }
        }
    }
}

@Composable
fun ContentEmulator(model: AppStore, coroutineScope: CoroutineScope) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            BtnIcon(
                icon = FontAwesomeIcons.Solid.BookDead,
                onClick = { model.onKillAllEmulatorClick(coroutineScope) },
                description = "Kill All Emulators",
                modifier = Modifier.padding(horizontal = 8.dp),
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
            )
            BtnIcon(
                icon = Icons.Rounded.Refresh,
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = { model.onGetEmulatorsListClick(coroutineScope) },
                description = "Refresh Emulators List",
                buttonSize = Dimensions.btnSizeSmall,
                iconSize = Dimensions.btnIconSizeSmall,
            )
        }
        EmulatorsList(model, coroutineScope)

    }
}

@Composable
private fun EmulatorsList(model: AppStore, coroutineScope: CoroutineScope) {
    val listState = rememberLazyListState()
    val state = model.state
    Box(
        modifier = Modifier.fillMaxSize().border(BorderStroke(1.dp, color = Color.DarkGray))
    ) {
        val items = state.emulatorsList
        LazyColumn(state = listState) {
            items(
                items,
                key = { item -> item }
            ) { item ->
                EmulatorItem(
                    item,
                    model,
                    coroutineScope
                )
            }
        }
        if (items.size > 2) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = listState
                )
            )
        }
    }
}

@Composable
private fun EmulatorItem(
    emulatorName: String,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = emulatorName,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )

        if (emulatorName != AppStore.EMULATOR_NONE) {
            BtnIcon(
                icon = TablerIcons.Wiper,
                onClick = { model.onWipeAndLaunch(coroutineScope, emulatorName) },
                description = "Wipe Data",
                modifier = Modifier.padding(end = 4.dp)
            )
            BtnIcon(
                icon = LineAwesomeIcons.Android,
                onClick = { model.onLaunchEmulatorClick(coroutineScope, emulatorName) },
                description = "Launch Emulator",
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}
