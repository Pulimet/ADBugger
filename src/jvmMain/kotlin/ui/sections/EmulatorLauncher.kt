package ui.sections

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
import ui.theme.MyColors
import ui.theme.Paddings
import ui.widgets.BtnIcon
import ui.widgets.ExpandableCard
import ui.widgets.LoadingSpinner

@Composable
fun EmulatorLauncher(
    model: AppStore,
    coroutineScope: CoroutineScope,
) {
    ExpandableCard(
        title = "Emulator Launcher",
        modifier = Modifier.padding(
            horizontal = Paddings.cardHorizontal, vertical = Paddings.cardVertical
        )
    ) {
        if (model.state.isEmulatorsLoading) {
            LoadingSpinner(Modifier.padding(Paddings.spinnerPadding).fillMaxWidth())
        } else {
            ContentEmulator(model, coroutineScope)
        }
    }
}

@Composable
fun ContentEmulator(model: AppStore, coroutineScope: CoroutineScope) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            EmulatorItem(
                AppStore.EMULATOR_NONE,
                model.state.selectedEmulator == AppStore.EMULATOR_NONE,
                { model.onEmulatorClick(AppStore.EMULATOR_NONE) },
                modifier = Modifier.weight(1f),
                model,
                coroutineScope
            )
            BtnIcon(
                icon = FontAwesomeIcons.Solid.BookDead,
                onClick = { model.onKillAllEmulatorClick(coroutineScope) },
                description = "Kill All Emulators",
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            BtnIcon(
                icon = Icons.Rounded.Refresh,
                modifier = Modifier.padding(end = 8.dp),
                onClick = { model.onGetEmulatorsListClick(coroutineScope) },
                description = "Refresh Emulators List"
            )
        }
        EmulatorsList(model, coroutineScope) { model.onEmulatorClick(it) }

    }
}

@Composable
private fun EmulatorsList(model: AppStore, coroutineScope: CoroutineScope, onClicked: (emulatorName: String) -> Unit) {
    val listState = rememberLazyListState()
    val state = model.state
    Box(
        modifier = Modifier.fillMaxWidth().heightIn(max = 120.dp).border(BorderStroke(0.5.dp, color = Color.DarkGray))
    ) {
        val items = state.emulatorsList
        LazyColumn(state = listState) {
            items(
                items,
                key = { item -> item }
            ) { item ->
                EmulatorItem(
                    item,
                    state.selectedEmulator == item,
                    { onClicked(it) },
                    Modifier.fillMaxWidth(),
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
    isSelected: Boolean,
    onClicked: (emulatorName: String) -> Unit,
    modifier: Modifier = Modifier,
    model: AppStore,
    coroutineScope: CoroutineScope
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClicked(emulatorName) })
    ) {
        RadioButton(
            selected = isSelected, onClick = { onClicked(emulatorName) }, colors = RadioButtonDefaults.colors(
                selectedColor = MyColors.radioButtonSelected,
                unselectedColor = MyColors.radioButtonUnselected
            )
        )
        Text(text = emulatorName, color = Color.White, fontSize = 12.sp, modifier = Modifier.weight(1f))

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
